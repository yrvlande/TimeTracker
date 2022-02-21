package com.fis.timetracker.service.schedulerService;

import com.fis.timetracker.config.LogBuilder;
import com.fis.timetracker.entity.TimeEvent;
import com.fis.timetracker.entity.UserSessionDetail;
import com.fis.timetracker.repository.TimeEventRepository;
import com.fis.timetracker.service.dbService.PersistUserSessionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ReportScheduler.class);
    private final TimeEventRepository timeEventRepository;
    private  final PersistUserSessionDetails persistUserSessionDetails;

    public ReportScheduler(TimeEventRepository timeEventRepository, PersistUserSessionDetails persistUserSessionDetails) {
        this.timeEventRepository = timeEventRepository;
        this.persistUserSessionDetails = persistUserSessionDetails;
    }

    @Scheduled( cron = "*/30 * * * * *")
    public void generateReport() throws ParseException {
        logger.info(new LogBuilder()
                .message("Report Generation Started")
                .logEvent("ReportGeneration")
                .toString());

        long totalDayMinutes = 1440;

        LocalDate currentDate = LocalDate.now();
        currentDate = currentDate.minusDays(1);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp startTimeStamp = new Timestamp(formatter.parse( currentDate.toString() +" 00:00:01").getTime() + TimeUnit.HOURS.toMillis(5) + TimeUnit.MINUTES.toMillis(30));
        Timestamp endTimeStamp = new Timestamp(formatter.parse( currentDate.toString() +" 23:59:59").getTime() + TimeUnit.HOURS.toMillis(5) + TimeUnit.MINUTES.toMillis(30));

        logger.info(new LogBuilder()
                .message("Fetching Distinct userId from timeEvents")
                .toString());

        List<TimeEvent> userIds = timeEventRepository.getUserIds();

        logger.info(new LogBuilder()
                .message("Fetched " + userIds.size() + " userIds")
                .toString());

        for (TimeEvent userId : userIds) {
            long effectiveWorkingMinutes = 0;

            logger.info(new LogBuilder()
                    .message("Fetching TimeEvent Details for userId " + userId.getUserId()
                            + " from " + startTimeStamp.toString() + " to " + endTimeStamp.toString())
                    .toString());

            List<TimeEvent> timeEvents = timeEventRepository.getClickInfo( userId.getUserId(), startTimeStamp, endTimeStamp );
            if(!timeEvents.isEmpty()){
                Timestamp firstClickTimestamp = timeEvents.get(0).getMouseClickTime();
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
                float activeHours = (float)effectiveWorkingMinutes/60;
                UserSessionDetail userSessionDetail =  userSessionDetailEntityMapper(userId.getUserId(), currentDate,  firstClickTimestamp, lastTimestamp, activeHours);
                persistUserSessionDetails.persistUserSessionDetails(userSessionDetail);
                logger.info(new LogBuilder()
                        .message("User " + userId.getUserId() + " was active for " + activeHours + " hours on " + currentDate.toString()
                                + ", User logged in at " + firstClickTimestamp.toString() + " and logged out at " + lastTimestamp)
                        .logEvent("UserSessionDetailsPersisted")
                        .toString());
            }
            else{
                logger.error(new LogBuilder()
                        .message("No Record Found for TimeEvent")
                        .toString());
            }
        }
    }

    private UserSessionDetail userSessionDetailEntityMapper(String userId, LocalDate businessDate, Timestamp userLoggedIn,
                                                            Timestamp userLoggedOut, float activeHours) {
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
