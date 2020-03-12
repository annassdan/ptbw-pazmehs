package tnc.at.brpl.services.thirdparty.sampling;

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
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.models.main.dto.BiologyOnReproduction3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSize3rdPartyDTO;
import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.BiologyOnReproductionRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeRepository;
import tnc.at.brpl.repositories.main.OperationalRepository;
import tnc.at.brpl.services.thirdparty.Delete3rdPartyResponse;
import tnc.at.brpl.utils.data.DataOrder;
import tnc.at.brpl.utils.data.validators.ValidatorUtil;
import tnc.at.brpl.utils.data.validators.thirdparty.BiologyOnReproduction3rdPartyValidator;
import tnc.at.brpl.utils.data.validators.thirdparty.BiologyOnSize3rdPartyValidator;
import tnc.at.brpl.utils.data.validators.thirdparty.Operational3rdPartyValidator;
import tnc.at.brpl.utils.thirdparty.Translator3rdParty;

import java.util.Date;

@Service
public class NonTripService {

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


    /* operational non trip service */

    public Operational3rdPartyDTO saveNonTripOperasional(Operational3rdPartyDTO operational3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(Operational3rdPartyValidator.validate(operational3rdPartyDTO));

        Translator3rdParty rdParty = new Translator3rdParty(new Date(), getUserInformation());
        operationalRepository.save(rdParty.transformOperational(operational3rdPartyDTO));
        return operational3rdPartyDTO;
    }


