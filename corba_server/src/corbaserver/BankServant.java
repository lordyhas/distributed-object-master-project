/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corbaserver;

import BankApp.OperationBancairePOA;
import org.omg.CORBA.ORB;

/**
 *
 * @author masj2413
 */
public class BankServant extends OperationBancairePOA {
    private ORB orb;
    private double amount = 2000;
    
    public void setOrb(ORB orb){
        this.orb = orb;
    }

    @Override
    public double balance() {
        return amount;
    }

    @Override
    public void depot(double amount) {
        this.amount +=amount;
    }

    @Override
    public void retrait(double amount) {
        this.amount -=amount;
    }
}
