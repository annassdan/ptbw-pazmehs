package tnc.at.brpl.apis.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.services.AsyncService;
import tnc.at.brpl.services.main.BiologyOnReproductionService;
import tnc.at.brpl.services.main.BiologyOnSizeService;
import tnc.at.brpl.services.main.LandingService;
import tnc.at.brpl.services.main.OperationalService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.Form;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.api.response.ResponseType;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.mainconfig.TokenIdentification;
import tnc.at.brpl.utils.mainconfig.configs.BiologyOnReproductionConfig;
import tnc.at.brpl.utils.mainconfig.configs.BiologyOnSizeConfig;
import tnc.at.brpl.utils.mainconfig.configs.LandingConfig;
import tnc.at.brpl.utils.mainconfig.TranslatorResult;
import tnc.at.brpl.utils.mainconfig.configs.OperationalConfig;
import tnc.at.brpl.utils.other.LandingAllOtherData;
import tnc.at.brpl.utils.other.Shared;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@CrossOrigin(origins = {
        Brpl.CORS.ORIGIN_1,
        Brpl.CORS.ORIGIN_2,
        Brpl.CORS.ORIGIN_3,
        Brpl.CORS.ORIGIN_4,
        Brpl.CORS.ORIGIN_5,
        Brpl.CORS.ORIGIN_6,
        Brpl.CORS.ORIGIN_7,
        Brpl.CORS.ORIGIN_8,
        Brpl.CORS.ORIGIN_9,
        Brpl.CORS.ORIGIN_10
},
        maxAge = Brpl.CORS.MAX_AGE_1,
        allowCredentials = Brpl.CORS.ALLOW_CREDENTIALS)
