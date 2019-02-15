package tnc.at.brpl.utils.export.excel;

import tnc.at.brpl.configurations.StorageService;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class ExcelFileNameGenerator {

    /**
     * @param target
     * @return
     */
    public static String generateFileName(StorageService.Target target) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return target.toString().toLowerCase() +
                dateFormat.format(date) +
                String.valueOf(Math.random());
    }
}
