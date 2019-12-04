package majorPlanner;

import majorPlanner.entity.Course;
import majorPlanner.entity.CourseRequirement;
import majorPlanner.entity.Requirement;
import majorPlanner.entity.StoredRequirement;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.RequirementGateway;
import majorPlanner.interactor.ViewCourseListInteractor;
import majorPlanner.request.ViewCourseListRequest;
import majorPlanner.response.Response;
import mock.AcceptingCourseGateway;
import mock.AcceptingRequirementGateway;
import mock.ProvidingCourseGateway;
import mock.RejectingRequirementGateway;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ViewCourseListForRequirementTest {
    private int requirementId;
    private ViewCourseListRequest request;
    private ViewCourseListInteractor interactor;
    private Response response;
    private RequirementGateway rejectingRequirementGateway = new RejectingRequirementGateway();
    private CourseGateway providingCourseGateway;

    @Test
    public void whenRequirementIdInvalid_ReturnErrorResponse(){
        requirementId = 123;
        providingCourseGateway = new ProvidingCourseGateway(Set.of());
        request = new ViewCourseListRequest(requirementId);
        interactor = new ViewCourseListInteractor(request, rejectingRequirementGateway, providingCourseGateway);
        response = interactor.execute();
        assertThat(response, is(Response.invalidRequirementId()));
        assertThat(interactor.requirementId, is(requirementId));
    }

    @Test
    public void whenRequirementIdIsValid_ReturnSuccessResponse(){
        requirementId = 123;
        request = new ViewCourseListRequest(requirementId);
        Course course1 = new Course("CS220");
        Course course2 = new Course("CS223");
        providingCourseGateway = new ProvidingCourseGateway(Set.of(course1, course2));
        RequirementGateway acceptingRequirementGateway = new AcceptingRequirementGateway();
        Requirement singleCourseRequirement = new CourseRequirement("CS220");
        StoredRequirement storedRequirement = new StoredRequirement(singleCourseRequirement, "Must have CS220");
        acceptingRequirementGateway.addRequirement(storedRequirement);
        interactor = new ViewCourseListInteractor(request, acceptingRequirementGateway, providingCourseGateway);
        response = interactor.execute();
        assertThat(response, is(Response.success(Set.of(new Course("CS220")))));
    }
}
