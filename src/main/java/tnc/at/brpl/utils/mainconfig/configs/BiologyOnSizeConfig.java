package tnc.at.brpl.utils.mainconfig.configs;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.models.main.BiologyOnSizeDetail;
import tnc.at.brpl.models.main.BiologyOnSizeSampleDetail;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.services.main.BiologyOnSizeService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.mainconfig.AnalysisFile;
import tnc.at.brpl.utils.mainconfig.Translator;
import tnc.at.brpl.utils.mainconfig.TranslatorResult;
import tnc.at.brpl.utils.mainconfig.models.FieldModel;
import tnc.at.brpl.utils.mainconfig.regulations.FormRegulations;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Configuration
public class BiologyOnSizeConfig extends BasicFormUploadConfig implements FormRegulations<BiologyOnSize> {

    private final String formIdentifier = "Form Sampling Biologi Komposisi Ukuran";
    private int formIdentifierRow = 0;
    private int formIdentifierColumn = 1;
    private final String secretFormIdentifier = "BS";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    public List<FieldModel> configurationLoaded;
    ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    BiologyOnSizeService sizeService;

    @Autowired
    SysUserRepository sysUserRepository;


    /**
     *
     * @param builder
     * @return
     */
    private String addComa(StringBuilder builder) {
        return (builder.toString().isEmpty()) ? "" : ", ";
    }

    public boolean isSelfForm(StorageService storageService, MultipartFile multipartFile) throws Exception {

        File file = storageService.getMultipartFile(multipartFile,
                storageService.generateFileName(multipartFile.getOriginalFilename(), StorageService.Target.BIOLOGI_UKURAN));

        return isSelfForm(file);
    }


    public boolean isSelfForm(File file) throws Exception {
        AnalysisFile analysisFile = new AnalysisFile();
        XSSFSheet sheet = analysisFile.analisysExcelFile(file);

        try {
            file.delete();
        } catch (Exception e) {}
        return isSelfForm(sheet);
    }


    private boolean isSelfForm(Sheet sheet) {
        return !(sheet.getRow(formIdentifierRow).getCell(formIdentifierColumn).getStringCellValue().isEmpty() ||
                !sheet.getRow(formIdentifierRow).getCell(formIdentifierColumn).getStringCellValue().equals(secretFormIdentifier));
    }

