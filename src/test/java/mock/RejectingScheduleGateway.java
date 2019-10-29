package mock;

import majorPlanner.entity.Schedule;
import majorPlanner.gateway.ScheduleGateway;

import java.util.List;

public class RejectingScheduleGateway implements ScheduleGateway {

    private int requestedScheduleID;

    @Override
    public void addSchedule(Schedule schedule) {

    }

    @Override
    public Schedule getSchedule(int id) {
        requestedScheduleID = id;
        return null;
    }

    @Override
    public List<Schedule> getSchedulesFromOwnerId(String owner) {
        return null;
    }

    @Override
    public void save() {

    }

    public int getRequestedScheduleID () { return requestedScheduleID;}
}
