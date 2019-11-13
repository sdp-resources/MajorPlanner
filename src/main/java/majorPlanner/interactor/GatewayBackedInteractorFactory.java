package majorPlanner.interactor;

import majorPlanner.gateway.Gateway;
import majorPlanner.request.*;
import org.jetbrains.annotations.NotNull;

public class GatewayBackedInteractorFactory implements InteractorFactory {
    public final RequestVisitor requestVisitor = new RequestVisitor();

    public Gateway getGateway() {
        return requestVisitor.getGateway();
    }

    public void setGateway(Gateway gateway) {
        this.requestVisitor.gateway = gateway;
    }

    public GatewayBackedInteractorFactory(Gateway gateway) {
        this.requestVisitor.gateway = gateway;
    }

    @Override
    public Interactor getInteractorFor(Request request) {
        return requestVisitor.visit(request);
    }

    public static class RequestVisitor implements majorPlanner.request.RequestVisitor<Interactor> {
        public Gateway gateway;

        public RequestVisitor() {
        }

        public Gateway getGateway() {
            return gateway;
        }

        @Override
        public Interactor visit(CreateScheduleRequest request) {
            return new CreateScheduleInteractor(gateway, gateway);
        }

        @Override
        public Interactor visit(AddCourseRequest request) {
            return new AddCourseToScheduleInteractor(gateway, gateway);
        }

        @Override
        public Interactor visit(ViewScheduleRequest request) {
            return new ViewScheduleInteractor(gateway);        }

        @Override
        public Interactor visit(RemoveCourseFromScheduleRequest request) {
            return new RemoveCourseFromScheduleInteractor(gateway, gateway);
        }
    }
}