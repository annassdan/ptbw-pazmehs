package tnc.at.brpl.services.administrator;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.administrator.PagesMapAccess;
import tnc.at.brpl.repositories.administrator.PagesMapAccessRepository;
import tnc.at.brpl.services.administrator.listeners.PagesMapAccessListener;
import tnc.at.brpl.utils.service.ServiceModel;

@Service
public class PagesMapAccessService extends ServiceModel<PagesMapAccess, PagesMapAccessRepository>
        implements PagesMapAccessListener {

}
