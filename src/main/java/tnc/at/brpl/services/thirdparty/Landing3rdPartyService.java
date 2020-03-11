package tnc.at.brpl.services.thirdparty;

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
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.models.main.dto.BiologyOnReproduction3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSize3rdPartyDTO;
import tnc.at.brpl.models.main.dto.Landing3rdPartyDTO;
import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.BiologyOnReproductionRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeRepository;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.repositories.main.OperationalRepository;
import tnc.at.brpl.services.main.LandingService;
import tnc.at.brpl.utils.data.validators.LandingValidator;
import tnc.at.brpl.utils.data.validators.ValidatorUtil;
import tnc.at.brpl.utils.data.validators.thirdparty.Landing3rdPartyValidator;
import tnc.at.brpl.utils.thirdparty.Translator3rdParty;

import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings("unused")
public class Landing3rdPartyService {

    @Autowired
    LandingRepository landingRepository;

    @Autowired
    OperationalRepository operationalRepository;

    @Autowired
    BiologyOnSizeRepository sizeRepository;

    @Autowired
    BiologyOnReproductionRepository reproductionRepository;

    @Autowired
    LandingService landingService;

    @Autowired
    SysUserRepository sysUserRepository;

    private Date auditDate;

    SysUser user;

    private void generateDateForAudit() {
        this.auditDate = new Date();
    }


    private String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    private SysUser getUserInformation(String username) {
        return sysUserRepository.findByPengguna(username);
    }

    private SysUser getUserInformation() {
        return sysUserRepository.findByPengguna(getUsername());
    }

    /**
     *
     * @param landing3rdPartyDTO save data sampling from mitra organization
     * @return Landing3rdPartyDTO
     */
    public Landing3rdPartyDTO save(Landing3rdPartyDTO landing3rdPartyDTO) {
        ValidatorUtil.expectNoThrowError(Landing3rdPartyValidator.validateAll(landing3rdPartyDTO));

        generateDateForAudit();
        SysUser sysUser = getUserInformation();

        user = sysUserRepository.findByPengguna(getUsername());

        Translator3rdParty rdParty = new Translator3rdParty(new Date(), sysUser);
        Landing landing = landingRepository.save(rdParty.transformLanding(landing3rdPartyDTO, sysUser));
        return rdParty.deTransformLanding(landing);
    }


    /**
     *
     * @param landing3rdPartyDTO
     * @return
     */
    public Landing3rdPartyDTO update(Landing3rdPartyDTO landing3rdPartyDTO) {
        if (landing3rdPartyDTO.getDataRincianPendaratan().isEmpty()) {
            throw new ResourceInternalServerErrorException("Data ini tidak mempunyai rincian kapal pendaratan, ");
        }

        generateDateForAudit();
        SysUser sysUser = getUserInformation();

        /* cek data berdasarkan ID, apakah sudah ada di database atau belum */
        if (landing3rdPartyDTO.getId() == null || landing3rdPartyDTO.getId().length() == 0) {
            throw new ResourceInternalServerErrorException("Data Pendaratan ini tidak memiliki ID");
        } else {
            Landing checkResult = landingRepository.findOne(landing3rdPartyDTO.getId());
            if (checkResult == null)
                throw new ResourceInternalServerErrorException("Data Pendaratan belum ada di Sistem e-BRPL, " +
                        "Silahkan lakukan proses input terlebih dahulu!");
        }

        user = sysUserRepository.findByPengguna(getUsername());

        Translator3rdParty rdParty = new Translator3rdParty(new Date(), sysUser);
        Landing landing = landingRepository.save(rdParty.transformLanding(landing3rdPartyDTO, sysUser));
        return rdParty.deTransformLanding(landing);
    }


    public BiologyOnSize3rdPartyDTO saveNonTripBiologiUkuran(BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        generateDateForAudit();
        SysUser sysUser = getUserInformation();

        /* cek data berdasarkan ID, apakah sudah ada di database atau belum */
        if (biologyOnSize3rdPartyDTO.getId() == null || biologyOnSize3rdPartyDTO.getId().length() == 0) {
            throw new ResourceInternalServerErrorException("Data Ukuran ini tidak memiliki ID");
        } else {
            Landing checkResult = landingRepository.findOne(biologyOnSize3rdPartyDTO.getId());
            if (checkResult != null)
                throw new ResourceInternalServerErrorException("Data Ukuran sudah ada di Sistem e-BRPL, " +
                        "Silahkan lakukan proses update jika ada perubahan pada data ini");
        }

        user = sysUserRepository.findByPengguna(getUsername());

        return null;
    }


