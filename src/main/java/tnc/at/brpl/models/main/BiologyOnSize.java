
package tnc.at.brpl.models.main;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.*;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.UnitType;
import tnc.at.brpl.utils.UnpredictableBoolean;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;


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
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.BIOLOGY_ON_SIZE)
public class BiologyOnSize extends EntityModel<BiologyOnSize, String> {

    // header

    @ApiModelProperty("Kode Enumerator/ Peneliti")              //
    @Column(name = "uuid_enumerator" + XMARK)
    private String uuidEnumerator;

    @ApiModelProperty("Kode Sumber Daya")
    @Column(name = "uuid_sumber_daya" + XMARK)
    private String uuidSumberDaya;

    @ApiModelProperty("Nama tempat Pendaratan")
    @Column(name = "nama_lokasi_sampling" + XMARK)
    private String namaLokasiSampling;

    @ApiModelProperty("Tanggal Sampling")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "tanggal_sampling" + XMARK)
    private Date tanggalSampling;

    @ApiModelProperty("Nama Kapal")
    @Column(name = "nama_kapal" + XMARK)
    private String namaKapal;

    @ApiModelProperty("Daerah penangkapan/memancing")
    @Column(name = "daerah_penangkapan" + XMARK)
    private String daerahPenangkapan;

    @ApiModelProperty("Kode Alat Tangkap")
    @Column(name = "uuid_alat_tangkap" + XMARK)
    private String uuidAlatTangkap;

    @ApiModelProperty("Apakah Penampung?")
    @Column(name = "penampung" + XMARK, length = 6)
//    @ColumnDefault("'na'")
//    private UnpredictableBoolean penampung;
    private boolean penampung;

    @ApiModelProperty("Apakah Penangkap?")
    @Column(name = "penangkap" + XMARK, length = 6)
//    @ColumnDefault("'na'")
//    private UnpredictableBoolean penangkap;
    private boolean penangkap;

    @ApiModelProperty("Total Tangkapan (Kg)")
    @Column(name = "total_tangkapan_volume" + XMARK)
    private double totalTangkapanVolume;

    @ApiModelProperty("Total Tangkapan (Ekor)")
    @Column(name = "total_tangkapan_individu" + XMARK)
    private int totalTangkapanIndividu;

    @ApiModelProperty("Jumlah total yang disampling per Individu (Ekor)")
    @Column(name = "total_sampel_individu" + XMARK)
    private int totalSampelIndividu;

    @ApiModelProperty("Jumlah total yang disampling per volume (Kg)")
    @Column(name = "total_sampel_volume" + XMARK)
    private double totalSampelVolume;

    /*@Enumerated(EnumType.STRING)
    @ApiModelProperty("Satuan total yang disampling (KG / EKOR)")
    @Column(name = "satuan_total_sampel" + XMARK)
    private UnitType satuanTotalSampel;*/



    //


    //@ApiModelProperty("Panjang Fork Length")
    //@Column(name = "fork_length" + XMARK)
    //private double forkLength;

    /**
     * Data detail sample yang diambil
     */
//    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(value = FetchMode.SUBSELECT)
    @ApiModelProperty("Data Rincian Jumlah Sampel yang digunakan - (Dengan referensi kode Biologi Ukuran)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_biologiukuran" + XMARK)
    private List<BiologyOnSizeSampleDetail> dataSampleDetail; // = new HashSet<>();

    /**
     * Detail ukuran ikan dari sample
     */
    @ApiModelProperty("Data Detail Biologi Ukuran - (Dengan referensi kode Biologi Ukuran)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_biologiukuran" + XMARK)
    private List<BiologyOnSizeDetail> dataUkuranDetail; // = new ArrayList<>();



    @ApiModelProperty("Status Dokumen")
    @Column(name = "status_dokumen" + XMARK)
    private DocumentStatus statusDokumen;


    @ApiModelProperty("Foto Dokumtenasi")
    @Column(name = "foto_dokumentasi" + XMARK)
    private String photoName;

//
//    @ApiModelProperty("Kepemilikan Data")
//    @Column(name = "uuid_kepemilikan_data" + XMARK)
//    @ColumnDefault("''")
//    private String uuidKepemilikanData;


    @ApiModelProperty("Pengupload Data")
    @Column(name = "uuid_pengupload" + XMARK)
    @ColumnDefault("''")
    private String uuidPengupload;

    @ApiModelProperty("Organisasi Pemilik Data")
    @Column(name = "organisasi" + XMARK)
    @ColumnDefault("''")
    private String organisasi;


    @ApiModelProperty("WPP")
    @Column(name = "wpp" + XMARK)
    private String wpp;


    @ApiModelProperty("Terverikasi Oleh pihak BRPL oleh")
    private String terverifikasiOleh;

    @ApiModelProperty("Terverikasi Oleh pihak Luar oleh")
    private String untukEksternalTerverifikasiOleh;


}
