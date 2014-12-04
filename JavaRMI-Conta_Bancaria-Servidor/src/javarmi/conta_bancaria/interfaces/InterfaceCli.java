/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author lucasmelocvl
 */
public interface InterfaceCli extends Remote{
    
    /**
     * Retorno do status da Conta.
     * Informa o cliente se sua conta foi criada com sucesso.
     * @param status True se a conta for criada com sucesso.
     * @param ref Referencia da conta do cliente.
     * @throws RemoteException 
     */
    public void retConta(boolean status, InterfaceConta ref) throws RemoteException;
    
    /**
     * Exibir o saldo do cliente.
     * É impresso na tela o saldo do cliente.
     * @param saldo Refere-se ao saldo do cliente.
     * @throws RemoteException 
     */
    public void exibirSaldo(float saldo) throws RemoteException;
    
    
    /**
     * Retorno da transferencia.
     * É informado ao cliente se a transferência foi executada com sucesso.
     * @param status Informa se a tranferência foi executada ou não.
     * @throws RemoteException 
     */
    public void retTransferencia(boolean status) throws RemoteException;
    
    /**
     * Receber saque.
     * É exibido na tela o saque, caso ele tenha sido realizado, simulando o
     * recebimento do valor sacado.
     * @param valor Valor sacado.
     * @throws RemoteException 
     */
    void ReceberSaque(float valor) throws RemoteException;
    
   /**
     * Retorno da transferencia.
     * É informado ao cliente se o depósito foi executada com sucesso.
     * @param valor Valor do depósito.
     * @throws RemoteException 
     */
    void retDepositar(float valor) throws RemoteException;
    
}
