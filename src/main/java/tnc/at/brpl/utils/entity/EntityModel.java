package tnc.at.brpl.utils.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import tnc.at.brpl.configurations.CustomDateTimeSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Merepresentasikan Bentuk Entity Class yang akan diolah dalam System e-BRPl
 *
 * @param <ClassEntity> Entitas Tabel yang ada di Database
 * @param <ID>          Tipe Data dari Primary Key yang ada di Tabel dalam Database
 *                      <p>
 *                      Copyright (c) 2017.
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
//@Builder(builderMethodName = "superBuilder")
@MappedSuperclass
public abstract class EntityModel<ClassEntity extends EntityListener, ID extends Serializable>
        implements Serializable, EntityListener<ID> {

    private static final long serialVersionUID = 12L;

    /**
     * PRIMARY KEY
     */
    @Id
    @GenericGenerator(name = "brpl_id", strategy = "tnc.at.brpl.configurations.BrplIdGenerator")
    @GeneratedValue(generator = "brpl_id")
    @ApiModelProperty("Kode Kunci")
    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;



    @ApiModelProperty("Tanggal Dibuatnya Data")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @Column(name = "dibuat_pada_tanggal")
    private Date dibuatPadaTanggal;


    @ApiModelProperty("Tanggal Terakhir Diubah")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @Column(name = "terakhir_diubah_pada_tanggal")
    private Date terakhirDiubahPadaTanggal;


    @ApiModelProperty("Dibuat/ terakhir diubah oleh siapa?")
    @Column(name = "terakhir_diubah_oleh")
    @ColumnDefault("''")
    private String dibuatAtauTerakhirDiubahOleh;


    /**
     * Class Entitas
     */
    @JsonIgnore
    @Transient
    public ClassEntity entity;


    @Override
    public String getUuid() {
        return uuid;
    }

    /**
     * Mengeset Manual nilai dari Primary Key
     *
     * @param uuid Primary Key
     */
    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Date getDibuatPadaTanggal() {
        return this.dibuatPadaTanggal;
    }

    @Override
    public void setDibuatPadaTanggal(Date date) {
        this.dibuatPadaTanggal = date;
    }

    @Override
    public Date getTerakhirDiubahPadaTanggal() {
        return this.terakhirDiubahPadaTanggal;
    }

    @Override
    public void setTerakhirDiubahPadaTanggal(Date date) {
        this.terakhirDiubahPadaTanggal = date;
    }

    @Override
    public String getDibuatAtauTerakhirDiubahOleh() {
        return this.dibuatAtauTerakhirDiubahOleh;
    }

    @Override
    public void setDibuatAtauTerakhirDiubahOleh(String oleh) {
        this.dibuatAtauTerakhirDiubahOleh = oleh;
    }


}
