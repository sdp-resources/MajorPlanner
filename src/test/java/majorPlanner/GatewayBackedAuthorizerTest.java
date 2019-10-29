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
import org.junit.experimental.theories.suppliers.TestedOn;

public class GatewayBackedAuthorizerTest {
    @Test
    public void userInSessionMatchesOwner()
    {
        Response response = authorize("dantgeo", "dantgeo", Role.User);
        Assert.assertFalse(response.containsError());
    }

    @Test
    public void userInSessionDoesNotMatchOwner(){
        Response response = authorize("dantgeo", "marsht", Role.User);
        Assert.assertTrue(response.containsError());
        Assert.assertEquals(GatewayBackedAuthorizer.USER_MISMATCH_MESSAGE, ((ErrorResponse)response).getError());
    }

    @Test
    public void adminInSessionIgnoresRequirements(){
        Response response = authorize("dantgeo", "marsht", Role.Admin);
        Assert.assertFalse(response.containsError());
    }

    private Response authorize(String scheduleOwner, String sessionUser, Role role) {
        CreateScheduleRequest request = new CreateScheduleRequest(scheduleOwner);
        request.setSession(new Session(null, sessionUser, role));
        Authorizer authorizer = new GatewayBackedAuthorizer();
        return authorizer.authorize(request);
    }
}
