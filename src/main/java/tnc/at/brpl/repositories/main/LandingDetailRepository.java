package tnc.at.brpl.repositories.main;

import org.springframework.transaction.annotation.Transactional;
import tnc.at.brpl.models.main.LandingDetail;
import tnc.at.brpl.utils.repository.RepositoryListener;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Transactional
public interface LandingDetailRepository extends RepositoryListener<LandingDetail, String> {
}
