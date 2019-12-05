package majorPlanner.entity;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MatchedResult implements MatchResult {
    private StoredRequirement req;
    private Course matchedCourse;

    public MatchedResult(StoredRequirement req, Course matchedCourse) {
        this.req = req;
        this.matchedCourse = matchedCourse;
    }

    public StoredRequirement getReq() {
        return req;
    }

    public void setReq(StoredRequirement req) {
        this.req = req;
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
        return Objects.equals(req, that.req) &&
                Objects.equals(matchedCourse, that.matchedCourse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(req, matchedCourse);
    }

    @Override
    public void handle(BiConsumer<StoredRequirement, Course> matched, Consumer<StoredRequirement> unmatched) {
        matched.accept(req, matchedCourse);
    }
}
