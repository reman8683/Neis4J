package com.reman8683.neis4j.dto;

import com.reman8683.neis4j.api.FindSchool;
import com.reman8683.neis4j.OfficeCode;

import java.io.IOException;

public class School {
    public static final String NEIS_URL = "open.neis.go.kr";
    private final String name;
    private final long schoolCode;
    private final String type;
    private final OfficeCode officeCode;

    public School(String name, long schoolCode, String type, OfficeCode officeCode) {
        this.name = name;
        this.schoolCode = schoolCode;
        this.type = type;
        this.officeCode = officeCode;
    }

    public School(String name) throws IOException {
        School school = new FindSchool().byName(name);
        this.name = school.name;
        this.schoolCode = school.schoolCode;
        this.type = school.type;
        this.officeCode = school.officeCode;
    }

    public School(long schoolCode) throws IOException {
        School school = new FindSchool().bySchoolCode(schoolCode);
        this.name = school.name;
        this.schoolCode = school.schoolCode;
        this.type = school.type;
        this.officeCode = school.officeCode;
    }

    public String getName() {
        return name;
    }

    public OfficeCode getOfficeCode() {
        return officeCode;
    }

    public long getSchoolCode() {
        return schoolCode;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", schoolCode=" + schoolCode +
                ", type='" + type + '\'' +
                ", officeCode=" + officeCode +
                '}';
    }
}
