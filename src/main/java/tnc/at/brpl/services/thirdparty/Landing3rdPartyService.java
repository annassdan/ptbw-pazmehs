package tnc.at.brpl.services.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.*;
import tnc.at.brpl.models.main.dto.*;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.services.main.LandingService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class Landing3rdPartyService {

    @Autowired
    LandingRepository landingRepository;

    @Autowired
    LandingService landingService;


    /**
     *
     * @param reproductionDetail3rdPatyDTO
     * @return
     */
    private List<BiologyOnReproductionDetail> transformReproductionDetail(List<BiologyOnReproductionDetail3rdPatyDTO> reproductionDetail3rdPatyDTO) {
        return reproductionDetail3rdPatyDTO.stream().map(dto -> {
            BiologyOnReproductionDetail detail = BiologyOnReproductionDetail.builder()
                    .panjang(dto.getPanjang())
                    .tipePanjang(dto.getTipePanjang())
                    .jenisKelamin(dto.getJenisKelamin())
                    .berat(dto.getBerat())
                    .beratIsiPerut(dto.getBeratIsiPerut())
                    .tkg(dto.getTkg())
                    .build();
            detail.setUuid(dto.getId());
            return detail;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param reproduction3rdPartyDTOs
     * @return
     */
    private List<BiologyOnReproduction> transformReproduction(List<BiologyOnReproduction3rdPartyDTO> reproduction3rdPartyDTOs) {

        return reproduction3rdPartyDTOs.stream().map(dto -> {
            BiologyOnReproduction biology = BiologyOnReproduction.builder()
                    .uuidSumberDaya(dto.getNamaSumberDaya())
                    .namaLokasiSampling(dto.getNamaLokasiSampling())
                    .namaKapal(dto.getNamaKapal())
                    .uuidSpesies(dto.getNamaSpesies())
                    .daerahPenangkapan(dto.getDaerahPenangkapan())
                    .penampung(dto.isPenampung())
                    .penangkap(dto.isPenangkap())
                    .uuidAlatTangkap(dto.getNamaAlatTangkap())
                    .tanggalSampling(dto.getTanggalSampling())
                    .uuidEnumerator(dto.getNamaPencatat())
                    .dataDetailReproduksi(transformReproductionDetail(dto.getDataDetailReproduksi()))
                    .statusDokumen(dto.getStatusDokumen())
                    .photoName("")
                    .uuidPengupload("")
                    .organisasi("")
                    .wpp(dto.getWpp())
                    .terverifikasiOleh("")
                    .untukEksternalTerverifikasiOleh("").build();
            biology.setUuid(dto.getId());
            return biology;
        }).collect(Collectors.toList());
    }


    /**
     *
     * @param sizeSampleDetail3rdPartyDTOs
     * @return
     */
    private List<BiologyOnSizeSampleDetail> transformSizeSampleDetail(List<BiologyOnSizeSampleDetail3rdPartyDTO> sizeSampleDetail3rdPartyDTOs) {
        return sizeSampleDetail3rdPartyDTOs.stream().map(dto -> {
            BiologyOnSizeSampleDetail sampleDetail = BiologyOnSizeSampleDetail.builder()
                    .uuidSpesies(dto.getNamaSpesies())
                    .sampleVolume(dto.getSampleVolume())
                    .sampleIndividu(dto.getSampleIndividu())
                    .tipePanjang(dto.getTipePanjang())
                    .build();
            sampleDetail.setUuid(dto.getId());
            return sampleDetail;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param sizeDetail3rdPartyDTOs
     * @return
     */
    private List<BiologyOnSizeDetail> transformSizeDetail(List<BiologyOnSizeDetail3rdPartyDTO> sizeDetail3rdPartyDTOs) {
        return sizeDetail3rdPartyDTOs.stream().map(dto -> {
            BiologyOnSizeDetail detail = BiologyOnSizeDetail.builder()
                    .uuidSpesies(dto.getNamaSpesies())
                    .panjang(dto.getPanjang())
                    .build();
            detail.setUuid(dto.getId());
            return detail;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param size3rdPartyDTOs
     * @return
     */
    private List<BiologyOnSize> transformSize(List<BiologyOnSize3rdPartyDTO> size3rdPartyDTOs) {
        return size3rdPartyDTOs.stream().map(dto -> {
            BiologyOnSize size = BiologyOnSize.builder()
                    .uuidEnumerator(dto.getNamaPencatat())
                    .uuidSumberDaya(dto.getNamaSumberDaya())
                    .namaLokasiSampling(dto.getNamaLokasiSampling())
                    .tanggalSampling(dto.getTanggalSampling())
                    .namaKapal(dto.getNamaKapal())
                    .daerahPenangkapan(dto.getDaerahPenangkapan())
                    .uuidAlatTangkap(dto.getNamaAlatTangkap())
                    .penampung(dto.isPenampung())
                    .penangkap(dto.isPenangkap())
                    .totalTangkapanVolume(dto.getTotalTangkapanVolume())
                    .totalTangkapanIndividu(dto.getTotalTangkapanIndividu())
                    .totalSampelVolume(dto.getTotalSampelVolume())
                    .totalSampelIndividu(dto.getTotalSampelIndividu())
                    .dataSampleDetail(transformSizeSampleDetail(dto.getDataSampleDetail()))
                    .dataUkuranDetail(transformSizeDetail(dto.getDataUkuranDetail()))
                    .statusDokumen(dto.getStatusDokumen())
                    .photoName("")
                    .uuidPengupload("")
                    .organisasi("")
                    .wpp(dto.getWpp())
                    .terverifikasiOleh("")
                    .untukEksternalTerverifikasiOleh("")
                    .build();
            size.setUuid(dto.getId());
            return size;
        }).collect(Collectors.toList());
    }


    /**
     *
     * @param specification3rdPartyDTOs
     * @param alatTangkap
     * @return
     */
    private List<OperationalOnFishingToolSpecification> transformOperationalSpecification(List<OperationalOnFishingToolSpecification3rdPartyDTO> specification3rdPartyDTOs, String alatTangkap) {
        return specification3rdPartyDTOs.stream().map(dto -> {
            OperationalOnFishingToolSpecification specification = OperationalOnFishingToolSpecification.builder()
                    .uuidAlatTangkap(alatTangkap)
                    .spesifikasi(dto.getSpesifikasi())
                    .nilaiSpesifikasi(dto.getNilaiSpesifikasi())
                    .satuanSpesifikasi(dto.getSatuanSpesifikasi())
                    .build();
            specification.setUuid(dto.getId());
            return specification;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param details3rdPartyDTOs
     * @return
     */
    private List<OperationalCatchDetails> transformOperationalCatch(List<OperationalCatchDetails3rdPartyDTO> details3rdPartyDTOs) {
        return details3rdPartyDTOs.stream().map(dto -> {
            OperationalCatchDetails details = OperationalCatchDetails.builder()
                    .uuidSpesies(dto.getNamaSpesies())
                    .kodeFao(dto.getKodeFao())
                    .totalBeratKg(dto.getTotalBeratKg())
                    .totalBeratEkor(dto.getTotalBeratEkor())
                    .segar(dto.isSegar())
                    .beku(dto.isBeku())
                    .asin(dto.isAsin())
                    .loin(dto.isLoin())
                    .rebus(dto.isRebus())
                    .build();
            details.setUuid(dto.getId());
            return details;
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param operational3rdPartyDTs
     * @return
     */
    private List<Operational> transformOperational(List<Operational3rdPartyDTO> operational3rdPartyDTs) {
        return operational3rdPartyDTs.stream().map(dto -> {
            Operational operational = Operational.builder()
                    .namaLokasiPendaratan(dto.getNamaLokasiPendaratan())
                    .uuidSumberDaya(dto.getNamaSumberDaya())
                    .uuidEnumerator(dto.getNamaPencatat())
                    .jamSampling(dto.getJamSampling())
                    .tanggalSampling(dto.getTanggalSampling())
                    .namaKapal(dto.getNamaKapal())
                    .tanggalBerangkat(dto.getTanggalBerangkat())
                    .tandaSelar(dto.getTandaSelar())
                    .tanggalKembali(dto.getTanggalKembali())
                    .namaPemilikKapal(dto.getNamaPemilikKapal())
                    .pelabuhanAsal(dto.getPelabuhanAsal())
                    .namaKapten(dto.getNamaKapten())
                    .jumlahAbk(dto.getJumlahAbk())
                    .panjangKapal(dto.getPanjangKapal())
                    .materialKapal(dto.getMaterialKapal())
                    .dayaCahaya(dto.getDayaCahaya())
                    .bobotKapal(dto.getBobotKapal())
                    .kapalBantu(dto.isKapalBantu())
                    .ukuranKapalBantu(dto.getUkuranKapalBantu())
                    .kapalAndon(dto.isKapalAndon())
                    .asalKapalAndon(dto.getAsalKapalAndon())
                    .jumlahPalka(dto.getJumlahPalka())
                    .jumlahBoks(dto.getJumlahBoks())
                    .mesinUtama(dto.getMesinUtama())
                    .freezer(dto.isFreezer())
                    .kapasitasFreezer(dto.getKapasitasFreezer())
                    .kapasitasPalkaBoks(dto.getKapasitasPalkaBoks())
                    .mesinBantu(dto.getMesinBantu())
                    .gps(dto.isGps())
                    .jenisGps(dto.getJenisGps())
                    .uuidAlatTangkap(dto.getNamaAlatTangkap())
                    .material(dto.getMaterialAlatTangkap())
                    .jumlahAlatTangkapYangDioperasikan(dto.getJumlahAlatTangkapYangDioperasikan())
                    .jumlahSetting(dto.getJumlahSetting())
                    .kedalamanAirMulai(dto.getKedalamanAirMulai())
                    .kedalamanAirHingga(dto.getKedalamanAirHingga())
                    .daerahPenangkapan(dto.getDaerahPenangkapan())
                    .jumlahHariPerTrip(dto.getJumlahHariPerTrip())
                    .jumlahHariMenangkap(dto.getJumlahHariMenangkap())
                    .jenisRumpon(dto.getJenisRumpon())
                    .jumlahBalokEs(dto.getJumlahBalokEs())
                    .jumlahRumponDikunjungi(dto.getJumlahRumponDikunjungi())
                    .jumlahRumponBerhasil(dto.getJumlahRumponBerhasil())
                    .waktuMemancing(dto.getWaktuMemancing())
                    .komentar(dto.getKomentar())
                    .sumberInformasi(dto.getSumberInformasi())
                    .jumlahTangkapanUntukDimakanDilautVolume(dto.getJumlahTangkapanUntukDimakanDilautVolume())
                    .jumlahTangkapanUntukDimakanDilautIndividu(dto.getJumlahTangkapanUntukDimakanDilautIndividu())
                    .dataSpesifikasiAlatTangkap(transformOperationalSpecification(dto.getDataSpesifikasiAlatTangkap(), dto.getNamaAlatTangkap()))
                    .dataOperasionalDetailTangkapan(transformOperationalCatch(dto.getDataOperasionalDetailTangkapan()))
                    .jumlahTangkapanVolume(dto.getJumlahTangkapanVolume())
                    .jumlahTangkapanIndividu(dto.getJumlahTangkapanIndividu())
                    .lamaPerendaman(dto.getLamaPerendaman())
                    .statusDokumen(dto.getStatusDokumen())
                    .photoName("")
                    .uuidPengupload("")
                    .organisasi("")
                    .wpp(dto.getWpp())
                    .terverifikasiOleh("")
                    .untukEksternalTerverifikasiOleh("")
                    .build();
            operational.setUuid(dto.getId());
            return operational;
        }).collect(Collectors.toList());
    }


    private List<LandingCatchDetail> transformLandingCatchDetail(List<LandingCatchDetail3rdPartyDTO> catchDetail3rdPartyDTOs) {
        return catchDetail3rdPartyDTOs.stream().map(dto -> {
            LandingCatchDetail catchDetail = LandingCatchDetail.builder()
                    .uuidSpesies(dto.getNamaSpesies())
                    .tangkapanVolume(dto.getTangkapanVolume())
                    .tangkapanIndividu(dto.getTangkapanIndividu())
                    .build();
            catchDetail.setUuid(dto.getId());
            return catchDetail;
        }).collect(Collectors.toList());
    }


    private List<LandingDetail> transformLandingDetail(List<LandingDetail3rdPartyDTO> detail3rdPartyDTOs) {
        return detail3rdPartyDTOs.stream().map(dto -> {
            LandingDetail detail = LandingDetail.builder()
                    .namaKapal(dto.getNamaKapal())
                    .penampung(dto.isPenampung())
                    .penangkap(dto.isPenangkap())
                    .jumlahKapalPenangkap(dto.getJumlahKapalPenangkap())
                    .uuidAlatTangkap(dto.getNamaAlatTangkap())
                    .jumlahSetting(dto.getJumlahSetting())
                    .jumlahMataPancing(dto.getJumlahMataPancing())
                    .rumpon(dto.isRumpon())
                    .cahaya(dto.isCahaya())
                    .daerahPenangkapan(dto.getDaerahPenangkapan())
                    .jumlahHariPerTrip(dto.getJumlahHariPerTrip())
                    .jumlahHariMenangkap(dto.getJumlahHariMenangkap())
                    .totalTangkapanVolume(dto.getTotalTangkapanVolume())
                    .totalTangkapanIndividu(dto.getTotalTangkapanIndividu())
                    .dataRincianTangkapanPendaratan(transformLandingCatchDetail(dto.getDataRincianTangkapanPendaratan()))
                    .build();
            detail.setUuid(dto.getId());
            return detail;
        }).collect(Collectors.toList());
    }


    /**
     *
     * @param landing3rdPartyDTO
     * @return
     */
    public Landing3rdPartyDTO save(Landing3rdPartyDTO landing3rdPartyDTO) {
        Landing landing = Landing.builder()
                .tanggalPendaratan(landing3rdPartyDTO.getTanggalPendaratan())
                .namaLokasiPendaratan(landing3rdPartyDTO.getNamaLokasiPendaratan())
                .uuidSumberDaya(landing3rdPartyDTO.getNamaSumberDaya())
                .uuidEnumerator(landing3rdPartyDTO.getNamaPencatat())
                .dataRincianPendaratan(transformLandingDetail(landing3rdPartyDTO.getDataRincianPendaratan()))
                .dataOperasional(transformOperational(landing3rdPartyDTO.getDataOperasional()))
                .dataUkuran(transformSize(landing3rdPartyDTO.getDataUkuran()))
                .dataReproduksi(transformReproduction(landing3rdPartyDTO.getDataReproduksi()))
                .userGroup(null)
                .statusDokumen(landing3rdPartyDTO.getStatusDokumen())
                .photoName("")
                .uuidPengupload("")
                .organisasi("")
                .wpp(landing3rdPartyDTO.getWpp())
                .terverifikasiOleh("")
                .untukEksternalTerverifikasiOleh("")
                .build();

        landingRepository.save(landing);
        return landing3rdPartyDTO;
    }



}
