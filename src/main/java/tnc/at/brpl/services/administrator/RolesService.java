package tnc.at.brpl.services.administrator;


import org.springframework.stereotype.Service;
import tnc.at.brpl.models.administrator.Roles;
import tnc.at.brpl.repositories.administrator.RolesRepository;
import tnc.at.brpl.services.administrator.listeners.RolesListener;
import tnc.at.brpl.utils.service.ServiceModel;

@Service
public class RolesService extends ServiceModel<Roles, RolesRepository> implements RolesListener {
}
