package majorPlanner;

import majorPlanner.entity.Schedule;
import majorPlanner.interactor.RemoveCourseFromScheduleInteractor;
import majorPlanner.request.RemoveCourseFromScheduleRequest;
import majorPlanner.response.Response;
import mock.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class RemoveCourseFromScheduleTest {

    private static final String COURSE_ID = "Course1";
    private static final int SCHEDULE_ID = 1234;
    private AcceptingCourseGateway acceptCourseGateway;
    private RejectingCourseGateway rejectCourseGateway;
    private ScheduleGatewaySpy rejectScheduleGateway;
    private RemoveCourseFromScheduleInteractor courseInteractor;
    private RemoveCourseFromScheduleRequest request;
    private Response response;

    @Before
    public void setup(){
        rejectCourseGateway = new RejectingCourseGateway();
        acceptCourseGateway = new AcceptingCourseGateway();
        rejectScheduleGateway = new RejectingScheduleGateway();
    }

    @Test
    public void removeCourseThatDoesNotExist(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(rejectCourseGateway, null);
        request = new RemoveCourseFromScheduleRequest(COURSE_ID, SCHEDULE_ID);
        response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is(true));
        assertThat(rejectCourseGateway.getRequestedCourseID(), is(request.courseID));
    }

    @Test
    public void deleteCourseFromScheduleThatDoesNotExist(){
        courseInteractor = new RemoveCourseFromScheduleInteractor(acceptCourseGateway, rejectScheduleGateway);
        request = new RemoveCourseFromScheduleRequest(COURSE_ID, SCHEDULE_ID);
        response = courseInteractor.executeRequest(request);
        assertThat(response.containsError(), is(true));
        assertThat(rejectScheduleGateway.getRequestedScheduleID(), is(request.scheduleID));
    }

    @Test
    public void whenDeletingCourseFromEmptyScheduleGetAnError(){
        AcceptingScheduleGateway acceptScheduleGateway = new AcceptingScheduleGateway();
        courseInteractor = new RemoveCourseFromScheduleInteractor(acceptCourseGateway, acceptScheduleGateway);
        Schedule schedule = acceptScheduleGateway.returnedSchedule;
        request = new RemoveCourseFromScheduleRequest(COURSE_ID, SCHEDULE_ID);
        response = courseInteractor.executeRequest(request);
        assertThat(schedule.getID(), is(SCHEDULE_ID));
        assertThat(response.containsError(), is(true));
    }
}
