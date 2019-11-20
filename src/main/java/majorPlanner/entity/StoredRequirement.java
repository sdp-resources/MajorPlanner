package majorPlanner.entity;

public class StoredRequirement {
    private final Requirement req;
    private final String requirementId;
    private final String requirementDescription;

    public StoredRequirement(Requirement req, String requirementId, String requirementDescription) {
        this.req = req;
        this.requirementId = requirementId;
        this.requirementDescription = requirementDescription;
    }
}
