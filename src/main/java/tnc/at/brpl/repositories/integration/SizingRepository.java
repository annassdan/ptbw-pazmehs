package tnc.at.brpl.repositories.integration;

import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.integration.Sizing;
import tnc.at.brpl.utils.repository.RepositoryListener;

@Transactional
public interface SizingRepository extends RepositoryListener<Sizing, String> {
}
