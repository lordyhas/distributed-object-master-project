package org.example.jira;

import org.example.issue.Assignee;
import org.example.issue.TaskEvolution;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface JiraConnection extends Remote {

    //public JiraRestClient getRestClient() throws RemoteException;

    //public void setRestClient(JiraRestClient restClient) throws RemoteException;

    //public String getUsername() throws RemoteException;

    //public void setUsername(String username) throws RemoteException;

    //public String getPassword() throws RemoteException;

    //public void setPassword(String password) throws RemoteException;

    //public String getJiraUrl() throws RemoteException;

    //public void setJiraUrl(String jiraUrl) throws RemoteException;


    //public String getIssuesFromJqlSearchJSON(String jqlSearch) throws RemoteException, TimeoutException;
    //public String getUser(String accountId) throws RemoteException;
    public List<Assignee> getAssignees()  throws RemoteException;
    public List<TaskEvolution> getAllTaskEvolution(List<Assignee> assignees)  throws RemoteException;

}

