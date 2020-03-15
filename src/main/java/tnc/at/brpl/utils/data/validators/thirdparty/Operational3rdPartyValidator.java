package tnc.at.brpl.utils.data.validators.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.models.main.OperationalCatchDetails;
import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.models.main.dto.OperationalCatchDetails3rdPartyDTO;
import tnc.at.brpl.models.main.dto.OperationalOnFishingToolSpecification3rdPartyDTO;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.OperationalCatchDetailsRepository;
import tnc.at.brpl.repositories.main.OperationalRepository;
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
public class Operational3rdPartyValidator {

    private static OperationalRepository operationalRepository;

    private static OperationalCatchDetailsRepository operationalCatchDetailsRepository;

    private static SysUserRepository sysUserRepository;

    @Autowired
    private OperationalRepository operationalRepositoryInjecter;

    @Autowired
    private OperationalCatchDetailsRepository operationalCatchDetailsRepositoryInjecter;

    @Autowired
    private SysUserRepository sysUserRepositoryInjecter;

    @PostConstruct
    private void init() {
        operationalCatchDetailsRepository = operationalCatchDetailsRepositoryInjecter;
        operationalRepository = operationalRepositoryInjecter;
        sysUserRepository = sysUserRepositoryInjecter;
    }

    public Operational3rdPartyValidator() {
    }

    private static String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    private SysUser getUserInformation() {
        return sysUserRepository.findByPengguna(getUsername());
    }


