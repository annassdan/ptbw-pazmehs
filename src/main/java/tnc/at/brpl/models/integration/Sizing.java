package tnc.at.brpl.models.integration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.models.master.Species;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Brpl.OTHER + Brpl.CONTENT.OTHER.SIZING)
public class Sizing extends EntityModel<Sizing, String> implements Brpl {

    // di tabel ifish adalah "fish_id"
//    @Column(name = "fish_id")
//    private String fish_id;

    @ManyToOne
    @JoinColumn(name = "fish_id", referencedColumnName = "uuid")
    private Species dataSpecies;

    @Column(name = "cm", columnDefinition = "Decimal(10,2) default '0.00'")
    private Double cm;


    @Column(name = "data_quality")
    private Integer dataQuality;

    // di tabel ifish adalah "landing_id"
//    @Column(name = "landing_id")
//    private String landing_id;

    // di tabel ifish adalah "offloading_id"
    @Column(name = "offloading_id")
    private Long offloadingId;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN_MINIMAL)
    @Column(name = "codrs_picture_date")
    private Date codrsPictureDate;

    @Column(name = "codrs_picture_name", length = 128)
    private String codrsPictureName;

    @Column(name = "length_type", length = 32)
    private String lengthType;



}
