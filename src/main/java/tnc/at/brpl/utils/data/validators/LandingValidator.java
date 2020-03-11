package tnc.at.brpl.utils.data.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.LandingCatchDetail;
import tnc.at.brpl.models.main.LandingDetail;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.utils.other.Shared;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
//@SuppressWarnings("unused")
public class LandingValidator {


    private static LandingRepository landingRepository;

    @Autowired
    private LandingRepository landingRepositoryInjecter;

    @Autowired
    private static SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRepository sysUserRepositoryInjecter;

    @PostConstruct
    private void init() {
        landingRepository = landingRepositoryInjecter;
        sysUserRepository = sysUserRepositoryInjecter;
    }

    public LandingValidator() {
    }

    private static String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    /**
     *
     * @param landing Data Landing
     * @return List<String>
     */
    public static List<String> validateLanding(Landing landing) {
        List<String> errorMessage = new ArrayList<>();

        return errorMessage;
    }

    /**
     *
     * @param landingDetail Data Landing Detail
     * @return List<String>
     */
    public static List<String> validateLandingDetail(LandingDetail landingDetail) {
        List<String> errorMessage = new ArrayList<>();

        return errorMessage;
    }



    public static List<String> validateLandingCatchDetail(LandingCatchDetail landingCatchDetail) {
        return validateLandingCatchDetail(landingCatchDetail, null);
    }

    /**
     *
     * @param landingCatchDetail Data landing Catch Detail
     * @return List<String>
     */
    public static List<String> validateLandingCatchDetail(LandingCatchDetail landingCatchDetail, LandingDetail landingDetail) {
        List<String> errorMessage = new ArrayList<>();
        String extend = landingCatchDetail == null ? "(Rincian Pendaratan untuk Kapal " + Shared.verifyString(landingDetail.getNamaKapal(), true) + ")." : "";

        return errorMessage;
    }

    /**
     *
     * @param landing Data Landing
     * @return List<String>
     */
    public static List<String> validateAllCriteria(Landing landing) {
        List<String> errorMessage = new ArrayList<>();

        /* validate landing header */
        errorMessage.addAll(validateLanding(landing));

        /* validate landing detail, Landing Catch Detail */
        List<String> errorsOnDetailAndCatch = landing.getDataRincianPendaratan()
                .stream()
                .map(landingDetail -> {
                    List<String> error = new ArrayList<>();

                    /* validate landing catch detail */
                    error.addAll(landingDetail.getDataRincianTangkapanPendaratan()
                            .stream()
                            .map(landingCatchDetail -> validateLandingCatchDetail(landingCatchDetail))
                            .flatMap(strings -> strings.stream())
                            .collect(Collectors.toList()));

                    error.addAll(validateLandingDetail(landingDetail));
                    return error;
                })
                .flatMap(strings -> strings.stream())
                .collect(Collectors.toList());

        /* add to list of error */
        errorMessage.addAll(errorsOnDetailAndCatch);

        return errorMessage;
    }



}
