package tnc.at.brpl.services.integration;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.integration.Deepslope;
import tnc.at.brpl.repositories.integration.DeepslopeRepository;
import tnc.at.brpl.services.integration.listeners.DeepslopeListener;
import tnc.at.brpl.utils.service.ServiceModel;

@Service
public class DeepslopeService extends ServiceModel<Deepslope, DeepslopeRepository> implements DeepslopeListener {
}
