package tnc.at.brpl.repositories.integration;

import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.integration.Boat;
import tnc.at.brpl.utils.repository.RepositoryListener;

@Transactional
public interface BoatRepository extends RepositoryListener<Boat, String> {
}
