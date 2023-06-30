package org.example;


import com.google.gson.Gson;

import com.google.gson.JsonElement;
import org.example.domain.Assignee;
import org.example.jira.JiraConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== === RMI Client === ===");
        try{
            String url = "rmi://localhost:5097/jira-api";

            JiraConnection jiraConnection = (JiraConnection)  Naming.lookup(url);


            try {
                List<Assignee> results = jiraConnection.getAssignees();
                System.out.println("Size of Assignees :" + results.size()+"\n");
                System.out.println("Assignees : " + results.get(0)+" ...\n");

                String json = new Gson().toJson(results.get(0));
                Assignee result0 = new Gson().fromJson(json, Assignee.class);

                System.out.println("Gson operations...");

                System.out.println("Assignees : " + result0+" ...\n");
                //Http.post("", gson);
                /*for(String str: results){
                    Issue issue = new Gson().fromJson(str, Issue.class);

                    //String reporterAccountId = issue.getReporter().getAccountId();
                    String reporterAccountId = Objects.requireNonNull(issue.getReporter()).getAccountId();
                    System.out.print("Reporter: "+ jiraConnection.getUser(reporterAccountId));
                    System.out.println(", Key: "+issue.getKey() + ", Title: "+issue.getSummary() + ", Status: " + issue.getStatus().getName());
                }*/
                //System.out.println("Ben a "+results.size() +" open issues");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }catch(RemoteException | MalformedURLException e){
            System.out.println("Failed to open the server");
            System.out.println("Error Message : "+e);
        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}