@RestController
@SuppressWarnings("unused")
public class
ResourceDataApi {

    @Autowired
    AsyncService asyncService;

    @Autowired
    LandingService landingService;

    @Autowired
    OperationalService operationalService;

    @Autowired
    BiologyOnSizeService biologyOnSizeService;

    @Autowired
    BiologyOnReproductionService biologyOnReproductionService;

    @Autowired
    StorageService storageService;

    @Autowired
    LandingConfig landingConfig;

    @Autowired
    OperationalConfig operationalConfig;

    @Autowired
    BiologyOnSizeConfig biologyOnSizeConfig;

    @Autowired
    BiologyOnReproductionConfig biologyOnReproductionConfig;


    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     *
     */
    Map<String, List<?>> dataTempLists = new HashMap<>();

    List<LandingAllOtherData> landingAllOtherData = new ArrayList<>();


    @PostMapping("/data/pendaratan/unggah/foto/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> uploadLandingImage(@RequestParam("file") MultipartFile file,
                                                                  @PathVariable("tokenupload") String tokenUpload) {
        try {
            storageService.storeDocumentImageFile(file, StorageService.Target.PENDARATAN);
            return ResponseResolver.builder()
                    .type(ResponseType.WRITE)
                    .httpStatus(HttpStatus.OK)
                    .body("File Berhasil diproses")
                    .build().map();
        } catch (Exception e) {
            return ResponseResolver.builder()
                    .type(ResponseType.WRITE)
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error(e.getMessage()).errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();

        }
    }

    @PostMapping("/data/operasional/unggah/foto/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> uploadOperasionalImage(@RequestParam("file") MultipartFile file,
                                                                      @PathVariable("tokenupload") String tokenUpload) {
        try {
            storageService.storeDocumentImageFile(file, StorageService.Target.OPERASIONAL);
            return ResponseResolver.builder()
                    .type(ResponseType.WRITE)
                    .httpStatus(HttpStatus.OK)
                    .body("File Berhasil diproses")
                    .build().map();
        } catch (Exception e) {
            return ResponseResolver.builder()
                    .type(ResponseType.WRITE)
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error(e.getMessage()).errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();

        }
    }

    @PostMapping("/data/biologiukuran/unggah/foto/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> uploadBiologiOnSizeImage(@RequestParam("file") MultipartFile file,
                                                                        @PathVariable("tokenupload") String tokenUpload) {
        try {
            storageService.storeDocumentImageFile(file, StorageService.Target.BIOLOGI_UKURAN);
            return ResponseResolver.builder()
                    .type(ResponseType.WRITE)
                    .httpStatus(HttpStatus.OK)
                    .body("File Berhasil diproses")
                    .build().map();
        } catch (Exception e) {
            return ResponseResolver.builder()
                    .type(ResponseType.WRITE)
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error(e.getMessage()).errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();

        }
    }

    @PostMapping("/data/biologireproduksi/unggah/foto/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> uploadBiologyOnReproductionImage(@RequestParam("file") MultipartFile file,
                                                                                @PathVariable("tokenupload") String tokenUpload) {
        try {
            storageService.storeDocumentImageFile(file, StorageService.Target.BIOLOGI_REPRODUKSI);
            return ResponseResolver.builder()
                    .type(ResponseType.WRITE)
                    .httpStatus(HttpStatus.OK)
                    .body("File Berhasil diproses")
                    .build().map();
        } catch (Exception e) {
            return ResponseResolver.builder()
                    .type(ResponseType.WRITE)
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error(e.getMessage()).errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();

        }
    }


    private boolean dataInMemoryExists(String token) {
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);
        return tokenIdentification.isTokenExists(token);
    }


    /**
     * Menyimpan sementara data yang dupload pada memory server
     *
     * @param tokenUpload
     * @param result
     */
    private void saveDataToMemory(String tokenUpload, TranslatorResult result) {
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);
        if (!dataInMemoryExists(tokenUpload)) {
            List results = new ArrayList<>();
            results.add(result.getObjectResult());
            dataTempLists.put(tokenUpload, results);
            asyncService.asyncRemoveToken(tokenUpload, dataTempLists); // create async thread untuk menghapus token yang expired
        } else {
            List results = dataTempLists.get(tokenUpload);
            results.add(result.getObjectResult());
            dataTempLists.replace(tokenUpload, results);
        }
    }



    //    ALL FORM
    @PostMapping("/data/allform/unggah/post/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> postUploadAllForm(@PathVariable("tokenupload") String tokenUpload) {
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);
        if (tokenIdentification.isTokenExists(tokenUpload)) {
            landingService.saves((List<Landing>) dataTempLists.get(tokenUpload));
            try {
                dataTempLists.remove(tokenUpload);
            } catch (Exception e) {
            }
        } else {
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error("Mohon Reset dan upload ulang file anda.")
                    .errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }

        return ResponseResolver.builder()
                .httpStatus(HttpStatus.OK)
                .body("Berhasil")
                .build().map();
    }

    @PostMapping("/data/allform/unggah/preview/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> uploadAllForm(@RequestParam("file") MultipartFile file,
                                                             @PathVariable("tokenupload") String tokenUpload) {
        return allFormProcess(file, tokenUpload);
    }

    @PostMapping("/data/allform/unggah/preview/{tokenupload}/{uploaderuuid}/{userrole}")
    public ResponseEntity<ResponseResolver<?>> uploadAllForm(@RequestParam("file") MultipartFile file,
                                                             @PathVariable("tokenupload") String tokenUpload,
                                                             @PathVariable("uploaderuuid") String uploaderUuid,
                                                             @PathVariable("userrole") int role) {
        return allFormProcess(file, tokenUpload, uploaderUuid, Shared.isNotVerified(role), role);
    }

    private ResponseEntity<ResponseResolver<?>> allFormProcess(MultipartFile file, String stoken) {
        return allFormProcess(file, stoken, null, DocumentStatus.DRAFT, 0);
    }

    private ResponseEntity<ResponseResolver<?>> allFormProcess(MultipartFile file, String stoken, String uploaderUuid,
                                                               DocumentStatus status, int role) {
        String message;
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);
        try {
            TranslatorResult result = null;
            String extraParamResponse;

//            UUID.randomUUID().toString()

//            Menganalisa file apa yang diupload
            if (landingConfig.isSelfForm(storageService, file)) {
//                TranslatorResult result = landingConfig.uploadProcess(storageService, file, UUID.randomUUID().toString(), uploaderUuid, status, role);
                TranslatorResult<Landing> tempResult = landingConfig.uploadProcess(storageService, file, UUID.randomUUID().toString(), uploaderUuid, status, role);
                result = tempResult;
                extraParamResponse = Form.PENDARATAN.name();

                // cek data apakah ada di memori atau tidak
//                Landing object = tempResult.getObjectResult();
//                if (tokenIdentification.isDataExistOnMemory(stoken, object)) {
//                    tempResult.setErrorsFound(
//                            new ArrayList<>(Arrays.asList(landingConfig.exists(object.getTanggalPendaratan(),
//                                    "Sudah ada pada list data upload")))
//                    );
//                }

            } else if (operationalConfig.isSelfForm(storageService, file)) {
                TranslatorResult<Operational> tempResult = operationalConfig.uploadProcess(storageService, file, UUID.randomUUID().toString(), uploaderUuid, status, role);
                result = tempResult;
                extraParamResponse = Form.OPERASIONAL.name();

                // cek data apakah ada di memori atau tidak
//                if (dataInMemoryExists(stoken)) {
//                    Operational object = tempResult.getObjectResult();
//                    if (tokenIdentification.isDataExistOnMemory(stoken, object, true)) {
//                        tempResult.setErrorsFound(
//                                new ArrayList<>(Arrays.asList(operationalConfig.exists(object.getTanggalSampling(),
//                                        "Sudah ada pada list data upload")))
//                        );
//                    }
//                }

            } else if (biologyOnSizeConfig.isSelfForm(storageService, file)) {
                TranslatorResult<BiologyOnSize> tempResult = biologyOnSizeConfig.uploadProcess(storageService, file, UUID.randomUUID().toString(), uploaderUuid, status, role);
                result = tempResult;
                extraParamResponse = Form.BIOLOGI_UKURAN.name();

                // cek data apakah ada di memori atau tidak
//                if (dataInMemoryExists(stoken)) {
//                    BiologyOnSize object = tempResult.getObjectResult();
//                    if (tokenIdentification.isDataExistOnMemory(stoken, object, true)) {
//                        tempResult.setErrorsFound(
//                                new ArrayList<>(Arrays.asList(biologyOnSizeConfig.exists(object.getTanggalSampling(),
//                                        "Sudah ada pada list data upload")))
//                        );
//                    }
//                }
            } else if (biologyOnReproductionConfig.isSelfForm(storageService, file)) {
                TranslatorResult<BiologyOnReproduction> tempResult = biologyOnReproductionConfig.uploadProcess(storageService, file, UUID.randomUUID().toString(), uploaderUuid, status, role);
                result = tempResult;
                extraParamResponse = Form.BIOLOGI_REPRODUKSI.name();

                // cek data apakah ada di memori atau tidak
//                if (dataInMemoryExists(stoken)) {
//                    BiologyOnReproduction object = tempResult.getObjectResult();
//                    if (tokenIdentification.isDataExistOnMemory(stoken, object, true)) {
//                        tempResult.setErrorsFound(
//                                new ArrayList<>(Arrays.asList(biologyOnReproductionConfig.exists(object.getTanggalSampling(),
//                                        "Sudah ada pada list data upload")))
//                        );
//                    }
//                }
            } else {
                return ResponseResolver.builder()
                        .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                        .error(Arrays.asList("File ini bukanlah salah satu dari jenis Form yang telah ditentukan."))
                        .errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                        .build().map();
            }
            // ====

            if (result == null || result.getErrorsFound().size() > 0) {
                return ResponseResolver.builder()
                        .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                        .errorType(result.getErrorType())
                        .error(result.getErrorsFound())
                        .build().map();
            } else {

                if (result.getObjectResult() instanceof Landing) {
                    if (dataInMemoryExists(stoken)) {
                        return ResponseResolver.builder()
                                .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                                .error(Arrays.asList("Hanya boleh menginputkan 1 data Pendaratan"))
                                .errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                                .build().map();
                    } else {
                        saveDataToMemory(stoken, result); // simpan data ke memory

//                        List<Landing> landings = (List<Landing>) dataTempLists.get(stoken);
//
//                        // check all data child which not included to landing data
//                        if (!landingAllOtherData.isEmpty()) {
//
//                            if (!landings.isEmpty()) {
//                                landings.forEach(landing -> {
//                                    landingAllOtherData.stream()
//                                            .filter(s -> s.getToken().equals(stoken))
//                                            .collect(Collectors.toList())
//                                            .forEach(s -> {
//                                                if (s.getForm() instanceof Operational) {
//                                                    if (landing.getDataOperasional() == null)
//                                                        landing.setDataOperasional(new ArrayList<>());
//                                                    landing.getDataOperasional().add((Operational) s.getForm());
//                                                } else if (s.getForm() instanceof BiologyOnSize) {
//                                                    if (landing.getDataUkuran() == null)
//                                                        landing.setDataUkuran(new ArrayList<>());
//                                                    landing.getDataUkuran().add((BiologyOnSize) s.getForm());
//                                                } else {
//                                                    if (landing.getDataReproduksi() == null)
//                                                        landing.setDataReproduksi(new ArrayList<>());
//                                                    landing.getDataReproduksi().add((BiologyOnReproduction) s.getForm());
//                                                }
//                                            });
//                                });
//                            }
//
//                            landingAllOtherData.removeIf(s -> s.getToken().equals(stoken));
//                        }
                        //
                    }
                } else {
                    Thread.sleep(100); // delay the process

                    if (dataInMemoryExists(stoken)) {
                        List<Landing> landings = (List<Landing>) dataTempLists.get(stoken);

                        final TranslatorResult translatorResult = result;
                        if (!landings.isEmpty()) {
                            landings.forEach(landing -> {
                                if (translatorResult.getObjectResult() instanceof Operational) {
                                    if (landing.getDataOperasional() == null)
                                        landing.setDataOperasional(new ArrayList<>());
                                    landing.getDataOperasional().add((Operational) translatorResult.getObjectResult());
                                } else if (translatorResult.getObjectResult() instanceof BiologyOnSize) {
                                    if (landing.getDataUkuran() == null)
                                        landing.setDataUkuran(new ArrayList<>());
                                    landing.getDataUkuran().add((BiologyOnSize) translatorResult.getObjectResult());
                                } else {
                                    if (landing.getDataReproduksi() == null)
                                        landing.setDataReproduksi(new ArrayList<>());
                                    landing.getDataReproduksi().add((BiologyOnReproduction) translatorResult.getObjectResult());
                                }
                            });
                        }
                    } else {
                        // Silahkan inputkan file pendaratan terlebih dahulu
                        return ResponseResolver.builder()
                                .httpStatus(HttpStatus.EXPECTATION_FAILED)
                                .error(Arrays.asList("Silahkan Inputkan File Pendaratan terlebih dahulu"))
                                .errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                                .build().map();

//                        landingAllOtherData.add(
//                                LandingAllOtherData.builder()
//                                        .token(stoken)
//                                        .form(result.getObjectResult())
//                                        .build()
//                        );
                    }
                }

            }


            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.OK)
                    .body(result.getObjectResult())
                    .extraParameterResponse(extraParamResponse)
                    .build().map();
        } catch (Exception e) {
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error(Arrays.asList(e.getMessage())).errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }

    }


