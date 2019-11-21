package majorPlanner.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CalendarTerm implements Term {
    private final Period period;
    private final Year year;

    public CalendarTerm(Period period, Year year) {
        this.period = period;
        this.year = year;
    }

    @Override
    public Period getPeriod() {
        return period;
    }

    @Override
    public Year getYear() {
        return year;
    }

    @Override
    public boolean isAfter(Term t2) {
        return t2.isParameterAfterThis(this);
    }

    @Override
    public boolean isParameterAfterThis(CalendarTerm calendarTerm) {
        return areOrdered(this, calendarTerm);
    }

    public static boolean areOrdered(CalendarTerm t1, CalendarTerm t2) {
        int yearCompare = t1.year.compareTo(t2.year);
        int periodCompare = t1.period.compareTo(t2.period);
        if(yearCompare > 0) return false;
        if(yearCompare < 0) return true;
        if(periodCompare > 0) return false;
        if(periodCompare < 0) return true;
        return false;
    }

    @Override
    public boolean isParameterAfterThis(TransferTerm transferTerm) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarTerm that = (CalendarTerm) o;
        return period == that.period &&
                year == that.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, year);
    }

    @NotNull
    public static CalendarTerm of(String period, String year) {
        return new CalendarTerm(Period.valueOf(period), Year.valueOf(year));
    }
}
