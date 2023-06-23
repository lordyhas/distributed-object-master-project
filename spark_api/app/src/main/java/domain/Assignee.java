package domain;

public class Assignee {
    private final int id;
    private String name;
    private final String email;
    private int jiraAccountId;

    public Assignee(int id, String name, String email, int jiraAccountId) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.jiraAccountId = jiraAccountId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public int getJiraAccountId() {
        return jiraAccountId;
    }

    public void setJiraAccountId(int jiraAccountId) {
        this.jiraAccountId = jiraAccountId;
    }


}
