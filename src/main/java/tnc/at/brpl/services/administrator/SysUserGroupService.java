package tnc.at.brpl.services.administrator;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.administrator.SysUserGroup;
import tnc.at.brpl.repositories.administrator.SysUserGroupRepository;
import tnc.at.brpl.services.administrator.listeners.SysUserGroupListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class SysUserGroupService  extends ServiceModel<SysUserGroup, SysUserGroupRepository> implements SysUserGroupListener {
}
