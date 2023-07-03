package org.example.rmi_server;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.google.common.collect.Lists;
import org.example.domain.Assignee;
import org.example.domain.TaskEvolution;
import org.example.domain.TaskState;
import org.example.jira.JiraConnection;

import java.net.URI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JiraServant extends UnicastRemoteObject implements JiraConnection {
    private String username = "htsheleka@gmail.com";
    private String password = "ATATT3xFfGF01EA5APVKG36E11dqLJtZQZHAzE8_HAhPlsfECfbVeBx2V0k2zSMwNgASdWLWp7TNeQwayn0kC1dD0ZSCYlQq88bMAFhQXjAHLEx8OJH2LX2sJoym2IUkTDkmKhdiejp1AiGVO-j5Phn9BELt_EsSrHQ7J4e1PT8QeRUjrsxbVng=7E013355";
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
            final SearchResult searchResult = this.restClient
                    .getSearchClient()
                    .searchJql(jqlSearch)
                    .get(5000, TimeUnit.SECONDS);
            //return (List<Issue>) searchResult.getIssues();
            return Lists.newArrayList(searchResult.getIssues());
        } catch (TimeoutException e) {
            System.err.println("jira rest client timeout from jql search error. cause: "
                    + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("jira rest client get issue from jql search error. cause: "
                    + e.getMessage());
            return null;
            //return Collections.emptyList();
        }
    }


    //----------------------------------------------------------------

    public List<Assignee> getAssigneesFromIssues(List<Issue> issues){ // n.log(n)
        boolean userExist = false;
        int id = 0;
        Set<Assignee> assignees = new HashSet<>();

        for(Issue issue : issues){
            if(issue.getAssignee() == null) continue;
            Assignee assignee = new Assignee(
                    id++,
                    issue.getAssignee().getDisplayName(),
                    issue.getAssignee().getAccountId()
            );

            assignees.add(assignee);
        }

        return assignees.stream().toList();
    }
    public Assignee getAssigneeFormIssue(Issue issue){
        if(issue.getAssignee() == null) return null;
        return new Assignee(
                0,
                issue.getAssignee().getDisplayName(),
                issue.getAssignee().getAccountId()
        );
    }

    public List<TaskEvolution> getTaskEvolutionByAssignee(List<Assignee> assignees) throws TimeoutException {
        String searchOpenIssues = "project = SDR AND status = Open AND assignee in (712020:794d753c-e996-4e91-ac5c-775e7d8bf0e9) ORDER BY Rank ASC";
        String searchBacklogIssues = "project = SDR AND status = Backlog AND assignee in (712020:794d753c-e996-4e91-ac5c-775e7d8bf0e9) ORDER BY Rank ASC";
        String searchBlockedIssues = "";
        String searchDoneIssues = "";
        String searchInProgressIssues = "";
        String searchPeerReviewIssues = "";

        List<TaskState> issueStatus = new ArrayList<>();
        issueStatus.add(TaskState.OPEN);
        issueStatus.add(TaskState.BLOCKED);
        issueStatus.add(TaskState.BACKLOG);
        issueStatus.add(TaskState.IN_PROGRESS);
        issueStatus.add(TaskState.PEER_REVIEW);
        issueStatus.add(TaskState.DONE);

        List<TaskEvolution> taskList = new ArrayList<>();

        for(Assignee assignee : assignees){
            String jiraAccountId = assignee.getJiraAccountId();
            TaskEvolution evolution = new TaskEvolution(0,0,0,0,0,0,0,0,0, new Date(), assignee.getId());

            try {
                String searchNewTask = "created >= -"+25+"d AND project = SDR ORDER BY assignee DESC, Rank ASC";
                String searchOldTask = "created >= -"+30+"d AND project = SDR ORDER BY assignee DESC, Rank ASC";
                int newtask = getIssuesFromJqlSearch(searchNewTask).size();
                int oldtask = getIssuesFromJqlSearch(searchOldTask).size();

                for (TaskState state : issueStatus){
                    String searchStatusIssues = "project = SDR AND status = \""+state.getStatus()+"\" AND assignee in ("+jiraAccountId+") ORDER BY Rank ASC";

                    List<Issue> issues = getIssuesFromJqlSearch(searchStatusIssues);

                    int count = issues.size();

                    switch (state){
                        case OPEN -> evolution.setOpenTaskCount(count);
                        case BLOCKED -> evolution.setBlockedTaskCount(count);
                        case BACKLOG -> evolution.setBacklogTaskCount(count);
                        case IN_PROGRESS -> evolution.setInProgressTaskCount(count);
                        case PEER_REVIEW -> evolution.setInReviewTaskCount(count);
                        case DONE -> evolution.setDoneTaskCount(count);
                        case OLD_TASK -> evolution.setOldTaskCount(oldtask);
                        case NEW_TASK -> evolution.setNewTaskCount(newtask);
                    }
                }
                taskList.add(evolution);
            } catch (TimeoutException e){
                System.out.println("Error Message : " + e);
            }
        }
        return  taskList;
    }



    @Override
    public List<Assignee> getAssignees() throws RemoteException {
        String searchIssues = "project = SDR ORDER BY assignee ASC";
        try {
            List<Issue> issues = getIssuesFromJqlSearch(searchIssues);
            return getAssigneesFromIssues(issues);
        } catch (TimeoutException e){
            System.out.println("Error Message : " + e);
        }
        return null;
    }

    @Override
    public List<TaskEvolution> getAllTaskEvolution(List<Assignee> assignees) throws RemoteException {
        try {
            return getTaskEvolutionByAssignee(assignees);
        } catch (TimeoutException e){
            System.out.println("Error Message : " + e);
        }


        return null;
    }
}


