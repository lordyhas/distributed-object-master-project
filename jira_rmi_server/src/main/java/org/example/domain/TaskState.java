package org.example.domain;

public enum TaskState {
    OPEN("Open"),
    BLOCKED("Blocked"),
    BACKLOG("Backlog"),
    IN_PROGRESS("In Progress"),
    PEER_REVIEW("peer review"),
    DONE("Done"),
    OLD_TASK("OLD TASK"),
    NEW_TASK("NEW TASK");

    final private String status;


    TaskState(String status) {
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
