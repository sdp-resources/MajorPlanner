package mock;

import majorPlanner.entity.Course;
import majorPlanner.gateway.CourseGateway;

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

    public String getRequestedCourseID() { return requestedCourseID; }
}
