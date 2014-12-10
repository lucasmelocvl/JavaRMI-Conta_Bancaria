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
import javarmi.conta_bancaria.gui.CriarConta;
import javarmi.conta_bancaria.gui.OpcoesOperacoes;
import javarmi.conta_bancaria.gui.TempTela;
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceConta;
import javarmi.conta_bancaria.interfaces.InterfaceServ;
import javax.swing.JOptionPane;

/**
 *
 * @author lucasmelocvl
 */

public class CliImpl extends UnicastRemoteObject implements InterfaceCli
{
    public String nomeCli;
    public String senhaCli;
    public String numConta;
    public boolean isPoupanca;
    public boolean isRecebNotif;
    public int banco;
    public float valor;
    public String contaBenef;
    
    InterfaceServ refServ;
    InterfaceConta contaCli;
    
    //Scanner sc = new Scanner(System.in);
    //TempTela tela;
    
    public CliImpl() throws RemoteException{
        
    }
    
    /**
     * @param referenciaServicoNomes Refencia do Servidor
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
    */
    public void receberRefServ(Registry referenciaServicoNomes) throws RemoteException, NotBoundException
    {
        try{
            refServ = (InterfaceServ) referenciaServicoNomes.lookup("Conta Bancária");
            CriarConta view = new CriarConta();
            view.setVisible(true);
        }catch(RemoteException e){
            System.out.println(e.getMessage());
            String msg = "Servidor bancário fora do ar!";
            JOptionPane.showMessageDialog(null, msg);
            System.exit(0);
        }
    }
    
    /**
     * Criar conta.
     * Cria uma nova conta para um cliente, enviando as informações
     * para o servidor.
     * @throws java.rmi.RemoteException
     */
    public void criarConta() throws RemoteException
    {
       refServ.criarConta(nomeCli, senhaCli, isPoupanca, isRecebNotif, this);
    }
    
    //######################
    
    /**
     * Esperar próximo comando.
     * Função que espera o próximo comando do usuário.
     * @throws java.rmi.RemoteException
     */
    /*public void esperarProxComando() throws RemoteException{
        
        System.out.println("\nQuais ações deseja realizar?");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Depositar");
        System.out.println("3. Sacar");
        System.out.println("4. Transferências");
        System.out.println("5. Sair\n");
        int acao = sc.nextInt();
        switch(acao){
            case 1:
                sc.nextLine();
                System.out.println("Digite o numero da sua conta:");
                numConta = sc.nextLine();
                System.out.println("Digite sua senha:");
                senha = sc.nextLine();
                refServ.consultarSaldo(numConta, senha, this);
                break;
            case 2:
                sc.nextLine();
                System.out.println("Digite o numero da conta que será depositado:");
                numConta = sc.nextLine();
                System.out.println("Digite o valor a ser depositado");
                valor = (float) sc.nextFloat();
                sc.nextLine();
                refServ.depositar(numConta, valor, this);
                break;
            case 3:
                sc.nextLine();
                System.out.println("Digite o numero da sua conta:");
                numConta = sc.nextLine();
                System.out.println("Digite sua senha:");
                senha = sc.nextLine();
                System.out.println("Digite o valor a ser sacado");
                valor = (float) sc.nextFloat();
                refServ.sacar(numConta, senha, valor, this);
                break;
            case 4:
                this.opcoesTransferencias();
                break;   
            case 5:
                System.out.println("Até logo!");
                System.exit(0);
                break;
        }
    }*/
    
