package tnc.at.brpl.models.administrator;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = Brpl.UNIQUE  + Brpl.ADMIN + Brpl.CONTENT.PAGES_MAP_ACCESS)
public class PagesMapAccess extends EntityModel<PagesMapAccess, String> {

    @ApiModelProperty("Dapat membatalkan approval data")
    @Column(name = "cancel_" + XMARK)
    @ColumnDefault("'false'")
    private boolean cancel;

    @ApiModelProperty("dapat melakukan approval data")
    @Column(name = "approve_" + XMARK)
    @ColumnDefault("'false'")
    private boolean approve;

    @ApiModelProperty("Dapat melihat")
    @Column(name = "view_" + XMARK)
    @ColumnDefault("'true'")
    private boolean view;

    @ApiModelProperty("Dapat membuat")
    @Column(name = "create_" + XMARK)
    @ColumnDefault("'false'")
    private boolean create;

    @ApiModelProperty("Dapat mengubah")
    @Column(name = "update_" + XMARK)
    @ColumnDefault("'false'")
    private boolean update;

    @ApiModelProperty("Dapat menghapus")
    @Column(name = "delete_" + XMARK)
    @ColumnDefault("'false'")
    private boolean delete;

    @ApiModelProperty("Dapat mendownload")
    @Column(name = "download_" + XMARK)
    @ColumnDefault("'false'")
    private boolean download;

}
