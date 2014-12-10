/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javarmi.conta_bancaria.interfaces.InterfaceCli;

/**
 *
 * @author lucasmelocvl
 */
public class ContaImpl extends UnicastRemoteObject{

    private String nomeCli;             //Nome do cliente.
    private String senhaCli;            //Senha do cliente.
    private String numConta;            //Número da conta do cliente.
    private int tipoConta;              //Poupanca ou corrente.
    private String numAgencia;          //Numero da agência.
    private String nomeBanco;           //Nome do banco.
    private int numBanco;               //Número do banco.
    private float saldo;               //Saldo do cliente.
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
        this.setNomeCli(nome);             
        this.setSenhaCli(senha);         
        boolean contaNaoExiste = this.verifNumConta(NConta);
        if(!contaNaoExiste){        //Verifica se a conta informada já existe
            return false;
        }
        setNumConta(NConta);          
        if(poupanca)
            this.setTipoConta(013);        //Poupança
        else
        this.setTipoConta(001);        //Conta-corrente
        this.setNumAgencia(NAgencia);      
        this.setNumBanco(NBanco);          //Número do banco
        switch(NBanco){
            case 104:
                this.setNomeBanco("Caixa Econômica Federal");
                break;
            case 356:
                this.setNomeBanco("Banco Real S.A.");
                break;
            case 477:
                this.setNomeBanco("Citibank S.A.");
                break;
            case 487:
                this.setNomeBanco("Deutsche Bank S.A. - Banco Alemão ");
                break;
        }
        this.setSaldo((float) 0.0);
        if(receberNotificacao){
            this.setReceberNotif(true);    //Se quiser receber notificação a ref é guardada
            this.setRefCli(ref);
        }else{
            this.setReceberNotif(false);
            this.setRefCli(null);
        }
        
        ServImpl.contas.inserirConta(getNumConta(), this);
        
        System.out.println("Novo cliente cadastrado: " + getNomeCli());
        System.out.println("Conta nº: " + getNumConta());
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

    /**
     * @return the nomeCli
     */
    public String getNomeCli()
    {
        return nomeCli;
    }

    /**
     * @param nomeCli the nomeCli to set
     */
    public void setNomeCli(String nomeCli)
    {
        this.nomeCli = nomeCli;
    }

    /**
     * @return the senhaCli
     */
    public String getSenhaCli()
    {
        return senhaCli;
    }

    /**
     * @param senhaCli the senhaCli to set
     */
    public void setSenhaCli(String senhaCli)
    {
        this.senhaCli = senhaCli;
    }

    /**
     * @return the numConta
     */
    public String getNumConta()
    {
        return numConta;
    }

    /**
     * @param numConta the numConta to set
     */
    public void setNumConta(String numConta)
    {
        this.numConta = numConta;
    }

    /**
     * @return the tipoConta
     */
    public int getTipoConta()
    {
        return tipoConta;
    }

    /**
     * @param tipoConta the tipoConta to set
     */
    public void setTipoConta(int tipoConta)
    {
        this.tipoConta = tipoConta;
    }

    /**
     * @return the numAgencia
     */
    public String getNumAgencia()
    {
        return numAgencia;
    }

    /**
     * @param numAgencia the numAgencia to set
     */
    public void setNumAgencia(String numAgencia)
    {
        this.numAgencia = numAgencia;
    }

    /**
     * @return the nomeBanco
     */
    public String getNomeBanco()
    {
        return nomeBanco;
    }

    /**
     * @param nomeBanco the nomeBanco to set
     */
    public void setNomeBanco(String nomeBanco)
    {
        this.nomeBanco = nomeBanco;
    }

    /**
     * @return the numBanco
     */
    public int getNumBanco()
    {
        return numBanco;
    }

    /**
     * @param numBanco the numBanco to set
     */
    public void setNumBanco(int numBanco)
    {
        this.numBanco = numBanco;
    }

    /**
     * @return the saldo
     */
    public float getSaldo()
    {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(float saldo)
    {
        this.saldo = saldo;
    }

    /**
     * @return the receberNotif
     */
    public boolean isReceberNotif()
    {
        return receberNotif;
    }

    /**
     * @param receberNotif the receberNotif to set
     */
    public void setReceberNotif(boolean receberNotif)
    {
        this.receberNotif = receberNotif;
    }

    /**
     * @return the refCli
     */
    public InterfaceCli getRefCli()
    {
        return refCli;
    }

    /**
     * @param refCli the refCli to set
     */
    public void setRefCli(InterfaceCli refCli)
    {
        this.refCli = refCli;
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
