package com.thoughtworks.model;

import java.io.Serializable;

public class ConferenceRawData implements Serializable, Comparable<ConferenceRawData> {

    private Integer sessionDuration;

    private String talkingSubject;

    private boolean scheduled;

    public ConferenceRawData(String talkingSubject, Integer sessionDuration, Integer id) {
        this.talkingSubject = talkingSubject;
        this.sessionDuration = sessionDuration;

    }

    private static final long serialVersionUID = -7158333225959355020L;

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public Integer getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Integer sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public String getTalkingSubject() {
        return talkingSubject;
    }

    public void setTalkingSubject(String talkingSubject) {
        this.talkingSubject = talkingSubject;
    }

    @Override
    public int compareTo(ConferenceRawData obj) {
        return sessionDuration.compareTo(obj.sessionDuration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConferenceRawData)) {
            return false;
        }

        ConferenceRawData that = (ConferenceRawData) o;

        if (scheduled != that.scheduled) {
            return false;
        }
        if (sessionDuration != null ? !sessionDuration.equals(that.sessionDuration) : that.sessionDuration != null) {
            return false;
        }
        return talkingSubject != null ? talkingSubject.equals(that.talkingSubject) : that.talkingSubject == null;

    }

    @Override
    public int hashCode() {
        int result = sessionDuration != null ? sessionDuration.hashCode() : 0;
        result = 31 * result + (talkingSubject != null ? talkingSubject.hashCode() : 0);
        result = 31 * result + (scheduled ? 1 : 0);
        return result;
    }

}
