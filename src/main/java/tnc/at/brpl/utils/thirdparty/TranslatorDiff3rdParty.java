package tnc.at.brpl.utils.thirdparty;


import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.other.Shared;
import tnc.at.brpl.utils.thirdparty.diff.CompareDiff;
import tnc.at.brpl.utils.thirdparty.diff.Diff;
import tnc.at.brpl.utils.thirdparty.diff.DiffProp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TranslatorDiff3rdParty implements Brpl {

    private static boolean identicalString(String a, String b) {
        return Shared.verifyString(a).equals(Shared.verifyString(b));
    }

    private static boolean identicalBoolean(boolean a, boolean b) {
        return a == b;
    }

    private static boolean identicalNumber(int a, int b) {
        return a == b;
    }

    private static boolean identicalNumber(double a, double b) {
        return a == b;
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
            if (!identicalNumber((int) oldValue, (int) newValue))
                addToDiff(diffProps, prop, oldValue, newValue);
        } else if (oldValue instanceof Double) {
            if (!identicalNumber((double) oldValue, (double) newValue))
                addToDiff(diffProps, prop, oldValue, newValue);
        } else if (oldValue instanceof Boolean) {
            if (!identicalBoolean((boolean) oldValue, (boolean) newValue))
                addToDiff(diffProps, prop, oldValue, newValue);
        } else if (oldValue instanceof Date) {
            if (!identicalDate((Date) oldValue, (Date) newValue))
                addToDiff(diffProps, prop, Shared.toFormatedStringDate((Date) oldValue), Shared.toFormatedStringDate((Date) newValue));
        } else {
            if (oldValue == newValue) {} else {
                addToDiff(diffProps, prop, oldValue, newValue);
            }
        }

    }

    /**
     *
     * @param oldDto
     * @param newDto
     * @return
     */
    public static CompareDiff analizingOperationalDiff(Operational3rdPartyDTO oldDto, Operational3rdPartyDTO newDto) {
        List<DiffProp> diffProps = new ArrayList<>();

        compareAndAddIt(diffProps, "uuid", TranslatorUser3rdParty.decodeId(oldDto.getId()), TranslatorUser3rdParty.decodeId(newDto.getId()));
        compareAndAddIt(diffProps, "namaLokasiPendaratan", oldDto.getNamaLokasiPendaratan(), newDto.getNamaLokasiPendaratan());
        compareAndAddIt(diffProps, "namaSumberDaya", oldDto.getNamaSumberDaya(), newDto.getNamaSumberDaya());
        compareAndAddIt(diffProps, "namaPencatat", oldDto.getNamaPencatat(), newDto.getNamaPencatat());
        compareAndAddIt(diffProps, "tanggalSampling", oldDto.getTanggalSampling(), newDto.getTanggalSampling());
        compareAndAddIt(diffProps, "namaKapal", oldDto.getNamaKapal(), newDto.getNamaKapal());

        Diff diff = Diff.builder()
                .onId(oldDto.getId())
                .props(diffProps).build();






        return CompareDiff.builder()
                .identical(diffProps.isEmpty())
                .diff(diff)
                .build();
    }

}
