package tnc.at.brpl.services.main;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.BiologyOnReproductionDetail;
import tnc.at.brpl.repositories.main.BiologyOnReproductionDetailRepository;
import tnc.at.brpl.services.main.listeners.BiologyOnReproductionDetailListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class BiologyOnReproductionDetailService
    extends ServiceModel<BiologyOnReproductionDetail, BiologyOnReproductionDetailRepository>
    implements BiologyOnReproductionDetailListener {
}
