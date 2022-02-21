package com.fis.timetracker.repository;

import com.fis.timetracker.entity.UserSessionDetail;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UserSessionDetailsRepository extends CassandraRepository<UserSessionDetail, String> {
}
