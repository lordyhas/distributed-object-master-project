package org.example.domain;

import java.io.Serializable;

public class Assignee implements Serializable {
    private final int id;
    private String name;
    private final String email;
    private String jiraAccountId;

    public Assignee(int id, String name, String email, String jiraAccountId) {
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

    public String getJiraAccountId() {
        return jiraAccountId;
    }

    public void setJiraAccountId(String jiraAccountId) {
        this.jiraAccountId = jiraAccountId;
    }


}
