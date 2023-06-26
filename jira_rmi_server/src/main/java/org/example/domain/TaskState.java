package org.example.domain;

public enum TaskState {
    OPENED("OPENED"),
    BLOCKED(""),
    BACKLOG("BACKLOG"),
    IN_PROGRESS("IN PROGRESS"),
    IN_REVIEW("IN REVIEW"),
    DONE("DONE"),
    OLD_TASK("OLD TASK"),
    NEW_TASK("NEW TASK");

    final private String status;


    TaskState(String status) {
        this.status = status;
    }

    String getStatus(){
        return this.status;
    }
}
