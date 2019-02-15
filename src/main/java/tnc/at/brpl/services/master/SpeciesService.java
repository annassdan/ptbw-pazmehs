


package tnc.at.brpl.services.master;


import org.springframework.stereotype.Service;
import tnc.at.brpl.models.master.Species;
import tnc.at.brpl.repositories.master.SpeciesRepository;
import tnc.at.brpl.services.master.listeners.SpeciesListener;
import tnc.at.brpl.utils.service.ServiceModel;



/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class SpeciesService extends ServiceModel<Species, SpeciesRepository> implements SpeciesListener {

}
