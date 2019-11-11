package majorPlanner.interactor;

import org.jetbrains.annotations.NotNull;

public interface InteractorFactory {
    @NotNull
    Interactor createSchedule();

    @NotNull
    Interactor viewSchedule();

    @NotNull
    Interactor addCourseToSchedule();

    @NotNull
    Interactor removeCourseFromSchedule();
}
