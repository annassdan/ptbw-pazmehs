package tnc.at.brpl.services.administrator;


import org.springframework.stereotype.Service;
import tnc.at.brpl.models.administrator.Accesses;
import tnc.at.brpl.repositories.administrator.AccessesRepository;
import tnc.at.brpl.services.administrator.listeners.AccessesListener;
import tnc.at.brpl.utils.service.ServiceModel;

@Service
public class AccessesService extends ServiceModel<Accesses, AccessesRepository> implements AccessesListener {


}
