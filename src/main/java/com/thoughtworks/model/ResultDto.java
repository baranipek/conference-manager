package com.thoughtworks.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultDto implements Serializable {

    private static final long serialVersionUID = 6460184654182025679L;

    private Integer totalInputSessionDuration = 0;

    private List<ConferenceRawData> conferenceRawDataList = new ArrayList<>();

    private List<SessionDto> scheduledSessions = new ArrayList<>();

    public void setScheduledSessions(List<SessionDto> scheduledSessions) {
        this.scheduledSessions = new ArrayList<>();
    }

    public List<SessionDto> getScheduledSessions() {
        return scheduledSessions;
    }


    public List<ConferenceRawData> getConferenceRawDataList() {
        return conferenceRawDataList;
    }

    public ResultDto addItem(ConferenceRawData item) {
        getConferenceRawDataList().add(item);
        return this;

    }

    public ResultDto addItem(SessionDto item) {
        getScheduledSessions().add(item);
        return this;

    }

    public ResultDto addItemList(List<SessionDto> items) {
        getScheduledSessions().addAll(items);
        return this;

    }

    public ResultDto removeItem(ConferenceRawData item) {
        getConferenceRawDataList().remove(item);
        return this;

    }

    public Integer getTotalInputSessionDuration() {
        return totalInputSessionDuration;
    }

    public void setTotalInputSessionDuration(Integer totalInputSessionDuration) {
        this.totalInputSessionDuration = totalInputSessionDuration;
    }

    public void setConferenceRawDataList(List<ConferenceRawData> conferenceRawDataList) {
        this.conferenceRawDataList = conferenceRawDataList;
    }


}
