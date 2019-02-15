


package tnc.at.brpl.services.administrator.listeners;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.utils.service.ServiceListener;

/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unused")
public interface SysUserListener extends ServiceListener<SysUser> {

    Page<SysUser> findByPenggunaAndKatasandi(String username, String password, Pageable pageable);

    SysUser findByPenggunaAndKatasandi(String username, String password);

    SysUser findOneByPengguna(String username);

    long countByPengguna(String username);

}
