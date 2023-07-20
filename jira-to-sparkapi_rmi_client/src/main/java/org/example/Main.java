package org.example;


import com.google.gson.Gson;

import org.example.issue.Assignee;
import org.example.issue.TaskEvolution;
import org.example.jira.JiraConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static String sparkUrl = "http://localhost:4567/jira/services";

    public static void main(String[] args)  {
        System.out.println("=== === RMI Client === ===");
        rmi_client();
        /*try {
            String assigneesJson = Http.get(sparkUrl+"/assignees");
            String tasksJson = Http.get(sparkUrl+"/tasks");

            System.out.println("Assignees : \n"+assigneesJson);
            System.out.println("----------------------------------");
            System.out.println("Tasks : \n"+tasksJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
    public static void rmi_client() {
        try{
            String url = "rmi://localhost:5097/jira-api";

            JiraConnection jiraConnection = (JiraConnection) Naming.lookup(url);

            List<Assignee> assignees = jiraConnection.getAssignees();
            List<TaskEvolution> tasks = jiraConnection.getAllTaskEvolution(assignees);

            //todo : make an wait here before send or ask for permission
            //todo : Check if toJson() can send a list
            Http.post(sparkUrl+"/assignees", new Gson().toJson(assignees));
            Http.post(sparkUrl+"/tasks", new Gson().toJson(tasks));

            //------------------------------------------------------------

            String assigneesJson = Http.get(sparkUrl+"/assignees");
            String tasksJson = Http.get(sparkUrl+"/tasks");

            System.out.println("Assignees : \n"+assigneesJson);
            System.out.println("----------------------------------");
            System.out.println("Tasks : \n"+tasksJson);

        }catch(RemoteException | MalformedURLException e){
            System.out.println("Failed to open the server");
            System.out.println("Error Message : "+e);
        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


/*static List<TaskEvolution> taskData = new ArrayList<>(List.of(
            new TaskEvolution(0, 1, 0, 0,0, 0, 0, 0, 0, new Date(), 1),
            new TaskEvolution(0, 9, 0, 5,1, 0, 0, 0, 0, new Date(), 40),
            new TaskEvolution(0, 7, 4, 5,1, 0, 0, 0, 0, new Date(), 16),
            new TaskEvolution(0, 9, 0, 5,1, 0, 0, 0, 0, new Date(), 2)
    ));
    static List<Assignee> assigneeData = new ArrayList<>(List.of(
            new Assignee(1, "Acacia Nday", "5f6bc1db06e34200711db7ee"),
            new Assignee(40, "Marinakinja", "712020:d758e715-289d-4667-8e1c-bbbc2c21ee9b"),
            new Assignee(16, "Hassan Tsheleka", "712020:794d753c-e996-4e91-ac5c-775e7d8bf0e9"),
            new Assignee(2, "Benjamin Oleko", "627cba6c6ba8640069cf1faa")
    ));*/