    /**
     * Opções de transferência.
     * Imprime as opções de transferência bancária.
     * @throws java.rmi.RemoteException
     */
    /*public void opcoesTransferencias() throws RemoteException{
        
        System.out.println("\nQue tipo de transferência deseja realizar?");
        System.out.println("1. Para conta-corrente");
        System.out.println("2. Para poupança");
        System.out.println("3. DOC");
        System.out.println("4. TED");
        System.out.println("5. Voltar ao menu principal\n");
        int acao = sc.nextInt();
        switch(acao){
            case 1:
                sc.nextLine();
                System.out.println("Digite o numero da sua conta:");
                numConta = sc.nextLine();
                System.out.println("Digite sua senha:");
                senha = sc.nextLine();
                System.out.println("Digite o valor a ser transferido");
                valor = (float) sc.nextFloat();
                sc.nextLine();
                System.out.println("Digite o numero da conta do beneficiário:");
                contaBenef = sc.nextLine();
                refServ.realizarTransferenciaCC(numConta, senha, valor, contaBenef, this);
                break;
            case 2:
                sc.nextLine();
                System.out.println("Digite o numero da sua conta:");
                numConta = sc.nextLine();
                System.out.println("Digite sua senha:");
                senha = sc.nextLine();
                System.out.println("Digite o valor a ser transferido");
                valor = (float) sc.nextFloat();
                sc.nextLine();
                System.out.println("Digite o numero da conta do beneficiário:");
                contaBenef = sc.nextLine();
                refServ.realizarTransferenciaCP(numConta, senha, valor, contaBenef, this);
                break;
            case 3:
                sc.nextLine();
                System.out.println("Digite o numero da sua conta:");
                numConta = sc.nextLine();
                System.out.println("Digite sua senha:");
                senha = sc.nextLine();
                System.out.println("Digite o valor a ser transferido");
                valor = (float) sc.nextFloat();
                sc.nextLine();
                System.out.println("Digite o numero do banco do beneficiário:");
                banco = sc.nextInt();
                sc.nextLine();
                //System.out.println("É conta poupança? S/N");
                //poup = sc.next();
                //poupanca = poup.contains("s");
                System.out.println("Digite o numero da conta do beneficiário:");
                contaBenef = sc.nextLine();
                refServ.realizarTransferenciaDOC(numConta, senha, valor, banco, poupanca, contaBenef, this);
                break;
            case 4:
                sc.nextLine();
                System.out.println("Digite o numero da sua conta:");
                numConta = sc.nextLine();
                System.out.println("Digite sua senha:");
                senha = sc.nextLine();
                System.out.println("Digite o valor a ser transferido");
                valor = (float) sc.nextFloat();
                System.out.println("Digite o numero do banco do beneficiário:");
                banco = sc.nextInt();
                sc.nextLine();
                System.out.println("É conta poupança? S/N");
                //poup = sc.next();
                //poupanca = poup.contains("s");
                //System.out.println("Digite o numero da conta do beneficiário:");
                contaBenef = sc.nextLine();
                refServ.realizarTransferenciaTED(numConta, senha, valor, banco, poupanca, contaBenef, this);
                break;
            case 5:
                this.esperarProxComando();
                break;
        }
    }*/
    
    //######################
    
    @Override
    public void retConta(boolean status, InterfaceConta ref) throws RemoteException
    {
        try{
            if(status){
                System.out.println("\nSua conta foi criada com sucesso!"); 
                contaCli = ref;
                nomeCli = contaCli.getNomeCliente();
                senhaCli = contaCli.getSenhaCli();
                numConta = contaCli.getNumConta();
                
                String msg = 
                    "Conta criada com sucesso! \n" +
                    "Nome: " + nomeCli + "\n" +
                    "Senha: " + senhaCli + "\n" +
                    "Numero da conta: "+ numConta + "\n";
                JOptionPane.showMessageDialog(null, msg);
                OpcoesOperacoes opOp = new OpcoesOperacoes();
                opOp.setVisible(true);
            }else{   
                String msg = "Não foi possível cadastrar a nova conta";
                JOptionPane.showMessageDialog(null, msg);
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
            //this.esperarProxComando();
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
        System.out.println(msg); 
    }
    
    @Override
    public void ReceberSaque(float valor) throws RemoteException
    {
        try{
            System.out.printf("\nVocê realizou um saque de R$%.2f\n", valor);
            //this.esperarProxComando();
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
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void contaInexistente() throws RemoteException
    {
        try{
            System.out.println("\nNúmero da conta inexistente, tente novamente ou consulte o seu gerente.");
            //this.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public void senhaIncorreta() throws RemoteException
    {
        try{
            System.out.println("\nSenha incorreta, tente novamente.");
            //this.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public void saldoInsuficiente() throws RemoteException
    {
        try{
            System.out.println("\nSaldo insuficiente, não foi possível realizar a operação.");
            //this.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }       
    }

    @Override
    public void msgServer(String msg) throws RemoteException
    {
        try{
            System.out.println(msg);
            //this.esperarProxComando();
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }     
    }
    
}