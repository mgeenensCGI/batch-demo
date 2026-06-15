package com.example.batch_demo.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.batch_demo.utils.MappingUtils.trim;

public class DateUtils {

    public static final DateTimeFormatter ISO_LOCAL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;


    private DateUtils() {
    }

    public static LocalDate parseDate(String value, String fieldName, DateTimeFormatter formatter) {
        String cleaned = trim(value);
        if (cleaned == null || cleaned.isBlank()) {
            throw new IllegalArgumentException("Field '%s' is blank".formatted(fieldName));
        }

        try {
            return LocalDate.parse(cleaned, formatter);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Field '%s' is not a valid ISO date: %s".formatted(fieldName, cleaned), ex);
        }
    }
}
