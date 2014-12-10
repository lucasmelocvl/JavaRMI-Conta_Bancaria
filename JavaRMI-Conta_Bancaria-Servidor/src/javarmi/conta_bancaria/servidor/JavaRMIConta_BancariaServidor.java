/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.servidor;

import javarmi.conta_bancaria.impl.ServImpl;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

/**
 * JavaRMIConta_BancariaServidor.
 * @author lucasmelocvl
 */
public class JavaRMIConta_BancariaServidor 
{

    /**
     * Main.
     * Inicia o servidor.
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     * @throws java.rmi.AlreadyBoundException
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException 
    {
        ServImpl servidor = new ServImpl();
    }
    
}
