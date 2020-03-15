package tnc.at.brpl.utils.data.validators.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.LandingCatchDetail;
import tnc.at.brpl.models.main.LandingDetail;
import tnc.at.brpl.models.main.dto.*;
import tnc.at.brpl.repositories.main.LandingCatchDetailsRepository;
import tnc.at.brpl.repositories.main.LandingDetailRepository;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.utils.data.ThirdPartyDocumentStatus;
import tnc.at.brpl.utils.data.validators.ValidatorUtil;
import tnc.at.brpl.utils.other.Shared;
import tnc.at.brpl.utils.thirdparty.TranslatorUser3rdParty;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Landing3rdPartyValidator {

    private static LandingRepository landingRepository;

    private static LandingDetailRepository landingDetailRepository;

    private static LandingCatchDetailsRepository landingCatchDetailsRepository;

    @Autowired
    private LandingRepository landingRepositoryInjecter;

    @Autowired
    private LandingDetailRepository landingDetailRepositoryInjecter;

    @Autowired
    private LandingCatchDetailsRepository landingCatchDetailsRepositoryInjecter;


    @PostConstruct
    private void init() {
        landingRepository = landingRepositoryInjecter;
        landingDetailRepository = landingDetailRepositoryInjecter;
        landingCatchDetailsRepository = landingCatchDetailsRepositoryInjecter;
    }

    public Landing3rdPartyValidator() {
    }

    /**
     * @param landing3rdPartyDTO Data Landing on Mitra Point of View
     * @return List<String>
     */
    public static List<String> validateLanding3rdParty(Landing3rdPartyDTO landing3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

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
            errorMessage.add("Ada data dengan status dokumen '" + landing3rdPartyDTO.getStatusDokumen().toString() + "', tidak dapat diproses. Silahkan gunakan status dokumen 'VERIVIED' atau 'WAITING'");
        }

        return errorMessage;
    }


    /**
     * @param landingDetail3rdPartyDTO Data Landing Detail on Mitra Point of View
     * @param index                    default index -1, (Index detail kapal di form pendaratan
     * @return @return List<String>
     */
    public static List<String> validateLandingDetail3rdParty(LandingDetail3rdPartyDTO landingDetail3rdPartyDTO, int index, String sumberDaya, boolean insert, boolean existOnParent) {
        List<String> errorMessage = new ArrayList<>();
        String extend = index > -1 ? "(Rincian pendaratan urutan ke-" + (index + 1) + " untuk Kapal "
                + Shared.verifyString(landingDetail3rdPartyDTO.getNamaKapal(), true) + ")." : "";
        String id = landingDetail3rdPartyDTO.getId();

        LandingDetail checkResult = null;
        if (Shared.isStringNullOrEmpty(id)) {
            errorMessage.add("Data Detail Pendaratan ini tidak memiliki ID");
        } else {
            checkResult = landingDetailRepository.findOne(TranslatorUser3rdParty.encodeId(id));
            if (checkResult == null)
                checkResult = landingDetailRepository.findOne(id);

            if (insert) {
                if (checkResult != null)
                    errorMessage.add("Data Detail Pendaratan dengan ID " + id + " tidak dapat diproses karena sudah ada di Sistem e-BRPL");
            } else {
                if (checkResult == null) {
                    errorMessage.add("Data Detail Pendaratan dengan ID " + id + " tidak dapat diproses karena tidak ditemukan di Sistem e-BRPL");
                } else { // is update and not null checkResult
                    if (!existOnParent)
                        errorMessage.add("Data Detail Pendaratan dengan ID " + id + " tidak dapat diproses karena sudah digunakan di data trip yang lain");
                }
            }
        }

        if (Shared.isStringNullOrEmpty(landingDetail3rdPartyDTO.getNamaKapal()))
            errorMessage.add("Mohon inputkan nama kapal yang benar. " + extend);

        if (Shared.isStringNullOrEmpty(landingDetail3rdPartyDTO.getNamaAlatTangkap()))
            errorMessage.add("Alat tangkap tidak boleh kosong. " + extend);

        if (!ValidatorUtil.alatTangkapValid(Shared.verifyString(sumberDaya), landingDetail3rdPartyDTO.getNamaAlatTangkap()))
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
     * @param landingDetail3rdPartyDTO
     * @return List<String>
     */
    public static List<String> validateLandingCatchDetail3rdParty(LandingCatchDetail3rdPartyDTO landingCatchDetail3rdPartyDTO, LandingDetail3rdPartyDTO landingDetail3rdPartyDTO, boolean insert, boolean existOnParent) {
        List<String> errorMessage = new ArrayList<>();
        String extend = landingDetail3rdPartyDTO == null ? "(Rincian pendaratan untuk kapal " + Shared.verifyString(landingDetail3rdPartyDTO.getNamaKapal(), true) + ")." : "";

        String id = landingCatchDetail3rdPartyDTO.getId();
        if (!Shared.isStringNullOrEmpty(id)) {
            LandingCatchDetail checkResult = landingCatchDetailsRepository.findOne(id);
            if (insert) {
                if (checkResult != null)
                    errorMessage.add("Data Tangkapan Ukuran dengan ID " + id + " tidak dapat diproses karena sudah ada di Sistem e-BRPL");
            } else {
                if (checkResult == null) {
                    errorMessage.add("Data Tangkapan dengan ID " + id + " tidak dapat diproses karena tidak ditemukan di Sistem e-BRPL");
                } else { // is update and not null checkResult
                    if (!existOnParent)
                        errorMessage.add("Data Tangkapan dengan ID " + id + " tidak dapat diproses karena sudah digunakan di data yang lain");
                }
            }
        }

        if (Shared.isStringNullOrEmpty(landingCatchDetail3rdPartyDTO.getNamaSpesies()) && (landingCatchDetail3rdPartyDTO.getTangkapanIndividu() > 0 || landingCatchDetail3rdPartyDTO.getTangkapanVolume() > 0))
            errorMessage.add("Ada nama spesies yang kosong. " + extend);

        if (landingCatchDetail3rdPartyDTO.getTangkapanIndividu() <= 0 || landingCatchDetail3rdPartyDTO.getTangkapanVolume() <= 0)
            errorMessage.add("Ada jumlah detail tangkapan ikan yang tidak benar. " + extend);

        return errorMessage;
    }


    public static List<String> validateAll(Landing3rdPartyDTO landing3rdPartyDTO, boolean insert) {
        List<String> errorMessage = new ArrayList<>();

        /* validate landing */
        errorMessage.addAll(validateLanding3rdParty(landing3rdPartyDTO));
        String id = landing3rdPartyDTO.getId();

        /* cek data berdasarkan ID, apakah sudah ada di database atau belum */
        Landing checkResult = null;
        if (Shared.isStringNullOrEmpty(id)) {
            errorMessage.add("Data Pendaratan ini tidak memiliki ID");
        } else {
            checkResult = landingRepository.findOne(TranslatorUser3rdParty.encodeId(id));
            if (checkResult == null)
                checkResult = landingRepository.findOne(id);

            if (checkResult != null) {
                if (insert)
                    errorMessage.add("Data Pendaratan dengan ID " + id + " tidak dapat diproses karena sudah ada di Sistem e-BRPL");
            } else {
                if (!insert)
                    errorMessage.add("Data Pendaratan dengan ID " + id + " tidak dapat diproses karena tidak ditemukan di Sistem e-BRPL");
            }
        }

        if (!insert && checkResult == null)
            return errorMessage;

        if (!insert) {
            SysUser user = TranslatorUser3rdParty.getUserInformation();
            if (!checkResult.getOrganisasi().trim().toUpperCase().equals(user.getOrganisasi().trim().toUpperCase()))
                errorMessage.add("Maaf tidak dapat diubah, karena organisasi pemilik dari data ini bukan dari " + user.getOrganisasi());
        }

        final Landing existingLanding = checkResult;

        /* validate landing detail, Landing Catch Detail */
        List<String> errorsOnDetailAndCatch = IntStream.range(0, landing3rdPartyDTO.getDataRincianPendaratan().size())
                .mapToObj(index -> {
                    List<String> error = new ArrayList<>();
                    LandingDetail3rdPartyDTO landingDetail3rdPartyDTO = landing3rdPartyDTO.getDataRincianPendaratan().get(index);
                    boolean detailExistOnParent = existingLanding != null && existingLanding.getDataRincianPendaratan().stream()
                            .anyMatch(dto -> dto.getUuid().equals(landingDetail3rdPartyDTO.getId()) || dto.getUuid().equals(TranslatorUser3rdParty.encodeId(landingDetail3rdPartyDTO.getId())));
                    error.addAll(validateLandingDetail3rdParty(landingDetail3rdPartyDTO, index, landing3rdPartyDTO.getNamaSumberDaya(), insert, detailExistOnParent));

                    /* validate landing catch detail */
                    final LandingDetail existingLandingDetail = existingLanding != null ? existingLanding.getDataRincianPendaratan().get(index) : null;
                    error.addAll(IntStream.range(0, landingDetail3rdPartyDTO.getDataRincianTangkapanPendaratan().size())
                            .mapToObj(j -> {
                                LandingCatchDetail3rdPartyDTO catchDetail = landingDetail3rdPartyDTO.getDataRincianTangkapanPendaratan().get(j);
                                boolean catchExistOnHisParent = existingLandingDetail != null && !Shared.isStringNullOrEmpty(catchDetail.getId())
                                        && existingLandingDetail.getDataRincianTangkapanPendaratan().stream()
                                        .anyMatch(dto -> dto.getUuid().equals(catchDetail.getId()) || dto.getUuid().equals(TranslatorUser3rdParty.encodeId(catchDetail.getId())));

                                return validateLandingCatchDetail3rdParty(catchDetail, landingDetail3rdPartyDTO, insert, catchExistOnHisParent);
                            })
                            .flatMap(strings -> strings.stream())
                            .collect(Collectors.toList()));

                    return error;
                }).flatMap(strings -> strings.stream()).collect(Collectors.toList());


        /* add to list of error */
        errorMessage.addAll(errorsOnDetailAndCatch);
        /**/

        /* validate Operational */
        List<String> errorsOnOperatinal = IntStream.range(0, landing3rdPartyDTO.getDataOperasional().size())
                .mapToObj(index -> {
                    Operational3rdPartyDTO operational3rdPartyDTO = landing3rdPartyDTO.getDataOperasional().get(index);
                    boolean existOnParent = existingLanding != null && existingLanding.getDataOperasional().stream()
                            .anyMatch(dto -> dto.getUuid().equals(operational3rdPartyDTO.getId()) || dto.getUuid().equals(TranslatorUser3rdParty.encodeId(operational3rdPartyDTO.getId())));
                    return Operational3rdPartyValidator.validate(operational3rdPartyDTO, true, insert, existOnParent);
                }).flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnOperatinal);

        /* validate Biology Size */
        List<String> errorsOnSize = IntStream.range(0, landing3rdPartyDTO.getDataUkuran().size())
                .mapToObj(index -> {
                    BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO = landing3rdPartyDTO.getDataUkuran().get(index);
                    boolean existOnParent = existingLanding != null && existingLanding.getDataOperasional().stream()
                            .anyMatch(dto -> dto.getUuid().equals(biologyOnSize3rdPartyDTO.getId()) || dto.getUuid().equals(TranslatorUser3rdParty.encodeId(biologyOnSize3rdPartyDTO.getId())));
                    return BiologyOnSize3rdPartyValidator.validate(biologyOnSize3rdPartyDTO, true, insert, existOnParent);
                }).flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnSize);

        /* validate Biology Repro */
        List<String> errorsOnReproduction = IntStream.range(0, landing3rdPartyDTO.getDataReproduksi().size())
                .mapToObj(index -> {
                    BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO = landing3rdPartyDTO.getDataReproduksi().get(index);
                    boolean existOnParent = existingLanding != null && existingLanding.getDataOperasional().stream()
                            .anyMatch(dto -> dto.getUuid().equals(biologyOnReproduction3rdPartyDTO.getId()) || dto.getUuid().equals(TranslatorUser3rdParty.encodeId(biologyOnReproduction3rdPartyDTO.getId())));
                    return BiologyOnReproduction3rdPartyValidator.validate(biologyOnReproduction3rdPartyDTO, true, insert, existOnParent);
                }).flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnReproduction);

        return errorMessage;
    }


}
