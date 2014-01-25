package com.adaofeliz.railstime.database.dao.station;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User: ADAO
 * Date: 5/2/13 - 6:45 AM
 */

@Document
public class StationDao implements Comparable<StationDao> {

    @Id
    public String stationId;
    public String stationName;

    public String stationAddress;
    public String stationTown;
    public String stationCountry;

    public String stationLongitude;
    public String stationLatitude;

    public String stationLineChunk;

    @Override
    public int compareTo(StationDao stationDao) {
        return Integer.parseInt(stationId) - Integer.parseInt(stationDao.stationId);
    }
}
