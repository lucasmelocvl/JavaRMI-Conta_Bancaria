/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceConta;

/**
 *
 * @author lucasmelocvl
 */
public class ContaImpl extends UnicastRemoteObject implements InterfaceConta{

    private String nomeCli;             //Nome do cliente.
    private String senhaCli;            //Senha do cliente.
    private String numConta;            //Número da conta do cliente.
    private int tipoConta;              //Poupanca ou corrente.
    private String numAgencia;          //Numero da agência.
    private String nomeBanco;           //Nome do banco.
    private int numBanco;               //Número do banco.
    private float saldo;                //Saldo do cliente.
    private boolean receberNotif;       //Receber notificações.
    private InterfaceCli refCli;        //Referência do cliente.
    private boolean bloqueado;          //Flag de bloqueio.
    
    public ContaImpl() throws RemoteException{

    }

    /**
     * Conta Impl.
     * Construtor, cria nova conta do cliente ao ser instaciada.
     * Instancia os dados da nova conta do cliente e insere na hashMap 
     * de contas, a nova conta.
     * @param nome Nome do cliente.
     * @param senha Senha do cliente.
     * @param NConta
     * @param NAgencia
     * @param NBanco
     * @param poupanca true se for poupança.
     * @param receberNotificacao true se o cliente quiser receber notificações.
     * @param ref Interface do cliente.
     * @return True se a conta for criada com sucesso.
     * @throws RemoteException 
     */
    public boolean criarConta(String nome, String senha, String NConta, String NAgencia, int NBanco, boolean poupanca, 
            boolean receberNotificacao, InterfaceCli ref) throws RemoteException
    {
        nomeCli = nome;             
        senhaCli = senha;         
        boolean contaNaoExiste = this.verifNumConta(NConta);
        if(!contaNaoExiste){        //Verifica se a conta informada já existe
            return false;
        }
        numConta = NConta;          
        if(poupanca)
            tipoConta = 013;        //Poupança
        else
            tipoConta = 001;        //Conta-corrente
        numAgencia = NAgencia;      
        numBanco = NBanco;          //Número do banco
        switch(NBanco){
            case 104:
                nomeBanco = "Caixa Econômica Federal";
                break;
            case 356:
                nomeBanco = "Banco Real S.A.";
                break;
            case 477:
                nomeBanco = "Citibank S.A.";
                break;
            case 487:
                nomeBanco = "Deutsche Bank S.A. - Banco Alemão ";
                break;
        }
        saldo = (float) 0.0;
        if(receberNotificacao){
            receberNotif = true;    //Se quiser receber notificação a ref é guardada
            refCli = ref;
        }else{
            receberNotif = false;
            refCli = null;
        }
        
        ServImpl.contas.inserirConta(numConta, this);
        
        System.out.println("Novo cliente cadastrado: " + nomeCli);
        System.out.println("Conta nº: " + numConta);
        return true;
    }
    
    /**
     * Verificar número da conta.
     * Verifica se já existe o núme 
     * @param NConta Conta informado. 
     * Se já existir, retorna falso.
     * @return True se a conta for verificada com sucesso e não existir.
     */
    public boolean verifNumConta(String NConta)
    {
        String numeroConta = NConta;
        boolean contaExiste;
        contaExiste = ServImpl.contas.contaExiste(numeroConta);
        if(contaExiste)
            return false;
        return true;
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
    public String getNumAgencia() throws RemoteException
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
    public void setNumAgencia(String numeroAgencia) throws RemoteException
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
    public void setNomeBanco(String nomeBanco) throws RemoteException
    {
        this.nomeBanco = nomeBanco;
    }

    @Override
    public int getNumBanco() throws RemoteException
    {
        return numBanco;
    }

    @Override
    public void setNumBanco(int numBanco) throws RemoteException
    {
        this.numBanco = numBanco;
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

    @Override
    public InterfaceCli getRefCli()  throws RemoteException
    {
        try
        {
            return refCli;
        }catch(UnsupportedOperationException e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }       
    }

    /**
     * @return the bloqueado
     */
    public boolean isBloqueado()
    {
        return bloqueado;
    }

    /**
     * @param bloqueado the bloqueado to set
     */
    public void setBloqueado(boolean bloqueado)
    {
        this.bloqueado = bloqueado;
    }
    
}
