package tnc.at.brpl.services.main.listeners;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.service.ServiceListener;

import java.util.Date;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public interface BiologyOnSizeListener extends ServiceListener<BiologyOnSize> {

    boolean isDuplicated(BiologyOnSize biologyOnSize);

    Page<BiologyOnSize> findAllByUuidPengupload(Pageable var1, String uuidPengupload);

    Page<BiologyOnSize> findAllByUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                  String uuidPengupload,
                                                                  DocumentStatus statusDokumenNot);

    Page<BiologyOnSize> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                                  String uuidSumberDaya,
                                                                                  String uuidPengupload,
                                                                                  DocumentStatus statusDokumenNot);


    Page<BiologyOnSize> findAllByWppOrUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                       String wpp,
                                                                       String uuidPengupload,
                                                                       DocumentStatus statusDokumenNot);


//    Page<BiologyOnSize> fetchingDataAsForExportExcel();

    /**
     * @return
     */
    Page<BiologyOnSize> fetchingDataAsForExportExcel(Pageable var1, String organisasi,
                                                     DocumentStatus status,
                                                     String wpp,
                                                     String sumberdaya,
                                                     String uuidPengupload,
                                                     String pencatat,
                                                     String namaLokasiSampling,
                                                     String namaKapal,
                                                     String alatTangkap,
                                                     String gridDaerahPenangkapan,
                                                     Date tanggalSamplingMulai,
                                                     Date tanggalSamplingHingga);

//    Page<BiologyOnSize> fetchingDataAsForExportExcel(Pageable var1, String organisasi,
//                                                     DocumentStatus status,
//                                                     String wpp,
//                                                     String sumberdaya,
//                                                     String pencatat,
//                                                     String namaLokasiSampling,
//                                                     String namaKapal,
//                                                     String alatTangkap,
//                                                     String gridDaerahPenangkapan);


    Page<BiologyOnSize> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1, String wpp,
                                                                     String uuidPengupload,
                                                                     DocumentStatus statusDokumenNot,
                                                                     String organisasi,
                                                                     DocumentStatus status,
                                                                     String sumberdaya,
                                                                     String pencatat,
                                                                     String namaLokasiSampling,
                                                                     String namaKapal,
                                                                     String alatTangkap,
                                                                     String gridDaerahPenangkapan,
                                                                     Date tanggalSamplingMulai,
                                                                     Date tanggalSamplingHingga);


    Page<BiologyOnSize> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1,
                                                                   DocumentStatus status,
                                                                   String wpp,
                                                                     String uuidPengupload,
                                                                     String organisasi,
                                                                     String sumberdaya,
                                                                     String pencatat,
                                                                     String namaLokasiSampling,
                                                                     String namaKapal,
                                                                     String alatTangkap,
                                                                     String gridDaerahPenangkapan,
                                                                     Date tanggalSamplingMulai,
                                                                     Date tanggalSamplingHingga);
//
//    Page<BiologyOnSize> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1, String wpp,
//                                                                     String uuidPengupload,
//                                                                     DocumentStatus statusDokumenNot,
//                                                                     String organisasi,
//                                                                     DocumentStatus status,
//                                                                     String sumberdaya,
//                                                                     String pencatat,
//                                                                     String namaLokasiSampling,
//                                                                     String namaKapal,
//                                                                     String alatTangkap,
//                                                                     String gridDaerahPenangkapan);


    Page<BiologyOnSize> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1, String uuidSumberDaya,
                                                                        String uuidPengupload,
                                                                        DocumentStatus statusDokumenNot,
                                                                        String organisasi,
                                                                        DocumentStatus status,
                                                                        String wpp,
                                                                        String pencatat,
                                                                        String namaLokasiSampling,
                                                                        String namaKapal,
                                                                        String alatTangkap,
                                                                        String gridDaerahPenangkapan,
                                                                        Date tanggalSamplingMulai,
                                                                        Date tanggalSamplingHingga);


//    Page<BiologyOnSize> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1, String uuidSumberDaya,
//                                                                        String uuidPengupload,
//                                                                        DocumentStatus statusDokumenNot,
//                                                                        String organisasi,
//                                                                        DocumentStatus status,
//                                                                        String wpp,
//                                                                        String pencatat,
//                                                                        String namaLokasiSampling,
//                                                                        String namaKapal,
//                                                                        String alatTangkap,
//                                                                        String gridDaerahPenangkapan);


    Page<BiologyOnSize> fetchingDataAsNormalUser(Pageable var1, String uuidPengupload,
                                                 String organisasi,
                                                 DocumentStatus status,
                                                 String namaLokasiSampling,
                                                 String namaKapal,
                                                 String alatTangkap,
                                                 String gridDaerahPenangkapan,
                                                 Date tanggalSamplingMulai,
                                                 Date tanggalSamplingHingga);

//    Page<BiologyOnSize> fetchingDataAsNormalUser(Pageable var1, String uuidPengupload,
//                                                 String organisasi,
//                                                 DocumentStatus status,
//                                                 String namaLokasiSampling,
//                                                 String namaKapal,
//                                                 String alatTangkap,
//                                                 String gridDaerahPenangkapan);


    long countAsSuperuser(String organisasi,
                          DocumentStatus status,
                          String wpp,
                          String sumberdaya,
                          String uuidPengupload,
                          String pencatat,
                          String namaLokasiSampling,
                          String namaKapal,
                          String alatTangkap,
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
                               String gridDaerahPenangkapan,
                               Date tanggalSamplingMulai,
                               Date tanggalSamplingHingga);

    long countAsValidatorNGO(DocumentStatus status,
                             String wpp,
                               String uuidPengupload,
                               String organisasi,
                               String sumberdaya,
                               String pencatat,
                               String namaLokasiSampling,
                               String namaKapal,
                               String alatTangkap,
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
//                                  String gridDaerahPenangkapan);


    long countDataAsNormalUser(String uuidPengupload,
                               String organisasi,
                               DocumentStatus status,
                               String namaLokasiSampling,
                               String namaKapal,
                               String alatTangkap,
                               String gridDaerahPenangkapan,
                               Date tanggalSamplingMulai,
                               Date tanggalSamplingHingga);

//    long countDataAsNormalUser(String uuidPengupload,
//                               String organisasi,
//                               DocumentStatus status,
//                               String namaLokasiSampling,
//                               String namaKapal,
//                               String alatTangkap,
//                               String gridDaerahPenangkapan);


}
