package tnc.at.brpl.utils.thirdparty;

import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.main.*;
import tnc.at.brpl.models.main.dto.*;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.data.ThirdPartyDocumentStatus;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Translator3rdParty implements Brpl {

    private Date auditDate;

    SysUser user;

    public Translator3rdParty() {
    }


    public String encodeId(String originalId) {
        if (user == null)
            return originalId;
        return user.getUuid() + MITRA_ID_DELIMITER + originalId;
    }

    public String decodeId(String encodedId) {
        String[] splitedId = encodedId.split(MITRA_ID_DELIMITER, 2);
        return splitedId.length == 0 || splitedId.length == 1 ? encodedId : splitedId[1];
    }

    public Translator3rdParty(Date auditDate, SysUser user) {
        this.auditDate = auditDate;
        this.user = user;
    }


    private DocumentStatus enTransformDocumentStatus(ThirdPartyDocumentStatus thirdPartyDocumentStatus) {
        switch (thirdPartyDocumentStatus) {
            case PENDING:
                return DocumentStatus.PENDING;
            case WAITING:
                return DocumentStatus.WAITING;
            case VERIFIED:
                return DocumentStatus.NOT_VERIFIED;
            default:
                return DocumentStatus.WAITING;
        }
    }


    private ThirdPartyDocumentStatus deTransformDocumentStatus(DocumentStatus status) {
        switch (status) {
            case PENDING:
                return ThirdPartyDocumentStatus.PENDING;
            case WAITING:
                return ThirdPartyDocumentStatus.WAITING;
            case NOT_VERIFIED:
                return ThirdPartyDocumentStatus.VERIFIED;
            default:
                return ThirdPartyDocumentStatus.WAITING;
        }
    }


    public List<BiologyOnReproductionDetail> transformReproductionDetail(List<BiologyOnReproductionDetail3rdPatyDTO> reproductionDetail3rdPatyDTO) {

        if (user == null || auditDate == null || reproductionDetail3rdPatyDTO == null)
            return null;

        return reproductionDetail3rdPatyDTO.stream().map(dto -> {
            BiologyOnReproductionDetail detail = BiologyOnReproductionDetail.builder()
                    .panjang(dto.getPanjang())
                    .tipePanjang(dto.getTipePanjang())
                    .jenisKelamin(dto.getJenisKelamin())
                    .berat(dto.getBerat())
                    .beratIsiPerut(dto.getBeratIsiPerut())
                    .tkg(dto.getTkg())
                    .build();
            detail.setUuid(encodeId(dto.getId()));
            detail.setDibuatPadaTanggal(auditDate);
            detail.setTerakhirDiubahPadaTanggal(auditDate);
            detail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return detail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnReproduction> transformReproductions(List<BiologyOnReproduction3rdPartyDTO> reproduction3rdPartyDTOs) {

        if (user == null || auditDate == null || reproduction3rdPartyDTOs == null)
            return null;

        return reproduction3rdPartyDTOs.stream().map(dto -> transformReproduction(dto)).collect(Collectors.toList());
    }

    public BiologyOnReproduction transformReproduction(BiologyOnReproduction3rdPartyDTO dto) {

        if (user == null || auditDate == null || dto == null)
            return null;

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
                .statusDokumen(enTransformDocumentStatus(dto.getStatusDokumen()))
                .photoName("")
                .uuidPengupload(user.getUuid())
                .organisasi(user.getOrganisasi())
                .wpp(dto.getWpp())
                .terverifikasiOleh("")
                .byMachine(true)
                .untukEksternalTerverifikasiOleh("").build();
        biology.setUuid(encodeId(dto.getId()));
        biology.setDibuatPadaTanggal(auditDate);
        biology.setTerakhirDiubahPadaTanggal(auditDate);
        biology.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
        return biology;
    }


    public List<BiologyOnSizeSampleDetail> transformSizeSampleDetail(List<BiologyOnSizeSampleDetail3rdPartyDTO> sizeSampleDetail3rdPartyDTOs) {

        if (user == null || auditDate == null || sizeSampleDetail3rdPartyDTOs == null)
            return null;

        return sizeSampleDetail3rdPartyDTOs.stream().map(dto -> {
            BiologyOnSizeSampleDetail sampleDetail = BiologyOnSizeSampleDetail.builder()
                    .uuidSpesies(dto.getNamaSpesies())
                    .sampleVolume(dto.getSampleVolume())
                    .sampleIndividu(dto.getSampleIndividu())
                    .tipePanjang(dto.getTipePanjang())
                    .build();
            sampleDetail.setUuid(encodeId(dto.getId()));
            sampleDetail.setDibuatPadaTanggal(auditDate);
            sampleDetail.setTerakhirDiubahPadaTanggal(auditDate);
            sampleDetail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return sampleDetail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSizeDetail> transformSizeDetail(List<BiologyOnSizeDetail3rdPartyDTO> sizeDetail3rdPartyDTOs) {

        if (user == null || auditDate == null || sizeDetail3rdPartyDTOs == null)
            return null;

        return sizeDetail3rdPartyDTOs.stream().map(dto -> {
            BiologyOnSizeDetail detail = BiologyOnSizeDetail.builder()
                    .uuidSpesies(dto.getNamaSpesies())
                    .panjang(dto.getPanjang())
                    .build();
            detail.setUuid(encodeId(dto.getId()));
            detail.setDibuatPadaTanggal(auditDate);
            detail.setTerakhirDiubahPadaTanggal(auditDate);
            detail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return detail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSize> transformSizes(List<BiologyOnSize3rdPartyDTO> size3rdPartyDTOs) {

        if (user == null || auditDate == null || size3rdPartyDTOs == null)
            return null;

        return size3rdPartyDTOs.stream().map(dto -> transformSize(dto)).collect(Collectors.toList());
    }

    public BiologyOnSize transformSize(BiologyOnSize3rdPartyDTO dto) {

        if (user == null || auditDate == null || dto == null)
            return null;

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
                .statusDokumen(enTransformDocumentStatus(dto.getStatusDokumen()))
                .photoName("")
                .uuidPengupload(user.getUuid())
                .organisasi(user.getOrganisasi())
                .wpp(dto.getWpp())
                .terverifikasiOleh("")
                .untukEksternalTerverifikasiOleh("")
                .byMachine(true)
                .build();
        size.setUuid(encodeId(dto.getId()));
        size.setDibuatPadaTanggal(auditDate);
        size.setTerakhirDiubahPadaTanggal(auditDate);
        size.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
        return size;
    }


    public List<OperationalOnFishingToolSpecification> transformOperationalSpecification(List<OperationalOnFishingToolSpecification3rdPartyDTO> specification3rdPartyDTOs, String alatTangkap) {

        if (user == null || auditDate == null || specification3rdPartyDTOs == null)
            return null;

        return specification3rdPartyDTOs.stream().map(dto -> {
            OperationalOnFishingToolSpecification specification = OperationalOnFishingToolSpecification.builder()
                    .uuidAlatTangkap(alatTangkap)
                    .spesifikasi(dto.getSpesifikasi())
                    .nilaiSpesifikasi(dto.getNilaiSpesifikasi())
                    .satuanSpesifikasi(dto.getSatuanSpesifikasi())
                    .build();
            specification.setUuid(encodeId(dto.getId()));
            specification.setDibuatPadaTanggal(auditDate);
            specification.setTerakhirDiubahPadaTanggal(auditDate);
            specification.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return specification;
        }).collect(Collectors.toList());
    }


    public List<OperationalCatchDetails> transformOperationalCatch(List<OperationalCatchDetails3rdPartyDTO> details3rdPartyDTOs) {

        if (user == null || auditDate == null || details3rdPartyDTOs == null)
            return null;

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
            details.setUuid(encodeId(dto.getId()));
            details.setDibuatPadaTanggal(auditDate);
            details.setTerakhirDiubahPadaTanggal(auditDate);
            details.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return details;
        }).collect(Collectors.toList());
    }


    public List<Operational> transformOperationals(List<Operational3rdPartyDTO> operational3rdPartyDTs) {

        if (user == null || auditDate == null || operational3rdPartyDTs == null)
            return null;

        return operational3rdPartyDTs.stream().map(dto -> transformOperational(dto)).collect(Collectors.toList());
    }

    public Operational transformOperational(Operational3rdPartyDTO dto) {

        if (user == null || auditDate == null || dto == null)
            return null;

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
                .statusDokumen(enTransformDocumentStatus(dto.getStatusDokumen()))
                .photoName("")
                .uuidPengupload(user.getUuid())
                .organisasi(user.getOrganisasi())
                .wpp(dto.getWpp())
                .terverifikasiOleh("")
                .untukEksternalTerverifikasiOleh("")
                .byMachine(true)
                .build();
        operational.setUuid(encodeId(dto.getId()));
        operational.setDibuatPadaTanggal(auditDate);
        operational.setTerakhirDiubahPadaTanggal(auditDate);
        operational.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
        return operational;
    }


    public List<LandingCatchDetail> transformLandingCatchDetail(List<LandingCatchDetail3rdPartyDTO> catchDetail3rdPartyDTOs) {

        if (user == null || auditDate == null || catchDetail3rdPartyDTOs == null)
            return null;

        return catchDetail3rdPartyDTOs.stream().map(dto -> {
            if (dto.getTangkapanVolume() < 0)
                throw new ResourceInternalServerErrorException("Ada data detail tangkapan Kapal, yang mempunyai detail tangkapan (Volume) dibawah 0");

            if (dto.getTangkapanIndividu() < 0)
                throw new ResourceInternalServerErrorException("Ada data detail tangkapan Kapal, yang mempunyai detail tangkapan (Individu) dibawah 0");

            LandingCatchDetail catchDetail = LandingCatchDetail.builder()
                    .uuidSpesies(dto.getNamaSpesies())
                    .tangkapanVolume(dto.getTangkapanVolume())
                    .tangkapanIndividu(dto.getTangkapanIndividu())
                    .build();
            catchDetail.setUuid(encodeId(dto.getId()));
            catchDetail.setDibuatPadaTanggal(auditDate);
            catchDetail.setTerakhirDiubahPadaTanggal(auditDate);
            catchDetail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return catchDetail;
        }).collect(Collectors.toList());
    }

    public List<LandingDetail> transformLandingDetail(List<LandingDetail3rdPartyDTO> detail3rdPartyDTOs) {

        if (user == null || auditDate == null || detail3rdPartyDTOs == null)
            return null;

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
            detail.setUuid(encodeId(dto.getId()));
            detail.setDibuatPadaTanggal(auditDate);
            detail.setTerakhirDiubahPadaTanggal(auditDate);
            detail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return detail;
        }).collect(Collectors.toList());
    }

    public Landing transformLanding(Landing3rdPartyDTO landing3rdPartyDTO) {

        if (user == null || auditDate == null || landing3rdPartyDTO == null)
            return null;

        Landing landing = Landing.builder()
                .tanggalPendaratan(landing3rdPartyDTO.getTanggalPendaratan())
                .namaLokasiPendaratan(landing3rdPartyDTO.getNamaLokasiPendaratan())
                .uuidSumberDaya(landing3rdPartyDTO.getNamaSumberDaya())
                .uuidEnumerator(landing3rdPartyDTO.getNamaPencatat())
                .dataRincianPendaratan(transformLandingDetail(landing3rdPartyDTO.getDataRincianPendaratan()))
                .dataOperasional(transformOperationals(landing3rdPartyDTO.getDataOperasional()))
                .dataUkuran(transformSizes(landing3rdPartyDTO.getDataUkuran()))
                .dataReproduksi(transformReproductions(landing3rdPartyDTO.getDataReproduksi()))
                .userGroup(null)
                .statusDokumen(enTransformDocumentStatus(landing3rdPartyDTO.getStatusDokumen()))
                .photoName("")
                .uuidPengupload(user.getUuid())
                .organisasi(user.getOrganisasi())
                .wpp(landing3rdPartyDTO.getWpp())
                .terverifikasiOleh("")
                .untukEksternalTerverifikasiOleh("")
                .byMachine(true)
                .build();

        landing.setUuid(encodeId(landing3rdPartyDTO.getId()));
        landing.setDibuatPadaTanggal(auditDate);
        landing.setTerakhirDiubahPadaTanggal(auditDate);
        landing.setDibuatAtauTerakhirDiubahOleh(user.getUuid());

        return landing;
    }


    /* Detransform*/

    public List<BiologyOnReproductionDetail3rdPatyDTO> deTransformReproductionDetail(List<BiologyOnReproductionDetail> reproductionDetail) {

        return reproductionDetail.stream().map(dto -> {
            BiologyOnReproductionDetail3rdPatyDTO detail = BiologyOnReproductionDetail3rdPatyDTO.builder()
                    .panjang(dto.getPanjang())
                    .tipePanjang(dto.getTipePanjang())
                    .jenisKelamin(dto.getJenisKelamin())
                    .berat(dto.getBerat())
                    .beratIsiPerut(dto.getBeratIsiPerut())
                    .tkg(dto.getTkg())
                    .id(decodeId(dto.getUuid()))
                    .build();
            return detail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnReproduction3rdPartyDTO> deTransformReproductions(List<BiologyOnReproduction> reproduction) {
        return reproduction.stream().map(dto -> deTransformReproduction(dto)).collect(Collectors.toList());
    }


    public BiologyOnReproduction3rdPartyDTO deTransformReproduction(BiologyOnReproduction dto) {
        BiologyOnReproduction3rdPartyDTO biology = BiologyOnReproduction3rdPartyDTO.builder()
                .namaSumberDaya(dto.getUuidSumberDaya())
                .namaLokasiSampling(dto.getNamaLokasiSampling())
                .namaKapal(dto.getNamaKapal())
                .namaSpesies(dto.getUuidSpesies())
                .daerahPenangkapan(dto.getDaerahPenangkapan())
                .penampung(dto.isPenampung())
                .penangkap(dto.isPenangkap())
                .namaAlatTangkap(dto.getUuidAlatTangkap())
                .tanggalSampling(dto.getTanggalSampling())
                .namaPencatat(dto.getUuidEnumerator())
                .dataDetailReproduksi(deTransformReproductionDetail(dto.getDataDetailReproduksi()))
                .statusDokumen(deTransformDocumentStatus(dto.getStatusDokumen()))
                .wpp(dto.getWpp())
                .id(decodeId(dto.getUuid()))
                .build();

        return biology;
    }


    public List<BiologyOnSizeSampleDetail3rdPartyDTO> deTransformSizeSampleDetail(List<BiologyOnSizeSampleDetail> sizeSampleDetail) {

        return sizeSampleDetail.stream().map(dto -> {
            BiologyOnSizeSampleDetail3rdPartyDTO sampleDetail = BiologyOnSizeSampleDetail3rdPartyDTO.builder()
                    .namaSpesies(dto.getUuidSpesies())
                    .sampleVolume(dto.getSampleVolume())
                    .sampleIndividu(dto.getSampleIndividu())
                    .tipePanjang(dto.getTipePanjang())
                    .id(decodeId(dto.getUuid()))
                    .build();

            return sampleDetail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSizeDetail3rdPartyDTO> deTransformSizeDetail(List<BiologyOnSizeDetail> sizeDetail) {

        return sizeDetail.stream().map(dto -> {
            BiologyOnSizeDetail3rdPartyDTO detail = BiologyOnSizeDetail3rdPartyDTO.builder()
                    .namaSpesies(dto.getUuidSpesies())
                    .panjang(dto.getPanjang())
                    .id(decodeId(dto.getUuid()))
                    .build();
            return detail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSize3rdPartyDTO> deTransformSizes(List<BiologyOnSize> onSize) {
        return onSize.stream().map(dto -> deTransformSize(dto)).collect(Collectors.toList());
    }


    public BiologyOnSize3rdPartyDTO deTransformSize(BiologyOnSize dto) {

        BiologyOnSize3rdPartyDTO size = BiologyOnSize3rdPartyDTO.builder()
                .namaPencatat(dto.getUuidEnumerator())
                .namaSumberDaya(dto.getUuidSumberDaya())
                .namaLokasiSampling(dto.getNamaLokasiSampling())
                .tanggalSampling(dto.getTanggalSampling())
                .namaKapal(dto.getNamaKapal())
                .daerahPenangkapan(dto.getDaerahPenangkapan())
                .namaAlatTangkap(dto.getUuidAlatTangkap())
                .penampung(dto.isPenampung())
                .penangkap(dto.isPenangkap())
                .totalTangkapanVolume(dto.getTotalTangkapanVolume())
                .totalTangkapanIndividu(dto.getTotalTangkapanIndividu())
                .totalSampelVolume(dto.getTotalSampelVolume())
                .totalSampelIndividu(dto.getTotalSampelIndividu())
                .dataSampleDetail(deTransformSizeSampleDetail(dto.getDataSampleDetail()))
                .dataUkuranDetail(deTransformSizeDetail(dto.getDataUkuranDetail()))
                .statusDokumen(deTransformDocumentStatus(dto.getStatusDokumen()))
                .wpp(dto.getWpp())
                .id(decodeId(dto.getUuid()))
                .build();
        return size;
    }


    public List<OperationalOnFishingToolSpecification3rdPartyDTO> deTransformOperationalSpecification
            (List<OperationalOnFishingToolSpecification> onSpecification, String alatTangkap) {
        return onSpecification.stream().map(dto -> {
            OperationalOnFishingToolSpecification3rdPartyDTO specification = OperationalOnFishingToolSpecification3rdPartyDTO.builder()
                    .spesifikasi(dto.getSpesifikasi())
                    .nilaiSpesifikasi(dto.getNilaiSpesifikasi())
                    .satuanSpesifikasi(dto.getSatuanSpesifikasi())
                    .id(decodeId(dto.getUuid()))
                    .build();
            return specification;
        }).collect(Collectors.toList());
    }


    public List<OperationalCatchDetails3rdPartyDTO> deTransformOperationalCatch(List<OperationalCatchDetails> operationalDetails) {
        return operationalDetails.stream().map(dto -> {
            OperationalCatchDetails3rdPartyDTO details = OperationalCatchDetails3rdPartyDTO.builder()
                    .namaSpesies(dto.getUuidSpesies())
                    .kodeFao(dto.getKodeFao())
                    .totalBeratKg(dto.getTotalBeratKg())
                    .totalBeratEkor(dto.getTotalBeratEkor())
                    .segar(dto.isSegar())
                    .beku(dto.isBeku())
                    .asin(dto.isAsin())
                    .loin(dto.isLoin())
                    .rebus(dto.isRebus())
                    .id(decodeId(dto.getUuid()))
                    .build();

            return details;
        }).collect(Collectors.toList());
    }


    public List<Operational3rdPartyDTO> deTransformOperationals(List<Operational> operationals) {
        return operationals.stream().map(dto -> deTransformOperational(dto)).collect(Collectors.toList());
    }


    public Operational3rdPartyDTO deTransformOperational(Operational dto) {
        Operational3rdPartyDTO operational = Operational3rdPartyDTO.builder()
                .namaLokasiPendaratan(dto.getNamaLokasiPendaratan())
                .namaSumberDaya(dto.getUuidSumberDaya())
                .namaPencatat(dto.getUuidEnumerator())
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
                .namaAlatTangkap(dto.getUuidAlatTangkap())
                .materialAlatTangkap(dto.getMaterial())
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
                .dataSpesifikasiAlatTangkap(deTransformOperationalSpecification(dto.getDataSpesifikasiAlatTangkap(), dto.getUuidAlatTangkap()))
                .dataOperasionalDetailTangkapan(deTransformOperationalCatch(dto.getDataOperasionalDetailTangkapan()))
                .jumlahTangkapanVolume(dto.getJumlahTangkapanVolume())
                .jumlahTangkapanIndividu(dto.getJumlahTangkapanIndividu())
                .lamaPerendaman(dto.getLamaPerendaman())
                .statusDokumen(deTransformDocumentStatus(dto.getStatusDokumen()))
                .wpp(dto.getWpp())
                .id(decodeId(dto.getUuid()))
                .build();

        return operational;
    }

    public List<LandingCatchDetail3rdPartyDTO> deTransformLandingCatchDetail(List<LandingCatchDetail> catchDetails) {

        return catchDetails.stream().map(dto -> {
            LandingCatchDetail3rdPartyDTO catchDetail = LandingCatchDetail3rdPartyDTO.builder()
                    .namaSpesies(dto.getUuidSpesies())
                    .tangkapanVolume(dto.getTangkapanVolume())
                    .tangkapanIndividu(dto.getTangkapanIndividu())
                    .id(decodeId(dto.getUuid()))
                    .build();
            return catchDetail;
        }).collect(Collectors.toList());
    }


    public List<LandingDetail3rdPartyDTO> deTransformLandingDetail(List<LandingDetail> details) {

        return details.stream().map(dto -> {
            LandingDetail3rdPartyDTO detail = LandingDetail3rdPartyDTO.builder()
                    .namaKapal(dto.getNamaKapal())
                    .penampung(dto.isPenampung())
                    .penangkap(dto.isPenangkap())
                    .jumlahKapalPenangkap(dto.getJumlahKapalPenangkap())
                    .namaAlatTangkap(dto.getUuidAlatTangkap())
                    .jumlahSetting(dto.getJumlahSetting())
                    .jumlahMataPancing(dto.getJumlahMataPancing())
                    .rumpon(dto.isRumpon())
                    .cahaya(dto.isCahaya())
                    .daerahPenangkapan(dto.getDaerahPenangkapan())
                    .jumlahHariPerTrip(dto.getJumlahHariPerTrip())
                    .jumlahHariMenangkap(dto.getJumlahHariMenangkap())
                    .totalTangkapanVolume(dto.getTotalTangkapanVolume())
                    .totalTangkapanIndividu(dto.getTotalTangkapanIndividu())
                    .dataRincianTangkapanPendaratan(deTransformLandingCatchDetail(dto.getDataRincianTangkapanPendaratan()))
                    .id(decodeId(dto.getUuid()))
                    .build();
            return detail;
        }).collect(Collectors.toList());
    }


    public Landing3rdPartyDTO deTransformLanding(Landing landing) {

        Landing3rdPartyDTO landingDTO = Landing3rdPartyDTO.builder()
                .tanggalPendaratan(landing.getTanggalPendaratan())
                .namaLokasiPendaratan(landing.getNamaLokasiPendaratan())
                .namaSumberDaya(landing.getUuidSumberDaya())
                .namaPencatat(landing.getUuidEnumerator())
                .dataRincianPendaratan(deTransformLandingDetail(landing.getDataRincianPendaratan()))
                .dataOperasional(deTransformOperationals(landing.getDataOperasional()))
                .dataUkuran(deTransformSizes(landing.getDataUkuran()))
                .dataReproduksi(deTransformReproductions(landing.getDataReproduksi()))
                .statusDokumen(deTransformDocumentStatus(landing.getStatusDokumen()))
                .wpp(landing.getWpp())
                .id(decodeId(landing.getUuid()))
                .build();

        return landingDTO;
    }


    public List<Landing3rdPartyDTO> deTransformLandings(List<Landing> landings) {
        return landings.stream().map(landing -> deTransformLanding(landing)).collect(Collectors.toList());
    }


}
