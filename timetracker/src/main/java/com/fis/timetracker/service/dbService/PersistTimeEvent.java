package com.fis.timetracker.service.dbService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.timetracker.entity.TimeEvent;
import com.fis.timetracker.model.EventData;
import com.fis.timetracker.repository.TimeEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class PersistTimeEvent {

    private TimeEventRepository timeEventRepository;

    public PersistTimeEvent(TimeEventRepository timeEventRepository) {
        this.timeEventRepository = timeEventRepository;
    }

    @Transactional
    public TimeEvent saveTimeEvent(EventData eventData) {
           TimeEvent timeEvent = null;
           Long maxId = timeEventRepository.getMaxId();
           if (maxId == null) {
               timeEvent = timeEventEntityMapper(eventData,0);
           } else {
               timeEvent = timeEventEntityMapper(eventData, maxId + 1);
              ;
           }
           timeEventRepository.save(timeEvent);
            return timeEvent;
    }




    private TimeEvent timeEventEntityMapper(EventData eventData, long id) {
        TimeEvent timeEvent = new TimeEvent();
        timeEvent.setId(id);
        timeEvent.setUserId(eventData.getUserId());
        timeEvent.setxCoordinate(eventData.getxCoordinate());
        timeEvent.setyCoordinate(eventData.getyCoordinate());
        timeEvent.setMouseClickTime(eventData.getMouseClickTime());
        timeEvent.setInsertedOn(new Timestamp(System.currentTimeMillis()));
        return timeEvent;
    }
}
