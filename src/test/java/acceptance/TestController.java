package acceptance;

import majorPlanner.Controller;
import majorPlanner.authorizer.Authorizer;
import majorPlanner.entity.User;
import majorPlanner.gateway.Gateway;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import mock.AcceptingAuthorizer;
import mock.MemoryGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestController extends Controller {
    private static TestController instance;
    private Map<String, Session> usersToSessions;
    private Map<String, String> usersToRoles;
    public final Gateway gateway;
    public List<Response> responses;

    public TestController(Gateway gateway)
    {
        super(gateway, new AcceptingAuthorizer());
        this.gateway = gateway;
        responses = new ArrayList<>();
        usersToRoles = new HashMap<>();
        usersToSessions = new HashMap<>();
    }

    public static void resetInstance()
    {
        instance = new TestController(new MemoryGateway());
    }

    public static TestController getInstance()
    {
        return instance;
    }

    public void defineUser(String name, String role)
    {
        gateway.addUser(new User(name, User.Role.valueOf(role)));
        usersToRoles.put(name, role);
    }

    public void defineSession(String name)
    {
        usersToSessions.put(name, new Session(null, name, usersToRoles.get(name)));
    }

    public Response createSchedule(String sessionUser, String ownerUser, String scheduleName, String description)
    {
        Response response = super.createSchedule(usersToSessions.get(sessionUser), ownerUser, scheduleName, description);
        responses.add(response);
        return response;
    }
}
