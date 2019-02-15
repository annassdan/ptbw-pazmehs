package tnc.at.brpl.services.main;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.LandingDetail;
import tnc.at.brpl.repositories.main.LandingDetailRepository;
import tnc.at.brpl.services.main.listeners.LandingDetailListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class LandingDetailService extends ServiceModel<LandingDetail, LandingDetailRepository>
    implements LandingDetailListener {



}
