

package tnc.at.brpl.repositories.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.repository.RepositoryListener;

import java.util.Date;


/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Transactional
//@SuppressWarnings("unused")
public interface OperationalRepository extends RepositoryListener<Operational, String> {


    @Query("SELECT COUNT(data) FROM Operational data  WHERE " +
            "(:namaLokasiPendaratan is null OR data.namaLokasiPendaratan = :namaLokasiPendaratan) AND " +
            "(:uuidSumberDaya is null OR data.uuidSumberDaya = :uuidSumberDaya) AND " +
            "(:uuidEnumerator is null OR data.uuidEnumerator = :uuidEnumerator) AND " +
            "(cast(:tanggalSampling as date) is null OR data.tanggalSampling = :tanggalSampling) AND " +
            "(:namaKapal is null OR data.namaKapal = :namaKapal) AND " +
            "(cast(:tanggalBerangkat as date) is null OR data.tanggalBerangkat = :tanggalBerangkat) AND " +
            "(:tandaSelar is null OR data.tandaSelar = :tandaSelar) AND " +
            "(cast(:tanggalKembali as date) is null OR data.tanggalKembali = :tanggalKembali) AND " +
            "(:namaPemilikKapal is null OR data.namaPemilikKapal = :namaPemilikKapal) AND " +
            "(:pelabuhanAsal is null OR data.pelabuhanAsal = :pelabuhanAsal) AND " +
            "(:namaKapten is null OR data.namaKapten = :namaKapten) AND " +
            "(:jumlahAbk is null OR data.jumlahAbk = :jumlahAbk) AND " +
            "(:panjangKapal is null OR data.panjangKapal = :panjangKapal) AND " +
            "(:materialKapal is null OR data.materialKapal = :materialKapal) AND " +
            "(:dayaCahaya is null OR data.dayaCahaya = :dayaCahaya) AND " +
            "(:bobotKapal is null OR data.bobotKapal = :bobotKapal) AND " +
            "(:kapalBantu is null OR data.kapalBantu = :kapalBantu) AND " +
            "(:ukuranKapalBantu is null OR data.ukuranKapalBantu = :ukuranKapalBantu) AND " +
            "(:kapalAndon is null OR data.kapalAndon = :kapalAndon) AND " +
            "(:asalKapalAndon is null OR data.asalKapalAndon = :asalKapalAndon) AND " +
            "(:jumlahPalka is null OR data.jumlahPalka = :jumlahPalka) AND " +
            "(:jumlahBoks is null OR data.jumlahBoks = :jumlahBoks) AND " +
            "(:mesinUtama is null OR data.mesinUtama = :mesinUtama) AND " +
            "(:freezer is null OR data.freezer = :freezer) AND " +
            "(:kapasitasFreezer is null OR data.kapasitasFreezer = :kapasitasFreezer) AND " +
            "(:kapasitasPalkaBoks is null OR data.kapasitasPalkaBoks = :kapasitasPalkaBoks) AND " +
            "(:mesinBantu is null OR data.mesinBantu = :mesinBantu) AND " +
            "(:gps is null OR data.gps = :gps) AND " +
            "(:jenisGps is null OR data.jenisGps = :jenisGps) AND " +
            "(:uuidAlatTangkap is null OR data.uuidAlatTangkap = :uuidAlatTangkap) AND " +
            "(:material is null OR data.material = :material) AND " +
            "(:jumlahAlatTangkapYangDioperasikan is null OR data.jumlahAlatTangkapYangDioperasikan = :jumlahAlatTangkapYangDioperasikan) AND " +
            "(:jumlahSetting is null OR data.jumlahSetting = :jumlahSetting) AND " +
            "(:kedalamanAirMulai is null OR data.kedalamanAirMulai = :kedalamanAirMulai) AND " +
            "(:kedalamanAirHingga is null OR data.kedalamanAirHingga = :kedalamanAirHingga) AND " +
            "(:daerahPenangkapan is null OR data.daerahPenangkapan = :daerahPenangkapan) AND " +
            "(:jumlahHariPerTrip is null OR data.jumlahHariPerTrip = :jumlahHariPerTrip) AND " +
            "(:jumlahHariMenangkap is null OR data.jumlahHariMenangkap = :jumlahHariMenangkap) AND " +
            "(:jenisRumpon is null OR data.jenisRumpon = :jenisRumpon) AND " +
            "(:jumlahBalokEs is null OR data.jumlahBalokEs = :jumlahBalokEs) AND " +
            "(:jumlahRumponDikunjungi is null OR data.jumlahRumponDikunjungi = :jumlahRumponDikunjungi) AND " +
            "(:jumlahRumponBerhasil is null OR data.jumlahRumponBerhasil = :jumlahRumponBerhasil) AND " +
            "(:waktuMemancing is null OR data.waktuMemancing = :waktuMemancing) AND " +
            "(:sumberInformasi is null OR data.sumberInformasi = :sumberInformasi) AND " +
            "(:jumlahTangkapanUntukDimakanDilautVolume is null OR data.jumlahTangkapanUntukDimakanDilautVolume = :jumlahTangkapanUntukDimakanDilautVolume) AND " +
            "(:jumlahTangkapanUntukDimakanDilautIndividu is null OR data.jumlahTangkapanUntukDimakanDilautIndividu = :jumlahTangkapanUntukDimakanDilautIndividu) AND " +
            "(:jumlahTangkapanVolume is null OR data.jumlahTangkapanVolume = :jumlahTangkapanVolume) AND " +
            "(:jumlahTangkapanIndividu is null OR data.jumlahTangkapanIndividu = :jumlahTangkapanIndividu) AND " +
            "(:organisasi is null OR UPPER(data.organisasi) = UPPER(:organisasi)) AND " +
            "(:lamaPerendaman is null OR data.lamaPerendaman = :lamaPerendaman) AND " +
            "(:wpp is null OR data.wpp = :wpp) "
    )

