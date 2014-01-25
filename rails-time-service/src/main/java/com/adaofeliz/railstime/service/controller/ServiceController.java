package com.adaofeliz.railstime.service.controller;


import com.adaofeliz.railstime.service.delegate.TrainsDelegate;
import com.adaofeliz.railstime.service.dto.train.Train;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: ADAO
 * Date: 5/2/13 - 5:33 AM
 */

@Controller
@RequestMapping("/service/*")
public class ServiceController {

    @Resource
    private TrainsDelegate trainsDelegate;

    @RequestMapping(value = "/train", method = RequestMethod.GET)
    @ResponseBody
    public List<Train> fetchTrains() {
        return trainsDelegate.getAllTrains();
    }

    @RequestMapping(value = "/train/type", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, ArrayList<Train>> fetchTrainsByType() {
        return trainsDelegate.getAllTrainsByType();
    }

    @RequestMapping(value = "/train/type/{type}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Train> fetchTrainsByType(@PathVariable String type) {
        return trainsDelegate.getAllTrainsByType(type);
    }

    @RequestMapping(value = "/train/origin", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, ArrayList<Train>> fetchTrainsByOrigin() {
        return trainsDelegate.getAllTrainsByOrigin();
    }

    @RequestMapping(value = "/train/origin/{origin}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Train> fetchTrainsByOrigin(@PathVariable String origin) {
        return trainsDelegate.getAllTrainsByOrigin(origin);
    }

    @RequestMapping(value = "/train/{trainId}", method = RequestMethod.GET)
    @ResponseBody
    public Train fetchTrainsById(@PathVariable String trainId) {
        return trainsDelegate.getTrainById(trainId);
    }

    @RequestMapping(value = "/station", method = RequestMethod.GET)
    @ResponseBody
    public String fetchStations() {
        return "Not Implemented.";
    }

    @RequestMapping(value = "/station/{stationId}", method = RequestMethod.GET)
    @ResponseBody
    public String fetchStationsById(@PathVariable String stationId) {
        return "Not Implemented.";
    }
}
