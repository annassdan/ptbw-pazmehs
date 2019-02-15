package tnc.at.brpl.services.master;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.master.FishingTool;
import tnc.at.brpl.repositories.master.FishingToolRepository;
import tnc.at.brpl.services.master.listeners.FishingToolListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class FishingToolService extends ServiceModel<FishingTool, FishingToolRepository> implements FishingToolListener {
}
