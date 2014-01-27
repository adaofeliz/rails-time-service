package com.adaofeliz.railstime.service.delegate;

import com.adaofeliz.railstime.database.crud.DatabaseClient;
import com.adaofeliz.railstime.database.dao.station.StationDao;
import com.adaofeliz.railstime.database.dao.train.TrainDao;
import com.adaofeliz.railstime.database.dao.train.TrainDaoStationTime;
import com.adaofeliz.railstime.service.context.UserContext;
import com.adaofeliz.railstime.service.dto.train.Train;
import com.adaofeliz.railstime.service.dto.train.TrainPosition;
import com.adaofeliz.railstime.service.dto.train.TrainStation;
import com.adaofeliz.railstime.service.dto.train.TrainTime;
import com.adaofeliz.railstime.utils.geocalc.DegreeCoordinate;
import com.adaofeliz.railstime.utils.geocalc.EarthCalc;
import com.adaofeliz.railstime.utils.geocalc.Point;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * User: ADAO
 * Date: 5/2/13 - 5:44 AM
 */

@Component
public class TrainsDelegate {

    private static Logger LOG = LoggerFactory.getLogger(TrainsDelegate.class);

    @Resource
    private UserContext userContext;

    @Resource
    private DatabaseClient databaseClient;

    public List<Train> getAllTrains() {
        DateTime dateTime = DateTime.now().toDateTime(DateTimeZone.UTC);
        dateTime.minusMinutes(30);
        return mappingTrainDaoListToTrainList(databaseClient.getTrainListByTime(
                Integer.valueOf(dateTime.hourOfDay().get()),
                Integer.valueOf(dateTime.minuteOfHour().getAsShortText())));
    }

    public Train getTrainById(String trainId) {
        return mappingTrainDaoToTrain(databaseClient.getTrainById(trainId));
    }

    public Map<String, ArrayList<Train>> getAllTrainsByType() {
        List<Train> trainList = getAllTrains();
        Map<String, ArrayList<Train>> trainByTypeMap = new HashMap<>();

        for (Train train : trainList) {
            if (trainByTypeMap.containsKey(train.trainType)) {
                trainByTypeMap.get(train.trainType).add(train);
            } else {
                trainByTypeMap.put(train.trainType, Lists.newArrayList(train));
            }
        }

        return trainByTypeMap;
    }

    public ArrayList<Train> getAllTrainsByType(String trainType) {
        return getAllTrainsByType().get(trainType);
    }

    public Map<String, ArrayList<Train>> getAllTrainsByOrigin() {

        List<Train> trainList = getAllTrains();
        Map<String, ArrayList<Train>> trainsMap = new HashMap<>();

        for (Train train : trainList) {
            if (trainsMap.containsKey(train.trainOrigin.stationName)) {
                trainsMap.get(train.trainOrigin.stationName).add(train);
            } else {
                trainsMap.put(train.trainOrigin.stationName, Lists.newArrayList(train));
            }
        }

        return trainsMap;
    }

    public ArrayList<Train> getAllTrainsByOrigin(String origin) {
        return getAllTrainsByOrigin().get(origin);
    }

    private List<Train> mappingTrainDaoListToTrainList(List<TrainDao> trainDaoList) {

        List<Train> trainList = new ArrayList<>();

        for (TrainDao trainDao : trainDaoList) {
            Train train = mappingTrainDaoToTrain(trainDao);

            if (train != null) {
                if (train.trainPosition.currentLatitude != null && !train.trainPosition.currentLatitude.isNaN()) {
                    trainList.add(train);
                }
            }

        }

        return trainList;
    }

    private Train mappingTrainDaoToTrain(TrainDao trainDao) {

        Train train = new Train();

        if (trainDao != null) {

            train.trainId = trainDao.trainId;
            train.trainName = trainDao.trainName;
            train.trainType = trainDao.trainType;
            train.trainCountry = trainDao.trainCountry;

            List<TrainStation> trainBoardList = mappingTrainBoardList(trainDao);
            train.trainOrigin = trainBoardList.get(0); // Origin
            train.trainDestination = trainBoardList.get(trainBoardList.size() - 1); // Destination
            train.trainBoard = trainBoardList;

            train.trainPosition = mappingTrainPosition(trainDao);

        }

        return train;
    }

    private List<TrainStation> mappingTrainBoardList(TrainDao trainDao) {
        List<TrainStation> trainBoardList = new ArrayList<>();
        for (TrainDaoStationTime trainDaoStationTime : trainDao.trainBoard) {

            TrainStation trainStation = new TrainStation();

            // Direct Mapping
            trainStation.stationId = trainDaoStationTime.trainStationId;
            trainStation.stationName = trainDaoStationTime.trainStationName;
            trainStation.platform = trainDaoStationTime.trainStationPlatform;

            trainStation.time = mappingTrainTime(trainDaoStationTime);

            // TODO - Implement Delay
            // trainStation.status = "On Time";

            trainBoardList.add(trainStation);
        }
        return trainBoardList;
    }

