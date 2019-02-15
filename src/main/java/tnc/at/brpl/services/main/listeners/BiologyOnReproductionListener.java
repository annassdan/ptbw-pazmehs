package tnc.at.brpl.services.main.listeners;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.service.ServiceListener;

import java.util.Date;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public interface BiologyOnReproductionListener extends ServiceListener<BiologyOnReproduction> {

    boolean isDuplicated(BiologyOnReproduction biologyOnReproduction);

    Page<BiologyOnReproduction> findAllByUuidPengupload(Pageable var1, String uuidPengupload);

    Page<BiologyOnReproduction> findAllByUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                          String uuidPengupload,
                                                                          DocumentStatus statusDokumenNot);

    Page<BiologyOnReproduction> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                                          String uuidSumberDaya,
                                                                                          String uuidPengupload,
                                                                                          DocumentStatus statusDokumenNot);


    Page<BiologyOnReproduction> findAllByWppOrUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                               String wpp,
                                                                               String uuidPengupload,
                                                                               DocumentStatus statusDokumenNot);

//    Page<BiologyOnReproduction> fetchingDataAsForExportExcel();


    Page<BiologyOnReproduction> fetchingDataAsForExportExcel(Pageable var1, String organisasi,
                                                             DocumentStatus status,
                                                             String wpp,
                                                             String sumberdaya,
                                                             String uuidPengupload,
                                                             String pencatat,
                                                             String namaLokasiSampling,
                                                             String namaKapal,
                                                             String alatTangkap,
                                                             String spesies,
                                                             String gridDaerahPenangkapan,
                                                             Date tanggalSamplingMulai,
                                                             Date tanggalSamplingHingga);

//    Page<BiologyOnReproduction> fetchingDataAsForExportExcel(Pageable var1, String organisasi,
//                                                             DocumentStatus status,
//                                                             String wpp,
//                                                             String sumberdaya,
//                                                             String pencatat,
//                                                             String namaLokasiSampling,
//                                                             String namaKapal,
//                                                             String alatTangkap,
//                                                             String spesies,
//                                                             String gridDaerahPenangkapan);


    Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1, String wpp,
                                                                             String uuidPengupload,
                                                                             DocumentStatus statusDokumenNot,
                                                                             String organisasi,
                                                                             DocumentStatus status,
                                                                             String sumberdaya,
                                                                             String pencatat,
                                                                             String namaLokasiSampling,
                                                                             String namaKapal,
                                                                             String alatTangkap,
                                                                             String spesies,
                                                                             String gridDaerahPenangkapan,
                                                                             Date tanggalSamplingMulai,
                                                                             Date tanggalSamplingHingga);


    Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1,
                                                                           DocumentStatus status,
                                                                           String wpp,
                                                                           String uuidPengupload,
                                                                           String organisasi,
                                                                           String sumberdaya,
                                                                           String pencatat,
                                                                           String namaLokasiSampling,
                                                                           String namaKapal,
                                                                           String alatTangkap,
                                                                           String spesies,
                                                                           String gridDaerahPenangkapan,
                                                                           Date tanggalSamplingMulai,
                                                                           Date tanggalSamplingHingga);

//    Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1, String wpp,
//                                                                             String uuidPengupload,
//                                                                             DocumentStatus statusDokumenNot,
//                                                                             String organisasi,
//                                                                             DocumentStatus status,
//                                                                             String sumberdaya,
//                                                                             String pencatat,
//                                                                             String namaLokasiSampling,
//                                                                             String namaKapal,
//                                                                             String alatTangkap,
//                                                                             String spesies,
//                                                                             String gridDaerahPenangkapan);


    Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1, String uuidSumberDaya,
                                                                                String uuidPengupload,
                                                                                DocumentStatus statusDokumenNot,
                                                                                String organisasi,
                                                                                DocumentStatus status,
                                                                                String wpp,
                                                                                String pencatat,
                                                                                String namaLokasiSampling,
                                                                                String namaKapal,
                                                                                String alatTangkap,
                                                                                String spesies,
                                                                                String gridDaerahPenangkapan,
                                                                                Date tanggalSamplingMulai,
                                                                                Date tanggalSamplingHingga);

