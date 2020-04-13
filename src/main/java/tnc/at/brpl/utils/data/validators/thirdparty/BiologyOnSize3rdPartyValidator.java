package tnc.at.brpl.utils.data.validators.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.models.main.BiologyOnSizeDetail;
import tnc.at.brpl.models.main.BiologyOnSizeSampleDetail;
import tnc.at.brpl.models.main.dto.BiologyOnSize3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSizeDetail3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSizeSampleDetail3rdPartyDTO;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeDetailRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeSampleDetailRepository;
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
public class BiologyOnSize3rdPartyValidator {

    private static BiologyOnSizeRepository biologyOnSizeRepository;

    private static BiologyOnSizeDetailRepository biologyOnSizeDetailRepository;

    private static BiologyOnSizeSampleDetailRepository biologyOnSizeSampleRepository;

    private static SysUserRepository sysUserRepository;

    @Autowired
    private BiologyOnSizeRepository biologyOnSizeRepositoryInjecter;

    @Autowired
    private static BiologyOnSizeDetailRepository biologyOnSizeDetailRepositoryInjecter;

    @Autowired
    private static BiologyOnSizeSampleDetailRepository biologyOnSizeSampleRepositoryInjecter;

    @Autowired
    private SysUserRepository sysUserRepositoryInjecter;

    @PostConstruct
    private void init() {
        biologyOnSizeRepository = biologyOnSizeRepositoryInjecter;
        biologyOnSizeDetailRepository = biologyOnSizeDetailRepositoryInjecter;
        biologyOnSizeSampleRepository = biologyOnSizeSampleRepositoryInjecter;
        sysUserRepository = sysUserRepositoryInjecter;
    }

    public BiologyOnSize3rdPartyValidator() {
    }

