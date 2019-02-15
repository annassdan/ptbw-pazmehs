package tnc.at.brpl.repositories.administrator;
import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.administrator.PagesMapRoles;
import tnc.at.brpl.utils.repository.RepositoryListener;

@Transactional
public interface PagesMapRolesRepository extends RepositoryListener<PagesMapRoles, String> {

}
