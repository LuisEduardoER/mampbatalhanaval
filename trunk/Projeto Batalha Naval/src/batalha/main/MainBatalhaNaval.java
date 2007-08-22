/*
 * MainBatalhaNaval.java
 *
 * Criado em 19 de Agosto de 2007, 14:27
 *
 */

package batalha.main;

import batalha.interfacegrafica.jogo.*;
import batalha.interfacegrafica.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
/**
 *
 * @author Usuario
 */
public class MainBatalhaNaval {

    public static void main(String args[]){
        
        JFrame teste = new JFrame();
        //AreaDeConfiguracaoDeNavio area = new AreaDeConfiguracaoDeNavio();
        //teste.getContentPane().add(area);
        //teste.pack();
        teste.setVisible(true);
        PainelDoJogo painelDoJogo = new PainelDoJogo();
        teste.getContentPane().add(painelDoJogo);
        teste.setSize(685,580);
        //teste.setResizable(false);
        teste.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //teste.setVisible(true);
    }
    
}
