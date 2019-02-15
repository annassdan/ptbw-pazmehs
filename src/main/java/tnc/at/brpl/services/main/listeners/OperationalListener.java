package tnc.at.brpl.services.main.listeners;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.service.ServiceListener;

import java.util.Date;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public interface OperationalListener extends ServiceListener<Operational> {

    boolean isDuplicated(Operational operational);

    Page<Operational> findAllByUuidPengupload(Pageable var1, String uuidPengupload);

    Page<Operational> findAllByUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                String uuidPengupload,
                                                                DocumentStatus statusDokumenNot);

    Page<Operational> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                                String uuidSumberDaya,
                                                                                String uuidPengupload,
                                                                                DocumentStatus statusDokumenNot);


    Page<Operational> findAllByWppOrUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                     String wpp,
                                                                     String uuidPengupload,
                                                                     DocumentStatus statusDokumenNot);


    /**
     * @return
     */
    Page<Operational> fetchingDataAsForExportExcel(Pageable var1, String organisasi,
                                                   String gridDaerahPenangkapan,
                                                   String namaKapal,
                                                   String namaPemilikKapal,
                                                   String namaKapten,
                                                   String pelabuhanAsal,
                                                   String alatTangkap,
                                                   DocumentStatus status,
                                                   String wpp,
                                                   String sumberdaya,
                                                   String uuidPengupload,
                                                   String pencatat,
                                                   String lokasiPendaratan,
                                                   Date tanggalSamplingMulai,
                                                   Date tanggalSamplingHingga,
                                                   Date tanggalBerangkatMulai,
                                                   Date tanggalBerangkatHingga,
                                                   Date tanggalKembaliMulai,
                                                   Date tanggalKembaliHingga);

    Page<Operational> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1,
                                                                 DocumentStatus status,
                                                                 String wpp,
                                                                 String uuidPengupload,
                                                                 String organisasi,
                                                                 String gridDaerahPenangkapan,
                                                                 String namaKapal,
                                                                 String namaPemilikKapal,
                                                                 String namaKapten,
                                                                 String pelabuhanAsal,
                                                                 String alatTangkap,
                                                                 String sumberdaya,
                                                                 String pencatat,
                                                                 String lokasiPendaratan,
                                                                 Date tanggalSamplingMulai,
                                                                 Date tanggalSamplingHingga,
                                                                 Date tanggalBerangkatMulai,
                                                                 Date tanggalBerangkatHingga,
                                                                 Date tanggalKembaliMulai,
                                                                 Date tanggalKembaliHingga);

    Page<Operational> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1, String wpp,
                                                                   String uuidPengupload,
                                                                   DocumentStatus statusDokumenNot,
                                                                   String organisasi,
                                                                   String gridDaerahPenangkapan,
                                                                   String namaKapal,
                                                                   String namaPemilikKapal,
                                                                   String namaKapten,
                                                                   String pelabuhanAsal,
                                                                   String alatTangkap,
                                                                   DocumentStatus status,
                                                                   String sumberdaya,
                                                                   String pencatat,
                                                                   String lokasiPendaratan,
                                                                   Date tanggalSamplingMulai,
                                                                   Date tanggalSamplingHingga,
                                                                   Date tanggalBerangkatMulai,
                                                                   Date tanggalBerangkatHingga,
                                                                   Date tanggalKembaliMulai,
                                                                   Date tanggalKembaliHingga);


    Page<Operational> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1, String uuidSumberDaya,
                                                                      String uuidPengupload,
                                                                      DocumentStatus statusDokumenNot,
                                                                      String organisasi,
                                                                      String gridDaerahPenangkapan,
                                                                      String namaKapal,
                                                                      String namaPemilikKapal,
                                                                      String namaKapten,
                                                                      String pelabuhanAsal,
                                                                      String alatTangkap,
                                                                      DocumentStatus status,
                                                                      String wpp,
                                                                      String pencatat,
                                                                      String lokasiPendaratan,
                                                                      Date tanggalSamplingMulai,
                                                                      Date tanggalSamplingHingga,
                                                                      Date tanggalBerangkatMulai,
                                                                      Date tanggalBerangkatHingga,
                                                                      Date tanggalKembaliMulai,
                                                                      Date tanggalKembaliHingga);


    Page<Operational> fetchingDataAsNormalUser(Pageable var1, String uuidPengupload,
                                               String organisasi,
                                               String gridDaerahPenangkapan,
                                               String namaKapal,
                                               String namaPemilikKapal,
                                               String namaKapten,
                                               String pelabuhanAsal,
                                               String alatTangkap,
                                               DocumentStatus status,
                                               String lokasiPendaratan,
                                               Date tanggalSamplingMulai,
                                               Date tanggalSamplingHingga,
                                               Date tanggalBerangkatMulai,
                                               Date tanggalBerangkatHingga,
                                               Date tanggalKembaliMulai,
                                               Date tanggalKembaliHingga);


    long countAsSuperuser(String organisasi,
                          String gridDaerahPenangkapan,
                          String namaKapal,
                          String namaPemilikKapal,
                          String namaKapten,
                          String pelabuhanAsal,
                          String alatTangkap,
                          DocumentStatus status,
                          String wpp,
                          String sumberdaya,
                          String uuidPengupload,
                          String pencatat,
                          String lokasiPendaratan,
                          Date tanggalSamplingMulai,
                          Date tanggalSamplingHingga,
                          Date tanggalBerangkatMulai,
                          Date tanggalBerangkatHingga,
                          Date tanggalKembaliMulai,
                          Date tanggalKembaliHingga);


    long countAsValidatorPJWPP(String wpp,
                               String uuidPengupload,
                               DocumentStatus statusDokumenNot,
                               String organisasi,
                               String gridDaerahPenangkapan,
                               String namaKapal,
                               String namaPemilikKapal,
                               String namaKapten,
                               String pelabuhanAsal,
                               String alatTangkap,
                               DocumentStatus status,
                               String sumberdaya,
                               String pencatat,
                               String lokasiPendaratan,
                               Date tanggalSamplingMulai,
                               Date tanggalSamplingHingga,
                               Date tanggalBerangkatMulai,
                               Date tanggalBerangkatHingga,
                               Date tanggalKembaliMulai,
                               Date tanggalKembaliHingga);

    long countAsValidatorNGO(DocumentStatus status,
                             String wpp,
                             String uuidPengupload,
                             String organisasi,
                             String gridDaerahPenangkapan,
                             String namaKapal,
                             String namaPemilikKapal,
                             String namaKapten,
                             String pelabuhanAsal,
                             String alatTangkap,
                             String sumberdaya,
                             String pencatat,
                             String lokasiPendaratan,
                             Date tanggalSamplingMulai,
                             Date tanggalSamplingHingga,
                             Date tanggalBerangkatMulai,
                             Date tanggalBerangkatHingga,
                             Date tanggalKembaliMulai,
                             Date tanggalKembaliHingga);


    long countAsValidatorPeneliti(String uuidSumberDaya,
                                  String uuidPengupload,
                                  DocumentStatus statusDokumenNot,
                                  String organisasi,
                                  String gridDaerahPenangkapan,
                                  String namaKapal,
                                  String namaPemilikKapal,
                                  String namaKapten,
                                  String pelabuhanAsal,
                                  String alatTangkap,
                                  DocumentStatus status,
                                  String wpp,
                                  String pencatat,
                                  String lokasiPendaratan,
                                  Date tanggalSamplingMulai,
                                  Date tanggalSamplingHingga,
                                  Date tanggalBerangkatMulai,
                                  Date tanggalBerangkatHingga,
                                  Date tanggalKembaliMulai,
                                  Date tanggalKembaliHingga);


    long countDataAsNormalUser(String uuidPengupload,
                               String organisasi,
                               String gridDaerahPenangkapan,
                               String namaKapal,
                               String namaPemilikKapal,
                               String namaKapten,
                               String pelabuhanAsal,
                               String alatTangkap,
                               DocumentStatus status,
                               String lokasiPendaratan,
                               Date tanggalSamplingMulai,
                               Date tanggalSamplingHingga,
                               Date tanggalBerangkatMulai,
                               Date tanggalBerangkatHingga,
                               Date tanggalKembaliMulai,
                               Date tanggalKembaliHingga);

}
