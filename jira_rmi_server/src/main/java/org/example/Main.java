package org.example;

import org.example.rmi_server.JiraServant;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Main {
    public static void main(String[] args) {
        System.out.println("[Start RMI Server]\n");

        try{
            JiraServant servant  =  new JiraServant();
            String url = "rmi://localhost:5097/jira-api";
            LocateRegistry.createRegistry(5097);
            Naming.rebind(url, servant);

            System.out.println("Server status : on");
            System.out.println("Server ready...");
        }catch(RemoteException | MalformedURLException e){
            System.out.println("Failed to open the server");
        }

    }
}