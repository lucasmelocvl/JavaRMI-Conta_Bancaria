/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi.conta_bancaria.impl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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
    
    public CliImpl() throws RemoteException, NotBoundException{
        Registry referenciaServicoNomes;
        referenciaServicoNomes = LocateRegistry.getRegistry("localhost", 1099);
        try
        {
            refServ = (InterfaceServ) referenciaServicoNomes.lookup("Conta Bancária");
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
    public boolean criarConta() throws RemoteException
    {
       boolean ret = refServ.criarConta(nomeCli, senhaCli, numConta, banco, isPoupanca, isRecebNotif, this);
       return ret;
    }

    /**
     * Valida as informações de um usuário.
     * @return
     * @throws RemoteException 
     */
    public boolean validarUsuario() throws RemoteException{
        boolean ret = refServ.validarUsuario(numConta, senhaCli, this);
        return ret;
    }
    
    /**
     * Realiza a solicitação do saldo.
     * @throws RemoteException 
     */
    public void consultarSaldo() throws RemoteException{
        refServ.consultarSaldo(numConta, senhaCli, this);
    }
    
    @Override
    public void retConta() throws RemoteException
    {
        try{
            System.out.println("\nSua conta foi criada com sucesso!"); 
            nomeCli = contaCli.getNomeCliente();
            senhaCli = contaCli.getSenhaCli();
            numConta = contaCli.getNumConta();

            String msg = 
                "Conta criada com sucesso! \n" +
                "Nome: " + nomeCli + "\n" +
                "Senha: " + senhaCli + "\n" +
                "Numero da conta: "+ numConta + "\n";
            JOptionPane.showMessageDialog(null, msg);
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
        String msg;
        try{
            if(contaCli.getTipoConta()==013)
                msg = "\nConta poupança:";
            else System.out.println("Conta corrente:");
                msg = "Seu saldo é de: R$%.2f\n"+saldo;
            JOptionPane.showMessageDialog(null, msg);
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
            String msg = "Número da conta inexistente, tente novamente ou consulte o seu gerente.";
            JOptionPane.showMessageDialog(null, msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public void senhaIncorreta() throws RemoteException
    {
        try{
            String msg = "Senha incorreta, tente novamente.";
            JOptionPane.showMessageDialog(null, msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public void saldoInsuficiente() throws RemoteException
    {
        try{
            String msg = "Saldo insuficiente, não foi possível realizar a operação.";
            JOptionPane.showMessageDialog(null, msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }      
    }

    @Override
    public void msgServer(String msg) throws RemoteException
    {
        try{
            JOptionPane.showMessageDialog(null, msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }     
    }
    
}