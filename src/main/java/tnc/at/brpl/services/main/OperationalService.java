package tnc.at.brpl.services.main;

import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.models.master.Enumerator;
import tnc.at.brpl.repositories.main.OperationalRepository;
import tnc.at.brpl.services.main.listeners.OperationalListener;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.service.ServiceModel;

import javax.persistence.criteria.*;
import java.util.Date;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class OperationalService extends ServiceModel<Operational, OperationalRepository>
        implements OperationalListener {


    @Override
    public boolean isDuplicated(Operational operational) {
        long d = repository.countDuplicateData(
                operational.getNamaLokasiPendaratan(),
                operational.getUuidSumberDaya(),
                operational.getUuidEnumerator(),
                operational.getTanggalSampling(),
                operational.getNamaKapal(),
                operational.getTanggalBerangkat(),
                operational.getTandaSelar(),
                operational.getTanggalKembali(),
                operational.getNamaPemilikKapal(),
                operational.getPelabuhanAsal(),
                operational.getNamaKapten(),
                operational.getJumlahAbk(),
                operational.getPanjangKapal(),
                operational.getMaterialKapal(),
                operational.getDayaCahaya(),
                operational.getBobotKapal(),
                operational.isKapalBantu(),
                operational.getUkuranKapalBantu(),
                operational.isKapalAndon(),
                operational.getAsalKapalAndon(),
                operational.getJumlahPalka(),
                operational.getJumlahBoks(),
                operational.getMesinUtama(),
                operational.isFreezer(),
                operational.getKapasitasFreezer(),
                operational.getKapasitasPalkaBoks(),
                operational.getMesinBantu(),
                operational.isGps(),
                operational.getJenisGps(),
                operational.getUuidAlatTangkap(),
                operational.getMaterial(),
                operational.getJumlahAlatTangkapYangDioperasikan(),
                operational.getJumlahSetting(),
                operational.getKedalamanAirMulai(),
                operational.getKedalamanAirHingga(),
                operational.getDaerahPenangkapan(),
                operational.getJumlahHariPerTrip(),
                operational.getJumlahHariMenangkap(),
                operational.getJenisRumpon(),
                operational.getJumlahBalokEs(),
                operational.getJumlahRumponDikunjungi(),
                operational.getJumlahRumponBerhasil(),
                operational.getWaktuMemancing(),
                operational.getSumberInformasi(),
                operational.getJumlahTangkapanUntukDimakanDilautVolume(),
                operational.getJumlahTangkapanUntukDimakanDilautIndividu(),
                operational.getJumlahTangkapanVolume(),
                operational.getJumlahTangkapanIndividu(),
                operational.getOrganisasi(),
                operational.getLamaPerendaman(),
                operational.getWpp()
        );

        return (d > 0);
    }

    @Override
    public Page<Operational> findAllByUuidPengupload(Pageable var1, String uuidPengupload) {
        return repository.findAllByUuidPenguploadOrderByDibuatPadaTanggalAsc(var1, uuidPengupload);
    }

    @Override
    public Page<Operational> findAllByUuidPenguploadOrStatusDokumenNot(Pageable var1, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.findAllByUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(var1, uuidPengupload, statusDokumenNot);
    }

    @Override
    public Page<Operational> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNot(Pageable var1, String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(var1, uuidSumberDaya, uuidPengupload, statusDokumenNot);
    }


    @Override
    public Page<Operational> findAllByWppOrUuidPenguploadOrStatusDokumenNot(Pageable var1, String wpp, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.findAllByWppOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(var1, wpp, uuidPengupload, statusDokumenNot);
    }

    @Override
    public Page<Operational> fetchingDataAsForExportExcel(Pageable var1, String organisasi,
                                                          String gridDaerahPenangkapan,
                                                          String namaKapal,
                                                          String namaPemilikKapal,
                                                          String namaKapten,
                                                          String pelabuhanAsal,
                                                          String alatTangkap,
                                                          DocumentStatus status,
                                                          String wpp, String sumberdaya,
                                                          String uuidPengupload,
                                                          String pencatat, String lokasiPendaratan, Date tanggalSamplingMulai,
                                                          Date tanggalSamplingHingga,
                                                          Date tanggalBerangkatMulai,
                                                          Date tanggalBerangkatHingga,
                                                          Date tanggalKembaliMulai,
                                                          Date tanggalKembaliHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsSuperuser(organisasi, gridDaerahPenangkapan,
                    namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status,
                    wpp, sumberdaya, uuidPengupload, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcel(pageable,
                organisasi, gridDaerahPenangkapan, namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp, sumberdaya,
                uuidPengupload, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }

    @Override
    public Page<Operational> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1, DocumentStatus status,
                                                                        String wpp, String uuidPengupload, String organisasi,
                                                                          String gridDaerahPenangkapan,
                                                                          String namaKapal,
                                                                          String namaPemilikKapal,
                                                                          String namaKapten,
                                                                          String pelabuhanAsal,
                                                                          String alatTangkap,
                                                                          String sumberdaya, String pencatat, String lokasiPendaratan, Date tanggalSamplingMulai,
                                                                          Date tanggalSamplingHingga, Date tanggalBerangkatMulai,
                                                                          Date tanggalBerangkatHingga,
                                                                          Date tanggalKembaliMulai,
                                                                          Date tanggalKembaliHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsValidatorNGO( status, wpp, uuidPengupload,  organisasi, gridDaerahPenangkapan,
                    namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap,  sumberdaya, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcelAsValidatorNGO(pageable, status, wpp,
                 uuidPengupload,  organisasi, gridDaerahPenangkapan,
                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap,  sumberdaya, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }


    @Override
    public Page<Operational> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1, String wpp, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
                                                                          String gridDaerahPenangkapan,
                                                                          String namaKapal,
                                                                          String namaPemilikKapal,
                                                                          String namaKapten,
                                                                          String pelabuhanAsal,
                                                                          String alatTangkap,
                                                                          DocumentStatus status,
                                                                          String sumberdaya, String pencatat, String lokasiPendaratan, Date tanggalSamplingMulai,
                                                                          Date tanggalSamplingHingga, Date tanggalBerangkatMulai,
                                                                          Date tanggalBerangkatHingga,
                                                                          Date tanggalKembaliMulai,
                                                                          Date tanggalKembaliHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsValidatorPJWPP(wpp, uuidPengupload, organisasi, gridDaerahPenangkapan,
                    namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, sumberdaya, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable,
                wpp, uuidPengupload, organisasi, gridDaerahPenangkapan,
                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, sumberdaya, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }


    @Override
    public Page<Operational> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1, String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot,
                                                                             String organisasi,
                                                                             String gridDaerahPenangkapan,
                                                                             String namaKapal,
                                                                             String namaPemilikKapal,
                                                                             String namaKapten,
                                                                             String pelabuhanAsal,
                                                                             String alatTangkap,
                                                                             DocumentStatus status,
                                                                             String wpp, String pencatat, String lokasiPendaratan, Date tanggalSamplingMulai,
                                                                             Date tanggalSamplingHingga, Date tanggalBerangkatMulai,
                                                                             Date tanggalBerangkatHingga,
                                                                             Date tanggalKembaliMulai,
                                                                             Date tanggalKembaliHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsValidatorPeneliti(uuidSumberDaya, uuidPengupload, organisasi, gridDaerahPenangkapan,
                    namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable,
                uuidSumberDaya, uuidPengupload, organisasi, gridDaerahPenangkapan,
                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }


    @Override
    public Page<Operational> fetchingDataAsNormalUser(Pageable var1, String uuidPengupload, String organisasi,
                                                      String gridDaerahPenangkapan,
                                                      String namaKapal,
                                                      String namaPemilikKapal,
                                                      String namaKapten,
                                                      String pelabuhanAsal,
                                                      String alatTangkap,
                                                      DocumentStatus status,
                                                      String lokasiPendaratan, Date tanggalSamplingMulai,
                                                      Date tanggalSamplingHingga, Date tanggalBerangkatMulai,
                                                      Date tanggalBerangkatHingga,
                                                      Date tanggalKembaliMulai,
                                                      Date tanggalKembaliHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countDataAsNormalUser(uuidPengupload, organisasi, gridDaerahPenangkapan,
                    namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi, gridDaerahPenangkapan,
                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }


    @Override
    public long countAsSuperuser(String organisasi,
                                 String gridDaerahPenangkapan,
                                 String namaKapal,
                                 String namaPemilikKapal,
                                 String namaKapten,
                                 String pelabuhanAsal,
                                 String alatTangkap,
                                 DocumentStatus status,
                                 String wpp, String sumberdaya,
                                 String uuidPengupload,
                                 String pencatat, String lokasiPendaratan, Date tanggalSamplingMulai,
                                 Date tanggalSamplingHingga, Date tanggalBerangkatMulai,
                                 Date tanggalBerangkatHingga,
                                 Date tanggalKembaliMulai,
                                 Date tanggalKembaliHingga) {
        return repository.countAsSuperuser(organisasi, gridDaerahPenangkapan,
                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp, sumberdaya, uuidPengupload,
                pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }


    @Override
    public long countAsValidatorPJWPP(String wpp, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
                                      String gridDaerahPenangkapan,
                                      String namaKapal,
                                      String namaPemilikKapal,
                                      String namaKapten,
                                      String pelabuhanAsal,
                                      String alatTangkap,
                                      DocumentStatus status,
                                      String sumberdaya, String pencatat, String lokasiPendaratan, Date tanggalSamplingMulai,
                                      Date tanggalSamplingHingga, Date tanggalBerangkatMulai,
                                      Date tanggalBerangkatHingga,
                                      Date tanggalKembaliMulai,
                                      Date tanggalKembaliHingga) {
        return repository.countAsValidatorPJWPP(wpp, uuidPengupload, organisasi, gridDaerahPenangkapan,
                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, sumberdaya, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }


    @Override
    public long countAsValidatorNGO(DocumentStatus status,
                                    String wpp, String uuidPengupload, String organisasi,
                                      String gridDaerahPenangkapan,
                                      String namaKapal,
                                      String namaPemilikKapal,
                                      String namaKapten,
                                      String pelabuhanAsal,
                                      String alatTangkap,
                                      String sumberdaya, String pencatat, String lokasiPendaratan, Date tanggalSamplingMulai,
                                      Date tanggalSamplingHingga, Date tanggalBerangkatMulai,
                                      Date tanggalBerangkatHingga,
                                      Date tanggalKembaliMulai,
                                      Date tanggalKembaliHingga) {
        return repository.countAsValidatorNGO(status, wpp, uuidPengupload, organisasi, gridDaerahPenangkapan,
                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, sumberdaya, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }


    @Override
    public long countAsValidatorPeneliti(String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
                                         String gridDaerahPenangkapan,
                                         String namaKapal,
                                         String namaPemilikKapal,
                                         String namaKapten,
                                         String pelabuhanAsal,
                                         String alatTangkap,
                                         DocumentStatus status,
                                         String wpp, String pencatat, String lokasiPendaratan, Date tanggalSamplingMulai,
                                         Date tanggalSamplingHingga, Date tanggalBerangkatMulai,
                                         Date tanggalBerangkatHingga,
                                         Date tanggalKembaliMulai,
                                         Date tanggalKembaliHingga) {
        return repository.countAsValidatorPeneliti(uuidSumberDaya, uuidPengupload, organisasi, gridDaerahPenangkapan,
                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp, pencatat, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }


    @Override
    public long countDataAsNormalUser(String uuidPengupload, String organisasi,
                                      String gridDaerahPenangkapan,
                                      String namaKapal,
                                      String namaPemilikKapal,
                                      String namaKapten,
                                      String pelabuhanAsal,
                                      String alatTangkap,
                                      DocumentStatus status,
                                      String lokasiPendaratan, Date tanggalSamplingMulai,
                                      Date tanggalSamplingHingga, Date tanggalBerangkatMulai,
                                      Date tanggalBerangkatHingga,
                                      Date tanggalKembaliMulai,
                                      Date tanggalKembaliHingga) {
        return repository.countDataAsNormalUser(uuidPengupload, organisasi, gridDaerahPenangkapan,
                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, lokasiPendaratan, tanggalSamplingMulai, tanggalSamplingHingga, tanggalBerangkatMulai, tanggalBerangkatHingga, tanggalKembaliMulai, tanggalKembaliHingga);
    }


//    @Override
//    public Page<Operational> fetchingDataAsForExportExcel() {
//        long size = repository.count();
//        size = (size == 0) ? 1 : size;
//        Pageable pageable = new PageRequest(0, (int) size);
//        return repository.fetchingDataAsForExportExcel(pageable);
//    }
//
//    @Override
//    public Page<Operational> fetchingDataAsForExportExcelUsingSpecification() {
////        Specification<Operational> specification = (root, criteriaQuery, criteriaBuilder) -> {
////            return criteriaBuilder.and(criteriaBuilder.equal(root.get("organisasi"), "BRPL"), criteriaBuilder.equal(root.get("namaLokasiPendaratan"), "desa"));
////        };
//
//
//        long size = repository.count();
////        long size = 200;
//        size = (size == 0) ? 1 : size;
//        Pageable pageable = new PageRequest(0, (int) size);
//
//        return repository.fetchingDataAsForExportExcel(pageable);
//    }


}
