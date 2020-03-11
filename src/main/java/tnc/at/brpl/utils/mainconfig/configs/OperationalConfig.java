package tnc.at.brpl.utils.mainconfig.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.models.main.OperationalCatchDetails;
import tnc.at.brpl.models.main.OperationalOnFishingToolSpecification;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.services.main.OperationalService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.mainconfig.AnalysisFile;
import tnc.at.brpl.utils.mainconfig.Translator;
import tnc.at.brpl.utils.mainconfig.TranslatorResult;
import tnc.at.brpl.utils.mainconfig.models.FieldModel;
import tnc.at.brpl.utils.mainconfig.regulations.FormRegulations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Configuration
public class OperationalConfig extends BasicFormUploadConfig implements FormRegulations<Operational> {

    private final String formIdentifier = "Form Operasional";
    private int formIdentifierRow = 0;
    private int formIdentifierColumn = 1;

    private final String secretFormIdentifier = "O";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    public List<FieldModel> configurationLoaded;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    OperationalService operationalService;

    @Autowired
    SysUserRepository sysUserRepository;

    public boolean isSelfForm(StorageService storageService, MultipartFile multipartFile) throws Exception {

        File file = storageService.getMultipartFile(multipartFile,
                storageService.generateFileName(multipartFile.getOriginalFilename(), StorageService.Target.OPERASIONAL));

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
                storageService.generateFileName(file.getOriginalFilename(), StorageService.Target.OPERASIONAL),
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
        return "Data Operasional tanggal " +
                CustomDateSerializer.toPattern(date) + " <b>Sudah ada di Database</b>!!!";
    }

    public String exists(Date date, String message) {
        return "Data Operasional tanggal " +
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
                                          int sheetIndex, DocumentStatus status, int role) throws Exception {
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
            translator.getErrors().add("Bukan Format File Operasional"); // tambahkan pesan error
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
        Operational operational = objectMapper.readValue(translated, Operational.class);
//        operational.setStatusDokumen(status);
        if (operational.getOrganisasi().toUpperCase().equals(Brpl.DEFAULT_ORGANIZATION)) {
            operational.setOrganisasi(operational.getOrganisasi().toUpperCase());
        } // set to uppercase


        if (operationalService.isDuplicated(operational)) {

            translator.getErrors().add(
                    exists(operational.getTanggalSampling())
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
                        operational.setStatusDokumen(DocumentStatus.NOT_VERIFIED);
                    else
                        operational.setStatusDokumen(DocumentStatus.DRAFT);

                    if (!operational.getOrganisasi().equals(Brpl.DEFAULT_ORGANIZATION)) {
                        operational.setOrganisasi(Brpl.DEFAULT_ORGANIZATION);
                    }
                } else {
                    operational.setOrganisasi(sysUser.getOrganisasi());
                    if (role == 5) { // user biasa dari ngo
                        operational.setStatusDokumen(DocumentStatus.PENDING);
                    } else if (role == 2) { // validator dari ngo
                        operational.setStatusDokumen(DocumentStatus.WAITING);
                    }
                }
            } else {
                operational.setStatusDokumen(DocumentStatus.NOT_VERIFIED);
                operational.setOrganisasi(Brpl.DEFAULT_ORGANIZATION);
            }

            operational.setUuidPengupload(uploaderUuid);
        }

        tempFile.delete(); // hapus file semntara yang diupload dari server

        operational = checkFishingGearSpec(operational); // cek jika ada data spesifikasi yang kosong


        List<String> errors;
        errors = translator.getErrors();
        errors = analyzingDataRegulations(operational, errors);

        errors = compabilityRecheck(errors, operational); // recheck


        if (errors.size() > 0) {
            return TranslatorResult.builder()
                    .errorsFound(errors).errorType(ResponseResolver.DataErrorType.RAW_ERROR).build();
        }


