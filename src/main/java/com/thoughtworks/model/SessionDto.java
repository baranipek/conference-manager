package com.thoughtworks.model;

import java.io.Serializable;

public class SessionDto implements Serializable {

    private String scheduledTime;

    private Integer sessionDuration;

    private String sessionSubject;

    private boolean scheduled;


    public SessionDto(String sessionSubject, int sessionDuration) {
        this.sessionSubject = sessionSubject;
        this.sessionDuration = sessionDuration;

    }

    public SessionDto() {

    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Integer getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Integer sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public String getSessionSubject() {
        return sessionSubject;
    }

    public void setSessionSubject(String sessionSubject) {
        this.sessionSubject = sessionSubject;
    }

    @Override
    public String toString() {
        return "{"+ scheduledTime + '\'' +
                "," + sessionDuration +
                ",'" + sessionSubject + '\'' +
                ",=" + scheduled +
                '}';
    }

}