    public TranslatorResult uploadProcess(String uploaderUuid,
                                          StorageService storageService,
                                          MultipartFile file,
                                          DocumentStatus status) throws Exception {
        return uploadProcess(storageService, file,
                storageService.generateFileName(file.getOriginalFilename(), StorageService.Target.BIOLOGI_UKURAN),
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
        return "Data Ukuran tanggal " +
                CustomDateSerializer.toPattern(date) + " <b>Sudah ada di Database</b>!!!";
    }

    public String exists(Date date, String message) {
        return "Data Ukuran tanggal " +
                CustomDateSerializer.toPattern(date) + " <b>" + message + "</b>!!!";
    }

    /**
     * Menangani proses upload form biologi ukuran
     *
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
                                          int sheetIndex,
                                          DocumentStatus status,
                                          int role) throws Exception {
        File tempFile = storageService.getMultipartFile(file, tempName);
        AnalysisFile analysisFile = new AnalysisFile();
        Translator translator = new Translator();

        Sheet sheet = null;
        if (getExtention(tempName).equals("xls")) {
            sheet = analysisFile.analisysExcelFileHSS(tempFile);
        } else {
            sheet = analysisFile.analisysExcelFile(tempFile);
        }

//        XSSFSheet sheet = analysisFile.analisysExcelFile(tempFile);

        if (!isSelfForm(sheet)) {
            tempFile.delete(); // hapus file semntara yang diupload dari server
            translator.getErrors().add("Bukan Format File Biologi Ukuran"); // tambahkan pesan error
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
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);

        BiologyOnSize biologyOnSize = objectMapper.readValue(translated, BiologyOnSize.class);
        biologyOnSize.setStatusDokumen(status);
        if (biologyOnSize.getOrganisasi().toUpperCase().equals(Brpl.DEFAULT_ORGANIZATION)) {
            biologyOnSize.setOrganisasi(biologyOnSize.getOrganisasi().toUpperCase());
        } // set to uppercase

        if (sizeService.isDuplicated(biologyOnSize)) {
            translator.getErrors().add(
                    exists(biologyOnSize.getTanggalSampling())
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
                        biologyOnSize.setStatusDokumen(DocumentStatus.NOT_VERIFIED);
                    else
                        biologyOnSize.setStatusDokumen(DocumentStatus.DRAFT);

                    if (!biologyOnSize.getOrganisasi().equals(Brpl.DEFAULT_ORGANIZATION)) {
                        biologyOnSize.setOrganisasi(Brpl.DEFAULT_ORGANIZATION);
                    }
                } else {
                    biologyOnSize.setOrganisasi(sysUser.getOrganisasi());
                    if (role == 5) { // user biasa dari ngo
                        biologyOnSize.setStatusDokumen(DocumentStatus.WAITING);
                    } else if (role == 2) { // validator dari ngo
                        biologyOnSize.setStatusDokumen(DocumentStatus.NOT_VERIFIED);
                    }
                }
            } else {
                biologyOnSize.setStatusDokumen(DocumentStatus.NOT_VERIFIED);
                biologyOnSize.setOrganisasi(Brpl.DEFAULT_ORGANIZATION);
            }


            biologyOnSize.setUuidPengupload(uploaderUuid);
        }

        List<String> errors;
        errors = translator.getErrors();
        errors = analyzingDataRegulations(biologyOnSize, errors);  // analisa file dengan aturan yang telah ditetapkan

        errors = compabilityRecheck(errors, biologyOnSize); // recheck

        if (errors.size() > 0) {
            return TranslatorResult.builder()
                    .errorsFound(errors).errorType(ResponseResolver.DataErrorType.RAW_ERROR).build();
        }


        tempFile.delete(); // hapus file semntara yang diupload dari server
        return TranslatorResult.builder()
                .errorsFound(translator.getErrors())
                .objectResult(biologyOnSize).build();
    }


    /**
     *
     * @param errors
     * @param data
     * @return
     */
    private List<String> compabilityRecheck(List<String> errors, BiologyOnSize data) {


        if (data.getOrganisasi() == null || data.getOrganisasi().isEmpty())
            errors.add("Pastikan anda menentukan nama organisasi data yang akan diupload");

        if (data.getUuidEnumerator() == null || data.getWpp().isEmpty())
            errors.add("Wilayah WPP belum dipilih");


        if (data.getUuidEnumerator() == null || data.getUuidEnumerator().isEmpty())
            errors.add("Nama Pencatat masih Kosong");

        if (data.getNamaLokasiSampling() == null || data.getNamaLokasiSampling().isEmpty())
            errors.add("Nama Lokasi Sampling masih kosong");

        if (data.getTanggalSampling() == null)
            errors.add("Pastikan tanggal sampling anda inputkan dengan benar");

        if (data.getUuidSumberDaya() == null || data.getUuidSumberDaya().isEmpty())
            errors.add("Pilihlah salah satu jenis sumberdaya");

        if (data.getNamaKapal() == null || data.getNamaKapal().isEmpty())
            errors.add("Mohon inputkan nama kapal yang benar");


//        if ((!data.isPenampung() && !data.isPenangkap()) )
//            errors.add("Pastikan salah satu kolom Penangkap atau Penampung terpilih.");

        if (data.getUuidAlatTangkap() == null || data.getUuidAlatTangkap().isEmpty())
            errors.add("Pilihlah salah satu jenis alat tangkap yang ddigunakan");

        if (data.getDaerahPenangkapan() == null || data.getDaerahPenangkapan().isEmpty())
            errors.add("Silahkan inputkasn Grid Daerah Penangkapan yang benar.");


//        if ((data.getTotalTangkapanVolume() == 0 && data.getTotalTangkapanIndividu() == 0))
//            errors.add("Pastikan anda mengisi salah satu kolom total tangkapan, dalam satuan  yang tersedia (Volume/ Individu).");
//
//        if ((data.getTotalSampelVolume() == 0 && data.getTotalSampelIndividu() == 0) )
//            errors.add("Pastikan anda mengisi salah satu kolom total sample, dalam satuan  yang tersedia (Volume/ Individu).");


        int i = 1;

        for (BiologyOnSizeSampleDetail sizeSampleDetail: data.getDataSampleDetail()) {
            boolean someNotNull = false;

            if ((sizeSampleDetail.getUuidSpesies() != null && !sizeSampleDetail.getUuidSpesies().isEmpty()) ||
                    (sizeSampleDetail.getSampleIndividu() > 0) ||
                    (sizeSampleDetail.getSampleVolume() > 0) ||
                    (sizeSampleDetail.getTipePanjang() != null && !sizeSampleDetail.getTipePanjang().isEmpty())){
                someNotNull = true;
            }

            if (someNotNull) {
                if (sizeSampleDetail.getUuidSpesies() == null || sizeSampleDetail.getUuidSpesies().isEmpty())
                    errors.add("Mohon Pastikan nama Spesies ikan tidak kosong.\n (Sample No. " + i + ").");

                if ((sizeSampleDetail.getSampleIndividu() == 0 && sizeSampleDetail.getSampleVolume() == 0))
                    errors.add("Pastikan anda mengisi salah satu jumalh sample yang tersedia (Volume/ Individu).\n (Sample No. " + i + ").");
//
                if ((sizeSampleDetail.getTipePanjang() == null || sizeSampleDetail.getTipePanjang().isEmpty()))
                    errors.add("Pastikan tipe panjangnya terdefinisi.\n (Data Rincian Sample No. " + i + ").");


            }

            i++;
        }


        int j = 1;
        for (BiologyOnSizeDetail sizeDetail: data.getDataUkuranDetail()) {

            if ((sizeDetail.getUuidSpesies() == null || sizeDetail.getUuidSpesies().isEmpty()) &&
                    (sizeDetail.getPanjang() > 0 ))
                errors.add("Mohon Pastikan nama Spesies ikan tidak kosong.\n (Data Ukuran Ke. " + j + ").");

            if ((sizeDetail.getUuidSpesies() != null && !sizeDetail.getUuidSpesies().isEmpty()) &&
                    (sizeDetail.getPanjang() <= 0))
                errors.add("Inputkan nilai panjang dengan benar.\n (Data Ukuran Ke. " + j + ").");

            j++;
        }


        return errors;
    }




    /**
     * fungsi yang digunakan untuk menganalisa isi file apakah sesuai dengan
     * aturan yang diterapkan untuk setiap format form
     *
     * @param data data yang didapat dari file excel yang teruplaod
     * @return null jika tidak ada aturan/role yang tidak sesuai
     */
    @Override
    public List<String> analyzingDataRegulations(BiologyOnSize data) {
        List<String> errors = new ArrayList<>();
        return analyzingDataRegulations(data, errors);
    }

    @Override
    public List<String> analyzingDataRegulations(BiologyOnSize data, List<String> errors) {

//        errors = speciesLengthAndLengthTypeOnDetailsRegulation(errors, data);
        errors = individualSampleEqualsDetailsRegulation(errors, data);
        errors = catchGreaterThanSample(errors, data);
        errors = totalSampleMustEqualsSampleDetails(errors, data);
//        errors = catchAndSample(errors, data);
        return errors;
    }


    private List<String> totalSampleMustEqualsSampleDetails(List<String> errors, BiologyOnSize data) {

        double kg = 0;
        int ekor = 0;
        for (BiologyOnSizeSampleDetail detail: data.getDataSampleDetail()) {
            kg += detail.getSampleVolume();
            ekor += detail.getSampleIndividu();
        }


//        if (data.getTotalSampelVolume() < kg || data.getTotalSampelVolume() > kg)
//            errors.add("Pastikan jumlah sample volume sama dengan jumlah keseluruhan rincian data sample volume");
//
//
//        if (data.getTotalSampelIndividu() < ekor|| data.getTotalSampelIndividu() > ekor)
//            errors.add("Pastikan jumlah sample individu sama dengan jumlah keseluruhan rincian data sample individu");


        return errors;
    }




    /**
     * Fungsi yang digunakan untuk aturan Total Jumlah Individu (Ekor) sama dengan jumlah
     * species pada Rincian Data Ukuran
     *
     * @param errors Error List yang ada
     * @param data data yang didapat dari file excel yang terupload
     * @return
     */
    private List<String> individualSampleEqualsDetailsRegulation(List<String> errors, BiologyOnSize data) {

        if (data.getTotalSampelIndividu() > 0 && data.getTotalSampelVolume() <= 0) {
//            errors.add((data.getTotalSampelIndividu() == data.getDataUkuranDetail().size())
//                    ? "" : "Total Data sample per individu (Ekor) tidak sama dengan jumlah sample ukuran pada rincian data.");
        }

        return errors;
    }


    /**
     * Fungsi yang digunakan untuk Total Hasil Tangkapan lebih besar dibandingkan dengan Jumlah Sampel
     *
     * @param errors Error List yang ada
     * @param data data yang didapat dari file excel yang terupload
     * @return
     */
    private List<String> catchGreaterThanSample(List<String> errors, BiologyOnSize data) {

        if (data.getTotalTangkapanVolume() > 0 && data.getTotalTangkapanIndividu() <= 0) { // jika volume terisi
//            if ((data.getTotalTangkapanVolume() < data.getTotalSampelVolume())) {
//                errors.add("Jumlah sample volume (Kg) tidak boleh lebih besar dari jumlah tangkapan volume (Kg)");
//            }
        } else if (data.getTotalTangkapanVolume() <= 0 && data.getTotalTangkapanIndividu() > 0) {  // jika individu terisi
//            if (data.getTotalTangkapanIndividu() < data.getTotalSampelIndividu()) {
//                errors.add("Jumlah sample individu (Ekor) tidak boleh lebih besar dari jumlah tangkapan individu (Ekor)");
//            }
        }

        return errors;
    }


    /**
     * Fungsi yang digunakan untuk Melihat hasil tangkapan dan juga sample (Volume, atau individu == Harus salah satu yang diisi)
     *
     * @param errors Error List yang ada
     * @param data data yang didapat dari file excel yang terupload
     * @return
     */
    private List<String> catchAndSample(List<String> errors, BiologyOnSize data) {

        if (data.getTotalTangkapanVolume() > 0 && data.getTotalTangkapanIndividu() > 0) { // pada total tangkapan
            errors.add("Total Hasil Tangkapan hanya boleh berisi salah satu nilai (Volume atau Individu)");
        }

        if (data.getTotalSampelVolume() > 0 && data.getTotalSampelIndividu() > 0) {  // pada total sample
            errors.add("Total Sample hanya boleh berisi salah satu nilai (Volume atau Individu)");
        }

        return errors;
    }

}
