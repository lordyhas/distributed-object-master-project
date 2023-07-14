/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package SparkAPI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import database.MyDatabase;
import domain.*;
import service.JiraDataLoggingService;
import service.JiraDataLogging;
import service.UserService;
import service.UserServiceImp;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

public class App {
    static TaskEvolution[] taskData = new TaskEvolution[]{
            new TaskEvolution(0, 1, 0, 0,0, 0, 0, 0, 0, new java.util.Date(), 1),
            new TaskEvolution(0, 9, 0, 5,1, 0, 0, 0, 0, new java.util.Date(), 40),
            new TaskEvolution(0, 7, 4, 5,1, 0, 0, 0, 0, new java.util.Date(), 16),
            new TaskEvolution(0, 9, 0, 5,1, 0, 0, 0, 0, new java.util.Date(), 2)
    };

    static Assignee[] assigneeData = new Assignee[]{
            new Assignee(1, "Acacia Nday", "5f6bc1db06e34200711db7ee"),
            new Assignee(40, "Marinakinja", "712020:d758e715-289d-4667-8e1c-bbbc2c21ee9b"),
            new Assignee(16, "Hassan Tsheleka", "712020:794d753c-e996-4e91-ac5c-775e7d8bf0e9"),
            new Assignee(2, "Benjamin Oleko", "627cba6c6ba8640069cf1faa"),
    };
    static List<Assignee> assigneeList = Arrays.stream(assigneeData).toList(); //new ArrayList<>();
    static List<TaskEvolution> taskEvolutionList = Arrays.stream(taskData).toList(); //new ArrayList<>();
    public String getGreeting() {
        return "Hello World!";
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("=== [Spark Server] started ===");
        main_spark();
        System.out.println("=== [Spark Server] finish : off ===");
    }

    public static void main_spark() throws SQLException, ClassNotFoundException {
        //System.out.println(new App().getGreeting());

        final JiraDataLogging data = new JiraDataLogging();

        get("/jira/services/tasks", (request, response)->{
            response.type("application/json");
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,
                            new Gson().toJsonTree(taskEvolutionList))
            );
        });

        get("/jira/services/tasks/:id", (request, response)->{
            response.type("application/json");
            int id = Integer.parseInt(request.params(":id"));
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,
                            new Gson().toJsonTree(data.getAssignee(id))));
        });

        delete("/jira/services/tasks/:id", (request, response)->{
            response.type("application/json");
            int id = Integer.parseInt(request.params(":id"));
            if (data.deleteTaskEvolution(id)) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, "TaskEvolution deleted"));
            } else {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,
                                "TaskEvolution not found or error on delete"));
            }

        });

        put("/jira/services/tasks/:id", (request, response)->{
            response.type("application/json");

            TaskEvolution task = new Gson().fromJson(request.body(), TaskEvolution.class);

            if (data.updateTaskEvolution(task)) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,
                                new Gson().toJson("TaskEvolution edited")));
            } else {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,
                                new Gson().toJson("TaskEvolution not found or error in edit")));
            }

        });

        ///-----------------------------------------------------------------------------------------

        get("/jira/services/assignees", (request, response)->{
            response.type("application/json");
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,
                            new Gson().toJsonTree(assigneeList))
            );
        });

        get("/jira/services/assignees/:id", (request, response)->{
            response.type("application/json");
            int id = Integer.parseInt(request.params(":id"));
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,
                            new Gson().toJsonTree(data.getTaskEvolution(id))));
        });

        post("/jira/services/assignees", (request, response)->{
            response.type("application/json");
            List<Assignee> assignees = new Gson().fromJson(request.body(), new TypeToken<List<User>>(){}.getType());

            for(Assignee assignee : assignees){
                data.addAssignee(assignee);
            }
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });


        put("/jira/services/assignees/:id", (request, response)->{
            response.type("application/json");

            Assignee assignee = new Gson().fromJson(request.body(), Assignee.class);
            if (data.updateAssignee(assignee)) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,
                                new Gson().toJson("Assignee edited")));
            } else {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,
                                new Gson().toJson("Assignee not found or error in edit")));
            }
        });
        delete("/jira/services/assignees/:id", (request, response)->{
            response.type("application/json");
            int id = Integer.parseInt(request.params(":id"));
            if (data.deleteAssignee(id)) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Assignee deleted"));
            } else {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,
                                "Assignee not found or error on delete"));
            }
        });


    }
    public static void __main() throws ClassNotFoundException {
        final UserService service = new UserServiceImp();

        //final JiraDataLoggingService jiraService = new JiraDataLogging();
        get("/hello",(request, response)-> "Hello Word");
        
        get("/hello/:name",(request, response)->{
            
            return "Hello, "+request.params(":name");
                
        });
        
        post("/users", (request, response) -> {
            response.type("application/json");

            User user = new Gson().fromJson(request.body(), User.class);
            service.addUser(user);

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/users", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, 
                    new Gson().toJsonTree(service.getUsers()))
            );
        });

        get("/users/:id", (request, response) -> {
            response.type("application/json");
            int id = Integer.parseInt(request.params(":id"));
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, 
                            new Gson().toJsonTree(service.getUser(id))));
        });
        
        put("/users/:id", (request, response) -> {
            response.type("application/json");

            User toEdit = new Gson().fromJson(request.body(), User.class);
            User editedUser = service.editUser(toEdit);

            if (editedUser != null) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, 
                                new Gson().toJsonTree(editedUser)));
            } else {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, 
                                new Gson().toJson("User not found or error in edit")));
            }
        });
        
        delete("/users/:id", (request, response) -> {
            response.type("application/json");
            int id = Integer.parseInt(request.params(":id"));
            service.deleteUser(id);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "user deleted"));
        });

        options("/users/:id", (request, response) -> {
            response.type("application/json");
            int id = Integer.parseInt(request.params(":id"));
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, 
                            (service.userExist(id)) ? "User exists" : "User does not exists"));
        });

    }



    static void testDB() throws ClassNotFoundException, SQLException {
        final JiraDataLogging jiraService = new JiraDataLogging();
        //jiraService.showTables();
        //jiraService.addTaskEvolution(taskEvolutionList.get(0));

        List<Assignee> assignees = (List<Assignee>) jiraService.getAssignees();
        List<TaskEvolution> tasks = (List<TaskEvolution>) jiraService.getTaskEvolutions();

        System.out.println("Assignees : len("+assignees.size()+")");
        for(Assignee a : assignees){
            System.out.println(a);
        }

        System.out.println("Tasks : len("+tasks.size()+")");
        for(TaskEvolution t : tasks){
            System.out.println(t);
        }
    }
}
