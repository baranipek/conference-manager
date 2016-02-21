package com.thoughtworks.utility;

import com.thoughtworks.model.ConferenceRawData;
import com.thoughtworks.model.SessionDto;

import java.util.ArrayList;
import java.util.List;

public final class DtoConverter {

    public DtoConverter() {
    }

    public SessionDto convert(ConferenceRawData currentConferenceRawData) {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setSessionSubject(currentConferenceRawData.getTalkingSubject());
        sessionDto.setScheduled(true);
        sessionDto.setSessionDuration(currentConferenceRawData.getSessionDuration());
        return sessionDto;
    }

    public List<SessionDto> convert(List<ConferenceRawData> conferenceRawDatas) {
        List<SessionDto> sessionDtos = new ArrayList<>();
        for  (ConferenceRawData conferenceRawData: conferenceRawDatas) {
            SessionDto sessionDto = convert(conferenceRawData);
            sessionDtos.add(sessionDto);
        }
       return sessionDtos;
    }

    public SessionDto convert(ConferenceRawData conferenceRawData, String scheduledTime) {
        SessionDto sessionDto = convert(conferenceRawData);
        sessionDto.setScheduledTime(scheduledTime);
        return sessionDto;
    }
}
