package mock;

import majorPlanner.entity.Course;
import majorPlanner.gateway.CourseGateway;

public class AcceptingCourseGateway implements CourseGateway {

    private String requestedCourseID;
    public Course providedCourse;

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public Course getCourse(String courseID) {
        requestedCourseID = courseID;
        providedCourse = new Course(requestedCourseID);
        return providedCourse;
    }

    public String getRequestedCourseID() { return requestedCourseID; }
}
