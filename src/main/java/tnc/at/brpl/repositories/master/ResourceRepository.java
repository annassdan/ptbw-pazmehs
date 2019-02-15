


package tnc.at.brpl.repositories.master;

import tnc.at.brpl.models.master.Resource;
import tnc.at.brpl.utils.repository.RepositoryListener;

/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public interface ResourceRepository extends RepositoryListener<Resource, String> {


    Resource findByNamaSumberDaya(String resourceName);


}
