package tnc.at.brpl.services.main;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.LandingCatchDetail;
import tnc.at.brpl.repositories.main.LandingCatchDetailsRepository;
import tnc.at.brpl.services.main.listeners.LandingCatchDetailsListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class LandingCatchDetailsService extends ServiceModel<LandingCatchDetail, LandingCatchDetailsRepository>
    implements LandingCatchDetailsListener {
}
