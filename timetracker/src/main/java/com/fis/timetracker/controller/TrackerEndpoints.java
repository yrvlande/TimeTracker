package com.fis.timetracker.controller;

import com.fis.timetracker.model.EventData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class TrackerEndpoints {

   @RequestMapping(value = "event/data", method = {RequestMethod.POST, RequestMethod.GET})
    public EventData postMouseEvents(@RequestBody EventData eventData) {
        System.out.println(eventData);
        return eventData;
    }

}
