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
public interface InterfaceServ extends Remote{
    
    
    
    /**
     * Realiza a consulta de saldo do cliente.
     * Através dessa funcao o cliente poderá consultar seu saldo da conta
     * corrente, e seu saldo da poupança. A diferenciação dos tipos de conta
     * é realizada pelo valor do parametro passado.
     * @param poupanca Será false caso seja conta corrente, e true para poupanca
     * @throws RemoteException 
     */
    void consultarSaldo(boolean poupanca) throws RemoteException;
    
    
    /**
     * Realizar transferencia.
     * Ao chamar essa função, o servidor retorna em tela as demais opções para
     * transferência.
     * @throws RemoteException 
     */
    void realizarTransferencia() throws RemoteException;
    
    /**
     * Sacar.
     * O cliente realiza o saque no valor do parâmetro passado.
     * @param valor Valor a ser sacado.
     * @throws RemoteException 
     */
    void sacar(float valor) throws RemoteException;
    
    /**
     * Depositar.
     * O cliente realiza o depósito em sua conta no valor do parâmetro passado.
     * @param valor Valor do depósito
     * @throws RemoteException 
     */
    void depositar(float valor) throws RemoteException;
    
    /**
     * Registrar interesse nas movimentações.
     * O cliente registra interesse, especificamente, nas movimentações
     * de saque e depósito.
     * @throws RemoteException 
     */
    void registrarInteresse() throws RemoteException;
    
        
}
