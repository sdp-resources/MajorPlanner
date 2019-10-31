package mock;

import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;

import java.util.List;

public class AcceptingScheduleGateway extends ScheduleGatewaySpy {

    private final User dummyUser = new User(null, null);
    public final Schedule providedSchedule = new Schedule(dummyUser, "Uncle Bob", "fake course");

    @Override
    public Schedule getSchedule(int scheduleID) {
        super.getSchedule(scheduleID);
        providedSchedule.setID(scheduleID);
        return providedSchedule;
    }

    @Override
    public List<Schedule> getSchedulesFromOwnerId(String ownerId) {
        return null;
    }


}
