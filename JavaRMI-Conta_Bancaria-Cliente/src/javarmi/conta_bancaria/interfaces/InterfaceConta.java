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
public interface InterfaceConta extends Remote{

    public String getNomeCliente() throws RemoteException;

    public void setNomeCliente(String nomeCliente) throws RemoteException;
    
    public String getSenhaCli() throws RemoteException;

    public void setSenhaCli(String senha) throws RemoteException;

    public String getNumConta() throws RemoteException;

    public void setNumConta(String numeroConta) throws RemoteException;

    public int getTipoConta() throws RemoteException;

    public void setTipoConta(int tipoContaCli) throws RemoteException;

    public int getNumAgencia() throws RemoteException;

    public void setNumAgencia(int numeroAgencia) throws RemoteException;

    public String getNomeBanco() throws RemoteException;
    
    public void setNomeBanco(String nomeBanco) throws RemoteException;

    public int getNumBanco() throws RemoteException;
    
    public void setNumBanco(int numBanco) throws RemoteException;
 
    public float getSaldo() throws RemoteException;

    public void setSaldo(float saldoCli) throws RemoteException;

    public boolean isReceberNotif() throws RemoteException;

    public void setReceberNotif(boolean notificacao) throws RemoteException;

    public InterfaceCli getRefCli() throws RemoteException;

}
