package mock;

import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;
import majorPlanner.gateway.ScheduleGateway;

import java.util.List;

public class AcceptingScheduleGateway implements ScheduleGateway {

    private int requestedScheduleID;

    @Override
    public void addSchedule(Schedule schedule) {

    }

    @Override
    public Schedule getSchedule(int scheduleID) {
        User dummyUser = new User(null, null);
        String description = "fake course";
        String name = "Uncle Bob";
        requestedScheduleID = scheduleID;
        return new Schedule(dummyUser, name, description);
    }

    @Override
    public List<Schedule> getSchedulesFromOwnerId(String ownerId) {
        return null;
    }

    @Override
    public void save() {

    }

    public int getRequestedScheduleID() { return requestedScheduleID; }
}
