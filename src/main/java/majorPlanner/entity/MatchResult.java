package majorPlanner.entity;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface MatchResult {
    void handle(BiConsumer<StoredRequirement, Course> matched, Consumer<StoredRequirement> unmatched);
}
