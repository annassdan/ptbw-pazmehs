package tnc.at.brpl.utils.mainconfig.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.BiologyOnReproductionDetail;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.services.main.BiologyOnReproductionService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.mainconfig.AnalysisFile;
import tnc.at.brpl.utils.mainconfig.Translator;
import tnc.at.brpl.utils.mainconfig.TranslatorResult;
import tnc.at.brpl.utils.mainconfig.models.FieldModel;
import tnc.at.brpl.utils.mainconfig.regulations.FormRegulations;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Configuration
public class BiologyOnReproductionConfig extends BasicFormUploadConfig implements FormRegulations<BiologyOnReproduction> {

    private final String formIdentifier = "Form Sampling Biologi Reproduksi";
    private int formIdentifierRow = 0;
    private int formIdentifierColumn = 1;
    private final String secretFormIdentifier = "BR";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    public List<FieldModel> configurationLoaded;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    BiologyOnReproductionService reproductionService;

    @Autowired
    SysUserRepository sysUserRepository;

    /**
     * @param builder
     * @return
     */
    private String addComa(StringBuilder builder) {
        return (builder.toString().isEmpty()) ? "" : ", ";
    }

    public boolean isSelfForm(StorageService storageService, MultipartFile multipartFile) throws Exception {

        File file = storageService.getMultipartFile(multipartFile,
                storageService.generateFileName(multipartFile.getOriginalFilename(), StorageService.Target.BIOLOGI_REPRODUKSI));

        return isSelfForm(file);
    }


    public boolean isSelfForm(File file) throws Exception {
        AnalysisFile analysisFile = new AnalysisFile();
        XSSFSheet sheet = analysisFile.analisysExcelFile(file);

        try {
            file.delete();
        } catch (Exception e) {
        }
        return isSelfForm(sheet);
    }


    private boolean isSelfForm(Sheet sheet) {
        return !(sheet.getRow(formIdentifierRow).getCell(formIdentifierColumn).getStringCellValue().isEmpty() ||
                !sheet.getRow(formIdentifierRow).getCell(formIdentifierColumn).getStringCellValue().equals(secretFormIdentifier));
    }


    public TranslatorResult uploadProcess(String uploaderUuid,
                                          StorageService storageService,
                                          MultipartFile file, DocumentStatus status) throws Exception {
        return uploadProcess(storageService, file,
                storageService.generateFileName(file.getOriginalFilename(), StorageService.Target.BIOLOGI_REPRODUKSI),
                uploaderUuid, 0, status, 0);
    }

    private String getExtention(String tempName) {
        String ext = "";
        for (int i = tempName.length() - 1; i >= 0 ; i--) {
            if (String.valueOf(tempName.charAt(i)).equals("."))
                break;

            ext = String.valueOf(tempName.charAt(i)) + ext;
        }

        return ext;
    }


    public String exists(Date date) {
        return "Data Reproduksi tanggal " +
                CustomDateSerializer.toPattern(date) + " <b>Sudah ada di Database</b>!!!";
    }

    public String exists(Date date, String message) {
        return "Data Reproduksi tanggal " +
                CustomDateSerializer.toPattern(date) + " <b>" + message + "</b>!!!";
    }


