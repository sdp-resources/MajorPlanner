package mock;

import majorPlanner.entity.Course;
import majorPlanner.gateway.CourseGateway;

public class AcceptingCourseGateway implements CourseGateway {

    private String requestedCourseID;

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public Course getCourse(String courseID) {
        requestedCourseID = courseID;
        return new Course(requestedCourseID);
    }

    public String getRequestedCourseID() { return requestedCourseID; }
}
