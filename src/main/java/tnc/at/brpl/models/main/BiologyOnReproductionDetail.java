package tnc.at.brpl.models.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.LengthType;
import tnc.at.brpl.utils.Sex;
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
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.BIOLOGY_ON_REPRODUCTION_DETAIL)
public class BiologyOnReproductionDetail extends EntityModel<BiologyOnReproductionDetail, String>
    implements Brpl {



    @ApiModelProperty("Ukuran Panjang")
    @Column(name = "panjang" + XMARK)
    private double panjang;

//    @Enumerated(EnumType.STRING)
    @ApiModelProperty("Tipe Panjang (FL, SL, TL, CL, ML)")
    @Column(name = "tipe_panjang" + XMARK, length = 15)
    private String tipePanjang;


    //@Enumerated(EnumType.STRING)
    @ApiModelProperty("Jenis Kelamin")
    @Column(name = "jenis_kelamin" + XMARK, length = 10)
    @ColumnDefault("'UI'")
    private String jenisKelamin;


    @ApiModelProperty("Berat Ikan")
    @Column(name = "berat" + XMARK)
    private double berat;

    @ApiModelProperty("Tingkat Kematangan Gonad")
    @Column(name = "tkg" + XMARK, length = 50)
    private String tkg;


//    @ApiModelProperty("Berat Gonad Ikan")
//    @Column(name = "berat_gonad" + XMARK)
//    private double beratGonad;

    @ApiModelProperty("Berat Isi Perut")
    @Column(name = "berat_isi_perut" + XMARK)
    private double beratIsiPerut;


//    @ApiModelProperty("Data Detail Biologi Reproduksi Isi Perut - (Dengan referensi kode Biologi reproduksi Detail)")
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "uuid_biologireproduksidetail" + XMARK)
////    private Set<BiologyOnReproductionStomachContentsDetail> dataIsiPerutDetail = new HashSet<>();
//    private List<BiologyOnReproductionStomachContentsDetail> dataIsiPerutDetail = new ArrayList<>();


}
