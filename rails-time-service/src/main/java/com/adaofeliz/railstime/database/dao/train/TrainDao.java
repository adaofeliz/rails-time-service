package com.adaofeliz.railstime.database.dao.train;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * User: ADAO
 * Date: 5/2/13 - 6:44 AM
 */

@Document
public class TrainDao {

    @Id
    public String trainId;
    public String trainName;
    public String trainType;

    public String trainCountry;

    public TrainDaoStationTime trainStationOrigin;
    public TrainDaoStationTime trainStationDestination;

    public List<TrainDaoStationTime> trainBoard;

}
