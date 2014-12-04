/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi.conta_bancaria.impl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javarmi.conta_bancaria.interfaces.InterfaceServ;
import java.rmi.server.UnicastRemoteObject;
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceConta;

/**
 *
 * @author Lucas
 */
public class ServImpl extends UnicastRemoteObject implements InterfaceServ{

    InterfaceCli refCli;
    ContaImpl contaCli;
    
    public ServImpl() throws RemoteException, AlreadyBoundException
    {
        //Cria o registro para receber as referencias, para a porta 1099, local
        Registry referenciaServicoNome = LocateRegistry.createRegistry(1099);
        
        //A classe é associada a um nome para ser acessado externamente
        //(Registra uma referencia de objeto remoto)
        referenciaServicoNome.rebind("Conta Bancária", this);
        
        System.out.println("Serviços bancárias iniciados..\n");
        
    }

    @Override
    public void criarConta(String nome, String senha, InterfaceCli ref) throws RemoteException
    {
        try{
            refCli = ref;
            contaCli = new ContaImpl(nome, senha, ref);
            InterfaceConta refConta = (InterfaceConta) contaCli;
            refCli.retConta(true, refConta);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public float consultarSaldo(InterfaceCli ref) throws RemoteException
    {
        try{
            ref.exibirSaldo(contaCli.getSaldo());
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return (float) 0.0;
    }

    @Override
    public void realizarTransferencia(double valor, InterfaceCli ref) throws RemoteException
    {
        try{
            
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void sacar(double valor, InterfaceCli ref) throws RemoteException
    {
        try{
            
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void depositar(double valor, InterfaceCli ref) throws RemoteException
    {
        try{
            
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void registrarInteresse(InterfaceCli ref) throws RemoteException
    {
        try{
            
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
}
