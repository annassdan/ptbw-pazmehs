

package tnc.at.brpl.services.master;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.master.FishingArea;
import tnc.at.brpl.repositories.master.FishingAreaRepository;
import tnc.at.brpl.services.master.listeners.FishingAreaaListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class FishingAreaService extends ServiceModel<FishingArea, FishingAreaRepository> implements FishingAreaaListener {
}
