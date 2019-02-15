package tnc.at.brpl.services.main;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.OperationalCatchDetails;
import tnc.at.brpl.repositories.main.OperationalCatchDetailsRepository;
import tnc.at.brpl.services.main.listeners.OperationalCatchDetailsListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class OperationalCatchDetailsService extends ServiceModel<OperationalCatchDetails, OperationalCatchDetailsRepository>
            implements OperationalCatchDetailsListener {
}
