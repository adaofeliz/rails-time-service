package com.adaofeliz.railstime.job.crawler.pt.refer;


import com.adaofeliz.railstime.database.dao.station.StationDao;
import com.adaofeliz.railstime.database.dao.train.TrainDao;
import com.adaofeliz.railstime.database.dao.train.TrainDaoStationTime;
import com.adaofeliz.railstime.job.crawler.pt.refer.domain.StationRefer;
import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.html.form.Form;
import com.gistlabs.mechanize.document.node.Node;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * User: ADAO
 * Date: 4/16/13 - 5:02 PM
 */

@Component
public class ReferWebsiteCrawler {

    private static Logger LOG = LoggerFactory.getLogger(ReferWebsiteCrawler.class);

    // Stations
    private static String stationsIdsUrl = "http://www.refer.pt/MenuPrincipal/Passageiros/EstacoesnaRede.aspx";
    private static String stationDetailsUrl = "http://www.refer.pt/Handlers/StationHandler.ashx?action=detail&stationID=";

    // Trains
    private static String trainIdsUrl = "http://www.refer.pt/MenuPrincipal/Passageiros/PartidaseChegadas.aspx?Type=Arrivals&stationid=";
    private static String trainDetailsUrl = "http://www.refer.pt/MenuPrincipal/Passageiros/EstacoesnaRede/Comboio.aspx?trainid=";

    public List<StationDao> fetchStationsId() {

        List<StationDao> stationDaoList = new ArrayList<>();

        MechanizeAgent agent = new MechanizeAgent();

        Document page = agent.get(stationsIdsUrl);
        String response = page.getRoot().toString();

        String cutBegin = "var stations = [";
        String cutEnd = "];";

        if (response.contains(cutBegin)) {

            String stationsListString = response.substring(response.indexOf(cutBegin) + cutBegin.length());
            stationsListString = stationsListString.substring(0, stationsListString.indexOf(cutEnd));
            stationsListString = stationsListString.replace("\",\"", "\"#\"");
            stationsListString = stationsListString.replace("\"", "");
            String[] stationsArray = stationsListString.split("#");

            for (int i = 0; i < stationsArray.length; i++) {
                StationDao stationDao = new StationDao();

                String[] stationDetails = stationsArray[i].replace('|', '#').split("#");

                stationDao.stationName = stationDetails[0];
                stationDao.stationId = stationDetails[1];

                stationDaoList.add(stationDao);

            }

        }

        return stationDaoList;
    }

    public List<StationDao> fetchStationDetailsList(List<StationDao> stationDaoListIds) {

        List<StationDao> stationDaoListReturn = new ArrayList<>();

        for (StationDao stationDao : stationDaoListIds) {

            StationDao stationDaoNew = fetchStationDetails(stationDao);

            if (stationDaoNew != null) {
                stationDaoListReturn.add(stationDaoNew);
            }

        }

        return stationDaoListReturn;
    }

    public StationDao fetchStationDetails(StationDao stationDao) {

        StationDao stationDaoReturn = null;

        // Extra info
        StationRefer stationRefer = fetchStationDetailsById(stationDao.stationId);
        if (stationRefer != null) {

            stationDao.stationCountry = "PT";
            stationDao.stationAddress = stationRefer.morada;
            stationDao.stationLineChunk = stationRefer.linha;
            stationDao.stationTown = stationRefer.nome;

            // Localization
            // lat=39.44026513 ; long=-8.1943365959999994
            stationDao.stationLatitude = stationRefer.coordenadas.substring(4, stationRefer.coordenadas.indexOf(" ;"));
            stationDao.stationLongitude = stationRefer.coordenadas.substring(stationRefer.coordenadas.indexOf("long=") + 5);

            stationDaoReturn = stationDao;

        } else {
            LOG.error("No extra data for Station: " + stationDao.stationId);
        }

        return stationDaoReturn;
    }

    private StationRefer fetchStationDetailsById(String stationId) {
        StationRefer stationRefer = null;

        try {
            MechanizeAgent agent = new MechanizeAgent();

            Document page = agent.get(stationDetailsUrl + stationId);
            InputStream response = page.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            stationRefer = objectMapper.readValue(response, StationRefer.class);
            LOG.info("Fetching Station: " + stationRefer.nome);

        } catch (Exception e) {
            LOG.error("Error fetching StationDetailsById id: " + stationId);
        }

        return stationRefer;
    }


