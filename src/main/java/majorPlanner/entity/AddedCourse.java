package majorPlanner.entity;

public class AddedCourse {
    private Term term;
    private Year year;
    private Course course;

    public AddedCourse(Term term, Year year, Course course){
        this.term = term;
        this.year = year;
        this.course = course;
    }


    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
