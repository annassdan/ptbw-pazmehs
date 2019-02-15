package tnc.at.brpl.models.integration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Brpl.OTHER + Brpl.CONTENT.OTHER.DEEPSLOPE)
public class Deepslope extends EntityModel<Deepslope, String> implements Brpl {

    @Column(name = "approach")
    private Integer approach;

    // ditabel ifish adalah "user_id"
    @Column(name = "user_id")
    private Long userId;

    // ditabel ifish adalah "partner_id"
    @Column(name = "partner_id")
    private Long partnerId;

    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "landing_date")
    private Date landingDate;

    @Column(name = "landing_location", length = 128)
    private String landingLocation;

    @Column(name = "wpp1", length = 8)
    private String wpp1;

    @Column(name = "wpp2", length = 8)
    private String wpp2;

    @Column(name = "wpp3", length = 8)
    private String wpp3;

    // ditabel ifish adalah "boat_id"
    @ManyToOne
    @JoinColumn(name = "boat_id", referencedColumnName = "uuid")
    private Boat dataBoat;

    @Column(name = "fishing_gear", length = 128)
    private String fishingGear;

    @Column(name = "brought_by", length = 128)
    private String broughtBy;

    @Column(name = "other_fishing_ground", length = 128)
    private String otherFishingGround;

    @Column(name = "supplier", length = 128)
    private String supplier;

    @Column(name = "fishery_type", length = 32)
    private String fisheryType;

    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "entry_date")
    private Date entryTate;

    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "posting_date")
    private Date postingDate;

    @Column(name = "posting_user")
    private Long postingUser;

    @Column(name = "doc_status", length = 32)
    private String docStatus;

    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "first_codrs_picture_date")
    private Date firstCodrsPictureDate;

    @Column(name = "data_status", length = 32)
    private String dataStatus;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "fishing_ledger")
    private Long fishingLedger;

    @Column(name = "total_catch")
    private Integer totalCatch;

    @Fetch(value = FetchMode.SELECT)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "landing_id")
    private List<Sizing> dataSizing;

}
