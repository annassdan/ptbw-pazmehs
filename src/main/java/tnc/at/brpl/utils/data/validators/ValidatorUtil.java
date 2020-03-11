package tnc.at.brpl.utils.data.validators;

import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.services.thirdparty.Utility3rdPartyService;
import tnc.at.brpl.services.thirdparty.util.AlatTangkap;
import tnc.at.brpl.utils.other.Shared;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ValidatorUtil {

    public static boolean tkgValid(String tkg) {
        if (tkg == null || tkg.length() == 0)
            return false;

        return Utility3rdPartyService.tkg().stream().anyMatch(s -> s.getTkg().toUpperCase().equals(tkg.toUpperCase()));
    }




    public static boolean tipePanjangValid(String tkg) {
        if (tkg == null || tkg.length() == 0)
            return false;

        return Utility3rdPartyService.lengthTypes.stream().anyMatch(s -> s.toUpperCase().equals(tkg.toUpperCase()));
    }


    public static boolean wppValid(String wpp) {
        if (wpp == null || wpp.length() == 0)
            return false;

        return Utility3rdPartyService.wpps.stream().anyMatch(s -> s.toUpperCase().equals(wpp.toUpperCase()));
    }


    public static boolean sumberdayaValid(String sd) {
        if (sd == null || sd.length() == 0)
            return false;

        return Utility3rdPartyService.sumberDaya().stream().anyMatch(s -> s.getSumberDaya().toUpperCase().equals(sd.toUpperCase()));
    }


    public static boolean alatTangkapValid(String sumberdaya) {
        if (sumberdaya == null) return false;

        Optional<List<AlatTangkap>> opt = Utility3rdPartyService.sumberDaya().stream()
                .filter(d -> d.getSumberDaya().trim().toUpperCase().equals(sumberdaya.trim().toUpperCase()))
                .findFirst()
                .map(sumberDaya -> sumberDaya.getDaftarAlatTangkap());

        return opt.isPresent();
    }

    /**
     *
     * @param errors Void
     */
    public static void expectNoThrowError(List<String> errors) {
        if (!errors.isEmpty()) {
            String message = errors.get(0);
            if (message == null || message.length() == 0) {
                if (errors.size() > 1) {
                    errors.remove(0);
                    expectNoThrowError(errors);
                } else {
                    return;
                }
            }

            /* remove unused spaces between words */
            if (Shared.verifyString(message).length() == 0) {
                if (errors.size() > 1) {
                    errors.remove(0);
                    expectNoThrowError(errors);
                } else {
                    return;
                }
            }

            throw new ResourceInternalServerErrorException(message);
        }
    }


    /**
     *
     * @param errors current no filtered errors
     * @return Boolean
     */
    public static boolean expectHaveErrorMessage(List<String> errors) {
        if (!errors.isEmpty()) {
            boolean expect = false;
            List<String> filtered = errors.stream()
                    .filter(s -> {
                        if (s == null || s.length() == 0)
                            return false;

                        /* remove unused spaces between words */
                        return Shared.verifyString(s).length() != 0;
                    }).collect(Collectors.toList());;

            return expect;
        }

        return false;
    }


}
