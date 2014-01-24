package com.adaofeliz.railstime.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: ADAO
 * Date: 3/19/13 | Time: 12:36 PM
 */

@Controller
@RequestMapping("/")
public class BaseController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

}
