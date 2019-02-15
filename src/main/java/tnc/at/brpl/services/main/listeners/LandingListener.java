package tnc.at.brpl.services.main.listeners;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.service.ServiceListener;

import java.util.Date;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public interface LandingListener extends ServiceListener<Landing> {


    boolean isDuplicated(Landing landing);

    /**
     * @param var1
     * @param uuidPengupload
     * @return
     */
    Page<Landing> findAllByUuidPengupload(Pageable var1, String uuidPengupload);

    /**
     * @param var1
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    Page<Landing> findAllByUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                            String uuidPengupload,
                                                            DocumentStatus statusDokumenNot);


    /**
     * @param var1
     * @param uuidSumberDaya
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    Page<Landing> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNot(Pageable var1,
                                                                            String uuidSumberDaya,
                                                                            String uuidPengupload,
                                                                            DocumentStatus statusDokumenNot);

    Page<Landing> findAllByWppOrUuidPenguploadOrStatusDokumenNotPJWPP(Pageable var1,
                                                                      String wpp,
                                                                      String uuidPengupload,
                                                                      DocumentStatus statusDokumenNot);


    Page<Landing> findAllBySumberdayaOrUuidPenguploadOrStatusDokumenNotPeneliti(Pageable var1,
                                                                                String sumberdaya,
                                                                                String uuidPengupload,
                                                                                DocumentStatus statusDokumenNot);


    /**
     * @return
     */
    Page<Landing> fetchingDataAsForExportExcel(Pageable var1,
                                               String organisasi,
                                               DocumentStatus status,
                                               String wpp,
                                               String sumberdaya,
                                               String pencatat,
                                               String lokasiPendaratan,
                                               Date waktuPendaratanMulai,
                                               Date waktuPendaratanHingga);


    Page<Landing> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1,
                                                             DocumentStatus status,
                                                             String wpp,
                                                               String uuidPengupload,
                                                               String organisasi,
                                                               String sumberdaya,
                                                               String pencatat,
                                                               String lokasiPendaratan,
                                                               Date waktuPendaratanMulai,
                                                               Date waktuPendaratanHingga);


    Page<Landing> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1,
                                                               String wpp,
                                                               String uuidPengupload,
                                                               DocumentStatus statusDokumenNot,
                                                               String organisasi,
                                                               DocumentStatus status,
                                                               String sumberdaya,
                                                               String pencatat,
                                                               String lokasiPendaratan,
                                                               Date waktuPendaratanMulai,
                                                               Date waktuPendaratanHingga);


    Page<Landing> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1,
                                                                  String uuidSumberDaya,
                                                                  String uuidPengupload,
                                                                  DocumentStatus statusDokumenNot,
                                                                  String organisasi,
                                                                  DocumentStatus status,
                                                                  String wpp,
                                                                  String pencatat,
                                                                  String lokasiPendaratan,
                                                                  Date waktuPendaratanMulai,
                                                                  Date waktuPendaratanHingga);

//
//    Page<Landing> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1,
//                                                                  String uuidSumberDaya,
//                                                                  String uuidPengupload,
//                                                                  DocumentStatus statusDokumenNot,
//                                                                  String organisasi,
//                                               DocumentStatus status,
//                                                                  String wpp,
//                                                                  String pencatat,
//                                                                  String lokasiPendaratan);


    Page<Landing> fetchingDataAsNormalUser(Pageable var1,
                                           String uuidPengupload,
                                           String organisasi,
                                           DocumentStatus status,
                                           String lokasiPendaratan,
                                           Date waktuPendaratanMulai,
                                           Date waktuPendaratanHingga);

//    Page<Landing> fetchingDataAsNormalUser(Pageable var1,
//                                           String uuidPengupload,
//                                           String organisasi,
//                                               DocumentStatus status,
//                                           String lokasiPendaratan);


    long countAsSuperuser(String organisasi,
                          DocumentStatus status,
                          String wpp,
                          String sumberdaya,
                          String pencatat,
                          String lokasiPendaratan,
                          Date waktuPendaratanMulai,
                          Date waktuPendaratanHingga);

//    long countAsSuperuser(String organisasi,
//                                               DocumentStatus status,
//                          String wpp,
//                          String sumberdaya,
//                          String pencatat,
//                          String lokasiPendaratan);


    long countAsValidatorPJWPP(String wpp,
                               String uuidPengupload,
                               DocumentStatus statusDokumenNot,
                               String organisasi,
                               DocumentStatus status,
                               String sumberdaya,
                               String pencatat,
                               String lokasiPendaratan,
                               Date waktuPendaratanMulai,
                               Date waktuPendaratanHingga);

    long countAsValidatorNGO(DocumentStatus status,
                             String wpp,
                               String uuidPengupload,
                               String organisasi,
                               String sumberdaya,
                               String pencatat,
                               String lokasiPendaratan,
                               Date waktuPendaratanMulai,
                               Date waktuPendaratanHingga);


//    long countAsValidatorPJWPP(String wpp,
//                               String uuidPengupload,
//                               DocumentStatus statusDokumenNot,
//                               String organisasi,
//                                               DocumentStatus status,
//                               String sumberdaya,
//                               String pencatat,
//                               String lokasiPendaratan);


    long countAsValidatorPeneliti(String uuidSumberDaya,
                                  String uuidPengupload,
                                  DocumentStatus statusDokumenNot,
                                  String organisasi,
                                  DocumentStatus status,
                                  String wpp,
                                  String pencatat,
                                  String lokasiPendaratan,
                                  Date waktuPendaratanMulai,
                                  Date waktuPendaratanHingga);

//    long countAsValidatorPeneliti(String uuidSumberDaya,
//                                  String uuidPengupload,
//                                  DocumentStatus statusDokumenNot,
//                                  String organisasi,
//                                               DocumentStatus status,
//                                  String wpp,
//                                  String pencatat,
//                                  String lokasiPendaratan);


    long countDataAsNormalUser(String uuidPengupload,
                               String organisasi,
                               DocumentStatus status,
                               String lokasiPendaratan,
                               Date waktuPendaratanMulai,
                               Date waktuPendaratanHingga);

//    long countDataAsNormalUser(String uuidPengupload,
//                               String organisasi,
//                                               DocumentStatus status,
//                               String lokasiPendaratan);


}
