package tnc.at.brpl.models.integration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = Brpl.OTHER + Brpl.CONTENT.OTHER.BOAT)
public class Boat extends EntityModel<Boat, String> implements Brpl {

    @Column(name = "program_site", length = 128)
    private String programSite;

    @Column(name = "program_type", length = 32)
    private String programType;

    @Column(name = "picture_original")
    private Long pictureOriginal;

    @Column(name = "picture_censored")
    private Long pictureCensored;

    @Column(name = "boat_code", length = 24)
    private String boatCode;

    @Column(name = "boat_name", length = 64)
    private String boatName;

    @Column(name = "fishing_gear", length = 32)
    private String fishingGear;

    @Column(name = "codrs_contract")
    private Integer codrsContract;

    @Column(name = "captain", length = 64)
    private String captain;

    @Column(name = "captain_origin", columnDefinition = "TEXT")
    private String captainOrigin;

    @Column(name = "owner", length = 64)
    private String owner;

    @Column(name = "owner_origin", columnDefinition = "TEXT")
    private String ownerOrigin;

    @Column(name = "owner_district", columnDefinition = "TEXT")
    private String ownerDistrict;

    @Column(name = "owner_province", columnDefinition = "TEXT")
    private String ownerProvince;

    @Column(name = "registration_port", length = 64)
    private String registrationPort;

    @Column(name = "year_built")
    private Integer yearBuilt;

//    @Digits(integer = 6, fraction = 2)
    @Column(name = "length_of_boat", columnDefinition = "Decimal(6,2) default '0.00'")
//    @ColumnDefault("'0'")
    private Double lengthOfBoat;

//    @Digits(integer = 6, fraction = 2)
    @Column(name = "width_of_boat", columnDefinition = "Decimal(6,2) default '0.00'")
//    @ColumnDefault("'0'")
    private Double widthOfBoat;

//    @Digits(integer = 6, fraction = 2)
    @Column(name = "height_of_boat", columnDefinition = "Decimal(6,2) default '0.00'")
//    @ColumnDefault("'0'")
    private Double heightOfBoat;

//    @Digits(integer = 10, fraction = 2)
    @Column(name = "capacity_palka_m3", columnDefinition = "Decimal(10,2) default '0.00'")
//    @ColumnDefault("'0'")
    private Double capacityPalkaM3;

//    @Digits(integer = 6, fraction = 2)
    @Column(name = "gt_estimate", columnDefinition = "Decimal(6,2) default '0.00'")
//    @ColumnDefault("'0'")
    private Double gtEstimate;

//    @Digits(integer = 6, fraction = 2)
    @Column(name = "gt_declared", columnDefinition = "Decimal(6,2) default '0.00'")
//    @ColumnDefault("'0'")
    private Double gtDeclared;

    @Column(name = "size_category", length = 32)
    private String sizeCategory;

    @Column(name = "engine_spec", columnDefinition = "TEXT")
    private String engineSpec;

    @Column(name = "number_of_engine")
    private Integer numberOfEngine;

    @Column(name = "number_of_crew")
    private Integer numberOfCrew;

    @Column(name = "wpp_permit1", length = 32)
    private String wppPermit1;

    @Column(name = "wpp_permit2", length = 32)
    private String wppPermit2;

    @Column(name = "wpp_permit3", length = 32)
    private String wppPermit3;

    @Column(name = "landing_port1", columnDefinition = "TEXT")
    private String landingPort1;

    @Column(name = "landing_port2", columnDefinition = "TEXT")
    private String landingPort2;

    @Column(name = "trans_destination1", columnDefinition = "TEXT")
    private String transDestination1;

    @Column(name = "trans_destination2", columnDefinition = "TEXT")
    private String transDestination2;

    @Column(name = "company1", columnDefinition = "TEXT")
    private String company1;

    @Column(name = "company2", columnDefinition = "TEXT")
    private String company2;

    @Column(name = "company3", columnDefinition = "TEXT")
    private String company3;

    @Column(name = "fishing_area1", length = 32)
    private String fishingArea1;

    @Column(name = "fishing_area2", length = 32)
    private String fishingArea2;

    @Column(name = "fishing_area3", length = 32)
    private String fishingArea3;

    @Column(name = "avg_trip_per_year")
    private Integer avgTripPerYear;

    @Column(name = "avg_catch_per_trip_kg", columnDefinition = "Decimal(6,2) default '0.00'")
    private Double avgCatchPerTripKg;

    @Column(name = "avg_catch_per_year_kg", columnDefinition = "Decimal(12,2) default '0.00'")
    private Double avgCatchPerYearKg;

    @Column(name = "uuid_data", columnDefinition = "TEXT")
    private String uuidData; // di kolom tabel ifish adalah "uuid"

    @Column(name = "counter")
    private Integer counter;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN_MINIMAL)
    @Column(name = "codrs_start_date")
    private Date codrsStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN_MINIMAL)
    @Column(name = "codrs_end_date")
    private Date codrsEndDate;

    @Column(name = "engine_hp1")
    private Integer engineHp1;

    @Column(name = "engine_hp2")
    private Integer engineHp2;

    @Column(name = "engine_hp3")
    private Integer engineHp3;

    @Column(name = "category", length = 32)
    private String category;

    @Column(name = "fishing_gear_description", columnDefinition = "TEXT")
    private String fishingGearDescription;

}
