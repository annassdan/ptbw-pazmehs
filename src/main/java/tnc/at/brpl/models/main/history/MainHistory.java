package tnc.at.brpl.models.main.history;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tnc.at.brpl.utils.data.HistoryActionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class MainHistory implements Serializable {

    private static final long serialVersionUID = 24L;

    @Id
    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
    @GeneratedValue(generator = "brpl_id")
    @Column(name = "uuid", unique = true, nullable = false)
    @ApiModelProperty("ID")
    private String uuid;

    @ApiModelProperty("Action Type")
    private HistoryActionType actionType;

    @CreatedBy
    private String actionTypedBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionTypedAt;

    @Column(name = "affected_to", updatable = false)
    @ApiModelProperty("Affected To")
    private String affectedTo;

    @Column(name = "changes", columnDefinition = "TEXT", updatable = false)
    private String changes;

    @Column(name = "meta", columnDefinition = "TEXT", updatable = false)
    private String meta;


}
