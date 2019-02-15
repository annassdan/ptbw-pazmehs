
package tnc.at.brpl.models.main;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Target;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.models.administrator.SysUserGroup;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.*;


/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Brpl.UNIQUE + Brpl.CONTENT.LANDING)
public class Landing extends EntityModel<Landing, String> {

    /**
     * Header
     */
    @ApiModelProperty("Tanggal Pendaratan")
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "tanggal_pendaratan" + XMARK)
    private Date tanggalPendaratan;

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

    /**
     * Data Rincian Pendaratan
     */
    @ApiModelProperty("Data Operasional - (Dengan referensi Kode Pendaratan)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_pendaratan" + XMARK)
//    private Set<LandingDetail> dataRincianPendaratan = new HashSet<>();
    private List<LandingDetail> dataRincianPendaratan = new ArrayList<>();

    @ApiModelProperty("Status Dokumen")
    @Column(name = "status_dokumen" + XMARK)
    private DocumentStatus statusDokumen;

    @ApiModelProperty("Foto Dokumtenasi")
    @Column(name = "foto_dokumentasi" + XMARK)
    private String photoName;


    @ApiModelProperty("Pengupload Data")
    @Column(name = "uuid_pengupload" + XMARK)
    @ColumnDefault("''")
    private String uuidPengupload;

    /**
     * Data Operasional
     */
    @Fetch(value = FetchMode.SELECT)
    @ApiModelProperty("Data Operasional - (Dengan referensi Kode Pendaratan)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_pendaratan" + XMARK)
    private List<Operational> dataOperasional = new ArrayList<>();

    /**
     * Data Biology Ukuran
     */
    @Fetch(value = FetchMode.SELECT)
    @ApiModelProperty("Data Sampling Biology Ukuran - (Dengan referensi kode Pendaratan)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_pendaratan" + XMARK)
    private List<BiologyOnSize> dataUkuran = new ArrayList<>();

    /**
     * Data Biology Reproduksi
     */
    @Fetch(value = FetchMode.SELECT)
    @ApiModelProperty("Data Sampling Biology Reproduksi - (Dengan referensi kode Pendaratan)")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_pendaratan" + XMARK)
    private List<BiologyOnReproduction> dataReproduksi = new ArrayList<>();

    @ApiModelProperty("Organisasi Pemilik Data")
    @Column(name = "organisasi" + XMARK)
    @ColumnDefault("''")
    private String organisasi;

    @ApiModelProperty("User Group")
    @ManyToOne
    private SysUserGroup userGroup;

    @ApiModelProperty("WPP")
    @Column(name = "wpp" + XMARK)
    private String wpp;


    @ApiModelProperty("Terverikasi Oleh pihak BRPL oleh")
    private String terverifikasiOleh;

    @ApiModelProperty("Terverikasi Oleh pihak Luar oleh")
    private String untukEksternalTerverifikasiOleh;





}
