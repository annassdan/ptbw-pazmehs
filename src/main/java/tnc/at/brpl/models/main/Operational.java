
package tnc.at.brpl.models.main;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.configurations.CustomTimeSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.UnpredictableBoolean;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.OPERATIONAL)
public class Operational extends EntityModel<Operational, String> {

    /** Header */
//    @ApiModelProperty("Tanggal Pendaratan")
//    @Temporal(TemporalType.DATE)
//    @JsonSerialize(using = CustomDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
//    @Column(name = "tanggal_pendaratan" + XMARK)
//    private Date tanggalPendaratan;

    @ApiModelProperty("Nama tempat Pendaratan")
    @Column(name = "nama_lokasi_pendaratan" + XMARK)
    private String namaLokasiPendaratan;

    @ApiModelProperty("Kode Sumber Daya")
    @Column(name = "uuid_sumber_daya" + XMARK)
    private String uuidSumberDaya;

    @ApiModelProperty("Kode Enumerator")
    @Column(name = "uuid_enumerator" + XMARK)
    private String uuidEnumerator;

    /** ============= */

    @ApiModelProperty("Tanggal Sampling")
    @Temporal(TemporalType.TIME)
    @JsonSerialize(using = CustomTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    @Column(name = "jam_sampling" + XMARK)
    private Date jamSampling;

    @ApiModelProperty("Tanggal Sampling")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "tanggal_sampling" + XMARK)
    private Date tanggalSampling;

    @ApiModelProperty("Nama Kapal")
    @Column(name = "nama_kapal" + XMARK)
    private String namaKapal;

    @ApiModelProperty("Tanggal Berangkat")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "tanggal_berangkat" + XMARK)
    private Date tanggalBerangkat;

    @ApiModelProperty("Nomor Lambung Kapal/ Tanda Selar")
    @Column(name = "tanda_selar" + XMARK)
    private String tandaSelar;

    @ApiModelProperty("Tanggal Kembali")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "tanggal_kembali" + XMARK)
    private Date tanggalKembali;

    @ApiModelProperty("Nama Pemilik Kapal")
    @Column(name = "nama_pemilik_kapal" + XMARK)
    private String namaPemilikKapal;

    @ApiModelProperty("Pelabuhan Asal")
    @Column(name = "pelabuhan_asal" + XMARK)
    private String pelabuhanAsal;


    @ApiModelProperty("Nama Kapten")
    @Column(name = "nama_kapten" + XMARK)
    private String namaKapten;

    @ApiModelProperty("Jumlah ABK")
    @Column(name = "jumah_abk" + XMARK)
    private int jumlahAbk;

    @ApiModelProperty("Panjang Kapal")
    @Column(name = "panjang_kapal" + XMARK)
    private double panjangKapal;

    @ApiModelProperty("Bahan Pembuatan Kapal")
    @Column(name = "material_kapal" + XMARK)
    private String materialKapal;

    @ApiModelProperty("Daya Lampu/Cahaya")
    @Column(name = "daya_cahaya" + XMARK)
    private int dayaCahaya;

    @ApiModelProperty("Berat Kapal")
    @Column(name = "bobot_kapal" + XMARK)
    private double bobotKapal;

    @ApiModelProperty("Kapal Bantu")
    @Column(name = "kapal_bantu" + XMARK)
//    @ColumnDefault("'na'")
//    private UnpredictableBoolean kapalBantu;
    private boolean kapalBantu;

    @ApiModelProperty("Kapal Bantu")
    @Column(name = "ukuran_kapal_bantu" + XMARK)
    private int ukuranKapalBantu;


    @ApiModelProperty("Kapal Andon")
    @Column(name = "kapal_andon" + XMARK)
    private boolean kapalAndon;

    @ApiModelProperty("Kapal Asal")
    @Column(name = "asal_kapal_andon" + XMARK)
    private String asalKapalAndon;

    @ApiModelProperty("Jumlah Palka")
    @Column(name = "jumlah_palka" + XMARK)
    private int jumlahPalka;

    @ApiModelProperty("Jumlah Boks")
    @Column(name = "jumlah_boks" + XMARK)
    private int jumlahBoks;

    @ApiModelProperty("Mesin Utama")
    @Column(name = "mesin_utama" + XMARK)
    private int mesinUtama;

    @ApiModelProperty("Pendingin (Freezer)")
    @Column(name = "freezer" + XMARK)
