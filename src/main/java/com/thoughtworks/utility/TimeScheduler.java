package com.thoughtworks.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeScheduler {
    public TimeScheduler() {
    }

    String getNextScheduledTime(Date date, Integer sessionDuration) {
        long timeInLong = date.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma ");

        long timeDurationInLong = sessionDuration * 60 * 1000;
        long newTimeInLong = timeInLong + timeDurationInLong;

        date.setTime(newTimeInLong);
        String str = dateFormat.format(date);
        return str;
    }
}