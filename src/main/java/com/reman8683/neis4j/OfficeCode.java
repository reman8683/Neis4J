package com.reman8683.neis4j;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OfficeCode {
    SEOUL("B10"),
    BUSAN("C10"),
    DAEGU("D10"),
    INCHEON("E10"),
    GWANGJU("F10"),
    DAEJEON("G10"),
    ULSAN("H10"),
    SEJONG("I10"),
    GEONGGI("J10"),
    GANGWON("K10"),
    CHONGBUK("M10"),
    CHONGNAM("N10"),
    JEONBUK("P10"),
    JEONNAM("Q10"),
    GEONGBUK("R10"),
    GEONGNAM("S10"),
    JEJU("T10"),
    UNKNOWN("Error");

    private String officeCode;

    OfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    private static final Map<String, OfficeCode> officeCodes =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(OfficeCode::getOfficeCode, Function.identity())));

    public static OfficeCode find(String officeCode) {
        return Optional.ofNullable(officeCodes.get(officeCode)).orElse(UNKNOWN);
    }
}
