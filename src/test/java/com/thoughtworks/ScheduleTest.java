package com.thoughtworks;

import com.thoughtworks.exception.ConferenceException;
import com.thoughtworks.manager.SchedulerManager;
import com.thoughtworks.model.ResultDto;
import com.thoughtworks.model.SessionDto;
import com.thoughtworks.utility.OutputWriter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class ScheduleTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private String properDateFile;

    private String properDateFileOne;

    private SchedulerManager schedulerManager;

    private static final Integer totalInputTime = 770;

    private String startingTime;

    private OutputWriter outputWriter;

    @Before
    public void setup() {
        properDateFile = "trackmanagement-inputs";
        properDateFileOne= "invalidtimeparameter";
        schedulerManager = new SchedulerManager();
        outputWriter = new OutputWriter();
        startingTime = "09:00AM";
    }

    @Test
    public void scheduleTotalTimeSuccessfully() throws ConferenceException {
        ResultDto resultDto = schedulerManager.schedule(properDateFile);
        assertEquals(resultDto.getTotalInputSessionDuration(), totalInputTime);

    }

    @Test(expected = ConferenceException.class)
    public void shouldThrowsInvalidTimeException() throws ConferenceException {
        ResultDto resultDto = schedulerManager.schedule(properDateFileOne);
    }

    @Test
    public void scheduleStartingTimeSuccessfully() throws ConferenceException {
        ResultDto resultDto = schedulerManager.schedule(properDateFile);
        assertNotNull(resultDto.getScheduledSessions());
        assertEquals(resultDto.getScheduledSessions().get(0).getScheduledTime().trim(), startingTime);

    }

    @Test
    public void printResults() throws ConferenceException {
        ResultDto resultDto = schedulerManager.schedule(properDateFile);
        resultDto.getScheduledSessions().forEach((System.out::println));
    }


}