    public BiologyOnReproduction3rdPartyDTO saveNonTripBiologiReproduksi(BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        return null;
    }

    public Operational3rdPartyDTO saveNonTripOperasional(Operational3rdPartyDTO operational3rdPartyDTO) {
        return null;
    }



    public Page<?> getAll3rdPartyData(int page, int size) {
        SysUser sysUser = getUserInformation();

        Pageable paging = new PageRequest(page, size);
        Page<Landing> landingPage = landingRepository.findAllByOrganisasiOrderByDibuatPadaTanggalAsc(paging, sysUser.getOrganisasi());

        Translator3rdParty rdParty = new Translator3rdParty();
        List<Landing3rdPartyDTO> landing3rdPartyDTOList = rdParty.deTransformLandings(landingPage.getContent());

        return new PageImpl<>(landing3rdPartyDTOList, paging, landingPage.getTotalElements());
    }


    public Landing3rdPartyDTO getById3rdPartyData(String id) {
        SysUser sysUser = getUserInformation();

        Landing landing = landingRepository.findOne(id);
        if (landing == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak dapat ditemukan");

        if (!landing.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf, organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        Translator3rdParty rdParty = new Translator3rdParty();
        return rdParty.deTransformLanding(landing);
    }


    public Operational3rdPartyDTO getOperasionalById3rdPartyData(String id) {
        SysUser sysUser = getUserInformation();

        Operational operational = operationalRepository.findOne(id);
        if (operational == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        if (!operational.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        Translator3rdParty rdParty = new Translator3rdParty();
        return rdParty.deTransformOperational(operational);
    }


    public BiologyOnSize3rdPartyDTO getUkuranById3rdPartyData(String id) {
        SysUser sysUser = getUserInformation();

        BiologyOnSize size = sizeRepository.findOne(id);
        if (size == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        if (!size.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        Translator3rdParty rdParty = new Translator3rdParty();
        return rdParty.deTransformSize(size);
    }


    public BiologyOnReproduction3rdPartyDTO getReproduksiById3rdPartyData(String id) {
        SysUser sysUser = getUserInformation();

        BiologyOnReproduction reproduction = reproductionRepository.findOne(id);
        if (reproduction == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        if (!reproduction.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        Translator3rdParty rdParty = new Translator3rdParty();
        return rdParty.deTransformReproduction(reproduction);
    }


    public Delete3rdPartyResponse deleteById3rdPartyData(String id) {
        SysUser sysUser = getUserInformation();

        Landing landing = landingRepository.findOne(id);
        if (landing == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        if (!landing.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        landingRepository.delete(id);
        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public Delete3rdPartyResponse deleteOperasionalById3rdPartyData(String id) {
        SysUser sysUser = getUserInformation();

        Operational operational = operationalRepository.findOne(id);
        if (operational == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        if (!operational.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        operationalRepository.delete(id);
        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public Delete3rdPartyResponse deleteUkuranById3rdPartyData(String id) {
        SysUser sysUser = getUserInformation();

        BiologyOnSize size = sizeRepository.findOne(id);
        if (size == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        if (!size.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        sizeRepository.delete(id);
        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }


    public Delete3rdPartyResponse deleteReproduksiById3rdPartyData(String id) {
        SysUser sysUser = getUserInformation();

        BiologyOnReproduction reproduction = reproductionRepository.findOne(id);
        if (reproduction == null)
            throw new ResourceInternalServerErrorException("Data dengan ID " + id + ", tidak ditemukan!!");

        if (!reproduction.getOrganisasi().trim().toUpperCase().equals(sysUser.getOrganisasi().trim().toUpperCase())) {
            throw new ResourceInternalServerErrorException("Maaf tidak dapat dihapus, karena organisasi pemilik dari data ini bukan dari " + sysUser.getOrganisasi().toUpperCase());
        }

        reproductionRepository.delete(id);
        return Delete3rdPartyResponse.builder().message("Data dengan ID '" + id + "', berhasil dihapus dari database." ).build();
    }



}