    /**
     * @param storageService
     * @param file
     * @param tempName
     * @return
     * @throws Exception
     */
    @Override
    public TranslatorResult uploadProcess(StorageService storageService,
                                          MultipartFile file, String tempName,
                                          String uploaderUuid,
                                          int sheetIndex, DocumentStatus status,
                                          int role) throws Exception {
        File tempFile = storageService.getMultipartFile(file, tempName);
        AnalysisFile analysisFile = new AnalysisFile();
        Translator translator = new Translator();
//        XSSFSheet sheet = analysisFile.analisysExcelFile(tempFile);

        Sheet sheet = null;
        if (getExtention(tempName).equals("xls")) {
            sheet = analysisFile.analisysExcelFileHSS(tempFile);
        } else {
            sheet = analysisFile.analisysExcelFile(tempFile);
        }

        if (!isSelfForm(sheet)) {
            tempFile.delete(); // hapus file semntara yang diupload dari server
            translator.getErrors().add("Bukan Format File Biologi Reproduksi"); // tambahkan pesan error
            return TranslatorResult.builder()
                    .errorsFound(translator.getErrors()).build();
        }


        // check version
//        if (sheet.getRow(0).getCell(0).getStringCellValue().isEmpty() ||
//                !sheet.getRow(0).getCell(0).getStringCellValue().equals(Brpl.FORM_SURRENT_VERSION)) {
//        tempFile.delete(); // hapus file semntara yang diupload dari server
//            translator.getErrors().add("Pastikan anda menggunakan form versi terbaru."); // tambahkan pesan error
//            return TranslatorResult.builder()
//                    .errorsFound(translator.getErrors()).build();
//        }

        String translated = translator.translate(configurationLoaded, sheet);

        /* hasil json dari translasi mapping menjadi object class Pendaratan*/
        BiologyOnReproduction biologyOnReproduction = objectMapper.readValue(translated, BiologyOnReproduction.class);
//        biologyOnReproduction.setStatusDokumen(DocumentStatus.DRAFT);

        if (biologyOnReproduction.getOrganisasi().toUpperCase().equals(Brpl.DEFAULT_ORGANIZATION)) {
            biologyOnReproduction.setOrganisasi(biologyOnReproduction.getOrganisasi().toUpperCase());
        } // set to uppercase

        if (reproductionService.isDuplicated(biologyOnReproduction)) {
            translator.getErrors().add(
                    exists(biologyOnReproduction.getTanggalSampling())
            ); // tambahkan pesan error
            return TranslatorResult.builder()
                    .errorsFound(translator.getErrors()).build();
        }

        if (uploaderUuid != null || uploaderUuid.length() > 0) {

            if (role > 1) {
                SysUser sysUser = sysUserRepository.findOne(uploaderUuid);

                if (Brpl.DEFAULT_ORGANIZATION.toLowerCase().trim().equals(sysUser.getOrganisasi().toLowerCase().trim())
                || sysUser.getOrganisasi().toLowerCase().trim().contains(Brpl.DEFAULT_ORGANIZATION.toLowerCase())) {
                    if (role == 3 || role == 4) // jika yang upload adlah verifikator
                        biologyOnReproduction.setStatusDokumen(DocumentStatus.NOT_VERIFIED);
                    else
                        biologyOnReproduction.setStatusDokumen(DocumentStatus.DRAFT);
                    if (!biologyOnReproduction.getOrganisasi().equals(Brpl.DEFAULT_ORGANIZATION)) {
                        biologyOnReproduction.setOrganisasi(Brpl.DEFAULT_ORGANIZATION);
                    }
                } else {
                    biologyOnReproduction.setOrganisasi(sysUser.getOrganisasi());
                    if (role == 5) { // user biasa dari ngo
                        biologyOnReproduction.setStatusDokumen(DocumentStatus.PENDING);
                    }
                }

            } else {
                biologyOnReproduction.setStatusDokumen(DocumentStatus.NOT_VERIFIED);
            }


            biologyOnReproduction.setUuidPengupload(uploaderUuid);
        }

        tempFile.delete(); // hapus file semntara yang diupload dari server

        List<String> errors;
        errors = translator.getErrors();
        errors = analyzingDataRegulations(biologyOnReproduction, errors);  // analisa file dengan aturan yang telah ditetapkan

        errors = compabilityRecheck(errors, biologyOnReproduction); // recheck
        if (errors.size() > 0) {
            return TranslatorResult.builder()
                    .errorsFound(errors).errorType(ResponseResolver.DataErrorType.RAW_ERROR).build();
        }


        return TranslatorResult.builder()
                .errorsFound(translator.getErrors())
                .objectResult(biologyOnReproduction).build();
    }


