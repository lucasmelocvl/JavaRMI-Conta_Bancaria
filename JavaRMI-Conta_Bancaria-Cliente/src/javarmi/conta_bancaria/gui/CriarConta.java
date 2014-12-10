/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi.conta_bancaria.gui;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javarmi.conta_bancaria.impl.CliImpl;
import javarmi.conta_bancaria.interfaces.InterfaceConta;
import javax.swing.JOptionPane;

/**
 *
 * @author lucasmelocvl
 */
public class CriarConta extends javax.swing.JFrame{

    CliImpl cliImpl;
    InterfaceConta contaCli;
    String nomeCli;
    String senhaCli;
    String confirmSenhaCli;
    boolean isPoupanca;
    boolean receberNotif;
    private String numConta;
    private int numBanco;
    
    /**
     * Creates new form CriarConta
     */
    public CriarConta()
    {
        initComponents();

        try {
            cliImpl = new CliImpl();
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(CriarConta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Confirmar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        poupanca = new javax.swing.JCheckBox();
        receberNotificacao = new javax.swing.JCheckBox();
        senha = new javax.swing.JTextField();
        confirmSenha = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        numeroConta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        banco = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jInternalFrame1.setVisible(true);

        jLabel1.setText("Seja bem-vindo ao sistema de contas bancárias");

        jLabel2.setText("Nome:");

        jLabel3.setText("Informe seus dados:");

        jLabel4.setText("Senha:");

        Confirmar.setText("Confirmar");
        Confirmar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ConfirmarActionPerformed(evt);
            }
        });

        jLabel5.setText("Confirmar senha:");

        poupanca.setText("Poupança?");

        receberNotificacao.setText("Receber notificações automaticamente?");

        jLabel6.setText("Conta:");

        numeroConta.setText("  .   - ");

        jLabel7.setText("Banco:");

        banco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "104", "356", "477", "487" }));

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel1))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(poupanca))
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(67, 67, 67)
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nome)
                                    .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(confirmSenha)
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addComponent(numeroConta, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(Confirmar)
                                .addComponent(receberNotificacao)
                                .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(197, 197, 197))))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirmSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(numeroConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(poupanca)
                    .addComponent(jLabel7)
                    .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(receberNotificacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Confirmar)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ConfirmarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ConfirmarActionPerformed
    {//GEN-HEADEREND:event_ConfirmarActionPerformed
        nomeCli = nome.getText();
        senhaCli = senha.getText();
        confirmSenhaCli = confirmSenha.getText();
        isPoupanca = poupanca.isSelected();
        receberNotif = receberNotificacao.isSelected();
        numConta = numeroConta.getText();
        String bank = banco.getSelectedItem().toString();
        numBanco = Integer.parseInt(bank);
                                
        if(nomeCli.equals("") || senhaCli.equals("") || confirmSenhaCli.equals(""))
        {
            String msg = "Por favor, preencha todos os campos.";
            JOptionPane.showMessageDialog(null, msg);
        }
        else if(!senhaCli.equals(confirmSenhaCli))
        {
            String msg = "Senhas não correspondem, tente novamente.";
            JOptionPane.showMessageDialog(null, msg);
        }
        else
        {
            cliImpl.nomeCli = nomeCli;
            cliImpl.senhaCli = senhaCli;
            cliImpl.isPoupanca = isPoupanca;
            cliImpl.isRecebNotif = receberNotif;
            cliImpl.numConta = numConta;
            cliImpl.banco = numBanco;
            System.out.println(nomeCli);
            System.out.println(senhaCli);
            System.out.println(isPoupanca);
            System.out.println(receberNotif);

            boolean result = false;
            try 
            {
                result = cliImpl.criarConta();
                if(!result)
                {
                    String msg = "Não foi possível criar sua conta, tente novamente.";
                    JOptionPane.showMessageDialog(null, msg);
                }
                else
                {
                    this.setVisible(false);
                    new OpcoesOperacoes(cliImpl).setVisible(true);
                }
                
            } 
            catch (RemoteException ex) 
            {
                Logger.getLogger(CriarConta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }//GEN-LAST:event_ConfirmarActionPerformed

    public static void main(String[] args)
    {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CriarConta().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Confirmar;
    private javax.swing.JComboBox banco;
    private javax.swing.JTextField confirmSenha;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField nome;
    private javax.swing.JTextField numeroConta;
    private javax.swing.JCheckBox poupanca;
    private javax.swing.JCheckBox receberNotificacao;
    private javax.swing.JTextField senha;
    // End of variables declaration//GEN-END:variables

}
