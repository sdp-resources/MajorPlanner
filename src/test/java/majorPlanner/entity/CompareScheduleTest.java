package majorPlanner.entity;

import majorPlanner.interactor.CompareScheduleInteractor;
import majorPlanner.request.CompareScheduleRequest;
import majorPlanner.response.Response;
import mock.AcceptingScheduleGateway;
import mock.RejectingScheduleGateway;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompareScheduleTest {
    private static final int SCHEDULE_ID = 1234;
    private CompareScheduleInteractor compareScheduleInteractor;
    private CompareScheduleRequest request;
    private RejectingScheduleGateway rejectingScheduleGateway;
    private AcceptingScheduleGateway acceptingScheduleGateway;
    private Response response;
    private Course cs220;
    private Program program;

    @Before
    public void setUp() {
        request = new CompareScheduleRequest(SCHEDULE_ID);
        rejectingScheduleGateway = new RejectingScheduleGateway();
        acceptingScheduleGateway = new AcceptingScheduleGateway();
        cs220 = new Course("CS220");
        program = new Program("Program name", "Program description",
                List.of(new StoredRequirement(new CourseRequirement("CS220"), "stored req 1")));
    }

    @Test
    public void noScheduleExists_ErrorResponseReturned() {
        compareScheduleInteractor = new CompareScheduleInteractor(request, rejectingScheduleGateway);
        response = compareScheduleInteractor.execute();
        assertThat(response, is(Response.invalidSchedule()));
        assertThat(rejectingScheduleGateway.getRequestedScheduleID(), is(request.getScheduleId()));
    }

    @Test
    public void scheduleExists_CompareSchedule() {
        compareScheduleInteractor = new CompareScheduleInteractor(request, acceptingScheduleGateway);
        Schedule schedule = acceptingScheduleGateway.returnedSchedule;
        schedule.addCourse(cs220, "Fall", "Freshman");
        schedule.addProgram(program);
        response = compareScheduleInteractor.execute();
        assertThat(response, is(Response.success(schedule.compareScheduleToProgram())));
    }
}
