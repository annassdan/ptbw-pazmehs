package tnc.at.brpl.utils.mainconfig.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
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
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.LandingCatchDetail;
import tnc.at.brpl.models.main.LandingDetail;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.services.main.LandingService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.mainconfig.AnalysisFile;
import tnc.at.brpl.utils.mainconfig.Translator;
import tnc.at.brpl.utils.mainconfig.TranslatorResult;
import tnc.at.brpl.utils.mainconfig.models.FieldModel;
import tnc.at.brpl.utils.mainconfig.regulations.FormRegulations;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Configuration
public class LandingConfig extends BasicFormUploadConfig implements FormRegulations<Landing> {

    private final String formIdentifier = "Form Pendaratan";
    private int formIdentifierRow = 0;
    private int formIdentifierColumn = 1;

    @Autowired
    LandingService landingService;

    @Autowired
    SysUserRepository sysUserRepository;


    /**
     * Data rahasia untuk mengindentifikasi form pendaratan
     */
    @SuppressWarnings("unused")
    private final String secretFormIdentifier = "L";

    @SuppressWarnings("unused")
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    public List<FieldModel> configurationLoaded;
    ObjectMapper objectMapper = new ObjectMapper();


    public boolean isSelfForm(StorageService storageService, MultipartFile multipartFile) throws Exception {

        File file = storageService.getMultipartFile(multipartFile,
                storageService.generateFileName(multipartFile.getOriginalFilename(), StorageService.Target.PENDARATAN));
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
//        private boolean isSelfForm(XSSFSheet sheet) {
        return !(sheet.getRow(formIdentifierRow).getCell(formIdentifierColumn).getStringCellValue().isEmpty() ||
                !sheet.getRow(formIdentifierRow).getCell(formIdentifierColumn).getStringCellValue().equals(secretFormIdentifier));
    }


    public TranslatorResult uploadProcess(String uploaderUuid,
                                          StorageService storageService,
                                          MultipartFile file,
                                          DocumentStatus status) throws Exception {
        return uploadProcess(storageService, file,
                storageService.generateFileName(file.getOriginalFilename(), StorageService.Target.PENDARATAN),
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
        return "Data Pendaratan tanggal " +
                CustomDateSerializer.toPattern(date) + " <b>Sudah ada di Database</b>!!!";
    }

    public String exists(Date date, String message) {
        return "Data Pendaratan tanggal " +
                CustomDateSerializer.toPattern(date) + " <b>" + message + "</b>!!!";
    }

    /**
     * Proses Upload Data Pendaratan
     *
     * @param storageService
     * @param file
     * @param tempName
     * @throws IOException
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

            translator.getErrors().add("Bukan Format File Pendaratan"); // tambahkan pesan error
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
        Landing landing = objectMapper.readValue(translated.getBytes(), Landing.class);

        if (landingService.isDuplicated(landing)) {
            translator.getErrors().add(
                    exists(landing.getTanggalPendaratan())
            ); // tambahkan pesan error
            return TranslatorResult.builder()
                    .errorsFound(translator.getErrors()).build();
        }

        /** set nilai landing yang diperlukan **/
        if (uploaderUuid != null && uploaderUuid.length() > 0) {
            if (role > 1) {
                SysUser sysUser = sysUserRepository.findOne(uploaderUuid);

                if (Brpl.DEFAULT_ORGANIZATION.toLowerCase().trim().equals(sysUser.getOrganisasi().toLowerCase().trim())
                || sysUser.getOrganisasi().toLowerCase().contains(Brpl.DEFAULT_ORGANIZATION.toLowerCase())) {
                    if (role == 3 || role == 4) // jika yang upload adlah verifikator
                        landing.setStatusDokumen(DocumentStatus.NOT_VERIFIED);
                    else
                        landing.setStatusDokumen(DocumentStatus.DRAFT);
                    if (!landing.getOrganisasi().equals(Brpl.DEFAULT_ORGANIZATION)) {
                        landing.setOrganisasi(Brpl.DEFAULT_ORGANIZATION);
                    }
                } else {
                    landing.setOrganisasi(sysUser.getOrganisasi());
                    if (role == 5) { // user biasa dari ngo
                        landing.setStatusDokumen(DocumentStatus.PENDING);
                    } else if (role == 2) { // validator dari ngo
                        landing.setStatusDokumen(DocumentStatus.WAITING);
                    }
                }

            } else {
                landing.setStatusDokumen(DocumentStatus.NOT_VERIFIED);
                landing.setOrganisasi(Brpl.DEFAULT_ORGANIZATION);
            }



            landing.setUuidPengupload(uploaderUuid);
        }

//        landing.setStatusDokumen(status); // set status dokumen;
        if (landing.getOrganisasi().toUpperCase().equals(Brpl.DEFAULT_ORGANIZATION)) {
            landing.setOrganisasi(landing.getOrganisasi().toUpperCase());
        } // set to uppercase

//        if (uploaderUuid != null || uploaderUuid.length() > 0) {
//            landing.setUuidPengupload(uploaderUuid);
//        }
        /** =========================================== **/

        tempFile.delete(); // hapus file semntara yang diupload dari server

        List<String> errors;
        errors = translator.getErrors();
        errors = analyzingDataRegulations(landing, errors);  // analisa file dengan aturan yang telah ditetapkan

        errors = compabilityRecheck(errors, landing);
        // Cek isi detail dari spesies dan kuantiti
        landing = checkDetailSpeciesAndQuantitesCompabilites(landing);
        if (errors.size() > 0) {
            return TranslatorResult.builder()
                    .errorsFound(errors).errorType(ResponseResolver.DataErrorType.RAW_ERROR).build();
        }

        return TranslatorResult.builder()
                .errorsFound(translator.getErrors())
                .objectResult(landing).build();
    }


