package com.fis.timetracker.service.schedulerService;

import com.fis.timetracker.entity.TimeEvent;
import com.fis.timetracker.repository.TimeEventRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ReportScheduler {
    private final TimeEventRepository timeEventRepository;

    public ReportScheduler(TimeEventRepository timeEventRepository) {
        this.timeEventRepository = timeEventRepository;
    }

    @Scheduled( cron = "*/59 * * * * *")
    public void generateReport() throws ParseException {
        long totalMinutes = 1440;

        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(0);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp startTimeStamp = new Timestamp(formatter.parse( localDate.toString() +" 00:00:01").getTime() + TimeUnit.HOURS.toMillis(5) + TimeUnit.MINUTES.toMillis(30));
        Timestamp endTimeStamp = new Timestamp(formatter.parse( localDate.toString() +" 23:59:59").getTime() + TimeUnit.HOURS.toMillis(5) + TimeUnit.MINUTES.toMillis(30));
        List<TimeEvent> useIds = timeEventRepository.getUserIds();

        for (TimeEvent userId : useIds) {
            long calculatedMinutes = 0;
            List<TimeEvent> timeEvents = timeEventRepository.getClickInfo( userId.getUserId(), startTimeStamp, endTimeStamp );
             TimeEvent firstEvent = timeEvents.get(0);
             Timestamp firstTimeStamp = firstEvent.getMouseClickTime();
             long difference = firstTimeStamp.getTime() - startTimeStamp.getTime();
            difference = (difference/1000)/60;
              if (difference >= 6)
                   calculatedMinutes = totalMinutes - difference;

              for (int i = 0; i < timeEvents.size()-1; i++) {
                  Timestamp first = timeEvents.get(i).getMouseClickTime();
                  Timestamp second = timeEvents.get(i+1).getMouseClickTime();
                  long diff = second.getTime() - first.getTime();
                  diff = (diff/1000)/60;
                  if (diff >= 6) {
                      calculatedMinutes = calculatedMinutes - diff;
                  }

              }
              Timestamp lastTimestamp = timeEvents.get(timeEvents.size()-1).getMouseClickTime();
               long lastDifference = endTimeStamp.getTime() - lastTimestamp.getTime();
            lastDifference = (lastDifference/1000)/60;

            if (lastDifference >= 6) {
                calculatedMinutes = calculatedMinutes - lastDifference;
            }


        }


    }
}
