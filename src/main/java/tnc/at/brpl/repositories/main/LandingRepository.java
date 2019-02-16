
package tnc.at.brpl.repositories.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.main.*;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.repository.RepositoryListener;

import java.util.Date;
import java.util.List;


/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Transactional
@SuppressWarnings("unused")
public interface LandingRepository extends RepositoryListener<Landing, String> {

    Page<Landing> findAllByOrganisasiOrderByDibuatPadaTanggalAsc(Pageable pageable, String organisasi);


//    @Query("SELECT data FROM Landing data WHERE " +
//            "data.uuid = :uuid AND " +
//            "data.dataRincianPendaratan.namaKapal = :namaKapal ")
//    Page<Landing> coba(Pageable pageable,
//                       @Param("uuid") String uuid,
//                       @Param("namaKapal") String namaKapal);


//    @Query("SELECT data FROM Landing data WHERE " +
//            "EXISTS ( " +
//            " SELECT data2 FROM LandingDetail data2 WHERE " +
//            " data2.namaKapal = :namaKapal " +
//            " )")

//    @Query("SELECT data FROM Landing data JOIN data.dataRincianPendaratan data2 WHERE " +
//            " data2.namaKapal = :namaKapal ")
//    Page<Landing> coba2(Pageable pageable,
//                        @Param("namaKapal") String namaKapal);

    @Query("SELECT data FROM Landing data JOIN data.dataRincianPendaratan data2 WHERE " +
            " data.uuid = :uuid AND  " +
            " data2.namaKapal = :namaKapal ")
    Page<Landing> coba2(Pageable pageable,
                        @Param("uuid") String uuid,
                        @Param("namaKapal") String namaKapal);

// @Param("organisasi") String organisasi,

    @Query("SELECT COUNT(data) FROM Landing data  WHERE " +
            "(:organisasi is null OR UPPER(data.organisasi) = UPPER(:organisasi)) AND " +
            "(:wpp is null OR data.wpp = :wpp) AND " +
            "(:uuidSumberDaya is null OR data.uuidSumberDaya = :uuidSumberDaya) AND " +
            "(:uuidEnumerator is null OR data.uuidEnumerator = :uuidEnumerator) AND " +
            "(:namaLokasiPendaratan is null OR data.namaLokasiPendaratan = :namaLokasiPendaratan) AND " +
            "(cast(:tanggalPendaratan as date) is null OR data.tanggalPendaratan = :tanggalPendaratan) ")
    long countDuplicateData(
            @Param("tanggalPendaratan") Date tanggalPendaratan,
            @Param("namaLokasiPendaratan") String namaLokasiPendaratan,
            @Param("uuidSumberDaya") String uuidSumberDaya,
            @Param("uuidEnumerator") String uuidEnumerator,
//            @Param("organisasi") List<LandingDetail> dataRincianPendaratan,
//            @Param("organisasi") String photoName,
//            @Param("organisasi") List<Operational> dataOperasional,
//            @Param("organisasi") List<BiologyOnSize> dataUkuran,
//            @Param("organisasi") List<BiologyOnReproduction> dataReproduksi,
            @Param("organisasi") String organisasi,
            @Param("wpp") String wpp
    );

    /**
     * Digunakan oleh user biasa untuk mengambil data yang dia punya enum/other
     *
     * @param var1
     * @param uuidPengupload
     * @return
     */
    Page<Landing> findAllByUuidPenguploadOrderByDibuatPadaTanggalAsc(Pageable var1, String uuidPengupload);

    /**
     * Digunakan oleh Superuser/ administrator untuk menampilkan data-data
     *
     * @param var1
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    Page<Landing> findAllByUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
                                                                                       String uuidPengupload,
                                                                                       DocumentStatus statusDokumenNot);


    /**
     * Digunakan oleh validator
     *
     * @param var1
     * @param uuidSumberDaya
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    Page<Landing> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
                                                                                                       String uuidSumberDaya,
                                                                                                       String uuidPengupload,
                                                                                                       DocumentStatus statusDokumenNot);


    Page<Landing> findAllByWppOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
                                                                                            String wpp,
                                                                                            String uuidPengupload,
                                                                                            DocumentStatus statusDokumenNot);


    /**
     * Digunakan untuk menampilkan data utama dengan status login sebagai validator NGO
     *
     * @param var1
     * @param organisasi
     * @param uuidPengupload
     * @return
     */
    @Query("SELECT data FROM Landing data WHERE " +
            "organisasi = :organisasi OR data.uuidPengupload = :uuidPengupload " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC")
    Page<Landing> fetchingDataAsValidatorNGO(Pageable var1,
                                             @Param("organisasi") String organisasi,
                                             @Param("uuidPengupload") String uuidPengupload);


    @Query("SELECT COUNT(data) FROM Landing data WHERE " +
            "(LOWER(data.organisasi) = LOWER(:organisasi) OR (data.uuidPengupload = :uuidPengupload) )" +
            " AND " +
            " (" +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) " +
            ") ")
    long countAsValidatorNGO(
            @Param("status") DocumentStatus status,
            @Param("wpp") String wpp, @Param("uuidPengupload") String uuidPengupload,
            @Param("organisasi") String organisasi,
            @Param("sumberdaya") String sumberdaya,
            @Param("pencatat") String pencatat,
            @Param("lokasiPendaratan") String lokasiPendaratan,
            @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
            @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);


