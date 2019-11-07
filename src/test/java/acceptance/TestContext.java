package acceptance;

import io.cucumber.java.Before;
import majorPlanner.entity.Schedule;
import majorPlanner.entity.User;
import majorPlanner.session.Session;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    public static Map<String, Object> data;

    public static void put(String name, Object o) {
        data.put(name, o);
    }

    public static Session getSession(String name) {
        return (Session) data.get(name);
    }

    public static int getScheduleId(String name) {
        return (int) data.get(name);
    }

    public static Schedule getSchedule(String name) {
        return (Schedule) data.get(name);
    }

    public static User getUser(String name) {
        return getSession(name).getUser();
    }

    @Before
    public void reset()
    {
        data = new HashMap<>();
    }

    public TestContext()
    {
        System.out.println("Basic steps constructor called");
    }
}
