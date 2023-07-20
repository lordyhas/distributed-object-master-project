package org.example.jira;

import org.example.issue.Assignee;
import org.example.issue.TaskEvolution;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface JiraConnection extends Remote {
    List<Assignee> getAssignees()  throws RemoteException;
    List<TaskEvolution> getAllTaskEvolution(List<Assignee> assignees)  throws RemoteException;

}
