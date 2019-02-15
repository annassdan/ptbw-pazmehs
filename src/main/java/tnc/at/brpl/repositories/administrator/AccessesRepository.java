package tnc.at.brpl.repositories.administrator;


import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.administrator.Accesses;
import tnc.at.brpl.utils.repository.RepositoryListener;

@Transactional
public interface AccessesRepository extends RepositoryListener<Accesses, String> {
}
