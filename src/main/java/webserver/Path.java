package webserver;

import majorPlanner.entity.Schedule;
import org.jetbrains.annotations.NotNull;

public class Path {
    @NotNull
    static String schedule(Schedule s) {
        return "/schedule/" + s.getID();
    }
}