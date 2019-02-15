

package tnc.at.brpl.utils.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.entity.EntityListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Merepresentasikan Listener API standard yang digunakan untuk koneksi ke Service data yang akan digunakan
 * untuk melakuakan transaksi ke databaase
 *
 * @param <Entity> Class Entitas yang merepresentasikan sebuah Tabel beserta Strukturnya yang ada di Database
 *
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public interface ApiListener<Entity extends EntityListener> extends Brpl {




    /**
     * Merepresentasikan API proses save ke Tabel di Database
     *
     * @param entity Class Entitas yang merepresentasikan sebuah Tabel beserta Strukturnya yang ada di Database
     * @return ResponseEntity
     */
    @SuppressWarnings("unused")
    ResponseEntity<ResponseResolver<?>> save(@Valid @RequestBody Entity entity);


    /**
     * Merepresentasikan API proses edit Data dari tabel di Database
     *
     * @param uuid Primary Key dari Tabel yang ada dalam Database
     * @param entity Class Entitas yang merepresentasikan sebuah Tabel beserta Strukturnya yang ada di Database
     * @return ResponseEntity
     */
    @SuppressWarnings("unused")
    ResponseEntity<ResponseResolver<?>> edit(@PathVariable(PRIMARY_KEY) String uuid, @Valid @RequestBody Entity entity);


    /**
     * Merepresentasikan API proses pengambilan salah satu data dari tabel di database
     * menggunakan value Primary Key dari Tabel tersebut
     *
     * @param uuid Primary Key dari Tabel yang ada dalam Database
     * @return ResponseEntity
     */
    @SuppressWarnings("unused")
    ResponseEntity<ResponseResolver<?>> findOne(@PathVariable(PRIMARY_KEY) String uuid);


    /**
     * Merepresentasikan API penghabpusan data dari sebuah tabel di database berdasarkan UUID (Primary Key)
     *
     * @param uuid Primary Key dari Tabel yang ada dalam Database
     * @return ResponseEntity
     */
    @SuppressWarnings("unused")
    ResponseEntity<ResponseResolver<?>> delete(@PathVariable(PRIMARY_KEY) String uuid);


    /**
     * Merepresentasikan API pengambilan seluruh data dari Database yang dibagi
     * dalam beberapa halaman
     *
     * @param page Halaman berapa yang akan ditampilkan pada sebuah webpage
     * @param size Jumlah data yang akan ditampilkan pada sebuah halaman
     * @return ResponseEntity
     */
    @SuppressWarnings("unused")

    ResponseEntity<ResponseResolver<?>> findAll(@RequestParam(PAGING.PAGE) final int page, @RequestParam(PAGING.SIZE) final int size);


}
