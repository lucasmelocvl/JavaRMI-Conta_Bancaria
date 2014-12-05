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
     * Criar Conta.
     * Cria a conta para um novo cliente.
     * @param nome Nome do cliente.
     * @param senha Senha do cliente.
     * @param poupanca true se for conta poupança, false se for conta corrente.
     * @param receberNotificacao true se o cliente quiser receber notificações.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void criarConta(String nome, String senha, boolean poupanca, boolean receberNotificacao, InterfaceCli ref) throws RemoteException;
    
    /**
     * Realiza a consulta de saldo do cliente.
     * Através dessa funcao o cliente poderá consultar seu saldo da conta
     * corrente, e seu saldo da poupança. A diferenciação dos tipos de conta
     * é realizada pelo valor do parametro passado.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void consultarSaldo(String numConta, String senha,InterfaceCli ref) throws RemoteException;
    
    
    /**
     * Realizar transferencia.
     * Ao chamar essa função, o servidor retorna em tela as demais opções para
     * transferência.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param valor a ser transferido.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void realizarTransferencia(String numConta, String senha, double valor, InterfaceCli ref) throws RemoteException;
    
    /**
     * Sacar.
     * O cliente realiza o saque no valor do parâmetro passado.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param valor Valor a ser sacado.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void sacar(String numConta, String senha, double valor, InterfaceCli ref) throws RemoteException;
    
    /**
     * Depositar.
     * O cliente realiza o depósito em sua conta no valor do parâmetro passado.
     * @param valor Valor do depósito
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void depositar(double valor, InterfaceCli ref) throws RemoteException;
    
    /**
     * Registrar interesse nas movimentações.
     * O cliente registra interesse, especificamente, nas movimentações
     * de saque e depósito.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void registrarInteresse(InterfaceCli ref) throws RemoteException;
    
        
}