    private List<String> compabilityRecheck(List<String> errors, BiologyOnReproduction data) {

        if (data.getOrganisasi() == null || data.getOrganisasi().isEmpty())
            errors.add("Pastikan anda menentukan nama organisasi data yang akan diupload");

        if (data.getUuidEnumerator() == null || data.getUuidEnumerator().isEmpty())
            errors.add("Nama Pencatat masih Kosong");

        if (data.getNamaLokasiSampling() == null || data.getNamaLokasiSampling().isEmpty())
            errors.add("Nama Lokasi Sampling masih kosong");

        if (data.getUuidEnumerator() == null || data.getWpp().isEmpty())
            errors.add("Wilayah WPP belum dipilih");


        if (data.getTanggalSampling() == null)
            errors.add("Pastikan tanggal sampling anda inputkan dengan benar");

        if (data.getUuidSumberDaya() == null || data.getUuidSumberDaya().isEmpty())
            errors.add("Pilihlah salah satu jenis sumberdaya");

        if (data.getNamaKapal() == null || data.getNamaKapal().isEmpty())
            errors.add("Mohon inputkan nama kapal yang benar");


//        if ((!data.isPenampung() && !data.isPenangkap()))
//            errors.add("Pastikan salah satu kolom Penangkap atau Penampung terpilih.");


        if (data.getUuidAlatTangkap() == null || data.getUuidAlatTangkap().isEmpty())
            errors.add("Pilihlah salah satu jenis alat tangkap yang digunakan");

        if (data.getDaerahPenangkapan() == null || data.getDaerahPenangkapan().isEmpty())
            errors.add("Silahkan inputkasn Grid Daerah Penangkapan yang benar.");

        if (data.getUuidSpesies() == null || data.getUuidSpesies().isEmpty())
            errors.add("Mohon Pastikan nama Spesies ikan tidak kosong");


        int i = 1;
        for (BiologyOnReproductionDetail reproductionDetail : data.getDataDetailReproduksi()) {

//            to upper case jenis kelamin
            reproductionDetail.setJenisKelamin(reproductionDetail.getJenisKelamin().toUpperCase());

            boolean notEmpty = false;

            if (reproductionDetail.getPanjang() > 0 || (reproductionDetail.getTipePanjang() != null && !reproductionDetail.getTipePanjang().isEmpty()) ||
                    reproductionDetail.getBerat() > 0 || reproductionDetail.getBeratIsiPerut() > 0 ||
                    (reproductionDetail.getJenisKelamin() != null && !reproductionDetail.getJenisKelamin().isEmpty())) {
                notEmpty = true;
            }


            if (notEmpty) {
                if (reproductionDetail.getPanjang() <= 0)
                    errors.add("Inputkan nilai panjang dengan benar.\n (Data Biologi No. " + i + ").");

                if ((reproductionDetail.getTipePanjang() == null && reproductionDetail.getTipePanjang().isEmpty()))
                    errors.add("Pastikan tipe panjangnya terdefinisi.\n (Data Biologi No. " + i + ").");

                if (reproductionDetail.getJenisKelamin() == null || reproductionDetail.getJenisKelamin().isEmpty())
                    errors.add("Pastikan jenis kelamin terdefinisikan.\n (Data Biologi No. " + i + ").");

//                if (reproductionDetail.getBerat() <= 0)
//                    errors.add("Inputkan nilai berat diatas 0.\n (Data Biologi No. " + i + ").");

//                if (reproductionDetail.getBeratIsiPerut() < 1)
//                    errors.add("Berat Gonad tidak boleh dibawah 1 gram.\n (Data Biologi No. " + i + ").");


            }

//            if (reproductionDetail.getPanjang() <= 0 &&
//                    (
//                    (reproductionDetail.getTipePanjang() != null && !reproductionDetail.getTipePanjang().isEmpty()) ||
//                     reproductionDetail.getBerat() > 0 || reproductionDetail.getBeratIsiPerut() > 0 ||
//                     (reproductionDetail.getJenisKelamin() != null && !reproductionDetail.getJenisKelamin().isEmpty())
//                    ))
//                errors.add("Inputkan nilai panjang dengan benar.\n (Data Biologi No. " + i + ").");
//
//
//            if (reproductionDetail.getPanjang() > 0) {
//
//                if (reproductionDetail.getTipePanjang() == null || reproductionDetail.getTipePanjang().isEmpty())
//                    errors.add("Pastikan tipe panjangnya terdefinisi.\n (Data Biologi No. " + i + ").");
//
//                if (reproductionDetail.getJenisKelamin() == null || reproductionDetail.getJenisKelamin().isEmpty())
//                    errors.add("Pastikan jenis kelamin terdefinisikan.\n (Data Biologi No. " + i + ").");
//
//                if (reproductionDetail.getBerat() <= 0)
//                    errors.add("Inputkan nilai berat diatas 0.\n (Data Biologi No. " + i + ").");
//
//                if (reproductionDetail.getBeratIsiPerut() <= 0)
//                    errors.add("Inputkan nilai berat isi perut diatas 0.\n (Data Biologi No. " + i + ").");
//
//
//            }

            i++;
        }


        return errors;
    }


