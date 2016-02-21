package com.thoughtworks.enums;

public enum SessionsEnum {

    MORNING("morning", 900, 1200),
    LUNCH("lunch", 1200, 1300),
    AFTERNOON("afternoon", 1300, 1700),
    NETWORKING("networking", 1600, 1800);

    private String name;

    private Integer startTime;

    private Integer endTime;

    SessionsEnum(String name, Integer startTime, Integer endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
