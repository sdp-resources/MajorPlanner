package majorPlanner.interactor;

import majorPlanner.entity.Course;
import majorPlanner.entity.StoredRequirement;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.RequirementGateway;
import majorPlanner.request.ViewCourseListRequest;
import majorPlanner.response.Response;

import java.util.List;
import java.util.Set;

public class ViewCourseListInteractor implements Interactor {
    private CourseGateway courseGateway;
    public RequirementGateway requirementGateway;
    public ViewCourseListRequest request;
    public int requirementId;

    public ViewCourseListInteractor(ViewCourseListRequest request, RequirementGateway requirementGateway, CourseGateway courseGateway) {
        this.request = request;
        this.requirementGateway = requirementGateway;
        this.courseGateway = courseGateway;
    }

    @Override
    public Response execute() {
        // TODO: Implement correctly
        requirementId = request.getRequirementId();
        StoredRequirement requirement = requirementGateway.getRequirement(requirementId);
        if (requirement == null){
            return Response.invalidRequirementId();
        }
        Set<Course> allCourses = courseGateway.getAllCourses();
        Set matchedCourses = requirement.getRequirement().matches(allCourses);
        return Response.success(matchedCourses);
    }
}