    public List<TrainDao> fetchTrainsIdsByStationId(String stationId) {

        List<TrainDao> trainsList = new ArrayList<>();

        try {

            MechanizeAgent agent = new MechanizeAgent();
            Document page = agent.get(trainIdsUrl);

            Form form = page.form("Form");

            MultipartEntity multipartEntity = new MultipartEntity();

            multipartEntity.addPart("__VIEWSTATE", new StringBody(form.get("__VIEWSTATE").getValue()));
            multipartEntity.addPart("dnn$ctr569$TrainArrivalsAndDepartures$hdnStationID", new StringBody(stationId));
            multipartEntity.addPart("dnn$ctr569$TrainArrivalsAndDepartures$ddlTipoComboio", new StringBody("Departures"));
            multipartEntity.addPart("dnn$ctr569$TrainArrivalsAndDepartures$btnSubmit", new StringBody("Procurar"));

            DateTime dateTimeNow =
                    DateTime.now().withZone(
                            DateTimeZone.forTimeZone(
                                    TimeZone.getTimeZone("Europe/London")));

            DateTime dateTimeTomorrow = dateTimeNow.plusDays(1);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            multipartEntity.addPart("dnn$ctr569$TrainArrivalsAndDepartures$tbDayInitial", new StringBody(dateFormat.format(dateTimeNow.toDate())));
            multipartEntity.addPart("dnn$ctr569$TrainArrivalsAndDepartures$tbDayFinal", new StringBody(dateFormat.format(dateTimeTomorrow.toDate())));

            HttpPost httpPost = new HttpPost(trainIdsUrl);
            httpPost.setEntity(multipartEntity);

            page = agent.request(httpPost);

            Node trainsTableNode = page.getRoot().find(".mod-table-values table tbody");
            if (trainsTableNode.getValue() != null) {

                List<? extends Node> trainsNodes = trainsTableNode.findAll("tr");

                if (trainsNodes != null) {
                    for (Node train : trainsNodes) {

                        if (train.find(".two-columns .right").getValue() != null) {
                            TrainDao trainDao = new TrainDao();
                            trainDao.trainId = train.find(".two-columns .right").getValue();
                            trainDao.trainType = train.find(".two-columns .left").getValue();
                            trainsList.add(trainDao);
                        }

                    }
                }
            } else {
                LOG.info("No trains for Station Id: " + stationId);
            }
        } catch (Exception e) {
            LOG.error("Error fetchTrainsByStationId id: " + stationId, e);
        }

        return trainsList;

    }

    public List<TrainDao> fetchTrainDetails(List<TrainDao> trainsIdsList) {

        List<TrainDao> trainsListReturn = new ArrayList<>();

        try {
            if (!trainsIdsList.isEmpty()) {

                for (TrainDao trainDao : trainsIdsList) {

                    try {

                        MechanizeAgent agent = new MechanizeAgent();
                        Document page = agent.get(trainDetailsUrl + trainDao.trainId);

                        // Train extra details:
                        Node trainDetails = page.getRoot().find(".TrainDetail_Content div div h5");
                        trainDao.trainName = trainDetails.find("#dnn_ctr787_TrainDetail_tbOperator").getValue();

                        trainDao.trainCountry = "PT";

                        Node trainTableNode = page.getRoot().find(".mod-table-values table tbody");
                        if (trainTableNode.getValue() != null) {

                            List<? extends Node> trainStations = trainTableNode.findAll("tr");

                            if (trainStations != null) {
                                List<TrainDaoStationTime> trainDaoStationTimes = new ArrayList<>();
                                for (Node trainStation : trainStations) {

                                    List<? extends Node> trainDetailsList = trainStation.findAll("td");

                                    TrainDaoStationTime trainDaoStationTime = new TrainDaoStationTime();

                                    String[] trainTimeArray = trainDetailsList.get(0).getValue().split("h");

                                    // Converting to UTC
                                    DateTime dateTime =
                                            DateTime.now().withZone(
                                                    DateTimeZone.forTimeZone(
                                                            TimeZone.getTimeZone("Europe/London")));

                                    dateTime = dateTime.withHourOfDay(Integer.parseInt(trainTimeArray[0]));
                                    dateTime = dateTime.withMinuteOfHour(Integer.parseInt(trainTimeArray[1]));

                                    dateTime = dateTime.withZone(DateTimeZone.UTC);

                                    trainDaoStationTime.trainScheduleHours = dateTime.hourOfDay().get();
                                    trainDaoStationTime.trainScheduleMinutes = dateTime.minuteOfHour().get();

                                    trainDaoStationTime.getTrainScheduleHoursMinutes =
                                            (trainDaoStationTime.trainScheduleHours * 100) + trainDaoStationTime.trainScheduleMinutes;

                                    String stationIdAttribute = trainDetailsList.get(1).find("a").getAttribute("href");
                                    stationIdAttribute = stationIdAttribute.substring(stationIdAttribute.indexOf("=") + 1);
                                    trainDaoStationTime.trainStationId = stationIdAttribute;
                                    trainDaoStationTime.trainStationName = trainDetailsList.get(1).find("a").getValue();

                                    trainDaoStationTime.trainStationPlatform = trainDetailsList.get(2).getValue();

                                    trainDaoStationTimes.add(trainDaoStationTime);

                                }

                                if (!trainDaoStationTimes.isEmpty()) {
                                    trainDao.trainStationOrigin = trainDaoStationTimes.get(0);
                                    trainDao.trainStationDestination = trainDaoStationTimes.get(trainDaoStationTimes.size() - 1);
                                }

                                trainDao.trainBoard = trainDaoStationTimes;

                                trainsListReturn.add(trainDao);
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("Error fetchTrainDetails", e);
                    }
                }
            }

        } catch (Exception e) {
            LOG.error("Error fetchTrainDetails", e);
        }

        return trainsListReturn;

    }

}
