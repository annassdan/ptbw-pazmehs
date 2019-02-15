package tnc.at.brpl.apis.administrator;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.models.administrator.PagesMap;
import tnc.at.brpl.models.administrator.PagesMapRoles;
import tnc.at.brpl.services.administrator.PagesMapRolesService;
import tnc.at.brpl.services.administrator.PagesMapService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@RestController
@Transactional
@RequestMapping(value = Brpl.API +
        Brpl.PACKAGE_ADMINISTRATOR +
        Brpl.URL_DISPATCHER + Brpl.CONTENT.PAGES_MAP_ROLES + Brpl.URL_DISPATCHER,
        produces = { Brpl.MODE.JSON })
@SuppressWarnings("unused")
public class PagesMapRolesApi extends ApiModel<PagesMapRoles, PagesMapRolesService> {

}
