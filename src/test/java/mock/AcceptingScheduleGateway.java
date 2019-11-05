package mock;

import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;

import java.util.List;

public class AcceptingScheduleGateway extends ScheduleGatewaySpy {

    private final User dummyUser = new User(null, null);
    public final Schedule returnedSchedule = new Schedule(dummyUser, "Uncle Bob", "fake course");

    @Override
    public Schedule getSchedule(int scheduleID) {
        super.getSchedule(scheduleID);
        returnedSchedule.setID(scheduleID);
        return returnedSchedule;
    }

    @Override
    public List<Schedule> getSchedulesFromOwnerId(String ownerId) {
        return null;
    }


}
