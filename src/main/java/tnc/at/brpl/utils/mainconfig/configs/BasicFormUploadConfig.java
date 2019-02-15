package tnc.at.brpl.utils.mainconfig.configs;

import org.springframework.web.multipart.MultipartFile;
import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.mainconfig.TranslatorResult;

public abstract class BasicFormUploadConfig {

    @SuppressWarnings("unused")
    public TranslatorResult uploadProcess(StorageService storageService, MultipartFile file, String tempName) throws Exception {
        return uploadProcess(storageService, file, tempName, null, 0, DocumentStatus.DRAFT, 0);
    }

    @SuppressWarnings("unused")
    public TranslatorResult uploadProcess(StorageService storageService, MultipartFile file, String tempName, int sheetIndex) throws Exception {
        return uploadProcess(storageService, file, tempName, null, sheetIndex, DocumentStatus.DRAFT, 0);
    }

    @SuppressWarnings("unused")
    public TranslatorResult uploadProcess(StorageService storageService, MultipartFile file, String tempName, String uploaderUuid) throws Exception {
        return uploadProcess(storageService, file, tempName, uploaderUuid, 0, DocumentStatus.DRAFT, 0);
    }

    public TranslatorResult uploadProcess(StorageService storageService, MultipartFile file, String tempName,
                                          String uploaderUuid, DocumentStatus status, Object o, int role) throws Exception {
        return uploadProcess(storageService, file, tempName, uploaderUuid, 0, status, role);
    }

//    public TranslatorResult uploadProcess(StorageService storageService, MultipartFile file, String tempName, String uploaderUuid, DocumentStatus status, role) throws Exception {
//        return uploadProcess(storageService, file, tempName, uploaderUuid, 0, status, 0);
//    }

    public TranslatorResult uploadProcess(StorageService storageService,
                                          MultipartFile file, String tempName,
                                          String uploaderUuid, DocumentStatus status,
                                          int role) throws Exception {
        return uploadProcess(storageService, file, tempName, uploaderUuid, 0, status, role);
    }


    @SuppressWarnings("unused")
    public abstract TranslatorResult uploadProcess(StorageService storageService,
                                                   MultipartFile file, String tempName,
                                                   String uploaderUuid,
                                                   int sheetIndex,
                                                   DocumentStatus status,
                                                   int role) throws Exception;

    @SuppressWarnings("unused")
    public abstract TranslatorResult uploadProcess(String uploaderUuid,
                                                   StorageService storageService,
                                                   MultipartFile multipartFile,
                                                   DocumentStatus status) throws Exception;
}
