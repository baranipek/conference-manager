package com.thoughtworks.utility;

import com.thoughtworks.model.ConferenceRawData;
import com.thoughtworks.model.ResultDto;
import com.thoughtworks.model.SessionDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleChecker {
    private final TotalTimeChecker totalTimeChecker = new TotalTimeChecker();

    private final TimeScheduler timeScheduler = new TimeScheduler();

    private DtoConverter dtoConverter = new DtoConverter();

    private ScheduleChecker() {
    }

    public static ScheduleChecker createScheduleChecker() {
        return new ScheduleChecker();
    }

    public List<List<ConferenceRawData>> scheduleSessions(ResultDto resultDto, int concurrentScheduledEventCount, boolean isMorningSession) {
        int minSessionDuration = 180;
        int maxSessionDuration = 240;

        if (isMorningSession) {
            maxSessionDuration = minSessionDuration;
        }

        int sessionsSize = resultDto.getConferenceRawDataList().size();

        int totalReservedRoomCount = 0;
        List<List<ConferenceRawData>> combinationsOfSessions = new ArrayList<List<ConferenceRawData>>();

        for (int count = 0; count < sessionsSize; count++) {
            int startPoint = count;
            int totalTime = 0;
            List<ConferenceRawData> conferenceRawDatas = new ArrayList<>();
            totalTime = totalTimeChecker.checkInputs                              (resultDto, isMorningSession, minSessionDuration, maxSessionDuration, sessionsSize, startPoint,
                                                     totalTime, conferenceRawDatas);

            boolean isExpectedInputs;
            if (isMorningSession) {
                isExpectedInputs = (totalTime == maxSessionDuration);
            } else {
                isExpectedInputs = (totalTime >= minSessionDuration && totalTime <= maxSessionDuration);
            }

            if (isExpectedInputs) {
                combinationsOfSessions.add(conferenceRawDatas);
                for (ConferenceRawData talk : conferenceRawDatas) {
                    talk.setScheduled(true);
                }
                totalReservedRoomCount++;
                if (totalReservedRoomCount == concurrentScheduledEventCount) {
                    break;
                }
            }

        }

        return combinationsOfSessions;

    }

    public ResultDto scheduleSessionsTime(ResultDto resultDto, List<List<ConferenceRawData>> morningSessions, List<List<ConferenceRawData>>
            afternoonSessions, int paralelSessionsCount) {
        List<List<SessionDto>> scheduledSessionList = new ArrayList<List<SessionDto>>();

        for (int counter = 0; counter < paralelSessionsCount; counter++) {
            List<SessionDto> sessions = new ArrayList<SessionDto>();

            GetStartingTime getStartingTime = new GetStartingTime().invoke();
            SimpleDateFormat dateFormat = getStartingTime.getDateFormat();
            Date date = getStartingTime.getDate();

            String scheduledTime = dateFormat.format(date);

            List<ConferenceRawData> mornSessionTalkList = morningSessions.get(counter);
            scheduledTime = setSessionDtoParameters(sessions, date, scheduledTime, mornSessionTalkList);

            int lunchTimeDuration = 60;
            SessionDto lunch = new SessionDto("Lunch", 60);
            lunch.setScheduledTime(scheduledTime);
            sessions.add(lunch);

            scheduledTime = timeScheduler.getNextScheduledTime(date, lunchTimeDuration);
            List<ConferenceRawData> afterNoonSessions = afternoonSessions.get(counter);
            scheduledTime = setSessionDtoParameters(sessions, date, scheduledTime, afterNoonSessions);

            SessionDto networkingSession = new SessionDto("Networking Event", 60);
            networkingSession.setScheduledTime(scheduledTime);
            sessions.add(networkingSession);

            scheduledSessionList.add(sessions);
        }
        resultDto.setScheduledSessions(null);
        scheduledSessionList.stream().forEach(x-> resultDto.addItemList(x));

        return resultDto;
    }



    private String setSessionDtoParameters(List<SessionDto> sessions, Date date, String scheduledTime, List<ConferenceRawData> mornSessionTalkList) {
        for (ConferenceRawData conferenceRawData : mornSessionTalkList) {
            SessionDto sessionDto = dtoConverter.convert(conferenceRawData, scheduledTime);
            scheduledTime = timeScheduler.getNextScheduledTime(date, sessionDto.getSessionDuration());
            sessions.add(sessionDto);
        }
        return scheduledTime;
    }

    private class GetStartingTime {
        private Date date;

        private SimpleDateFormat dateFormat;

        public Date getDate() {
            return date;
        }

        public SimpleDateFormat getDateFormat() {
            return dateFormat;
        }

        public GetStartingTime invoke() {
            date = new Date();
            dateFormat = new SimpleDateFormat("hh:mma ");
            date.setHours(9);
            date.setMinutes(0);
            date.setSeconds(0);
            return this;
        }
    }
}