//    @ColumnDefault("'na'")
//    private UnpredictableBoolean freezer;
    private boolean freezer;

    @ApiModelProperty("Kapasitas Freezer ")
    @Column(name = "kapasitas_freezer" + XMARK)
    private double kapasitasFreezer;

    @ApiModelProperty("Kapasitas Palka atau Boks")
    @Column(name = "kapasitas_palka_boks" + XMARK)
    private int kapasitasPalkaBoks;

    @ApiModelProperty("Mesin Bantu")
    @Column(name = "mesin_bantu" + XMARK)
    private int mesinBantu;

    @ApiModelProperty("Apakah menggunakan GPS?")
    @Column(name = "gps" + XMARK)
//    @ColumnDefault("'na'")
//    private UnpredictableBoolean gps;
    private boolean gps;

    @ApiModelProperty("Jenis GPS")
    @Column(name = "jenis_gps" + XMARK)
    private String jenisGps;

    /// ======================================= alat tangkap ==

    @ApiModelProperty("Kode Alat Tangkap")
    @Column(name = "uuid_alat_tangkap" + XMARK)
    private String uuidAlatTangkap;

    @ApiModelProperty("Bahan Pembuat Alat Tangkap")
    @Column(name = "material" + XMARK)
    private String material;

    @ApiModelProperty("Jumlah Alat Tangkap yang dioperasikan")
    @Column(name = "jumlah_alat_tangkap_yang_dioperasikan" + XMARK)
    private int jumlahAlatTangkapYangDioperasikan;

    /*@ApiModelProperty("Panjang Ris atas")
    @Column(name = "panjang_ris_atas" + XMARK)
    private int panjangRisAtas; // old = netDimensions*/

    /*@ApiModelProperty("Ukuran Standar/ ukuran jaring insang ")
    @Column(name = "ukuran_jaring_insang" + XMARK)
    private int ukuranJaringInsang;*/

    // @ApiModelProperty("Ukuran Normal jaring")
    //@Column(name = "net_normal_size" + XMARK)
    //private int netNormalSize;

    /*@ApiModelProperty("Ukuran Kantong Pukat")
    @Column(name = "ukuran_kantong_pukat" + XMARK)
    private int ukuranKantongPukat;*/

    /*@ApiModelProperty("Ukuran Badan Pukat")
    @Column(name = "ukuran_badan_pukat" + XMARK)
    private int ukuranBadanPukat;*/

    /*@ApiModelProperty("Ukuran Sayap Pukat")
    @Column(name = "ukuran_sayap_pukat" + XMARK)
    private int ukuranSayapPukat;*/

    /*@ApiModelProperty("Ukuran Lapis Dalam Jaring Tramel")
    @Column(name = "ukuran_lapis_dalam_jaring_tramel" + XMARK)
    private int ukuranLapisDalamJaringTramel;*/

    /*@ApiModelProperty("Ukuran Lapis Luar Jaring Tramel")
    @Column(name = "ukuran_lapis_luar_jaring_tramel" + XMARK)
    private int ukuranLapisLuarJaringTramel;*/
    // pancing

    /*@ApiModelProperty("Nomor mata pancing")
    @Column(name = "nomor_mata_pancing" + XMARK)
    private int nomorMataPancing;*/

    /*@ApiModelProperty("Ukuran Tinggi mata Pancing")
    @Column(name = "ukuran_tinggi_mata_pancing" + XMARK)
    private double ukuranTinggiMataPancing;*/

    /*@ApiModelProperty("Ukuran Lebar Mata Pancing")
    @Column(name = "ukuran_lebar_mata_pancing" + XMARK)
    private double ukuranLebarMataPancing;*/
    //

    @ApiModelProperty("Jumlah kali Setting")
    @Column(name = "jumlah_setting" + XMARK)
    private int jumlahSetting;

    // ========================================================


    @ApiModelProperty("Kedalaman Perairan Mulai")
    @Column(name = "kedalaman_air_mulai" + XMARK)
    private double kedalamanAirMulai;

    @ApiModelProperty("Kedalaman Perairan Hingga")
    @Column(name = "kedalaman_air_hingga" + XMARK)
    private double kedalamanAirHingga;

    @ApiModelProperty("Daerah penangkapan/memancing")
    @Column(name = "daerah_penangkapan" + XMARK)
    private String daerahPenangkapan;

    @ApiModelProperty("Jumlah Hari Per Trip")
    @Column(name = "jumlah_hari_per_trip" + XMARK)
    private int jumlahHariPerTrip;

    @ApiModelProperty("Jumlah Hari Menangkap")
    @Column(name = "jumlah_hari_menangkap" + XMARK)
    private int jumlahHariMenangkap;

    @ApiModelProperty("Jenis Rumpon")
    @Column(name = "jenis_rumpon" + XMARK)
    private String jenisRumpon;

    @ApiModelProperty("Total Balok Es")
    @Column(name = "jumlah_balok_es" + XMARK)
    private double jumlahBalokEs;

    @ApiModelProperty("Jumlah Rumpon yang dikunjungi")
    @Column(name = "jumlah_rumpon_dikunjungi" + XMARK)
    private int jumlahRumponDikunjungi;

    @ApiModelProperty("Jumlah rumpon berhasil")
    @Column(name = "jumlah_rumpon_berhasil" + XMARK)
    private int jumlahRumponBerhasil;

