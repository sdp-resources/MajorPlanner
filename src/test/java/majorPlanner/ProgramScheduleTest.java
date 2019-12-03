package majorPlanner;

import org.junit.Test;
import majorPlanner.entity.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProgramScheduleTest {

    @Test
    public void addProgramToSchedule(){
        User owner = new User("George", Role.User);
        Schedule schedule = new Schedule(owner, "My Schedule", "My description");
        List<StoredRequirement> storedReqs = new ArrayList<>();
        Program program = new Program("Program name", "Program description", storedReqs);
        schedule.addProgram(program);
        assertThat(schedule.compareScheduleToProgram().isEmpty(), is(true));
    }

}
