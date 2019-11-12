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
