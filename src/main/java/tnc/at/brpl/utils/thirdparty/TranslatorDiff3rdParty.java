package tnc.at.brpl.utils.thirdparty;


import org.apache.commons.lang3.ArrayUtils;
import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.HistoryDiffType;
import tnc.at.brpl.utils.other.Shared;
import tnc.at.brpl.utils.thirdparty.diff.CompareDiff;
import tnc.at.brpl.utils.thirdparty.diff.Diff;
import tnc.at.brpl.utils.thirdparty.diff.DiffProp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TranslatorDiff3rdParty implements Brpl {

    private static boolean identicalString(String a, String b) {
        return Shared.verifyString(a).equals(Shared.verifyString(b));
    }

    private static boolean identicalBoolean(Boolean a, Boolean b) {
        return a.equals(b);
    }

    private static boolean identicalNumber(Integer a, Integer b) {
        return a.equals(b);
    }

    private static boolean identicalNumber(Double a, Double b) {
        return a.equals(b);
    }

    private static boolean identicalDate(Date a, Date b) {
        String dateA = Shared.toFormatedStringDate(a);
        String dateB = Shared.toFormatedStringDate(b);
        return dateA.equals(dateB);
    }

    private static <T> void addToDiff(List<DiffProp> diffProps, String prop, T oldValue, T newValue) {
        diffProps.add(DiffProp.builder().onProp(prop).oldValue(oldValue).newValue(newValue).build());
    }

    private static <T> void compareAndAddIt(List<DiffProp> diffProps, String prop, T oldValue, T newValue) {
        if (oldValue instanceof String) {
          if (!identicalString((String) oldValue, (String) newValue))
              addToDiff(diffProps, prop, oldValue, newValue);
        } else if (oldValue instanceof Integer) {
            if (!identicalNumber((Integer) oldValue, (Integer) newValue))
                addToDiff(diffProps, prop, oldValue, newValue);
        } else if (oldValue instanceof Double) {
            if (!identicalNumber((Double) oldValue, (Double) newValue))
                addToDiff(diffProps, prop, oldValue, newValue);
        } else if (oldValue instanceof Boolean) {
            if (!identicalBoolean((Boolean) oldValue, (Boolean) newValue))
                addToDiff(diffProps, prop, oldValue, newValue);
        } else if (oldValue instanceof Date) {
            if (!identicalDate((Date) oldValue, (Date) newValue))
                addToDiff(diffProps, prop, Shared.toFormatedStringDate((Date) oldValue), Shared.toFormatedStringDate((Date) newValue));
        } else {
            if (oldValue == newValue) {} else {
//                addToDiff(diffProps, prop, oldValue, newValue);
            }
        }

    }

    private static <T> Field[] getSuperclassFileds(Class<T> t) {
        return t.getSuperclass().getDeclaredFields();
    }

    private static <T> Field[] getClassFileds(Class<T> t) {
        return t.getDeclaredFields();
    }


    /**
     *
     * @param oldDto
     * @param newDto
     * @return
     */
    public static CompareDiff analizingOperationalDiff(Operational3rdPartyDTO oldDto, Operational3rdPartyDTO newDto) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<DiffProp> diffProps = new ArrayList<>();

        /* reflecting */
        Field[] fileds = ArrayUtils.addAll(getSuperclassFileds(Operational3rdPartyDTO.class), getClassFileds(Operational3rdPartyDTO.class));


//        oldDto.is

//        Arrays.stream(fileds).map(field -> {
//
//        });
        for (Field field : fileds) {
            String targetMethod = field.getType().equals(boolean.class) ? "is" : "get";
            targetMethod += Shared.changeFirstToTitle(field.getName());
            Method oldMethod = oldDto.getClass().getMethod(targetMethod);
            Method newMethod = newDto.getClass().getMethod(targetMethod);
            Object oldValue = oldMethod.invoke(oldDto);
            Object newValue = newMethod.invoke(newDto);
            compareAndAddIt(diffProps, field.getName(), oldValue, newValue);
        }

//        Operational3rdPartyDTO.class.getFields()

        compareAndAddIt(diffProps, "uuid", TranslatorUser3rdParty.decodeId(oldDto.getId()), TranslatorUser3rdParty.decodeId(newDto.getId()));
        compareAndAddIt(diffProps, "namaLokasiPendaratan", oldDto.getNamaLokasiPendaratan(), newDto.getNamaLokasiPendaratan());
        compareAndAddIt(diffProps, "namaSumberDaya", oldDto.getNamaSumberDaya(), newDto.getNamaSumberDaya());
        compareAndAddIt(diffProps, "namaPencatat", oldDto.getNamaPencatat(), newDto.getNamaPencatat());
        compareAndAddIt(diffProps, "tanggalSampling", oldDto.getTanggalSampling(), newDto.getTanggalSampling());
        compareAndAddIt(diffProps, "namaKapal", oldDto.getNamaKapal(), newDto.getNamaKapal());

        Diff diff = Diff.builder()
                .onId(oldDto.getId())
                .diffType(diffProps.isEmpty() ? null : HistoryDiffType.CHANGED)
                .props(diffProps).build();

        return CompareDiff.builder()
                .identical(diffProps.isEmpty())
                .diff(diff)
                .build();
    }

}
