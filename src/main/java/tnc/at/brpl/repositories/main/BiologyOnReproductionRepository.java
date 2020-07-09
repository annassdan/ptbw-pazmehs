

package tnc.at.brpl.repositories.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.main.BiologyOnReproduction;
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
public interface BiologyOnReproductionRepository extends RepositoryListener<BiologyOnReproduction, String> {


    @Query("SELECT COUNT(data) FROM BiologyOnReproduction data  WHERE " +
            "(:uuidEnumerator is null OR data.uuidEnumerator = :uuidEnumerator) AND " +
            "(:uuidSumberDaya is null OR data.uuidSumberDaya = :uuidSumberDaya) AND " +
            "(:uuidSpesies is null OR data.uuidSumberDaya = :uuidSpesies) AND " +
            "(:namaLokasiSampling is null OR data.namaLokasiSampling = :namaLokasiSampling) AND " +
            "(cast(:tanggalSampling as date) is null OR data.tanggalSampling = :tanggalSampling) AND " +
            "(:namaKapal is null OR data.namaKapal = :namaKapal) AND " +
            "(:daerahPenangkapan is null OR data.daerahPenangkapan = :daerahPenangkapan) AND " +
            "(:uuidAlatTangkap is null OR data.uuidAlatTangkap = :uuidAlatTangkap) AND " +
            "data.penampung = :penampung AND " +
            "data.penangkap = :penangkap AND " +
            "(:organisasi is null OR UPPER(data.organisasi) = UPPER(:organisasi)) AND " +
            "(:wpp is null OR data.wpp = :wpp) "
    )
    Long countDuplicateData(
            @Param("uuidSumberDaya") String uuidSumberDaya,
            @Param("namaLokasiSampling") String namaLokasiSampling,
            @Param("namaKapal") String namaKapal,
            @Param("uuidSpesies") String uuidSpesies,
            @Param("daerahPenangkapan") String daerahPenangkapan,
            @Param("penampung") boolean penampung,
            @Param("penangkap") boolean penangkap,
            @Param("uuidAlatTangkap") String uuidAlatTangkap,
            @Param("tanggalSampling") Date tanggalSampling,
            @Param("uuidEnumerator") String uuidEnumerator,
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
    Page<BiologyOnReproduction> findAllByUuidPenguploadOrderByDibuatPadaTanggalAsc(Pageable var1, String uuidPengupload);

    /**
     * Digunakan oleh Superuser/ administrator untuk menampilkan data-data
     *
     * @param var1
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    Page<BiologyOnReproduction> findAllByUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
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
    Page<BiologyOnReproduction> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
                                                                                                                     String uuidSumberDaya,
                                                                                                                     String uuidPengupload,
                                                                                                                     DocumentStatus statusDokumenNot);


    Page<BiologyOnReproduction> findAllByWppOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
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
    @Query("SELECT data FROM BiologyOnReproduction data WHERE " +
            "organisasi = :organisasi OR data.uuidPengupload = :uuidPengupload " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC")
    Page<BiologyOnReproduction> fetchingDataAsValidatorNGO(Pageable var1,
                                                   @Param("organisasi") String organisasi,
                                                   @Param("uuidPengupload") String uuidPengupload);


    @Query("SELECT data FROM BiologyOnReproduction data WHERE " +
            "(LOWER(data.organisasi) = LOWER(:organisasi) OR (data.uuidPengupload = :uuidPengupload) )" +
            " AND " +
            "( " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
    Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1,
                                                                           @Param("status") DocumentStatus status,
                                                                           @Param("wpp") String wpp,
                                                                             @Param("uuidPengupload") String uuidPengupload,
                                                                             @Param("organisasi") String organisasi,
                                                                             @Param("sumberdaya") String sumberdaya,
                                                                             @Param("pencatat") String pencatat,
                                                                             @Param("namaLokasiSampling") String namaLokasiSampling,
                                                                             @Param("namaKapal") String namaKapal,
                                                                             @Param("alatTangkap") String alatTangkap,
                                                                             @Param("spesies") String spesies,
                                                                             @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                                             @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                                             @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


    @Query("SELECT COUNT(data) FROM BiologyOnReproduction data WHERE " +
            "(LOWER(data.organisasi) = LOWER(:organisasi) OR (data.uuidPengupload = :uuidPengupload) )" +
            " AND " +
            "( " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
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
                               @Param("spesies") String spesies,
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
    @Query("SELECT data FROM BiologyOnReproduction data WHERE " +
            "data.wpp = :wpp AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC")
    Page<BiologyOnReproduction> fetchingDataAsValidatorPJWPP(Pageable var1,
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
    @Query("SELECT data FROM BiologyOnReproduction data WHERE " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :uuidSumberDaya || '%') AND (data.uuidPengupload = :uuidPengupload OR data.statusDokumen != :statusDokumenNot) " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC")
    Page<BiologyOnReproduction> fetchingDataAsValidatorPeneliti(Pageable var1,
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
    @Query("SELECT data FROM BiologyOnReproduction data  WHERE  " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "(:uuidPengupload is null OR (data.uuidPengupload LIKE '%' || :uuidPengupload || '%') ) AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            "GROUP BY data.uuid ORDER BY data.tanggalSampling DESC ")
    Page<BiologyOnReproduction> fetchingDataAsForExportExcel(Pageable var1,
                                                             @Param("organisasi") String organisasi,
                                                             @Param("status") DocumentStatus status,
                                                             @Param("wpp") String wpp,
                                                             @Param("sumberdaya") String sumberdaya,
                                                             @Param("uuidPengupload") String uuidPengupload,
                                                             @Param("pencatat") String pencatat,
                                                             @Param("namaLokasiSampling") String namaLokasiSampling,
                                                             @Param("namaKapal") String namaKapal,
                                                             @Param("alatTangkap") String alatTangkap,
                                                             @Param("spesies") String spesies,
                                                             @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                             @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                             @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


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
    @Query("SELECT COUNT(data) FROM BiologyOnReproduction data  WHERE " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "(:uuidPengupload is null OR (data.uuidPengupload LIKE '%' || :uuidPengupload || '%') ) AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
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
                          @Param("spesies") String spesies,
                          @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                          @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                          @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);



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
    @Query("SELECT data FROM BiologyOnReproduction data WHERE " +
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
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
    Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1,
                                                                             @Param("wpp") String wpp,
                                                                             @Param("uuidPengupload") String uuidPengupload,
                                                                             @Param("organisasi") String organisasi,
                                                                             @Param("status") DocumentStatus status,
                                                                             @Param("sumberdaya") String sumberdaya,
                                                                             @Param("pencatat") String pencatat,
                                                                             @Param("namaLokasiSampling") String namaLokasiSampling,
                                                                             @Param("namaKapal") String namaKapal,
                                                                             @Param("alatTangkap") String alatTangkap,
                                                                             @Param("spesies") String spesies,
                                                                             @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                                             @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                                             @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);



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
    @Query("SELECT COUNT(data) FROM BiologyOnReproduction data WHERE " +
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
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
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
                               @Param("spesies") String spesies,
                               @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                               @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                               @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);


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
    @Query("SELECT data FROM BiologyOnReproduction data WHERE " +
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
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
    Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1,
                                                                                @Param("uuidSumberDaya") String uuidSumberDaya,
                                                                                @Param("uuidPengupload") String uuidPengupload,

                                                                                @Param("organisasi") String organisasi,
                                                                                @Param("status") DocumentStatus status,
                                                                                @Param("wpp") String wpp,
                                                                                @Param("pencatat") String pencatat,
                                                                                @Param("namaLokasiSampling") String namaLokasiSampling,
                                                                                @Param("namaKapal") String namaKapal,
                                                                                @Param("alatTangkap") String alatTangkap,
                                                                                @Param("spesies") String spesies,
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
    @Query("SELECT COUNT(data) FROM BiologyOnReproduction data WHERE " +
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
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
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
                                  @Param("spesies") String spesies,
                                  @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                  @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                  @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);




    /**
     * @param var1
     * @param uuidPengupload
     * @param organisasi
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT data FROM BiologyOnReproduction data WHERE " +
            "data.uuidPengupload = :uuidPengupload " +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
            "LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%') AND " +
            "LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC")
    Page<BiologyOnReproduction> fetchingDataAsNormalUser(Pageable var1,
                                                         @Param("uuidPengupload") String uuidPengupload,
                                                         @Param("organisasi") String organisasi,
                                                         @Param("status") DocumentStatus status,
                                                         @Param("namaLokasiSampling") String namaLokasiSampling,
                                                         @Param("namaKapal") String namaKapal,
                                                         @Param("alatTangkap") String alatTangkap,
                                                         @Param("spesies") String spesies,
                                                         @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                         @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                         @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);



    /**
     * @param uuidPengupload
     * @param organisasi
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM BiologyOnReproduction data WHERE " +
            "data.uuidPengupload = :uuidPengupload " +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.namaLokasiSampling) LIKE LOWER('%' || :namaLokasiSampling || '%') AND " +
            "LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%') AND " +
            "LOWER(data.uuidSpesies) LIKE LOWER('%' || :spesies || '%') AND " +
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
            @Param("spesies") String spesies,
            @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
            @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
            @Param("tanggalSamplingHingga") Date tanggalSamplingHingga);

    Page<BiologyOnReproduction> findAllByOrganisasiOrderByDibuatPadaTanggalAsc(Pageable paging, String organisasi);

    Page<BiologyOnReproduction> findAllByOrganisasiOrderByDibuatPadaTanggalDesc(Pageable paging, String organisasi);

    Page<BiologyOnReproduction> findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalAsc(Pageable pageable, String organisasi, boolean nonTrip);

    Page<BiologyOnReproduction> findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalDesc(Pageable pageable, String organisasi, boolean nonTrip);


}
