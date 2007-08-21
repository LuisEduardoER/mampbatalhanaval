/*
 * AreaDeConfiguracaoDeNavio.java
 *
 * Criado em 12 de Agosto de 2007, 22:42
 *
 */

package batalha.interfacegrafica.jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Usuario
 */
public class AreaDeConfiguracaoDeNavio extends JPanel{
    
    private JButton[] containerDosNavios = null;
    private ImageIcon[] imagens = null;
    protected String[] nomeDosNavios = null;
    protected boolean configuracaoFoiOk = false;
    private int contadorPosicionamentoOk = 0;
    protected static final String INICIO_IMAGENS = "";
    protected Image imagemUltimoNavio = null;
    protected String nomeUltimoNavio = null;
    protected int larguraUltimoNavio = 0, 
                posicaoUltimoNavio = 0;
    
    protected boolean verticalShip = false;
    
    public AreaDeConfiguracaoDeNavio() {
        
        containerDosNavios = new JButton[5];
        imagens = new ImageIcon[5];
        nomeDosNavios = new String[5];
        
        nomeDosNavios[0] = "patrulha";
        nomeDosNavios[1] = "varredor";
        nomeDosNavios[2] = "encouracado";
        nomeDosNavios[3] = "seawolf";
        nomeDosNavios[4] = "portaavioes";
    
        this.setLayout(new GridLayout(1,5));
        for(int i = 0; i < 5; i++)
        { 
            imagens[i] = new ImageIcon(INICIO_IMAGENS+nomeDosNavios[i]+".jpg");
            System.out.printf("Fonte imagem %d: %s\n",i,INICIO_IMAGENS+nomeDosNavios[i]+".jpeg");
        }
        
        for(int i = 0; i < 5; i++){
            
            containerDosNavios[i] = new JButton();
            containerDosNavios[i].setIcon(new ImageIcon(nomeDosNavios[i]));
            this.add(containerDosNavios[i]);
        }
        
        this.addMouseListener(new MouseHandler());
      //  repaint();
    }
   
    protected void disableUltimoNavioSelecionado(){
    
        containerDosNavios[posicaoUltimoNavio].setEnabled(false);
        contadorPosicionamentoOk++;
        repaint();
        
    }
    
    
    protected void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
    }
    
    private class MouseHandler extends MouseAdapter{
        
        public void mousePressed(MouseEvent me){
            
            for(int i = 0; i < 5; i++){
                
                Point p = me.getPoint();
                Component c = AreaDeConfiguracaoDeNavio.this.getComponentAt(p);
                
                if(c == containerDosNavios[i] && containerDosNavios[i].isEnabled()){
                    
                    containerDosNavios[i].requestFocusInWindow();
                    nomeUltimoNavio = nomeDosNavios[i];
                    imagemUltimoNavio = (imagens[i]).getImage();
                    posicaoUltimoNavio = i;
                }    
            }
        }
    }
}
