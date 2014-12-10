/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi.conta_bancaria.impl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javarmi.conta_bancaria.contas.MapContas;
import javarmi.conta_bancaria.interfaces.InterfaceCli;
import javarmi.conta_bancaria.interfaces.InterfaceConta;
import javarmi.conta_bancaria.interfaces.InterfaceServ;

/**
 *
 * @author Lucas
 */
public class ServImpl extends UnicastRemoteObject implements InterfaceServ{

    public static MapContas contas;
    
    public ServImpl() throws RemoteException, AlreadyBoundException
    {
        try{
            //Cria o registro para receber as referencias, para a porta 1099, local
            Registry referenciaServicoNome = LocateRegistry.createRegistry(1099);

            //A classe é associada a um nome para ser acessado externamente
            //(Registra uma referencia de objeto remoto)
            referenciaServicoNome.rebind("Conta Bancária", this);

            //Inicia o mapa hash de contas
            contas = new MapContas();

            System.out.println("Serviços bancárias iniciados..\n");
        }catch(RemoteException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        
    }

    @Override
    public void criarConta(String nome, String senha, boolean poupanca, boolean receberNotificacao, InterfaceCli ref) throws RemoteException
    {
        try{
            InterfaceCli refCli = ref;
            ContaImpl contaCli = new ContaImpl(nome, senha, poupanca, receberNotificacao, ref);
            InterfaceConta refConta = (InterfaceConta) contaCli;
            refCli.retConta(true, refConta);
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void consultarSaldo(String numConta, String senha, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                ref.exibirSaldo(conta.getSaldo());
            }else{
                ref.senhaIncorreta();
            }
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void sacar(String numConta, String senha, float valor, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                float saldoFuturo = conta.getSaldo() - valor;
                if(saldoFuturo >= (float)0.0){
                    conta.setSaldo(saldoFuturo);
                    ref.ReceberSaque(valor);
                }else{
                    ref.saldoInsuficiente();
                }
            }else{
                ref.senhaIncorreta();
            }
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void depositar(String numConta, float valor, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            conta.setSaldo(conta.getSaldo() + valor);
            if(conta.isReceberNotif()){
                InterfaceCli beneficiario = conta.getRefCli();
                beneficiario.retDepositar(valor);
            }
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void registrarInteresse(String numConta, String senha, InterfaceCli ref) throws RemoteException
    {
        try{
            
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void realizarTransferenciaCC(String numConta, String senha, float valor,
            String contaBenef, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                float saldoFuturo = conta.getSaldo() - valor;
                if(saldoFuturo >= (float)0.0){
                    ContaImpl beneficiario = contas.recuperarConta(contaBenef);
                    if(beneficiario == null){
                        String msg = "Conta de beneficiário incorreta.\n"
                                    + "Esta conta-corrente não existe.";
                        ref.msgServer(msg);
                    }else{
                        if(beneficiario.getTipoConta() == 001){
                            try{
                                beneficiario.setSaldo(beneficiario.getSaldo() + valor);
                                conta.setSaldo(saldoFuturo);
                                String msg = "Transferencia realizada com sucesso para a "
                                        + "conta nº"+contaBenef+" no valor de R$"+valor+".";
                                System.out.println("fora do if");
                                System.out.println(beneficiario.isReceberNotif());
                                ref.notifTransferencia(msg);
                                //if(beneficiario.isReceberNotif()){
                                    System.out.println("dentro do if");
                                    String msg2 = "Notificação automática:"
                                            + "Foi realizado uma transferência para sua "
                                            + "conta no valor de R$"+valor+" pela conta nº"
                                            + conta.getNumConta()+".";
                                    InterfaceCli refBenef = beneficiario.getRefCli();
                                    refBenef.notifTransferencia(msg2);
                                //}
                                
                            }catch(Exception e){
                                System.out.println("errooooooooo");
                                System.out.println(e.getMessage());
                            }
                        }else{
                            String msg = "Conta de beneficiário incorreta.\n"
                                    + "Verifique se o beneficiário possui conta-corrente "
                                    + "ou poupança.";
                            ref.msgServer(msg);
                        }
                    }
                }else{
                    ref.saldoInsuficiente();
                }
            }else{
                ref.senhaIncorreta();
            }
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }  
    }

    @Override
    public void realizarTransferenciaCP(String numConta, String senha, float valor,
            String contaBenef, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                float saldoFuturo = conta.getSaldo() - valor;
                if(saldoFuturo >= (float)0.0){
                    ContaImpl beneficiario = contas.recuperarConta(contaBenef);
                    if(beneficiario == null){
                            String msg = "Conta de beneficiário incorreta.\n"
                                    + "Esta conta-corrente não existe.";
                            ref.msgServer(msg);
                    }else{
                        if(beneficiario.getTipoConta() == 013){
                            try{
                                beneficiario.setSaldo(beneficiario.getSaldo() + valor);
                                conta.setSaldo(saldoFuturo);
                                String msg = "Transferencia realizada com sucesso para a "
                                        + "conta nº"+contaBenef+" no valor de R$"+valor+".";
                                ref.notifTransferencia(msg);
                                conta.getNumConta();
                                if(beneficiario.isReceberNotif()){
                                    String msg2 = "Notificação automática:"
                                            + "Foi realizado uma transferência para sua "
                                            + "conta no valor de R$"+valor+" pela conta nº"
                                            + conta.getNumConta()+".";
                                    InterfaceCli refBenef = beneficiario.getRefCli();
                                    refBenef.notifTransferencia(msg2);
                                }
                            }catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                        }else{
                            String msg = "Conta de beneficiário incorreta.\n"
                                    + "Verifique se o beneficiário possui conta-corrente"
                                    + "ou poupança.";
                            ref.msgServer(msg);
                        }
                    }
                }else{
                    ref.saldoInsuficiente();
                }
            }else{
                ref.senhaIncorreta();
            }            
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public void realizarTransferenciaDOC(String numConta, String senha, float valor,
            int numBanco, boolean poupanca, String contaBenef, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                if(valor < (float)4999.00){
                    float saldoFuturo = conta.getSaldo() - valor;
                    if(saldoFuturo >= (float)0.0){
                        /*
                        Supõe-se que exista outros bancos quaisquer, na qual será
                        transferido certo valor.
                        Para transferência para outros bancos, não é possível
                        verificar se a conta existe ou não.
                        */
                        try{
                            InterfaceConta beneficiario = null;
                            beneficiario.setNumBanco(numBanco);
                            if(poupanca)
                                beneficiario.setTipoConta(013);
                            else
                                beneficiario.setTipoConta(001);
                            beneficiario.setNumConta(contaBenef);
                            beneficiario.setSaldo(beneficiario.getSaldo() + valor);
                            conta.setSaldo(saldoFuturo);
                            String msg = "Transferencia realizada com sucesso para a"
                                        + "conta nº"+contaBenef+" no valor de R$"+valor+".";
                            ref.notifTransferencia(msg);
                        }catch(Exception e){
                            String msg = "Não foi possível completar o DOC";
                            System.out.println(msg + " da conta nº"+numConta);
                            ref.msgServer(msg+".");
                        }
                    }else{
                        ref.saldoInsuficiente();
                    }
                }else{
                    String msg = "Impossível realizar operação. \n"
                            + "DOC aceita apenas valores de R$4.999,00\n"
                            + "Para transferências acima desse valor, selecione"
                            + "a transferência via TED.";
                    ref.msgServer(msg);
                }
            }else{
                ref.senhaIncorreta();
            }            
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }     
    }

    @Override
    public void realizarTransferenciaTED(String numConta, String senha, float valor,
            int numBanco, boolean poupanca, String contaBenef, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                if(valor >= (float)750.00){
                    float saldoFuturo = conta.getSaldo() - valor;
                    if(saldoFuturo >= (float)0.0){
                        /*
                        Supõe-se que exista outros bancos quaisquer, na qual será
                        transferido certo valor.
                        Para transferência para outros bancos, não é possível
                        verificar se a conta existe ou não.
                        */
                        try{
                            InterfaceConta beneficiario = null;
                            beneficiario.setNumBanco(numBanco);
                            if(poupanca)
                                beneficiario.setTipoConta(013);
                            else
                                beneficiario.setTipoConta(001);
                            beneficiario.setNumConta(contaBenef);
                            beneficiario.setSaldo(beneficiario.getSaldo() + valor);
                            conta.setSaldo(saldoFuturo);
                            String msg = "Transferencia realizada com sucesso para a "
                                        + "conta nº"+contaBenef+" no valor de R$"+valor+".";
                            ref.notifTransferencia(msg);
                        }catch(Exception e){
                            String msg = "Não foi possível completar o TED";
                            System.out.println(msg + " da conta nº"+numConta);
                            ref.msgServer(msg+".");
                        }
                    }else{
                        ref.saldoInsuficiente();
                    }
                }else{
                    String msg = "Impossível realizar operação: \n"
                            + "Operações via TED aceitam apenas valores a partir de R$750.00. "
                            + "Para transferências abaixo desse valor, selecione"
                            + "a transferência via DOC.";
                    ref.msgServer(msg);
                }
            }else{
                ref.senhaIncorreta();
            }         
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
