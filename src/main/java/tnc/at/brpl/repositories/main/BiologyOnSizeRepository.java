


package tnc.at.brpl.repositories.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.repository.RepositoryListener;

import java.util.Date;


/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Transactional
@SuppressWarnings("unused")
public interface BiologyOnSizeRepository extends RepositoryListener<BiologyOnSize, String> {




    @Query("SELECT COUNT(data) FROM BiologyOnSize data  WHERE " +
            "(:uuidEnumerator is null OR data.uuidEnumerator = :uuidEnumerator) AND " +
            "(:uuidSumberDaya is null OR data.uuidSumberDaya = :uuidSumberDaya) AND " +
            "(:namaLokasiSampling is null OR data.namaLokasiSampling = :namaLokasiSampling) AND " +
            "(cast(:tanggalSampling as date) is null OR data.tanggalSampling = :tanggalSampling) AND " +
            "(:namaKapal is null OR data.namaKapal = :namaKapal) AND " +
            "(:daerahPenangkapan is null OR data.daerahPenangkapan = :daerahPenangkapan) AND " +
            "(:uuidAlatTangkap is null OR data.uuidAlatTangkap = :uuidAlatTangkap) AND " +
            "data.penampung = :penampung AND " +
            "data.penangkap = :penangkap AND " +
            "(:totalTangkapanVolume is null OR data.totalTangkapanVolume = :totalTangkapanVolume) AND " +
            "(:totalTangkapanIndividu is null OR data.totalTangkapanIndividu = :totalTangkapanIndividu) AND " +
            "(:totalSampelIndividu is null OR data.totalSampelIndividu = :totalSampelIndividu) AND " +
            "(:totalSampelVolume is null OR data.totalSampelVolume = :totalSampelVolume) AND " +
            "(:organisasi is null OR UPPER(data.organisasi) = UPPER(:organisasi)) AND " +
            "(:wpp is null OR data.wpp = :wpp) "
    )
    long countDuplicateData(
            @Param("uuidEnumerator") String uuidEnumerator,
            @Param("uuidSumberDaya") String uuidSumberDaya,
            @Param("namaLokasiSampling") String namaLokasiSampling,
            @Param("tanggalSampling") Date tanggalSampling,
            @Param("namaKapal") String namaKapal,
            @Param("daerahPenangkapan") String daerahPenangkapan,
            @Param("uuidAlatTangkap") String uuidAlatTangkap,
            @Param("penampung") boolean penampung,
            @Param("penangkap") boolean penangkap,
            @Param("totalTangkapanVolume") double totalTangkapanVolume,
            @Param("totalTangkapanIndividu") int totalTangkapanIndividu,
            @Param("totalSampelIndividu") int totalSampelIndividu,
            @Param("totalSampelVolume") double totalSampelVolume,
            @Param("organisasi") String organisasi,
            @Param("wpp") String wpp
    );

    /**
     * Digunakan oleh user biasa untuk mengambil data yang dia punya
     *
     * @param var1
     * @param uuidPengupload
     * @return
     */
    Page<BiologyOnSize> findAllByUuidPenguploadOrderByDibuatPadaTanggalAsc(Pageable var1, String uuidPengupload);

    /**
     * Digunakan oleh validator dengan membaypass sumber daya
     *
     * @param var1
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    Page<BiologyOnSize> findAllByUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
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
    Page<BiologyOnSize> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
                                                                                                             String uuidSumberDaya,
                                                                                                             String uuidPengupload,
                                                                                                             DocumentStatus statusDokumenNot);


    Page<BiologyOnSize> findAllByWppOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
                                                                                                  String wpp,
                                                                                                  String uuidPengupload,
                                                                                                  DocumentStatus statusDokumenNot);


//    /**
//     * Digunakan untuk menampilkan data utama dengan status login sebagai validator
//     *
//     * @param var1
//     * @param wpp
//     * @param uuidPengupload
//     * @param statusDokumenNot
//     * @return
//     */
//    @Query("SELECT data FROM BiologyOnSize data WHERE " +
//            "data.wpp = :wpp AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) " +
//            "GROUP BY data.uuid ORDER BY data.uuid ASC")
//    Page<BiologyOnSize> fetchingDataAsValidator(Pageable var1,
//                                          @Param("wpp") String wpp,
//                                          @Param("uuidPengupload") String uuidPengupload,
//                                          @Param("statusDokumenNot") DocumentStatus statusDokumenNot);
//
//
//    /**
//     *
//     * @param var1
//     * @return
//     */
//    @Query("SELECT data FROM BiologyOnSize data  " +
//            "GROUP BY data.uuid ORDER BY data.uuid ASC")
//    Page<BiologyOnSize> fetchingDataAsForExportExcel(Pageable var1);



