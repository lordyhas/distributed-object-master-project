/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corbaserver;

import GetData.*;
import JiraIssue.Assignee;
import JiraIssue.TaskEvolution;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;




/**
 *
 * @author lordyhas
 */
public class JiraServant extends RetrieveDataPOA {
    
    String url = "http://localhost:4567/jira/services";

    @Override
    public Assignee getAssignee(int id)  {
        try {
            String data = Http.get(url+"/assignees:id", id);
            Assignee assignee = new Gson().fromJson(data, Assignee.class);
            
            return assignee;
            
        } catch (IOException ex) {
            Logger.getLogger(JiraServant.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    @Override
    public Assignee[] getAllAssignee() {
        try {
            String data = Http.get(url+"/assignees");
            Type listType = new TypeToken<List<Assignee>>(){}.getType();

            // Convertir le JSON en une List<Person>
            List<Assignee> list = new Gson().fromJson(data, listType);
            
            Assignee[] assignees = (Assignee[]) list.toArray();
            
            return assignees;
            
        } catch (IOException ex) {
            Logger.getLogger(JiraServant.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    @Override
    public TaskEvolution getTaskEvolution(int id) {
        try {
            String data = Http.get(url+"/tasks:id", id);
            
            TaskEvolution task = new Gson().fromJson(data, TaskEvolution.class);
            
            return task;
            
        } catch (IOException ex) {
            Logger.getLogger(JiraServant.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    @Override
    public TaskEvolution[] getAllTaskEvolution() {
        try {
            String data = Http.get(url+"/tasks");
            
            Type listType = new TypeToken<List<TaskEvolution>>(){}.getType();
            List<TaskEvolution> tasks = new Gson().fromJson(data, listType);
            
            return (TaskEvolution[]) tasks.toArray();
            
        } catch (IOException ex) {
            Logger.getLogger(JiraServant.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    
    
}
