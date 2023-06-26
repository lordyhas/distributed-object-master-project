package org.example;

import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.example.rmi_server.JiraServant;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws RemoteException {
        System.out.println("Hello world!");
        _jira_test();
        /*try{
            JiraServant servant  =  new JiraServant();
            String url = "rmi://localhost:5097/jira-api";
            LocateRegistry.createRegistry(5097);
            Naming.rebind(url, servant);

            System.out.println("Server ready...");
        }catch(RemoteException | MalformedURLException e){
            System.out.println("Failed to open the server");
        }*/

    }

    public static void _jira_test() throws RemoteException {
        JiraServant jiraSample = new JiraServant();
        String jqlSearch = "project = 'SDR' ORDER BY created DESC";
        String jqlSearch2 = "project = SDR ORDER BY assignee ASC";
        String jql2 =  "project = 'SDR' AND assignee IN (627cba6c6ba8640069cf1faa,712020:794d753c-e996-4e91-ac5c-775e7d8bf0e9) AND status IN (Blocked,Done) ORDER BY created DESC";


        try {
            List<Issue> results = jiraSample.getIssuesFromJqlSearch(jqlSearch);
            //List<String> resultsG = jiraSample.getIssuesFromJqlSearchGson(jqlSearch);
            //System.out.println(resultsG);
            //System.out.println("len : "+resultsG.size()+"\n");
            //System.out.println("object : "+resultsG.get(2)+"\n\n");
            JsonElement str = new Gson().toJsonTree(results.get(1));
            System.out.println(str);

            Issue issue = new Gson().fromJson(str, Issue.class);
            System.out.println(issue);
            //System.out.println(new Gson().fromJson(resultsG.get(2),Object.class));

            List<Issue> issues = new ArrayList<>();
            int i = 0;
            for(;i < -1;i++){
                //System.out.println("object : "+resultsG.get(i)+"\n\n");
                //Issue issue = new Gson().fromJson((String) e,Issue.class);
                //System.out.println("issue : "+issue+"\n\n");
                //break;
            }

            /*List list = new Gson().fromJson(results, List.class);
            for(Object e : list){
                //System.out.println("object : "+e+"\n\n");
                Issue issue = new Gson().fromJson((String) e,Issue.class);
                System.out.println("issue : "+issue+"\n\n");
            }*/



        } catch (TimeoutException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void _jira_main() throws RemoteException {
        JiraServant jiraSample = new JiraServant();
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
        try {
            String results = jiraSample.getIssuesFromJqlSearchJSON(jql2);
            /*for(String str: results){
                Issue issue = new Gson().fromJson(str, Issue.class);
                //String reporterAccountId = issue.getReporter().getAccountId();
                String reporterAccountId = Objects.requireNonNull(issue.getReporter()).getAccountId();
                System.out.print("Reporter: "+ jiraSample.getUser(reporterAccountId));
                System.out.println(", Key: "+issue.getKey() + ", Title: "+issue.getSummary() + ", Status: " + issue.getStatus().getName());
            }*/
            jqlSearch = "project = 'SDR' AND assignee = 627cba6c6ba8640069cf1faa AND status IN (Open) ORDER BY created DESC";
            //List<String> resultByUser = jiraSample.getIssuesFromJqlSearch(jqlSearch);
            //System.out.println("Ben a "+resultByUser.size() +" open issues");
        } catch (TimeoutException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}