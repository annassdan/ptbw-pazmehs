


package tnc.at.brpl.services.master;


import org.springframework.stereotype.Service;
import tnc.at.brpl.models.master.Resource;
import tnc.at.brpl.repositories.master.ResourceRepository;
import tnc.at.brpl.services.master.listeners.ResourceListener;
import tnc.at.brpl.utils.service.ServiceModel;


/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class ResourceService extends ServiceModel<Resource, ResourceRepository> implements ResourceListener {

}
