package tnc.at.brpl.models.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.UnpredictableBoolean;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.LANDING_DETAILS)
public class LandingDetail extends EntityModel<LandingDetail, String> {

    @ApiModelProperty("Nama Kapal")
    @Column(name = "nama_kapal" + XMARK)
    private String namaKapal;

    @ApiModelProperty("Apakah Penampung?")
    @Column(name = "penampung" + XMARK)
//    @ColumnDefault("'NA'")
//    private UnpredictableBoolean penampung;
    private boolean penampung;

    @ApiModelProperty("Apakah Penangkap?")
    @Column(name = "penangkap" + XMARK)
//    @ColumnDefault("'NA'")
//    private UnpredictableBoolean penangkap;
    private boolean penangkap;

    @ApiModelProperty("Jumlah Kapal Penangkap")
    @Column(name = "jumlah_kapal_penangkap" + XMARK)
    private int jumlahKapalPenangkap;

    @ApiModelProperty("Kode Alat Tangkap")
    @Column(name = "uuid_alat_tangkap" + XMARK)
    private String uuidAlatTangkap;

    @ApiModelProperty("Jumlah Seting")
    @Column(name = "jumlah_setting" + XMARK)
    private int jumlahSetting;

    @ApiModelProperty("Jumlah Mata Pancing")
    @Column(name = "jumlah_mata_pancing" + XMARK)
    private int jumlahMataPancing;

//    @ApiModelProperty("Rumpon atau Cahaya")
//    @Column(name = "rumpon_cahaya" + XMARK)
//    private String rumpon_cahaya;

    @ApiModelProperty("Rumpon ")
    @Column(name = "rumpon" + XMARK)
    private boolean rumpon;

    @ApiModelProperty("Cahaya")
    @Column(name = "cahaya" + XMARK)
    private boolean cahaya;

    @ApiModelProperty("Daerah penangkapan/memancing")
    @Column(name = "daerah_penangkapan" + XMARK)
    private String daerahPenangkapan;

    //@ApiModelProperty("Jumlah Hari Efektif")
    //@Column(name = "jumlah_hari_efektif_memancing" + XMARK)
    //private int jumlahHariEfektifMemancing;

    @ApiModelProperty("Jumlah Hari Per Trip")
    @Column(name = "jumlah_hari_per_trip" + XMARK)
    private int jumlahHariPerTrip;

    @ApiModelProperty("Jumlah Hari Menangkap")
    @Column(name = "jumlah_hari_menangkap" + XMARK)
    private int jumlahHariMenangkap;

    @ApiModelProperty("Total Tangkapan (Kg)")
    @Column(name = "total_tangkapan_volume" + XMARK)
    private double totalTangkapanVolume;

    @ApiModelProperty("Total Tangkapan (Ekor)")
    @Column(name = "total_tangkapan_individu" + XMARK)
    private int totalTangkapanIndividu;

    /**
     * Data Detail Tangkapan
     */
    @ApiModelProperty("Data Detail Tangkapan pada Pendaratan - (Dengan referensi kode Rincian Pendaratan)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_rincian_pendaratan" + XMARK)
//    private Set<LandingCatchDetail> dataRincianTangkapanPendaratan = new HashSet<>();
    private List<LandingCatchDetail> dataRincianTangkapanPendaratan = new ArrayList<>();


}
