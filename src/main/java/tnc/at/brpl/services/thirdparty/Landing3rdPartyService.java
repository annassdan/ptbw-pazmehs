package tnc.at.brpl.services.thirdparty;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.*;
import tnc.at.brpl.models.main.dto.*;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.services.main.LandingService;
import tnc.at.brpl.utils.thirdparty.Translator3rdParty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class Landing3rdPartyService {

    @Autowired
    LandingRepository landingRepository;

    @Autowired
    LandingService landingService;

    @Autowired
    SysUserRepository sysUserRepository;

    private Date auditDate;

    SysUser user;

    private void generateDateForAudit() {
        this.auditDate = new Date();
    }



    /**
     *
     * @param landing3rdPartyDTO
     * @return
     */
    public Landing3rdPartyDTO save(Landing3rdPartyDTO landing3rdPartyDTO) {

        generateDateForAudit();
        String username = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        SysUser sysUser = sysUserRepository.findByPengguna(username);
        user = sysUserRepository.findByPengguna(username);

        Translator3rdParty rdParty = new Translator3rdParty(new Date(), sysUser);
        landingRepository.save(rdParty.transformLanding(landing3rdPartyDTO, sysUser));
        return landing3rdPartyDTO;
    }



    public Page<?> getAll3rdPartyData(int page, int size) {
        String username = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        SysUser sysUser = sysUserRepository.findByPengguna(username);

        Pageable paging = new PageRequest(page, size);
        Page<Landing> landingPage = landingRepository.findAllByOrganisasiOrderByDibuatPadaTanggalAsc(paging, sysUser.getOrganisasi());

        Translator3rdParty rdParty = new Translator3rdParty();
        List<Landing3rdPartyDTO> landing3rdPartyDTOList = rdParty.deTransformLandings(landingPage.getContent());

        return new PageImpl<>(landing3rdPartyDTOList, paging, landingPage.getTotalElements());
    }


    public Landing3rdPartyDTO getById3rdPartyData(String id) {
        String username = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        SysUser sysUser = sysUserRepository.findByPengguna(username);

        Landing landing = landingRepository.findOne(id);
        if (!landing.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf, organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        Translator3rdParty rdParty = new Translator3rdParty();
        return rdParty.deTransformLanding(landing);
    }


    public Delete3rdPartyResponse deleteById3rdPartyData(String id) {
        String username = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        SysUser sysUser = sysUserRepository.findByPengguna(username);

        Landing landing = landingRepository.findOne(id);
        if (!landing.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        landingRepository.delete(id);
        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


}
