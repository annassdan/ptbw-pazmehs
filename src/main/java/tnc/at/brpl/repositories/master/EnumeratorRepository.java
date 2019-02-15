


package tnc.at.brpl.repositories.master;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import tnc.at.brpl.models.master.Enumerator;
import tnc.at.brpl.utils.repository.RepositoryListener;


/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public interface EnumeratorRepository extends RepositoryListener<Enumerator, String>{


    //String  = "";
    @Query("Select a from Enumerator a ")
    Page<Enumerator> findAllWithJoin(Pageable pageable);

}
