package com.fis.timetracker.repository;

import com.fis.timetracker.entity.TimeEvent;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeEventRepository extends CrudRepository<TimeEvent, Long> {

@Query("SELECT MAX(id) FROM timeEvents")
    public Long getMaxId();
}
