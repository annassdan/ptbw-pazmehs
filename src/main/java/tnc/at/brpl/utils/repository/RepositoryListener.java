

package tnc.at.brpl.utils.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Merepresentasikan Bentuk Repository dasar dari System BRPL.
 * Untuk membentuk Repositori dasar ini maka digunakan beberapa repository dasar :
 * 1. {@link PagingAndSortingRepository}
 * 2. {@link JpaSpecificationExecutor} *
 *
 * @param <Entity> Entitas Tabel yang ada di Database
 * @param <ID> Tipe Data dari Primary Key yang ada di Tabel dalam Database
 *
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@NoRepositoryBean
public interface RepositoryListener<Entity, ID extends Serializable> extends PagingAndSortingRepository<Entity, ID>, JpaSpecificationExecutor<Entity> {


    @Override
    @Query("SELECT data FROM #{#entityName} data " +
            "GROUP BY data.uuid ORDER BY data.dibuatPadaTanggal DESC ")
    Page<Entity> findAll(Pageable pageable);

    @Override
    @Query("SELECT data FROM #{#entityName} data " +
            "GROUP BY data.uuid ORDER BY data.dibuatPadaTanggal DESC ")
    Iterable<Entity> findAll();

    Page<Entity> findAllByUuid(Pageable var1, String uuid);

    Page<Entity> findAllByOrderByDibuatPadaTanggalAsc(Pageable var1);

}
