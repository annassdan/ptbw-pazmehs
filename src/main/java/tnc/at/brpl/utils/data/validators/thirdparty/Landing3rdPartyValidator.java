package tnc.at.brpl.utils.data.validators.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.dto.Landing3rdPartyDTO;
import tnc.at.brpl.models.main.dto.LandingCatchDetail3rdPartyDTO;
import tnc.at.brpl.models.main.dto.LandingDetail3rdPartyDTO;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.utils.data.ThirdPartyDocumentStatus;
import tnc.at.brpl.utils.data.validators.ValidatorUtil;
import tnc.at.brpl.utils.other.Shared;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Landing3rdPartyValidator {

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

    public Landing3rdPartyValidator() {
    }

    private static String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    /**
     * @param landing3rdPartyDTO Data Landing on Mitra Point of View
     * @return List<String>
     */
    public static List<String> validateLanding3rdParty(Landing3rdPartyDTO landing3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        /* cek data berdasarkan ID, apakah sudah ada di database atau belum */
        if (Shared.isStringNullOrEmpty(landing3rdPartyDTO.getId())) {
            errorMessage.add("Data Pendaratan ini tidak memiliki ID");
        } else {
            Landing checkResult = landingRepository.findOne(landing3rdPartyDTO.getId());
            if (checkResult != null)
                errorMessage.add("Data dengan ID " + landing3rdPartyDTO.getId() +
                        " Pendaratan sudah ada di Sistem e-BRPL, Silahkan lakukan proses update jika ada perubahan pada data ini.");
        }

        /* verify Lokasi Pendaratan */
        if (Shared.isStringNullOrEmpty(landing3rdPartyDTO.getNamaLokasiPendaratan()))
            errorMessage.add("Nama Lokasi Pendaratan pada data Pendaratan tidak boleh kosong.");

        /* verify nama pencatat */
        if (Shared.isStringNullOrEmpty((landing3rdPartyDTO.getNamaPencatat())))
            errorMessage.add("Nama Enumerator pada data Pendaratan tidak boleh kosong.");

        /* verify sumberdaya */
        if (Shared.isStringNullOrEmpty((landing3rdPartyDTO.getNamaSumberDaya())))
            errorMessage.add("Ada Sumber Daya yang tidak valid...");

        /* verify sumberdaya again */
        if (!Shared.isStringNullOrEmpty((landing3rdPartyDTO.getNamaSumberDaya())) && !ValidatorUtil.sumberdayaValid(landing3rdPartyDTO.getNamaSumberDaya()))
            errorMessage.add("Data Pendaratan, dengan sumber daya '" + landing3rdPartyDTO.getNamaSumberDaya() + "' yang tidak valid dengan ketentuan BRPL, " +
                    "Silahkan lihat kembali daftar sumber daya yang diakomodir oleh BRPL pada API sumber daya. ");

        /* verify wpp */
        if (Shared.isStringNullOrEmpty((landing3rdPartyDTO.getWpp())))
            errorMessage.add("Ada WPP yang tidak valid...");

        /* verify wpp again */
        if (!Shared.isStringNullOrEmpty((landing3rdPartyDTO.getWpp())) && !ValidatorUtil.wppValid(landing3rdPartyDTO.getWpp()))
            errorMessage.add("Pendaratan ini memiliki WPP yang tidak valid");

        /*verify waktu/tanggal pendaratan */
        if (landing3rdPartyDTO.getTanggalPendaratan() == null)
            errorMessage.add("Tanggal Pendaratan untuk data pendaratan ini tidak boleh kosong.");

        if (landing3rdPartyDTO.getDataRincianPendaratan().isEmpty())
            errorMessage.add("Data ini tidak mempunyai rincian kapal pendaratan");

        if (landing3rdPartyDTO.getStatusDokumen() == ThirdPartyDocumentStatus.WAITING || landing3rdPartyDTO.getStatusDokumen() == ThirdPartyDocumentStatus.VERIFIED) {
        } else {
            errorMessage.add("Ada data dengan status dokumen '"+ landing3rdPartyDTO.getStatusDokumen().toString() +"', tidak dapat diproses. Silahkan gunakan status dokumen 'VERIVIED' atau 'WAITING'");
        }

        return errorMessage;
    }


    public static List<String> validateLandingDetail3rdParty(LandingDetail3rdPartyDTO landingDetail3rdPartyDTO) {
        return validateLandingDetail3rdParty(landingDetail3rdPartyDTO, -1, null);
    }

    /**
     * @param landingDetail3rdPartyDTO Data Landing Detail on Mitra Point of View
     * @param index                    default index -1, (Index detail kapal di form pendaratan
     * @return @return List<String>
     */
    public static List<String> validateLandingDetail3rdParty(LandingDetail3rdPartyDTO landingDetail3rdPartyDTO, int index, String sumberDaya) {
        List<String> errorMessage = new ArrayList<>();
        String extend = index > -1 ? "(Rincian pendaratan urutan ke-" + (index + 1) + " untuk Kapal "
                + Shared.verifyString(landingDetail3rdPartyDTO.getNamaKapal(), true) + ")." : "";

        if (Shared.isStringNullOrEmpty(landingDetail3rdPartyDTO.getNamaKapal()))
            errorMessage.add("Mohon inputkan nama kapal yang benar. " + extend);

        if (Shared.isStringNullOrEmpty(landingDetail3rdPartyDTO.getNamaAlatTangkap()))
            errorMessage.add("Alat tangkap tidak boleh kosong. " + extend);

        if (!ValidatorUtil.alatTangkapValid(Shared.verifyString(sumberDaya)))
            errorMessage.add("Alat tangkap \'" + Shared.verifyString(landingDetail3rdPartyDTO.getNamaAlatTangkap(), true) +
                    "', tidak diakomodir untuk Sumber Daya '" + sumberDaya + "'." +
                    "Silahkan lihat kembali daftar alat tangkap yang diakomodir oleh BRPL pada API alat tangkap. " + extend);

        if (Shared.isStringNullOrEmpty(landingDetail3rdPartyDTO.getDaerahPenangkapan()))
            errorMessage.add("Silahkan inputkasn Grid Daerah Penangkapan yang benar. " + extend);

        if (landingDetail3rdPartyDTO.getJumlahHariPerTrip() < 1)
            errorMessage.add("Terdapat kesalahan pada jumlah hari per trip (harus > 0). " + extend);

        if (landingDetail3rdPartyDTO.getJumlahHariMenangkap() < 0)
            errorMessage.add("Terdapat kesalahan pada jumlah hari menangkap tidak boleh negatif. " + extend);

        if (landingDetail3rdPartyDTO.getJumlahHariMenangkap() > 0 && landingDetail3rdPartyDTO.getDataRincianTangkapanPendaratan().isEmpty())
            errorMessage.add("Rincian detail tangkapan tidak ada. " + extend);

        return errorMessage;
    }


    /**
     * @param landingCatchDetail3rdPartyDTO Data catch landing Detail on Mitra Point of View
     * @return List<String>
     */
    public static List<String> validateLandingCatchDetail3rdParty(LandingCatchDetail3rdPartyDTO landingCatchDetail3rdPartyDTO) {
        return validateLandingCatchDetail3rdParty(landingCatchDetail3rdPartyDTO, null);
    }


    /**
     * @param landingCatchDetail3rdPartyDTO Data catch landing Detail on Mitra Point of View
     * @param landingDetail3rdPartyDTO
     * @return List<String>
     */
    public static List<String> validateLandingCatchDetail3rdParty(LandingCatchDetail3rdPartyDTO landingCatchDetail3rdPartyDTO, LandingDetail3rdPartyDTO landingDetail3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();
        String extend = landingDetail3rdPartyDTO == null ? "(Rincian pendaratan untuk kapal " + Shared.verifyString(landingDetail3rdPartyDTO.getNamaKapal(), true) + ")." : "";

        if (Shared.isStringNullOrEmpty(landingCatchDetail3rdPartyDTO.getNamaSpesies()) && (landingCatchDetail3rdPartyDTO.getTangkapanIndividu() > 0 || landingCatchDetail3rdPartyDTO.getTangkapanVolume() > 0))
            errorMessage.add("Ada nama spesies yang kosong. " + extend);

        if (landingCatchDetail3rdPartyDTO.getTangkapanIndividu() <= 0 || landingCatchDetail3rdPartyDTO.getTangkapanVolume() <= 0)
            errorMessage.add("Ada jumlah detail tangkapan ikan yang tidak benar. " + extend);

        return errorMessage;
    }

    /**
     * @param landing3rdPartyDTO Only Landing data from Mitra Point of View
     * @return List<String>
     */
    public static List<String> validate(Landing3rdPartyDTO landing3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        /* validate landing header */
        errorMessage.addAll(validateLanding3rdParty(landing3rdPartyDTO));

        /* validate landing detail, Landing Catch Detail */
        List<String> errorsOnDetailAndCatch = IntStream.range(0, landing3rdPartyDTO.getDataRincianPendaratan().size())
                .mapToObj(index -> {
                    List<String> error = new ArrayList<>();
                    LandingDetail3rdPartyDTO landingDetail3rdPartyDTO = landing3rdPartyDTO.getDataRincianPendaratan().get(index);
                    error.addAll(validateLandingDetail3rdParty(landingDetail3rdPartyDTO, index, landing3rdPartyDTO.getNamaSumberDaya()));

                    /* validate landing catch detail */
                    error.addAll(IntStream.range(0, landingDetail3rdPartyDTO.getDataRincianTangkapanPendaratan().size())
                            .mapToObj(j -> validateLandingCatchDetail3rdParty(landingDetail3rdPartyDTO.getDataRincianTangkapanPendaratan().get(j), landingDetail3rdPartyDTO))
                            .flatMap(strings -> strings.stream())
                            .collect(Collectors.toList()));

                    return error;
                }).flatMap(strings -> strings.stream()).collect(Collectors.toList());


        /* add to list of error */
        errorMessage.addAll(errorsOnDetailAndCatch);

        return errorMessage;
    }


    public static List<String> validateAll(Landing3rdPartyDTO landing3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        /* validate landing */
        errorMessage.addAll(validate(landing3rdPartyDTO));

        /* validate Operational */
        List<String> errorsOnOperatinal = IntStream.range(0, landing3rdPartyDTO.getDataOperasional().size())
                .mapToObj(index -> Operational3rdPartyValidator.validate(landing3rdPartyDTO.getDataOperasional().get(index)))
                .flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnOperatinal);

        /* validate Biology Size */
        List<String> errorsOnSize = IntStream.range(0, landing3rdPartyDTO.getDataUkuran().size())
                .mapToObj(index -> BiologyOnSize3rdPartyValidator.validate(landing3rdPartyDTO.getDataUkuran().get(index)))
                .flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnSize);

        /* validate Biology Repro */
        List<String> errorsOnReproduction = IntStream.range(0, landing3rdPartyDTO.getDataReproduksi().size())
                .mapToObj(index -> BiologyOnReproduction3rdPartyValidator.validate(landing3rdPartyDTO.getDataReproduksi().get(index)))
                .flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnReproduction);

        return errorMessage;
    }


}
