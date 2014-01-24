package com.adaofeliz.railstime.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * User: ADAO
 * Date: 5/2/13 - 6:48 AM
 */

@Service
public class FetchDataPT implements Runnable {

    private static Logger LOG = LoggerFactory.getLogger(FetchDataPT.class);
    private static int numberOfErrors = 0;
    private static String lastError = "";

    @Scheduled(fixedDelay = 15000)
    public void run() {

        LOG.info("Starting FetchDataPT Job #" + numberOfErrors);
        if (numberOfErrors > 0) {
            LOG.info("Last error: " + lastError);
        }

    }

}
