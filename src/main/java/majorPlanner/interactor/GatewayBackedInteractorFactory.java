package majorPlanner.interactor;

import majorPlanner.gateway.Gateway;
import org.jetbrains.annotations.NotNull;

public class GatewayBackedInteractorFactory implements InteractorFactory {
    public Gateway gateway;

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public GatewayBackedInteractorFactory(Gateway gateway) {
        this.gateway = gateway;
    }

    @Override
    @NotNull
    public Interactor createSchedule() {
        return new CreateScheduleInteractor(gateway, gateway);
    }

    @Override
    @NotNull
    public Interactor viewSchedule() {
        return new ViewScheduleInteractor(gateway);
    }

    @Override
    @NotNull
    public Interactor addCourseToSchedule() {
        return new AddCourseToScheduleInteractor(gateway, gateway);
    }

    @Override
    @NotNull
    public Interactor removeCourseFromSchedule() {
        return new RemoveCourseFromScheduleInteractor(gateway, gateway);
    }
}