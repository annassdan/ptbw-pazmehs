
package tnc.at.brpl.apis.administrator;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.services.administrator.SysUserService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;
import tnc.at.brpl.utils.api.response.ResponseModel;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.api.response.ResponseType;

/**
 * Copyright (c) 2017
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */

@RestController
@RequestMapping(value = Brpl.API +
        Brpl.PACKAGE_ADMINISTRATOR +
        Brpl.URL_DISPATCHER + Brpl.CONTENT.SYSUSER  + Brpl.URL_DISPATCHER,
                produces = { Brpl.MODE.JSON })
@SuppressWarnings("unused")
public class SysUserApi extends ApiModel<SysUser, SysUserService> {

    /**
     * Fungsi yang digunakan untuk login pada System BRPL
     *
     * @param username Username pengguna
     * @param password Password pengguna
     * @return ResponseResolver
     */
    @GetMapping(value = "/auth", params = {"username", "password"})
    public ResponseEntity<ResponseResolver<?>> findByPenggunaAndKatasandi(@RequestParam("username") String username,
                                                                      @RequestParam("password") String password){

        Pageable pageable= new PageRequest(0, 1000);
        ResponseModel<?> result = ResponseModel.builder()
                .build().generateModel(service.findByPenggunaAndKatasandi(username, password, pageable));
        return  ResponseResolver.builder()
                .type(ResponseType.READ_ONE)
                .body(result).error("")
                .build().map();
    }


    /**
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/count", params = {"username"})
    public ResponseEntity<ResponseResolver<?>> countByPengguna(@RequestParam("username") String username) {

        return  ResponseResolver.builder()
                .type(ResponseType.READ)
                .body(service.countByPengguna(username)).error("")
                .build().map();
    }

}
