


package tnc.at.brpl.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.entity.EntityListener;
import tnc.at.brpl.utils.repository.RepositoryListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Merepresentasikan fungsi-fungsi standard yang digunakan untuk proses Service dari sebuah Tabel yang ada di Database
 *
 * @param <Entity> Class Entitas yang merepresentasikan sebuah Tabel beserta Strukturnya yang ada di Database
 * @param <Repository> Interface {@link RepositoryListener} yang akan digunakan, dimana didalamnya menyajikan
 *                     fungsi-fungsi Standard yang di Sediakan oleh JPA untuk proses ke Database
 *
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unchecked")
public class ServiceModel<Entity extends EntityListener, Repository extends RepositoryListener> implements ServiceListener<Entity> {

    @Autowired
    protected Repository repository;


    /**
     * Merepresentasikan fungsi utama yang digunakan untuk menyimpan data ke Database
     *
     * @param entity Struktur dan Data yang akan diproses ke Tabel yang akan dituju
     * @return Entity
     */
    @Override
    public Entity save(Entity entity) {

        /** Pemberian tanggal waktu dibuatnya data tersebut dan pengubahannya **/
        Date date = new Date();
        entity.setDibuatPadaTanggal(date);
        entity.setTerakhirDiubahPadaTanggal(entity.getDibuatPadaTanggal());

        entity = (Entity) repository.save(entity);
        return entity;
    }

    /**
     * Merepresentasikan fungsi utama yang digunakan untuk menyimpan beberapa data sekaligus ke Database
     *
     * @param entities Struktur dan Data yang akan diproses ke Tabel yang akan dituju
     * @return Entity
     */
    @Override
    public List<Entity> saves(List<Entity> entities) {

        /** Pemberian tanggal waktu dibuatnya data tersebut dan pengubahannya **/
//        Date date = new Date();
        entities.forEach( entity -> {
            entity.setDibuatPadaTanggal(new Date());
            entity.setTerakhirDiubahPadaTanggal(entity.getDibuatPadaTanggal());
        });

        entities = (List<Entity>) repository.save(entities);

        return entities;
    }


    /**
     * Merepresentasikan fungsi utama pengubahan data dari sebuah Tabel yang ada di Database
     *
     * @param entity Struktur dan Data yang akan diproses ke Tabel yang akan dituju
     * @return Entity
     */
    @Override
    public Entity edit(Entity entity) {

        Date date = new Date();
        entity.setTerakhirDiubahPadaTanggal(date);

//        logger.info("EDIT = " + String.valueOf(entity.getDibuatPadaTanggal()));

        entity = (Entity) repository.save(entity);
        return entity;
    }


    /**
     * Merepresentasikan service pengambilan sebuah data dari tabel yang ada di database
     * dengan menggunakan Primary Key sebagai Parameter
     *
     * @param uuid Primary Key dari Tabel yang ada dalam Database
     * @return Entity
     */
    @Override
    public Entity findOne(String uuid) {
        return (Entity) repository.findOne(uuid);
    }


    /**
     * Merepresentasikan fungsi penghapusan data dari tabel di Database
     * berdasarkan UUID (Primary Key)
     *
     * @param uuid Primary Key dari Tabel yang ada dalam Database
     */
    @Override
    public void delete(String uuid) {
        repository.delete(uuid);
    }

    /**
     * Merepresentasikan pengambilan seluruh data dari sebuah Tabel yang ada di database dengan
     * membagi data dalam beberapa halaman
     *
     * @param page Halaman berapa yang akan ditampilkan pada sebuah webpage
     * @param size Jumlah data yang akan ditampilkan pada sebuah halaman
     * @return Page
     */
    @Override
    public Page<Entity> findAll(int page, int size) {
        page = (page > 0) ? page - 1 : page;
        Pageable paging = new PageRequest(page, size);
        return repository.findAllByOrderByDibuatPadaTanggalAsc(paging);
//        return repository.findAll(paging);
    }

    @Override
    public Page<Entity> findAllByUuid(int page, int size, String uuid) {
        page = (page > 0) ? page - 1 : page;
        Pageable paging = new PageRequest(page, size);
        return repository.findAllByUuid(paging, uuid);
    }





    /**
     * Membuat Logger Model
     *
     * @param clazz nama class yang terproses pada log tersebut
     * @param message Pesan yang akan ditampilkan pada log aplikasi
     * @return String
     */
    private String makeLog(Class<?> clazz, String message){
        return  " SERVICE Process in CLASS ### " +
                  clazz.getName() +
                " with message [' " +
                  message +
                " ']";
    }


}
