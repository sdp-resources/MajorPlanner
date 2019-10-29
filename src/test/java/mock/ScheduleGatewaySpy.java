package mock;

import majorPlanner.entity.Schedule;
import majorPlanner.gateway.ScheduleGateway;

import java.util.List;

public class ScheduleGatewaySpy implements ScheduleGateway {

    private Schedule providedSchedule;
    private Boolean saveCalled = false;

    @Override
    public void addSchedule(Schedule schedule) {
        providedSchedule = schedule;
    }

    @Override
    public Schedule getSchedule(int id) {
        return null;
    }

    @Override
    public List<Schedule> getSchedulesFromOwnerId(String owner) {
        return null;
    }

    @Override
    public void save() {
        saveCalled = true;
    }

    public Schedule getProvidedSchedule() {
        return providedSchedule;
    }

    public Boolean saveCalled() { return saveCalled; }
}
