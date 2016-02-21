package com.thoughtworks.utility;

import com.thoughtworks.constants.ConferenceConstants;
import com.thoughtworks.exception.ConferenceException;
import com.thoughtworks.model.ConferenceRawData;
import com.thoughtworks.model.ConferenceRawDataBuilder;
import com.thoughtworks.model.ResultDto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class InputReader {

    private File file;

    private static final String filePath = "/src/main/resources/";

    private Integer time;

    public ResultDto readListFromFile(String fileName) throws ConferenceException {
        ResultDto resultDto = new ResultDto();
        Integer talkCount = -1;
        file = new File("");

        StringBuffer fileBuffer = new StringBuffer();
        fileBuffer.append(file.getAbsolutePath()).append(filePath).append(fileName);
        try {
            FileInputStream stream = new FileInputStream(fileBuffer.toString());
            DataInputStream inputStream = new DataInputStream(stream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String readLine = reader.readLine();
            ConferenceRawData conferenceRawData = null;
            while (readLine != null) {

                int lastSpaceIndex = getLastSpaceIndex(readLine);
                String subject = readLine.substring(0, lastSpaceIndex);
                throwInvalidSubject(subject);
                String sessionDuration = readLine.substring(lastSpaceIndex + 1);

                Integer timeDuration = checkSessionDurationParameters(sessionDuration);
                talkCount++;
                conferenceRawData = parseLine(talkCount, subject, timeDuration);
                readLine = reader.readLine();
                resultDto.setTotalInputSessionDuration(resultDto.getTotalInputSessionDuration()+timeDuration);
                resultDto.addItem(conferenceRawData);
            }
            inputStream.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return resultDto;
    }

    private Integer checkSessionDurationParameters(String sessionDuration) throws ConferenceException {
        if (sessionDuration.endsWith(ConferenceConstants.minSuffix)) {
            time = Integer.parseInt(sessionDuration.substring(0, sessionDuration.indexOf(ConferenceConstants.minSuffix)));
            if (time > ConferenceConstants.maxTalkMinutes || time < ConferenceConstants.minTalkMinutes) {
                throw new ConferenceException("Invalid time period");
            }
        } else if (sessionDuration.endsWith(ConferenceConstants.lightningSuffix)) {
            String lightningTime = sessionDuration.substring(0, sessionDuration.indexOf(ConferenceConstants.lightningSuffix));
            if ("".equals(lightningTime)) {
                time = ConferenceConstants.lightningMinutes;
            } else {
                time = Integer.parseInt(lightningTime) * ConferenceConstants.lightningMinutes;
            }
        }
        return time;
    }

    private void throwInvalidSubject(String subject) throws ConferenceException {
        if (subject == null || "".equals(subject.trim())) {
            throw new ConferenceException("Invalid subject name, " + subject);
        }
    }

    private int getLastSpaceIndex(String readLine) throws ConferenceException {
        int lastSpaceIndex = readLine.lastIndexOf(" ");
        if (lastSpaceIndex == -1) {
            throw new ConferenceException("Talk time must be specified");
        }
        return lastSpaceIndex;
    }

    private ConferenceRawData parseLine( Integer talkCount, String subject, Integer timeDuration) {
        return new ConferenceRawDataBuilder().setSessionDuration(timeDuration).setId(talkCount).setTalkingSubject(subject).createConferenceRawData();
    }
}
