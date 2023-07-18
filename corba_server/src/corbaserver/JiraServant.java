/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corbaserver;

import GetData.*;
import JiraIssue.Assignee;
import JiraIssue.TaskEvolution;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.omg.CORBA.ORB;




/**
 *
 * @author lordyhas
 */
public class JiraServant extends RetrieveDataPOA {
    private ORB orb;
    
    private int port = 4567;
    private String url = "http://localhost:4567/jira/services";   
    
    public void setOrb(ORB orb){
        this.orb = orb;
    }

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
            
            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);

            // Get the value of the key as a JsonElement
            JsonElement dataElement = jsonObject.get("data");
            
            //Type listType = new TypeToken<List<Assignee>>(){}.getType();

            // Convertir le JSON en une List<Person>
            Assignee[] list = new Gson().fromJson(dataElement, Assignee[].class);
            
            Assignee[] assignees = list;
            
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
            
            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);

            // Get the value of the key as a JsonElement
            JsonElement dataElement = jsonObject.get("data");
            
            //Type listType = new TypeToken<List<TaskEvolution>>(){}.getType();
            TaskEvolution[] tasks = new Gson().fromJson(dataElement, TaskEvolution[].class);
            
            return tasks;
            
        } catch (IOException ex) {
            Logger.getLogger(JiraServant.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHello() {
        return "Hello Word";
    }

    
    
}
