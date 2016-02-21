package com.thoughtworks.manager;

import com.thoughtworks.constants.ConferenceConstants;
import com.thoughtworks.exception.ConferenceException;
import com.thoughtworks.model.ConferenceRawData;
import com.thoughtworks.model.ResultDto;
import com.thoughtworks.utility.DtoConverter;
import com.thoughtworks.utility.InputReader;

import java.util.Collections;
import java.util.List;

import static com.thoughtworks.utility.ScheduleChecker.createScheduleChecker;

public class SchedulerManager {

    public ResultDto schedule(String fileName) throws ConferenceException {
        InputReader inputReader = new InputReader();
        ResultDto resultDto = inputReader.readListFromFile(fileName);
        Collections.sort(resultDto.getConferenceRawDataList(), Collections.reverseOrder());

        DtoConverter dtoConverter = new DtoConverter();

        if (resultDto.getTotalInputSessionDuration() <= 0) {
            throw new ConferenceException("Input time durations are invalid");
        }
        int concurrentScheduledEventCount = resultDto.getTotalInputSessionDuration() / ConferenceConstants.totalSessionDurationPerRoom;
        List<List<ConferenceRawData>> morningSessions = createScheduleChecker().scheduleSessions(resultDto, concurrentScheduledEventCount, true);

        for (List<ConferenceRawData> conferenceRawDatas : morningSessions) {
            resultDto.addItemList(dtoConverter.convert(conferenceRawDatas));
            resultDto.getConferenceRawDataList().removeAll(conferenceRawDatas);
        }

        List<List<ConferenceRawData>> afternoonSessions = createScheduleChecker().scheduleSessions(resultDto, concurrentScheduledEventCount, false);

        return createScheduleChecker().scheduleSessionsTime(resultDto, morningSessions, afternoonSessions, afternoonSessions.size());

    }
}
