package org.example.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface JiraConnection extends Remote {

    public JiraRestClient getRestClient() throws RemoteException;

    public void setRestClient(JiraRestClient restClient) throws RemoteException;

    public String getUsername() throws RemoteException;

    public void setUsername(String username) throws RemoteException;

    public String getPassword() throws RemoteException;

    public void setPassword(String password) throws RemoteException;

    public String getJiraUrl() throws RemoteException;

    public void setJiraUrl(String jiraUrl) throws RemoteException;


    public String getIssuesFromJqlSearchJSON(String jqlSearch) throws RemoteException, TimeoutException;
    public String getUser(String accountId) throws RemoteException;

}

