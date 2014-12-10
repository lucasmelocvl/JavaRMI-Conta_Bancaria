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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public String numAgencia;
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
     * @return 
     * @throws java.rmi.RemoteException
     */
    public boolean criarConta() throws RemoteException
    {
       boolean ret = refServ.criarConta(nomeCli, senhaCli, numConta, numAgencia, banco, isPoupanca, isRecebNotif, this);
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
    public float consultarSaldo() throws RemoteException{
        float saldo = refServ.consultarSaldo(numConta, senhaCli, this);
        return saldo;
    }
    
    /**
     * Realiza a solicitação do saldo.
     * @throws RemoteException 
     */
    public boolean depositar(float valor) throws RemoteException{
        boolean ret = refServ.depositar(numConta, valor, this);
        return ret;
    }
    
    /**
     * Realiza a solicitação do saldo.
     * @throws RemoteException 
     */
    public boolean sacar(float valor) throws RemoteException{
        boolean ret = refServ.sacar(numConta, senhaCli, valor, this);
        return ret;
    }
    
    @Override
    public void retConta() throws RemoteException
    {
        
    }

    @Override
    public void exibirSaldo(float saldo) throws RemoteException
    {

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
            String msg = "Notificação automática:" +
            "Foi depositado em sua conta um valor de R$" + valor;
            System.out.println(msg);
            //JOptionPane.showMessageDialog(null, msg);
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