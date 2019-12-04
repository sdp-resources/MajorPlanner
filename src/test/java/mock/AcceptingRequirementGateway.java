package mock;

import majorPlanner.entity.StoredRequirement;
import majorPlanner.gateway.RequirementGateway;

public class AcceptingRequirementGateway implements RequirementGateway {
    public StoredRequirement storedRequirement;

    @Override
    public void addRequirement(StoredRequirement requirement) {
        storedRequirement = requirement;
    }

    @Override
    public StoredRequirement getRequirement(Integer id) {
        return storedRequirement;
    }
}
