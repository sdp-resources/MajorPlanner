package majorPlanner.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Program {
    private final List<StoredRequirement> storedReqs;

    public Program(List<StoredRequirement> storedReqs) {
        this.storedReqs = storedReqs;
    }

    public List<MatchResult> match(Set<Course> courses) {
        return new ArrayList<>();
    }
}

