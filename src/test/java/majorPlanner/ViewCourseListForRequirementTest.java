package majorPlanner;

import majorPlanner.entity.*;
import majorPlanner.gateway.CourseGateway;
import majorPlanner.gateway.RequirementGateway;
import majorPlanner.interactor.ViewCourseListInteractor;
import majorPlanner.request.ViewCourseListRequest;
import majorPlanner.response.Response;
import mock.AcceptingRequirementGateway;
import mock.ProvidingCourseGateway;
import mock.RejectingRequirementGateway;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ViewCourseListForRequirementTest {
    private int requirementId = 123;
    private ViewCourseListRequest request;
    private ViewCourseListInteractor interactor;
    private Response response;
    private CourseGateway providingCourseGateway;
    private RequirementGateway rejectingRequirementGateway;
    private RequirementGateway acceptingRequirementGateway;
    private Course course1;
    private Course course2;
    private Course course3;
    private StoredRequirement storedRequirement;
    private Requirement singleCourseRequirement1;
    private Requirement singleCourseRequirement2;
    private EitherRequirement eitherRequirement;
    private ExcludeRequirement excludeRequirement;

    @Before
    public void setUp() {
        course1 = new Course("CS220");
        course2 = new Course("CS223");
        course3 = new Course("CS321");
        request = new ViewCourseListRequest(requirementId);
        rejectingRequirementGateway = new RejectingRequirementGateway();
        acceptingRequirementGateway = new AcceptingRequirementGateway();
        providingCourseGateway = new ProvidingCourseGateway(Set.of(course1, course2, course3));
        singleCourseRequirement1 = new CourseRequirement("CS220");
        singleCourseRequirement2 = new CourseRequirement("CS223");
        eitherRequirement = new EitherRequirement(singleCourseRequirement1, singleCourseRequirement2);
        excludeRequirement = new ExcludeRequirement(eitherRequirement, Set.of("CS223"));
    }

    @Test
    public void whenRequirementIdInvalid_ReturnErrorResponse(){
        interactor = new ViewCourseListInteractor(request, rejectingRequirementGateway, providingCourseGateway);
        response = interactor.execute();
        assertThat(response, is(Response.invalidRequirementId()));
        assertThat(interactor.getRequirementId(), is(requirementId));
    }

    @Test
    public void whenSingleCourseRequirementIdIsValid_ReturnSuccessResponse(){
        storedRequirement = new StoredRequirement(singleCourseRequirement1, "Must have CS220");
        acceptingRequirementGateway.addRequirement(storedRequirement);
        interactor = new ViewCourseListInteractor(request, acceptingRequirementGateway, providingCourseGateway);
        response = interactor.execute();
        assertThat(response, is(Response.success(Set.of(course1))));
        assertThat(interactor.getRequirementId(), is(requirementId));
    }

    @Test
    public void whenEitherRequirementIdIsValid_ReturnSuccessResponse() {
        storedRequirement = new StoredRequirement(eitherRequirement, "Must have either CS220 OR CS223");
        acceptingRequirementGateway.addRequirement(storedRequirement);
        interactor = new ViewCourseListInteractor(request, acceptingRequirementGateway, providingCourseGateway);
        response = interactor.execute();
        assertThat(response, is(Response.success(Set.of(course1, course2))));
        assertThat(interactor.getRequirementId(), is(requirementId));
    }

    @Test
    public void whenExcludeRequirementIdIsValid_ReturnSuccessResponse() {
        storedRequirement = new StoredRequirement(excludeRequirement, "Must contain either CS220 OR CS223, but not CS223");
        acceptingRequirementGateway.addRequirement(storedRequirement);
        interactor = new ViewCourseListInteractor(request, acceptingRequirementGateway, providingCourseGateway);
        response = interactor.execute();
        assertThat(response, is(Response.success(Set.of(course1))));
        assertThat(interactor.getRequirementId(), is(requirementId));
    }
}
