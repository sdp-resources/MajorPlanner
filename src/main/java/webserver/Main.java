package webserver;

import json.JsonConverter;
import majorPlanner.Controller;
import majorPlanner.authorizer.Authorizer;
import majorPlanner.entity.*;
import majorPlanner.gateway.Gateway;
import majorPlanner.interactor.GatewayBackedInteractorFactory;
import majorPlanner.request.Request;
import majorPlanner.response.Response;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    // TODO: Really should not be here
    private static List<Program> programs = new ArrayList<>();

    public static void main(String[] args) {
        WebServer server = new WebServer(6680);
        Gateway gateway = new MemoryGateway();
        gateway.addUser(new User("Joe", Role.User));
        Authorizer authorizer = new Authorizer() {
            public Response authorize(Request request) {
                return Response.ok();
            }
        };
        Controller requestHandler = new Controller(authorizer, new GatewayBackedInteractorFactory(gateway));
        server.setRequestHandler(requestHandler);
        server.setGateway(gateway);
        makeTemporaryData(gateway);
        server.start();
    }

    private static void makeTemporaryData(Gateway gateway) {
        createCourses(gateway);
        createPrograms(gateway);
        User owner = new User("Joe", Role.User);
        gateway.addUser(owner);
        Schedule schedule = new Schedule(owner, "English major schedule", "schedule description");
        schedule.addTransferCourse(Course.withTags("ENG171", "ENG", "1XX"));
        schedule.addCourse(Course.withTags("ENG245", "ENG", "2XX"), Period.Fall.toString(), Year.Freshman.toString());
//        schedule.addCourse(Course.withTags("ENG327", "ENG", "3XX"), Period.Winter.toString(), Year.Sophomore.toString());
        schedule.addProgram(programs.get(1));

        gateway.addSchedule(schedule);
        schedule.compareScheduleToProgram();
    }

    private static void createPrograms(Gateway gateway) {
        String json = readDataFile("programs.json");
        programs.addAll(getPrograms(json));
        for (Program program : programs) {
            for (StoredRequirement req : program.getStoredReqs()) {
                gateway.addRequirement(req);
            }
        }

    }

    private static List<Program> getPrograms(String json) {
        try {
            return new JsonConverter().deserializeProgramList(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void createCourses(Gateway gateway) {
        String json = readDataFile("courses.json");

        try {
            for (Course course : new JsonConverter().deserializeCourseList(json)) {
                gateway.addCourse(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readDataFile(String filename) {
        InputStream stream = Main.class.getResourceAsStream("../data/" + filename);

        return new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.joining("\n"));
    }

}
