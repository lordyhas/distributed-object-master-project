package service;

import domain.Assignee;
import domain.TaskEvolution;

import java.sql.SQLException;
import java.util.Collection;

public interface JiraDataLoggingService {

    TaskEvolution getTaskEvolution(int id) throws SQLException;
    Collection<TaskEvolution> getTaskEvolutions() throws SQLException;
    boolean addTaskEvolution(TaskEvolution task) throws SQLException;
    boolean deleteTaskEvolution(int id) throws SQLException;

    boolean updateTaskEvolution(TaskEvolution task) throws TaskEvolutionException, SQLException;

    Assignee getAssignee(int id) throws SQLException;
    Collection<Assignee> getAssignees() throws SQLException;

    boolean addAssignee(Assignee assignee) throws SQLException;
    boolean deleteAssignee(int id) throws SQLException;
    boolean updateAssignee(Assignee assignee) throws AssigneeException, SQLException;

}
