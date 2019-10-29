package majorPlanner.gateway;

import majorPlanner.entity.Schedule;

import java.util.List;
import java.util.function.Function;

public interface ScheduleGateway {
    void addSchedule(Schedule schedule);
    Schedule getSchedule(int id);
    List<Schedule> getSchedulesFromOwnerId(String ownerId);
    void save();
}
