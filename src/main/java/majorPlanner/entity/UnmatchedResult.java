package majorPlanner.entity;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UnmatchedResult implements MatchResult {
    private StoredRequirement req;

    public UnmatchedResult(StoredRequirement req) {
        this.req = req;
    }

    public StoredRequirement getReq() {
        return req;
    }

    public void setReq(StoredRequirement req) {
        this.req = req;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnmatchedResult that = (UnmatchedResult) o;
        return Objects.equals(req, that.req);
    }

    @Override
    public int hashCode() {
        return Objects.hash(req);
    }

    @Override
    public void handle(BiConsumer<StoredRequirement, Course> matched, Consumer<StoredRequirement> unmatched) {
        unmatched.accept(req);
    }
}
