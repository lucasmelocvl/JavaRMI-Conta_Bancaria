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
            receberNotificacao = false;
            
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
                
                refServ.consultarSaldo(numConta, senha, this);
                refServ.depositar(150.50, this);
                refServ.consultarSaldo(numConta, senha, this);
                
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
                System.out.println("Conta poupança");
            else System.out.println("Conta corrente");
            System.out.println("Seu saldo é de: " + saldo);
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
    public void ReceberSaque(float valor) throws RemoteException
    {
        try{

        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void retDepositar(float valor) throws RemoteException
    {
        try{

        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}