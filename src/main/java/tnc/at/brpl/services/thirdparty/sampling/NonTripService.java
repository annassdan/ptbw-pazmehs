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
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.models.main.dto.BiologyOnReproduction3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSize3rdPartyDTO;
import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.models.main.history.BiologyOnReproductionHistory;
import tnc.at.brpl.models.main.history.BiologyOnSizeHistory;
import tnc.at.brpl.models.main.history.OperationalHistory;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.BiologyOnReproductionRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeRepository;
import tnc.at.brpl.repositories.main.OperationalRepository;
import tnc.at.brpl.repositories.main.history.BiologyOnReproductionHistoryRepository;
import tnc.at.brpl.repositories.main.history.BiologyOnSizeHistoryRepository;
import tnc.at.brpl.repositories.main.history.OperationalHistoryRepository;
import tnc.at.brpl.services.thirdparty.Delete3rdPartyResponse;
import tnc.at.brpl.utils.data.DataOrder;
import tnc.at.brpl.utils.data.HistoryActionType;
import tnc.at.brpl.utils.data.validators.ValidatorUtil;
import tnc.at.brpl.utils.data.validators.thirdparty.BiologyOnReproduction3rdPartyValidator;
import tnc.at.brpl.utils.data.validators.thirdparty.BiologyOnSize3rdPartyValidator;
import tnc.at.brpl.utils.data.validators.thirdparty.Operational3rdPartyValidator;
import tnc.at.brpl.utils.other.Shared;
import tnc.at.brpl.utils.thirdparty.Translator3rdParty;
import tnc.at.brpl.utils.thirdparty.TranslatorDiff3rdParty;
import tnc.at.brpl.utils.thirdparty.TranslatorUser3rdParty;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@Service
public class NonTripService {

    @Autowired
    OperationalRepository operationalRepository;

    @Autowired
    OperationalHistoryRepository operationalHistoryRepository;

    @Autowired
    BiologyOnSizeRepository sizeRepository;

    @Autowired
    BiologyOnSizeHistoryRepository sizeHistoryRepository;

    @Autowired
    BiologyOnReproductionRepository reproductionRepository;

    @Autowired
    BiologyOnReproductionHistoryRepository reproductionHistoryRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    private String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    private SysUser getUserInformation() {
        return sysUserRepository.findByPengguna(getUsername());
    }


    /* operational non trip service */

    public Operational3rdPartyDTO saveNonTripOperasional(Operational3rdPartyDTO operational3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(Operational3rdPartyValidator.validate(operational3rdPartyDTO, false, true, false));

        Translator3rdParty rdParty = new Translator3rdParty(true, getUserInformation());
        Operational operational = rdParty.transformOperational(operational3rdPartyDTO);
        operational.setNonTrip(true);
        Operational saved = operationalRepository.save(operational);
        operationalHistoryRepository.save(OperationalHistory.builder()
                .actionType(HistoryActionType.INSERT)
                .affectedTo(saved.getUuid())
                .nonTrip(true)
                .build());
        return operational3rdPartyDTO;
    }


    public Operational3rdPartyDTO updateNonTripOperasional(Operational3rdPartyDTO operational3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(Operational3rdPartyValidator.validate(operational3rdPartyDTO, false, false, false));

//        Operational operational = operationalRepository.findOne(TranslatorUser3rdParty.encodeId(operational3rdPartyDTO.getId()));
//        if (operational == null)
//            operational = operationalRepository.findOne(operational3rdPartyDTO.getId());


        Translator3rdParty rdParty = new Translator3rdParty(true, getUserInformation());
//
//        if (operational != null) {
//            Operational3rdPartyDTO dto = rdParty.deTransformOperational(operational);
//            try {
//                TranslatorDiff3rdParty.analizingOperationalDiff(dto, operational3rdPartyDTO);
//            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }

        Operational saved = operationalRepository.save(rdParty.transformOperational(operational3rdPartyDTO));
        operationalHistoryRepository.save(OperationalHistory.builder().actionType(HistoryActionType.UPDATE)
                .affectedTo(saved.getUuid())
                .changes(null)
                .nonTrip(false)
                .build());
        return operational3rdPartyDTO;
    }


