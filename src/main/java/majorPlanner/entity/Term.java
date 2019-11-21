package majorPlanner.entity;

public interface Term {
    Period getPeriod();

    Year getYear();

    boolean isAfter(Term term);

    boolean isBefore(Term term);

    boolean isBefore(TransferTerm transferTerm);

    boolean isBefore(CalendarTerm calendarTerm);
}

