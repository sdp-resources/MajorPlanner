package mock;

import majorPlanner.entity.Schedule;

import java.util.List;

public class RejectingScheduleGateway extends ScheduleGatewaySpy {


    @Override
    public Schedule getSchedule(int id) {
        super.getSchedule(id);
        return null;
    }

    @Override
    public List<Schedule> getSchedulesFromOwnerId(String owner) {
        return null;
    }

}
