package mock;

import majorPlanner.entity.Schedule;
import majorPlanner.gateway.ScheduleGateway;

import java.util.List;

public class ScheduleGatewaySpy implements ScheduleGateway {

    private Schedule providedSchedule;
    private Boolean saveCalled = false;
    private int requestedScheduleID;

    @Override
    public void addSchedule(Schedule schedule) {
        providedSchedule = schedule;
    }

    @Override
    public Schedule getSchedule(int scheduleID) {
        setRequestedScheduleID(scheduleID);
        return null;
    }

    protected void setRequestedScheduleID(int scheduleID) {
        requestedScheduleID = scheduleID;
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

    public int getRequestedScheduleID() { return requestedScheduleID; }
}
