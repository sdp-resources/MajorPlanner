package acceptance;

import majorPlanner.Controller;
import majorPlanner.authorizer.GatewayBackedAuthorizer;
import majorPlanner.entity.Course;
import majorPlanner.entity.StoredRequirement;
import majorPlanner.entity.User;
import majorPlanner.gateway.Gateway;
import majorPlanner.interactor.GatewayBackedInteractorFactory;
import majorPlanner.interactor.Interactor;
import majorPlanner.request.Request;
import majorPlanner.response.Response;
import webserver.MemoryGateway;

import java.util.ArrayList;
import java.util.List;

public class TestController extends Controller {
    private static TestController instance;
    public final Gateway gateway;
    public List<Response> responses = new ArrayList<>();

    public TestController(Gateway gateway)
    {
        super(new GatewayBackedAuthorizer(gateway), new GatewayBackedInteractorFactory(gateway));
        this.gateway = gateway;
    }

    public static void resetInstance()
    {
        instance = new TestController(new MemoryGateway());
    }

    public static TestController getInstance()
    {
        return instance;
    }

    public void addUser(User user)
    {
        gateway.addUser(user);
    }

    public void defineCourse(Course course) {
        gateway.addCourse(course);
    }

    public void defineRequirement(StoredRequirement storedRequirement) {
        gateway.addRequirement(storedRequirement);
    }

    @Override
    protected Response execute(Request request, Interactor interactor) {
        Response response = super.execute(request, interactor);
        responses.add(response);
        return response;
    }
}
