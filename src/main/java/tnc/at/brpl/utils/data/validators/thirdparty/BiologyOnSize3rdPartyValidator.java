package tnc.at.brpl.utils.data.validators.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.models.main.dto.BiologyOnSize3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSizeDetail3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSizeSampleDetail3rdPartyDTO;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeRepository;
import tnc.at.brpl.utils.data.ThirdPartyDocumentStatus;
import tnc.at.brpl.utils.data.validators.ValidatorUtil;
import tnc.at.brpl.utils.other.Shared;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BiologyOnSize3rdPartyValidator {

    private static BiologyOnSizeRepository biologyOnSizeRepository;

    @Autowired
    private BiologyOnSizeRepository biologyOnSizeRepositoryInjecter;

    @Autowired
    private static SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRepository sysUserRepositoryInjecter;

    @PostConstruct
    private void init() {
        biologyOnSizeRepository = biologyOnSizeRepositoryInjecter;
        sysUserRepository = sysUserRepositoryInjecter;
    }

    public BiologyOnSize3rdPartyValidator() {
    }

    private static String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }


    public static List<String> validateSize3rdParty(BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        /* cek data berdasarkan ID, apakah sudah ada di database atau belum */
        if (Shared.isStringNullOrEmpty(biologyOnSize3rdPartyDTO.getId())) {
            errorMessage.add("Data Pendaratan ini tidak memiliki ID");
        } else {
            BiologyOnSize checkResult = biologyOnSizeRepository.findOne(biologyOnSize3rdPartyDTO.getId());
            if (checkResult != null)
                errorMessage.add("Data Biologi Ukuran dengan ID " + biologyOnSize3rdPartyDTO.getId() +
                        " sudah ada di Sistem e-BRPL, Silahkan lakukan proses update jika ada perubahan pada data ini.");
        }

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

        if (!ValidatorUtil.alatTangkapValid(Shared.verifyString(biologyOnSize3rdPartyDTO.getNamaSumberDaya())))
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
            errorMessage.add("Ada data dengan status dokumen '"+ biologyOnSize3rdPartyDTO.getStatusDokumen().toString() +"', tidak dapat diproses. Silahkan gunakan status dokumen 'VERIVIED' atau 'WAITING'");
        }

        return errorMessage;
    }

    public static List<String> validateSizeSample3rdParty(BiologyOnSizeSampleDetail3rdPartyDTO biologyOnSizeSampleDetail3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        if (Shared.isStringNullOrEmpty(biologyOnSizeSampleDetail3rdPartyDTO.getNamaSpesies())
                && (biologyOnSizeSampleDetail3rdPartyDTO.getSampleIndividu() > 0 || biologyOnSizeSampleDetail3rdPartyDTO.getSampleVolume() > 0))
            errorMessage.add("Ada nama spesies ikan yang kosong pada data sampel biologi ukuran. ");

        if (biologyOnSizeSampleDetail3rdPartyDTO.getSampleIndividu() <= 0 && biologyOnSizeSampleDetail3rdPartyDTO.getSampleVolume() <= 0)
            errorMessage.add("Ada data jumlah sample yang tidak tepat pada data sampel biologi ukuran. ");

        if (!ValidatorUtil.tipePanjangValid(Shared.verifyString(biologyOnSizeSampleDetail3rdPartyDTO.getTipePanjang())))
            errorMessage.add("Pastikan kembali tipe panjang pada sample biologi ukuran");

        return errorMessage;
    }


    public static List<String> validateSizeDetail3rdParty(BiologyOnSizeDetail3rdPartyDTO sizeDetail3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        if (Shared.isStringNullOrEmpty(sizeDetail3rdPartyDTO.getNamaSpesies()) && sizeDetail3rdPartyDTO.getPanjang() > 0)
            errorMessage.add("Ada nama spesies ikan yang kosong pada data detail biologi ukuran. ");

        if (sizeDetail3rdPartyDTO.getPanjang() <= 0)
            errorMessage.add("Ada ukuran ikan yang tidak benar pada data detail biologi ukuran. ");

        return errorMessage;
    }


    public static List<String> validate(BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        errorMessage.addAll(validateSize3rdParty(biologyOnSize3rdPartyDTO));

        List<String> errorsOnSamples = IntStream.range(0, biologyOnSize3rdPartyDTO.getDataSampleDetail().size())
                .mapToObj(index -> validateSizeSample3rdParty(biologyOnSize3rdPartyDTO.getDataSampleDetail().get(index)))
                .flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnSamples);

        List<String> errorsOnDetail = IntStream.range(0, biologyOnSize3rdPartyDTO.getDataUkuranDetail().size())
                .mapToObj(index -> validateSizeDetail3rdParty(biologyOnSize3rdPartyDTO.getDataUkuranDetail().get(index)))
                .flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnDetail);

        return errorMessage;
    }


}
