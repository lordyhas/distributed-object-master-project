/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package corbaclient;


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
        // TODO code application logic here
         try {

            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            /*OperationBancaire op = OperationBancaireHelper.narrow(ncRef.resolve_str("OperationBancaire"));
            System.out.println("current balance: "+op.balance());
            op.depot(150);
            System.out.println("current balance: "+op.balance());
            op.retrait(300);
            System.out.println("current balance: "+op.balance());*/
           
            
        }catch(Exception e){
            System.out.println("Erreur lors de l ouverture du serveur"+e);
        }
        
    }
    
}