    /**
     * Digunakan untuk menampilkan data utama dengan status login sebagai validator NGO
     *
     * @param var1
     * @param organisasi
     * @param uuidPengupload
     * @return
     */
    @Query("SELECT data FROM BiologyOnSize data WHERE " +
            "organisasi = :organisasi OR data.uuidPengupload = :uuidPengupload " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC")
    Page<BiologyOnSize> fetchingDataAsValidatorNGO(Pageable var1,
                                                 @Param("organisasi") String organisasi,
                                                 @Param("uuidPengupload") String uuidPengupload);


    @Query("SELECT data FROM BiologyOnSize data WHERE " +
            "(LOWER(data.organisasi) = LOWER(:organisasi) OR (data.uuidPengupload = :uuidPengupload) )" +
            " AND " +
            "( " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
    Page<BiologyOnSize> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1,
                                                                   @Param("status") DocumentStatus status,
                                                                   @Param("wpp") String wpp,
                                                                     @Param("uuidPengupload") String uuidPengupload,
                                                                     @Param("organisasi") String organisasi,
                                                                     @Param("sumberdaya") String sumberdaya,
                                                                     @Param("pencatat") String pencatat,
                                                                     @Param("namaLokasiSampling") String namaLokasiSampling,
                                                                     @Param("namaKapal") String namaKapal,
                                                                     @Param("alatTangkap") String alatTangkap,

                                                                     @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                                     @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                                     @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


    @Query("SELECT COUNT(data) FROM BiologyOnSize data WHERE " +
            "(LOWER(data.organisasi) = LOWER(:organisasi) OR (data.uuidPengupload = :uuidPengupload) )" +
            " AND " +
            "( " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ")")
    long countAsValidatorNGO(@Param("status") DocumentStatus status,
                             @Param("wpp") String wpp,
                               @Param("uuidPengupload") String uuidPengupload,
                               @Param("organisasi") String organisasi,
                               @Param("sumberdaya") String sumberdaya,
                               @Param("pencatat") String pencatat,
                               @Param("namaLokasiSampling") String namaLokasiSampling,
                               @Param("namaKapal") String namaKapal,
                               @Param("alatTangkap") String alatTangkap,

                               @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                               @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                               @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);




    /**
     * Digunakan untuk menampilkan data utama dengan status login sebagai validator PJ WPP
     *
     * @param var1
     * @param wpp
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    @Query("SELECT data FROM BiologyOnSize data WHERE " +
            "data.wpp = :wpp AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC")
    Page<BiologyOnSize> fetchingDataAsValidatorPJWPP(Pageable var1,
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
    @Query("SELECT data FROM BiologyOnSize data WHERE " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :uuidSumberDaya || '%') AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC")
    Page<BiologyOnSize> fetchingDataAsValidatorPeneliti(Pageable var1,
                                                        @Param("uuidSumberDaya") String uuidSumberDaya,
                                                        @Param("uuidPengupload") String uuidPengupload,
                                                        @Param("statusDokumenNot") DocumentStatus statusDokumenNot);


    /**
     * Fungsi yang digunakan untuk mengambil data yang akan diexport ke excel sebagai superuser atau administrator
     *
     * @param var1
     * @param organisasi
     * @param wpp
     * @param sumberdaya
     * @param pencatat
     * @param namaLokasiSampling
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT data FROM BiologyOnSize data  WHERE  " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "(:uuidPengupload is null OR (data.uuidPengupload LIKE '%' || :uuidPengupload || '%') ) AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            "GROUP BY data.uuid ORDER BY data.tanggalSampling DESC  ")
    Page<BiologyOnSize> fetchingDataAsForExportExcel(Pageable var1,
                                                     @Param("organisasi") String organisasi,
                                                     @Param("status") DocumentStatus status,
                                                     @Param("wpp") String wpp,
                                                     @Param("sumberdaya") String sumberdaya,
                                                     @Param("uuidPengupload") String uuidPengupload,
                                                     @Param("pencatat") String pencatat,
                                                     @Param("namaLokasiSampling") String namaLokasiSampling,
                                                     @Param("namaKapal") String namaKapal,
                                                     @Param("alatTangkap") String alatTangkap,
                                                     @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                     @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                     @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


//    /**
//     * Fungsi yang digunakan untuk mengambil data yang akan diexport ke excel sebagai superuser atau administrator
//     *
//     * @param var1
//     * @param organisasi
//     * @param wpp
//     * @param sumberdaya
//     * @param pencatat
//     * @param namaLokasiSampling
//     * @return
//     */
//    @Query("SELECT data FROM BiologyOnSize data  WHERE  " +
//            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
//            "(:status is null OR data.statusDokumen = :status) AND " +
//            "data.wpp LIKE %:wpp% AND " +
//            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
//            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
//            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
//            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
//            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
//
//            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')  " +
//            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
//    Page<BiologyOnSize> fetchingDataAsForExportExcel(Pageable var1,
//                                                     @Param("organisasi") String organisasi,
//                                               @Param("status") DocumentStatus status,
//                                                     @Param("wpp") String wpp,
//                                                     @Param("sumberdaya") String sumberdaya,
//                                                     @Param("pencatat") String pencatat,
//                                                     @Param("namaLokasiSampling") String namaLokasiSampling,
//                                                     @Param("namaKapal") String namaKapal,
//                                                     @Param("alatTangkap") String alatTangkap,
//
//                                                     @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan);


    /**
     * Fungsi yang digunakan untuk menghitung jumlah data yang akan diexport ke excel sebagai superuser ataupun administrator
     *
     * @param organisasi
     * @param wpp
     * @param sumberdaya
     * @param pencatat
     * @param namaLokasiSampling
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM BiologyOnSize data  WHERE " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "(:uuidPengupload is null OR (data.uuidPengupload LIKE '%' || :uuidPengupload || '%') ) AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) ")
    long countAsSuperuser(@Param("organisasi") String organisasi,
                          @Param("status") DocumentStatus status,
                          @Param("wpp") String wpp,
                          @Param("sumberdaya") String sumberdaya,
                          @Param("uuidPengupload") String uuidPengupload,
                          @Param("pencatat") String pencatat,
                          @Param("namaLokasiSampling") String namaLokasiSampling,
                          @Param("namaKapal") String namaKapal,
                          @Param("alatTangkap") String alatTangkap,

                          @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                          @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                          @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


//    /**
//     * Fungsi yang digunakan untuk menghitung jumlah data yang akan diexport ke excel sebagai superuser ataupun administrator
//     *
//     * @param organisasi
//     * @param wpp
//     * @param sumberdaya
//     * @param pencatat
//     * @param namaLokasiSampling
//     * @return
//     */
//    @Query("SELECT COUNT(data) FROM BiologyOnSize data  WHERE " +
//            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
//            "(:status is null OR data.statusDokumen = :status) AND " +
//            "data.wpp LIKE %:wpp% AND " +
//            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
//            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
//            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
//            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
//            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
//
//            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')  ")
//    long countAsSuperuser(@Param("organisasi") String organisasi,
//                                               @Param("status") DocumentStatus status,
//                          @Param("wpp") String wpp,
//                          @Param("sumberdaya") String sumberdaya,
//                          @Param("pencatat") String pencatat,
//                          @Param("namaLokasiSampling") String namaLokasiSampling,
//                          @Param("namaKapal") String namaKapal,
//                          @Param("alatTangkap") String alatTangkap,
//
//                          @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan);


    /**
     * @param var1
     * @param wpp
     * @param uuidPengupload
     * @param organisasi
     * @param sumberdaya
     * @param pencatat
     * @param namaLokasiSampling
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT data FROM BiologyOnSize data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(data.wpp = :wpp AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
    Page<BiologyOnSize> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1,
                                                                     @Param("wpp") String wpp,
                                                                     @Param("uuidPengupload") String uuidPengupload,
                                                                     @Param("organisasi") String organisasi,
                                                                     @Param("status") DocumentStatus status,
                                                                     @Param("sumberdaya") String sumberdaya,
                                                                     @Param("pencatat") String pencatat,
                                                                     @Param("namaLokasiSampling") String namaLokasiSampling,
                                                                     @Param("namaKapal") String namaKapal,
                                                                     @Param("alatTangkap") String alatTangkap,

                                                                     @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                                     @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                                     @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);

//
//    /**
//     * @param var1
//     * @param wpp
//     * @param uuidPengupload
//     * @param statusDokumenNot
//     * @param organisasi
//     * @param sumberdaya
//     * @param pencatat
//     * @param namaLokasiSampling
//     * @return
//     */
//    @Query("SELECT data FROM BiologyOnSize data WHERE " +
//            "data.wpp = :wpp AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) AND " +
//            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
//            "(:status is null OR data.statusDokumen = :status) AND " +
//            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
//            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
//            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
//            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
//            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
//
//            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') " +
//            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
//    Page<BiologyOnSize> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1,
//                                                                     @Param("wpp") String wpp,
//                                                                     @Param("uuidPengupload") String uuidPengupload,
//                                                                     @Param("statusDokumenNot") DocumentStatus statusDokumenNot,
//                                                                     @Param("organisasi") String organisasi,
//                                               @Param("status") DocumentStatus status,
//                                                                     @Param("sumberdaya") String sumberdaya,
//                                                                     @Param("pencatat") String pencatat,
//                                                                     @Param("namaLokasiSampling") String namaLokasiSampling,
//                                                                     @Param("namaKapal") String namaKapal,
//                                                                     @Param("alatTangkap") String alatTangkap,
//
//                                                                     @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan);


    /**
     * @param wpp
     * @param uuidPengupload
     * @param organisasi
     * @param sumberdaya
     * @param pencatat
     * @param namaLokasiSampling
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM BiologyOnSize data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(data.wpp = :wpp AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ")")
    long countAsValidatorPJWPP(@Param("wpp") String wpp,
                               @Param("uuidPengupload") String uuidPengupload,
                               @Param("organisasi") String organisasi,
                               @Param("status") DocumentStatus status,
                               @Param("sumberdaya") String sumberdaya,
                               @Param("pencatat") String pencatat,
                               @Param("namaLokasiSampling") String namaLokasiSampling,
                               @Param("namaKapal") String namaKapal,
                               @Param("alatTangkap") String alatTangkap,

                               @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                               @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                               @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


//    /**
//     * @param wpp
//     * @param uuidPengupload
//     * @param statusDokumenNot
//     * @param organisasi
//     * @param sumberdaya
//     * @param pencatat
//     * @param namaLokasiSampling
//     * @return
//     */
//    @Query("SELECT COUNT(data) FROM BiologyOnSize data WHERE " +
//            "data.wpp = :wpp AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) AND " +
//            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
//            "(:status is null OR data.statusDokumen = :status) AND " +
//            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
//            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
//            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
//            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
//            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
//
//            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') ")
//    long countAsValidatorPJWPP(@Param("wpp") String wpp,
//                               @Param("uuidPengupload") String uuidPengupload,
//                               @Param("statusDokumenNot") DocumentStatus statusDokumenNot,
//                               @Param("organisasi") String organisasi,
//                                               @Param("status") DocumentStatus status,
//                               @Param("sumberdaya") String sumberdaya,
//                               @Param("pencatat") String pencatat,
//                               @Param("namaLokasiSampling") String namaLokasiSampling,
//                               @Param("namaKapal") String namaKapal,
//                               @Param("alatTangkap") String alatTangkap,
//
//                               @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan);


    /**
     * @param var1
     * @param uuidSumberDaya
     * @param uuidPengupload
     * @param organisasi
     * @param wpp
     * @param pencatat
     * @param namaLokasiSampling
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT data FROM BiologyOnSize data WHERE " +
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
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
    Page<BiologyOnSize> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1,
                                                                        @Param("uuidSumberDaya") String uuidSumberDaya,
                                                                        @Param("uuidPengupload") String uuidPengupload,

                                                                        @Param("organisasi") String organisasi,
                                                                        @Param("status") DocumentStatus status,
                                                                        @Param("wpp") String wpp,
                                                                        @Param("pencatat") String pencatat,
                                                                        @Param("namaLokasiSampling") String namaLokasiSampling,
                                                                        @Param("namaKapal") String namaKapal,
                                                                        @Param("alatTangkap") String alatTangkap,

                                                                        @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                                        @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                                        @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);




    /**
     * @param uuidSumberDaya
     * @param uuidPengupload
     * @param organisasi
     * @param wpp
     * @param pencatat
     * @param namaLokasiSampling
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM BiologyOnSize data WHERE " +
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
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ")")
    long countAsValidatorPeneliti(@Param("uuidSumberDaya") String uuidSumberDaya,
                                  @Param("uuidPengupload") String uuidPengupload,
                                  @Param("organisasi") String organisasi,
                                  @Param("status") DocumentStatus status,
                                  @Param("wpp") String wpp,
                                  @Param("pencatat") String pencatat,
                                  @Param("namaLokasiSampling") String namaLokasiSampling,
                                  @Param("namaKapal") String namaKapal,
                                  @Param("alatTangkap") String alatTangkap,

                                  @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                  @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                  @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


//    /**
//     * @param uuidSumberDaya
//     * @param uuidPengupload
//     * @param statusDokumenNot
//     * @param organisasi
//     * @param wpp
//     * @param pencatat
//     * @param namaLokasiSampling
//     * @return
//     */
//    @Query("SELECT COUNT(data) FROM BiologyOnSize data WHERE " +
//            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :uuidSumberDaya || '%')  AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) AND " +
//            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
//            "(:status is null OR data.statusDokumen = :status) AND " +
//            "data.wpp LIKE %:wpp% AND " +
//            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
//            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
//            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
//            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
//
//            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') ")
//    long countAsValidatorPeneliti(@Param("uuidSumberDaya") String uuidSumberDaya,
//                                  @Param("uuidPengupload") String uuidPengupload,
//                                  @Param("statusDokumenNot") DocumentStatus statusDokumenNot,
//
//                                  @Param("organisasi") String organisasi,
//                                               @Param("status") DocumentStatus status,
//                                  @Param("wpp") String wpp,
//                                  @Param("pencatat") String pencatat,
//                                  @Param("namaLokasiSampling") String namaLokasiSampling,
//                                  @Param("namaKapal") String namaKapal,
//                                  @Param("alatTangkap") String alatTangkap,
//
//                                  @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan);


    /**
     * @param var1
     * @param uuidPengupload
     * @param organisasi
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT data FROM BiologyOnSize data WHERE " +
            "data.uuidPengupload = :uuidPengupload " +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC")
    Page<BiologyOnSize> fetchingDataAsNormalUser(Pageable var1,
                                                 @Param("uuidPengupload") String uuidPengupload,
                                                 @Param("organisasi") String organisasi,
                                                 @Param("status") DocumentStatus status,
                                                 @Param("namaLokasiSampling") String namaLokasiSampling,
                                                 @Param("namaKapal") String namaKapal,
                                                 @Param("alatTangkap") String alatTangkap,

                                                 @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                 @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                 @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


//    /**
//     * @param var1
//     * @param uuidPengupload
//     * @param organisasi
//     * @return
//     */
//    @Query("SELECT data FROM BiologyOnSize data WHERE " +
//            "data.uuidPengupload = :uuidPengupload AND " +
//            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
//            "(:status is null OR data.statusDokumen = :status) AND " +
//            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
//            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
//            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
//
//            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') " +
//            "GROUP BY data.uuid ORDER BY data.uuid ASC")
//    Page<BiologyOnSize> fetchingDataAsNormalUser(Pageable var1,
//                                                 @Param("uuidPengupload") String uuidPengupload,
//                                                 @Param("organisasi") String organisasi,
//                                               @Param("status") DocumentStatus status,
//                                                 @Param("namaLokasiSampling") String namaLokasiSampling,
//                                                 @Param("namaKapal") String namaKapal,
//                                                 @Param("alatTangkap") String alatTangkap,
//
//                                                 @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan);


    /**
     * @param uuidPengupload
     * @param organisasi
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM BiologyOnSize data WHERE " +
            "data.uuidPengupload = :uuidPengupload " +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +

            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ")")
    long countDataAsNormalUser(
            @Param("uuidPengupload") String uuidPengupload,
            @Param("organisasi") String organisasi,
            @Param("status") DocumentStatus status,
            @Param("namaLokasiSampling") String namaLokasiSampling,
            @Param("namaKapal") String namaKapal,
            @Param("alatTangkap") String alatTangkap,

            @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
            @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
            @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


//    /**
//     * @param uuidPengupload
//     * @param organisasi
//     * @return
//     */
//    @Query("SELECT COUNT(data) FROM BiologyOnSize data WHERE " +
//            "data.uuidPengupload = :uuidPengupload AND " +
//            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
//            "(:status is null OR data.statusDokumen = :status) AND " +
//            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
//            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
//            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
//
//            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') ")
//    long countDataAsNormalUser(@Param("uuidPengupload") String uuidPengupload,
//                               @Param("organisasi") String organisasi,
//                                               @Param("status") DocumentStatus status,
//                               @Param("namaLokasiSampling") String namaLokasiSampling,
//                               @Param("namaKapal") String namaKapal,
//                               @Param("alatTangkap") String alatTangkap,
//
//                               @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan);

}