    public Operational3rdPartyDTO updateNonTripOperasional(Operational3rdPartyDTO operational3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(Operational3rdPartyValidator.validate(operational3rdPartyDTO));

        Operational existing = operationalRepository.findOne(operational3rdPartyDTO.getId());
        if (existing == null)
            throw new ResourceInternalServerErrorException("Data Operasional tidak ada di database, silahkan lakukan penginputan terlebih dahulu.");

        SysUser sysUser = getUserInformation();
        if (!existing.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat ubah, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        Translator3rdParty rdParty = new Translator3rdParty(new Date(), sysUser);
        operationalRepository.save(rdParty.transformOperational(operational3rdPartyDTO));
        return operational3rdPartyDTO;
    }


    public Delete3rdPartyResponse deleteNonTripOperasional(String id) {
        Operational operational = operationalRepository.findOne(id);
        if (operational == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        SysUser sysUser = getUserInformation();
        if (!operational.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        operationalRepository.delete(id);
        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public Operational3rdPartyDTO getOneNonTripOperasional(String id) {
        Operational operational = operationalRepository.findOne(id);
        if (operational == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak dapat ditemukan");

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
                ? operationalRepository.findAllByOrganisasiOrderByDibuatPadaTanggalAsc(paging, sysUser.getOrganisasi())
                : operationalRepository.findAllByOrganisasiOrderByDibuatPadaTanggalDesc(paging, sysUser.getOrganisasi());

        Translator3rdParty rdParty = new Translator3rdParty();
        return new PageImpl<>(rdParty.deTransformOperationals(operationalPage.getContent()), paging, operationalPage.getTotalElements());
    }
    /* end of operational non trip service */


    /* biology on sizen non trip service */

    public BiologyOnSize3rdPartyDTO saveNonTripBiologyOnSize(BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(BiologyOnSize3rdPartyValidator.validate(biologyOnSize3rdPartyDTO));

        Translator3rdParty rdParty = new Translator3rdParty(new Date(), getUserInformation());
        sizeRepository.save(rdParty.transformSize(biologyOnSize3rdPartyDTO));
        return biologyOnSize3rdPartyDTO;
    }


    public BiologyOnSize3rdPartyDTO updateNonTripBiologyOnSize(BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(BiologyOnSize3rdPartyValidator.validate(biologyOnSize3rdPartyDTO));

        BiologyOnSize existing = sizeRepository.findOne(biologyOnSize3rdPartyDTO.getId());
        if (existing == null)
            throw new ResourceInternalServerErrorException("Data Ukuran tidak ada di database, silahkan lakukan penginputan terlebih dahulu.");

        SysUser sysUser = getUserInformation();
        if (!existing.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat ubah, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        Translator3rdParty rdParty = new Translator3rdParty(new Date(), sysUser);
        sizeRepository.save(rdParty.transformSize(biologyOnSize3rdPartyDTO));
        return biologyOnSize3rdPartyDTO;
    }


    public Delete3rdPartyResponse deleteNonTripBiologyOnSize(String id) {
        BiologyOnSize size = sizeRepository.findOne(id);
        if (size == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        SysUser sysUser = getUserInformation();
        if (!size.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        sizeRepository.delete(id);
        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public BiologyOnSize3rdPartyDTO getOneNonTripBiologyOnSize(String id) {
        BiologyOnSize size = sizeRepository.findOne(id);
        if (size == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak dapat ditemukan");

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
                ? sizeRepository.findAllByOrganisasiOrderByDibuatPadaTanggalAsc(paging, sysUser.getOrganisasi())
                : sizeRepository.findAllByOrganisasiOrderByDibuatPadaTanggalDesc(paging, sysUser.getOrganisasi());

        Translator3rdParty rdParty = new Translator3rdParty();
        return new PageImpl<>(rdParty.deTransformSizes(biologyOnSizePage.getContent()), paging, biologyOnSizePage.getTotalElements());
    }
    /* end of biology on sizen non trip service */



    /* biology on reproduction non trip service */

    public BiologyOnReproduction3rdPartyDTO saveNonTripBiologyOnReproduction(BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(BiologyOnReproduction3rdPartyValidator.validate(biologyOnReproduction3rdPartyDTO));

        Translator3rdParty rdParty = new Translator3rdParty(new Date(), getUserInformation());
        reproductionRepository.save(rdParty.transformReproduction(biologyOnReproduction3rdPartyDTO));
        return biologyOnReproduction3rdPartyDTO;
    }


    public BiologyOnReproduction3rdPartyDTO updateNonTripBiologyOnReproduction(BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        /* validate data */
        ValidatorUtil.expectNoThrowError(BiologyOnReproduction3rdPartyValidator.validate(biologyOnReproduction3rdPartyDTO));

        BiologyOnReproduction existing = reproductionRepository.findOne(biologyOnReproduction3rdPartyDTO.getId());
        if (existing == null)
            throw new ResourceInternalServerErrorException("Data Reproduksi tidak ada di database, silahkan lakukan penginputan terlebih dahulu.");

        SysUser sysUser = getUserInformation();
        if (!existing.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat ubah, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        Translator3rdParty rdParty = new Translator3rdParty(new Date(), sysUser);
        reproductionRepository.save(rdParty.transformReproduction(biologyOnReproduction3rdPartyDTO));
        return biologyOnReproduction3rdPartyDTO;
    }


    public Delete3rdPartyResponse deleteNonTripBiologyOnReproduction(String id) {
        BiologyOnReproduction reproduction = reproductionRepository.findOne(id);
        if (reproduction == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        SysUser sysUser = getUserInformation();
        if (!reproduction.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase()))
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi());

        reproductionRepository.delete(id);
        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public BiologyOnReproduction3rdPartyDTO getOneNonTripBiologyOnReproduction(String id) {
        BiologyOnReproduction reproduction = reproductionRepository.findOne(id);
        if (reproduction == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak dapat ditemukan");

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
                ? reproductionRepository.findAllByOrganisasiOrderByDibuatPadaTanggalAsc(paging, sysUser.getOrganisasi())
                : reproductionRepository.findAllByOrganisasiOrderByDibuatPadaTanggalDesc(paging, sysUser.getOrganisasi());

        Translator3rdParty rdParty = new Translator3rdParty();
        return new PageImpl<>(rdParty.deTransformReproductions(biologyOnReproductionPage.getContent()), paging, biologyOnReproductionPage.getTotalElements());
    }
    /* end of biology on reproduction non trip service */



}
