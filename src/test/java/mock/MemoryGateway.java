package mock;

import javafx.scene.Scene;
import mp.entity.Schedule;
import mp.gateway.Gateway;
import mp.session.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryGateway implements Gateway {
    private Map<String, Session> sessionMap;
    private Map<Integer, Schedule> scheduleMap;

    public MemoryGateway()
    {
        sessionMap = new HashMap<String, Session>();
        scheduleMap = new HashMap<Integer, Schedule>();
    }

    @Override
    public void addSession(Session session) {
        sessionMap.put(session.token, session);
    }

    @Override
    public Session getSession(String token) {
        return sessionMap.get(token);
    }

    @Override
    public void addSchedule(Schedule schedule) {
        scheduleMap.put(schedule.getId(), schedule);
    }

    @Override
    public Schedule getSchedule(int id) {
        return scheduleMap.get(id);
    }

    @Override
    public List<Schedule> getSchedules(String owner) {
        List<Schedule> schedules = new ArrayList<>();
        for (Schedule s : scheduleMap.values()) {
            if(s.getOwner().equals(owner))
            {
                schedules.add(s);
            }
        }
        return schedules;
    }
}
