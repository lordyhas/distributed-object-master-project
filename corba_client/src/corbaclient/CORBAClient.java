/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package corbaclient;


import GetData.RetrieveData;
import GetData.RetrieveDataHelper;
import JiraIssue.Assignee;
import JiraIssue.TaskEvolution;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 *
 * @author lordyhas
 */
public class CORBAClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            RetrieveData data = RetrieveDataHelper.narrow(ncRef.resolve_str("RetrieveData"));
            
            System.out.println("message from server : "+data.getHello());
            
            Assignee[] assignees = data.getAllAssignee();
            TaskEvolution[] tasks = data.getAllTaskEvolution();
            
            System.out.println("Here are assignee : ");
            for(Assignee assignee : assignees){
                System.out.println("Assignee{id="+assignee.id+", name="+assignee.name+", jiraAccountId="+assignee.jiraAccountId+"}");
            }
            
            System.out.println("Here are assignee : ");
            for(TaskEvolution task : tasks){
                System.out.println("TaskEvolution{" +
                "id="+task.id+", " +
                "openTaskCount="+task.openTaskCount+", " +
                "blockedTaskCount="+task.blockedTaskCount+", " +
                "backlogTaskCount="+task.backlogTaskCount+", " +
                "inProgressTaskCount="+task.inProgressTaskCount+", " +
                "inReviewTaskCount="+task.inReviewTaskCount+", " +
                "doneTaskCount="+task.doneTaskCount+", " +
                "oldTaskCount="+task.oldTaskCount+", " +
                "newTaskCount="+task.newTaskCount+", " +
                "statisticDate="+task.statisticDate+", " +
                "assigneeId="+task.assigneeId+
                "}");
            }
        }catch(Exception e){
            System.out.println("Erreur lors de l'ouverture du serveur \nError : "+e);
        }
        
    }
    
}
