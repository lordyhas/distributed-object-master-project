package org.example.issue;

import java.io.Serializable;
public class Assignee implements Serializable{
    private final int id;
    private String name;
    private final String email = null;
    private String jiraAccountId;

    public Assignee(int id, String name, String jiraAccountId) {
        super();
        this.id = id;
        this.name = name;
        //this.email = email;
        this.jiraAccountId = jiraAccountId;
    }

    @Override
    public String toString() {
        return "Assignee{id="+this.id+", name="+this.name+", jiraAccountId="+this.jiraAccountId+"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Assignee other = (Assignee) obj;
        return this.name.equals(other.name) &&
                this.jiraAccountId.equals(other.jiraAccountId);
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + jiraAccountId.hashCode();
        return result;
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



    public String getJiraAccountId() {
        return jiraAccountId;
    }

    public void setJiraAccountId(String jiraAccountId) {
        this.jiraAccountId = jiraAccountId;
    }


}