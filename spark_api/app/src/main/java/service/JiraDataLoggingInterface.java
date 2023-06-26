package service;

import domain.Assignee;
import domain.TaskEvolution;

import java.sql.SQLException;
import java.util.Collection;

public interface JiraDataLoggingInterface {

    TaskEvolution getTaskEvolution(int id) throws SQLException;
    Collection<TaskEvolution> getTaskEvolutions() throws SQLException;
    void addTaskEvolution(TaskEvolution task) throws SQLException;
    void deleteTaskEvolution(int id) throws SQLException;

    void updateTaskEvolution(TaskEvolution task) throws TaskEvolutionException, SQLException;

    Assignee getAssignee(int id) throws SQLException;
    Collection<Assignee> getAssignees() throws SQLException;

    void addAssignee(Assignee assignee) throws SQLException;
    void deleteAssignee(int id) throws SQLException;
    void updateAssignee(Assignee assignee) throws AssigneeException, SQLException;

}
