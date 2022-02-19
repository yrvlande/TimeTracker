package com.fis.timetracker.repository;

import com.fis.timetracker.entity.TimeEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeEventRepository extends CrudRepository<TimeEvent, Long> {
}
