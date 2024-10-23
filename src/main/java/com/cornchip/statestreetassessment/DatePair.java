package com.cornchip.statestreetassessment;

import java.time.LocalDate;
import java.util.Calendar;

// Technically, the "correct" way to do this is with private fields and getter/setters
// Also, this should be called a "CalendarPair" because its no longer using Date types
public class DatePair {
    public LocalDate startDate;
    public LocalDate endDate;

    public DatePair(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
