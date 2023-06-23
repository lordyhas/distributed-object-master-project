package org.example.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface JiraConnection {

    public JiraRestClient getRestClient();

    public void setRestClient(JiraRestClient restClient);

    public String getUsername();

    public void setUsername(String username);

    public String getPassword();

    public void setPassword(String password);

    public String getJiraUrl();

    public void setJiraUrl(String jiraUrl);


    public List<Issue> getIssuesFromJqlSearch(String jqlSearch) throws TimeoutException;
    public String getUser(String accountId);

}