        return TranslatorResult.builder()
                .errorsFound(translator.getErrors())
                .objectResult(operational).build();
    }


    /**
     * @param errors
     * @param data
     * @return
     */
    private List<String> compabilityRecheck(List<String> errors, Operational data) {

        if (data.getOrganisasi() == null || data.getOrganisasi().isEmpty())
            errors.add("Pastikan anda menentukan nama organisasi data yang akan diupload");

        if (data.getUuidEnumerator() == null || data.getUuidEnumerator().isEmpty())
            errors.add("Nama Pencatat masih Kosong");

        if (data.getNamaLokasiPendaratan() == null || data.getNamaLokasiPendaratan().isEmpty())
            errors.add("Nama Lokasi Pendaratan masih kosong");

        if (data.getTanggalSampling() == null)
            errors.add("Pastikan tanggal sampling anda inputkan dengan benar");

        if (data.getUuidEnumerator() == null || data.getWpp().isEmpty())
            errors.add("Wilayah WPP belum dipilih");


        if (data.getJamSampling() == null)
            errors.add("Pastikan jam sampling anda inputkan dengan benar");

        if (data.getUuidSumberDaya() == null || data.getUuidSumberDaya().isEmpty())
            errors.add("Pilihlah salah satu jenis sumberdaya");


        if (data.getNamaKapal() == null || data.getNamaKapal().isEmpty())
            errors.add("Mohon inputkan nama kapal yang benar");

        if (data.getNamaPemilikKapal() == null || data.getNamaPemilikKapal().isEmpty())
            errors.add("Nama Pemilik Kapal masih kosong");

        if (data.getNamaKapten() == null || data.getNamaKapten().isEmpty())
            errors.add("Nama Kapten masih kosong");

        if (data.getTanggalBerangkat() == null)
            errors.add("Tanggal berangkat tidak boleh kosong");

        if (data.getTanggalKembali() == null)
            errors.add("Tanggal kembali tidak boleh kosong");

        if (data.getPelabuhanAsal() == null || data.getPelabuhanAsal().isEmpty())
            errors.add("Pastikan anda menginputkan pelabuhan asal kapal");

        if (data.getJumlahAbk() < 1)
            errors.add("Jumlah ABK tidak ada?");

        if (data.getPanjangKapal() <= 0)
            errors.add("Pastikan anda menginputkan panjang kapal yang benar (panjang > 0 )");
//
//
        if (data.getBobotKapal() <= 0)
            errors.add("Bobot kapal yang anda inputkan tidak dapat diproses");

        if (data.getMesinUtama() <= 0)
            errors.add("Daya Mesin utama tidak dapat diproses");

        if (data.getMaterialKapal() == null || data.getMaterialKapal().isEmpty())
            errors.add("Data Material kapal tidak ada ");

        if (data.getKapasitasPalkaBoks() < 0)
            errors.add("Data kapasitas palka/ boks tidak dapat diproses");
//
//        if (data.isKapalBantu() && data.getUkuranKapalBantu() <= 0)
//            errors.add("Data ukuran kapal bantu tidak dapat diproses. pastikan anda mengisi dengan nilai lebih besar dari  0");

//        if (data.isFreezer() && data.getKapasitasFreezer() <= 0)
//            errors.add("Data kapasitas freezer tidak dapat diproses. pastikan anda mengisi dengan nilai lebih besar dari  0");

//        if (data.isGps() && (data.getJenisGps() == null || data.getJenisGps().isEmpty()))
//            errors.add("Data jenis GPS bantu tidak dapat diproses");

        if (data.getUuidAlatTangkap() == null || data.getUuidAlatTangkap().isEmpty())
            errors.add("Pilihlah salah satu jenis alat tangkap yang ddigunakan");
//
        if (data.getMaterial() == null || data.getMaterial().isEmpty())
            errors.add("Inputkan data material alat tangkap dengan benar");
//
        if (data.getJumlahAlatTangkapYangDioperasikan() <= 0)
            errors.add("Pastikan jumlah alat yang dioperasikan terinput dengan benar");

        if (data.getDaerahPenangkapan() == null || data.getDaerahPenangkapan().isEmpty())
            errors.add("Silahkan inputkasn Grid Daerah Penangkapan yang benar.");

        if (data.getJumlahSetting() <= 0)
            errors.add("Pastikan anda menginputkan jumlah setting yang benar");

//        if (data.getKedalamanAirMulai() < 0)
//            errors.add("Inputkan mulai kedalaman air dengan benar");
//
//        if (data.getKedalamanAirHingga() <= 0)
//            errors.add("Inputkan hingga kedalaman air dengan benar");
//
//        if (data.getKedalamanAirMulai() > data.getKedalamanAirHingga())
//            errors.add("Pastikan nilai kedalaman mulai harus lebih kecil atau sama dengan kedalama air hingga");

        if (data.getJumlahHariPerTrip() <= 0)
            errors.add("Terdapat kesalahan pada jumlah hari per trip (harus > 0).");

        if (data.getJumlahHariMenangkap() < 0)
            errors.add("Terdapat kesalahan pada jumlah hari menangkap tidak boleh negatif.");

        if (data.getWaktuMemancing() == null || data.getWaktuMemancing().isEmpty())
            errors.add("Tentukan waktu pemancingan");

//        if ((data.getJumlahTangkapanVolume() == 0 && data.getJumlahTangkapanIndividu() == 0))
//            errors.add("Pastikan anda mengisi total hasil tangkapan, dalam satuan  yang tersedia (Volume/ Individu).");

//        if ((data.getJumlahTangkapanUntukDimakanDilautVolume() > 0 && data.getJumlahTangkapanUntukDimakanDilautIndividu() > 0))
//            errors.add("Pastikan anda mengisi salah satu jenis jumlah tangkapan untuk dimakan, dalam satuan yang tersedia (Volume/ Individu).");


        if (data.getSumberInformasi() == null || data.getSumberInformasi().isEmpty())
            errors.add("Pastikan sumber informasi data diketahui");

        int i = 1;
        double volume = 0;
        double ekor = 0;

        for (OperationalCatchDetails catchDetails : data.getDataOperasionalDetailTangkapan()) {

            if ((catchDetails.getUuidSpesies() == null || catchDetails.getUuidSpesies().isEmpty()) && (
                    catchDetails.getTotalBeratKg() > 0 || catchDetails.getTotalBeratEkor() > 0 || (catchDetails.getKodeFao() != null && !catchDetails.getKodeFao().isEmpty())
            ))
                errors.add("Pastikan anda menginputkan nama spesies ikan dengan benar.\n " +
                        "Pada detail tangkapan No. " + i + ").");

            if ((catchDetails.getUuidSpesies() != null && !catchDetails.getUuidSpesies().isEmpty()) &&
                    ((catchDetails.getTotalBeratEkor() == 0 && catchDetails.getTotalBeratKg() == 0))
                    )
                errors.add("Pastikan anda mengisi salah satu hasil tangkapan per spesies yang tersedia (Volume/ Individu).\n " +
                        "Pada detail tangkapan No." + i + ").");

            volume += catchDetails.getTotalBeratKg();
            ekor += catchDetails.getTotalBeratEkor();


            i++;


        }

//
//        if (volume < data.getJumlahTangkapanVolume() || volume > data.getJumlahTangkapanVolume()) {
//            errors.add("Pastikan Total Tangkapan Volume sama dengan jumlah total individu pada Rincian Tangkapan");
//        }
//
//
//        if (ekor < data.getJumlahTangkapanIndividu() || ekor > data.getJumlahTangkapanIndividu()) {
//            errors.add("Pastikan Total Tangkapan Individu sama dengan jumlah total invividu pada Rincian Tangkapan");
//        }


        return errors;
    }


    /**
     * Melakukan pengecekan terhadap aturan yang diterapkan pada setiap form operasional
     *
     * @param data
     * @return
     */
    @Override
    public List<String> analyzingDataRegulations(Operational data) {
        List<String> errors = new ArrayList<>();
        return analyzingDataRegulations(data, errors);
    }

    @Override
    public List<String> analyzingDataRegulations(Operational data, List<String> errors) {

        errors = checkCatchDetails(errors, data);
        return errors;
    }


    /**
     * Melakukan pengecekan terhadap data rincian hasil tangkapan
     *
     * @param errors
     * @param data
     * @return
     */
    private List<String> checkCatchDetails(List<String> errors, Operational data) {

        int i = 1;
        for (OperationalCatchDetails catchDetails : data.getDataOperasionalDetailTangkapan()) {

            if (catchDetails.getKodeFao().trim().length() > 0 ||
                    catchDetails.getTotalBeratEkor() > 0 || catchDetails.getTotalBeratKg() > 0 ||
                    catchDetails.isAsin() || catchDetails.isBeku() || catchDetails.isSegar()) {
                if (catchDetails.getUuidSpesies().trim().isEmpty()) {
                    errors.add("Nama Spesies kosong pada detail hasil tangkapan No " + 1);
                }
            }
            i++;
        }

        return errors;
    }

    private Operational checkFishingGearSpec(Operational operational) {
        List<OperationalOnFishingToolSpecification> tempSpec = new ArrayList<>();

        for (OperationalOnFishingToolSpecification specification : operational.getDataSpesifikasiAlatTangkap()) {
            if (specification.getSpesifikasi().trim().isEmpty() &&
                    specification.getNilaiSpesifikasi().isEmpty())
                continue;
            else
                tempSpec.add(specification);
        }

        operational.setDataSpesifikasiAlatTangkap(tempSpec);
        return operational;
    }
}
