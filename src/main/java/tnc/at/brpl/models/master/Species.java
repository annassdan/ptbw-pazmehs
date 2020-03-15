
package tnc.at.brpl.models.master;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@SuperBuilder
@Table(name = Brpl.UNIQUE  + Brpl.MASTER + Brpl.CONTENT.SPECIES)
public class Species extends EntityModel<Species, String> {

    /*
    @ApiModelProperty("Nama Spesies dalam Bahasa Indonesia")
    @Column(name = "nama_spesies_id" + XMARK)
    private String namaSpesiesID;

    @ApiModelProperty("Nama Spesies dalam Bahasa Latin")
    @Column(name = "nama_spesies_lat" + XMARK)
    private String namaSpesiesLAT;

    @ApiModelProperty("Nama Spesies dalam Bahasa Lain")
    @Column(name = "nama_lain_spesies" + XMARK)
    private String namaLainSpesies;

    @ApiModelProperty("Family dari Spesies")
    @Column(name = "family" + XMARK)
    private String family;

    @ApiModelProperty("Class dari Spesies")
    @Column(name = "classs" + XMARK)
    private String classs;

    @ApiModelProperty("Ordo dari Spesies")
    @Column(name = "ordo" + XMARK)
    private String ordo;


    @ApiModelProperty("Kode FAO dari Spesies")
    @Column(name = "kode_fao" + XMARK)
    private String kodeFAO;

    @ApiModelProperty("Deskripsi dari Spesies")
    @Column(name = "deskripsi_spesies" + XMARK)
    private String deskripsiSpesies;
    */

    @Column(name = "largest_specimen_cm", columnDefinition = "Decimal(10,2) default '0.00'")
    private Double largestSpecimenCm;


    @ApiModelProperty("Apakah merupakan tipe Family?")
    @Column(name = "is_family_id" + XMARK)
    private boolean family;

    @ApiModelProperty("Fish Phylum")
    @Column(name = "fish_phylum" + XMARK, length = 64)
    private String fishPhylum;

    @ApiModelProperty("Fish Class")
    @Column(name = "fish_class" + XMARK, length = 64)
    private String fishClass;

    @ApiModelProperty("Fish Order")
    @Column(name = "fish_order" + XMARK, length = 64)
    private String fishOrder;

    @ApiModelProperty("Fish Family")
    @Column(name = "fish_family" + XMARK, length = 64)
    private String fishFamily;

    @ApiModelProperty("Fish Genus")
    @Column(name = "fish_genus" + XMARK, length = 64)
    private String fishGenus;

    @ApiModelProperty("Fish Species")
    @Column(name = "fish_species" + XMARK, length = 64)
    private String fishSpecies;

    @ApiModelProperty("Nama Umum Ikan")
    @Column(name = "common_names" + XMARK, columnDefinition = "TEXT")
    private String namaUmum;

    @ApiModelProperty("Nama Indonesia Ikan")
    @Column(name = "indonesian_name" + XMARK, columnDefinition = "TEXT")
    private String namaIndonesia;

    @ApiModelProperty("Nama Latin Ikan")
    @Column(name = "hawaiian_name" + XMARK, columnDefinition = "TEXT")
    private String namaLatin;

    @Column(name = "market_fishes_of_indonesia" + XMARK, columnDefinition = "TEXT")
    private String marketFishesOfIndonesia;

    @Column(name = "other_names" + XMARK, columnDefinition = "TEXT")
    private String otherNames;

    @Column(name = "fish_code" + XMARK)
    private String fishCode;

    @ApiModelProperty("Counter")
    @Column(name = "counter" + XMARK)
    private Integer counter;

    @Column(name = "prefix_code" + XMARK)
    private String prefixCode;

    @Column(name = "insite_code" + XMARK)
    private String insiteCode;

    @Column(name = "default_picture_id" + XMARK)
    private Long defaultPictureId;

    @Column(name = "largest_specimen_id" + XMARK)
    private Long largestSpecimenId;

    @Column(name = "largest_specimen_picture" + XMARK)
    private Long largestSpecimenPicture;

    @Column(name = "largest_specimen_catch_area" + XMARK, length = 128)
    private String largestSpecimenCatchArea;

    @Column(name = "uoa" + XMARK, length = 4)
    private String uoa;

    @ApiModelProperty("LMAT")
    @Column(name = "lmat" + XMARK)
    private Integer lmat;

    @ApiModelProperty("LOPT")
    @Column(name = "lopt" + XMARK)
    private int lopt;

    @ApiModelProperty("LINF")
    @Column(name = "linf" + XMARK)
    private int linf;

    @ApiModelProperty("LMAX")
    @Column(name = "lmax" + XMARK)
    private int lmax;

    @ApiModelProperty("LMATM")
    @Column(name = "lmatm" + XMARK)
    private int lmatm;

    @ApiModelProperty("Spesies Nomor ID")
    @Column(name = "species_id_number" + XMARK)
    private int speciesIdNumber;

    @ApiModelProperty("Reported Trade Limit Weight")
    @Column(name = "reported_trade_limit_weight" + XMARK)
    private Double reportedTradeLimitWeight;

    @ApiModelProperty("Var A")
    @Column(name = "var_a" + XMARK)
    private Double varA;

    @ApiModelProperty("Var B")
    @Column(name = "var_b" + XMARK)
    private Double varB;

    @ApiModelProperty("Length Basis")
    @Column(name = "length_basis" + XMARK, length = 4)
    private String lengthBasis;

    @ApiModelProperty("Converted Trade Limit L")
    @Column(name = "converted_trade_limit_l" + XMARK)
    private Double convertedTradeLimitL;

    @ApiModelProperty("Plotted Trade Limit TL")
    @Column(name = "plotted_trade_limit_tl" + XMARK)
    private Double plottedTradeLimitTl;

    @ApiModelProperty("Conversion Factor TL2FL")
    @Column(name = "conversion_factor_tl2fl" + XMARK)
    private Double conversionFactorTl2fl;


}
