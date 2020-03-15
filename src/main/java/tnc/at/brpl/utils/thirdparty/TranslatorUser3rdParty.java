package tnc.at.brpl.utils.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.dto.Main3rdPartyDTO;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.other.Shared;

import javax.annotation.PostConstruct;

@Component
public class TranslatorUser3rdParty implements Brpl {


    private static SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRepository sysUserRepositoryInjecter;

    @PostConstruct
    private void init() {
        sysUserRepository = sysUserRepositoryInjecter;
    }


    private static String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public static SysUser getUserInformation() {
        return sysUserRepository.findByPengguna(getUsername());
    }


    public static <T extends Main3rdPartyDTO> String encodeId(T model) {
        SysUser user = getUserInformation();
        if (user == null)
            return Shared.verifyString(model.getId());

        return Shared.verifyString(model.getId()).isEmpty() ? "" : user.getUuid() + MITRA_ID_DELIMITER + Shared.verifyString(model.getId());
    }

    public static String encodeId(String id) {
        SysUser user = getUserInformation();
        if (user == null)
            return Shared.verifyString(id);

        return Shared.verifyString(id).isEmpty() ? "" : user.getUuid() + MITRA_ID_DELIMITER + Shared.verifyString(id);
    }

    public static String decodeId(String encodedId) {
        String[] splitedId = encodedId.split(MITRA_ID_DELIMITER, 2);
        return splitedId.length == 0 || splitedId.length == 1 ? encodedId : splitedId[1];
    }

}
