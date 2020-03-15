package tnc.at.brpl.models.integration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;

//@Entity
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
//@Table(name = Brpl.OTHER + Brpl.CONTENT.OTHER.TRACKER)
public class Tracker extends EntityModel<Tracker, String> implements Brpl {

    // di tabel ifish adalah "tracker_id"
    @Column(name = "tracker_id")
    private String tracker_id;

    @Column(name = "tracker_name", length = 64)
    private String tracker_name;

    // di tabel ifish adalah "feed_id"
    @Column(name = "feed_id", length = 128)
    private String feed_id;

    @Column(name = "status")
    private short status;

    @Column(name = "auth_code", length = 16)
    private String auth_code;

    @Column(name = "notes", length = 64)
    private String notes;


}
