package tnc.at.brpl.services.administrator;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.administrator.PagesMap;
import tnc.at.brpl.repositories.administrator.PagesMapRepository;
import tnc.at.brpl.services.administrator.listeners.PagesMapListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class PagesMapService extends ServiceModel<PagesMap, PagesMapRepository> implements PagesMapListener {

}
