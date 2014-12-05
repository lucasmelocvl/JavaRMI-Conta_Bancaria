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

    private String nomeCli;
    private String senhaCli;
    private String numConta;
    private int tipoConta;
    private int numAgencia;
    private String nomeBanco;
    private int numBanco;
    private float saldo;
    private boolean receberNotif;
    private final InterfaceCli refCli;
    
    /**
     * Conta Impl.
     * Construtor, cria nova conta do cliente ao ser instaciada.
     * Instancia os dados da nova conta do cliente e insere na hashMap 
     * de contas, a nova conta.
     * @param nome Nome do cliente.
     * @param senha Senha do cliente.
     * @param poupanca true se for poupança.
     * @param receberNotificacao true se o cliente quiser receber notificações.
     * @param ref Interface do cliente.
     * @throws RemoteException 
     */
    public ContaImpl(String nome, String senha, boolean poupanca, 
            boolean receberNotificacao, InterfaceCli ref) throws RemoteException
    {
        nomeCli = nome;
        senhaCli = senha;
        numConta = "12345678-9";
        //numConta = this.gerarNumConta();
        if(poupanca)
            tipoConta = 013;
        else
            tipoConta = 001;
        numAgencia = 306;
        nomeBanco = "Uncle Scrooge Bank";
        numBanco = 171;
        saldo = (float) 0.0;
        if(receberNotificacao)receberNotif = true;
        refCli = ref;
        
        ServImpl.contas.inserirConta(numConta, this);
        
        System.out.println("Novo cliente cadastrado: " + nomeCli);
        System.out.println("Conta nº: " + numConta);
        
        //ContaImpl contaMap = ServImpl.contas.recuperarConta(numConta);
        //System.out.println(contaMap.getSenhaCli());
    }

    /**
     * Gerar numero de conta.
     * Gera um novo numero de conta.
     * Considera-se que já exista uma conta (contaExiste), para que entre no 
     * loop do while, e enquanto não for gerado uma conta diferente das 
     * existentes, é procurado por novos números de conta.
     * @return numeroConta Numero da conta do cliente.
     */
    public String gerarNumConta()
    {
        String numeroConta = "";
        boolean contaExiste = true;
        while(contaExiste){
            for(int i=0; i<10;i++){
                int number= (int) (0+Math.random()*9);
                numeroConta += String.valueOf(number);
                if(i==8){
                    numeroConta += "-";
                }
            }
            contaExiste = ServImpl.contas.contaExiste(numeroConta);
        }
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
    
}
