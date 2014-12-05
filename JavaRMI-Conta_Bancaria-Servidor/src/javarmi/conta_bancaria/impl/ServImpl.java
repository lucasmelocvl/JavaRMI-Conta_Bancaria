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
import java.rmi.server.UnicastRemoteObject;
import javarmi.conta_bancaria.contas.MapContas;
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceConta;
import javarmi.conta_bancaria.interfaces.InterfaceServ;

/**
 *
 * @author Lucas
 */
public class ServImpl extends UnicastRemoteObject implements InterfaceServ{

    public static MapContas contas;
    
    public ServImpl() throws RemoteException, AlreadyBoundException
    {
        try{
            //Cria o registro para receber as referencias, para a porta 1099, local
            Registry referenciaServicoNome = LocateRegistry.createRegistry(1099);

            //A classe é associada a um nome para ser acessado externamente
            //(Registra uma referencia de objeto remoto)
            referenciaServicoNome.rebind("Conta Bancária", this);

            //Inicia o mapa hash de contas
            contas = new MapContas();

            System.out.println("Serviços bancárias iniciados..\n");
        }catch(RemoteException e){
            System.out.println(e.getMessage());
        }
        
    }

    @Override
    public void criarConta(String nome, String senha, boolean poupanca, boolean receberNotificacao, InterfaceCli ref) throws RemoteException
    {
        try{
            InterfaceCli refCli = ref;
            ContaImpl contaCli = new ContaImpl(nome, senha, poupanca, receberNotificacao, ref);
            InterfaceConta refConta = (InterfaceConta) contaCli;
            refCli.retConta(true, refConta);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void consultarSaldo(String nome, String senha, InterfaceCli ref) throws RemoteException
    {
        try{
            //ref.exibirSaldo(contaCli.getSaldo());
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void realizarTransferencia(String nome, String senha, double valor, InterfaceCli ref) throws RemoteException
    {
        try{
            
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void sacar(String nome, String senha, double valor, InterfaceCli ref) throws RemoteException
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
            //ref.retDepositar(valor);
            
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
