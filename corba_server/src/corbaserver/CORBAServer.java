/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package corbaserver;

//import BankApp.OperationBancaire;
//import BankApp.OperationBancaireHelper;
import GetData.RetrieveData;
import GetData.RetrieveDataHelper;
import JiraIssue.Assignee;
import JiraIssue.TaskEvolution;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/**
 *
 * @author lordyhas
 */
public class CORBAServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        getDataTest();
        //return;
        try {
            
    
            // initialisation du bus ORB
            ORB orb = ORB.init(args, null);
            // activation du POA
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();
            
            //enregister le servant dans ORB
            JiraServant servant = new JiraServant();
            servant.setOrb(orb);
            
            //avoir la reference du servant
            org.omg.CORBA.Object ref = poa.servant_to_reference(servant);
            RetrieveData href = RetrieveDataHelper.narrow(ref);
            
            // faire appel au service CORBA
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NameComponent path[] = ncRef.to_name("RetrieveData");
            // demarrer la communication
            ncRef.rebind(path, href);
            System.out.println("Server ready and waiting...");
            orb.run();   
            
        }catch(Exception e){
            System.out.println("Erreur lors de l ouverture du serveur");
        }
    }
    
    
    static void getDataTest() {
        String sparkUrl = "http://localhost:4567/jira/services";
        //http://localhost:4567/jira/services/assignees
        /// get all assignees from spark api
        try{
            String assigneesJson = Http.get(sparkUrl+"/assignees");

            /// get all tasks from spark api
            String tasksJson = Http.get(sparkUrl+"/tasks");
            
            System.out.println(assigneesJson);
            
            
            JsonObject jsonObject = new Gson().fromJson(assigneesJson, JsonObject.class);

            // Get the value of the key as a JsonElement
            JsonElement dataElement = jsonObject.get("data");

            // Convert the JsonElement to an array of User objects using Gson
            //User[] users = gson.fromJson(dataElement, User[].class);

            
            /// convert assigneesJson to list of assignees
            List<Assignee> assignees = new Gson().fromJson(dataElement, new TypeToken<List<Assignee>>(){}.getType());
            //List<TaskEvolution> tasks = new Gson().fromJson(tasksJson, new TypeToken<List<TaskEvolution>>(){}.getType());

            System.out.println("Here are assignee : ");
            for(Assignee assignee : assignees){
                System.out.println(assignee);
            }
        }catch (IOException e) {
            System.out.println("Error : "+e);
        }
    }
    
    
}
