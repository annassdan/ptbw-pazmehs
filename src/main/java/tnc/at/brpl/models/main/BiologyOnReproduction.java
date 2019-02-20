
package tnc.at.brpl.models.main;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.UnpredictableBoolean;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.entity.EntityModel;
import javax.persistence.*;
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
@Table(name = Brpl.UNIQUE + Brpl    .CONTENT.BIOLOGY_ON_REPRODUCTION)
public class BiologyOnReproduction extends EntityModel<BiologyOnReproduction, String> {

    // header
    @ApiModelProperty("Kode Sumber Daya")
    @Column(name = "uuid_sumber_daya" + XMARK)
    private String uuidSumberDaya;

    @ApiModelProperty("Nama tempat Sampling")
    @Column(name = "nama_lokasi_sampling" + XMARK)
    private String namaLokasiSampling;

    @ApiModelProperty("Nama Kapal")
    @Column(name = "nama_kapal" + XMARK)
    private String namaKapal;


    @ApiModelProperty("Kode Spesies")
    @Column(name = "uuid_spesies" + XMARK)
    private String uuidSpesies;

    @ApiModelProperty("Daerah penangkapan/memancing")
    @Column(name = "daerah_penangkapan" + XMARK)
    private String daerahPenangkapan;

    @ApiModelProperty("Apakah Penampung?")
    @Column(name = "penampung" + XMARK, length = 6)
//    @ColumnDefault("'na'")
//    private UnpredictableBoolean penampung;
    private boolean penampung;


    @ApiModelProperty("Apakah Penangkap?")
//    @ColumnDefault("'na'")
    @Column(name = "penangkap" + XMARK, length = 6)
//    private UnpredictableBoolean penangkap;
    private boolean penangkap;

//    @ApiModelProperty("Lama Memancing")
//    @Column(name = "lama_memancing" + XMARK)
//    private int lamaMemancing;

    @ApiModelProperty("Kode Alat Tangkap")
    @Column(name = "uuid_alat_tangkap" + XMARK)
    private String uuidAlatTangkap;

    @ApiModelProperty("Tanggal Sampling")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "tanggal_sampling" + XMARK)
    private Date tanggalSampling;

    @ApiModelProperty("Kode Enumerator/ Peneliti") //
    @Column(name = "uuid_enumerator" + XMARK)
    private String uuidEnumerator;

    //detail

    @ApiModelProperty("Data Detail Biologi Reproduksi - (Dengan referensi kode Biologi reproduksi)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "uuid_biologireproduksi" + XMARK)
//    private Set<BiologyOnReproductionDetail> dataDetailReproduksi = new HashSet<>();
    private List<BiologyOnReproductionDetail> dataDetailReproduksi = new ArrayList<>();


    @ApiModelProperty("Status Dokumen")
    @Column(name = "status_dokumen" + XMARK)
    private DocumentStatus statusDokumen;


    @ApiModelProperty("Foto Dokumtenasi")
    @Column(name = "foto_dokumentasi" + XMARK)
    private String photoName;


//    @ApiModelProperty("Kepemilikan Data")
//    @Column(name = "uuid_ke
//    pemilikan_data" + XMARK)
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

    @ColumnDefault("false")
    private boolean byMachine;


}
