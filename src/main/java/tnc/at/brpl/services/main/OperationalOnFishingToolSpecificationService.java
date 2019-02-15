package tnc.at.brpl.services.main;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.OperationalOnFishingToolSpecification;
import tnc.at.brpl.repositories.main.OperationalOnFishingToolSpecificationRepository;
import tnc.at.brpl.services.main.listeners.OperationalOnFishingToolSpecificationListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class OperationalOnFishingToolSpecificationService extends ServiceModel<OperationalOnFishingToolSpecification, OperationalOnFishingToolSpecificationRepository>
            implements OperationalOnFishingToolSpecificationListener {
}
