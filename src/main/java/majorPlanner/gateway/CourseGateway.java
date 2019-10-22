package majorPlanner.gateway;

import majorPlanner.entity.Course;

public interface CourseGateway {
    void addCourse(Course course);

    Course getCourse(String courseID);
}
