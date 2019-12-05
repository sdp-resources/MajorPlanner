package majorPlanner;

import org.junit.Before;
import org.junit.Test;
import majorPlanner.entity.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProgramScheduleTest {

    private StoredRequirement storedReq1;
    private Requirement req1;
    private Course course1;
    private StoredRequirement storedReq2;
    private Requirement req2;
    private Course course2;
    private StoredRequirement storedReq3;
    private Requirement req3;

    @Before
    public void setUp() throws Exception {
        req1 = new CourseRequirement("CS220");
        storedReq1 = new StoredRequirement(req1, "stored req 1");
        course1 = new Course("CS220");
        req2 = new CourseRequirement("CS223");
        storedReq2 = new StoredRequirement(req2, "stored req 2");
        course2 = new Course("CS223");
        req3 = new CourseRequirement("MAT217");
        storedReq3 = new StoredRequirement(req3, "stored req 3");

    }

    @Test
    public void addProgramToSchedule(){
        User owner = new User("George", Role.User);
        Schedule schedule = new Schedule(owner, "My Schedule", "My description");
        List<StoredRequirement> storedReqs = new ArrayList<>();
        Program program = new Program("Program name", "Program description", storedReqs);
        schedule.addProgram(program);
        assertThat(schedule.compareScheduleToProgram().isEmpty(), is(true));
    }

    @Test
    public void compareProgramToSchedule_UnmatchedResult(){
        User owner = new User("George", Role.User);
        Schedule schedule = new Schedule(owner, "My Schedule", "My description");
        List<StoredRequirement> storedReqs = new ArrayList<>();
        storedReqs.add(storedReq1);
        Program program = new Program("Program name", "Program description", storedReqs);
        schedule.addProgram(program);
        assertThat(schedule.compareScheduleToProgram(), is(List.of(new UnmatchedResult(storedReq1))));
    }

    @Test
    public void compareProgramToSchedule_ResultHasMatch(){
        User owner = new User("George", Role.User);
        Schedule schedule = new Schedule(owner, "My Schedule", "My description");
        schedule.addCourse(course1, "Fall", "Freshman");
        List<StoredRequirement> storedReqs = new ArrayList<>();
        storedReqs.add(storedReq1);
        Program program = new Program("Program name", "Program description", storedReqs);
        schedule.addProgram(program);
        assertThat(schedule.compareScheduleToProgram(), is(List.of(new MatchedResult(storedReq1, course1))));
        assertThat(schedule.containsCourse(course1), is(true));
    }

    @Test
    public void compareProgramToSchedule_ResultCombinationOfMatchedAndUnmatchedResults(){

    }


}