    private static String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }


    public static List<String> validateSize3rdParty(BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        /* verify Lokasi Pendaratan */
        if (Shared.isStringNullOrEmpty(biologyOnSize3rdPartyDTO.getNamaLokasiSampling()))
            errorMessage.add("Nama Lokasi Sampling pada data Biologi Ukuran tidak boleh kosong.");

        /* verify nama pencatat */
        if (Shared.isStringNullOrEmpty((biologyOnSize3rdPartyDTO.getNamaPencatat())))
            errorMessage.add("Nama Enumerator pada data Pendaratan tidak boleh kosong.");

        /* verify sumberdaya */
        if (Shared.isStringNullOrEmpty(biologyOnSize3rdPartyDTO.getNamaSumberDaya()))
            errorMessage.add("Ada Sumber Daya yang tidak valid...");

        /* verify sumberdaya again */
        if (!Shared.isStringNullOrEmpty((biologyOnSize3rdPartyDTO.getNamaSumberDaya())) && !ValidatorUtil.sumberdayaValid(biologyOnSize3rdPartyDTO.getNamaSumberDaya()))
            errorMessage.add("Data Operasional, dengan sumber daya '" + biologyOnSize3rdPartyDTO.getNamaSumberDaya() + "' yang tidak valid dengan ketentuan BRPL, " +
                    "Silahkan lihat kembali daftar sumber daya yang diakomodir oleh BRPL pada API sumber daya. ");

        /* verify wpp */
        if (Shared.isStringNullOrEmpty((biologyOnSize3rdPartyDTO.getWpp())))
            errorMessage.add("Ada WPP yang tidak valid...");

        /* verify wpp again */
        if (!Shared.isStringNullOrEmpty((biologyOnSize3rdPartyDTO.getWpp())) && !ValidatorUtil.wppValid(biologyOnSize3rdPartyDTO.getWpp()))
            errorMessage.add("Pendaratan ini memiliki WPP yang tidak valid");

        /*verify waktu/tanggal pendaratan */
        if (biologyOnSize3rdPartyDTO.getTanggalSampling() == null)
            errorMessage.add("Tanggal sampling untuk data operasioanl ini tidak boleh kosong.");

        if (Shared.isStringNullOrEmpty(biologyOnSize3rdPartyDTO.getNamaKapal()))
            errorMessage.add("Mohon inputkan nama kapal yang benar");

        if (!ValidatorUtil.alatTangkapValid(Shared.verifyString(biologyOnSize3rdPartyDTO.getNamaSumberDaya()), biologyOnSize3rdPartyDTO.getNamaAlatTangkap()))
            errorMessage.add("Alat tangkap \'" + Shared.verifyString(biologyOnSize3rdPartyDTO.getNamaAlatTangkap(), true) +
                    "', tidak diakomodir untuk Sumber Daya '" + Shared.verifyString(biologyOnSize3rdPartyDTO.getNamaSumberDaya(), true) + "'." +
                    "Silahkan lihat kembali daftar alat tangkap yang diakomodir oleh BRPL pada API alat tangkap. ");

        if (Shared.isStringNullOrEmpty(biologyOnSize3rdPartyDTO.getNamaKapal()))
            errorMessage.add("Nama Kapal tidak boleh kosong");

        /* verify apakah ada spesifikasi atau tidak */
        if (!Shared.isStringNullOrEmpty(biologyOnSize3rdPartyDTO.getNamaKapal())
                && !biologyOnSize3rdPartyDTO.getDataUkuranDetail().isEmpty()
                && biologyOnSize3rdPartyDTO.getDataSampleDetail().isEmpty())
            errorMessage.add("Data sample tidak boleh kosong jika anda menginputkan data ukuran detail tangkapan. ");

        /* verify apakah detail informasi tangkapan ada atau tidak */
        if (Shared.isStringNullOrEmpty(biologyOnSize3rdPartyDTO.getNamaKapal()) && biologyOnSize3rdPartyDTO.getDataUkuranDetail().isEmpty())
            errorMessage.add("Rincian detail tangkapan tidak boleh kosong");

        if (biologyOnSize3rdPartyDTO.getStatusDokumen() == ThirdPartyDocumentStatus.WAITING || biologyOnSize3rdPartyDTO.getStatusDokumen() == ThirdPartyDocumentStatus.VERIFIED) {
        } else {
            errorMessage.add("Ada data dengan status dokumen '" + biologyOnSize3rdPartyDTO.getStatusDokumen().toString() + "', tidak dapat diproses. Silahkan gunakan status dokumen 'VERIVIED' atau 'WAITING'");
        }

        return errorMessage;
    }

    public static List<String> validateSizeSample3rdParty(BiologyOnSizeSampleDetail3rdPartyDTO biologyOnSizeSampleDetail3rdPartyDTO,
                                                          boolean insert,
                                                          boolean existOnParent) {
        List<String> errorMessage = new ArrayList<>();
        String id = biologyOnSizeSampleDetail3rdPartyDTO.getId();

        if (!Shared.isStringNullOrEmpty(id)) {
            BiologyOnSizeSampleDetail checkResult = biologyOnSizeSampleRepository.findOne(id);
            if (insert) {
                if (checkResult != null)
                    errorMessage.add("Data Sampel Biologi Ukuran dengan ID " + id + " tidak dapat diproses karena sudah ada di Sistem e-BRPL");
            } else {
                if (checkResult == null) {
                    errorMessage.add("Data Sampel Biologi Reproduksi dengan ID " + id + " tidak dapat diproses karena tidak ditemukan di Sistem e-BRPL");
                } else { // is update and not null checkResult
                    if (!existOnParent)
                        errorMessage.add("Data Sampel Biologi Reproduksi dengan ID " + id + " tidak dapat diproses karena sudah digunakan di data yang lain");
                }
            }
        }


        if (Shared.isStringNullOrEmpty(biologyOnSizeSampleDetail3rdPartyDTO.getNamaSpesies())
                && (biologyOnSizeSampleDetail3rdPartyDTO.getSampleIndividu() > 0 || biologyOnSizeSampleDetail3rdPartyDTO.getSampleVolume() > 0))
            errorMessage.add("Ada nama spesies ikan yang kosong pada data sampel biologi ukuran. ");

        if (biologyOnSizeSampleDetail3rdPartyDTO.getSampleIndividu() <= 0 && biologyOnSizeSampleDetail3rdPartyDTO.getSampleVolume() <= 0)
            errorMessage.add("Ada data jumlah sample yang tidak tepat pada data sampel biologi ukuran. ");

        if (!ValidatorUtil.tipePanjangValid(Shared.verifyString(biologyOnSizeSampleDetail3rdPartyDTO.getTipePanjang())))
            errorMessage.add("Pastikan kembali tipe panjang pada sample biologi ukuran");

        return errorMessage;
    }


    public static List<String> validateSizeDetail3rdParty(BiologyOnSizeDetail3rdPartyDTO sizeDetail3rdPartyDTO,
                                                          boolean insert,
                                                          boolean existOnParent) {
        List<String> errorMessage = new ArrayList<>();
        String id = sizeDetail3rdPartyDTO.getId();

        if (!Shared.isStringNullOrEmpty(id)) {
            BiologyOnSizeDetail checkResult = biologyOnSizeDetailRepository.findOne(id);
            if (insert) {
                if (checkResult != null)
                    errorMessage.add("Data Detail Biologi Ukuran dengan ID " + id + " tidak dapat diproses karena sudah ada di Sistem e-BRPL");
            } else {
                if (checkResult == null) {
                    errorMessage.add("Data Detail Biologi Reproduksi dengan ID " + id + " tidak dapat diproses karena tidak ditemukan di Sistem e-BRPL");
                } else { // is update and not null checkResult
                    if (!existOnParent)
                        errorMessage.add("Data Detail Biologi Reproduksi dengan ID " + id + " tidak dapat diproses karena sudah digunakan di data yang lain");
                }
            }
        }

        if (Shared.isStringNullOrEmpty(sizeDetail3rdPartyDTO.getNamaSpesies()) && sizeDetail3rdPartyDTO.getPanjang() > 0)
            errorMessage.add("Ada nama spesies ikan yang kosong pada data detail biologi ukuran. ");

        if (sizeDetail3rdPartyDTO.getPanjang() < 0)
            errorMessage.add("Ada ukuran ikan yang tidak benar pada data detail biologi ukuran. ");

        return errorMessage;
    }


    public static List<String> validate(BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO, boolean haveRelationWithParent, boolean insert, boolean existOnParent) {
        List<String> errorMessage = new ArrayList<>();
        String id = biologyOnSize3rdPartyDTO.getId();

        errorMessage.addAll(validateSize3rdParty(biologyOnSize3rdPartyDTO));

        /* cek data berdasarkan ID, apakah sudah ada di database atau belum */
        BiologyOnSize checkResult = null;
        if (Shared.isStringNullOrEmpty(id)) {
            errorMessage.add("Data Biologi Ukuran ini tidak memiliki ID");
        } else {
            checkResult = biologyOnSizeRepository.findOne(TranslatorUser3rdParty.encodeId(id));
            if (checkResult == null)
                checkResult = biologyOnSizeRepository.findOne(id);

            if (insert) {
                if (checkResult != null)
                    errorMessage.add("Data Biologi Ukuran dengan ID " + id + " tidak dapat diproses karena sudah ada di Sistem e-BRPL");
            } else {
                if (checkResult == null) {
                    errorMessage.add("Data Biologi Ukuran dengan ID " + id + " tidak dapat diproses karena tidak ditemukan di Sistem e-BRPL");
                } else { // is update and not null checkResult
                    if (haveRelationWithParent && !existOnParent)
                        errorMessage.add("Data Biologi Ukuran dengan ID " + id + " tidak dapat diproses karena sudah digunakan di data trip yang lain");
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

        final BiologyOnSize existing = checkResult;

        List<String> errorsOnSamples = IntStream.range(0, biologyOnSize3rdPartyDTO.getDataSampleDetail().size())
                .mapToObj(index -> {
                    BiologyOnSizeSampleDetail3rdPartyDTO sampleDetail3rdPartyDTO = biologyOnSize3rdPartyDTO.getDataSampleDetail().get(index);
                    boolean existOnHisParent = existing != null && !Shared.isStringNullOrEmpty(sampleDetail3rdPartyDTO.getId())
                            && existing.getDataSampleDetail().stream()
                            .anyMatch(dto -> dto.getUuid().equals(sampleDetail3rdPartyDTO.getId()) || dto.getUuid().equals(TranslatorUser3rdParty.encodeId(sampleDetail3rdPartyDTO.getId())));
                    return validateSizeSample3rdParty(sampleDetail3rdPartyDTO, insert, existOnHisParent);
                })
                .flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnSamples);

        List<String> errorsOnDetail = IntStream.range(0, biologyOnSize3rdPartyDTO.getDataUkuranDetail().size())
                .mapToObj(index -> {
                    BiologyOnSizeDetail3rdPartyDTO sizeDetail3rdPartyDTO = biologyOnSize3rdPartyDTO.getDataUkuranDetail().get(index);
                    boolean existOnHisParent = existing != null && !Shared.isStringNullOrEmpty(sizeDetail3rdPartyDTO.getId())
                            && existing.getDataUkuranDetail().stream()
                            .anyMatch(dto -> dto.getUuid().equals(sizeDetail3rdPartyDTO.getId()) || dto.getUuid().equals(TranslatorUser3rdParty.encodeId(sizeDetail3rdPartyDTO.getId())));
                    return validateSizeDetail3rdParty(sizeDetail3rdPartyDTO, insert, existOnHisParent);
                })
                .flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnDetail);

        return errorMessage;
    }


}
