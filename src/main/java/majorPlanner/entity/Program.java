package majorPlanner.entity;

import java.util.*;

public class Program {
    private String name;
    private String description;
    private List<StoredRequirement> storedReqs;
    private List<MatchResult> matchResults;

    public Program(String name, String description, List<StoredRequirement> storedReqs) {
        this.name = name;
        this.description = description;
        this.storedReqs = storedReqs;
    }

    public List<MatchResult> match(Set<Course> courses) {
        matchResults = new ArrayList<>();
        for (StoredRequirement req : storedReqs) {
           matchResults.add(matchRequirement(req, courses));
        }
        return matchResults;
    }

    private MatchResult matchRequirement(StoredRequirement req, Set<Course> courses) {
        Requirement curReq = req.getRequirement();
        Set<Course> matchedCourses = curReq.matches(courses);
        for (Course matchedCourse : matchedCourses) {
            courses.remove(matchedCourse);
            return new MatchedResult(req, matchedCourse);
        }
        return new UnmatchedResult(req);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StoredRequirement> getStoredReqs() {
        return storedReqs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equals(name, program.name) &&
                Objects.equals(description, program.description) &&
                Objects.equals(storedReqs, program.storedReqs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, storedReqs);
    }

    @Override
    public String toString() {
        return "Program{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", storedReqs=" + storedReqs +
                '}';
    }
}

