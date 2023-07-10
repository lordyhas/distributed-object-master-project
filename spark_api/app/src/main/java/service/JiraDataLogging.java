package service;

import database.MyDatabase;
import domain.Assignee;
import domain.TaskEvolution;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JiraDataLogging implements JiraDataLoggingService {
    private final MyDatabase database;
    private final String prefix = "jr_";
    public JiraDataLogging() throws ClassNotFoundException {
        this.database = new MyDatabase();
    }

    @Override
    public TaskEvolution getTaskEvolution(int id) throws SQLException {
        Connection con = database.getConnection();

        TaskEvolution task = null;

        PreparedStatement ps = con.prepareStatement("SELECT * FROM "+prefix+"task_evolution WHERE id = ?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            task = new TaskEvolution(
                    rs.getInt("id"),
                    rs.getInt("open_task_count"),
                    rs.getInt("blocked_task_count"),
                    rs.getInt("backlog_task_count"),
                    rs.getInt("in_progress_task_count"),
                    rs.getInt("in_review_task_count"),
                    rs.getInt("done_task_count"),
                    rs.getInt("old_task_count"),
                    rs.getInt("new_task_count"),
                    rs.getDate("statistic_date"),
                    rs.getInt("assigneeId")
            );
        }
        con.close();
        return task;
    }

    @Override
    public Collection<TaskEvolution> getTaskEvolutions() throws SQLException {
        Connection con = database.getConnection();

        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM "+prefix+"task_evolution";
        ResultSet rs = stmt.executeQuery(sql);

        List<TaskEvolution> list = new ArrayList<>();

        while (rs.next()) {
            TaskEvolution te = new TaskEvolution(
                    rs.getInt("id"),
                    rs.getInt("open_task_count"),
                    rs.getInt("blocked_task_count"),
                    rs.getInt("backlog_task_count"),
                    rs.getInt("in_progress_task_count"),
                    rs.getInt("in_review_task_count"),
                    rs.getInt("done_task_count"),
                    rs.getInt("old_task_count"),
                    rs.getInt("new_task_count"),
                    rs.getDate("statistic_date"),
                    rs.getInt("assigneeId")
            );

            list.add(te);
        }

        stmt.close();
        con.close();

        return list;
    }


    @Override
    public boolean addTaskEvolution(TaskEvolution te) throws SQLException {
        if(te == null) return false;
        Connection con = database.getConnection();
        String sql = "INSERT INTO "+prefix+"task_evolution (id, open_task_count, blocked_task_count, backlog_task_count, in_progress_task_count, in_review_task_count, done_task_count, old_task_count, new_task_count, statistic_date, assigneeId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, te.getId());
        ps.setInt(2, te.getOpenTaskCount());
        ps.setInt(3, te.getBlockedTaskCount());
        ps.setInt(4, te.getBacklogTaskCount());
        ps.setInt(5, te.getInProgressTaskCount());
        ps.setInt(6, te.getInReviewTaskCount());
        ps.setInt(7, te.getDoneTaskCount());
        ps.setInt(8, te.getOldTaskCount());
        ps.setInt(9, te.getNewTaskCount());
        ps.setDate(10, (Date) te.getStatisticDate()); //todo change statistic_date type to long in db
        ps.setInt(11, te.getAssigneeId());

        int rows = ps.executeUpdate();

        con.close();

        return rows > 0;
    }

    @Override
    public boolean deleteTaskEvolution(int id) throws SQLException {
        Connection con = database.getConnection();
        String sql = "DELETE FROM "+prefix+"task_evolution WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        int rows = statement.executeUpdate();
        con.close();

        return rows > 0;
    }

    @Override
    public boolean updateTaskEvolution(TaskEvolution te) throws TaskEvolutionException, SQLException {
        if(te == null) return false;
        Connection con = database.getConnection();

        String sql = "UPDATE "+prefix+"task_evolution SET open_task_count = ?, blocked_task_count = ?, backlog_task_count = ?, in_progress_task_count = ?, in_review_task_count = ?, done_task_count = ?, old_task_count = ?, new_task_count = ?, statistic_date = ?, assigneeId = ? WHERE id = ?;\n";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, te.getOpenTaskCount());
        ps.setInt(2, te.getBlockedTaskCount());
        ps.setInt(3, te.getBacklogTaskCount());
        ps.setInt(4, te.getInProgressTaskCount());
        ps.setInt(5, te.getInReviewTaskCount());
        ps.setInt(6, te.getDoneTaskCount());
        ps.setInt(7, te.getOldTaskCount());
        ps.setInt(8, te.getNewTaskCount());
        ps.setDate(9, (Date) te.getStatisticDate());
        ps.setInt(10, te.getAssigneeId());
        ps.setInt(11, te.getId());

        int rows = ps.executeUpdate();
        con.close();

        return rows > 0;
    }

    @Override
    public Assignee getAssignee(int id) throws SQLException {
        Connection con = database.getConnection();
        Assignee assignee = null;

        PreparedStatement ps = con.prepareStatement("SELECT * FROM "+prefix+"assignee WHERE id = ?");

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            //int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String jiraAccountId = rs.getString("jiraAccountId"); //todo change jiraAccountId type to string in db

            assignee = new Assignee(id, name, jiraAccountId);

        }
        con.close();
        return assignee;
    }

    @Override
    public Collection<Assignee> getAssignees() throws SQLException {
        Connection con = database.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM "+prefix+"assignee");


        List<Assignee> list = new ArrayList<>();

        while (rs.next()) {

            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String jiraAccountId = rs.getString("jiraAccountId");

            Assignee a = new Assignee(id, name, jiraAccountId);

            list.add(a);
        }
        con.close();

        return list;
    }

    @Override
    public boolean addAssignee(Assignee assignee) throws SQLException {
        if(assignee == null) return false;
        Connection con = database.getConnection();
        String sql = "INSERT INTO "+prefix+"assignee (id, name, jiraAccountId) VALUES (?, ?, ?);";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, assignee.getId());
        ps.setString(2, assignee.getName());
        //ps.setString(3, assignee.getEmail());
        ps.setString(3, assignee.getJiraAccountId());

        boolean result = ps.execute();

        con.close();

        return result;
    }

    @Override
    public boolean deleteAssignee(int id) throws SQLException {
        Connection con = database.getConnection();
        String sql = "DELETE FROM "+prefix+"assignee WHERE id = ?;";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        int rows = ps.executeUpdate();

        con.close();
        return rows > 0;
    }

    @Override
    public boolean updateAssignee(Assignee assignee) throws AssigneeException, SQLException {
        if(assignee == null) return false;
        Connection con = database.getConnection();
        String sql = "UPDATE "+prefix+"assignee SET name = ?, jiraAccountId = ? WHERE id = ?;";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, assignee.getName());
        //ps.setString(2, assignee.getEmail());
        ps.setString(2, assignee.getJiraAccountId());
        ps.setInt(3, assignee.getId());

        int rows = ps.executeUpdate();

        con.close();

        return rows > 0;

    }
}
