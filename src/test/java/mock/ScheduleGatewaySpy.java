package mock;

import majorPlanner.entity.Schedule;
import majorPlanner.gateway.ScheduleGateway;

import java.util.List;

public class ScheduleGatewaySpy implements ScheduleGateway {

    private Schedule providedSchedule;

    @Override
    public void addSchedule(Schedule schedule) {
        providedSchedule = schedule;
    }

    @Override
    public Schedule getSchedule(int id) {
        return null;
    }

    @Override
    public List<Schedule> getSchedules(String owner) {
        return null;
    }

    public Schedule getProvidedSchedule() {
        return providedSchedule;
    }
}
