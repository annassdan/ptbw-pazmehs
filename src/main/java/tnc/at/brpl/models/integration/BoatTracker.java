package tnc.at.brpl.models.integration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

//@Entity
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
//@Table(name = Brpl.OTHER + Brpl.CONTENT.OTHER.BOAT_TRACKER)
public class BoatTracker extends EntityModel<BoatTracker, String> implements Brpl {

    // di tabel ifish adalah "boat_id"
    @Column(name = "boat_id")
    @ColumnDefault("'0'")
    @NotNull
    private String boat_id;

    // di tabel ifish adalah "tracker_id"
    @Column(name = "tracker_id")
    @ColumnDefault("'0'")
    @NotNull
    private String tracker_id;

    @Column(name = "tracker_status")
    @ColumnDefault("'0'")
    private int tracker_status;

    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "tracker_start_date")
    private Date tracker_start_date;

    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "tracker_end_date")
    private Date tracker_end_date;


}
