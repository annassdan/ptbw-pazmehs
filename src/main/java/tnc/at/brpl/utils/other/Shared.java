package tnc.at.brpl.utils.other;

import tnc.at.brpl.utils.data.DocumentStatus;

import javax.print.Doc;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Shared {

    public static DocumentStatus analyzingDocumentStatus(String status) {
        switch (status) {
            case "DRAFT": return DocumentStatus.DRAFT;
            case "NOT_VERIFIED": return DocumentStatus.NOT_VERIFIED;
            case "VERIFIED": return DocumentStatus.VERIFIED;
            case "PENDING": return DocumentStatus.PENDING;
            default: return null;
        }
    }

    public static Date stringDDMMYYYYToDateYYYYMMDD(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date tempWaktuPendaratan = sdf.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String tempDate = dateFormat.format(tempWaktuPendaratan);


        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat1.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat1.parse(tempDate);
    }


    public static DocumentStatus isNotVerified(int role) {
        switch (role) {
            case 1:
                return DocumentStatus.NOT_VERIFIED;
            case 2:
                return DocumentStatus.DRAFT;
            default:
                return DocumentStatus.DRAFT;
        }
    }

}
