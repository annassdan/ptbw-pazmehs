package tnc.at.brpl.models.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.configurations.CustomTimeSerializer;
import tnc.at.brpl.models.main.OperationalCatchDetails;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class Operational3rdPartyDTO implements Brpl {

    @Id
    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
    @GeneratedValue(generator = "brpl_id")
    @ApiModelProperty("ID")
    private String id;


    @ApiModelProperty("Nama tempat Pendaratan")
    private String namaLokasiPendaratan;

    @ApiModelProperty("Nama Sumber Daya")
    private String namaSumberDaya;

    @ApiModelProperty("Nama Enumerator/Pencatat/Peneliti/Partner")
    private String namaPencatat;

    /** ============= */

    @ApiModelProperty("Tanggal Sampling")
    @Temporal(TemporalType.TIME)
    @JsonSerialize(using = CustomTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private Date jamSampling;

    @ApiModelProperty("Tanggal Sampling")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private Date tanggalSampling;

    @ApiModelProperty("Nama Kapal")
    private String namaKapal;

    @ApiModelProperty("Tanggal Berangkat")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private Date tanggalBerangkat;

    @ApiModelProperty("Nomor Lambung Kapal/ Tanda Selar")
    private String tandaSelar;

    @ApiModelProperty("Tanggal Kembali")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private Date tanggalKembali;

    @ApiModelProperty("Nama Pemilik Kapal")
    private String namaPemilikKapal;

    @ApiModelProperty("Pelabuhan Asal")
    private String pelabuhanAsal;

    @ApiModelProperty("Nama Kapten")
    private String namaKapten;

    @ApiModelProperty("Jumlah ABK")
    private int jumlahAbk;

    @ApiModelProperty("Panjang Kapal")
    private double panjangKapal;

    @ApiModelProperty("Bahan Pembuatan Kapal")
    private String materialKapal;

    @ApiModelProperty("Daya Lampu/Cahaya")
    private int dayaCahaya;

    @ApiModelProperty("Berat Kapal (GT)")
    private int bobotKapal;

    @ApiModelProperty("Apakah Mempunyai Kapal Bantu?")
    private boolean kapalBantu;

    @ApiModelProperty("Jika mempunyai kapal bantu, berapa ukuran Kapal Bantu (GT)?")
    private int ukuranKapalBantu;


    @ApiModelProperty("Apakah Kapal Kapal Andon?")
    private boolean kapalAndon;

    @ApiModelProperty("Jika kapal andon, maka berasal darimana?")
    private String asalKapalAndon;

    @ApiModelProperty("Jumlah Palka")
    private int jumlahPalka;

    @ApiModelProperty("Jumlah Boks")
    private int jumlahBoks;

    @ApiModelProperty("Mesin Utama (PK)")
    private int mesinUtama;

    @ApiModelProperty("Apakah mempunyai Pendingin (Freezer)")
    private boolean freezer;

    @ApiModelProperty("Jika mempunya Pendingin, maka kapasitasnya berapa?")
    private double kapasitasFreezer;

    @ApiModelProperty("Kapasitas Palka atau Boks")
    private int kapasitasPalkaBoks;

    @ApiModelProperty("Apakah mempunyai mesin bantu? Jika ada, maka berapa ukurannya (GT)?")
    private int mesinBantu;

    @ApiModelProperty("Apakah menggunakan GPS?")
    private boolean gps;

    @ApiModelProperty("Jika menggunakan GPS, maka apa jenis GPS-nya?")
    private String jenisGps;

    /// ======================================= alat tangkap ==

    @ApiModelProperty("Nama Alat Tangkap")
    private String namaAlatTangkap;

    @ApiModelProperty("Bahan Pembuat Alat Tangkap")
    private String materialAlatTangkap;

    @ApiModelProperty("Jumlah Alat Tangkap yang dioperasikan")
    private int jumlahAlatTangkapYangDioperasikan;


    @ApiModelProperty("Jumlah kali Setting")
    private int jumlahSetting;

    // ========================================================


    @ApiModelProperty("Kedalaman Perairan Mulai")
    private double kedalamanAirMulai;

    @ApiModelProperty("Kedalaman Perairan Hingga")
    private double kedalamanAirHingga;

    @ApiModelProperty("Daerah penangkapan/memancing berdasarkan grid yang ditetapkan oleh BRPL")
    private String daerahPenangkapan;

    @ApiModelProperty("Jumlah Hari Per Trip")
    private int jumlahHariPerTrip;

    @ApiModelProperty("Jumlah Hari Menangkap")
    private int jumlahHariMenangkap;

    @ApiModelProperty("Jenis Rumpon")
    private String jenisRumpon;

    @ApiModelProperty("Total Balok Es yang digunakan")
    private double jumlahBalokEs;

    @ApiModelProperty("Jumlah Rumpon yang dikunjungi")
    private int jumlahRumponDikunjungi;

    @ApiModelProperty("Jumlah rumpon berhasil")
    private int jumlahRumponBerhasil;

    @ApiModelProperty("Waktu Operasi")
    private String waktuMemancing;

    @ApiModelProperty("Komentar")
    private String komentar;

    @ApiModelProperty("Sumber Informasi")
    private String sumberInformasi;


    @ApiModelProperty("Jumlah Tangkapan Untuk Dimakan (Kg)")
    private double jumlahTangkapanUntukDimakanDilautVolume;

    @ApiModelProperty("Jumlah Tangkapan Untuk Dimakan (Ekor)")
    private int jumlahTangkapanUntukDimakanDilautIndividu;

    /**
     * Data Operasional Spesifikasi Alat Tangkap
     */
    @ApiModelProperty("Data Informasi Alat Tangkap - (Dengan referensi Kode Operasional)")
    private List<OperationalOnFishingToolSpecification3rdPartyDTO> dataSpesifikasiAlatTangkap = new ArrayList<>();

    /**
     * Data Rincian Hasil Tangkapan
     */
    @ApiModelProperty("Data Informasi Detail Tangkapan Operasional - (Dengan referensi Kode Operasional)")
    private List<OperationalCatchDetails3rdPartyDTO> dataOperasionalDetailTangkapan = new ArrayList<>();


    @ApiModelProperty("Status Dokumen")
    private DocumentStatus statusDokumen;

    @ApiModelProperty("Jumlah Tangkapan (Kg)")
    private double jumlahTangkapanVolume;

    @ApiModelProperty("Jumlah Tangkapan (Ekor)")
    private int jumlahTangkapanIndividu;

    @ApiModelProperty("Lama Perendamana Alat Tangkap")
    @Column(name = "lama_perendaman" + XMARK)
    private double lamaPerendaman;

    @ApiModelProperty("WPP")
    @Column(name = "wpp" + XMARK)
    private String wpp;

}
