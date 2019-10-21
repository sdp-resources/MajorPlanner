package acceptance;

import majorPlanner.Controller;
import majorPlanner.gateway.Gateway;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import mock.MemoryGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestController extends Controller {
    private static TestController instance;
    private Map<String, Session> usersToSessions;
    public final Gateway gateway;
    public List<Response> responses;

    public TestController(Gateway gateway)
    {
        super(gateway);
        this.gateway = gateway;
        responses = new ArrayList<>();
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
        usersToSessions.put(name, new Session(null, name, role));
    }

    public Response createSchedule(String sessionUser, String ownerUser, String scheduleName, String description)
    {
        Response response = super.createSchedule(usersToSessions.get(sessionUser), ownerUser, scheduleName, description);
        responses.add(response);
        return response;
    }
}
