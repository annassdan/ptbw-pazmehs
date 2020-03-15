package tnc.at.brpl.models.main.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tnc.at.brpl.utils.Brpl;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = Brpl.CONTENT.HISTORY + Brpl.UNDERSCORE + Brpl.UNIQUE + Brpl.CONTENT.LANDING )
@SuperBuilder
public class LandingHistory extends MainHistory {
}
