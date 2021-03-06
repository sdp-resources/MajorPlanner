package majorPlanner.entity;

public class AddedCourse {
    private Term term;
    private Course course;

    public AddedCourse(Term term, Course course){
        this.term = term;
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Term getTerm() { return term;}

    public boolean isTransferCourse() { return term.equals(new TransferTerm()); }

    public boolean matchesTerm(Period period, Year year) {
        return term.equals(new CalendarTerm(period, year));
    }

    boolean matches(Course course, Term term) {
        return this.course.equals(course) && this.term.equals(term);
    }
}
