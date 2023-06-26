package org.example.rmi_server;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.example.domain.Assignee;
import org.example.domain.TaskEvolution;
import org.example.jira.IssueRemotable;
import org.example.jira.JiraConnection;
import org.joda.time.DateTime;

import java.net.URI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JiraServant extends UnicastRemoteObject implements JiraConnection {

    private String username = "jordan.masakuna@gmail.com";
    private String password = "ATATT3xFfGF0pCvljLnDlWTOO5Hxw8Jwa8g80gNGK5RwmpwfR8vQpKbxZFiti2XiMDMopk8VgV-DWQgx3CIK-I9MJbnHqYXHRnuvfuIAxJwjxPHiTYNXXoyYNP9yGodmv-feOCJ9vukYJWEz8BwlyfUvIV0VagXk1b_xXx1uSh4AAHhjVSwi6Fg=AA977FB6";
    private String jiraUrl = "https://jira-unh-master-gl2023.atlassian.net/";
    private JiraRestClient restClient;


    public JiraServant() throws RemoteException {
        super();
        this.restClient = getJiraRestClient();
    }

    public JiraRestClient getRestClient() throws RemoteException {
        return restClient;
    }

    public void setRestClient(JiraRestClient restClient) throws RemoteException {
        this.restClient = restClient;
    }

    public String getUsername() throws RemoteException {
        return username;
    }

    public void setUsername(String username) throws RemoteException {
        this.username = username;
    }

    public String getPassword() throws RemoteException {
        return password;
    }

    public void setPassword(String password) throws RemoteException {
        this.password = password;
    }

    public String getJiraUrl() throws RemoteException {
        return jiraUrl;
    }

    public void setJiraUrl(String jiraUrl) throws RemoteException {
        this.jiraUrl = jiraUrl;
    }

    private JiraRestClient getJiraRestClient() throws RemoteException {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(getJiraUri(), this.username, this.password);
    }

    private URI getJiraUri() throws RemoteException {
        return URI.create(this.jiraUrl);
    }
    private void getAssigneeFormIssue(Issue issue){
        Assignee assignee = new Assignee(
                0,
                Objects.requireNonNull(issue.getAssignee()).getName(),
                issue.getAssignee().getEmailAddress(),
                issue.getAssignee().getAccountId()
                );
        TaskEvolution evolution = new TaskEvolution(0,0,0,0,0,0,0,0,0, new Date(), 0);

        issue.getStatus().getName();
        //issue.get
    }

    public List<Issue> getIssuesFromJqlSearch(String jqlSearch) throws RemoteException, TimeoutException {
        try {
            final SearchResult searchResult = this.getRestClient().getSearchClient()
                    .searchJql(jqlSearch)
                    .get(5000, TimeUnit.SECONDS);
            //return (List<Issue>) searchResult.getIssues();
            return Lists.newArrayList(searchResult.getIssues());
        } catch (TimeoutException e) {
            System.err.println("jira rest client timeout from jql search error. cause: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("jira rest client get issue from jql search error. cause: " + e.getMessage());
            return null;
            //return Collections.emptyList();
        }
    }

    public List<String> getIssuesFromJqlSearchGson(String jqlSearch) throws RemoteException, TimeoutException {
        try {
            final SearchResult searchResult = this.getRestClient().getSearchClient()
                    .searchJql(jqlSearch)
                    .get(5000, TimeUnit.SECONDS);
            //return (List<Issue>) searchResult.getIssues();
            List<String> list = new ArrayList<>();
            List<Issue> issues = Lists.newArrayList(searchResult.getIssues());

            for(Issue issue : issues){
                String str = new Gson().toJson(searchResult.getIssues());
                list.add(str);

            }

            return list;
        } catch (TimeoutException e) {
            System.err.println("jira rest client timeout from jql search error. cause: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("jira rest client get issue from jql search error. cause: " + e.getMessage());
            return null;
            //return Collections.emptyList();
        }
    }

    public String getIssuesFromJqlSearchJSON(String jqlSearch) throws RemoteException, TimeoutException{
        return new Gson().toJson(this.getIssuesFromJqlSearch(jqlSearch));
    }
    public String getUser(String accountId) throws RemoteException{
        try {
            HttpResponse<JsonNode> response = Unirest.get(this.jiraUrl+"rest/api/2/user")
                    .basicAuth(this.username, this.password)
                    .header("Accept", "application/json")
                    .queryString("accountId", accountId).asJson();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody().toString(), com.fasterxml.jackson.databind.JsonNode.class).get("displayName").asText();
        } catch (UnirestException ex) {
            System.out.println(ex.getMessage());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JiraServant.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
//jira-to-sparkapi_rmi_client


