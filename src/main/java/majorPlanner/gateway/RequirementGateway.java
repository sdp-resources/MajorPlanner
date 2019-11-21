package majorPlanner.gateway;

import majorPlanner.entity.StoredRequirement;

public interface RequirementGateway {
    void addRequirement(StoredRequirement requirement);
    StoredRequirement getRequirement(Integer id);
}
