/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javarmi.conta_bancaria.impl.CliImpl;

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
    public void consultarSaldo(String numConta, String senha, InterfaceCli ref) throws RemoteException;
    
    
    /**
     * Realizar transferencia entre conta-correntes.
     * Realiza a transferência de um certo valor para a conta corrente do 
     * beneficiário.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param valor a ser transferido.
     * @param contaBenef Numero da conta do beneficiário.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void realizarTransferenciaCC(String numConta, String senha, float valor,
            String contaBenef, InterfaceCli ref) throws RemoteException;
    
     /**
     * Realizar transferencia entre conta-corrente e poupança.
     * Realiza a transferência de um certo valor para a conta poupança do 
     * beneficiário.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param valor a ser transferido.
     * @param contaBenef Numero da conta do beneficiário.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void realizarTransferenciaCP(String numConta, String senha, float valor,
             String contaBenef, InterfaceCli ref) throws RemoteException;
    
    /**
     * Realizar transferencia entre conta-corrente e poupança.
     * Realiza a transferência de um certo valor para a conta poupança do 
     * beneficiário.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param valor a ser transferido.
     * @param numBanco Codigo numero do banco do beneficiário.
     * @param poupanca True se for conta poupança.
     * @param contaBenef Numero da conta do beneficiário.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void realizarTransferenciaDOC(String numConta, String senha, float valor,
             int numBanco, boolean poupanca, String contaBenef, InterfaceCli ref) throws RemoteException;
    
        /**
     * Realizar transferencia entre conta-corrente e poupança.
     * Realiza a transferência de um certo valor para a conta poupança do 
     * beneficiário.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param valor a ser transferido.
     * @param numBanco Codigo numero do banco do beneficiário.
     * @param poupanca True se for conta poupança.
     * @param contaBenef Numero da conta do beneficiário.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void realizarTransferenciaTED(String numConta, String senha, float valor,
             int numBanco, boolean poupanca, String contaBenef, InterfaceCli ref) throws RemoteException;
    
    /**
     * Sacar.
     * O cliente realiza o saque no valor do parâmetro passado.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param valor Valor a ser sacado.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void sacar(String numConta, String senha, float valor, InterfaceCli ref) throws RemoteException;
    
    /**
     * Depositar.
     * O cliente realiza o depósito em sua conta no valor do parâmetro passado.
     * @param numConta Numero da conta do cliente.
     * @param valor Valor do depósito
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void depositar(String numConta, float valor, InterfaceCli ref) throws RemoteException;
    
    /**
     * Registrar interesse nas movimentações.
     * O cliente registra interesse, especificamente, nas movimentações
     * de saque e depósito.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param ref Referencia do cliente.
     * @throws RemoteException 
     */
    public void registrarInteresse(String numConta, String senha, InterfaceCli ref) throws RemoteException;
    
}
