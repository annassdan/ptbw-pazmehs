package tnc.at.brpl.services.main;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.BiologyOnSizeSampleDetail;
import tnc.at.brpl.repositories.main.BiologyOnSizeSampleDetailRepository;
import tnc.at.brpl.services.main.listeners.BiologyOnSizeSampleDetailListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class BiologyOnSizeSampleDetailService
        extends ServiceModel<BiologyOnSizeSampleDetail, BiologyOnSizeSampleDetailRepository>
        implements BiologyOnSizeSampleDetailListener {
}
