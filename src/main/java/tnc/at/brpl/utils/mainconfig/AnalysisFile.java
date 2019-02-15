package tnc.at.brpl.utils.mainconfig;
//
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public class AnalysisFile {

    @SuppressWarnings("unused")
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    public XSSFSheet analisysExcelFile(File file) throws Exception {
        return analisysExcelFile(file, 0);
    }


    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    public XSSFSheet analisysExcelFile(File file, int sheetIndex) throws Exception {

        try {
            return getStandardExcelFile(file);
        } catch (Exception e) {
            try {
                return getEncryptedExcelFile(file);
            } catch (IOException e1) {
                e1.printStackTrace();
                throw new IOException(e1);
            } catch (GeneralSecurityException e2) {
                throw new GeneralSecurityException(e2);
            }
        }
    }


    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    private XSSFSheet getStandardExcelFile(File file) throws IOException {
        return getStandardExcelFile(file, 0);
    }


    private XSSFSheet getStandardExcelFile(File file, int sheetIndex) throws IOException {
        FileInputStream excelFileStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(excelFileStream);
        return workbook.getSheetAt(sheetIndex);
    }


    /**
     *
     * @param file
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private XSSFSheet getEncryptedExcelFile(File file) throws IOException, GeneralSecurityException {
        return  getEncryptedExcelFile(file, 0);
    }


    /**
     *
     * @param file
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private XSSFSheet getEncryptedExcelFile(File file, int sheeIndex) throws IOException, GeneralSecurityException {
        FileInputStream excelFileStream = new FileInputStream(file);
        POIFSFileSystem system = new POIFSFileSystem(excelFileStream);
        EncryptionInfo encryptionInfo = new EncryptionInfo(system);
        Decryptor decryptor = Decryptor.getInstance(encryptionInfo);
        XSSFWorkbook workbook = new XSSFWorkbook(decryptor.getDataStream(system));
        return workbook.getSheetAt(sheeIndex);
    }



    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public HSSFSheet analisysExcelFileHSS(File file) throws Exception {

        try {
            return getStandardExcelFileHSS(file);
        } catch (Exception e) {
            try {
                return getEncryptedExcelFileHSS(file);
            } catch (IOException e1) {
                e1.printStackTrace();
                logger.info("1");
                logger.info(e1.getMessage());
                throw new IOException(e1);
//                return null;
            } catch (GeneralSecurityException e2) {
                logger.info("2");
                logger.info(e2.getMessage());
                throw new GeneralSecurityException(e2);
//                return null;
            }
        }
    }


    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    private HSSFSheet getStandardExcelFileHSS(File file) throws IOException {
        FileInputStream excelFileStream = new FileInputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook(excelFileStream);
        return workbook.getSheetAt(0);
    }


    /**
     *
     * @param file
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private HSSFSheet getEncryptedExcelFileHSS(File file) throws IOException, GeneralSecurityException {
        FileInputStream excelFileStream = new FileInputStream(file);
        POIFSFileSystem system = new POIFSFileSystem(excelFileStream);
        EncryptionInfo encryptionInfo = new EncryptionInfo(system);
        Decryptor decryptor = Decryptor.getInstance(encryptionInfo);
        HSSFWorkbook workbook = new HSSFWorkbook(decryptor.getDataStream(system));
        return workbook.getSheetAt(0);
    }


}
