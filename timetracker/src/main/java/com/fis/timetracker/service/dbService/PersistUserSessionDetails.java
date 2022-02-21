package com.fis.timetracker.service.dbService;

import com.fis.timetracker.entity.UserSessionDetail;
import com.fis.timetracker.repository.UserSessionDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersistUserSessionDetails {
    private final UserSessionDetailsRepository userSessionDetailsRepository;

    public PersistUserSessionDetails(UserSessionDetailsRepository userSessionDetailsRepository) {
    this.userSessionDetailsRepository = userSessionDetailsRepository;
    }

    @Transactional
    public UserSessionDetail persistUserSessionDetails(UserSessionDetail userSessionDetail){
          return userSessionDetailsRepository.save(userSessionDetail);
    }
}
