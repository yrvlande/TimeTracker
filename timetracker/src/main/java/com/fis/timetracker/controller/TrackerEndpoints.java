package com.fis.timetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.timetracker.entity.TimeEvent;
import com.fis.timetracker.model.EventData;
import com.fis.timetracker.service.dbService.PersistTimeEvent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class TrackerEndpoints {
    private final PersistTimeEvent persistTimeEvent;


    public TrackerEndpoints(PersistTimeEvent persistTimeEvent){
        this.persistTimeEvent = persistTimeEvent;
    }

   @RequestMapping(value = "event/data", method = {RequestMethod.POST, RequestMethod.GET})
    public TimeEvent postMouseEvents(@RequestBody EventData eventData) {
       return persistTimeEvent.saveTimeEvent(eventData);
    }

}
