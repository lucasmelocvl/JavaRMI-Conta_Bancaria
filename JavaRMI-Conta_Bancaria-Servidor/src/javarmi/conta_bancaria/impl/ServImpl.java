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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public boolean criarConta(String nome, String senha, String numConta, String numAgencia,int banco, boolean poupanca, boolean receberNotificacao, InterfaceCli ref) throws RemoteException
    {
        try{
            InterfaceCli refCli = ref;
            ContaImpl contaCli = new ContaImpl();
            boolean ret = contaCli.criarConta(nome, senha, numConta, numAgencia, banco, poupanca, receberNotificacao, ref);
            return ret;
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public float consultarSaldo(String numConta, String senha, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                return conta.getSaldo();
            }else{
                ref.senhaIncorreta();
            }
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return 0;
    }

    @Override
    public boolean sacar(String numConta, String senha, float valor, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null) return false;
            if(!conta.getSenhaCli().equals(senha)) return false;
            
            int count = 0;
            while (conta.isBloqueado() && count++ < 5){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(conta.isBloqueado()) return false;
            
            conta.setBloqueado(true);
            
            float saldoFuturo = conta.getSaldo() - valor;
            if(saldoFuturo < (float)0.0){
                return false;
            }
            conta.setSaldo(saldoFuturo);
            conta.setBloqueado(false);
            return true;
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public boolean depositar(String numConta, float valor, InterfaceCli ref) throws RemoteException
    {
        try{            
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null) return false;
            
            int count = 0;
            while (conta.isBloqueado() && count++ < 5){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(conta.isBloqueado()) return false;
            
            conta.setBloqueado(true);
            conta.setSaldo(conta.getSaldo() + valor);
            if(conta.isReceberNotif()){
                InterfaceCli beneficiario = conta.getRefCli();
                beneficiario.retDepositar(valor);
            }
            conta.setBloqueado(false);
            return true;
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
    public boolean realizarTransferenciaCC(String numConta, String senha, float valor,
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
                        String msg = "\nConta de beneficiário incorreta.\n"
                                    + "Esta conta-corrente não existe.";
                        ref.msgServer(msg);
                    }else{
                        if(beneficiario.getTipoConta() == 001 && conta.getNumBanco() == beneficiario.getNumBanco()){
                            try{
                                beneficiario.setSaldo(beneficiario.getSaldo() + valor);
                                conta.setSaldo(saldoFuturo);
                                String msg = "\nTransferencia realizada com sucesso para a "
                                        + "conta nº"+contaBenef+" no valor de R$"+valor+".";
                                ref.notifTransferencia(msg);
                                if(beneficiario.isReceberNotif()){
                                    String msg2 = "\nNotificação automática:\n"
                                            + "Foi realizado uma transferência para sua "
                                            + "conta no valor de R$"+valor+" pela conta nº"
                                            + conta.getNumConta()+".";
                                    InterfaceCli refBenef = beneficiario.getRefCli();
                                    refBenef.notifTransferencia(msg2);
                                }
                                return true;                                
                            }catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                        }else{
                            String msg = "\nConta de beneficiário incorreta.\n"
                                    + "Verifique se o beneficiário possui conta-corrente "
                                    + "ou poupança e possui cadastro no mesmo banco.";
                            ref.msgServer(msg);
                        }
                    }
                }else{
                    ref.saldoInsuficiente();
                }
            }else{
                ref.senhaIncorreta();
            }
            return false;
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }  
    }

    @Override
    public boolean realizarTransferenciaCP(String numConta, String senha, float valor,
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
                            String msg = "\nConta de beneficiário incorreta.\n"
                                    + "Esta conta-corrente não existe.";
                            ref.msgServer(msg);
                    }else{
                        if(beneficiario.getTipoConta() == 013 && conta.getNumBanco() == beneficiario.getNumBanco()){
                            try{
                                beneficiario.setSaldo(beneficiario.getSaldo() + valor);
                                conta.setSaldo(saldoFuturo);
                                String msg = "\nTransferencia realizada com sucesso para a "
                                        + "conta nº"+contaBenef+" no valor de R$"+valor+".";
                                ref.notifTransferencia(msg);
                                conta.getNumConta();
                                if(beneficiario.isReceberNotif()){
                                    String msg2 = "\nNotificação automática:\n"
                                            + "Foi realizado uma transferência para sua "
                                            + "conta no valor de R$"+valor+" pela conta nº"
                                            + conta.getNumConta()+".";
                                    InterfaceCli refBenef = beneficiario.getRefCli();
                                    refBenef.notifTransferencia(msg2);
                                }
                                return true;
                            }catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                        }else{
                            String msg = "\nConta de beneficiário incorreta.\n"
                                    + "Verifique se o beneficiário possui conta-corrente"
                                    + "ou poupança e se possui cadastro no mesmo banco.";
                            ref.msgServer(msg);
                        }
                    }
                }else{
                    ref.saldoInsuficiente();
                }
            }else{
                ref.senhaIncorreta();
            }   
            return false;
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }

    @Override
    public boolean realizarTransferenciaDOC(String numConta, String senha, float valor,
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
                        ContaImpl beneficiario = contas.recuperarConta(contaBenef);
                        if(beneficiario == null){
                                String msg = "\nConta de beneficiário incorreta.\n"
                                        + "Esta conta-corrente não existe.";
                                ref.msgServer(msg);
                        }else{
                            int tipoConta;
                            if(poupanca)
                                tipoConta = 013;
                            else
                                tipoConta = 001;
                            if(beneficiario.getNumBanco() == numBanco && beneficiario.getTipoConta() == tipoConta){
                                try{
                                    beneficiario.setSaldo(beneficiario.getSaldo() + valor);
                                    conta.setSaldo(saldoFuturo);
                                    String msg = "\nTransferencia realizada com sucesso para a"
                                                + "conta nº"+contaBenef+" no valor de R$"+valor+".";
                                    ref.notifTransferencia(msg);
                                    if(beneficiario.isReceberNotif()){
                                    String msg2 = "\nNotificação automática:\n"
                                            + "Foi realizado uma transferência para sua "
                                            + "conta no valor de R$"+valor+" pela conta nº"
                                            + conta.getNumConta()+" do banco "+conta.getNumBanco()+".";
                                    InterfaceCli refBenef = beneficiario.getRefCli();
                                    refBenef.notifTransferencia(msg2);
                                    }
                                    return true;
                                }catch(Exception e){
                                    String msg = "\nNão foi possível completar o DOC";
                                    System.out.println(msg + " da conta nº"+numConta);
                                    ref.msgServer(msg+".");
                                }
                            }else{
                                String msg = "\nConta de beneficiário incorreta.\n"
                                        + "Verifique se o beneficiário possui número do "
                                        + "banco e da conta informados.";
                                ref.msgServer(msg);
                            }
                        }
                    }else{
                        ref.saldoInsuficiente();
                    }
                }else{
                    String msg = "\nImpossível realizar operação. \n"
                            + "DOC aceita apenas valores de R$4.999,00\n"
                            + "Para transferências acima desse valor, selecione"
                            + "a transferência via TED.";
                    ref.msgServer(msg);
                }
            }else{
                ref.senhaIncorreta();
            }   
            return false;
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }     
    }

    @Override
    public boolean realizarTransferenciaTED(String numConta, String senha, float valor,
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
                        ContaImpl beneficiario = contas.recuperarConta(contaBenef);
                        if(beneficiario == null){
                                String msg = "\nConta de beneficiário incorreta.\n"
                                        + "Esta conta-corrente não existe.";
                                ref.msgServer(msg);
                        }else{
                            int tipoConta;
                            if(poupanca)
                                tipoConta = 013;
                            else
                                tipoConta = 001;
                            if(beneficiario.getNumBanco() == numBanco && beneficiario.getTipoConta() == tipoConta){
                                try{
                                    beneficiario.setSaldo(beneficiario.getSaldo() + valor);
                                    conta.setSaldo(saldoFuturo);
                                    String msg = "\nTransferencia realizada com sucesso para a"
                                                + "conta nº"+contaBenef+" no valor de R$"+valor+".";
                                    ref.notifTransferencia(msg);
                                    if(beneficiario.isReceberNotif()){
                                    String msg2 = "\nNotificação automática:\n"
                                            + "Foi realizado uma transferência para sua "
                                            + "conta no valor de R$"+valor+" pela conta nº"
                                            + conta.getNumConta()+" do banco "+conta.getNumBanco()+".";
                                    InterfaceCli refBenef = beneficiario.getRefCli();
                                    refBenef.notifTransferencia(msg2);
                                    }
                                    return true;
                                }catch(Exception e){
                                    String msg = "\nNão foi possível completar o DOC";
                                    System.out.println(msg + " da conta nº"+numConta);
                                    ref.msgServer(msg+".");
                                }
                            }else{
                                String msg = "\nConta de beneficiário incorreta.\n"
                                        + "Verifique se o beneficiário possui número do "
                                        + "banco e da conta informados.";
                                ref.msgServer(msg);
                            }
                        }
                    }else{
                        ref.saldoInsuficiente();
                    }
                }else{
                    String msg = "\nImpossível realizar operação: \n"
                            + "Operações via TED aceitam apenas valores a partir de R$750.00. "
                            + "Para transferências abaixo desse valor, selecione"
                            + "a transferência via DOC.";
                    ref.msgServer(msg);
                }
            }else{
                ref.senhaIncorreta();
            } 
            return false;
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public boolean validarUsuario(String numConta, String senhaCli, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(!(conta.getSenhaCli().equals(senhaCli))){
                ref.senhaIncorreta();
            }else{
                return true;
            }   
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return false;
    }
    
}
