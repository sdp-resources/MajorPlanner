package majorPlanner.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Course {
    private String id;
    private Set<String> tags = new HashSet<>();
    private Prerequisite prerequisite = new NoPrerequisite();

    public Course (String id) {
        this.id = id;
    }

    public static Course withTags(String id, String... tags) {
        Course course = new Course(id);
        course.addTags(tags);
        return course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void addTags(String... args) {
        tags.addAll(Arrays.asList(args));
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public boolean hasAllTags(Set<String> expectedTags){
        for (String tag : expectedTags) {
           if (!this.hasTag(tag)){
               return false;
           }
        }
        return true;
    }

    public Set<String> getTags(){
        return tags;
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Course{" + id +
                ", " + tags +
                '}';
    }

    public void setPrerequisite(Prerequisite prereq) {
        this.prerequisite = prereq;
    }

    public Prerequisite getPrerequisite() {
        return prerequisite;
    }
}
