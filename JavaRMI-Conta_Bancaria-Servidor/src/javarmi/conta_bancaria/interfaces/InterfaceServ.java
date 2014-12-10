/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface do Servidor.
 * @author lucasmelocvl
 */
public interface InterfaceServ extends Remote{
    
    /**
     * Criar Conta.
     * Cria a conta para um novo cliente.
     * @param nome Nome do cliente.
     * @param senha Senha do cliente.
     * @param numConta Numero da conta.
     * @param numAgencia Numero da agencia.
     * @param banco Numero do banco.
     * @param poupanca true se for conta poupança, false se for conta corrente.
     * @param receberNotificacao true se o cliente quiser receber notificações.
     * @param ref Referencia do cliente.
     * @return True se a contar for criada com sucesso.
     * @throws RemoteException 
     */
    public boolean criarConta(String nome, String senha, String numConta, String numAgencia, int banco, boolean poupanca, boolean receberNotificacao, InterfaceCli ref) throws RemoteException;
    
    /**
     * Realiza a consulta de saldo do cliente.
     * Através dessa funcao o cliente poderá consultar seu saldo da conta
     * corrente, e seu saldo da poupança. A diferenciação dos tipos de conta
     * é realizada pelo valor do parametro passado.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param ref Referencia do cliente.
     * @return saldo disponível.
     * @throws RemoteException 
     */
    public float consultarSaldo(String numConta, String senha, InterfaceCli ref) throws RemoteException;
    
    
    /**
     * Realizar transferencia entre conta-correntes.
     * Realiza a transferência de um certo valor para a conta corrente do 
     * beneficiário.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param valor a ser transferido.
     * @param contaBenef Numero da conta do beneficiário.
     * @param ref Referencia do cliente.
     * @return true se a transferência for realizada com sucesso.
     * @throws RemoteException 
     */
    public boolean realizarTransferenciaCC(String numConta, String senha, float valor,
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
     * @return true se a transferência for realizada com sucesso.
     * @throws RemoteException 
     */
    public boolean realizarTransferenciaCP(String numConta, String senha, float valor,
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
     * @return true se a transferência for realizada com sucesso.
     * @throws RemoteException 
     */
    public boolean realizarTransferenciaDOC(String numConta, String senha, float valor,
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
     * @return true se a transferência for realizada com sucesso.
     * @throws RemoteException 
     */
    public boolean realizarTransferenciaTED(String numConta, String senha, float valor,
             int numBanco, boolean poupanca, String contaBenef, InterfaceCli ref) throws RemoteException;
    
    /**
     * Sacar.
     * O cliente realiza o saque no valor do parâmetro passado.
     * @param numConta Numero da conta do cliente.
     * @param senha Senha do cliente.
     * @param valor Valor a ser sacado.
     * @param ref Referencia do cliente.
     * @return true se o saque for realizado com sucesso.
     * @throws RemoteException 
     */
    public boolean sacar(String numConta, String senha, float valor, InterfaceCli ref) throws RemoteException;
    
    /**
     * Depositar.
     * O cliente realiza o depósito em sua conta no valor do parâmetro passado.
     * @param numConta Numero da conta do cliente.
     * @param valor Valor do depósito
     * @param ref Referencia do cliente.
     * @return true se o depósito for realizado com sucesso.
     * @throws RemoteException 
     */
    public boolean depositar(String numConta, float valor, InterfaceCli ref) throws RemoteException;

    /**
     * Validar usuário.
     * Realiza a validação de um usuário.
     * @param numConta Numero da conta do cliente.
     * @param senhaCli Senha do cliente.
     * @param ref Referencia do cliente.
     * @return True se os dados estiverem corretos.
     * @throws java.rmi.RemoteException
     */
    public boolean validarUsuario(String numConta, String senhaCli, InterfaceCli ref) throws RemoteException;
    
}
