package org.example;

import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

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
            String url = "rmi://localhost:5097/jira-api"; //todo: change this to jira

            JiraConnection jiraConnection = (JiraConnection)  Naming.lookup(url);
            String jqlSearch = "project = 'SDR' AND assignee = 627cba6c6ba8640069cf1faa AND status IN (Open) ORDER BY created DESC";

            try {
                String results = jiraConnection.getIssuesFromJqlSearchJSON(jqlSearch);
                System.out.println(results);
                //new Gson().fromJson().toString();
                //String gson = new Gson().toJson(resultByUser.get(1));
                //Http.post("", gson);
                /*for(String str: results){
                    Issue issue = new Gson().fromJson(str, Issue.class);

                    //String reporterAccountId = issue.getReporter().getAccountId();
                    String reporterAccountId = Objects.requireNonNull(issue.getReporter()).getAccountId();
                    System.out.print("Reporter: "+ jiraConnection.getUser(reporterAccountId));
                    System.out.println(", Key: "+issue.getKey() + ", Title: "+issue.getSummary() + ", Status: " + issue.getStatus().getName());
                }*/
                //System.out.println("Ben a "+results.size() +" open issues");
            } catch (TimeoutException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } /*catch (IOException e) {
                throw new RuntimeException(e);
            }*/

        }catch(RemoteException | MalformedURLException e){
            System.out.println("Failed to open the server");
            System.out.println("Error Message : "+e);
        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    void jira_api() throws MalformedURLException, NotBoundException, RemoteException {
        JiraConnection jiraSample = (JiraConnection)  Naming.lookup("rmi://localhost:5097/jira-api");
        String jqlSearch = "project = 'SDR' ORDER BY created DESC";
        String jql2 =  "project = 'SDR' AND assignee IN (627cba6c6ba8640069cf1faa,712020:794d753c-e996-4e91-ac5c-775e7d8bf0e9) AND status IN (Blocked,Done) ORDER BY created DESC";
        try {
            Iterable<BasicProject> projects = jiraSample.getRestClient().getProjectClient().getAllProjects().claim();
            for (BasicProject project : projects) {
                System.out.println(project.getKey() + ": " + project.getName());
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        /*try {
            List<Issue> results = jiraSample.getIssuesFromJqlSearchJSON(jql2);
            for(Issue issue: results){
                //String reporterAccountId = issue.getReporter().getAccountId();
                String reporterAccountId = Objects.requireNonNull(issue.getReporter()).getAccountId();
                System.out.print("Reporter: "+ jiraSample.getUser(reporterAccountId));
                System.out.println(", Key: "+issue.getKey() + ", Title: "+issue.getSummary() + ", Status: " + issue.getStatus().getName());
            }
            jqlSearch = "project = 'SDR' AND assignee = 627cba6c6ba8640069cf1faa AND status IN (Open) ORDER BY created DESC";
            //List<Issue> resultByUser = jiraSample.getIssuesFromJqlSearchJSON(jqlSearch);
            //System.out.println("Ben a "+resultByUser.size() +" open issues");
        } catch (TimeoutException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}