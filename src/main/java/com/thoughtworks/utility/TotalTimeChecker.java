package com.thoughtworks.utility;

import com.thoughtworks.model.ConferenceRawData;
import com.thoughtworks.model.ResultDto;

import java.util.List;

public class TotalTimeChecker {
    public TotalTimeChecker() {
    }

    int checkInputs(ResultDto resultDto, boolean isMorningSession, int minSessionDuration, int maxSessionDuration, int sessionsSize, int startPoint, int totalTime, List<ConferenceRawData> conferenceRawDatas) {
        while (startPoint != sessionsSize) {
            int currentCount = startPoint;
            startPoint++;
            ConferenceRawData currentConferenceRawData = resultDto.getConferenceRawDataList().get(currentCount);
            if (currentConferenceRawData.isScheduled()) {
                continue;
            }
            int sessionTime = currentConferenceRawData.getSessionDuration();

            if (sessionTime > maxSessionDuration || sessionTime + totalTime > maxSessionDuration) {
                continue;
            }

            totalTime += sessionTime;
            conferenceRawDatas.add(currentConferenceRawData);

            if (isMorningSession) {
                if (totalTime == maxSessionDuration) {
                    break;
                }
            } else if (totalTime >= minSessionDuration) {
                break;
            }

        }
        return totalTime;
    }
}