//    ========================


    //    PENDARATAN
    @PostMapping("/data/pendaratan/unggah/post/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> postUploadLanding(@PathVariable("tokenupload") String tokenUpload) {
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);
        if (tokenIdentification.isTokenExists(tokenUpload)) {
            landingService.saves((List<Landing>) dataTempLists.get(tokenUpload));
            try {
                dataTempLists.remove(tokenUpload);
            } catch (Exception e) {
            }
        } else {
            logger.error("Data tidak ada");
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error("Mohon Reset dan upload ulang file anda.")
                    .errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }

        return ResponseResolver.builder()
                .httpStatus(HttpStatus.OK)
                .body("Berhasil")
                .build().map();
    }

    @PostMapping("/data/pendaratan/unggah/preview/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> uploadLanding(@RequestParam("file") MultipartFile file,
                                                             @PathVariable("tokenupload") String tokenUpload) {
        return landingProcess(file, tokenUpload);
    }

    @PostMapping("/data/pendaratan/unggah/preview/{tokenupload}/{uploaderuuid}/{userrole}")
    public ResponseEntity<ResponseResolver<?>> uploadLanding(@RequestParam("file") MultipartFile file,
                                                             @PathVariable("tokenupload") String tokenUpload,
                                                             @PathVariable("uploaderuuid") String uploaderUuid,
                                                             @PathVariable("userrole") int role) {


        return landingProcess(file, tokenUpload, uploaderUuid, Shared.isNotVerified(role), role);
    }

    private ResponseEntity<ResponseResolver<?>> landingProcess(MultipartFile file, String stoken) {
        return landingProcess(file, stoken, null, DocumentStatus.DRAFT, 0);
    }

    private ResponseEntity<ResponseResolver<?>> landingProcess(MultipartFile file,
                                                               String stoken,
                                                               String uploaderUuid,
                                                               DocumentStatus status, int role) {
        String message;
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);
        try {

            String tempName = storageService.generateFileName(file.getOriginalFilename(), StorageService.Target.PENDARATAN);
            TranslatorResult result = landingConfig.uploadProcess(storageService, file, tempName, uploaderUuid, status, role);

            Landing object = (Landing) result.getObjectResult();
            if (tokenIdentification.isDataExistOnMemory(stoken, object)) {
                result.setErrorsFound(
                        new ArrayList<>(Arrays.asList(landingConfig.exists(object.getTanggalPendaratan(),
                                "Sudah ada pada list data yang akan diupload")))
                );
            }


            if (result.getErrorsFound().size() > 0) {
                return ResponseResolver.builder()
                        .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                        .errorType(result.getErrorType())
                        .error(result.getErrorsFound())
                        .build().map();
            }

            saveDataToMemory(stoken, result); // simpan data ke memory

            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.OK)
                    .body(result.getObjectResult())
                    .build().map();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error(Arrays.asList(e.getMessage())).errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }

    }


    //    OPERASIONAL
    @PostMapping("/data/operasional/unggah/post/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> postUploadOperational(@PathVariable("tokenupload") String tokenUpload) {
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);
        if (tokenIdentification.isTokenExists(tokenUpload)) {
            operationalService.saves((List<Operational>) dataTempLists.get(tokenUpload));
            try {
                dataTempLists.remove(tokenUpload);
            } catch (Exception e) {
            }
        } else {
            logger.error("Data tidak ada");
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error("Mohon Reset dan upload ulang file anda.")
                    .errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }

        return ResponseResolver.builder()
                .httpStatus(HttpStatus.OK)
                .body("Berhasil")
                .build().map();
    }

    @PostMapping("/data/operasional/unggah/preview/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> uploadOperational(@RequestParam("file") MultipartFile file,
                                                                 @PathVariable("tokenupload") String tokenUpload) {
        return operationalProcess(file, tokenUpload);
    }

    @PostMapping("/data/operasional/unggah/preview/{tokenupload}/{uploaderuuid}/{userrole}")
    public ResponseEntity<ResponseResolver<?>> uploadOperational(@RequestParam("file") MultipartFile file,
                                                                 @PathVariable("tokenupload") String tokenUpload,
                                                                 @PathVariable("uploaderuuid") String uploaderUuid,
                                                                 @PathVariable("userrole") int role) {
        return operationalProcess(file, tokenUpload, uploaderUuid, Shared.isNotVerified(role), role);
    }

    private ResponseEntity<ResponseResolver<?>> operationalProcess(MultipartFile file, String stoken) {
        return operationalProcess(file, stoken, null, DocumentStatus.DRAFT, 0);
    }

    private ResponseEntity<ResponseResolver<?>> operationalProcess(MultipartFile file,
                                                                   String stoken, String uploaderUuid, DocumentStatus status,
                                                                   int role
    ) {
        String message;
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);

        try {
            String tempName = storageService.generateFileName(file.getOriginalFilename(), StorageService.Target.OPERASIONAL);
            TranslatorResult result = operationalConfig.uploadProcess(storageService, file, tempName, uploaderUuid, status, role);

            Operational object = (Operational) result.getObjectResult();
            if (tokenIdentification.isDataExistOnMemory(stoken, object)) {
                result.setErrorsFound(
                        new ArrayList<>(Arrays.asList(operationalConfig.exists(object.getTanggalSampling(),
                                "Sudah ada pada list data yang akan diupload")))
                );
            }

            if (result.getErrorsFound().size() > 0) {
                return ResponseResolver.builder()
                        .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                        .errorType(result.getErrorType())
                        .error(result.getErrorsFound())
                        .build().map();
            }

            saveDataToMemory(stoken, result); // simpan data ke memory

            // files.add(file.getOriginalFilename());
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.OK)
                    .body(result.getObjectResult())
                    .build().map();
        } catch (Exception e) {
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error(Arrays.asList(e.getMessage())).errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }
    }


    //    BIOLOGI UKURAN
    @PostMapping("/data/biologiukuran/unggah/post/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> postUploadBiologyOnSize(@PathVariable("tokenupload") String tokenUpload) {

        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);
        if (tokenIdentification.isTokenExists(tokenUpload)) {
            biologyOnSizeService.saves((List<BiologyOnSize>) dataTempLists.get(tokenUpload));
            try {
                dataTempLists.remove(tokenUpload);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        } else {
            logger.error("Data tidak ada");
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error("Mohon Reset dan upload ulang file anda.")
                    .errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }

        return ResponseResolver.builder()
                .httpStatus(HttpStatus.OK)
                .body("Berhasil")
                .build().map();
    }

    @PostMapping("/data/biologiukuran/unggah/preview/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> uploadBiologyOnSize(@RequestParam("file") MultipartFile file,
                                                                   @PathVariable("tokenupload") String tokenUpload) {
        return biologyOnSizeProcess(file, tokenUpload);
    }

    @PostMapping("/data/biologiukuran/unggah/preview/{tokenupload}/{uploaderuuid}/{userrole}")
    public ResponseEntity<ResponseResolver<?>> uploadBiologyOnSize(@RequestParam("file") MultipartFile file,
                                                                   @PathVariable("tokenupload") String tokenUpload,
                                                                   @PathVariable("uploaderuuid") String uploaderUuid,
                                                                   @PathVariable("userrole") int role) {
        return biologyOnSizeProcess(file, tokenUpload, uploaderUuid, Shared.isNotVerified(role), role);
    }

    private ResponseEntity<ResponseResolver<?>> biologyOnSizeProcess(MultipartFile file, String stoken) {
        return biologyOnSizeProcess(file, stoken, null, DocumentStatus.DRAFT, 0);
    }

    private ResponseEntity<ResponseResolver<?>> biologyOnSizeProcess(MultipartFile file, String stoken, String uploaderUuid,
                                                                     DocumentStatus status,
                                                                     int role) {
        String message;
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);

        try {
            String tempName = storageService.generateFileName(file.getOriginalFilename(), StorageService.Target.BIOLOGI_UKURAN);
            TranslatorResult result = biologyOnSizeConfig.uploadProcess(storageService, file, tempName, uploaderUuid, status, role);

            BiologyOnSize object = (BiologyOnSize) result.getObjectResult();
            if (tokenIdentification.isDataExistOnMemory(stoken, object)) {
                result.setErrorsFound(
                        new ArrayList<>(Arrays.asList(biologyOnSizeConfig.exists(object.getTanggalSampling(),
                                "Sudah ada pada list data yang akan diupload")))
                );
            }

            if (result.getErrorsFound().size() > 0) {
                return ResponseResolver.builder()
                        .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                        .errorType(result.getErrorType())
                        .error(result.getErrorsFound())
                        .build().map();
            }

            saveDataToMemory(stoken, result); // simpan data ke memory

            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.OK)
                    .body(result.getObjectResult())
                    .build().map();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error(Arrays.asList(e.getMessage())).errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }
    }


    //    BIOLOGI REPRODUKSI
    @PostMapping("/data/biologireproduksi/unggah/post/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> postUploadBiologyOnReproduction(@PathVariable("tokenupload") String tokenUpload) {

        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);
        if (tokenIdentification.isTokenExists(tokenUpload)) {
            biologyOnReproductionService.saves((List<BiologyOnReproduction>) dataTempLists.get(tokenUpload));
            try {
                dataTempLists.remove(tokenUpload);
            } catch (Exception e) {
            }
        } else {
            logger.error("Data tidak ada");
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error("Mohon Reset dan upload ulang file anda.")
                    .errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }

        return ResponseResolver.builder()
                .httpStatus(HttpStatus.OK)
                .body("Berhasil")
                .build().map();
    }

    @PostMapping("/data/biologireproduksi/unggah/preview/{tokenupload}")
    public ResponseEntity<ResponseResolver<?>> uploadBiologyOnReproduction(@RequestParam("file") MultipartFile file,
                                                                           @PathVariable("tokenupload") String tokenUpload) {
        return biologyOnReproductionProcess(file, tokenUpload);
    }

    @PostMapping("/data/biologireproduksi/unggah/preview/{tokenupload}/{uploaderuuid}/{userrole}")
    public ResponseEntity<ResponseResolver<?>> uploadBiologyOnReproduction(@RequestParam("file") MultipartFile file,
                                                                           @PathVariable("tokenupload") String tokenUpload,
                                                                           @PathVariable("uploaderuuid") String uploaderUuid,
                                                                           @PathVariable("userrole") int role) {
        return biologyOnReproductionProcess(file, tokenUpload, uploaderUuid, Shared.isNotVerified(role), role);
    }

    private ResponseEntity<ResponseResolver<?>> biologyOnReproductionProcess(MultipartFile file, String stoken) {
        return biologyOnReproductionProcess(file, stoken, null, DocumentStatus.DRAFT, 0);
    }

    private ResponseEntity<ResponseResolver<?>> biologyOnReproductionProcess(MultipartFile file, String stoken, String uploaderUuid,
                                                                             DocumentStatus status, int role) {
        String message;
        TokenIdentification tokenIdentification = new TokenIdentification(dataTempLists);

        try {
            String tempName = storageService.generateFileName(file.getOriginalFilename(), StorageService.Target.BIOLOGI_REPRODUKSI);
            TranslatorResult result = biologyOnReproductionConfig.uploadProcess(storageService, file, tempName, uploaderUuid, status, role);

            BiologyOnReproduction object = (BiologyOnReproduction) result.getObjectResult();
            if (tokenIdentification.isDataExistOnMemory(stoken, object)) {
                result.setErrorsFound(
                        new ArrayList<>(Arrays.asList(biologyOnReproductionConfig.exists(object.getTanggalSampling(),
                                "Sudah ada pada list data yang akan diupload")))
                );
            }

            if (result.getErrorsFound().size() > 0) {
                return ResponseResolver.builder()
                        .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                        .errorType(result.getErrorType())
                        .error(result.getErrorsFound())
                        .build().map();
            }
            saveDataToMemory(stoken, result); // simpan data ke memory

            // files.add(file.getOriginalFilename());
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.OK)
                    .body(result.getObjectResult())
                    .build().map();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseResolver.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .error(Arrays.asList(e.getMessage())).errorType(ResponseResolver.DataErrorType.DEFAULT_ERROR)
                    .build().map();
        }
    }


}

