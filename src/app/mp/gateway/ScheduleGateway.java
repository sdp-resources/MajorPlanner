package mp.gateway;

import mp.entity.Schedule;

import java.util.List;

public interface ScheduleGateway {
    void addSchedule(Schedule schedule);
    Schedule getSchedule(int id);
    List<Schedule> getSchedules(String owner);
}
