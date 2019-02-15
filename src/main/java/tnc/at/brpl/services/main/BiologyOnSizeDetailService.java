package tnc.at.brpl.services.main;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.BiologyOnSizeDetail;
import tnc.at.brpl.repositories.main.BiologyOnSizeDetailRepository;
import tnc.at.brpl.services.main.listeners.BiologyOnSizeDetailListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class BiologyOnSizeDetailService
    extends ServiceModel<BiologyOnSizeDetail, BiologyOnSizeDetailRepository>
    implements BiologyOnSizeDetailListener {
}
