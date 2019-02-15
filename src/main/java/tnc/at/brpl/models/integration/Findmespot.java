package tnc.at.brpl.models.integration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = Brpl.OTHER + Brpl.CONTENT.OTHER.FINDMESPOT)
public class Findmespot extends EntityModel<Findmespot, String> implements Brpl {


    // di tabel ifish adalah "findmespot_id"
    @Column(name = "findmespot_id")
    private String findmespot_id;

    // di tabel ifish adalah "tracker_id"
    @Column(name = "tracker_id")
    @ColumnDefault("'0'")
    @NotNull
    private String tracker_id;

    @Column(name = "tracker_name", length = 64)
    @ColumnDefault("''")
    private String tracker_name;

    @Column(name = "unix_time")
    private String unix_time;

    @Column(name = "message_type", length = 32)
    private String message_type;

    @Column(name = "latitude", columnDefinition = "Decimal(10,5) default '0.00'")
    private double latitude;

    @Column(name = "longitude", columnDefinition = "Decimal(10,5) default '0.00'")
    private double longitude;

    // di tabel ifish adalah "model_id"
    @Column(name = "model_id", length = 32)
    private String model_id;

    @Column(name = "show_custom_msg", length = 8)
    private String show_custom_msg;

    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "date_time")
    private Date date_time;

    @Column(name = "battery_state", length = 64)
    String battery_state;

    @Column(name = "hidden")
    private short hidden;

    @Column(name = "message_content", columnDefinition = "TEXT")
    String message_content;

    @Column(name = "daily_avg_latitude", columnDefinition = "Decimal(10,5) default '0.00'")
    private double daily_avg_latitude;

    @Column(name = "daily_avg_longitude", columnDefinition = "Decimal(10,5) default '0.00'")
    private double daily_avg_longitude;

}