    @Transactional
    public Delete3rdPartyResponse deleteNonTripOperasional(String id) {
        Operational operational = operationalRepository.findOne(TranslatorUser3rdParty.encodeId(id));
        if (operational == null) {
            operational = operationalRepository.findOne(id);
            if (operational == null) throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");
        }

        SysUser sysUser = getUserInformation();
        if (!operational.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        String meta = Shared.objectToJsonString(operational);
        operationalRepository.delete(id);
        operationalHistoryRepository.save(OperationalHistory.builder()
                .actionType(HistoryActionType.DELETE)
                .affectedTo(id)
                .meta(meta)
                .nonTrip(false)
                .build());

        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public Operational3rdPartyDTO getOneNonTripOperasional(String id) {
        Operational operational = operationalRepository.findOne(TranslatorUser3rdParty.encodeId(id));
        if (operational == null) {
            operational = operationalRepository.findOne(id);
            if (operational == null) throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak dapat ditemukan");
        }

        SysUser sysUser = getUserInformation();
        if (!operational.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf, organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());
        }

        Translator3rdParty rdParty = new Translator3rdParty();
        return rdParty.deTransformOperational(operational);
    }


    public Page<Operational3rdPartyDTO> getAllNonTripOperasional(int page, int size, DataOrder order) {
        Pageable paging = new PageRequest(page, size);
        SysUser sysUser = getUserInformation();

        Page<Operational> operationalPage = order == DataOrder.asc
                ? operationalRepository.findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalAsc(paging, sysUser.getOrganisasi(), true)
                : operationalRepository.findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalDesc(paging, sysUser.getOrganisasi(), true);

        Translator3rdParty rdParty = new Translator3rdParty();
        return new PageImpl<>(rdParty.deTransformOperationals(operationalPage.getContent()), paging, operationalPage.getTotalElements());
    }
    /* end of operational non trip service */


    /* biology on sizen non trip service */

    public BiologyOnSize3rdPartyDTO saveNonTripBiologyOnSize(BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(BiologyOnSize3rdPartyValidator.validate(biologyOnSize3rdPartyDTO, false, true, false));

        Translator3rdParty rdParty = new Translator3rdParty(true, getUserInformation());
        BiologyOnSize saved = sizeRepository.save(rdParty.transformSize(biologyOnSize3rdPartyDTO));
        sizeHistoryRepository.save(BiologyOnSizeHistory.builder()
                .actionType(HistoryActionType.INSERT)
                .affectedTo(saved.getUuid())
                .nonTrip(true)
                .build());
        return biologyOnSize3rdPartyDTO;
    }


    public BiologyOnSize3rdPartyDTO updateNonTripBiologyOnSize(BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(BiologyOnSize3rdPartyValidator.validate(biologyOnSize3rdPartyDTO, false, false, false));

        Translator3rdParty rdParty = new Translator3rdParty(true, getUserInformation());
        BiologyOnSize saved = sizeRepository.save(rdParty.transformSize(biologyOnSize3rdPartyDTO));
        sizeHistoryRepository.save(BiologyOnSizeHistory.builder().actionType(HistoryActionType.UPDATE)
                .affectedTo(saved.getUuid())
                .changes(null)
                .nonTrip(false)
                .build());
        return biologyOnSize3rdPartyDTO;
    }


    public Delete3rdPartyResponse deleteNonTripBiologyOnSize(String id) {
        BiologyOnSize size = sizeRepository.findOne(TranslatorUser3rdParty.encodeId(id));
        if (size == null) {
            size = sizeRepository.findOne(id);
            if (size == null) throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");
        }

        SysUser sysUser = getUserInformation();
        if (!size.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        String meta = Shared.objectToJsonString(size);
        sizeRepository.delete(id);
        sizeHistoryRepository.save(BiologyOnSizeHistory.builder()
                .actionType(HistoryActionType.DELETE)
                .affectedTo(id)
                .meta(meta)
                .nonTrip(false)
                .build());

        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public BiologyOnSize3rdPartyDTO getOneNonTripBiologyOnSize(String id) {
        BiologyOnSize size = sizeRepository.findOne(TranslatorUser3rdParty.encodeId(id));
        if (size == null) {
            size = sizeRepository.findOne(id);
            if (size == null) throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak dapat ditemukan");
        }

        SysUser sysUser = getUserInformation();
        if (!size.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf, organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());
        }

        Translator3rdParty rdParty = new Translator3rdParty();
        return rdParty.deTransformSize(size);
    }


    public Page<BiologyOnSize3rdPartyDTO> getAllNonTripBiologyOnSize(int page, int size, DataOrder order) {
        Pageable paging = new PageRequest(page, size);
        SysUser sysUser = getUserInformation();

        Page<BiologyOnSize> biologyOnSizePage = order == DataOrder.asc
                ? sizeRepository.findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalAsc(paging, sysUser.getOrganisasi(), true)
                : sizeRepository.findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalDesc(paging, sysUser.getOrganisasi(), true);

        Translator3rdParty rdParty = new Translator3rdParty();
        return new PageImpl<>(rdParty.deTransformSizes(biologyOnSizePage.getContent()), paging, biologyOnSizePage.getTotalElements());
    }
    /* end of biology on sizen non trip service */



    /* biology on reproduction non trip service */

    public BiologyOnReproduction3rdPartyDTO saveNonTripBiologyOnReproduction(BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(BiologyOnReproduction3rdPartyValidator.validate(biologyOnReproduction3rdPartyDTO, false, true, false));

        Translator3rdParty rdParty = new Translator3rdParty(true, getUserInformation());
        BiologyOnReproduction saved = reproductionRepository.save(rdParty.transformReproduction(biologyOnReproduction3rdPartyDTO));
        reproductionHistoryRepository.save(BiologyOnReproductionHistory.builder()
                .actionType(HistoryActionType.INSERT)
                .affectedTo(saved.getUuid())
                .nonTrip(true)
                .build());
        return biologyOnReproduction3rdPartyDTO;
    }


    public BiologyOnReproduction3rdPartyDTO updateNonTripBiologyOnReproduction(BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(BiologyOnReproduction3rdPartyValidator.validate(biologyOnReproduction3rdPartyDTO, false, false, false));

        Translator3rdParty rdParty = new Translator3rdParty(true, getUserInformation());
        BiologyOnReproduction saved = reproductionRepository.save(rdParty.transformReproduction(biologyOnReproduction3rdPartyDTO));
        reproductionHistoryRepository.save(BiologyOnReproductionHistory.builder().actionType(HistoryActionType.UPDATE)
                .affectedTo(saved.getUuid())
                .changes(null)
                .nonTrip(false)
                .build());
        return biologyOnReproduction3rdPartyDTO;
    }


    public Delete3rdPartyResponse deleteNonTripBiologyOnReproduction(String id) {
        BiologyOnReproduction reproduction = reproductionRepository.findOne(TranslatorUser3rdParty.encodeId(id));
        if (reproduction == null) {
            reproduction = reproductionRepository.findOne(id);
            if (reproduction == null) throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");
        }

        SysUser sysUser = getUserInformation();
        if (!reproduction.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        String meta = Shared.objectToJsonString(reproduction);
        reproductionRepository.delete(id);
        reproductionHistoryRepository.save(BiologyOnReproductionHistory.builder()
                .actionType(HistoryActionType.DELETE)
                .affectedTo(id)
                .meta(meta)
                .nonTrip(false)
                .build());

        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public BiologyOnReproduction3rdPartyDTO getOneNonTripBiologyOnReproduction(String id) {
        BiologyOnReproduction reproduction = reproductionRepository.findOne(id);
        if (reproduction == null) {
            reproduction = reproductionRepository.findOne(id);
            if (reproduction == null)
                throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak dapat ditemukan");
        }

        SysUser sysUser = getUserInformation();
        if (!reproduction.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf, organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());
        }

        Translator3rdParty rdParty = new Translator3rdParty();
        return rdParty.deTransformReproduction(reproduction);
    }


    public Page<BiologyOnReproduction3rdPartyDTO> getAllNonTripBiologyOnReproduction(int page, int size, DataOrder order) {
        Pageable paging = new PageRequest(page, size);
        SysUser sysUser = getUserInformation();

        Page<BiologyOnReproduction> biologyOnReproductionPage = order == DataOrder.asc
                ? reproductionRepository.findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalAsc(paging, sysUser.getOrganisasi(), true)
                : reproductionRepository.findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalDesc(paging, sysUser.getOrganisasi(), true);

        Translator3rdParty rdParty = new Translator3rdParty();
        return new PageImpl<>(rdParty.deTransformReproductions(biologyOnReproductionPage.getContent()), paging, biologyOnReproductionPage.getTotalElements());
    }
    /* end of biology on reproduction non trip service */


}
