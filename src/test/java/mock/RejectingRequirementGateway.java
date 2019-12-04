package mock;

import majorPlanner.entity.StoredRequirement;
import majorPlanner.gateway.RequirementGateway;

public class RejectingRequirementGateway implements RequirementGateway {
    @Override
    public void addRequirement(StoredRequirement requirement) {

    }

    @Override
    public StoredRequirement getRequirement(Integer id) {
        return null;
    }
}
