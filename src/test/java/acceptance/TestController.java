package acceptance;

import majorPlanner.Controller;
import majorPlanner.authorizer.GatewayBackedAuthorizer;
import majorPlanner.entity.*;
import majorPlanner.gateway.Gateway;
import majorPlanner.interactor.GatewayBackedInteractorFactory;
import majorPlanner.interactor.Interactor;
import majorPlanner.request.Request;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import webserver.MemoryGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestController extends Controller {
    private static TestController instance;
    private Map<String, Session> usersToSessions = new HashMap<>();
    private Map<String, User> userNamesToUsers = new HashMap<>();
    private Map<String, Course> nameToCourse = new HashMap<>();
    private Map<String, Schedule> nameToSchedule = new HashMap<>();
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

    public void defineCourse(String name, String id) {
        Course course = new Course(id);
        gateway.addCourse(course);
        nameToCourse.put(name, course);
    }

    public Response addCourse(String user, String course, String scheduleName, Period period, Year year) {
        return addCourse(user, course, nameToSchedule.get(scheduleName).getID(), period.toString(), year.toString());
    }

    public Response addCourse(String user, String course, int scheduleId, String termString, String yearString) {
        Response response = super.addCourse(usersToSessions.get(user), course, scheduleId, termString, yearString);
        responses.add(response);
        return response;
    }

    public void defineSchedule(String name, String owner) {
        Schedule schedule = new Schedule(userNamesToUsers.get(owner), name, "");
        gateway.addSchedule(schedule);
        nameToSchedule.put(name, schedule);
    }

    public boolean scheduleHasCourse(String scheduleName, String courseName, String term, String year) {
        Schedule schedule = nameToSchedule.get(scheduleName);
        Course course = nameToCourse.get(courseName);
        return scheduleContainsAddedCourse(schedule, course, Period.valueOf(term), Year.valueOf(year));
    }

    private boolean scheduleContainsAddedCourse(Schedule schedule, Course course, Period period, Year year) {
        CalendarTerm t = new CalendarTerm(period, year);
        for (AddedCourse ac: schedule.getAddedCourses()) {
            if (ac.getCourse().getId().equals(course.getId()) && ac.getTerm().equals(t)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Response execute(Request request, Interactor interactor) {
        Response response = super.execute(request, interactor);
        responses.add(response);
        return response;
    }
}
