package mock;

import majorPlanner.entity.Course;
import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;
import majorPlanner.gateway.Gateway;
import majorPlanner.session.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryGateway implements Gateway {
    private Map<Integer, Schedule> scheduleMap;
    private Map<String, User> userMap;

    public MemoryGateway()
    {
        scheduleMap = new HashMap<>();
        userMap = new HashMap<>();
    }

    @Override
    public void addSchedule(Schedule schedule) {
        scheduleMap.put(schedule.getID(), schedule);
    }

    @Override
    public Schedule getSchedule(int id) {
        return scheduleMap.get(id);
    }

    @Override
    public List<Schedule> getSchedulesFromOwnerId(String ownerID) {
        List<Schedule> schedules = new ArrayList<>();
        for (Schedule s : scheduleMap.values()) {
            if(s.getOwner().getUserID().equals(ownerID))
            {
                schedules.add(s);
            }
        }
        return schedules;
    }

    @Override
    public void save() {

    }

    @Override
    public User getUser(String userID) {
        return userMap.get(userID);
    }

    @Override
    public void addUser(User user) {
        userMap.put(user.getUserID(), user);
    }

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public Course getCourse(String courseID) {
        return null;
    }
}
