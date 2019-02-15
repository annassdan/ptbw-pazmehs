package tnc.at.brpl.services.integration;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.integration.Boat;
import tnc.at.brpl.repositories.integration.BoatRepository;
import tnc.at.brpl.services.integration.listeners.BoatListener;
import tnc.at.brpl.utils.service.ServiceModel;

@Service
public class BoatService extends ServiceModel<Boat, BoatRepository> implements BoatListener {
}
