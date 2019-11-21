package majorPlanner.entity;

import java.util.Objects;

public class TransferTerm implements Term {
    @Override
    public Period getPeriod() {
        throw new RuntimeException();
    }

    @Override
    public Year getYear() {
        throw new RuntimeException();
    }

    @Override
    public boolean isAfter(Term term) {
        return term.isBefore(this);
    }

    @Override
    public boolean isBefore(Term term) {
        return term.isAfter(this);
    }

    @Override
    public boolean isBefore(CalendarTerm calendarTerm) {
        return true;
    }

    @Override
    public boolean isBefore(TransferTerm transferTerm) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}
