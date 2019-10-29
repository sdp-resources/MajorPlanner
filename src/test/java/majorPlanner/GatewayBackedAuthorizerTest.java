package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.authorizer.GatewayBackedAuthorizer;
import majorPlanner.entity.Role;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.response.ErrorResponse;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import org.junit.Assert;
import org.junit.Test;

public class GatewayBackedAuthorizerTest {
    @Test
    public void userInSessionMatchesOwner()
    {
        Response response = authorize("dantgeo", "dantgeo", Role.User);
        assertErrorFree(response);
    }

    @Test
    public void userInSessionDoesNotMatchOwner(){
        Response response = authorize("dantgeo", "marsht", Role.User);
        assertContainsError(response);
        assertMessageEquals(GatewayBackedAuthorizer.USER_MISMATCH_MESSAGE, (ErrorResponse) response);
    }

    @Test
    public void adminInSessionIgnoresRequirements(){
        Response response = authorize("dantgeo", "marsht", Role.Admin);
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

    private Response authorize(String scheduleOwner, String sessionUser, Role role) {
        CreateScheduleRequest request = new CreateScheduleRequest(scheduleOwner);
        request.setSession(new Session(null, sessionUser, role));
        Authorizer authorizer = new GatewayBackedAuthorizer();
        return authorizer.authorize(request);
    }
}
