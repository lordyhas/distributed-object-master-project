package org.example;


import com.google.gson.Gson;

import org.example.issue.Assignee;
import org.example.issue.TaskEvolution;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import java.util.Date;

public class Main {
    static TaskEvolution[] taskData = new TaskEvolution[]{
            new TaskEvolution(0, 1, 0, 0,0, 0, 0, 0, 0, new Date(), 0),
            new TaskEvolution(0, 9, 0, 5,1, 0, 0, 0, 0, new Date(), 40),
            new TaskEvolution(0, 7, 4, 5,1, 0, 0, 0, 0, new Date(), 16),
            new TaskEvolution(0, 9, 0, 5,1, 0, 0, 0, 0, new Date(), 1)
    };

    static Assignee[] assigneeData = new Assignee[]{
            new Assignee(0, "Acacia Nday", "5f6bc1db06e34200711db7ee"),
            new Assignee(40, "Marinakinja", "712020:d758e715-289d-4667-8e1c-bbbc2c21ee9b"),
            new Assignee(16, "Hassan Tsheleka", "712020:794d753c-e996-4e91-ac5c-775e7d8bf0e9"),
            new Assignee(1, "Benjamin Oleko", "627cba6c6ba8640069cf1faa"),
    };
    public static void main(String[] args) {
        System.out.println("=== === RMI Client === ===");
        String sparkUrl = "http://localhost:4567/jira/services";

        try{
            String url = "rmi://localhost:5097/jira-api";

            //JiraConnection jiraConnection = (JiraConnection)  Naming.lookup(url);



            //List<Assignee> results = jiraConnection.getAssignees();
            //System.out.println("Size of Assignees :" + results.size()+"\n");
            //System.out.println("Assignees : " + results.get(0)+" ...\n");

            String json = new Gson().toJson(assigneeData[2]);
            Assignee result0 = new Gson().fromJson(json, Assignee.class);

            System.out.println("Gson operations...");

            System.out.println("Assignees : " + result0+"\n");
            System.out.println("sent to : " + sparkUrl+"/assignees...");

            Http.post(sparkUrl+"/assignees", json);
            /*for(String str: results){
                    Issue issue = new Gson().fromJson(str, Issue.class);

                    //String reporterAccountId = issue.getReporter().getAccountId();
                    String reporterAccountId = Objects.requireNonNull(issue.getReporter()).getAccountId();
                    System.out.print("Reporter: "+ jiraConnection.getUser(reporterAccountId));
                    System.out.println(", Key: "+issue.getKey() + ", Title: "+issue.getSummary() + ", Status: " + issue.getStatus().getName());
               }*/
            //System.out.println("Ben a "+results.size() +" open issues");


        }catch(RemoteException | MalformedURLException e){
            System.out.println("Failed to open the server");
            System.out.println("Error Message : "+e);
        } /*catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}