package org.example;

import org.example.issue.Assignee;
import org.example.issue.TaskEvolution;

import java.util.List;

public class Data {
    private List<Assignee> assigneeList;
    private List<TaskEvolution> taskEvolutionList;

    public List<Assignee> getAssignees() {
        return assigneeList;
    }

    public void setAssignees(List<Assignee> assignees) {
        this.assigneeList = assignees;
    }

    public List<TaskEvolution> getTaskEvolutions() {
        return taskEvolutionList;
    }

    public void setTaskEvolutions(List<TaskEvolution> taskEvolutions) {
        this.taskEvolutionList = taskEvolutions;
    }
}
