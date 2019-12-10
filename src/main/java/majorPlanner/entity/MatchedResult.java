package majorPlanner.entity;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MatchedResult implements MatchResult {
    private StoredRequirement requirement;
    private Course matchedCourse;

    public MatchedResult(StoredRequirement req, Course matchedCourse) {
        this.requirement = req;
        this.matchedCourse = matchedCourse;
    }

    public StoredRequirement getRequirement() {
        return requirement;
    }

    public void setRequirement(StoredRequirement requirement) {
        this.requirement = requirement;
    }

    public Course getMatchedCourse() {
        return matchedCourse;
    }

    public void setMatchedCourse(Course matchedCourse) {
        this.matchedCourse = matchedCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchedResult that = (MatchedResult) o;
        return Objects.equals(requirement, that.requirement) &&
                Objects.equals(matchedCourse, that.matchedCourse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirement, matchedCourse);
    }

    @Override
    public void handle(BiConsumer<StoredRequirement, Course> matched, Consumer<StoredRequirement> unmatched) {
        matched.accept(requirement, matchedCourse);
    }
}
