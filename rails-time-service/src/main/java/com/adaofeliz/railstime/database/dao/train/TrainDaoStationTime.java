package com.adaofeliz.railstime.database.dao.train;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User: ADAO
 * Date: 6/13/13 - 3:56 PM
 */

@Document
public class TrainDaoStationTime {

    public String trainStationId;
    public String trainStationName;
    public String trainStationPlatform;

    public String trainMinutesDelay;

    public Integer getTrainScheduleHoursMinutes;

    public Integer trainScheduleHours;
    public Integer trainScheduleMinutes;

}
