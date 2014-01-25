package com.adaofeliz.railstime.service.dto.train;

import java.util.List;

/**
 * User: ADAO
 * Date: 5/2/13 - 5:42 AM
 */
public class Train {

    public String trainId;
    public String trainName;
    public String trainType;

    public String trainCountry;

    public TrainPosition trainPosition;

    public TrainStation trainOrigin;
    public TrainStation trainDestination;

    public List<TrainStation> trainBoard;

}