    /**
     * @param data
     * @return
     */
    @Override
    public List<String> analyzingDataRegulations(BiologyOnReproduction data) {
        List<String> errors = new ArrayList<>();
        return analyzingDataRegulations(data, errors);
    }

    @Override
    public List<String> analyzingDataRegulations(BiologyOnReproduction data, List<String> errors) {

//        errors = lengthAndLengthTypeOnDetailsRegulation(errors, data);
//        errors = gonadWeight(errors, data);
//        errors = catcherOrCollector(errors, data);
        return errors;
    }


    /**
     * Pengecekan terhadap penanpung atau penangkap, dimana data tersebut salah satu harus mempunyai isi
     *
     * @param errors
     * @param data
     * @return
     */
    private List<String> catcherOrCollector(List<String> errors, BiologyOnReproduction data) {

        if (!data.isPenampung() && !data.isPenangkap()) {
            errors.add("Pastikan salah satu kolom Penangkap atau Penampung terisi)");
        }

        return errors;
    }


    /**
     * Fungsi yang digunakan untuk menganalisa Panjang dan Tipe Panjang harus terisi lengkap terisi
     *
     * @param errors Error List yang ada
     * @param data   data yang didapat dari file excel yang terupload
     * @return
     */
    private List<String> lengthAndLengthTypeOnDetailsRegulation(List<String> errors, BiologyOnReproduction data) {

        for (BiologyOnReproductionDetail detail : data.getDataDetailReproduksi()) {
            StringBuilder mError = new StringBuilder();

            if ((detail.getPanjang() > 0) &&
                    (detail.getTipePanjang() != null && !detail.getTipePanjang().trim().isEmpty())) {
                continue;  // langsung lanjut ke data yang selanjutnya
            }

            mError.append((detail.getPanjang() == 0) ? "Ada Data Panjang yang Tidak Valid/Kosong" : "");  // Panjang tidak boleh kosong
            mError.append( // Tipe panjang tidak boleh kosong
                    (detail.getTipePanjang() == null || detail.getTipePanjang().trim().isEmpty())
                            ? addComa(mError) + "Ada Data Tipe Panjang yang Tidak Valid/Kosong" : ""
            );

            if (!mError.toString().isEmpty()) { // jika terdapat error
                errors.add(mError.toString());
            }

        }

        return errors;
    }


    /**
     * Fungsi untuk Berat gonand error untuk dibawah 1 gram
     *
     * @param errors
     * @param data
     * @return
     */
    private List<String> gonadWeight(List<String> errors, BiologyOnReproduction data) {

        for (BiologyOnReproductionDetail detail : data.getDataDetailReproduksi()) {
            StringBuilder mError = new StringBuilder();

            // Berat Gonad tidak boleh dibawah 1 gr
//           for (BiologyOnReproductionStomachContentsDetail d: detail.getDataIsiPerutDetail()){
//               mError.append((d.getBeratIsiPerut() < 1) ? "Berat Gonad tidak boleh dibawah 1 gram" : "");
//               break;
//           }

            mError.append((detail.getBeratIsiPerut() < 1) ? "Berat Gonad tidak boleh dibawah 1 gram" : "");

            if (!mError.toString().isEmpty()) { // jika terdapat error
                errors.add(mError.toString());
            }
        }

        return errors;
    }


}