    @Query("SELECT data FROM Landing data WHERE " +
            "(LOWER(data.organisasi) = LOWER(:organisasi) OR (data.uuidPengupload = :uuidPengupload) )" +
            " AND " +
            " (" +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) " +
            ") " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC ")
    Page<Landing> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1,
                                                             @Param("status") DocumentStatus status,
                                                             @Param("wpp") String wpp,
                                                             @Param("uuidPengupload") String uuidPengupload,
                                                             @Param("organisasi") String organisasi,
                                                             @Param("sumberdaya") String sumberdaya,
                                                             @Param("pencatat") String pencatat,
                                                             @Param("lokasiPendaratan") String lokasiPendaratan,
                                                             @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
                                                             @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);


    /**
     * Digunakan untuk menampilkan data utama dengan status login sebagai validator PJ WPP
     *
     * @param var1
     * @param wpp
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    @Query("SELECT data FROM Landing data WHERE " +
            "data.wpp = :wpp AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC")
    Page<Landing> fetchingDataAsValidatorPJWPP(Pageable var1,
                                               @Param("wpp") String wpp,
                                               @Param("uuidPengupload") String uuidPengupload,
                                               @Param("statusDokumenNot") DocumentStatus statusDokumenNot);


    /**
     * Digunakan untuk menampilkan data utama dengan status login sebagai validator Peneliti
     *
     * @param var1
     * @param uuidSumberDaya
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    @Query("SELECT data FROM Landing data WHERE " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :uuidSumberDaya || '%') AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC")
    Page<Landing> fetchingDataAsValidatorPeneliti(Pageable var1,
                                                  @Param("uuidSumberDaya") String uuidSumberDaya,
                                                  @Param("uuidPengupload") String uuidPengupload,
                                                  @Param("statusDokumenNot") DocumentStatus statusDokumenNot);


    /**
     * Fungsi yang digunakan untuk mengambil data yang akan diexport ke excel sebagai superuser atau administrator
     * "(:uuidPengupload is null OR (data.uuidPengupload LIKE '%' || :uuidPengupload || '%') ) AND " +
     *
     * @param var1
     * @param organisasi
     * @param wpp
     * @param sumberdaya
     * @param pencatat
     * @param lokasiPendaratan
     * @param waktuPendaratanMulai
     * @param waktuPendaratanHingga
     * @return
     */
    @Query("SELECT data FROM Landing data WHERE  " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "(:sumberdaya is null OR LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%')) AND " +


            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) " +
            "GROUP BY data.uuid " +
            "ORDER BY data.tanggalPendaratan DESC  ")
    Page<Landing> fetchingDataAsForExportExcel(Pageable var1,
                                               @Param("organisasi") String organisasi,
                                               @Param("status") DocumentStatus status,
                                               @Param("wpp") String wpp,
                                               @Param("sumberdaya") String sumberdaya,
                                               @Param("pencatat") String pencatat,
                                               @Param("lokasiPendaratan") String lokasiPendaratan,
                                               @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
                                               @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);


    /**
     * Fungsi yang digunakan untuk menghitung jumlah data yang akan diexport ke excel sebagai superuser ataupun administrator
     *
     * @param organisasi
     * @param wpp
     * @param sumberdaya
     * @param pencatat
     * @param lokasiPendaratan
     * @param waktuPendaratanMulai
     * @param waktuPendaratanHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM Landing data  WHERE " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) ")
    long countAsSuperuser(@Param("organisasi") String organisasi,
                          @Param("status") DocumentStatus status,
                          @Param("wpp") String wpp,
                          @Param("sumberdaya") String sumberdaya,
                          @Param("pencatat") String pencatat,
                          @Param("lokasiPendaratan") String lokasiPendaratan,
                          @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
                          @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);


    /**
     * @param var1
     * @param wpp
     * @param uuidPengupload
     * @param organisasi
     * @param sumberdaya
     * @param pencatat
     * @param lokasiPendaratan
     * @param waktuPendaratanMulai
     * @param waktuPendaratanHingga
     * @return
     */
    @Query("SELECT data FROM Landing data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(data.wpp = :wpp AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            " (" +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) " +
            ") " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC ")
    Page<Landing> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1,
                                                               @Param("wpp") String wpp,
                                                               @Param("uuidPengupload") String uuidPengupload,
                                                               @Param("organisasi") String organisasi,
                                                               @Param("status") DocumentStatus status,
                                                               @Param("sumberdaya") String sumberdaya,
                                                               @Param("pencatat") String pencatat,
                                                               @Param("lokasiPendaratan") String lokasiPendaratan,
                                                               @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
                                                               @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);

    @Query("SELECT COUNT(data) FROM Landing data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(data.wpp = :wpp AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            " (" +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) " +
            ") ")
    long countAsValidatorPJWPP(@Param("wpp") String wpp,
                               @Param("uuidPengupload") String uuidPengupload,
                               @Param("organisasi") String organisasi,
                               @Param("status") DocumentStatus status,
                               @Param("sumberdaya") String sumberdaya,
                               @Param("pencatat") String pencatat,
                               @Param("lokasiPendaratan") String lokasiPendaratan,
                               @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
                               @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);


    /**
     * @param var1
     * @param uuidSumberDaya
     * @param uuidPengupload
     * @param organisasi
     * @param wpp
     * @param pencatat
     * @param lokasiPendaratan
     * @param waktuPendaratanMulai
     * @param waktuPendaratanHingga
     * @return
     */
    @Query("SELECT data FROM Landing data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(LOWER(data.uuidSumberDaya) = LOWER(:uuidSumberDaya) AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) " +
            ") " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC ")
    Page<Landing> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1,
                                                                  @Param("uuidSumberDaya") String uuidSumberDaya,
                                                                  @Param("uuidPengupload") String uuidPengupload,

                                                                  @Param("organisasi") String organisasi,
                                                                  @Param("status") DocumentStatus status,
                                                                  @Param("wpp") String wpp,
                                                                  @Param("pencatat") String pencatat,
                                                                  @Param("lokasiPendaratan") String lokasiPendaratan,
                                                                  @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
                                                                  @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);


    /**
     * @param uuidSumberDaya
     * @param uuidPengupload
     * @param organisasi
     * @param wpp
     * @param pencatat
     * @param lokasiPendaratan
     * @param waktuPendaratanMulai
     * @param waktuPendaratanHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM Landing data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(LOWER(data.uuidSumberDaya) = LOWER(:uuidSumberDaya) AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) " +
            ")")
    long countAsValidatorPeneliti(@Param("uuidSumberDaya") String uuidSumberDaya,
                                  @Param("uuidPengupload") String uuidPengupload,

                                  @Param("organisasi") String organisasi,
                                  @Param("status") DocumentStatus status,
                                  @Param("wpp") String wpp,
                                  @Param("pencatat") String pencatat,
                                  @Param("lokasiPendaratan") String lokasiPendaratan,
                                  @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
                                  @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);


    /**
     * @param var1
     * @param uuidPengupload
     * @param organisasi
     * @param waktuPendaratanMulai
     * @param waktuPendaratanHingga
     * @return
     */
    @Query("SELECT data FROM Landing data WHERE " +
            "data.uuidPengupload = :uuidPengupload " +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) " +
            ") " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC")
    Page<Landing> fetchingDataAsNormalUser(Pageable var1,
                                           @Param("uuidPengupload") String uuidPengupload,
                                           @Param("organisasi") String organisasi,
                                           @Param("status") DocumentStatus status,
                                           @Param("lokasiPendaratan") String lokasiPendaratan,
                                           @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
                                           @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);

    /**
     * @param uuidPengupload
     * @param organisasi
     * @param waktuPendaratanMulai
     * @param waktuPendaratanHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM Landing data WHERE " +
            "data.uuidPengupload = :uuidPengupload " +
            " AND" +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:waktuPendaratanMulai as date) is null OR cast(:waktuPendaratanHingga as date) is null) OR (data.tanggalPendaratan BETWEEN :waktuPendaratanMulai AND  :waktuPendaratanHingga )) " +
            ") ")
    long countDataAsNormalUser(
            @Param("uuidPengupload") String uuidPengupload,
            @Param("organisasi") String organisasi,
            @Param("status") DocumentStatus status,
            @Param("lokasiPendaratan") String lokasiPendaratan,
            @Param("waktuPendaratanMulai") Date waktuPendaratanMulai,
            @Param("waktuPendaratanHingga") Date waktuPendaratanHingga);


}
