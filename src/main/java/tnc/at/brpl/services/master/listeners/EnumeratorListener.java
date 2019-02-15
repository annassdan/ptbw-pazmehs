


package tnc.at.brpl.services.master.listeners;

import org.springframework.data.domain.Page;
import tnc.at.brpl.models.master.Enumerator;
import tnc.at.brpl.utils.service.ServiceListener;

/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public interface EnumeratorListener extends ServiceListener<Enumerator> {

    Page<Enumerator> findAllWithJoin(int page, int size);

}
