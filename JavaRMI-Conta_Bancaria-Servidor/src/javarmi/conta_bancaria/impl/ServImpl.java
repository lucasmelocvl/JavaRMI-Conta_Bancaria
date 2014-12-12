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
import javarmi.conta_bancaria.interfaces.InterfaceServ;

/**
 *
 * @author lucasmelocvl
 */
public class ServImpl extends UnicastRemoteObject implements InterfaceServ{

    public static MapContas contas;
    
    /**
     * Implementação do servidor.
     * Construtor.
     * @throws RemoteException
     * @throws AlreadyBoundException 
     */
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
    @Override
    public float consultarSaldo(String numConta, String senha, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
                return (float)-0.0002;
            }else if(conta.getSenhaCli().equals(senha)){
                return conta.getSaldo();
            }else{
                ref.senhaIncorreta();
                return (float)-0.0001;
            }
        }catch(UnsupportedOperationException e){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

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
    @Override
    public boolean sacar(String numConta, String senha, float valor, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null) return false;
            if(!conta.getSenhaCli().equals(senha)) return false;
            
            //Verifica se a conta está bloqueada, se estiver, espera 2seg e
            //tenta novamente, o máxio de tentativas é 5.
            int count = 0;
            while (conta.isBloqueado() && count++ < 5){
                try {
                    Thread.sleep(2000);
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

    /**
     * Depositar.
     * O cliente realiza o depósito em sua conta no valor do parâmetro passado.
     * @param numConta Numero da conta do cliente.
     * @param valor Valor do depósito
     * @param ref Referencia do cliente.
     * @return true se o depósito for realizado com sucesso.
     * @throws RemoteException 
     */
    @Override
    public boolean depositar(String numConta, float valor, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null) return false;
            
            //Verifica se a conta está bloqueada, se estiver, espera 2seg e
            //tenta novamente, o máxio de tentativas é 5.
            int count = 0;
            while (conta.isBloqueado() && count++ < 5){
                try {
                    Thread.sleep(2000);
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
    @Override
    public boolean realizarTransferenciaCC(String numConta, String senha, float valor,
            String contaBenef, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                
                //Verifica se a conta está bloqueada, se estiver, espera 2seg e
                //tenta novamente, o máxio de tentativas é 5.
                int count = 0;
                while (conta.isBloqueado() && count++ < 5){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(conta.isBloqueado()) return false;
                
                conta.setBloqueado(true);
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
                                conta.setBloqueado(false);
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
                    conta.setBloqueado(false);
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
    @Override
    public boolean realizarTransferenciaCP(String numConta, String senha, float valor,
            String contaBenef, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                
                //Verifica se a conta está bloqueada, se estiver, espera 2seg e
                //tenta novamente, o máxio de tentativas é 5.
                int count = 0;
                while (conta.isBloqueado() && count++ < 5){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(conta.isBloqueado()) return false;
                
                conta.setBloqueado(true);
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
                                conta.setBloqueado(false);
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
                    conta.setBloqueado(false);
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
    @Override
    public boolean realizarTransferenciaDOC(String numConta, String senha, float valor,
            int numBanco, boolean poupanca, String contaBenef, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                
                //Verifica se a conta está bloqueada, se estiver, espera 2seg e
                //tenta novamente, o máxio de tentativas é 5.
                int count = 0;
                while (conta.isBloqueado() && count++ < 5){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(conta.isBloqueado()) return false;
                
                conta.setBloqueado(true);
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
                                    conta.setBloqueado(false);
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
                    conta.setBloqueado(false);
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
    @Override
    public boolean realizarTransferenciaTED(String numConta, String senha, float valor,
            int numBanco, boolean poupanca, String contaBenef, InterfaceCli ref) throws RemoteException
    {
        try{
            ContaImpl conta = contas.recuperarConta(numConta);
            if(conta == null){
                ref.contaInexistente();
            }else if(conta.getSenhaCli().equals(senha)){
                
                //Verifica se a conta está bloqueada, se estiver, espera 2seg e
                //tenta novamente, o máxio de tentativas é 5.
                int count = 0;
                while (conta.isBloqueado() && count++ < 5){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(conta.isBloqueado()) return false;
                
                conta.setBloqueado(true);
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
                                    conta.setBloqueado(false);
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
                    conta.setBloqueado(false);
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

    /**
     * Validar usuário.
     * Realiza a validação de um usuário.
     * @param numConta Numero da conta do cliente.
     * @param senhaCli Senha do cliente.
     * @param ref Referencia do cliente.
     * @return True se os dados estiverem corretos.
     * @throws java.rmi.RemoteException
     */
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
