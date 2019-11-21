package majorPlanner.entity;

import org.junit.Test;

import static majorPlanner.entity.Period.*;
import static majorPlanner.entity.Year.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class TermTest {
    private final Term freshmanFall = new CalendarTerm(Fall, Freshman);
    private final Term sophomoreFall = new CalendarTerm(Fall, Sophomore);
    private final Term freshmanWinter = new CalendarTerm(Winter, Freshman);
    private final Term transfer = new TransferTerm();

    @Test
    public void TransferTermsComeAfterTransferTerms() {
        assertThat(transfer.isAfter(transfer), is(true));
    }

    @Test
    public void CalendarTermsComeAfterTransferTerms() {
        assertThat(freshmanFall.isAfter(transfer), is(true));
    }

    @Test
    public void TransferTermsAreNotAfterCalenderTerms() {
        assertThat(transfer.isAfter(freshmanFall), is(false));
    }

    @Test
    public void bothAreCalenderTerms_DifferentYears() {
        assertThat(sophomoreFall.isAfter(freshmanFall), is(true));
        assertThat(freshmanFall.isAfter(sophomoreFall), is(false));
    }


    @Test
    public void bothAreCalendarTermsInSameYear_DifferentTerms(){
        assertThat(freshmanWinter.isAfter(freshmanFall), is(true));
        assertThat(freshmanFall.isAfter(freshmanWinter), is(false));
    }

    @Test
    public void bothAreCalendarTermsInSameYearAndSameTerm(){
        assertThat(freshmanFall.isAfter(freshmanFall), is(false));
    }
}