package mock;

import majorPlanner.entity.Schedule;
import majorPlanner.gateway.ScheduleGateway;

import java.util.List;

public class ScheduleGatewayDummy implements ScheduleGateway {
    @Override
    public void addSchedule(Schedule schedule) {

    }

    @Override
    public Schedule getSchedule(int id) {
        return null;
    }

    @Override
    public List<Schedule> getSchedules(String owner) {
        return null;
    }
}
