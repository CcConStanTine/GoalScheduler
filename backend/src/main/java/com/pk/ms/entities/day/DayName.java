package com.pk.ms.entities.day;

public enum DayName {
    EMPTY(0),
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    private final int dayNumber;

    DayName(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getDayNumber() { return dayNumber; }
}
