/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javarmi.conta_bancaria.conta;

import java.io.Serializable;
import javarmi.conta_bancaria.interfaces.InterfaceCli;

/**
 *
 * @author a1305093
 */
public class ContaCliente implements Serializable{

    private String nomeCliente;
    private String numConta;
    private int tipoConta;
    private int numAgencia;
    private String nomeBanco;
    private float saldo;
    private String senha;
    private boolean receberNotif;
    private InterfaceCli cliente;
    
    
    public ContaCliente(){
        String nomeCliente;
        String numConta;
        int numAgencia;
        String nomeBanco;
        
    }
    
}
