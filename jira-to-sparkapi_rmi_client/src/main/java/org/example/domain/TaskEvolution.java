package org.example.domain;

import java.io.Serializable;
import java.util.Date;

public class TaskEvolution implements Serializable {
    private final int id;
    private int openTaskCount;
    private int blockedTaskCount;
    private int backlogTaskCount;
    private int inProgressTaskCount;
    private int inReviewTaskCount;
    private int doneTaskCount;
    private int oldTaskCount;
    private int newTaskCount;
    private Date statisticDate;
    private int assigneeId;

    public TaskEvolution(
            int id,
            int openTaskCount,
            int blockedTaskCount,
            int backlogTaskCount,
            int inProgressTaskCount,
            int inReviewTaskCount,
            int doneTaskCount,
            int oldTaskCount,
            int newTaskCount,
            Date statisticDate,
            int assigneeId
    ) {
        this.id = id;
        this.openTaskCount = openTaskCount;
        this.blockedTaskCount = blockedTaskCount;
        this.backlogTaskCount = backlogTaskCount;
        this.inProgressTaskCount = inProgressTaskCount;
        this.inReviewTaskCount = inReviewTaskCount;
        this.doneTaskCount = doneTaskCount;
        this.oldTaskCount = oldTaskCount;
        this.newTaskCount = newTaskCount;
        this.statisticDate = statisticDate;
        this.assigneeId = assigneeId;
    }

    @Override
    public String toString() {
        return "TaskEvolution{" +
                "id="+this.id+", " +
                "openTaskCount="+this.openTaskCount+", " +
                "blockedTaskCount="+this.blockedTaskCount+", " +
                "inProgressTaskCount="+this.inProgressTaskCount+", " +
                "inReviewTaskCount="+this.inReviewTaskCount+", " +
                "doneTaskCount="+this.doneTaskCount+", " +
                "oldTaskCount="+this.oldTaskCount+", " +
                "newTaskCount="+this.newTaskCount+", " +
                "statisticDate="+this.statisticDate+", " +
                "assigneeId="+this.assigneeId+
                "}";
    }

    public int getId() {
        return id;
    }

    public int getOpenTaskCount() {
        return openTaskCount;
    }

    public void setOpenTaskCount(int openTaskCount) {
        this.openTaskCount = openTaskCount;
    }

    public int getBlockedTaskCount() {
        return blockedTaskCount;
    }

    public void setBlockedTaskCount(int blockedTaskCount) {
        this.blockedTaskCount = blockedTaskCount;
    }

    public int getBacklogTaskCount() {
        return backlogTaskCount;
    }

    public void setBacklogTaskCount(int backlogTaskCount) {
        this.backlogTaskCount = backlogTaskCount;
    }

    public int getInProgressTaskCount() {
        return inProgressTaskCount;
    }

    public void setInProgressTaskCount(int inProgressTaskCount) {
        this.inProgressTaskCount = inProgressTaskCount;
    }

    public int getInReviewTaskCount() {
        return inReviewTaskCount;
    }

    public void setInReviewTaskCount(int inReviewTaskCount) {
        this.inReviewTaskCount = inReviewTaskCount;
    }

    public int getDoneTaskCount() {
        return doneTaskCount;
    }

    public void setDoneTaskCount(int doneTaskCount) {
        this.doneTaskCount = doneTaskCount;
    }

    public int getOldTaskCount() {
        return oldTaskCount;
    }

    public void setOldTaskCount(int oldTaskCount) {
        this.oldTaskCount = oldTaskCount;
    }

    public int getNewTaskCount() {
        return newTaskCount;
    }

    public void setNewTaskCount(int newTaskCount) {
        this.newTaskCount = newTaskCount;
    }

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

}
