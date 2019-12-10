package majorPlanner.entity;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UnmatchedResult implements MatchResult {
    private StoredRequirement requirement;

    public UnmatchedResult(StoredRequirement req) {
        this.requirement = req;
    }

    public StoredRequirement getRequirement() {
        return requirement;
    }

    public void setRequirement(StoredRequirement requirement) {
        this.requirement = requirement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnmatchedResult that = (UnmatchedResult) o;
        return Objects.equals(requirement, that.requirement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirement);
    }

    @Override
    public void handle(BiConsumer<StoredRequirement, Course> matched, Consumer<StoredRequirement> unmatched) {
        unmatched.accept(requirement);
    }
}
