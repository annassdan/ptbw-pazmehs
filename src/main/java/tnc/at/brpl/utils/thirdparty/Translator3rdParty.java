package tnc.at.brpl.utils.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.main.*;
import tnc.at.brpl.models.main.dto.*;
import tnc.at.brpl.services.thirdparty.Utility3rdPartyService;
import tnc.at.brpl.services.thirdparty.util.SumberDaya;
import tnc.at.brpl.utils.data.DocumentStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Translator3rdParty {

    final List<String> lengthTypes = Arrays.asList("CL", "CW", "DW", "FL", "ML", "SL", "TL", "OT");

    final List<String> wpps = Arrays.asList("571", "572", "573", "711", "712", "713", "714", "715", "716", "717", "718");

    Utility3rdPartyService utility3rdPartyService = new Utility3rdPartyService();

    private Date auditDate;

    SysUser user;

    public Translator3rdParty() {
    }

    public Translator3rdParty(Date auditDate, SysUser user) {
        this.auditDate = auditDate;
        this.user = user;
    }


    private boolean lengthTypesValid(String lt) {
        return lengthTypes.stream().anyMatch(s -> s.toUpperCase().equals(lt.toUpperCase()));
    }


    private boolean sexValid(String sex) {
        return (sex.toUpperCase().equals("M") || sex.toUpperCase().equals("F"));
    }

    private boolean tkgValid(String tkg) {
        return utility3rdPartyService.tkg().stream().anyMatch(tkgObject -> tkgObject.getTkg().toUpperCase().equals(tkg.toUpperCase()));
    }

    private boolean sumberdayaValid(String sd) {
        return utility3rdPartyService.sumberDaya().stream().anyMatch(s -> s.getSumberDaya().toUpperCase().equals(sd.toUpperCase()));
    }


    private boolean alatTangkapValid(String al) {
        return utility3rdPartyService.sumberDaya().stream()
                .flatMap(sumberDaya -> sumberDaya.getDaftarAlatTangkap().stream())
                .collect(Collectors.toList()).stream()
                .anyMatch(alatTangkap -> alatTangkap.getAlatTangkap().toUpperCase().equals(al.toUpperCase()));
    }

    private boolean wppValid(String wpp) {
        return wpps.stream().anyMatch(s -> s.toUpperCase().equals(wpp.toUpperCase()));
    }


    private boolean alatTangkapValid(String sd, String al) {
        Optional<SumberDaya> daya = utility3rdPartyService.sumberDaya().stream()
                .filter(sumberDaya -> sumberDaya.getSumberDaya().toUpperCase().equals(sd.toUpperCase()))
                .findFirst();

        if (!daya.isPresent())
            return false;

        return  daya.get().getDaftarAlatTangkap().stream()
                .anyMatch(alatTangkap -> alatTangkap.getAlatTangkap().toUpperCase().equals(al.toUpperCase()));
    }


    private void checkSpecificationOfAlatTangkap(String sd, String al, OperationalOnFishingToolSpecification specification) {
        Optional<SumberDaya> daya = utility3rdPartyService.sumberDaya().stream()
                .filter(sumberDaya -> sumberDaya.getSumberDaya().toUpperCase().equals(sd.toUpperCase()))
                .findFirst();
//
//        if (!daya.isPresent())
//            return false;
//
//        return  daya.get().getDaftarAlatTangkap().stream()
//                .anyMatch(alatTangkap -> alatTangkap.getAlatTangkap().toUpperCase().equals(al.toUpperCase()));
    }

    private void checkDocumentStatus(DocumentStatus status) {
        if (status == DocumentStatus.PENDING || status == DocumentStatus.WAITING) {
        } else {
            throw new ResourceInternalServerErrorException("Ada data dengan status dokumen '"+ status.toString() +"', tidak dapat diproses. Silahkan gunakan status dokumen 'PENDING' atau 'WAITING'");
        }
    }


    public List<BiologyOnReproductionDetail> transformReproductionDetail(List<BiologyOnReproductionDetail3rdPatyDTO> reproductionDetail3rdPatyDTO) {

        if (user == null || auditDate == null)
            return null;

        return reproductionDetail3rdPatyDTO.stream().map(dto -> {

            if (!lengthTypesValid(dto.getTipePanjang()))
                throw new ResourceInternalServerErrorException("Ada data Reproduksi, dengan tipe panjang '" + dto.getTipePanjang() + "' yang tidak valid dengan ketentuan BRPL");

            if (!sexValid(dto.getJenisKelamin()))
                throw new ResourceInternalServerErrorException("Ada jenis kelamin ('" + dto.getJenisKelamin() + "') tidak valid");

            if (dto.getPanjang() < 0)
                throw new ResourceInternalServerErrorException("Ada data Reproduksi yang mempunyai panjang dibawah 0");

            if (dto.getBerat() < 0)
                throw new ResourceInternalServerErrorException("Ada data Reproduksi yang mempunyai berat dibawah 0");

            if (!tkgValid(dto.getTkg()))
                throw new ResourceInternalServerErrorException("Ada data Reproduksi, dengan nilai TKG '" + dto.getTkg() + "' tidak valid");


            BiologyOnReproductionDetail detail = BiologyOnReproductionDetail.builder()
                    .panjang(dto.getPanjang())
                    .tipePanjang(dto.getTipePanjang())
                    .jenisKelamin(dto.getJenisKelamin())
                    .berat(dto.getBerat())
                    .beratIsiPerut(dto.getBeratIsiPerut())
                    .tkg(dto.getTkg())
                    .build();
            detail.setUuid(dto.getId());
            detail.setDibuatPadaTanggal(auditDate);
            detail.setTerakhirDiubahPadaTanggal(auditDate);
            detail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return detail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnReproduction> transformReproduction(List<BiologyOnReproduction3rdPartyDTO> reproduction3rdPartyDTOs, SysUser sysUser) {

        if (user == null || auditDate == null)
            return null;

        return reproduction3rdPartyDTOs.stream().map(dto -> {

            if (!wppValid(dto.getWpp()))
                throw new ResourceInternalServerErrorException("Ada data Reproduksi, dengan WPP yang tidak valid");

            if (!sumberdayaValid(dto.getNamaSumberDaya()))
                throw new ResourceInternalServerErrorException("Ada data Reproduksi, dengan sumberdaya '" + dto.getNamaSumberDaya() + "' yang tidak valid dengan ketentuan BRPL");

            if (!alatTangkapValid(dto.getNamaAlatTangkap()))
                throw new ResourceInternalServerErrorException("Ada data Reproduksi, dengan alat tangkap '" + dto.getNamaAlatTangkap() + "' yang tidak valid dengan ketentuan BRPL");

            checkDocumentStatus(dto.getStatusDokumen());

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
                    .uuidPengupload(sysUser.getUuid())
                    .organisasi(sysUser.getOrganisasi())
                    .wpp(dto.getWpp())
                    .terverifikasiOleh("")
                    .untukEksternalTerverifikasiOleh("").build();
            biology.setUuid(dto.getId());
            biology.setDibuatPadaTanggal(auditDate);
            biology.setTerakhirDiubahPadaTanggal(auditDate);
            biology.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return biology;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSizeSampleDetail> transformSizeSampleDetail(List<BiologyOnSizeSampleDetail3rdPartyDTO> sizeSampleDetail3rdPartyDTOs) {

        if (user == null || auditDate == null)
            return null;

        return sizeSampleDetail3rdPartyDTOs.stream().map(dto -> {

            if (!lengthTypesValid(dto.getTipePanjang()))
                throw new ResourceInternalServerErrorException("Ada data Sample Ukuran, dengan tipe panjang '" + dto.getTipePanjang() + "' yang tidak valid dengan ketentuan BRPL");

            if (dto.getSampleVolume() < 0)
                throw new ResourceInternalServerErrorException("Ada data Ukuran, untuk sample pengukuran, yang mempunyai total (Volume) dibawah 0");

            if (dto.getSampleIndividu() < 0)
                throw new ResourceInternalServerErrorException("Ada data Ukuran, untuk sample pengukuran, yang mempunyai total (Individu) dibawah 0");

            BiologyOnSizeSampleDetail sampleDetail = BiologyOnSizeSampleDetail.builder()
                    .uuidSpesies(dto.getNamaSpesies())
                    .sampleVolume(dto.getSampleVolume())
                    .sampleIndividu(dto.getSampleIndividu())
                    .tipePanjang(dto.getTipePanjang())
                    .build();
            sampleDetail.setUuid(dto.getId());
            sampleDetail.setDibuatPadaTanggal(auditDate);
            sampleDetail.setTerakhirDiubahPadaTanggal(auditDate);
            sampleDetail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return sampleDetail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSizeDetail> transformSizeDetail(List<BiologyOnSizeDetail3rdPartyDTO> sizeDetail3rdPartyDTOs) {

        if (user == null || auditDate == null)
            return null;

        return sizeDetail3rdPartyDTOs.stream().map(dto -> {

            if (dto.getPanjang() < 0)
                throw new ResourceInternalServerErrorException("Ada data Ukuran, dengan nama Spesies '"+ dto.getNamaSpesies() +"' yang mempunyai panjang dibawah 0");

            BiologyOnSizeDetail detail = BiologyOnSizeDetail.builder()
                    .uuidSpesies(dto.getNamaSpesies())
                    .panjang(dto.getPanjang())
                    .build();
            detail.setUuid(dto.getId());
            detail.setDibuatPadaTanggal(auditDate);
            detail.setTerakhirDiubahPadaTanggal(auditDate);
            detail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return detail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSize> transformSize(List<BiologyOnSize3rdPartyDTO> size3rdPartyDTOs, SysUser sysUser) {

        if (user == null || auditDate == null)
            return null;

        return size3rdPartyDTOs.stream().map(dto -> {

            if (!wppValid(dto.getWpp()))
                throw new ResourceInternalServerErrorException("Ada data Ukuran, dengan WPP yang tidak valid");

            if (!sumberdayaValid(dto.getNamaSumberDaya()))
                throw new ResourceInternalServerErrorException("Ada data Ukuran, dengan sumberdaya '" + dto.getNamaSumberDaya() + "' yang tidak valid dengan ketentuan BRPL");

            if (!alatTangkapValid(dto.getNamaAlatTangkap()))
                throw new ResourceInternalServerErrorException("Ada data Ukuran, dengan alat tangkap '" + dto.getNamaAlatTangkap() + "' yang tidak valid dengan ketentuan BRPL");

            checkDocumentStatus(dto.getStatusDokumen());


            if (dto.getTotalSampelVolume() < 0)
                throw new ResourceInternalServerErrorException("Ada data Ukuran, jumlah sampel pengukuran (Volume) dibawah 0");

            if (dto.getTotalSampelIndividu() < 0)
                throw new ResourceInternalServerErrorException("Ada data Ukuran, jumlah sampel pengukuran (Individu) dibawah 0");


            if (dto.getTotalTangkapanVolume() < 0)
                throw new ResourceInternalServerErrorException("Terdapat data Ukuran, dengan total tangkapan (Volume) dibawah 0");

            if (dto.getTotalTangkapanIndividu() < 0)
                throw new ResourceInternalServerErrorException("Terdapat data Ukuran, dengan total tangkapan (Individu) dibawah 0");


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
                    .uuidPengupload(sysUser.getUuid())
                    .organisasi(sysUser.getOrganisasi())
                    .wpp(dto.getWpp())
                    .terverifikasiOleh("")
                    .untukEksternalTerverifikasiOleh("")
                    .build();
            size.setUuid(dto.getId());
            size.setDibuatPadaTanggal(auditDate);
            size.setTerakhirDiubahPadaTanggal(auditDate);
            size.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return size;
        }).collect(Collectors.toList());
    }


    public List<OperationalOnFishingToolSpecification> transformOperationalSpecification(List<OperationalOnFishingToolSpecification3rdPartyDTO> specification3rdPartyDTOs, String alatTangkap) {

        if (user == null || auditDate == null)
            return null;

        return specification3rdPartyDTOs.stream().map(dto -> {
            OperationalOnFishingToolSpecification specification = OperationalOnFishingToolSpecification.builder()
                    .uuidAlatTangkap(alatTangkap)
                    .spesifikasi(dto.getSpesifikasi())
                    .nilaiSpesifikasi(dto.getNilaiSpesifikasi())
                    .satuanSpesifikasi(dto.getSatuanSpesifikasi())
                    .build();
            specification.setUuid(dto.getId());
            specification.setDibuatPadaTanggal(auditDate);
            specification.setTerakhirDiubahPadaTanggal(auditDate);
            specification.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return specification;
        }).collect(Collectors.toList());
    }


    public List<OperationalCatchDetails> transformOperationalCatch(List<OperationalCatchDetails3rdPartyDTO> details3rdPartyDTOs) {

        if (user == null || auditDate == null)
            return null;

        return details3rdPartyDTOs.stream().map(dto -> {

            if (dto.getTotalBeratEkor() < 0)
                throw new ResourceInternalServerErrorException("Ada data Operasional, untuk detail tangkapan Kapal, yang mempunyai detail berat tangkapan (Ekor) dibawah 0");

            if (dto.getTotalBeratKg() < 0)
                throw new ResourceInternalServerErrorException("Ada data Operasional, untuk detail tangkapan Kapal, yang mempunyai detail berat tangkapan (Kg) dibawah 0");

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
            details.setDibuatPadaTanggal(auditDate);
            details.setTerakhirDiubahPadaTanggal(auditDate);
            details.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return details;
        }).collect(Collectors.toList());
    }


    public List<Operational> transformOperational(List<Operational3rdPartyDTO> operational3rdPartyDTs, SysUser sysUser) {

        if (user == null || auditDate == null)
            return null;

        return operational3rdPartyDTs.stream().map(dto -> {

            if (!wppValid(dto.getWpp()))
                throw new ResourceInternalServerErrorException("Ada data Operasional, dengan WPP yang tidak valid");

            if (!sumberdayaValid(dto.getNamaSumberDaya()))
                throw new ResourceInternalServerErrorException("Ada data Operasional, dengan sumberdaya '" + dto.getNamaSumberDaya() + "' yang tidak valid dengan ketentuan BRPL");

            if (!alatTangkapValid(dto.getNamaAlatTangkap()))
                throw new ResourceInternalServerErrorException("Ada data Operasional, dengan alat tangkap '" + dto.getNamaAlatTangkap() + "' yang tidak valid dengan ketentuan BRPL");

            checkDocumentStatus(dto.getStatusDokumen());

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
                    .uuidPengupload(sysUser.getUuid())
                    .organisasi(sysUser.getOrganisasi())
                    .wpp(dto.getWpp())
                    .terverifikasiOleh("")
                    .untukEksternalTerverifikasiOleh("")
                    .build();
            operational.setUuid(dto.getId());
            operational.setDibuatPadaTanggal(auditDate);
            operational.setTerakhirDiubahPadaTanggal(auditDate);
            operational.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return operational;
        }).collect(Collectors.toList());
    }


    public List<LandingCatchDetail> transformLandingCatchDetail(List<LandingCatchDetail3rdPartyDTO> catchDetail3rdPartyDTOs) {

        if (user == null || auditDate == null)
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
            catchDetail.setUuid(dto.getId());
            catchDetail.setDibuatPadaTanggal(auditDate);
            catchDetail.setTerakhirDiubahPadaTanggal(auditDate);
            catchDetail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return catchDetail;
        }).collect(Collectors.toList());
    }

    public List<LandingDetail> transformLandingDetail(List<LandingDetail3rdPartyDTO> detail3rdPartyDTOs) {

        if (user == null || auditDate == null)
            return null;

        return detail3rdPartyDTOs.stream().map(dto -> {

            if (!alatTangkapValid(dto.getNamaAlatTangkap()))
                throw new ResourceInternalServerErrorException("Ada data Kapal, dengan alat tangkap '" + dto.getNamaAlatTangkap() + "' yang tidak valid dengan ketentuan BRPL");

            if (dto.getTotalTangkapanVolume() < 0)
                throw new ResourceInternalServerErrorException("Ada data Kapal, yang mempunyai total tangkapan (Volume) dibawah 0");

            if (dto.getTotalTangkapanIndividu() < 0)
                throw new ResourceInternalServerErrorException("Ada data Kapal, yang mempunyai total tangkapan (Individu) dibawah 0");

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
            detail.setDibuatPadaTanggal(auditDate);
            detail.setTerakhirDiubahPadaTanggal(auditDate);
            detail.setDibuatAtauTerakhirDiubahOleh(user.getUuid());
            return detail;
        }).collect(Collectors.toList());
    }

    public Landing transformLanding(Landing3rdPartyDTO landing3rdPartyDTO, SysUser sysUser) {

        if (user == null || auditDate == null)
            return null;

        if (!wppValid(landing3rdPartyDTO.getWpp()))
            throw new ResourceInternalServerErrorException("Pendaratan ini memiliki WPP yang tidak valid");

        if (!sumberdayaValid(landing3rdPartyDTO.getNamaSumberDaya()))
            throw new ResourceInternalServerErrorException("Data Pendaratan, dengan sumberdaya '" + landing3rdPartyDTO.getNamaSumberDaya() + "' yang tidak valid dengan ketentuan BRPL");

        checkDocumentStatus(landing3rdPartyDTO.getStatusDokumen());

        Landing landing = Landing.builder()
                .tanggalPendaratan(landing3rdPartyDTO.getTanggalPendaratan())
                .namaLokasiPendaratan(landing3rdPartyDTO.getNamaLokasiPendaratan())
                .uuidSumberDaya(landing3rdPartyDTO.getNamaSumberDaya())
                .uuidEnumerator(landing3rdPartyDTO.getNamaPencatat())
                .dataRincianPendaratan(transformLandingDetail(landing3rdPartyDTO.getDataRincianPendaratan()))
                .dataOperasional(transformOperational(landing3rdPartyDTO.getDataOperasional(), sysUser))
                .dataUkuran(transformSize(landing3rdPartyDTO.getDataUkuran(), sysUser))
                .dataReproduksi(transformReproduction(landing3rdPartyDTO.getDataReproduksi(), sysUser))
                .userGroup(null)
                .statusDokumen(landing3rdPartyDTO.getStatusDokumen())
                .photoName("")
                .uuidPengupload(sysUser.getUuid())
                .organisasi(sysUser.getOrganisasi())
                .wpp(landing3rdPartyDTO.getWpp())
                .terverifikasiOleh("")
                .untukEksternalTerverifikasiOleh("")
                .build();

        landing.setUuid(landing3rdPartyDTO.getId());
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
                    .id(dto.getUuid())
                    .build();
            return detail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnReproduction3rdPartyDTO> deTransformReproduction(List<BiologyOnReproduction> reproduction) {


        return reproduction.stream().map(dto -> {
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
                    .statusDokumen(dto.getStatusDokumen())
                    .wpp(dto.getWpp())
                    .id(dto.getUuid())
                    .build();

            return biology;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSizeSampleDetail3rdPartyDTO> deTransformSizeSampleDetail(List<BiologyOnSizeSampleDetail> sizeSampleDetail) {

        return sizeSampleDetail.stream().map(dto -> {
            BiologyOnSizeSampleDetail3rdPartyDTO sampleDetail = BiologyOnSizeSampleDetail3rdPartyDTO.builder()
                    .namaSpesies(dto.getUuidSpesies())
                    .sampleVolume(dto.getSampleVolume())
                    .sampleIndividu(dto.getSampleIndividu())
                    .tipePanjang(dto.getTipePanjang())
                    .id(dto.getUuid())
                    .build();

            return sampleDetail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSizeDetail3rdPartyDTO> deTransformSizeDetail(List<BiologyOnSizeDetail> sizeDetail) {

        return sizeDetail.stream().map(dto -> {
            BiologyOnSizeDetail3rdPartyDTO detail = BiologyOnSizeDetail3rdPartyDTO.builder()
                    .namaSpesies(dto.getUuidSpesies())
                    .panjang(dto.getPanjang())
                    .id(dto.getUuid())
                    .build();
            return detail;
        }).collect(Collectors.toList());
    }


    public List<BiologyOnSize3rdPartyDTO> deTransformSize(List<BiologyOnSize> onSize) {

        return onSize.stream().map(dto -> {
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
                    .statusDokumen(dto.getStatusDokumen())
                    .wpp(dto.getWpp())
                    .id(dto.getUuid())
                    .build();
            return size;
        }).collect(Collectors.toList());
    }


    public List<OperationalOnFishingToolSpecification3rdPartyDTO> deTransformOperationalSpecification
            (List<OperationalOnFishingToolSpecification> onSpecification, String alatTangkap) {

        return onSpecification.stream().map(dto -> {
            OperationalOnFishingToolSpecification3rdPartyDTO specification = OperationalOnFishingToolSpecification3rdPartyDTO.builder()
                    .spesifikasi(dto.getSpesifikasi())
                    .nilaiSpesifikasi(dto.getNilaiSpesifikasi())
                    .satuanSpesifikasi(dto.getSatuanSpesifikasi())
                    .id(dto.getUuid())
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
                    .id(dto.getUuid())
                    .build();

            return details;
        }).collect(Collectors.toList());
    }


    public List<Operational3rdPartyDTO> deTransformOperational(List<Operational> operationals) {

        return operationals.stream().map(dto -> {
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
                    .statusDokumen(dto.getStatusDokumen())
                    .wpp(dto.getWpp())
                    .id(dto.getUuid())
                    .build();

            return operational;
        }).collect(Collectors.toList());
    }


    public List<LandingCatchDetail3rdPartyDTO> deTransformLandingCatchDetail(List<LandingCatchDetail> catchDetails) {

        return catchDetails.stream().map(dto -> {
            LandingCatchDetail3rdPartyDTO catchDetail = LandingCatchDetail3rdPartyDTO.builder()
                    .namaSpesies(dto.getUuidSpesies())
                    .tangkapanVolume(dto.getTangkapanVolume())
                    .tangkapanIndividu(dto.getTangkapanIndividu())
                    .id(dto.getUuid())
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
                    .id(dto.getUuid())
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
                .dataOperasional(deTransformOperational(landing.getDataOperasional()))
                .dataUkuran(deTransformSize(landing.getDataUkuran()))
                .dataReproduksi(deTransformReproduction(landing.getDataReproduksi()))
                .statusDokumen(landing.getStatusDokumen())
                .wpp(landing.getWpp())
                .id(landing.getUuid())
                .build();

        return landingDTO;
    }


    public List<Landing3rdPartyDTO> deTransformLandings(List<Landing> landings) {
        return landings.stream().map(landing -> deTransformLanding(landing)).collect(Collectors.toList());
    }

}
