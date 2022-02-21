package com.fis.timetracker.service.schedulerService;

import com.fis.timetracker.entity.TimeEvent;
import com.fis.timetracker.entity.UserSessionDetail;
import com.fis.timetracker.repository.TimeEventRepository;
import com.fis.timetracker.service.dbService.PersistUserSessionDetails;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ReportScheduler {
    private final TimeEventRepository timeEventRepository;
    private  final PersistUserSessionDetails persistUserSessionDetails;

    public ReportScheduler(TimeEventRepository timeEventRepository, PersistUserSessionDetails persistUserSessionDetails) {
        this.timeEventRepository = timeEventRepository;
        this.persistUserSessionDetails = persistUserSessionDetails;
    }

    @Scheduled( cron = "*/20 * * * * *")
    public void generateReport() throws ParseException {
        long totalDayMinutes = 1440;

        LocalDate currentDate = LocalDate.now();
        currentDate = currentDate.minusDays(1);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp startTimeStamp = new Timestamp(formatter.parse( currentDate.toString() +" 00:00:01").getTime() + TimeUnit.HOURS.toMillis(5) + TimeUnit.MINUTES.toMillis(30));
        Timestamp endTimeStamp = new Timestamp(formatter.parse( currentDate.toString() +" 23:59:59").getTime() + TimeUnit.HOURS.toMillis(5) + TimeUnit.MINUTES.toMillis(30));
        List<TimeEvent> userIds = timeEventRepository.getUserIds();

        for (TimeEvent userId : userIds) {
            long effectiveWorkingMinutes = 0;
            List<TimeEvent> timeEvents = timeEventRepository.getClickInfo( userId.getUserId(), startTimeStamp, endTimeStamp );
             TimeEvent firstClickEvent = timeEvents.get(0);
             Timestamp firstClickTimestamp = firstClickEvent.getMouseClickTime();
             long dayStartToLoginDifference = firstClickTimestamp.getTime() - startTimeStamp.getTime();
            dayStartToLoginDifference = (dayStartToLoginDifference/1000)/60;
              if (dayStartToLoginDifference >= 6)
                  effectiveWorkingMinutes = totalDayMinutes - dayStartToLoginDifference;

              for (int i = 0; i < timeEvents.size()-1; i++) {
                  Timestamp first = timeEvents.get(i).getMouseClickTime();
                  Timestamp second = timeEvents.get(i+1).getMouseClickTime();
                  long diff = second.getTime() - first.getTime();
                  diff = (diff/1000)/60;
                  if (diff >= 6) {
                      effectiveWorkingMinutes = effectiveWorkingMinutes - diff;
                  }

              }
              Timestamp lastTimestamp = timeEvents.get(timeEvents.size()-1).getMouseClickTime();
               long lastDifference = endTimeStamp.getTime() - lastTimestamp.getTime();
            lastDifference = (lastDifference/1000)/60;

            if (lastDifference >= 6) {
                effectiveWorkingMinutes = effectiveWorkingMinutes - lastDifference;
            }
            int activeHours = (int)effectiveWorkingMinutes/60;
            UserSessionDetail userSessionDetail =  userSessionDetailEntityMapper(userId.getUserId(), currentDate,  firstClickTimestamp, lastTimestamp, activeHours);
            persistUserSessionDetails.persistUserSessionDetails(userSessionDetail);
        }
    }

    private UserSessionDetail userSessionDetailEntityMapper(String userId, LocalDate businessDate, Timestamp userLoggedIn,
                                                            Timestamp userLoggedOut, int activeHours) {
        UserSessionDetail userSessionDetail = new UserSessionDetail();
        userSessionDetail.setUserId(userId);
        userSessionDetail.setBusinessDate(businessDate);
        userSessionDetail.setActiveHours(activeHours);
        userSessionDetail.setUserLoggedIn(userLoggedIn);
        userSessionDetail.setUserLoggedOut(userLoggedOut);
        userSessionDetail.setInsertedOn(new Timestamp(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(5) + TimeUnit.MINUTES.toMillis(30)));
        return  userSessionDetail;

    }
}
