package tnc.at.brpl.services.thirdparty.sampling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.dto.Landing3rdPartyDTO;
import tnc.at.brpl.models.main.history.LandingHistory;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.BiologyOnReproductionRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeRepository;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.repositories.main.OperationalRepository;
import tnc.at.brpl.repositories.main.history.LandingHistoryRepository;
import tnc.at.brpl.services.thirdparty.Delete3rdPartyResponse;
import tnc.at.brpl.utils.data.DataOrder;
import tnc.at.brpl.utils.data.HistoryActionType;
import tnc.at.brpl.utils.data.validators.ValidatorUtil;
import tnc.at.brpl.utils.data.validators.thirdparty.Landing3rdPartyValidator;
import tnc.at.brpl.utils.thirdparty.Translator3rdParty;
import tnc.at.brpl.utils.thirdparty.TranslatorUser3rdParty;

import java.util.Date;
import java.util.List;

@Service
public class TripService {


    @Autowired
    LandingRepository landingRepository;

    @Autowired
    LandingHistoryRepository landingHistoryRepository;

    @Autowired
    OperationalRepository operationalRepository;

    @Autowired
    BiologyOnSizeRepository sizeRepository;

    @Autowired
    BiologyOnReproductionRepository reproductionRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    private String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    private SysUser getUserInformation() {
        return sysUserRepository.findByPengguna(getUsername());
    }

    /**
     *
     * @param landing3rdPartyDTO save data sampling from mitra organization
     * @return Landing3rdPartyDTO
     */
    @Transactional
    public Landing3rdPartyDTO save(Landing3rdPartyDTO landing3rdPartyDTO) {
        /* validate the data first */
        ValidatorUtil.expectNoThrowError(Landing3rdPartyValidator.validateAll(landing3rdPartyDTO, true));

        Translator3rdParty rdParty = new Translator3rdParty(false, getUserInformation());
        Landing saved = landingRepository.save(rdParty.transformLanding(landing3rdPartyDTO));
        landingHistoryRepository.save(LandingHistory.builder()
                .actionType(HistoryActionType.INSERT)
                .affectedTo(saved.getUuid())
                .build());

        return landing3rdPartyDTO;
    }


    @Transactional
    public Landing3rdPartyDTO update(Landing3rdPartyDTO landing3rdPartyDTO) {
        /* validate the data first */
        ValidatorUtil.expectNoThrowError(Landing3rdPartyValidator.validateAll(landing3rdPartyDTO, false));

        Translator3rdParty rdParty = new Translator3rdParty(false, getUserInformation());
        Landing saved = landingRepository.save(rdParty.transformLanding(landing3rdPartyDTO));
        landingHistoryRepository.save(LandingHistory.builder()
                .actionType(HistoryActionType.UPDATE)
                .affectedTo(saved.getUuid())
                .changes("")
                .build());
        return landing3rdPartyDTO;
    }

    @Transactional
    public Delete3rdPartyResponse delete(String id) {
        String tempId = TranslatorUser3rdParty.encodeId(id);
        Landing landing = landingRepository.findOne(tempId);
        if (landing == null) {
            landing = landingRepository.findOne(id);
            if (landing == null) throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");
        }

        SysUser sysUser = getUserInformation();
        if (!landing.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        landingRepository.delete(id);
        landingHistoryRepository.save(LandingHistory.builder()
                .actionType(HistoryActionType.DELETE)
                .affectedTo(id)
                .meta(landing.toString())
                .build());

        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public Landing3rdPartyDTO getOne(String id) {
        String tempId = TranslatorUser3rdParty.encodeId(id);
        Landing landing = landingRepository.findOne(tempId);
        if (landing == null) {
            landing = landingRepository.findOne(id);
            if (landing == null) throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");
        }

        SysUser sysUser = getUserInformation();
        if (!landing.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf, organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());
        }

        Translator3rdParty rdParty = new Translator3rdParty();
        return rdParty.deTransformLanding(landing);
    }

    public Page<Landing3rdPartyDTO> getAll(int page, int size, DataOrder order) {
        Pageable paging = new PageRequest(page, size);
        SysUser sysUser = getUserInformation();

        Page<Landing> landingPage = order == DataOrder.asc
                ? landingRepository.findAllByOrganisasiOrderByDibuatPadaTanggalAsc(paging, sysUser.getOrganisasi())
                : landingRepository.findAllByOrganisasiOrderByDibuatPadaTanggalDesc(paging, sysUser.getOrganisasi());

        Translator3rdParty rdParty = new Translator3rdParty();
        return new PageImpl<>(rdParty.deTransformLandings(landingPage.getContent()), paging, landingPage.getTotalElements());
    }


}
