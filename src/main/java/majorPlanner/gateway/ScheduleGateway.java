package majorPlanner.gateway;

import majorPlanner.entity.Schedule;

import java.util.List;

public interface ScheduleGateway {
    void addSchedule(Schedule schedule);
    Schedule getSchedule(int id);
    List<Schedule> getSchedules(String owner);
}
