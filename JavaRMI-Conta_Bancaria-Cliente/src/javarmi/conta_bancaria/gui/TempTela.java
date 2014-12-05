/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi.conta_bancaria.gui;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceServ;

/**
 *
 * @author lucasmelocvl
 */
public class TempTela extends UnicastRemoteObject{
    
    InterfaceServ refServ;
    InterfaceCli refCli;
    Scanner sc = new Scanner(System.in);
    
    public TempTela(InterfaceServ refS, InterfaceCli refC) throws RemoteException{
        refServ = refS;
        refCli = refC;
        System.out.println("Seja bem-vindo!");
        System.out.println("Para começar, primeiro, informe seus dados...\n"); 
        this.criarConta();
    }
    
    public void criarConta() throws RemoteException{
        String nome = this.inserirNome();
        String senha = this.inserirSenha();
        boolean poupanca = this.isPoupanca();
        boolean receberNotificacao = this.isReceberNotif();

        //TelaInicial tela = new TelaInicial();

        refServ.criarConta(nome, senha, poupanca, receberNotificacao, refCli);
    }
    
    public String inserirNome(){
        System.out.println("Nome:");
        String nome = sc.nextLine();
        return nome;
    }
    
    public String inserirSenha(){
        System.out.println("Senha:");
        String senha = sc.nextLine();
        return senha;
    }
            
    public boolean isPoupanca(){
        System.out.println("Deseja criar uma conta poupança? S/N");
        try{
            String resposta = sc.next();
            return resposta.contains("s");
        }catch(Exception e){
            System.out.println("Entrada inválida.");
            return false;
        }
    }
    
    public boolean isReceberNotif(){
        System.out.println("Deseja receber notificações de eventos? S/N");
        try{
            String resposta = sc.next();
            return resposta.contains("s");
        }catch(Exception e){
            System.out.println("Entrada inválida.");
            return false;
        }
    }
    
    /**
     * Esperar próximo comando.
     * Função que espera o próximo comando do usuário.
     * @throws java.rmi.RemoteException
     */
    public void esperarProxComando() throws RemoteException{
        String numConta;
        String senha;
        float valor;
        
        System.out.println("\nQuais ações deseja realizar?");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Depositar");
        System.out.println("3. Sacar");
        System.out.println("4. Transferências\n");
        int acao = sc.nextInt();
        switch(acao){
            case 1:
                sc.nextLine();
                System.out.println("Digite o numero da sua conta:");
                numConta = sc.nextLine();
                System.out.println("Digite sua senha:");
                senha = sc.nextLine();
                refServ.consultarSaldo(numConta, senha, refCli);
                break;
            case 2:
                sc.nextLine();
                System.out.println("Digite o numero da conta que será depositado:");
                numConta = sc.nextLine();
                System.out.println("Digite o valor a ser depositado");
                valor = (float) sc.nextFloat();
                sc.nextLine();
                refServ.depositar(numConta, valor, refCli);
                break;
            case 3:
                sc.nextLine();
                System.out.println("Digite o numero da sua conta:");
                numConta = sc.nextLine();
                System.out.println("Digite sua senha:");
                senha = sc.nextLine();
                System.out.println("Digite o valor a ser sacado");
                valor = (float) sc.nextFloat();
                refServ.sacar(numConta, senha, valor, refCli);
                break;
            case 4:
                this.opcoesTransferencias();
                break;                
        }
    }
    
    /**
     * Opções de transferência.
     * Imprime as opções de transferência bancária.
     */
    public void opcoesTransferencias() throws RemoteException{
        String numConta;
        String senha;
        float valor;
        int banco;
        String poup;
        boolean poupanca;
        String contaBenef;
        
        System.out.println("\nQue tipo de transferência deseja realizar?");
        System.out.println("1. Para conta-corrente");
        System.out.println("2. Para poupança");
        System.out.println("3. DOC");
        System.out.println("4. TED\n");
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
                refServ.realizarTransferenciaCC(numConta, senha, valor, contaBenef, refCli);
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
                refServ.realizarTransferenciaCP(numConta, senha, valor, contaBenef, refCli);
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
                System.out.println("É conta poupança? S/N");
                poup = sc.next();
                poupanca = poup.contains("s");
                System.out.println("Digite o numero da conta do beneficiário:");
                contaBenef = sc.nextLine();
                refServ.realizarTransferenciaDOC(numConta, senha, valor, banco, poupanca, contaBenef, refCli);
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
                poup = sc.next();
                poupanca = poup.contains("s");
                System.out.println("Digite o numero da conta do beneficiário:");
                contaBenef = sc.nextLine();
                refServ.realizarTransferenciaTED(numConta, senha, valor, banco, poupanca, contaBenef, refCli);
        }
    }
    
}
