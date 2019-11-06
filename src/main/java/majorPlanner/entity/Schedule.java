package majorPlanner.entity;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private User owner;
    private String description;
    private String name;
    private int ID;
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


    public void setID(int id) {
        this.ID = id;
    }

    public int getID() {
        return ID;
    }

    public List<AddedCourse> getAddedCourses() {
        return addedCourses;
    }

    public void addCourse(Course course, Term term, Year year) {
        AddedCourse addedCourse = new AddedCourse(term, year, course);
        addedCourses.add(addedCourse);
    }

    public boolean containsCourse(Course course) {
        for (AddedCourse addedCourse : getAddedCourses())
            if (addedCourse.getCourse().equals(course)) return true;
        return false;
    }
}
