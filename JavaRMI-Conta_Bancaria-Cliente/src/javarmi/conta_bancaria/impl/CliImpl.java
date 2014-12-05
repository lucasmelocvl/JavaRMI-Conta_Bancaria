/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi.conta_bancaria.impl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import javarmi.conta_bancaria.gui.TelaInicial;
import javarmi.conta_bancaria.gui.TempTela;
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceConta;
import javarmi.conta_bancaria.interfaces.InterfaceServ;

/**
 *
 * @author lucasmelocvl
 */

public class CliImpl extends UnicastRemoteObject implements InterfaceCli
{
    
    InterfaceServ refServ;
    InterfaceConta contaCli;
    Scanner sc = new Scanner(System.in);
    TempTela tela;
    
    /**
     * @param referenciaServicoNomes Refencia do Servidor
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
    */
    public CliImpl(Registry referenciaServicoNomes) throws RemoteException, NotBoundException
    {
        String nome;
        String senha;
        boolean poupanca;
        boolean receberNotificacao;
        
        try{
            refServ = (InterfaceServ) referenciaServicoNomes.lookup("Conta Bancária");
            
            tela = new TempTela(refServ, this);
            
        }catch(RemoteException e){
            System.out.println(e.getMessage());
            System.out.println("Servidor bancário fora do ar!");
            System.exit(0);
        }
    }
    
    @Override
    public void retConta(boolean status, InterfaceConta ref) throws RemoteException
    {
        try{
            if(status){
                System.out.println("\nSua conta foi criada com sucesso!"); 
                contaCli = ref;
                String nome = contaCli.getNomeCliente();
                String senha = contaCli.getSenhaCli();
                String numConta = contaCli.getNumConta();
                
                System.out.println("Nome: " + nome + "");
                System.out.println("Senha: " + senha + "");
                System.out.println("Numero da conta: "+ numConta + "");
                tela.esperarProxComando();
            }else{   
                System.out.println("Não foi possível cadastrar a nova conta");
            }
           /* contaCli = refConta;
            String nomeCliente = contaCli.getNomeCliente();
            String nomeBanco = contaCli.getNomeBanco();
            String numConta = contaCli.getNumConta();
            System.out.println("Conta do cliente " + nomeCliente + " criado com sucesso!"
                    + "Nome do banco: " + nomeBanco + " e conta nº"+ numConta);*/
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void exibirSaldo(float saldo) throws RemoteException
    {
        try{
            if(contaCli.getTipoConta()==013)
                System.out.println("\nConta poupança:");
            else System.out.println("Conta corrente:");
            System.out.printf("Seu saldo é de: R$%.2f\n", saldo);
            tela.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void retTransferencia(boolean status) throws RemoteException
    {
        try{

        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    
    @Override
    public void notifTransferencia(String msg) throws RemoteException
    {
        try{
            System.out.println(msg);
            tela.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    }
    
    @Override
    public void ReceberSaque(float valor) throws RemoteException
    {
        try{
            System.out.printf("\nVocê realizou um saque de R$%.2f\n", valor);
            tela.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void retDepositar(float valor) throws RemoteException
    {
        try{
            System.out.println("\nNotificação automática:");
            System.out.printf("Foi depositado em sua conta um valor de R$%.2f\n", valor);
            tela.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void contaInexistente() throws RemoteException
    {
        try{
            System.out.println("\nNúmero da conta inexistente, tente novamente ou consulte o seu gerente.");
            tela.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public void senhaIncorreta() throws RemoteException
    {
        try{
            System.out.println("\nSenha incorreta, tente novamente.");
            tela.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public void saldoInsuficiente() throws RemoteException
    {
        try{
            System.out.println("\nSaldo insuficiente, não foi possível realizar a operação.");
            tela.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }       
    }

    @Override
    public void msgServer(String msg) throws RemoteException
    {
        try{
            System.out.println(msg);
            tela.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }     
    }
    
}