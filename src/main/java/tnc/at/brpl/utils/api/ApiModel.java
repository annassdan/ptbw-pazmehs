package tnc.at.brpl.utils.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.api.response.ResponseType;
import tnc.at.brpl.utils.api.response.ResponseModel;
import tnc.at.brpl.utils.entity.EntityListener;
import tnc.at.brpl.utils.service.ServiceListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Merepresentasikan Model dari basic Api yang ada di Sistem e-BRPL
 *
 * @param <Entity> Class Entitas yang merepresentasikan sebuah Tabel beserta Strukturnya yang ada di Database
 * @param <Service> Class Service yang merepresentasikan models action ke Tabel yang ada di Database
 *
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */

@SuppressWarnings("unused")
public class ApiModel<Entity extends EntityListener, Service extends ServiceListener>
        extends ApiLogger<Entity>
        implements ApiListener<Entity> {


    @Autowired
    protected Service service;

    /**
     * Merepresentasikan API proses save ke Tabel di Database
     *
     * @param entity Class Entitas yang merepresentasikan sebuah Tabel beserta Strukturnya yang ada di Database
     * @return ResponseEntity
     */
    @Transactional
    @ApiOperation(value = "Fungsi Yang digunakan untuk melakukan proses penyimpanan data " +
                          "ke tabel di database ::save()::")

    @PostMapping
    @Override
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseResolver<?>> save(@RequestBody Entity entity) {
        entity = (Entity) service.save(entity);

        return ResponseResolver.builder()
                .type(ResponseType.WRITE)
                .httpStatus(HttpStatus.OK)
                .body(entity)
                .build().map();
    }


    /**
     * Merepresentasikan API proses edit Data dari tabel di Database
     *
     * @param uuid Primary Key dari Tabel yang ada dalam Database
     * @param entity Class Entitas yang merepresentasikan sebuah Tabel beserta Strukturnya yang ada di Database
     * @return ResponseEntity
     */
    @Transactional
    @ApiOperation(value = "Fungsi Yang digunakan untuk melakukan proses pengubahan data " +
                          "ke tabel di database ::edit()::")

    @PutMapping(BEGAN_BRACER + PRIMARY_KEY + END_BRACER)
    @Override
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseResolver<?>> edit(@PathVariable(PRIMARY_KEY) String uuid,
                                         @RequestBody Entity entity) {

        boolean finded = service.findOne(uuid) != null;
        if(finded) {
            entity.setUuid(uuid);
            entity = (Entity) service.edit(entity);
        }

        return ResponseResolver.builder()
                .type(ResponseType.WRITE)
                .httpStatus(finded ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .status(finded ? HttpStatus.OK.value() : HttpStatus.NOT_EXTENDED.value())
                .message(finded ? RESPONSE.SUCCESS : HttpStatus.NOT_FOUND.getReasonPhrase())
                .body(entity)
                .build().map();
    }


    /**
     * Merepresentasikan API proses pengambilan salah satu data dari tabel di database
     * menggunakan value Primary Key dari Tabel tersebut
     *
     * @param uuid Primary Key dari Tabel yang ada dalam Database
     * @return ResponseEntity
     */
    @Transactional
    @ApiOperation(value = "Fungsi Yang digunakan untuk mengambil satu buah data " +
                          "dari tabel di database ::findOne()::")
    @GetMapping(BEGAN_BRACER + PRIMARY_KEY + END_BRACER)
    @Override
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseResolver<?>> findOne(@PathVariable(PRIMARY_KEY) String uuid) {
        logger.info(makeLog("uuid = " + uuid));
        Entity entity = (Entity) service.findOne(uuid);

        return ResponseResolver.builder()
                .type(ResponseType.READ_ONE)
                .body(entity)
                .build().map();
    }


    /**
     *
     * @param uuid Primary Key dari Tabel yang ada dalam Database
     * @return ResponseEntity
     */
    @Transactional
    @ApiOperation(value = "Fungsi Yang digunakan untuk menghapus salah satu " +
                          "data dari tabel di database ::delete()::")
    @DeleteMapping(BEGAN_BRACER + PRIMARY_KEY + END_BRACER)
    @Override
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseResolver<?>> delete(@PathVariable(PRIMARY_KEY) String uuid) {
        service.delete(uuid);
        return ResponseResolver.builder()
                .type(ResponseType.WRITE)
                .httpStatus(HttpStatus.OK)
                .message(HttpStatus.OK.getReasonPhrase())
                .build().map();
    }

    /**
     * Merepresentasikan API pengambilan seluruh data dari Database yang dibagi
     * dalam beberapa halaman
     *
     * @param page Halaman berapa yang akan ditampilkan pada sebuah webpage
     * @param size Jumlah data yang akan ditampilkan pada sebuah halaman
     * @return ResponseEntity
     */
    @Transactional
    @ApiOperation(value = "Fungsi Yang digunakan untuk mengambil seluruh data dari database " +
                          "oleh sebuah proses yang telah ditentukan ::findAll()::")

    @GetMapping(params = {PAGING.PAGE, PAGING.SIZE} )
    @Override
    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseResolver<?>> findAll(@RequestParam(PAGING.PAGE) int page,
                                       @RequestParam(PAGING.SIZE) int size) {
        return  ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(modelingResult(service.findAll(page, size)))
                .build().map();
    }




    /**
     *
     * @param page page has selected from database
     * @return ResponseModel
     */
    protected ResponseModel<?> modelingResult(Page<?> page){
        UriComponentsBuilder uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQuery(null);

        return ResponseModel.builder()
                .build().generateModel(page, uriComponentsBuilder);
    }

}
