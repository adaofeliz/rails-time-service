package com.adaofeliz.railstime.database.crud;

import com.adaofeliz.railstime.database.dao.station.StationDao;
import com.adaofeliz.railstime.database.dao.train.TrainDao;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: ADAO
 * Date: 5/18/13 - 9:00 AM
 */

@Repository
public class DatabaseClient {

    private static Logger LOG = LoggerFactory.getLogger(DatabaseClient.class);

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoTemplate;


    public static Map<String, StationDao> stationsMap = new HashMap();

    public void insertTrain(TrainDao trainDao) {
        createTrainCollection();
        mongoTemplate.insert(trainDao);
    }

    public void createTrainCollection() {
        if (!mongoTemplate.collectionExists(TrainDao.class)) {
            mongoTemplate.createCollection(TrainDao.class);
        }
    }

    public TrainDao getTrainById(String trainId) {

        TrainDao trainDaoReturn = null;

        if (mongoTemplate.collectionExists(StationDao.class)) {

            if (mongoTemplate.collectionExists(StationDao.class)) {
                DBCollection stationDaoCollection = mongoTemplate.getCollection("trainDao");
                BasicDBObject query = new BasicDBObject();
                query.put("_id", trainId);
                DBObject dbObject = stationDaoCollection.findOne(query);
                if (dbObject != null) {
                    trainDaoReturn = mongoTemplate.getConverter().read(TrainDao.class, dbObject);
                }
            }
        }

        return trainDaoReturn;
    }

    public List<TrainDao> getTrainListByTime(Integer hour, Integer minute) {

        List<TrainDao> trainDaoList = new ArrayList<>();

        if (mongoTemplate.collectionExists(StationDao.class)) {

            if (mongoTemplate.collectionExists(StationDao.class)) {

                DBCollection stationDaoCollection = mongoTemplate.getCollection("trainDao");


                /*
                { $and : [
                    { or : [
                        { $and: [
                            { "trainStationOrigin.trainScheduleHours" : { $gte : 8} },
                            { "trainStationOrigin.trainScheduleMinutes" : { $gte : 59} }
                        ] },
                        { $and: [
                            { "trainStationDestination.trainScheduleHours" : { $gte : 8} },
                            { "trainStationDestination.trainScheduleMinutes" : { $gte : 59} }
                        ] }
                    ] },
                    { or : [
                        { "trainStationOrigin.trainScheduleHours" : 8 },
                        { $and: [
                            { "trainStationDestination.trainScheduleHours" : { $gte : 8} },
                            { "trainStationDestination.trainScheduleMinutes" : { $gte : 59} }
                        ] }
                    ] }
                }]
                 */

                BasicDBObject queryAnd11 = new BasicDBObject("trainStationOrigin.trainScheduleHours", hour);
                BasicDBObject queryAnd12 = new BasicDBObject();
                queryAnd12.put("trainStationOrigin.trainScheduleMinutes", new BasicDBObject("$lte", minute));
                BasicDBObject queryAnd1 = new BasicDBObject("$and", Lists.newArrayList(queryAnd11, queryAnd12));

                BasicDBObject queryEqualHour1 = new BasicDBObject();
                queryEqualHour1.put("trainStationOrigin.trainScheduleHours", new BasicDBObject("$lt", hour));

                BasicDBObject queryOrAnd1 = new BasicDBObject("$or", Lists.newArrayList(queryEqualHour1, queryAnd1));

                BasicDBObject queryAnd21 = new BasicDBObject("trainStationDestination.trainScheduleHours", hour);
                BasicDBObject queryAnd22 = new BasicDBObject();
                queryAnd22.put("trainStationDestination.trainScheduleMinutes", new BasicDBObject("$gte", minute));
                BasicDBObject queryAnd2 = new BasicDBObject("$and", Lists.newArrayList(queryAnd21, queryAnd22));

                BasicDBObject queryEqualHour2 = new BasicDBObject();
                queryEqualHour2.put("trainStationDestination.trainScheduleHours", new BasicDBObject("$gt", hour));

                BasicDBObject queryOrAnd2 = new BasicDBObject("$or", Lists.newArrayList(queryEqualHour2, queryAnd2));

                BasicDBObject query = new BasicDBObject("$and", Lists.newArrayList(queryOrAnd1, queryOrAnd2));

                DBCursor dbCursor = stationDaoCollection.find(query).sort(new BasicDBObject("trainStationOrigin.trainScheduleHours", 1));

                while (dbCursor.hasNext()) {
                    DBObject dbObject = dbCursor.next();
                    if (dbObject != null) {
                        trainDaoList.add(mongoTemplate.getConverter().read(TrainDao.class, dbObject));
                    }
                }

            }
        }

        return trainDaoList;
    }

    public List<TrainDao> getTrainCollection() {
        List<TrainDao> trainDaoList = Lists.newArrayList();
        if (mongoTemplate.collectionExists(TrainDao.class)) {
            trainDaoList = mongoTemplate.findAll(TrainDao.class);
        }
        return trainDaoList;
    }

    public void insertStation(StationDao stationDao) {
        createStationCollection();
        mongoTemplate.insert(stationDao);
    }

    public void createStationCollection() {
        if (!mongoTemplate.collectionExists(StationDao.class)) {
            mongoTemplate.createCollection(StationDao.class);
        }
    }

    public StationDao getStationById(String stationDaoId) {

        StationDao stationDaoReturn = null;

        if (stationsMap.size() < 1) {
            List<StationDao> stationDaoList = getStationCollection();
            for (StationDao stationDao : stationDaoList) {
                stationsMap.put(stationDao.stationId, stationDao);
            }
        }

        stationDaoReturn = stationsMap.get(stationDaoId);

        return stationDaoReturn;
    }

    public List<StationDao> getStationCollection() {
        List<StationDao> stationDaoList = Lists.newArrayList();
        if (mongoTemplate.collectionExists(StationDao.class)) {
            stationDaoList = mongoTemplate.findAll(StationDao.class);
        }
        return stationDaoList;
    }

}