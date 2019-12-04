package mock;

import majorPlanner.entity.Course;
import majorPlanner.gateway.CourseGateway;

import java.util.List;
import java.util.Set;

public class ProvidingCourseGateway implements CourseGateway {
    private Set<Course> providedCourses;

    public ProvidingCourseGateway(Set<Course> providedCourses){
        this.providedCourses = providedCourses;
    }

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public Course getCourse(String courseID) {
        return null;
    }

    @Override
    public Set<Course> getAllCourses() {
        return providedCourses;
    }
}
