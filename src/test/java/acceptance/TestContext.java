package acceptance;

import io.cucumber.java.Before;
import majorPlanner.entity.*;
import majorPlanner.session.Session;
import org.hamcrest.Matcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestContext {
    public static Map<String, Object> data;

    public static void put(String name, Object o) {
        data.put(name, o);
    }

    public static Session getSession(String name) {
        return (Session) data.get(name);
    }

    public static int getId(String name) {
        return (int) data.get(name);
    }

    public static Schedule getSchedule(String name) {
        return (Schedule) data.get(name);
    }

    public static User getUser(String name) {
        return getSession(name).getUser();
    }

    public static Set<Course> getCourseList(String name) {
        return (Set<Course>) data.get(name);
    }

    public static Course getCourse(String courseName) {
       return (Course) data.get(courseName);
    }

    public static List<MatchResult> getMatchList(String matchesName) {
        return (List<MatchResult>) data.get(matchesName);
    }

    @Before
    public void reset()
    {
        data = new HashMap<>();
    }
}