    /**
     * Melakukan ckecking pada data detail tangkapan dan menghapus, data spesies kosong dan
     * jumlah tangkapan juga dalam kedaaan kosong
     *
     * @param landing
     * @return s
     */
    private Landing checkDetailSpeciesAndQuantitesCompabilites(Landing landing) {

        for (LandingDetail detail : landing.getDataRincianPendaratan()) {
            List<LandingCatchDetail> catchDetails = new ArrayList<>();
            for (LandingCatchDetail catchDetail : detail.getDataRincianTangkapanPendaratan()) {
                if (!catchDetail.getUuidSpesies().isEmpty() &&
                        catchDetail.getTangkapanVolume() == 0 &&
                        catchDetail.getTangkapanIndividu() == 0) {
                    continue;
                } else {
                    catchDetails.add(catchDetail);
                }
            }
            detail.setDataRincianTangkapanPendaratan(catchDetails);
        }

        return landing;
    }


    /**
     * @param errors
     * @param data
     * @return
     */
    private List<String> compabilityRecheck(List<String> errors, Landing data) {

        /*cek organisasi*/
        if (data.getOrganisasi() == null || data.getOrganisasi().isEmpty())
            errors.add("Pastikan anda menentukan nama organisasi data yang akan diupload");

        if (data.getUuidEnumerator() == null || data.getUuidEnumerator().isEmpty())
            errors.add("Nama Pencatat masih Kosong");


        if (data.getUuidEnumerator() == null || data.getWpp().isEmpty())
            errors.add("Wilayah WPP belum dipilih");

        if (data.getNamaLokasiPendaratan() == null || data.getNamaLokasiPendaratan().isEmpty())
            errors.add("Nama Lokasi Pendaratan masih kosong");

        if (data.getTanggalPendaratan() == null)
            errors.add("Pastikan tanggal pendaratan anda inputkan dengan benar");

        if (data.getUuidSumberDaya() == null || data.getUuidSumberDaya().isEmpty())
            errors.add("Pilihlah salah satu jenis sumberdaya");


        int i = 1;
        for (LandingDetail detail : data.getDataRincianPendaratan()) {


            if (detail.getNamaKapal() == null || detail.getNamaKapal().isEmpty())
                errors.add("Mohon inputkan nama kapal yang benar.\n (Pendaratan No. " + i +
                        " Kapal " + detail.getNamaKapal().toUpperCase() + ").");

            // penampung atau penangkap  || (detail.isPenampung() && detail.isPenangkap())
//            if (!detail.isPenampung() && !detail.isPenangkap()) {
//                errors.add("Pastikan salah satu kolom Penangkap atau Penampung terpilih,  \n (Pendaratan No. " + i +
//                        " Kapal " + detail.getNamaKapal().toUpperCase() + ").");
//            }

            if (detail.getUuidAlatTangkap() == null || detail.getUuidAlatTangkap().isEmpty())
                errors.add("Pastikan jenis alat tangkapnya diketahui.\n (Pendaratan No. " + i +
                        " Kapal " + detail.getNamaKapal().toUpperCase() + ").");

            if (detail.getDaerahPenangkapan() == null || detail.getDaerahPenangkapan().isEmpty())
                errors.add("Silahkan inputkasn Grid Daerah Penangkapan yang benar.\n (Pendaratan No. " + i +
                        " Kapal " + detail.getNamaKapal().toUpperCase() + ").");

            if (detail.getJumlahHariPerTrip() <= 0)
                errors.add("Terdapat kesalahan pada jumlah hari per trip (harus > 0).\n (Pendaratan No. " + i +
                        " Kapal " + detail.getNamaKapal().toUpperCase() + ").");

            if (detail.getJumlahHariMenangkap() < 0)
                errors.add("Terdapat kesalahan pada jumlah hari menangkap tidak boleh negatif.\n (Pendaratan No. " + i +
                        " Kapal " + detail.getNamaKapal().toUpperCase() + ").");

//            if ((detail.getTotalTangkapanVolume() <= 0 && detail.getTotalTangkapanIndividu() <= 0))
//                errors.add("Pastikan anda mengisi salah satu jenis total tangkapan yang tersedia (Volume/ Individu).\n (Pendaratan No. " + i +
//                        " Kapal " + detail.getNamaKapal().toUpperCase() + ").");


//            int j = 1;
//            double volume = 0;
//            double ekor = 0;

            for (LandingCatchDetail catchDetail : detail.getDataRincianTangkapanPendaratan()) {

//                ?commented on juli 4 2018
                if ((catchDetail.getUuidSpesies() == null || catchDetail.getUuidSpesies().isEmpty()) &&
                        (catchDetail.getTangkapanIndividu() > 0 || catchDetail.getTangkapanVolume() > 0))
                    errors.add("Mohon Pastikan nama Spesies ikan tidak kosong.\n (Pendaratan No. " + i +
                            " Kapal " + detail.getNamaKapal().toUpperCase() + ")");
//                volume += catchDetail.getTangkapanVolume();
//                ekor += catchDetail.getTangkapanIndividu();
//
//                j++;
            }

//            if (volume < detail.getTotalTangkapanVolume() || volume > detail.getTotalTangkapanVolume()) {
//                errors.add("Pastikan Total Tangkapan Volume sama dengan jumlah total individu pada Rincian Tangkapan, Pada Kapal " + detail.getNamaKapal());
//            }
//
//
//            if (ekor < detail.getTotalTangkapanIndividu() || ekor > detail.getTotalTangkapanIndividu()) {
//                errors.add("Pastikan Total Tangkapan Individu sama dengan jumlah total invividu pada Rincian Tangkapan, Pada Kapal" + detail.getNamaKapal());
//            }

            i++;
        }

        return errors;
    }


