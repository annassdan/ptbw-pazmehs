package tnc.at.brpl.repositories.administrator;

import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.administrator.PagesMap;
import tnc.at.brpl.utils.repository.RepositoryListener;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Transactional
public interface PagesMapRepository extends RepositoryListener<PagesMap, String> {

}
