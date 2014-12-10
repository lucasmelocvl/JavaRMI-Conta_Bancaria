/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.cliente;

import javarmi.conta_bancaria.impl.CliImpl;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 *
 * @author lucasmelocvl
 */
public class JavaRMIConta_BancariaCliente 
{

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     */
    /*public static void main(String[] args) throws RemoteException, NotBoundException 
    {
        Registry referenciaServicoNomes;
        referenciaServicoNomes = LocateRegistry.getRegistry("localhost", 1099);
        CliImpl cliente = new CliImpl();
        cliente.receberRefServ(referenciaServicoNomes);
    }*/
    
}
