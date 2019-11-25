package majorPlanner.interactor;

import majorPlanner.gateway.Gateway;
import majorPlanner.request.*;

public class GatewayBackedInteractorFactory implements InteractorFactory {
    private final RequestVisitor requestVisitor = new RequestVisitor();

    public GatewayBackedInteractorFactory(Gateway gateway) {
        setGateway(gateway);
    }

    public Gateway getGateway() {
        return requestVisitor.getGateway();
    }

    public void setGateway(Gateway gateway) {
        requestVisitor.gateway = gateway;
    }

    @Override
    public Interactor getInteractorFor(Request request) {
        return requestVisitor.visit(request);
    }

    public static class RequestVisitor implements majorPlanner.request.RequestVisitor<Interactor> {
        public Gateway gateway;

        public Gateway getGateway() {
            return gateway;
        }

        @Override
        public Interactor visit(CreateScheduleRequest request) {
            return new CreateScheduleInteractor(request, gateway, gateway);
        }

        @Override
        public Interactor visit(AddCourseRequest request) {
            return new AddCourseToScheduleInteractor(request, gateway, gateway);
        }

        @Override
        public Interactor visit(ViewScheduleRequest request) {
            return new ViewScheduleInteractor(request, gateway);        }

        @Override
        public Interactor visit(RemoveCourseFromScheduleRequest request) {
            return new RemoveCourseFromScheduleInteractor(request, gateway, gateway);
        }

        @Override
        public Interactor visit(AddTransferCourseToScheduleRequest request) {
            return new AddTransferCourseToScheduleInteractor(request, gateway, gateway);
        }

        @Override
        public Interactor visit(ViewCourseListRequest request) {
            return new ViewCourseListInteractor(request);
        }
    }
}