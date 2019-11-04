package acceptance;

import majorPlanner.Controller;
import majorPlanner.authorizer.GatewayBackedAuthorizer;
import majorPlanner.entity.*;
import majorPlanner.gateway.Gateway;
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
        super(gateway, new GatewayBackedAuthorizer(gateway));
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

    public void defineUser(String name, String roleName)
    {
        User user = new User(name, Role.valueOf(roleName));
        gateway.addUser(user);
        userNamesToUsers.put(name, user);
    }

    public void defineCourse(String name, String id) {
        Course course = new Course(id);
        gateway.addCourse(course);
        nameToCourse.put(name, course);
    }

    public void defineSession(String name)
    {
        usersToSessions.put(name, new Session(null, name, userNamesToUsers.get(name).getRole()));
    }

    public Response createSchedule(String sessionUser, String ownerUser, String scheduleName, String description)
    {
        Response response = super.createSchedule(usersToSessions.get(sessionUser), ownerUser, scheduleName, description);
        responses.add(response);
        return response;
    }

    public Response addCourse(String user, String course, String scheduleName, Term term, Year year) {
        return addCourse(user, course, nameToSchedule.get(scheduleName).getID(), term, year);
    }

    public Response addCourse(String user, String course, int scheduleId, Term term, Year year) {
        Response response = super.addCourse(usersToSessions.get(user), course, scheduleId, term, year);
        responses.add(response);
        return response;
    }

    public void defineSchedule(String name, String owner) {
        Schedule schedule = new Schedule(userNamesToUsers.get(owner), name, "");
        gateway.addSchedule(schedule);
        nameToSchedule.put(name, schedule);
    }

    public int getScheduleId(String name) {
        return nameToSchedule.get(name).getID();
    }

    public boolean scheduleHasCourse(String scheduleName, String courseName, String term, String year) {
        Schedule schedule = nameToSchedule.get(scheduleName);
        Course course = nameToCourse.get(courseName);
        return scheduleContainsAddedCourse(schedule, course,Term.valueOf(term), Year.valueOf(year));
    }

    private boolean scheduleContainsAddedCourse(Schedule schedule, Course course, Term term, Year year) {
        for (AddedCourse ac: schedule.getAddedCourses()) {
            if (ac.getCourse().getId().equals(course.getId()) && ac.getTerm().equals(term) && ac.getYear().equals(year)) {
                return true;
            }
        }
        return false;
    }
}
