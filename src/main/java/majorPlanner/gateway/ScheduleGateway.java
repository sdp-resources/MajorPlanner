package majorPlanner.gateway;

import majorPlanner.entity.Schedule;
import majorPlanner.entity.Term;

import java.util.List;
import java.util.function.Function;

public interface ScheduleGateway {
    void addSchedule(Schedule schedule);
    Schedule getSchedule(int id);
    List<Schedule> getSchedulesFromOwnerId(String ownerId);
    void save();
}
