package majorPlanner.entity;

public interface Term {
    Period getPeriod();

    Year getYear();

    boolean isAfter(Term t2);

    boolean isParameterAfterThis(TransferTerm transferTerm);

    boolean isParameterAfterThis(CalendarTerm calendarTerm);

}

