/*
 * PainelCentral.java
 *
 * Created on 4 de Setembro de 2007, 14:18
 *
 * Cria um painel para pintar o logo do jogo
 */

package batalha.interfacegrafica;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Renato
 */
public class PainelCentral extends JPanel{
    
    private Image imagem = null;
    private JButton btPoderEspecial = null;
    private JLabel labelPoderF1 = null;
    private JLabel labelPoderF2 = null;
    private boolean foiClickado = false;
    
    public PainelCentral() {
       
        SpringLayout layout = new SpringLayout();
        
        this.setLayout(layout);
        
        this.setPreferredSize(new Dimension(100,250));
        this.setMinimumSize(new Dimension(100,250));
        this.setMaximumSize(new Dimension(100,250));
         
        imagem = new ImageIcon("src/imagens/logo.jpg").getImage();
        btPoderEspecial = new javax.swing.JButton();
        btPoderEspecial.setIcon(new ImageIcon("src/imagens/patada.jpg"));
        btPoderEspecial.setPreferredSize(new Dimension(100,50));
        btPoderEspecial.setEnabled(false);
        labelPoderF1 = new JLabel("Use a patada");
        labelPoderF2 = new JLabel("com sabedoria!");
        
        this.add(btPoderEspecial);
        this.add(labelPoderF1);
        this.add(labelPoderF2);
        
        layout.putConstraint(SpringLayout.NORTH, labelPoderF1, 135, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, labelPoderF1, 8, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, labelPoderF2, 10, SpringLayout.SOUTH, labelPoderF1);
        layout.putConstraint(SpringLayout.WEST, labelPoderF2, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, btPoderEspecial, 10, SpringLayout.SOUTH, labelPoderF2);
    }
    
    protected void paintComponent(Graphics g){
        
        g.drawImage(imagem,0,0,100,130,this);
        btPoderEspecial.repaint();
    }
    
    public void habilitaPatada(){
        
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    
                    if(!foiClickado) btPoderEspecial.setEnabled(true);
                }
            }
        );
    }
   
    public boolean ehJogadaPatada(){
        
        return btPoderEspecial.isEnabled() && foiClickado;
    }
    
    public void desabilitaPatada(){
        
        btPoderEspecial.setEnabled(false);
    }
    
    public void setBotaoClickado(){
        
        foiClickado = true;
    }
    
    public JButton getBotaoPatada(){
        
        return btPoderEspecial;
    }
}