    Long countDuplicateData(
            @Param("namaLokasiPendaratan") String namaLokasiPendaratan,
            @Param("uuidSumberDaya") String uuidSumberDaya,
            @Param("uuidEnumerator") String uuidEnumerator,
            @Param("tanggalSampling") Date tanggalSampling,
            @Param("namaKapal") String namaKapal,
            @Param("tanggalBerangkat") Date tanggalBerangkat,
            @Param("tandaSelar") String tandaSelar,
            @Param("tanggalKembali") Date tanggalKembali,
            @Param("namaPemilikKapal") String namaPemilikKapal,
            @Param("pelabuhanAsal") String pelabuhanAsal,
            @Param("namaKapten") String namaKapten,
            @Param("jumlahAbk") int jumlahAbk,
            @Param("panjangKapal") double panjangKapal,
            @Param("materialKapal") String materialKapal,
            @Param("dayaCahaya") int dayaCahaya,
            @Param("bobotKapal") double bobotKapal,
            @Param("kapalBantu") boolean kapalBantu,
            @Param("ukuranKapalBantu") int ukuranKapalBantu,
            @Param("kapalAndon") boolean kapalAndon,
            @Param("asalKapalAndon") String asalKapalAndon,
            @Param("jumlahPalka") int jumlahPalka,
            @Param("jumlahBoks") int jumlahBoks,
            @Param("mesinUtama") int mesinUtama,
            @Param("freezer") boolean freezer,
            @Param("kapasitasFreezer") double kapasitasFreezer,
            @Param("kapasitasPalkaBoks") int kapasitasPalkaBoks,
            @Param("mesinBantu") int mesinBantu,
            @Param("gps") boolean gps,
            @Param("jenisGps") String jenisGps,
            @Param("uuidAlatTangkap") String uuidAlatTangkap,
            @Param("material") String material,
            @Param("jumlahAlatTangkapYangDioperasikan") int jumlahAlatTangkapYangDioperasikan,
            @Param("jumlahSetting") int jumlahSetting,
            @Param("kedalamanAirMulai") double kedalamanAirMulai,
            @Param("kedalamanAirHingga") double kedalamanAirHingga,
            @Param("daerahPenangkapan") String daerahPenangkapan,
            @Param("jumlahHariPerTrip") int jumlahHariPerTrip,
            @Param("jumlahHariMenangkap") int jumlahHariMenangkap,
            @Param("jenisRumpon") String jenisRumpon,
            @Param("jumlahBalokEs") double jumlahBalokEs,
            @Param("jumlahRumponDikunjungi") int jumlahRumponDikunjungi,
            @Param("jumlahRumponBerhasil") int jumlahRumponBerhasil,
            @Param("waktuMemancing") String waktuMemancing,
            @Param("sumberInformasi") String sumberInformasi,
            @Param("jumlahTangkapanUntukDimakanDilautVolume") double jumlahTangkapanUntukDimakanDilautVolume,
            @Param("jumlahTangkapanUntukDimakanDilautIndividu") int jumlahTangkapanUntukDimakanDilautIndividu,
            @Param("jumlahTangkapanVolume") double jumlahTangkapanVolume,
            @Param("jumlahTangkapanIndividu") int jumlahTangkapanIndividu,
            @Param("organisasi") String organisasi,
            @Param("lamaPerendaman") double lamaPerendaman,
            @Param("wpp") String wpp
    );

