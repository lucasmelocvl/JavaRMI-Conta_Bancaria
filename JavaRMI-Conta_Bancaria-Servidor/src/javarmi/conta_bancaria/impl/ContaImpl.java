/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.impl;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceConta;

/**
 *
 * @author lucasmelocvl
 */
public class ContaImpl extends UnicastRemoteObject implements InterfaceConta{

    private String nomeCli;
    private String senhaCli;
    private String numConta;
    private int tipoConta;
    private int numAgencia;
    private String nomeBanco;
    private float saldo;
    private boolean receberNotif;
    private final InterfaceCli refCli;
    
    
    public ContaImpl(String nome, String senha, InterfaceCli ref) throws RemoteException{
        nomeCli = nome;
        senhaCli = senha;
        numConta = this.gerarNumConta();
        tipoConta = 013;
        numAgencia = 306;
        nomeBanco = "Tio patinhas";
        refCli = ref;
        System.out.println("Novo cliente cadastrado: " + nomeCli + "\n");
    }

    /**
     * Gerar numero de conta.
     * Gera um novo numero de conta.
     * @return
     * @throws RemoteException 
     */
    public String gerarNumConta(){
        String numeroConta;
        numeroConta = "120.362.623.1";
        return numeroConta;
    }
    
    @Override
    public String getNomeCliente() throws RemoteException
    {
        try
        {
            return nomeCli;
        }catch(UnsupportedOperationException e)
        {
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void setNomeCliente(String nomeCliente) throws RemoteException
    {
        try
        {
            nomeCli = nomeCliente;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }
    
    @Override
    public String getSenhaCli() throws RemoteException
    {
        try
        {
            return senhaCli;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }   
    }

    @Override
    public void setSenhaCli(String senha) throws RemoteException
    {
        try
        {
            senhaCli = senha;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public String getNumConta() throws RemoteException
    {
        try
        {
            return numConta;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public void setNumConta(String numeroConta) throws RemoteException
    {
        try
        {
            numConta = numeroConta;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public int getTipoConta() throws RemoteException
    {
        try
        {
            return tipoConta;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public void setTipoConta(int tipoContaCli) throws RemoteException
    {
        try
        {
            tipoConta = tipoContaCli;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public int getNumAgencia() throws RemoteException
    {
        try
        {
            return numAgencia;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public void setNumAgencia(int numeroAgencia) throws RemoteException
    {
        try
        {
            numAgencia = numeroAgencia;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public String getNomeBanco() throws RemoteException
    {
        try
        {
            return nomeBanco;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public void setNomeBanco(String banco) throws RemoteException
    {
        try
        {
            nomeCli = banco;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public float getSaldo() throws RemoteException
    {
        try
        {
            return saldo;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public void setSaldo(float saldoCli) throws RemoteException
    {
        try
        {
            saldo = saldoCli;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public boolean isReceberNotif() throws RemoteException
    {
        try
        {
            return receberNotif;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }

    @Override
    public void setReceberNotif(boolean notificacao) throws RemoteException
    {
        try
        {
            receberNotif = notificacao;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }    
    }
    
}
