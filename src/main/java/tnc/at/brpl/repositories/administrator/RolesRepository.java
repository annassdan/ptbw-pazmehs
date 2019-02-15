package tnc.at.brpl.repositories.administrator;


import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.administrator.Roles;
import tnc.at.brpl.utils.repository.RepositoryListener;

@Transactional
public interface RolesRepository extends RepositoryListener<Roles, String> {
}
