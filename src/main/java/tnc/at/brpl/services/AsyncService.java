package tnc.at.brpl.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.mainconfig.TokenIdentification;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class AsyncService implements Brpl {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    TaskExecutor executor;

    /**
     *
     * @param token
     * @param listMap
     */
    @Async
    public void asyncRemoveToken(String token, Map<String, List<?>> listMap) {
        executor.execute(() -> {
            try {
                TimeUnit.MINUTES.sleep(BRPL_TOKEN_UPLOAD_EXPIRE);
                TokenIdentification tokenIdentification = new TokenIdentification(listMap);
                if (tokenIdentification.isTokenExists(token)) {
                    listMap.remove(token);
                    logger.info("Token Key " + token + " has deleted...............");
                } else {
                    logger.info("Token Key already deleted...............");
                }
            } catch (Exception e) { // catch all error or not existing token
            }
        });
    }
}
