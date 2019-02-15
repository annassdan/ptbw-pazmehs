


package tnc.at.brpl.services.master;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tnc.at.brpl.models.master.Enumerator;
import tnc.at.brpl.repositories.master.EnumeratorRepository;
import tnc.at.brpl.services.master.listeners.EnumeratorListener;
import tnc.at.brpl.utils.service.ServiceModel;

/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class EnumeratorService extends ServiceModel<Enumerator, EnumeratorRepository> implements EnumeratorListener {

    /**
     *
     * @return Page
     */
    @Override
    public Page<Enumerator> findAllWithJoin(int page, int size) {
        page = (page > 0) ? page - 1 : page;
        Pageable pageable= new PageRequest(page, size);
        return repository.findAllWithJoin(pageable);
    }

}
