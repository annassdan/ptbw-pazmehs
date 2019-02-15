
package tnc.at.brpl.models.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;


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
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.OPERATIONAL_ON_FISHING_TOOL_SPEC)
public class OperationalOnFishingToolSpecification extends EntityModel<OperationalOnFishingToolSpecification, String> {

    /**
     * Data Operasional
     */
    //@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "onFishingToolData")
    /*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uuid_operasional" + XMARK)
    @JsonIgnore
    private Operational uuidOperasional;*/

//    @ApiModelProperty("Kode Operasional")
//    @Column(name = "uuid_operasional" + XMARK)
//    private String uuidOperasional;

    @ApiModelProperty("Kode Alat Tangkap")
    @Column(name = "uuid_alat_tangkap" + XMARK)
    private String uuidAlatTangkap;

    @ApiModelProperty("Nama Spesifikasi ")
    @Column(name = "spesifikasi" + XMARK)
    private String spesifikasi;

    @ApiModelProperty("Nilai Spesifikasi ")
    @Column(name = "nilai_spesifikasi" + XMARK)
    private String nilaiSpesifikasi;

    @ApiModelProperty("Satuan Spesifikasi ")
    @Column(name = "satuan_spesifikasi" + XMARK)
    @ColumnDefault("''")
    private String satuanSpesifikasi;

   /* @ApiModelProperty("Panjang Ris atas")
    @Column(name = "panjang_ris_atas" + XMARK)
    private int panajangRisAtas; // old = netDimensions

    @ApiModelProperty("Ukuran Standar/ ukuran jaring insang ")
    @Column(name = "ukuran_jaring_insang" + XMARK)
    private int ukuranJaringInsang;

   // @ApiModelProperty("Ukuran Normal jaring")
    //@Column(name = "net_normal_size" + XMARK)
    //private int netNormalSize;

    @ApiModelProperty("Ukuran Kantong Pukat")
    @Column(name = "ukuran_kantong_pukat" + XMARK)
    private int ukuranKantongPukat;

    @ApiModelProperty("Ukuran Badan Pukat")
    @Column(name = "ukuran_badan_pukat" + XMARK)
    private int ukuranBadanPukat;

    @ApiModelProperty("Ukuran Sayap Pukat")
    @Column(name = "ukuran_sayap_pukat" + XMARK)
    private int ukuranSayapPukat;

    @ApiModelProperty("Ukuran Lapis Dalam Jaring Tramel")
    @Column(name = "ukuran_lapis_dalam_jaring_tramel" + XMARK)
    private int ukuranLapisDalamJaringTramel;

    @ApiModelProperty("Ukuran Lapis Luar Jaring Tramel")
    @Column(name = "ukuran_lapis_luar_jaring_tramel" + XMARK)
    private int ukuranLapisLuarJaringTramel;


    // pancing

    @ApiModelProperty("Nomor mata pancing")
    @Column(name = "nomor_mata_pancing" + XMARK)
    private int nomorMataPancing;

    @ApiModelProperty("Ukuran Tinggi mata Pancing")
    @Column(name = "ukuran_tinggi_mata_pancing" + XMARK)
    private double ukuranTinggiMataPancing;

    @ApiModelProperty("Ukuran Lebar Mata Pancing")
    @Column(name = "ukuran_lebar_mata_pancing" + XMARK)
    private double ukuranLebarMataPancing;
    //


    @ApiModelProperty("Jumlah Alat Tangkap yang dioperasikan")
    @Column(name = "jumlah_alat_tangkap_yang_dioperasikan" + XMARK)
    private int jumlahAlatTangkapYangDioperasikan;

    @ApiModelProperty("Jumlah kali Setting pada alat tangkap")
    @Column(name = "jumlah_setting_alat_tangkap" + XMARK)
    private int jumlahSettingAlatTangkap;

    @ApiModelProperty("Bahan Pembuat Alat Tangkap")
    @Column(name = "material" + XMARK)
    private String material;*/


}
