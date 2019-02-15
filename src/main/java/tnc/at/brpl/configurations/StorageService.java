package tnc.at.brpl.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class StorageService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final Path landingDataPath = Paths.get("res/data/pendaratan/");
    private final Path operationalDataPath = Paths.get("res/data/operasional/");
    private final Path biologiOnSizeDataPath = Paths.get("res/data/biologiukuran/");
    private final Path biologiOnReproductionDataPath = Paths.get("res/data/biologireproduksi/");

    private final Path imagePath = Paths.get("res/photos/");

    /**
     *
     */
    public void init() {
        try {
            Files.createDirectory(landingDataPath);
            Files.createDirectory(operationalDataPath);
            Files.createDirectory(biologiOnSizeDataPath);
            Files.createDirectory(biologiOnReproductionDataPath);
        } catch (IOException e) {
            //  throw new RuntimeException("Could not initialize storage!");
        }
    }


    /**
     *
     * @param file
     * @param tempName
     * @return
     * @throws IOException
     */
    public File getMultipartFile(MultipartFile file, String tempName) throws IOException {
        File temp = new File("res/temp/unggah/" + tempName);
        temp.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(temp);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return temp;
    }


    /**
     * @param oriFileName
     * @param target
     * @return
     */
    public String generateFileName(String oriFileName, Target target) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return target.toString().toLowerCase() +
                dateFormat.format(date) +
                String.valueOf(Math.random()) +
                oriFileName.substring(oriFileName.lastIndexOf("."));
    }

    /**
     * @param file
     */
    public void storeDocumentImageFile(MultipartFile file, Target target) {
        try {
            String fName = generateFileName(file.getOriginalFilename(), target);
            Files.copy(file.getInputStream(), imagePath.resolve(fName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param file
     */
    public void storeLandingFile(MultipartFile file) {
        try {
            String fName = generateFileName(file.getOriginalFilename(), Target.PENDARATAN);
            Files.copy(file.getInputStream(), landingDataPath.resolve(fName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param file
     */
    public void storeOperationalFile(MultipartFile file) {
        try {
            String fName = generateFileName(file.getOriginalFilename(), Target.OPERASIONAL);
            Files.copy(file.getInputStream(), operationalDataPath.resolve(fName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param file
     */
    public void storeBiologOnSizeFile(MultipartFile file) {
        try {
            String fName = generateFileName(file.getOriginalFilename(), Target.BIOLOGI_UKURAN);
            Files.copy(file.getInputStream(), biologiOnSizeDataPath.resolve(fName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param file
     */
    public void storeBiologyOnReproductionFile(MultipartFile file) {
        try {
            String fName = generateFileName(file.getOriginalFilename(), Target.BIOLOGI_REPRODUKSI);
            Files.copy(file.getInputStream(), biologiOnReproductionDataPath.resolve(fName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public enum Target {
        PENDARATAN,
        OPERASIONAL,
        BIOLOGI_UKURAN,
        BIOLOGI_REPRODUKSI
    }

}
