package com.reman8683.neis4j.timetable;

import com.reman8683.neis4j.school.School;

import java.io.IOException;
import java.util.List;

public class TimeTable {
    private final School school;
    private final String timeTableYMD;
    private final int grade;
    private final int classNumber;
    private final List<String> timeTable;

    protected TimeTable(School school, String timeTableYMD, int grade, int classNumber, List<String> timeTable) {
        this.school = school;
        this.timeTableYMD = timeTableYMD;
        this.grade = grade;
        this.classNumber = classNumber;
        this.timeTable = timeTable;
    }

    public TimeTable(School school, String timeTableYMD, int grade, int classNumber) throws IOException {
        TimeTable timeTable = new TimeTableApi().getTimeTable(school, timeTableYMD, grade, classNumber);
        this.school = school;
        this.timeTableYMD = timeTableYMD;
        this.grade = grade;
        this.classNumber = classNumber;
        this.timeTable = timeTable.getTimeTable();
    }

    public List<String> getTimeTable() {
        return timeTable;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "school=" + school +
                ", timeTableYMD='" + timeTableYMD + '\'' +
                ", grade=" + grade +
                ", classNumber=" + classNumber +
                ", timeTable=" + timeTable +
                '}';
    }
}
