package majorPlanner;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import majorPlanner.entity.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProgramScheduleTest {

    private StoredRequirement reqCS220;
    private Course cs220;
    private StoredRequirement reqCS223;
    private Course cs223;
    private StoredRequirement reqMAT217;
    private Course cs321;
    private StoredRequirement reqCS220_other;
    private Schedule schedule;
    private List<StoredRequirement> storedReqs;

    @Before
    public void setUp() throws Exception {
        User owner = new User("George", Role.User);
        schedule = new Schedule(owner, "My Schedule", "My description");
        storedReqs = new ArrayList<>();
        Program program = new Program("Program name", "Program description", storedReqs);
        schedule.addProgram(program);

        reqCS220 = makeRequirement("CS220", "stored req 1");
        reqCS220_other = makeRequirement("CS220", "stored req 4");
        reqCS223 = makeRequirement("CS223", "stored req 2");
        reqMAT217 = makeRequirement("MAT217", "stored req 3");
        cs220 = new Course("CS220");
        cs223 = new Course("CS223");
        cs321 =  new Course("CS321");
    }

    @Test
    public void addProgramToSchedule(){
        assertThat(schedule.compareScheduleToProgram().isEmpty(), is(true));
    }

    @Test
    public void compareProgramToSchedule_UnmatchedResult(){
        storedReqs.add(reqCS220);
        assertThat(schedule.compareScheduleToProgram(), is(List.of(new UnmatchedResult(reqCS220))));
    }

    @Test
    public void compareProgramToSchedule_ResultHasMatch(){
        schedule.addCourse(cs220, "Fall", "Freshman");
        storedReqs.add(reqCS220);
        assertThat(schedule.compareScheduleToProgram(), is(List.of(new MatchedResult(reqCS220, cs220))));
        assertThat(schedule.containsCourse(cs220), is(true));
    }

    @Test
    public void compareProgramToSchedule_ResultCombinationOfMatchedAndUnmatchedResults(){
        schedule.addCourse(cs220, "Fall", "Freshman");
        schedule.addCourse(cs223, "Fall", "Freshman");
        schedule.addCourse(cs321, "Fall", "Freshman");
        storedReqs.addAll(List.of(reqCS220, reqCS223, reqMAT217));
        assertThat(schedule.compareScheduleToProgram(), is(List.of(new MatchedResult(reqCS220, cs220), new MatchedResult(reqCS223, cs223), new UnmatchedResult(reqMAT217))));
    }

    @Test
    public void compareProgramToSchedule_OnlyOneCourseToMatchRequirement(){
        schedule.addCourse(cs220, "Fall", "Freshman");
        schedule.addCourse(cs321, "Fall", "Freshman");
        storedReqs.addAll(List.of(reqCS220, reqMAT217, reqCS220_other));
        assertThat(schedule.compareScheduleToProgram(), is(List.of(new MatchedResult(reqCS220, cs220), new UnmatchedResult(reqMAT217), new UnmatchedResult(reqCS220_other))));
    }

    @NotNull
    private StoredRequirement makeRequirement(String courseID, String description) {
        return new StoredRequirement(new CourseRequirement(courseID), description);
    }
}
