package org.example.rmi_server;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.example.jira.JiraConnection;

import java.net.URI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;
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

    public JiraRestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(JiraRestClient restClient) {
        this.restClient = restClient;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJiraUrl() {
        return jiraUrl;
    }

    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }


    private JiraRestClient getJiraRestClient() {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(getJiraUri(), this.username, this.password);
    }

    private URI getJiraUri() {
        return URI.create(this.jiraUrl);
    }

    public List<Issue> getIssuesFromJqlSearch(String jqlSearch) throws TimeoutException {
        try {
            final SearchResult searchResult = this.restClient.getSearchClient()
                    .searchJql(jqlSearch)
                    .get(5000, TimeUnit.SECONDS);
            return Lists.newArrayList(searchResult.getIssues());
        } catch (TimeoutException e) {
            System.err.println("jira rest client timeout from jql search error. cause: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("jira rest client get issue from jql search error. cause: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public String getUser(String accountId){
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

/*
public static void main(String[] args) {
        try{
            String url = "rmi://localhost:5097/Bank";

            OperationBancaire op  = (OperationBancaire) Naming.lookup(url);

            System.out.println("balance:"+ op.balance());
            op.depot(150);
            System.out.println("balance:"+ op.balance());
            op.retrait(300);
            System.out.println("balance:"+ op.balance());

        }catch(RemoteException | MalformedURLException e){
            System.out.println("Failed to open the server");
        } catch (NotBoundException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