    public static List<String> validateOperational3rdParty(Operational3rdPartyDTO operational3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        /* verify Lokasi Pendaratan */
        if (Shared.isStringNullOrEmpty(operational3rdPartyDTO.getNamaLokasiPendaratan()))
            errorMessage.add("Nama Lokasi Pendaratan pada data Operasional tidak boleh kosong.");

        /* verify nama pencatat */
        if (Shared.isStringNullOrEmpty((operational3rdPartyDTO.getNamaPencatat())))
            errorMessage.add("Nama Enumerator pada data Pendaratan tidak boleh kosong.");

        /* verify sumberdaya */
        if (Shared.isStringNullOrEmpty((operational3rdPartyDTO.getNamaSumberDaya())))
            errorMessage.add("Ada Sumber Daya yang tidak valid...");

        /* verify sumberdaya again */
        if (!Shared.isStringNullOrEmpty((operational3rdPartyDTO.getNamaSumberDaya())) && !ValidatorUtil.sumberdayaValid(operational3rdPartyDTO.getNamaSumberDaya()))
            errorMessage.add("Data Operasional, dengan sumber daya '" + operational3rdPartyDTO.getNamaSumberDaya() + "' yang tidak valid dengan ketentuan BRPL, " +
                    "Silahkan lihat kembali daftar sumber daya yang diakomodir oleh BRPL pada API sumber daya. ");

        /* verify wpp */
        if (Shared.isStringNullOrEmpty((operational3rdPartyDTO.getWpp())))
            errorMessage.add("Ada WPP yang tidak valid...");

        /* verify wpp again */
        if (!Shared.isStringNullOrEmpty((operational3rdPartyDTO.getWpp())) && !ValidatorUtil.wppValid(operational3rdPartyDTO.getWpp()))
            errorMessage.add("Pendaratan ini memiliki WPP yang tidak valid");

        /*verify waktu/tanggal pendaratan */
        if (operational3rdPartyDTO.getTanggalSampling() == null)
            errorMessage.add("Tanggal sampling untuk data operasioanl ini tidak boleh kosong.");

//        if (operational3rdPartyDTO.getJamSampling() == null)
//            errorMessage.add("Jam sampling untuk data pendaratan ini tidak boleh kosong.");

        if (Shared.isStringNullOrEmpty(operational3rdPartyDTO.getNamaKapal()))
            errorMessage.add("Mohon inputkan nama kapal yang benar");

        if (Shared.isStringNullOrEmpty(operational3rdPartyDTO.getNamaPemilikKapal()))
            errorMessage.add("Nama Pemilik Kapal masih kosong");

        if (operational3rdPartyDTO.isKapalAndon()) {
            if (Shared.isStringNullOrEmpty(operational3rdPartyDTO.getAsalKapalAndon()))
                errorMessage.add("Jika kapal ini andon, maka inputkan asal kapal andon ini. ");
        }

//        if (Shared.isStringNullOrEmpty(operational3rdPartyDTO.getPelabuhanAsal()))
//            errorMessage.add("Pastikan anda menginputkan pelabuhan asal kapal");

        if (operational3rdPartyDTO.getJumlahAbk() < 0)
            errorMessage.add("Jumlah ABK tidak boleh kurang dari 0");

        if (operational3rdPartyDTO.getPanjangKapal() <= 0)
            errorMessage.add("Pastikan anda menginputkan panjang kapal yang benar (panjang > 0 )");

        if (operational3rdPartyDTO.getBobotKapal() <= 0)
            errorMessage.add("Bobot kapal yang anda inputkan tidak dapat diproses");

        if (operational3rdPartyDTO.getMesinUtama() <= 0)
            errorMessage.add("Daya Mesin utama tidak dapat diproses");

        if (operational3rdPartyDTO.getKapasitasPalkaBoks() < 0)
            errorMessage.add("Data kapasitas palka/ boks tidak dapat diproses");

        if (Shared.isStringNullOrEmpty(operational3rdPartyDTO.getNamaAlatTangkap()))
            errorMessage.add("Alat tangkap tidak boleh kosong. ");

        if (!ValidatorUtil.alatTangkapValid(Shared.verifyString(operational3rdPartyDTO.getNamaSumberDaya()), operational3rdPartyDTO.getNamaAlatTangkap()))
            errorMessage.add("Alat tangkap \'" + Shared.verifyString(operational3rdPartyDTO.getNamaAlatTangkap(), true) +
                    "', tidak diakomodir untuk Sumber Daya '" + Shared.verifyString(operational3rdPartyDTO.getNamaSumberDaya(), true) + "'." +
                    "Silahkan lihat kembali daftar alat tangkap yang diakomodir oleh BRPL pada API alat tangkap. ");


        if (operational3rdPartyDTO.getJumlahAlatTangkapYangDioperasikan() < 0)
            errorMessage.add("Pastikan jumlah alat yang dioperasikan terinput dengan benar");

        if (Shared.isStringNullOrEmpty(operational3rdPartyDTO.getDaerahPenangkapan()))
            errorMessage.add("Silahkan inputkasn Grid Daerah Penangkapan yang benar.");

        if (operational3rdPartyDTO.getJumlahSetting() < 0)
            errorMessage.add("Pastikan anda menginputkan jumlah setting yang benar");

        if (operational3rdPartyDTO.getJumlahHariPerTrip() <= 0)
            errorMessage.add("Terdapat kesalahan pada jumlah hari per trip (harus > 0).");

        if (operational3rdPartyDTO.getJumlahHariMenangkap() < 0)
            errorMessage.add("Terdapat kesalahan pada jumlah hari menangkap tidak boleh kurang dari 0");

//        if (Shared.isStringNullOrEmpty(operational3rdPartyDTO.getWaktuMemancing()))
//            errorMessage.add("Tentukan waktu pemancingan");

        if (Shared.isStringNullOrEmpty(operational3rdPartyDTO.getSumberInformasi()))
            errorMessage.add("Pastikan sumber informasi data diketahui");

        /* verify apakah ada spesifikasi atau tidak */
        if (ValidatorUtil.alatTangkapValid(Shared.verifyString(operational3rdPartyDTO.getNamaSumberDaya()), operational3rdPartyDTO.getNamaAlatTangkap()) && operational3rdPartyDTO.getDataSpesifikasiAlatTangkap().isEmpty())
            errorMessage.add("Pastikan spesifikasi alat tangkap ada.");

        /* verify apakah detail informasi tangkapan ada atau tidak */
        if (operational3rdPartyDTO.getJumlahHariMenangkap() > 0 && operational3rdPartyDTO.getDataOperasionalDetailTangkapan().isEmpty())
            errorMessage.add("Rincian detail penangkapan tidak ada. ");

        if (operational3rdPartyDTO.getStatusDokumen() == ThirdPartyDocumentStatus.WAITING || operational3rdPartyDTO.getStatusDokumen() == ThirdPartyDocumentStatus.VERIFIED) {
        } else {
            errorMessage.add("Ada data dengan status dokumen '" + operational3rdPartyDTO.getStatusDokumen().toString() + "', tidak dapat diproses. Silahkan gunakan status dokumen 'VERIVIED' atau 'WAITING'");
        }

        return errorMessage;
    }

    public static List<String> validateOperationalGearSpecification3rdParty(OperationalOnFishingToolSpecification3rdPartyDTO operationalOnFishingToolSpecification3rdPartyDTO,
                                                                            Operational3rdPartyDTO operational3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();
        String extend = operational3rdPartyDTO == null ? "(Data operasional untuk kapal " + Shared.verifyString(operational3rdPartyDTO.getNamaKapal(), true) + ")." : "";

        if (operationalOnFishingToolSpecification3rdPartyDTO == null)
            return errorMessage;

        if (Shared.isStringNullOrEmpty(operationalOnFishingToolSpecification3rdPartyDTO.getSpesifikasi()))
            errorMessage.add("Ada spesifikasi Alat Tangkap yang kosong. " + extend);

        return errorMessage;
    }


