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
    
    public TempTela() throws RemoteException{
        /*refServ = refS;
        refCli = refC;*/
        System.out.println("Seja bem-vindo!");
        System.out.println("Para começar, primeiro, informe seus dados...\n"); 
        //this.criarConta();
    }
    
    /*public void criarConta() throws RemoteException{
        String nome = this.inserirNome();
        String senha = this.inserirSenha();
        boolean poupanca = this.isPoupanca();
        boolean receberNotificacao = this.isReceberNotif();

        //TelaInicial tela = new TelaInicial();

        refServ.criarConta(nome, senha, poupanca, receberNotificacao, refCli);
    }*/
    
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
    
}
