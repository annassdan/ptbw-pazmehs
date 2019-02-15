


package tnc.at.brpl.repositories.administrator;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.utils.repository.RepositoryListener;


/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unused")
public interface SysUserRepository extends RepositoryListener<SysUser, String> {

    SysUser findByPengguna(String username);

    long countByPengguna(String username);

    Page<SysUser> findByPengguna(String username, Pageable pageable);

    Page<SysUser> findByPenggunaAndKatasandi(String username, String password, Pageable pageable);

    SysUser findByPenggunaAndKatasandi(String username, String password);

    @Query("SELECT data FROM SysUser data WHERE " +
            "data.pengguna != 'superuser' AND data.pengguna != 'brpl' AND  data.pengguna != 'ifish' " +
            "ORDER BY data.dibuatPadaTanggal ASC")
    Page<SysUser> fetchAllDataUser(Pageable var1);


    SysUser findByUuidReferensi(String uuidRef);

}
