


package tnc.at.brpl.services.administrator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.services.administrator.listeners.SysUserListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class SysUserService extends ServiceModel<SysUser, SysUserRepository> implements SysUserListener {


    @Override
    public Page<SysUser> findAll(int page, int size) {
        page = (page > 0) ? page - 1 : page;
        Pageable paging = new PageRequest(page, size);
        return repository.fetchAllDataUser(paging);
    }

    /**
     * Fungsi yang digunakan untuk malakukan otentikasi terhadap user
     * yang melakukan login pada system e-brpl
     *
     * @param username Username untuk login
     * @param password Password untuk Login
     * @param pageable Pageable data
     * @return Pageable
     */
    @Override
    public Page<SysUser> findByPenggunaAndKatasandi(String username, String password, Pageable pageable) {
//        Pageable pageable= new PageRequest(0, 1000);
        return repository.findByPenggunaAndKatasandi(username, password, pageable);
    }


    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public SysUser findByPenggunaAndKatasandi(String username, String password) {
        return repository.findByPenggunaAndKatasandi(username, password);
    }

    /**
     * Mengambil satu buah data pengguna berdasarkan usename
     *
     * @param username
     * @return
     */
    @Override
    public SysUser findOneByPengguna(String username) {
        return repository.findByPengguna(username);
    }


    /**
     * Menghitung jumlah data berdasarkan username
     *
     * @param username
     * @return
     */
    @Override
    public long countByPengguna(String username) {
        return repository.countByPengguna(username);
    }


}
