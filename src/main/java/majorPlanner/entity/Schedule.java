package majorPlanner.entity;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private User owner;
    private String description;
    private String name;
    private Integer ID;
    private List<AddedCourse> addedCourses = new ArrayList<>();

    public Schedule(User owner, String name, String description) {
        this.setOwner(owner);
        this.setDescription(description);
        this.setName(name);
    }


    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setID(Integer id) {
        this.ID = id;
    }

    public Integer getID() {
        return ID;
    }

    public List<AddedCourse> getAddedCourses() {
        return addedCourses;
    }

    public void addCourse(Course course, String periodString, String yearString) {
        CalendarTerm term = CalendarTerm.of(periodString, yearString);
        AddedCourse addedCourse = new AddedCourse(term, course);
        addedCourses.add(addedCourse);
    }

    public boolean deleteCourse(Course courseToBeRemoved){
        int index = 0;
        for (AddedCourse course : addedCourses) {
            if(course.getCourse().equals(courseToBeRemoved)){
                addedCourses.remove(index);
                return true;
            }
            index += 1;
        }
        return false;
    }

    public boolean containsCourse(Course course) {
        for (AddedCourse addedCourse : addedCourses)
            if (addedCourse.getCourse().equals(course)) return true;
        return false;
    }

    public boolean isEmpty() {
        return addedCourses.size() == 0;
    }

    public boolean containsCourse(Course course, Term term) {
        for (AddedCourse ac : addedCourses) {
            if (ac.matches(course, term)) return true;
        }
        return false;
    }

    public void addTransferCourse(Course course) {
        Term term = new TransferTerm();
        AddedCourse addedCourse = new AddedCourse(term, course);
        addedCourses.add(addedCourse);
    }
}
