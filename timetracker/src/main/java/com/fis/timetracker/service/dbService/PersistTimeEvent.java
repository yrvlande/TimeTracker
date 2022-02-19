package com.fis.timetracker.service.dbService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.timetracker.entity.TimeEvent;
import com.fis.timetracker.model.EventData;
import com.fis.timetracker.repository.TimeEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersistTimeEvent {

    private TimeEventRepository timeEventRepository;

    public PersistTimeEvent(TimeEventRepository timeEventRepository) {
        this.timeEventRepository = timeEventRepository;
    }

    @Transactional
    public TimeEvent saveTimeEvent(EventData eventData) {
           TimeEvent timeEvent = timeEventEntityMapper(eventData);
           timeEventRepository.save(timeEvent);
            return timeEvent;
    }


    private TimeEvent timeEventEntityMapper(EventData eventData) {
        TimeEvent timeEvent = new TimeEvent();
        timeEvent.setId(eventData.getId());
        timeEvent.setUserId(eventData.getUserId());
        timeEvent.setxCoordinate(eventData.getxCoordinate());
        timeEvent.setyCoordinate(eventData.getyCoordinate());
        timeEvent.setMouseClickTime(eventData.getMouseClickTime());
        timeEvent.setInsertedOn(eventData.getInsertedOn());
        return timeEvent;
    }
}
