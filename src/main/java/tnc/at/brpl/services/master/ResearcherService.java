


package tnc.at.brpl.services.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.master.Researcher;
import tnc.at.brpl.models.master.Resource;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.master.ResearcherRepository;
import tnc.at.brpl.repositories.master.ResourceRepository;
import tnc.at.brpl.services.master.listeners.ResearcherListener;
import tnc.at.brpl.utils.service.ServiceModel;



/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class ResearcherService extends ServiceModel<Researcher, ResearcherRepository> implements ResearcherListener {


    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Override
    public Researcher edit(Researcher researcher) {

        SysUser sysUser = sysUserRepository.findByUuidReferensi(researcher.getUuid());

        if (researcher.getJabatan().equals("Penanggung Jawab WPP")) {
            sysUser.setWpp(researcher.getWpp());
            sysUserRepository.save(sysUser);
        } else if (researcher.getJabatan().equals("Ka. Kelti") || researcher.getJabatan().equals("Peneliti")) {
            Resource resource = resourceRepository.findByNamaSumberDaya(researcher.getUuidSumberDaya());
            sysUser.setSumberDaya(resource.getUuid());
            sysUserRepository.save(sysUser);
        }

        return super.edit(researcher);
    }
}
