package tnc.at.brpl.services.integration;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.integration.Sizing;
import tnc.at.brpl.repositories.integration.SizingRepository;
import tnc.at.brpl.services.integration.listeners.SizingListener;
import tnc.at.brpl.utils.service.ServiceModel;

@Service
public class SizingService extends ServiceModel<Sizing, SizingRepository> implements SizingListener {
}