//    @ApiModelProperty("Jumlah settign selema trip")
//    @Column(name = "jumlah_setting_per_trip" + XMARK)
//    private int jumlahSettingPerTrip;

//    @Deprecated // Sepertinya akan dihilangkan diganti dengan jumlah hari efektif
//    @ApiModelProperty("Jumlah Hari Operasi")
//    @Column(name = "jumlah_hari_efektif" + XMARK)
//    private int jumlahHariEfektif;

    @ApiModelProperty("Waktu Operasi")
    @Column(name = "waktu_memancing" + XMARK)
    private String waktuMemancing;

    @ApiModelProperty("Komentar")
    @Column(name = "komentar" + XMARK, columnDefinition = "TEXT")
    private String komentar;

    @ApiModelProperty("Sumber Informasi")
    @Column(name = "sumber_informasi" + XMARK)
    private String sumberInformasi;


    @ApiModelProperty("Jumlah Tangkapan Untuk Dimakan (Kg)")
    @Column(name = "jumlah_tangkapan_untuk_dimakan_dilaut_volume" + XMARK)
    private double jumlahTangkapanUntukDimakanDilautVolume;

    @ApiModelProperty("Jumlah Tangkapan Untuk Dimakan (Ekor)")
    @Column(name = "jumlah_tangkapan_untuk_dimakan_dilaut_individu" + XMARK)
    private int jumlahTangkapanUntukDimakanDilautIndividu;





//    @ApiModelProperty("Deskripsi")
//    @Column(name = "deskripsi" + XMARK)
//    private String deskripsi; // optional
    // =====================================
    /**
     * Data Operasional Spesifikasi Alat Tangkap
     */
    @Fetch(value = FetchMode.SELECT)
    @ApiModelProperty("Data Informasi Alat Tangkap - (Dengan referensi Kode Operasional)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_operasional" + XMARK)
    private List<OperationalOnFishingToolSpecification> dataSpesifikasiAlatTangkap = new ArrayList<>();

    /**
     * Data Rincian Hasil Tangkapan
     */
    @ApiModelProperty("Data Informasi Detail Tangkapan Operasional - (Dengan referensi Kode Operasional)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_operasional" + XMARK)
//    private Set<OperationalCatchDetails> dataOperasionalDetailTangkapan;
    private List<OperationalCatchDetails> dataOperasionalDetailTangkapan = new ArrayList<>();

    @ApiModelProperty("Status Dokumen")
    @Column(name = "status_dokumen" + XMARK)
    private DocumentStatus statusDokumen;

    @ApiModelProperty("Foto Dokumtenasi")
    @Column(name = "foto_dokumentasi" + XMARK)
    private String photoName;

//    @ApiModelProperty("Kepemilikan Data")
//    @Column(name = "uuid_kepemilikan_data" + XMARK)
//    @ColumnDefault("''")
//    private String uuidKepemilikanData;


    @ApiModelProperty("Jumlah Tangkapan (Kg)")
    @Column(name = "jumlah_tangkapan_volume" + XMARK)
    private double jumlahTangkapanVolume;

    @ApiModelProperty("Jumlah Tangkapan (Ekor)")
    @Column(name = "jumlah_tangkapan_individu" + XMARK)
    private int jumlahTangkapanIndividu;


    @ApiModelProperty("Pengupload Data")
    @Column(name = "uuid_pengupload" + XMARK)
    @ColumnDefault("''")
    private String uuidPengupload;


    @ApiModelProperty("Organisasi Pemilik Data")
    @Column(name = "organisasi" + XMARK)
    @ColumnDefault("''")
    private String organisasi;


    @ApiModelProperty("Lama Perendamana Alat Tangkap")
    @Column(name = "lama_perendaman" + XMARK)
    private double lamaPerendaman;

    @ApiModelProperty("WPP")
    @Column(name = "wpp" + XMARK)
    private String wpp;


    @ApiModelProperty("Terverikasi Oleh pihak BRPL oleh")
    private String terverifikasiOleh;

    @ApiModelProperty("Terverikasi Oleh pihak Luar oleh")
    private String untukEksternalTerverifikasiOleh;

}