    private TrainTime mappingTrainTime(TrainDaoStationTime trainDaoStationTime) {

        DateTime trainDateTime = DateTime.now(DateTimeZone.UTC);
        trainDateTime = trainDateTime.withHourOfDay(trainDaoStationTime.trainScheduleHours.intValue());
        trainDateTime = trainDateTime.withMinuteOfHour(trainDaoStationTime.trainScheduleMinutes.intValue());

        DateTime userDateTime = trainDateTime.toDateTime(DateTimeZone.forTimeZone(userContext.getUserTimezone()));

        TrainTime trainTime = new TrainTime();
        trainTime.year = userDateTime.year().getAsString();
        trainTime.month = userDateTime.monthOfYear().getAsString();
        trainTime.day = userDateTime.dayOfMonth().getAsString();
        trainTime.hour = userDateTime.hourOfDay().getAsString();
        trainTime.minute = userDateTime.minuteOfHour().getAsString();

        return trainTime;
    }

    private TrainPosition mappingTrainPosition(TrainDao trainDao) {

        TrainPosition trainPosition = new TrainPosition();

        DateTime nowDateTime = DateTime.now(DateTimeZone.UTC);
        int nowHour = nowDateTime.getHourOfDay();
        int nowMinutes = nowDateTime.getMinuteOfHour();

        // Origin Time
        int originHour = trainDao.trainBoard.get(0).trainScheduleHours;
        int originMinutes = trainDao.trainBoard.get(0).trainScheduleMinutes;
        if (nowHour < originHour || (nowHour == originHour && nowMinutes <= originMinutes)) { // The train still in the Station

            StationDao stationDao = databaseClient.getStationById(trainDao.trainBoard.get(0).trainStationId);

            if (stationDao != null && stationDao.stationLatitude != null && stationDao.stationLongitude != null) {
                trainPosition.currentLatitude = Double.parseDouble(stationDao.stationLatitude);
                trainPosition.currentLongitude = Double.parseDouble(stationDao.stationLongitude);
            } else {
                LOG.error(
                        "Error for station Id: " + trainDao.trainBoard.get(0).trainStationId +
                                " Name: " + trainDao.trainBoard.get(0).trainStationName);
            }
        } else {

            // Finding the stations
            String previousStationId = trainDao.trainBoard.get(0).trainStationId;
            String nextStationId = null;

            TrainDaoStationTime previousTimeStation = null;
            TrainDaoStationTime nextTimeStation = null;

            Iterator<TrainDaoStationTime> stationTimeIterator = trainDao.trainBoard.iterator();
            if (stationTimeIterator.hasNext()) {

                previousTimeStation = stationTimeIterator.next();
                nextTimeStation = previousTimeStation;

                do {

                    int stationHour = nextTimeStation.trainScheduleHours;
                    int stationMinutes = nextTimeStation.trainScheduleMinutes;

                    if (nowHour <= stationHour && nowMinutes <= stationMinutes) {

                        nextStationId = nextTimeStation.trainStationId;

                        if (previousTimeStation != null) {
                            previousStationId = previousTimeStation.trainStationId;
                        }
                    }

                    if (nextStationId == null) {
                        previousTimeStation = nextTimeStation;
                        nextTimeStation = stationTimeIterator.next();
                    }

                } while (nextStationId == null && stationTimeIterator.hasNext());
            }

            StationDao previousStationDao = databaseClient.getStationById(previousStationId);
            StationDao nextStationDao = databaseClient.getStationById(nextStationId);

            if (previousStationDao != null && nextStationDao != null) {

                DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");

                DateTime previousStationTime =
                        formatter.parseDateTime(
                                previousTimeStation.trainScheduleHours + ":" + previousTimeStation.trainScheduleMinutes + ":00");

                DateTime nextStationTime =
                        formatter.parseDateTime(
                                nextTimeStation.trainScheduleHours + ":" + nextTimeStation.trainScheduleMinutes + ":00");

                DateTime nowTime =
                        formatter.parseDateTime(
                                nowDateTime.hourOfDay().get() + ":" + nowDateTime.minuteOfHour().get() + ":" + nowDateTime.secondOfMinute().get());

                Interval intervalTotal = new Interval(previousStationTime, nextStationTime);
                int secondsTotal =
                        (intervalTotal.toPeriod().getMinutes() * 60) + intervalTotal.toPeriod().getSeconds();

                int secondsActual = 0;
                if (nowTime.isBefore(nextStationTime.getMillis())) {
                    Interval intervalActual = new Interval(nowTime, nextStationTime);
                    secondsActual =
                            (intervalActual.toPeriod().getMinutes() * 60) + intervalActual.toPeriod().getSeconds();
                }

                double distancePercentage = (double) secondsActual / (double) secondsTotal;

                Point standPoint = new Point(
                        new DegreeCoordinate(Double.parseDouble(previousStationDao.stationLatitude)),
                        new DegreeCoordinate(Double.parseDouble(previousStationDao.stationLongitude))
                );

                Point forePoint = new Point(
                        new DegreeCoordinate(Double.parseDouble(nextStationDao.stationLatitude)),
                        new DegreeCoordinate(Double.parseDouble(nextStationDao.stationLongitude))
                );

                double bearing = EarthCalc.getBearing(standPoint, forePoint);

                double distanceTotal = EarthCalc.getDistance(standPoint, forePoint);
                double distanceActual = distanceTotal * distancePercentage;

                Point currentLocation = EarthCalc.pointRadialDistance(standPoint, bearing, distanceActual);

                trainPosition.currentLatitude = currentLocation.getLatitude();
                trainPosition.currentLongitude = currentLocation.getLongitude();

            } else {

                LOG.error("Error for station Id: " + previousStationId + " " + (previousStationDao == null)
                        + " and station Id: " + nextStationId + " " + (nextStationDao == null));

            }
        }

        return trainPosition;
    }

}