    public static List<String> validateOperationalCatchDetail3rdParty(OperationalCatchDetails3rdPartyDTO operationalCatchDetails3rdPartyDTO,
                                                                      Operational3rdPartyDTO operational3rdPartyDTO,
                                                                      boolean insert,
                                                                      boolean existOnParent) {
        List<String> errorMessage = new ArrayList<>();
        String extend = operational3rdPartyDTO == null ? "(Data operasional untuk kapal " + Shared.verifyString(operational3rdPartyDTO.getNamaKapal(), true) + ")." : "";
        String id = operationalCatchDetails3rdPartyDTO.getId();

        if (!Shared.isStringNullOrEmpty(id)) {
            OperationalCatchDetails checkResult = operationalCatchDetailsRepository.findOne(id);
            if (insert) {
                if (checkResult != null)
                    errorMessage.add("Data Detail Tangkapan Operasional dengan ID " + id + " tidak dapat diproses karena sudah ada di Sistem e-BRPL");
            } else {
                if (checkResult == null) {
                    errorMessage.add("Data Detail Tangkapan Operasional dengan ID " + id + " tidak dapat diproses karena tidak ditemukan di Sistem e-BRPL");
                } else { // is update and not null checkResult
                    if (!existOnParent)
                        errorMessage.add("Data Detail Tangkapan Operasional dengan ID " + id + " tidak dapat diproses karena sudah digunakan di data yang lain");
                }
            }
        }

        if (Shared.isStringNullOrEmpty(operationalCatchDetails3rdPartyDTO.getNamaSpesies()) && (operationalCatchDetails3rdPartyDTO.getTotalBeratEkor() > 0 || operationalCatchDetails3rdPartyDTO.getTotalBeratKg() > 0))
            errorMessage.add("Ada nama spesies ikan yang kosong pada data detail tangkapan Operasional. " + extend);

        if (operationalCatchDetails3rdPartyDTO.getTotalBeratEkor() <= 0 && operationalCatchDetails3rdPartyDTO.getTotalBeratKg() <= 0)
            errorMessage.add("Ada ukuran ikan yang tidak benar pada data detail tangkapan Operasional. " + extend);

        return errorMessage;
    }


    public static List<String> validate(Operational3rdPartyDTO operational3rdPartyDTO, boolean haveRelationWithParent, boolean insert, boolean existOnParent) {
        List<String> errorMessage = new ArrayList<>();
        String id = operational3rdPartyDTO.getId();

        /* validate operational header */
        errorMessage.addAll(validateOperational3rdParty(operational3rdPartyDTO));

        /* cek data berdasarkan ID, apakah sudah ada di database atau belum */
        Operational checkResult = null;
        if (Shared.isStringNullOrEmpty(id)) {
            errorMessage.add("Data Operasional ini tidak memiliki ID");
        } else {
            checkResult = operationalRepository.findOne(TranslatorUser3rdParty.encodeId(id));
            if (checkResult == null)
                checkResult = operationalRepository.findOne(id);

            if (insert) {
                if (checkResult != null)
                    errorMessage.add("Data Operasional dengan ID " + id + " tidak dapat diproses karena sudah ada di Sistem e-BRPL");
            } else {
                if (checkResult == null) {
                    errorMessage.add("Data Operasional dengan ID " + id + " tidak dapat diproses karena tidak ditemukan di Sistem e-BRPL");
                } else { // is update and not null b
                    if (haveRelationWithParent && !existOnParent)
                        errorMessage.add("Data Operasional dengan ID " + id + " tidak dapat diproses karena sudah digunakan di data trip yang lain");
                }
            }
        }

        if (!insert && checkResult == null)
            return errorMessage;

        if (!insert) {
            SysUser user = TranslatorUser3rdParty.getUserInformation();
            if (!checkResult.getOrganisasi().trim().toUpperCase().equals(user.getOrganisasi().trim().toUpperCase()))
                errorMessage.add("Maaf tidak dapat diubah, karena organisasi pemilik dari data ini bukan dari " + user.getOrganisasi());
        }

        final Operational existing = checkResult;

        List<String> errorsOnSpecs = IntStream.range(0, operational3rdPartyDTO.getDataSpesifikasiAlatTangkap().size())
                .mapToObj(index -> validateOperationalGearSpecification3rdParty(operational3rdPartyDTO.getDataSpesifikasiAlatTangkap().get(index), operational3rdPartyDTO))
                .flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnSpecs);

        List<String> errorsOnDetail = IntStream.range(0, operational3rdPartyDTO.getDataOperasionalDetailTangkapan().size())
                .mapToObj(index -> {
                    OperationalCatchDetails3rdPartyDTO details3rdPartyDTO = operational3rdPartyDTO.getDataOperasionalDetailTangkapan().get(index);
                    boolean existOnHisParent = existing != null && !Shared.isStringNullOrEmpty(details3rdPartyDTO.getId())
                            && existing.getDataOperasionalDetailTangkapan().stream()
                            .anyMatch(dto -> dto.getUuid().equals(details3rdPartyDTO.getId()) || dto.getUuid().equals(TranslatorUser3rdParty.encodeId(details3rdPartyDTO.getId())));
                    return validateOperationalCatchDetail3rdParty(details3rdPartyDTO, operational3rdPartyDTO, insert, existOnHisParent);
                }).flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnDetail);

        return errorMessage;
    }


}
