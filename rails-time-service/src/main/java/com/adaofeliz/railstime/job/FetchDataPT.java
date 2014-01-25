package com.adaofeliz.railstime.job;

import com.adaofeliz.railstime.database.crud.DatabaseClient;
import com.adaofeliz.railstime.database.dao.station.StationDao;
import com.adaofeliz.railstime.database.dao.train.TrainDao;
import com.adaofeliz.railstime.job.crawler.pt.refer.ReferWebsiteCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * User: ADAO
 * Date: 5/2/13 - 6:48 AM
 */

@Service
public class FetchDataPT implements Runnable {

    private static Logger LOG = LoggerFactory.getLogger(FetchDataPT.class);
    private static int numberOfErrors = 0;
    private static String lastError = "";

    @Resource
    private DatabaseClient databaseClient;
    @Resource
    private ReferWebsiteCrawler referWebsiteCrawler;

    @Scheduled(fixedDelay = 1500000)
    public void run() {

        LOG.info("Starting FetchDataPT Job #" + numberOfErrors);
        if (numberOfErrors > 0) {
            LOG.info("Last error: " + lastError);
        }

        try {

            updateStations();

            updateTrains();

        } catch (Exception e) {

            lastError = e.getLocalizedMessage();
            numberOfErrors++;

            LOG.error(e.getMessage(), e);

        }
    }

    private void updateTrains() {
        // TRAINS

        List<StationDao> stationDaoDataBase = databaseClient.getStationCollection();
        Collections.sort(stationDaoDataBase);

        for (StationDao stationDao : stationDaoDataBase) {
            try {

                List<TrainDao> trainDaoIdsList = referWebsiteCrawler.fetchTrainsIdsByStationId(stationDao.stationId);

                Iterator<TrainDao> trainDaoIterator = trainDaoIdsList.iterator();
                while (trainDaoIterator.hasNext()) {
                    TrainDao trainDao = trainDaoIterator.next();
                    if (databaseClient.getTrainById(trainDao.trainId) != null) {
                        trainDaoIterator.remove();
                        LOG.debug("Train already exists in the database: " + trainDao.trainId);
                    }
                }

                List<TrainDao> trainDaoList = referWebsiteCrawler.fetchTrainDetails(trainDaoIdsList);
                for (TrainDao trainDao : trainDaoList) {
                    databaseClient.insertTrain(trainDao);
                    LOG.debug("New Train : " + trainDao.trainId);
                }

            } catch (Exception e) {
                lastError = e.getLocalizedMessage();
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void updateStations() {
        // STATIONS!
        try {

            List<StationDao> stationDaoIds = referWebsiteCrawler.fetchStationsId();

            Iterator<StationDao> stationDaoIdsIterator = stationDaoIds.iterator();
            while (stationDaoIdsIterator.hasNext()) {

                StationDao stationDao = stationDaoIdsIterator.next();

                if (databaseClient.getStationById(stationDao.stationId) != null) {
                    stationDaoIdsIterator.remove();
                    LOG.debug("Station already exists in the database: " + stationDao.stationId);
                }
            }

            for (StationDao stationDao : stationDaoIds) {
                StationDao stationDaoNew = referWebsiteCrawler.fetchStationDetails(stationDao);
                if (stationDaoNew != null) {
                    databaseClient.insertStation(stationDaoNew);
                    LOG.debug("New Station: " + stationDaoNew.stationName);
                }
            }

        } catch (Exception e) {
            lastError = e.getLocalizedMessage();
            LOG.error(e.getMessage(), e);
        }
    }

}
