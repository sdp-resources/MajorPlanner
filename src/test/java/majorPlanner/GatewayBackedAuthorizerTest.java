package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.authorizer.GatewayBackedAuthorizer;
import majorPlanner.entity.Role;
import majorPlanner.entity.Term;
import majorPlanner.entity.Year;
import majorPlanner.request.AddCourseRequest;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import org.jetbrains.annotations.NotNull;
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
    public void userInSessionMatchesOwner()
    {
        Response response = authorize("dantgeo", Role.User, getCreateScheduleRequest());
        assertErrorFree(response);
    }

    @Test
    public void userInSessionDoesNotMatchOwner(){
        Response response = authorize("marsht", Role.User, getCreateScheduleRequest());
        assertContainsError(response);
        assertMessageEquals(GatewayBackedAuthorizer.USER_MISMATCH_MESSAGE, (ErrorResponse) response);
    }

    @Test
    public void adminInSessionIgnoresRequirements(){
        Response response = authorize("marsht", Role.Admin, getCreateScheduleRequest());
        assertErrorFree(response);
    }

    private CreateScheduleRequest getCreateScheduleRequest() {
        return new CreateScheduleRequest("dantgeo", "", "");
    }

    @Test
    public void scheduleDoesNotExist(){
        AddCourseRequest request = new AddCourseRequest("CS220", 0, Term.Winter, Year.Freshman);
        Response response = authorize("marsht", Role.User, request);
        assertContainsError(response);
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
        request.setSession(new Session(null, sessionUser, role));
        Authorizer authorizer = new GatewayBackedAuthorizer(gateway);
        return authorizer.authorize(request);
    }
}
