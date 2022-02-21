package com.fis.timetracker.repository;

import com.fis.timetracker.entity.TimeEvent;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TimeEventRepository extends CassandraRepository<TimeEvent, Long> {

@Query("select max(id) from timeEvents")
    public Long getMaxId();

@Query("select * from timeEvents where userId=:userId and insertedOn>:startInsertedOn and insertedOn<:endInsertedOn ALLOW FILTERING")
   public List<TimeEvent> getClickInfo(
           String userId,
           Timestamp startInsertedOn,
           Timestamp endInsertedOn
);

@Query("select distinct userId from timeEvents")
    public List<TimeEvent> getUserIds();

}
