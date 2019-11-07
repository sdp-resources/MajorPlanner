package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.authorizer.GatewayBackedAuthorizer;
import majorPlanner.entity.*;
import majorPlanner.request.AddCourseRequest;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import webserver.MemoryGateway;

public class GatewayBackedAuthorizerTest {

    private MemoryGateway gateway;

    @Before
    public void setUp(){
        gateway = new MemoryGateway();
    }

    @Test
    public void createScheduleUserInSessionMatchesOwner()
    {
        Response response = authorize("dantgeo", Role.User, getCreateScheduleRequest());
        assertErrorFree(response);
    }

    @Test
    public void createScheduleUserInSessionDoesNotMatchOwner(){
        Response response = authorize("marsht", Role.User, getCreateScheduleRequest());
        assertContainsError(response);
        assertMessageEquals(GatewayBackedAuthorizer.USER_MISMATCH_MESSAGE, (ErrorResponse) response);
    }

    @Test
    public void createScheduleAdminInSessionIgnoresRequirements(){
        Response response = authorize("marsht", Role.Admin, getCreateScheduleRequest());
        assertErrorFree(response);
    }

    private CreateScheduleRequest getCreateScheduleRequest() {
        return new CreateScheduleRequest("dantgeo", "", "");
    }

    @Test
    public void addCourseScheduleDoesNotExist(){
        AddCourseRequest request = new AddCourseRequest("CS220", 0, Term.Winter, Year.Freshman);
        gateway.addCourse(new Course("CS220"));
        Response response = authorize("marsht", Role.User, request);
        assertContainsError(response);
        assertMessageEquals(GatewayBackedAuthorizer.SCHEDULE_DOES_NOT_EXIST_MESSAGE, (ErrorResponse) response);
    }

    @Test
    public void addCourseCourseDoesNotExist()
    {
        AddCourseRequest request = new AddCourseRequest("CS220", 23, Term.Winter, Year.Freshman);
        Schedule s = new Schedule(new User("marsht", Role.User), "MySchedule", "description");
        s.setID(23);
        gateway.addSchedule(s);
        Response response = authorize("marsht", Role.User, request);
        assertContainsError(response);
        assertMessageEquals(GatewayBackedAuthorizer.COURSE_DOES_NOT_EXIST_MESSAGE, (ErrorResponse) response);
    }

    @Test
    public void addCourseUserInSessionDoesNotMatchOwner()
    {
        AddCourseRequest request = new AddCourseRequest("CS223", 43, Term.Fall, Year.Junior);
        Schedule s = new Schedule(new User("smithma", Role.User), "MahSchedule", "jfdhgsjhlfdg");
        s.setID(43);
        gateway.addSchedule(s);
        gateway.addCourse(new Course("CS223"));
        Response response = authorize("givensb22", Role.User, request);
        assertContainsError(response);
        assertMessageEquals(GatewayBackedAuthorizer.USER_MISMATCH_MESSAGE, (ErrorResponse) response);
    }

    @Test
    public void addCourseUserInSessionMatchesOwner()
    {
        AddCourseRequest request = new AddCourseRequest("CS223", 43, Term.Fall, Year.Junior);
        Schedule s = new Schedule(new User("smithma", Role.User), "MahSchedule", "jfdhgsjhlfdg");
        s.setID(43);
        gateway.addSchedule(s);
        gateway.addCourse(new Course("CS223"));
        Response response = authorize("smithma", Role.User, request);
        assertErrorFree(response);
    }

    private void assertErrorFree(Response response) {
        Assert.assertFalse(response.containsError());
    }

    private void assertContainsError(Response response) {
        Assert.assertTrue(response.containsError());
    }

    private void assertMessageEquals(String message, ErrorResponse response) {
        Assert.assertEquals(message, response.getError());
    }

    private Response authorize(String sessionUser, Role role, Request request) {
        request.setSession(new Session(null, new User(sessionUser, role)));
        Authorizer authorizer = new GatewayBackedAuthorizer(gateway);
        return authorizer.authorize(request);
    }
}
