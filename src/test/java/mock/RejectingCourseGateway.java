package mock;

import majorPlanner.entity.Course;
import majorPlanner.gateway.CourseGateway;

import java.util.Set;

public class RejectingCourseGateway implements CourseGateway {
    public String requestedCourseID;
    @Override
    public void addCourse(Course course) {

    }

    @Override
    public Course getCourse(String courseID) {
        requestedCourseID = courseID;
        return null;
    }

    @Override
    public Set<Course> getAllCourses() {
        return null;
    }

    public String getRequestedCourseID() { return requestedCourseID; }
}
