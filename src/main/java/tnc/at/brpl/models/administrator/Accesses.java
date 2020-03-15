package tnc.at.brpl.models.administrator;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@Table(name = Brpl.UNIQUE  + Brpl.ADMIN + Brpl.CONTENT.ACCESSES)
public class Accesses extends EntityModel<Accesses, String> {


    @ApiModelProperty("Page yang boleh diakses pengguna")
    @Column(name = "uuid_peta_halaman" + XMARK)
    private String uuidPetaHalaman;

    @ApiModelProperty("Dapat membatalkan approval data")
    @Column(name = "cancel_" + XMARK)
    @ColumnDefault("'f'")
    private boolean cancel;

    @ApiModelProperty("dapat melakukan approval data")
    @Column(name = "approve_" + XMARK)
    @ColumnDefault("'f'")
    private boolean approve;

    @ApiModelProperty("Dapat melihat")
    @Column(name = "view_" + XMARK)
    @ColumnDefault("'t'")
    private boolean view;

    @ApiModelProperty("Dapat membuat")
    @Column(name = "create_" + XMARK)
    @ColumnDefault("'f'")
    private boolean create;

    @ApiModelProperty("Dapat mengubah")
    @Column(name = "update_" + XMARK)
    @ColumnDefault("'f'")
    private boolean update;

    @ApiModelProperty("Dapat menghapus")
    @Column(name = "delete_" + XMARK)
    @ColumnDefault("'f'")
    private boolean delete;


    @ApiModelProperty("Dapat mendownload")
    @Column(name = "download_" + XMARK)
    @ColumnDefault("'f'")
    private boolean download;
}
