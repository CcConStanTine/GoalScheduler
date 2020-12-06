package com.pk.ms.constants;

public enum Importance {
    NOTIMPORTANT(6, 7, 8, 10),
    REGULAR(15, 18, 17, 20),
    IMPORTANT(40, 40, 38, 35),
    VERYIMPORTANT(58, 58, 55, 50);

    private final int yearImportanceWeight;
    private final int monthImportanceWeight;
    private final int weekImportanceWeight;
    private final int dayImportanceWeight;

    Importance(int yearImportanceWeight, int monthImportanceWeight, int weekImportanceWeight, int dayImportanceWeight) {
        this.yearImportanceWeight = yearImportanceWeight;
        this.monthImportanceWeight = monthImportanceWeight;
        this.weekImportanceWeight = weekImportanceWeight;
        this.dayImportanceWeight = dayImportanceWeight;
    }

    public int getYearImportanceWeight() {
        return yearImportanceWeight;
    }

    public int getMonthImportanceWeight() {
        return monthImportanceWeight;
    }

    public int getWeekImportanceWeight() {
        return weekImportanceWeight;
    }

    public int getDayImportanceWeight() {
        return dayImportanceWeight;
    }
}
