package tnc.at.brpl.repositories.integration;

import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.integration.Deepslope;
import tnc.at.brpl.utils.repository.RepositoryListener;

@Transactional
public interface DeepslopeRepository extends RepositoryListener<Deepslope, String> {
}
