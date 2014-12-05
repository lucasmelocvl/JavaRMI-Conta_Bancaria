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
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceConta;
import javarmi.conta_bancaria.interfaces.InterfaceServ;
import javarmi.conta_bancaria.swing.TelaInicial;

/**
 *
 * @author lucasmelocvl
 */

public class CliImpl extends UnicastRemoteObject implements InterfaceCli
{
    
    InterfaceServ refServ;
    InterfaceConta contaCli;
    
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
        
        Scanner sc = new Scanner(System.in);
        
        try{
            refServ = (InterfaceServ) referenciaServicoNomes.lookup("Conta Bancária");
            System.out.println("Nome: Lucas de Melo Carvalho");
            nome = "Lucas de Melo Carvalho";

            //System.out.println("Nome: ");
            //nome = sc.nextLine();

            System.out.println("Senha: senha123");
            senha = "senha123";

            poupanca = false;
            receberNotificacao = true;
            
            TelaInicial tela = new TelaInicial();

            //System.out.println("Senha: ");
            //senha = sc.nextLine();
            
            refServ.criarConta(nome, senha, poupanca, receberNotificacao, this);
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
                System.out.println("\nConta do cliente criado com sucesso!"); 
                contaCli = ref;
                String nome = contaCli.getNomeCliente();
                String senha = contaCli.getSenhaCli();
                String numConta = contaCli.getNumConta();
                
                System.out.println("Nome: " + nome + "");
                System.out.println("Senha: " + senha + "");
                System.out.println("Numero da conta: "+ numConta + "");
                
                
                //1.Consulta de saldo
                refServ.consultarSaldo(numConta, senha, this);
                //4.Depósito
                refServ.depositar(numConta, (float)150.55, this);
                //3.Saque
                refServ.sacar(numConta, senha, (float)22.90, this);
                //2. Transferencia CC
                System.out.println("\nDigite o número da conta a ser depositado:");
                Scanner sc = new Scanner(System.in);
                String contaBenef = sc.next();
                float valor = (float)57.80;
                refServ.realizarTransferenciaCC(numConta, senha, valor, contaBenef, this);
                System.out.println("\nDOC");
                refServ.realizarTransferenciaDOC(numConta, senha, (float)11.02, 103, false, "12345678-9", this);
                System.out.println("\nTED");
                refServ.realizarTransferenciaTED(numConta, senha, (float)11.02, 103, false, "12345678-9", this);
                
                
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
                System.out.println("Conta poupança:");
            else System.out.println("Conta corrente:");
            System.out.println("Seu saldo é de: R$" + saldo);
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
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    }
    
    @Override
    public void ReceberSaque(float valor) throws RemoteException
    {
        try{
            System.out.println("\nVocê realizou um saque de R$" + valor);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void retDepositar(float valor) throws RemoteException
    {
        try{
            System.out.println("\nNotificação automática:");
            System.out.println("Foi depositado em sua conta um valor de R$" + valor);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void contaInexistente() throws RemoteException
    {
        try{
            System.out.println("\nNúmero da conta inexistente, tente novamente ou consulte o seu gerente.");
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public void senhaIncorreta() throws RemoteException
    {
        try{
            System.out.println("\nSenha incorreta, tente novamente.");
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public void saldoInsuficiente() throws RemoteException
    {
        try{
            System.out.println("\nSaldo insuficiente, não foi possível realizar a operação.");
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }       
    }

    @Override
    public void msgServer(String msg) throws RemoteException
    {
        try{
            System.out.println(msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }     
    }
    
}