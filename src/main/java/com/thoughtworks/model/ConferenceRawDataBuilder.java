package com.thoughtworks.model;

public class ConferenceRawDataBuilder {
    private String talkingSubject;

    private Integer sessionDuration;

    private Integer id;

    public ConferenceRawDataBuilder setTalkingSubject(String talkingSubject) {
        this.talkingSubject = talkingSubject;
        return this;
    }

    public ConferenceRawDataBuilder setSessionDuration(Integer sessionDuration) {
        this.sessionDuration = sessionDuration;
        return this;
    }

    public ConferenceRawDataBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public ConferenceRawData createConferenceRawData() {
        return new ConferenceRawData(talkingSubject, sessionDuration,id);
    }
}