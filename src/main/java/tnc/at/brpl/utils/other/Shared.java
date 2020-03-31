package tnc.at.brpl.utils.other;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.WordUtils;
import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.DocumentStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@SuppressWarnings("unused")
public class Shared {



    public static String objectToJsonString(Object object) {
        try {
            return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new ResourceInternalServerErrorException("Got Error");
    }

    public static String toFormatedStringDate(Date source) {
        DateFormat format = new SimpleDateFormat(Brpl.DATE_PATTERN);
        return format.format(source);
    }

    public static <R> R jsonStringToObject(String jsonString, Class<R> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<R> typeReference = new TypeReference<R>(){
            @Override
            public Type getType() {
                return clazz;
            }
        };
        try {
            return (R) objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ResourceInternalServerErrorException("Got Error");
    }

    public static Date toFormatedDate(String source) {
        try {
            return new SimpleDateFormat(Brpl.DATE_PATTERN).parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isStringNullOrEmpty(String obj) {
        return obj == null || verifyString(obj).length() == 0 || verifyString(obj).toLowerCase().equals("null");
    }

    public static boolean isStringEmpty(String obj) {
        return verifyString(obj).length() == 0 || verifyString(obj).toLowerCase().equals("null");
    }

    public static String changeFirstToTitle(String obj) {
        if (isStringNullOrEmpty(obj))
            return "";

        String first = obj.substring(0, 1).toUpperCase();
        return first + obj.substring(1, obj.length());
    }

    public static String verifyString(String obj) {
        return verifyString(obj, false);
    }

    public static String verifyString(String obj, boolean tranformToTitleCase) {
        if (obj == null)
            return "";

        if (tranformToTitleCase) {
            obj = obj.toLowerCase().replaceAll("\\s+", " ").trim();
            final char[] delimiters = { ' ', '-' };
            return WordUtils.capitalizeFully(obj, delimiters);
        } else {
            obj = obj.replaceAll("\\s+", " ").trim();
            return obj;
        }
    }


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