    /**
     * Digunakan oleh user biasa untuk mengambil data yang dia punya
     *
     * @param var1
     * @param uuidPengupload
     * @return
     */
    Page<Operational> findAllByUuidPenguploadOrderByDibuatPadaTanggalAsc(Pageable var1, String uuidPengupload);

    /**
     * Digunakan oleh Superuser/ administrator untuk menampilkan data-data
     *
     * @param var1
     * @param uuidPengupload
     * @param statusDokumenNot
     * @return
     */
    Page<Operational> findAllByUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
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
    Page<Operational> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
                                                                                                           String uuidSumberDaya,
                                                                                                           String uuidPengupload,
                                                                                                           DocumentStatus statusDokumenNot);


    Page<Operational> findAllByWppOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(Pageable var1,
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
    @Query("SELECT data FROM Operational data WHERE " +
            "organisasi = :organisasi OR data.uuidPengupload = :uuidPengupload " +
            "GROUP BY data.uuid " +
            "ORDER BY data.uuid ASC")
    Page<Operational> fetchingDataAsValidatorNGO(Pageable var1,
                                             @Param("organisasi") String organisasi,
                                             @Param("uuidPengupload") String uuidPengupload);

    @Query("SELECT COUNT(data) FROM Operational data WHERE " +
            "(LOWER(data.organisasi) = LOWER(:organisasi) OR (data.uuidPengupload = :uuidPengupload) )" +
            " AND " +
            "( " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) " +
            ") ")
    long countAsValidatorNGO(@Param("status") DocumentStatus status,
                             @Param("wpp") String wpp,
                               @Param("uuidPengupload") String uuidPengupload,
                               @Param("organisasi") String organisasi,
                               @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                               @Param("namaKapal") String namaKapal,
                               @Param("namaPemilikKapal") String namaPemilikKapal,
                               @Param("namaKapten") String namaKapten,
                               @Param("pelabuhanAsal") String pelabuhanAsal,
                               @Param("alatTangkap") String alatTangkap,
                               @Param("sumberdaya") String sumberdaya,
                               @Param("pencatat") String pencatat,
                               @Param("lokasiPendaratan") String lokasiPendaratan,
                               @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                               @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
                               @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
                               @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
                               @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
                               @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);


    @Query("SELECT data FROM Operational data WHERE " +
            "(LOWER(data.organisasi) = LOWER(:organisasi) OR (data.uuidPengupload = :uuidPengupload) )" +
            " AND " +
            "( " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
    Page<Operational> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1,
                                                                 @Param("status") DocumentStatus status,
                                                                 @Param("wpp") String wpp,
                                                                   @Param("uuidPengupload") String uuidPengupload,
                                                                   @Param("organisasi") String organisasi,
                                                                   @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                                   @Param("namaKapal") String namaKapal,
                                                                   @Param("namaPemilikKapal") String namaPemilikKapal,
                                                                   @Param("namaKapten") String namaKapten,
                                                                   @Param("pelabuhanAsal") String pelabuhanAsal,
                                                                   @Param("alatTangkap") String alatTangkap,
                                                                   @Param("sumberdaya") String sumberdaya,
                                                                   @Param("pencatat") String pencatat,
                                                                   @Param("lokasiPendaratan") String lokasiPendaratan,
                                                                   @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                                   @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
                                                                   @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
                                                                   @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
                                                                   @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
                                                                   @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);




    /**
     * Fungsi yang digunakan untuk mengambil data yang akan diexport ke excel sebagai superuser atau administrator
     *
     * @param var1
     * @param organisasi
     * @param wpp
     * @param sumberdaya
     * @param pencatat
     * @param lokasiPendaratan
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT data FROM Operational data  WHERE  " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "(:uuidPengupload is null OR (data.uuidPengupload LIKE '%' || :uuidPengupload || '%') ) AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) " +
            "GROUP BY data.uuid ORDER BY data.tanggalSampling DESC ")
    Page<Operational> fetchingDataAsForExportExcel(Pageable var1,
                                                   @Param("organisasi") String organisasi,
                                                   @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                   @Param("namaKapal") String namaKapal,
                                                   @Param("namaPemilikKapal") String namaPemilikKapal,
                                                   @Param("namaKapten") String namaKapten,
                                                   @Param("pelabuhanAsal") String pelabuhanAsal,
                                                   @Param("alatTangkap") String alatTangkap,
                                                   @Param("status") DocumentStatus status,
                                                   @Param("wpp") String wpp,
                                                   @Param("sumberdaya") String sumberdaya,
                                                   @Param("uuidPengupload") String uuidPengupload,
                                                   @Param("pencatat") String pencatat,
                                                   @Param("lokasiPendaratan") String lokasiPendaratan,
                                                   @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                   @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
                                                   @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
                                                   @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
                                                   @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
                                                   @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);


    /**
     * Fungsi yang digunakan untuk menghitung jumlah data yang akan diexport ke excel sebagai superuser ataupun administrator
     *
     * @param organisasi
     * @param wpp
     * @param sumberdaya
     * @param pencatat
     * @param lokasiPendaratan
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM Operational data  WHERE " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "(:uuidPengupload is null OR (data.uuidPengupload LIKE '%' || :uuidPengupload || '%') ) AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) ")
    long countAsSuperuser(@Param("organisasi") String organisasi,
                          @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                          @Param("namaKapal") String namaKapal,
                          @Param("namaPemilikKapal") String namaPemilikKapal,
                          @Param("namaKapten") String namaKapten,
                          @Param("pelabuhanAsal") String pelabuhanAsal,
                          @Param("alatTangkap") String alatTangkap,
                          @Param("status") DocumentStatus status,
                          @Param("wpp") String wpp,
                          @Param("sumberdaya") String sumberdaya,
                          @Param("uuidPengupload") String uuidPengupload,
                          @Param("pencatat") String pencatat,
                          @Param("lokasiPendaratan") String lokasiPendaratan,
                          @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                          @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
                          @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
                          @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
                          @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
                          @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);


    /**
     * @param var1
     * @param wpp
     * @param uuidPengupload
     * @param organisasi
     * @param sumberdaya
     * @param pencatat
     * @param lokasiPendaratan
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT data FROM Operational data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(data.wpp = :wpp AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            "( " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +
            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
    Page<Operational> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1,
                                                                   @Param("wpp") String wpp,
                                                                   @Param("uuidPengupload") String uuidPengupload,
                                                                   @Param("organisasi") String organisasi,
                                                                   @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                                   @Param("namaKapal") String namaKapal,
                                                                   @Param("namaPemilikKapal") String namaPemilikKapal,
                                                                   @Param("namaKapten") String namaKapten,
                                                                   @Param("pelabuhanAsal") String pelabuhanAsal,
                                                                   @Param("alatTangkap") String alatTangkap,
                                                                   @Param("status") DocumentStatus status,
                                                                   @Param("sumberdaya") String sumberdaya,
                                                                   @Param("pencatat") String pencatat,
                                                                   @Param("lokasiPendaratan") String lokasiPendaratan,
                                                                   @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                                   @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
                                                                   @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
                                                                   @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
                                                                   @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
                                                                   @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);


    /**
     * @param wpp
     * @param uuidPengupload
     * @param organisasi
     * @param sumberdaya
     * @param pencatat
     * @param lokasiPendaratan
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM Operational data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(data.wpp = :wpp AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            "( " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +

            "LOWER(data.uuidSumberDaya) LIKE LOWER('%' || :sumberdaya || '%') AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) " +
            ") ")
    long countAsValidatorPJWPP(@Param("wpp") String wpp,
                               @Param("uuidPengupload") String uuidPengupload,
                               @Param("organisasi") String organisasi,
                               @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                               @Param("namaKapal") String namaKapal,
                               @Param("namaPemilikKapal") String namaPemilikKapal,
                               @Param("namaKapten") String namaKapten,
                               @Param("pelabuhanAsal") String pelabuhanAsal,
                               @Param("alatTangkap") String alatTangkap,
                               @Param("status") DocumentStatus status,
                               @Param("sumberdaya") String sumberdaya,
                               @Param("pencatat") String pencatat,
                               @Param("lokasiPendaratan") String lokasiPendaratan,
                               @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                               @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
                               @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
                               @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
                               @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
                               @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);


    /**
     * @param var1
     * @param uuidSumberDaya
     * @param uuidPengupload
     * @param organisasi
     * @param wpp
     * @param pencatat
     * @param lokasiPendaratan
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT data FROM Operational data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(LOWER(data.uuidSumberDaya) = LOWER(:uuidSumberDaya) AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC ")
    Page<Operational> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1,
                                                                      @Param("uuidSumberDaya") String uuidSumberDaya,
                                                                      @Param("uuidPengupload") String uuidPengupload,

                                                                      @Param("organisasi") String organisasi,
                                                                      @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                                      @Param("namaKapal") String namaKapal,
                                                                      @Param("namaPemilikKapal") String namaPemilikKapal,
                                                                      @Param("namaKapten") String namaKapten,
                                                                      @Param("pelabuhanAsal") String pelabuhanAsal,
                                                                      @Param("alatTangkap") String alatTangkap,
                                                                      @Param("status") DocumentStatus status,
                                                                      @Param("wpp") String wpp,
                                                                      @Param("pencatat") String pencatat,
                                                                      @Param("lokasiPendaratan") String lokasiPendaratan,
                                                                      @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                                                      @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
                                                                      @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
                                                                      @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
                                                                      @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
                                                                      @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);


    /**
     * @param uuidSumberDaya
     * @param uuidPengupload
     * @param organisasi
     * @param wpp
     * @param pencatat
     * @param lokasiPendaratan
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM Operational data WHERE " +
            "(" +
            "data.uuidPengupload = :uuidPengupload OR " +
            "(LOWER(data.uuidSumberDaya) = LOWER(:uuidSumberDaya) AND (data.statusDokumen != 3 AND  data.statusDokumen != 4 AND data.statusDokumen != 5) ) " +
            ")" +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "data.wpp LIKE %:wpp% AND " +
            "LOWER(data.uuidEnumerator) LIKE LOWER('%' || :pencatat || '%') AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%') AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) " +
            ") ")
    long countAsValidatorPeneliti(@Param("uuidSumberDaya") String uuidSumberDaya,
                                  @Param("uuidPengupload") String uuidPengupload,

                                  @Param("organisasi") String organisasi,
                                  @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                  @Param("namaKapal") String namaKapal,
                                  @Param("namaPemilikKapal") String namaPemilikKapal,
                                  @Param("namaKapten") String namaKapten,
                                  @Param("pelabuhanAsal") String pelabuhanAsal,
                                  @Param("alatTangkap") String alatTangkap,
                                  @Param("status") DocumentStatus status,
                                  @Param("wpp") String wpp,
                                  @Param("pencatat") String pencatat,
                                  @Param("lokasiPendaratan") String lokasiPendaratan,
                                  @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                  @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
                                  @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
                                  @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
                                  @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
                                  @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);


    /**
     * @param var1
     * @param uuidPengupload
     * @param organisasi
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT data FROM Operational data WHERE " +
            "data.uuidPengupload = :uuidPengupload" +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%')  AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) " +
            ") " +
            "GROUP BY data.uuid ORDER BY data.uuid ASC")
    Page<Operational> fetchingDataAsNormalUser(Pageable var1,
                                               @Param("uuidPengupload") String uuidPengupload,
                                               @Param("organisasi") String organisasi,
                                               @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                               @Param("namaKapal") String namaKapal,
                                               @Param("namaPemilikKapal") String namaPemilikKapal,
                                               @Param("namaKapten") String namaKapten,
                                               @Param("pelabuhanAsal") String pelabuhanAsal,
                                               @Param("alatTangkap") String alatTangkap,
                                               @Param("status") DocumentStatus status,
                                               @Param("lokasiPendaratan") String lokasiPendaratan,
                                               @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
                                               @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
                                               @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
                                               @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
                                               @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
                                               @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);


    /**
     * @param uuidPengupload
     * @param organisasi
     * @param tanggalSamplingMulai
     * @param tanggalSamplingHingga
     * @return
     */
    @Query("SELECT COUNT(data) FROM Operational data WHERE " +
            "data.uuidPengupload = :uuidPengupload" +
            " AND " +
            "( " +
            "LOWER(data.organisasi) LIKE LOWER('%' || :organisasi || '%') AND " +
            "(:gridDaerahPenangkapan is null OR LOWER(data.daerahPenangkapan) LIKE LOWER('%' || :gridDaerahPenangkapan || '%')) AND " +
            "(:namaKapal is null OR LOWER(data.namaKapal) LIKE LOWER('%' || :namaKapal || '%')) AND " +
            "(:namaPemilikKapal is null OR LOWER(data.namaPemilikKapal) LIKE LOWER('%' || :namaPemilikKapal || '%')) AND " +
            "(:namaKapten is null OR LOWER(data.namaKapten) LIKE LOWER('%' || :namaKapten || '%')) AND " +
            "(:pelabuhanAsal is null OR LOWER(data.pelabuhanAsal) LIKE LOWER('%' || :pelabuhanAsal || '%')) AND " +
            "(:alatTangkap is null OR LOWER(data.uuidAlatTangkap) LIKE LOWER('%' || :alatTangkap || '%')) AND " +
            "(:status is null OR data.statusDokumen = :status) AND " +
            "LOWER(data.namaLokasiPendaratan) LIKE LOWER('%' || :lokasiPendaratan || '%')  AND " +
            "((cast(:tanggalSamplingMulai as date) is null OR cast(:tanggalSamplingHingga as date) is null) OR (data.tanggalSampling BETWEEN :tanggalSamplingMulai AND  :tanggalSamplingHingga )) AND " +
            "((cast(:tanggalBerangkatMulai as date) is null OR cast(:tanggalBerangkatHingga as date) is null) OR (data.tanggalBerangkat BETWEEN :tanggalBerangkatMulai AND  :tanggalBerangkatHingga )) AND " +
            "((cast(:tanggalKembaliMulai as date) is null OR cast(:tanggalKembaliHingga as date) is null) OR (data.tanggalKembali BETWEEN :tanggalKembaliMulai AND  :tanggalKembaliHingga )) " +
            ") ")
    long countDataAsNormalUser(
            @Param("uuidPengupload") String uuidPengupload,
            @Param("organisasi") String organisasi,
            @Param("gridDaerahPenangkapan") String gridDaerahPenangkapan,
            @Param("namaKapal") String namaKapal,
            @Param("namaPemilikKapal") String namaPemilikKapal,
            @Param("namaKapten") String namaKapten,
            @Param("pelabuhanAsal") String pelabuhanAsal,
            @Param("alatTangkap") String alatTangkap,
            @Param("status") DocumentStatus status,
            @Param("lokasiPendaratan") String lokasiPendaratan,
            @Param("tanggalSamplingMulai") Date tanggalSamplingMulai,
            @Param("tanggalSamplingHingga") Date tanggalSamplingHingga,
            @Param("tanggalBerangkatMulai") Date tanggalBerangkatMulai,
            @Param("tanggalBerangkatHingga") Date tanggalBerangkatHingga,
            @Param("tanggalKembaliMulai") Date tanggalKembaliMulai,
            @Param("tanggalKembaliHingga") Date tanggalKembaliHingga);



    Page<Operational> findAllByOrganisasiOrderByDibuatPadaTanggalAsc(Pageable pageable, String organisasi);

    Page<Operational> findAllByOrganisasiOrderByDibuatPadaTanggalDesc(Pageable pageable, String organisasi);

    Page<Operational> findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalAsc(Pageable pageable, String organisasi, boolean nonTrip);

    Page<Operational> findAllByOrganisasiAndNonTripOrderByDibuatPadaTanggalDesc(Pageable pageable, String organisasi, boolean nonTrip);

}
