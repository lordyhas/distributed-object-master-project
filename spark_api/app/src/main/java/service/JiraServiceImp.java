package service;

import database.MyDatabase;
import domain.Assignee;
import domain.TaskEvolution;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JiraServiceImp implements JiraService {
    private final MyDatabase database;

    private final String prefix = "jr_";


    public static void testSelectAll() throws ClassNotFoundException {
        final String prefix = "jr_";
        MyDatabase database = new MyDatabase();
        try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Ouvrir une connexion à la base de données
            Connection con = database.getConnection(); //DriverManager.getConnection(DB_URL, USER, PASS);

            // Créer un objet Statement pour exécuter des requêtes SQL
            Statement stmt = con.createStatement();

            // Exécuter une requête SQL et obtenir un objet ResultSet
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+prefix+"task_evolution");

            // Extraire les données du ResultSet
            while (rs.next()) {
                // Récupérer les données par nom de colonne
                int id = rs.getInt("id");
                int open_task_count = rs.getInt("open_task_count");
                int blocked_task_count = rs.getInt("blocked_task_count");
                int backlog_task_count = rs.getInt("backlog_task_count");
                int in_progress_task_count = rs.getInt("in_progress_task_count");
                int in_review_task_count = rs.getInt("in_review_task_count");
                int done_task_count = rs.getInt("done_task_count");
                int old_task_count = rs.getInt("old_task_count");
                int new_task_count = rs.getInt("new_task_count");
                Date statistic_date = rs.getDate("statistic_date");
                int assigneeId = rs.getInt("assigneeId");

                // Afficher les données
                System.out.println("ID: " + id);
                System.out.println("Open task count: " + open_task_count);
                System.out.println("Blocked task count: " + blocked_task_count);
                System.out.println("Backlog task count: " + backlog_task_count);
                System.out.println("In progress task count: " + in_progress_task_count);
                System.out.println("In review task count: " + in_review_task_count);
                System.out.println("Done task count: " + done_task_count);
                System.out.println("Old task count: " + old_task_count);
                System.out.println("New task count: " + new_task_count);
                System.out.println("Statistic date: " + statistic_date);
                System.out.println("Assignee ID: " + assigneeId);
            }

            // Fermer la connexion et les autres ressources
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            // Gérer les exceptions
            e.printStackTrace();
        }
    }

    public JiraServiceImp() throws ClassNotFoundException {
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

        // Parcourir le ResultSet
        while (rs.next()) {
            // Créer un objet TaskEvolution avec les valeurs du ResultSet
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
    public void addTaskEvolution(TaskEvolution te) throws SQLException {
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
        ps.setDate(10, (Date) te.getStatisticDate());
        ps.setInt(11, te.getAssigneeId());

        int rows = ps.executeUpdate();

        con.close();
    }

    @Override
    public void deleteTaskEvolution(int id) throws SQLException {
        Connection con = database.getConnection();
        String sql = "DELETE FROM "+prefix+"task_evolution WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        con.close();
    }

    @Override
    public void updateTaskEvolution(TaskEvolution te) throws TaskEvolutionException, SQLException {
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

        int row = ps.executeUpdate();
        con.close();

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
            int jiraAccountId = rs.getInt("jiraAccountId");

            assignee = new Assignee(id, name, email, jiraAccountId);

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
            int jiraAccountId = rs.getInt("jiraAccountId");

            Assignee a = new Assignee(id, name, email, jiraAccountId);

            list.add(a);
        }


        con.close();

        return list;
    }

    @Override
    public void addAssignee(Assignee assignee) throws SQLException {
        Connection con = database.getConnection();
        String sql = "INSERT INTO "+prefix+"assignee (id, name, email, jiraAccountId) VALUES (?, ?, ?, ?);";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, assignee.getId());
        ps.setString(2, assignee.getName());
        ps.setString(3, assignee.getEmail());
        ps.setInt(4, assignee.getJiraAccountId());

        con.close();
    }

    @Override
    public void deleteAssignee(int id) throws SQLException {
        Connection con = database.getConnection();
        String sql = "DELETE FROM "+prefix+"assignee WHERE id = ?;\n";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        int rows = ps.executeUpdate();

        con.close();
    }

    @Override
    public void updateAssignee(Assignee assignee) throws AssigneeException, SQLException {
        Connection con = database.getConnection();
        String sql = "UPDATE "+prefix+"assignee SET name = ?, email = ?, jiraAccountId = ? WHERE id = ?;\n";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, assignee.getName());
        ps.setString(2, assignee.getEmail());
        ps.setInt(3, assignee.getJiraAccountId());
        ps.setInt(4, assignee.getId());

        int rows = ps.executeUpdate();

        con.close();

    }
}
