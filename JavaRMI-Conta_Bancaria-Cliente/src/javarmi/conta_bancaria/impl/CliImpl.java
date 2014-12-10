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
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceServ;
import javax.swing.JOptionPane;

/**
 * Implementação do cliente.
 * Classe responsável por implementar a InterfaceCli.
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
    
    InterfaceServ refServ;
    
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
     * @return saldo disponível.
     * @throws RemoteException 
     */
    public float consultarSaldo() throws RemoteException{
        float saldo = refServ.consultarSaldo(numConta, senhaCli, this);
        return saldo;
    }
    
    /**
     * Realiza a solicitação do saldo.
     * @param valor valor a ser depositado.
     * @return true se o depósito for realizado com sucesso.
     * @throws RemoteException 
     */
    public boolean depositar(float valor) throws RemoteException{
        boolean ret = refServ.depositar(numConta, valor, this);
        return ret;
    }
    
    /**
     * Realiza a solicitação do saldo.
     * @param valor
     * @return true se o saque for realizado com sucesso.
     * @throws RemoteException 
     */
    public boolean sacar(float valor) throws RemoteException{
        boolean ret = refServ.sacar(numConta, senhaCli, valor, this);
        return ret;
    }
    
    /**
     * Realiza transferencia para conta-corrente.
     * @param valor Valor a ser transferido.
     * @param contaBenef Conta do beneficiário.
     * @return true se a transferência for realizada com sucesso.
     * @throws RemoteException 
     */
    public boolean transferenciaCC(float valor, String contaBenef) throws RemoteException{
        boolean ret = refServ.realizarTransferenciaCC(numConta, senhaCli, valor, contaBenef, this);
        return ret;
    }
    
    /**
     * Realiza transferencia para conta poupança.
     * @param valor Valor a ser transferido.
     * @param contaBenef Conta do beneficiário.
     * @return true se a transferência for realizada com sucesso.
     * @throws RemoteException 
     */
    public boolean transferenciaCP(float valor, String contaBenef) throws RemoteException{
        boolean ret = refServ.realizarTransferenciaCP(numConta, senhaCli, valor, contaBenef, this);
        return ret;
    }
    
    /**
     * Realiza transferencia para conta-corrente.
     * @param valor Valor a ser transferido.
     * @param banco Numero do banco do beneficiário.
     * @param isPoupanca true se for conta poupança.
     * @param contaBenef Número da conta do beneficiário.
     * @return true se a transferência for realizada com sucesso.
     * @throws RemoteException 
     */
    public boolean transferenciaDOC(float valor, int banco, boolean isPoupanca, String contaBenef) throws RemoteException{
        boolean ret = refServ.realizarTransferenciaDOC(numConta, senhaCli, valor, banco, isPoupanca, contaBenef, this);
        return ret;
    }
    
    /**
     * Realiza transferencia para conta-corrente.
     * @param valor Valor a ser transferido.
     * @param banco Numero do banco do beneficiário.
     * @param isPoupanca true se for conta poupança.
     * @param contaBenef Número da conta do beneficiário.
     * @return true se a transferência for realizada com sucesso.
     * @throws RemoteException 
     */
    public boolean transferenciaTED(float valor, int banco, boolean isPoupanca, String contaBenef) throws RemoteException{
        boolean ret = refServ.realizarTransferenciaTED(numConta, senhaCli, valor, banco, isPoupanca, contaBenef, this);
        return ret;
    }
       
    /**
     * Notificação de transferência.
     * Se o cliente estiver habilitado para receber notificações automáticas,
     * toda a vez que for transferido um valor para a sua conta, o cliente é
     * notificado.
     * @param msg Mensagem do servidor.
     * @throws java.rmi.RemoteException
     */
    @Override
    public void notifTransferencia(String msg) throws RemoteException
    {
        System.out.println(msg); 
    }
    
    /**
     * Receber saque.
     * É exibido na tela o saque, caso ele tenha sido realizado, simulando o
     * recebimento do valor sacado.
     * @param valor Valor sacado.
     * @throws RemoteException 
     */
    @Override
    public void ReceberSaque(float valor) throws RemoteException
    {
        try{
            System.out.printf("\nVocê realizou um saque de R$%.2f\n", valor);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    /**
     * Retorno do depósito.
     * É informado ao cliente se o depósito foi executada com sucesso.
     * @param valor Valor que foi depositado.
     * @throws RemoteException 
     */
    @Override
    public void retDepositar(float valor) throws RemoteException
    {
        try{
            String msg = "Notificação automática:\n" +
            "Foi depositado em sua conta um valor de R$" + valor;
            System.out.println(msg);
            //JOptionPane.showMessageDialog(null, msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    /**
     * Conta inexistente.
     * Retorna uma mensagem informando que a conta não existe.
     * @throws RemoteException 
     */
    @Override
    public void contaInexistente() throws RemoteException
    {
        try{
            String msg = "Número da conta inexistente, tente novamente ou consulte o seu gerente.";
            System.out.println(msg);
            //JOptionPane.showMessageDialog(null, msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    /**
     * Senha incorreta.
     * Retorna uma mensangem informando que a senha é incorreta.
     * @throws RemoteException 
     */
    @Override
    public void senhaIncorreta() throws RemoteException
    {
        try{
            String msg = "Senha incorreta, tente novamente.";
            System.out.println(msg);
            //JOptionPane.showMessageDialog(null, msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    /**
     * Saldo insuficiente.
     * Retorna uma mensangem informando que o saldo é insuficiente para realizar
     * a operação.
     * @throws RemoteException 
     */
    @Override
    public void saldoInsuficiente() throws RemoteException
    {
        try{
            String msg = "Saldo insuficiente, não foi possível realizar a operação.";
            System.out.println(msg);
            //JOptionPane.showMessageDialog(null, msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }      
    }

    /**
     * Mensagem do servidor.
     * Retorna para o cliente uma mensangem disparada pelo servidor.
     * @param msg Mensagem.
     * @throws RemoteException 
     */
    @Override
    public void msgServer(String msg) throws RemoteException
    {
        try{
            System.out.println(msg);
            //JOptionPane.showMessageDialog(null, msg);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }     
    }
    
}