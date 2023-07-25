/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package corbaserver;

//import BankApp.OperationBancaire;
//import BankApp.OperationBancaireHelper;
import GetData.RetrieveData;
import GetData.RetrieveDataHelper;
//import JiraIssue.Assignee;
//import JiraIssue.TaskEvolution;
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
            System.out.println("Erreur lors de l'ouverture du serveur "+e);
        }
    }
    
    
    
    
    
}