    /**
     * Analisis data yag dihasilkan berdasarkan aturan yang diterapkan pada form ini
     *
     * @param data
     * @return
     */
    @Override
    public List<String> analyzingDataRegulations(Landing data) {
        List<String> errors = new ArrayList<>();
        return analyzingDataRegulations(data, errors);
    }

    @Override
    public List<String> analyzingDataRegulations(Landing data, List<String> errors) {
        errors = catchSpeciesEmptyWhenQuantityNotEmpty(errors, data);
        return errors;
    }


    /**
     * Role untuk melakukan cek bahwa data detail tangkapan spesiesnya kosong dan data tangkapannya kosong juga
     *
     * @param errors
     * @param data
     * @return
     */
    private List<String> catchSpeciesEmptyWhenQuantityNotEmpty(List<String> errors, Landing data) {

        int i = 1;
        for (LandingDetail detail : data.getDataRincianPendaratan()) {
            int j = 0;
            for (LandingCatchDetail catchDetail : detail.getDataRincianTangkapanPendaratan()) {
                j++;
//                logger.info(i + "," + j + " : " + catchDetail.getUuidSpesies() + " || " + catchDetail.getTangkapanVolume());
                if (catchDetail.getUuidSpesies().trim().isEmpty() &&
                        (catchDetail.getTangkapanVolume() > 0 || catchDetail.getTangkapanIndividu() > 0)) {
                    errors.add("Ada Spesies yang Kosong, tetapi mempunyai jumlah tangkapan \n (Pendaratan No. " + i +
                            " Kapal " + detail.getNamaKapal().toUpperCase() + ").");
                }

            }
            i++;
        }


        return errors;
    }
}
