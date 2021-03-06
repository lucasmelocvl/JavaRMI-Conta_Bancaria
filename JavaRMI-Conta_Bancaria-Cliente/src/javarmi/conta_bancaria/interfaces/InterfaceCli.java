/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface do Cliente.
 * @author lucasmelocvl
 */
public interface InterfaceCli extends Remote{
    
    /**
     * Notificação de transferência.
     * Se o cliente estiver habilitado para receber notificações automáticas,
     * toda a vez que for transferido um valor para a sua conta, o cliente é
     * notificado.
     * @param msg Mensagem do servidor.
     * @throws java.rmi.RemoteException
     */
    public void notifTransferencia(String msg) throws RemoteException;
    
    /**
     * Receber saque.
     * É exibido na tela o saque, caso ele tenha sido realizado, simulando o
     * recebimento do valor sacado.
     * @param valor Valor sacado.
     * @throws RemoteException 
     */
    public void ReceberSaque(float valor) throws RemoteException;
    
   /**
     * Retorno do depósito.
     * É informado ao cliente se o depósito foi executada com sucesso.
     * @param valor Valor que foi depositado.
     * @throws RemoteException 
     */
    public void retDepositar(float valor) throws RemoteException;
        
    /**
     * Conta inexistente.
     * Retorna uma mensagem informando que a conta não existe.
     * @throws RemoteException 
     */
    public void contaInexistente() throws RemoteException;
    
    /**
     * Senha incorreta.
     * Retorna uma mensangem informando que a senha é incorreta.
     * @throws RemoteException 
     */
    public void senhaIncorreta() throws RemoteException;
    
    /**
     * Saldo insuficiente.
     * Retorna uma mensangem informando que o saldo é insuficiente para realizar
     * a operação.
     * @throws RemoteException 
     */
    public void saldoInsuficiente() throws RemoteException;
    
    /**
     * Mensagem do servidor.
     * Retorna para o cliente uma mensangem disparada pelo servidor.
     * @param msg Mensagem.
     * @throws RemoteException 
     */
    public void msgServer(String msg) throws RemoteException;
    
}
