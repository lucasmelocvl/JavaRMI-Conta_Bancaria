/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javarmi.conta_bancaria.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author lucasmelocvl
 */
public class ControleText extends JFrame{
    
    JTextField login;
    JPasswordField senha;
    JButton ok, cancel;
    
    JCheckBox bold;
    JCheckBox italic;
    
    public ControleText(){
        super("Textos e Senhas");
        
        login = new JTextField();
        senha = new JPasswordField();
        
        ok = new JButton("Ok");
        ok.addActionListener(new BotaoOkListener());
        cancel = new JButton("Cancel");
        cancel.addActionListener(new BotaoCancelListener());
        
        JTextField texto = new JTextField("Veja a fonte mudar", 12);
        texto.setFont(new Font("Serif", Font.PLAIN, 26));
        
        bold = new JCheckBox();
        bold.addItemListener(new CheckBoxListener());
        italic = new JCheckBox();
        italic.addItemListener(new CheckBoxListener());
        
        Container c = getContentPane();
        //c.setLayout(new GridLayout(6,2));
        c.setLayout(new FlowLayout());
        /*c.add(new JLabel("Login:"));
        c.add(login);
        c.add(new JLabel("Senha:"));
        c.add(senha);
        c.add(ok);
        c.add(cancel);*/
        c.add(texto);
        c.add(bold);
        c.add(italic);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
    }
    
    class CheckBoxListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(bold.isSelected() && italic.isSelected()){
                
            }
        }
        
    }
    
    public static void main(String[] args){
        new ControleText();
    }

    class BotaoOkListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String s = "Login: " + login.getText()
                    + "\nSenha: " + new String(senha.getPassword());
            JOptionPane.showMessageDialog(null, s);
        }
    }
    
    class BotaoCancelListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            login.setText("");
            senha.setText("");
        }
    }

}
