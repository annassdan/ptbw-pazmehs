package tnc.at.brpl.repositories.administrator;
import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.administrator.PagesMapAccess;
import tnc.at.brpl.utils.repository.RepositoryListener;

@Transactional
public interface PagesMapAccessRepository  extends RepositoryListener<PagesMapAccess, String> {

}
