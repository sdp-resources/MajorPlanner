package majorPlanner.gateway;

import majorPlanner.entity.Course;

import java.util.Set;

public interface CourseGateway {
    void addCourse(Course course);

    Course getCourse(String courseID);

    Set<Course> getAllCourses();
}
