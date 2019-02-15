package tnc.at.brpl.services.administrator;

import org.springframework.stereotype.Service;
import tnc.at.brpl.models.administrator.PagesMapRoles;
import tnc.at.brpl.repositories.administrator.PagesMapRolesRepository;
import tnc.at.brpl.services.administrator.listeners.PagesMapRolesListener;
import tnc.at.brpl.utils.service.ServiceModel;

@Service
public class PagesMapRolesService extends ServiceModel<PagesMapRoles, PagesMapRolesRepository>
        implements PagesMapRolesListener {

}
