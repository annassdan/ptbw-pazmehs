package tnc.at.brpl.utils.data.validators.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.dto.BiologyOnReproduction3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnReproductionDetail3rdPatyDTO;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.BiologyOnReproductionRepository;
import tnc.at.brpl.utils.data.ThirdPartyDocumentStatus;
import tnc.at.brpl.utils.data.validators.ValidatorUtil;
import tnc.at.brpl.utils.other.Shared;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BiologyOnReproduction3rdPartyValidator {

    private static BiologyOnReproductionRepository biologyOnReproductionRepository;

    @Autowired
    private BiologyOnReproductionRepository biologyOnReproductionRepositoryInjecter;

    @Autowired
    private static SysUserRepository sysUserRepository;

    @Autowired
    private SysUserRepository sysUserRepositoryInjecter;

    @PostConstruct
    private void init() {
        biologyOnReproductionRepository = biologyOnReproductionRepositoryInjecter;
        sysUserRepository = sysUserRepositoryInjecter;
    }

    public BiologyOnReproduction3rdPartyValidator() {
    }

    private static String getUsername() {
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public static List<String> validateReproduction3rdParty(BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        /* cek data berdasarkan ID, apakah sudah ada di database atau belum */
        if (Shared.isStringNullOrEmpty(biologyOnReproduction3rdPartyDTO.getId())) {
            errorMessage.add("Data Pendaratan ini tidak memiliki ID");
        } else {
            BiologyOnReproduction checkResult = biologyOnReproductionRepository.findOne(biologyOnReproduction3rdPartyDTO.getId());
            if (checkResult != null)
                errorMessage.add("Data Biologi Reproduksi dengan ID " + biologyOnReproduction3rdPartyDTO.getId() +
                        " sudah ada di Sistem e-BRPL, Silahkan lakukan proses update jika ada perubahan pada data ini.");
        }

        /* verify Lokasi Pendaratan */
        if (Shared.isStringNullOrEmpty(biologyOnReproduction3rdPartyDTO.getNamaLokasiSampling()))
            errorMessage.add("Nama Lokasi Sampling pada data Biologi Ukuran tidak boleh kosong.");

        /* verify nama pencatat */
        if (Shared.isStringNullOrEmpty((biologyOnReproduction3rdPartyDTO.getNamaPencatat())))
            errorMessage.add("Nama Enumerator pada data Pendaratan tidak boleh kosong.");

        /* verify sumberdaya */
        if (Shared.isStringNullOrEmpty(biologyOnReproduction3rdPartyDTO.getNamaSumberDaya()))
            errorMessage.add("Ada Sumber Daya yang tidak valid...");

        /* verify sumberdaya again */
        if (!Shared.isStringNullOrEmpty((biologyOnReproduction3rdPartyDTO.getNamaSumberDaya())) && !ValidatorUtil.sumberdayaValid(biologyOnReproduction3rdPartyDTO.getNamaSumberDaya()))
            errorMessage.add("Data Operasional, dengan sumber daya '" + biologyOnReproduction3rdPartyDTO.getNamaSumberDaya() +
                    "' yang tidak valid dengan ketentuan BRPL, " +
                    "Silahkan lihat kembali daftar sumber daya yang diakomodir oleh BRPL pada API sumber daya. ");

        /* verify wpp */
        if (Shared.isStringNullOrEmpty((biologyOnReproduction3rdPartyDTO.getWpp())))
            errorMessage.add("Ada WPP yang tidak valid...");

        /* verify wpp again */
        if (!Shared.isStringNullOrEmpty((biologyOnReproduction3rdPartyDTO.getWpp())) && !ValidatorUtil.wppValid(biologyOnReproduction3rdPartyDTO.getWpp()))
            errorMessage.add("Pendaratan ini memiliki WPP yang tidak valid");

        /*verify waktu/tanggal pendaratan */
        if (biologyOnReproduction3rdPartyDTO.getTanggalSampling() == null)
            errorMessage.add("Tanggal sampling untuk data operasioanl ini tidak boleh kosong.");

        if (Shared.isStringNullOrEmpty(biologyOnReproduction3rdPartyDTO.getNamaKapal()))
            errorMessage.add("Mohon inputkan nama kapal yang benar");

        if (!ValidatorUtil.alatTangkapValid(Shared.verifyString(biologyOnReproduction3rdPartyDTO.getNamaSumberDaya())))
            errorMessage.add("Alat tangkap \'" + Shared.verifyString(biologyOnReproduction3rdPartyDTO.getNamaAlatTangkap(), true) +
                    "', tidak diakomodir untuk Sumber Daya '" + Shared.verifyString(biologyOnReproduction3rdPartyDTO.getNamaSumberDaya(), true) + "'." +
                    "Silahkan lihat kembali daftar alat tangkap yang diakomodir oleh BRPL pada API alat tangkap. ");

        if (Shared.isStringNullOrEmpty(biologyOnReproduction3rdPartyDTO.getNamaKapal()))
            errorMessage.add("Nama Kapal tidak boleh kosong");

        if (Shared.isStringNullOrEmpty(biologyOnReproduction3rdPartyDTO.getNamaSpesies()))
            errorMessage.add("Nama Spesies tidak boleh kosong");

        /* verify apakah detail informasi tangkapan ada atau tidak */
        if (Shared.isStringNullOrEmpty(biologyOnReproduction3rdPartyDTO.getNamaSpesies()) && biologyOnReproduction3rdPartyDTO.getDataDetailReproduksi().isEmpty())
            errorMessage.add("Rincian detail reproduksi tidak boleh kosong");

        if (biologyOnReproduction3rdPartyDTO.getStatusDokumen() == ThirdPartyDocumentStatus.WAITING || biologyOnReproduction3rdPartyDTO.getStatusDokumen() == ThirdPartyDocumentStatus.VERIFIED) {
        } else {
            errorMessage.add("Ada data dengan status dokumen '"+ biologyOnReproduction3rdPartyDTO.getStatusDokumen().toString() +"', tidak dapat diproses. Silahkan gunakan status dokumen 'VERIVIED' atau 'WAITING'");
        }

        return errorMessage;
    }

    public static List<String> validateReproductionDetail3rdParty(BiologyOnReproductionDetail3rdPatyDTO reproductionDetail3rdPatyDTO) {
        List<String> errorMessage = new ArrayList<>();

        if (reproductionDetail3rdPatyDTO.getBerat() <= 0)
            errorMessage.add("Ada berat ikan yang tidak valid pada data detail biologi reproduksi.");

        if (reproductionDetail3rdPatyDTO.getBeratIsiPerut() <= 0)
            errorMessage.add("Ada berat isi perut  ikan yang tidak valid pada data detail biologi reproduksi. ");

        if (!Shared.isStringNullOrEmpty(reproductionDetail3rdPatyDTO.getJenisKelamin())
                && !(Shared.verifyString(reproductionDetail3rdPatyDTO.getJenisKelamin()).equals("M") || Shared.verifyString(reproductionDetail3rdPatyDTO.getJenisKelamin()).equals("F")))
            errorMessage.add("Ada jenis kelamin ikan yang tidak valid. ");

        if (reproductionDetail3rdPatyDTO.getPanjang() <= 0)
            errorMessage.add("Ada ikan yang panjangnya tidak valid. ");

        if (!ValidatorUtil.tipePanjangValid(Shared.verifyString(reproductionDetail3rdPatyDTO.getTipePanjang())))
            errorMessage.add("Ada ikan yang tipe panjangnya tidak valid. ");

        if (!ValidatorUtil.tkgValid(Shared.verifyString(reproductionDetail3rdPatyDTO.getTkg())))
            errorMessage.add("Ada ikan yang mempunyai TKG  tidak valid.");

        return errorMessage;
    }



    public static List<String> validate(BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        List<String> errorMessage = new ArrayList<>();

        errorMessage.addAll(validateReproduction3rdParty(biologyOnReproduction3rdPartyDTO));

        List<String> errorsOnDetail = IntStream.range(0, biologyOnReproduction3rdPartyDTO.getDataDetailReproduksi().size())
                .mapToObj(index -> validateReproductionDetail3rdParty(biologyOnReproduction3rdPartyDTO.getDataDetailReproduksi().get(index)))
                .flatMap(strings -> strings.stream()).collect(Collectors.toList());
        errorMessage.addAll(errorsOnDetail);

        return errorMessage;
    }


}
