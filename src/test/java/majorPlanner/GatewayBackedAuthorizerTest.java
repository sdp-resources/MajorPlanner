package majorPlanner;

import majorPlanner.authorizer.Authorizer;
import majorPlanner.authorizer.GatewayBackedAuthorizer;
import majorPlanner.entity.*;
import majorPlanner.request.AddCourseRequest;
import majorPlanner.request.CreateScheduleRequest;
import majorPlanner.request.Request;
import majorPlanner.response.Response;
import majorPlanner.session.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import webserver.MemoryGateway;

import static org.hamcrest.CoreMatchers.is;

public class GatewayBackedAuthorizerTest {

    public static final int SCHEDULE_ID = 43;
    public static final String COURSE_ID = "CS223";
    private MemoryGateway gateway;
    private Schedule schedule;
    private Course course;
    private AddCourseRequest addCourseRequest;
    private CreateScheduleRequest createScheduleRequest;

    @Before
    public void setUp(){
        gateway = new MemoryGateway();
        schedule = new Schedule(new User("smithma", Role.User), "MahSchedule", "jfdhgsjhlfdg");
        schedule.setID(SCHEDULE_ID);
        course = new Course(COURSE_ID);
        addCourseRequest = new AddCourseRequest(COURSE_ID, SCHEDULE_ID, "Winter", "Freshman");
        createScheduleRequest = new CreateScheduleRequest("dantgeo", "", "");
    }

    @Test
    public void createScheduleUserInSessionDoesNotMatchOwner(){
        Response response = authorize("marsht", Role.User, createScheduleRequest);
        Assert.assertEquals(Response.userMismatch(), response);
    }

    @Test
    public void createScheduleAdminInSessionIgnoresRequirements(){
        Response response = authorize("marsht", Role.Admin, createScheduleRequest);
        Assert.assertThat(response, is(Response.ok()));
    }

    @Test
    public void addCourseScheduleDoesNotExist(){
        gateway.addCourse(course);
        Response response = authorize("marsht", Role.User, addCourseRequest);
        Assert.assertEquals(Response.nonExistentSchedule(), response);
    }

    @Test
    public void addCourseCourseDoesNotExist()
    {
        gateway.addSchedule(schedule);
        Response response = authorize("marsht", Role.User, addCourseRequest);
        Assert.assertEquals(Response.nonExistentCourse(), response);
    }

    @Test
    public void addCourseUserInSessionDoesNotMatchOwner()
    {
        gateway.addSchedule(schedule);
        gateway.addCourse(course);
        Response response = authorize("givensb22", Role.User, addCourseRequest);
        Assert.assertEquals(Response.userMismatch(), response);
    }

    @Test
    public void addCourseUserInSessionMatchesOwner()
    {
        gateway.addSchedule(schedule);
        gateway.addCourse(course);
        Response response = authorize("smithma", Role.User, addCourseRequest);
        Assert.assertThat(response, is(Response.ok()));
    }

    private Response authorize(String sessionUser, Role role, Request request) {
        request.setSession(new Session(null, new User(sessionUser, role)));
        Authorizer authorizer = new GatewayBackedAuthorizer(gateway);
        return authorizer.authorize(request);
    }
}
