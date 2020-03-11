package tnc.at.brpl.utils.data.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.*;
import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.models.main.dto.OperationalCatchDetails3rdPartyDTO;
import tnc.at.brpl.models.main.dto.OperationalOnFishingToolSpecification3rdPartyDTO;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.OperationalRepository;
import tnc.at.brpl.utils.other.Shared;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class OperationalValidator {

    private static OperationalRepository operationalRepository;

    @Autowired
    private OperationalRepository operationalRepositoryInjecter;

    @Autowired
    private static SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRepository sysUserRepositoryInjecter;

    @PostConstruct
    private void init() {
        operationalRepository = operationalRepositoryInjecter;
        sysUserRepository = sysUserRepositoryInjecter;
    }

    public OperationalValidator() {
    }

    private static String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }


    public static List<String> validateOperational(Operational operational) {
        List<String> errorMessage = new ArrayList<>();

        return errorMessage;
    }


    public static List<String> validateOperational3rdParty(Operational3rdPartyDTO operational3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        return errorMessage;
    }

    public static List<String> validateOperationalGearSpecification(OperationalOnFishingToolSpecification operationalOnFishingToolSpecification) {
        List<String> errorMessage = new ArrayList<>();


        return errorMessage;
    }


    public static List<String> validateOperationalGearSpecification3rdParty(OperationalOnFishingToolSpecification3rdPartyDTO operationalOnFishingToolSpecification3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        if (operationalOnFishingToolSpecification3rdPartyDTO == null)
            return errorMessage;

        if (Shared.isStringNullOrEmpty(operationalOnFishingToolSpecification3rdPartyDTO.getSpesifikasi()))
            errorMessage.add("Ada spesifikasi Alat Tangkap yang kosong");

        return errorMessage;
    }


    public static List<String> validateOperationalCatchDetail(OperationalCatchDetails operationalCatchDetails) {
        List<String> errorMessage = new ArrayList<>();

        return errorMessage;
    }


    public static List<String> validateOperationalCatchDetail3rdParty(OperationalCatchDetails3rdPartyDTO operationalCatchDetails3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        if (Shared.isStringNullOrEmpty(operationalCatchDetails3rdPartyDTO.getNamaSpesies()) && (operationalCatchDetails3rdPartyDTO.getTotalBeratEkor() > 0 || operationalCatchDetails3rdPartyDTO.getTotalBeratKg() > 0))
            errorMessage.add("Ada nama Spesies ikan yang kosong pada data Detail tangkapan Operasional");

        if (operationalCatchDetails3rdPartyDTO.getTotalBeratEkor() <= 0 || operationalCatchDetails3rdPartyDTO.getTotalBeratKg() <= 0)
            errorMessage.add("Ada ukuran ikan yang tidak benar pada data Detail tangkapan Operasional ");

        return errorMessage;
    }





}