//
//    Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1, String uuidSumberDaya,
//                                                                                String uuidPengupload,
//                                                                                DocumentStatus statusDokumenNot,
//                                                                                String organisasi,
//                                                                                DocumentStatus status,
//                                                                                String wpp,
//                                                                                String pencatat,
//                                                                                String namaLokasiSampling,
//                                                                                String namaKapal,
//                                                                                String alatTangkap,
//                                                                                String spesies,
//                                                                                String gridDaerahPenangkapan);


    Page<BiologyOnReproduction> fetchingDataAsNormalUser(Pageable var1, String uuidPengupload,
                                                         String organisasi,
                                                         DocumentStatus status,
                                                         String namaLokasiSampling,
                                                         String namaKapal,
                                                         String alatTangkap,
                                                         String spesies,
                                                         String gridDaerahPenangkapan,
                                                         Date tanggalSamplingMulai,
                                                         Date tanggalSamplingHingga);

//    Page<BiologyOnReproduction> fetchingDataAsNormalUser(Pageable var1, String uuidPengupload,
//                                                         String organisasi,
//                                                         DocumentStatus status,
//                                                         String namaLokasiSampling,
//                                                         String namaKapal,
//                                                         String alatTangkap,
//                                                         String spesies,
//                                                         String gridDaerahPenangkapan);


    long countAsSuperuser(String organisasi,
                          DocumentStatus status,
                          String wpp,
                          String sumberdaya,
                          String uuidPengupload,
                          String pencatat,
                          String namaLokasiSampling,
                          String namaKapal,
                          String alatTangkap,
                          String spesies,
                          String gridDaerahPenangkapan,
                          Date tanggalSamplingMulai,
                          Date tanggalSamplingHingga);

//    long countAsSuperuser(String organisasi,
//                          DocumentStatus status,
//                          String wpp,
//                          String sumberdaya,
//                          String pencatat,
//                          String namaLokasiSampling,
//                          String namaKapal,
//                          String alatTangkap,
//                          String spesies,
//                          String gridDaerahPenangkapan);


    long countAsValidatorPJWPP(String wpp,
                               String uuidPengupload,
                               DocumentStatus statusDokumenNot,
                               String organisasi,
                               DocumentStatus status,
                               String sumberdaya,
                               String pencatat,
                               String namaLokasiSampling,
                               String namaKapal,
                               String alatTangkap,
                               String spesies,
                               String gridDaerahPenangkapan,
                               Date tanggalSamplingMulai,
                               Date tanggalSamplingHingga);

    long countAsValidatorNGO(
            DocumentStatus status,
            String wpp,
            String uuidPengupload,
            String organisasi,
            String sumberdaya,
            String pencatat,
            String namaLokasiSampling,
            String namaKapal,
            String alatTangkap,
            String spesies,
            String gridDaerahPenangkapan,
            Date tanggalSamplingMulai,
            Date tanggalSamplingHingga);


//    long countAsValidatorPJWPP(String wpp,
//                               String uuidPengupload,
//                               DocumentStatus statusDokumenNot,
//                               String organisasi,
//                               DocumentStatus status,
//                               String sumberdaya,
//                               String pencatat,
//                               String namaLokasiSampling,
//                               String namaKapal,
//                               String alatTangkap,
//                               String spesies,
//                               String gridDaerahPenangkapan);


    long countAsValidatorPeneliti(String uuidSumberDaya,
                                  String uuidPengupload,
                                  DocumentStatus statusDokumenNot,
                                  String organisasi,
                                  DocumentStatus status,
                                  String wpp,
                                  String pencatat,
                                  String namaLokasiSampling,
                                  String namaKapal,
                                  String alatTangkap,
                                  String spesies,
                                  String gridDaerahPenangkapan,
                                  Date tanggalSamplingMulai,
                                  Date tanggalSamplingHingga);

//    long countAsValidatorPeneliti(String uuidSumberDaya,
//                                  String uuidPengupload,
//                                  DocumentStatus statusDokumenNot,
//                                  String organisasi,
//                                  DocumentStatus status,
//                                  String wpp,
//                                  String pencatat,
//                                  String namaLokasiSampling,
//                                  String namaKapal,
//                                  String alatTangkap,
//                                  String spesies,
//                                  String gridDaerahPenangkapan);


    long countDataAsNormalUser(String uuidPengupload,
                               String organisasi,
                               DocumentStatus status,
                               String namaLokasiSampling,
                               String namaKapal,
                               String alatTangkap,
                               String spesies,
                               String gridDaerahPenangkapan,
                               Date tanggalSamplingMulai,
                               Date tanggalSamplingHingga);

//    long countDataAsNormalUser(String uuidPengupload,
//                               String organisasi,
//                               DocumentStatus status,
//                               String namaLokasiSampling,
//                               String namaKapal,
//                               String alatTangkap,
//                               String spesies,
//                               String gridDaerahPenangkapan);


}
