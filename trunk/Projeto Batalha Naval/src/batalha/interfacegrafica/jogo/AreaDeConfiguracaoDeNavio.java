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
    
    private JButton/*JPanel*/[] containerDosNavios = null;
    private ImageIcon[] imagens = null;
    protected String[] nomeDosNavios = null;
    protected boolean configuracaoFoiOk = false;
    protected static final String INICIO_IMAGENS = "";
    protected Image imagemUltimoNavio = null;
    protected String nomeUltimoNavio = null;
    protected int larguraUltimoNavio = 0, 
                posicaoUltimoNavio = -1;
    
    protected boolean verticalShip = false;
    
    private ActionHandler actionHandler = null;
    
    public AreaDeConfiguracaoDeNavio() {
        
        containerDosNavios = new JButton/*JPanel*/[5];
        imagens = new ImageIcon[5];
        nomeDosNavios = new String[5];
        actionHandler = new ActionHandler();
        
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Posicione seus navios"));
        nomeDosNavios[0] = "patrulha";
        nomeDosNavios[1] = "submarino";
        nomeDosNavios[2] = "encouracado";
        nomeDosNavios[3] = "seawolf";
        nomeDosNavios[4] = "portaavioes";
        
      //  this.setSize(600,120);
        
        this.setLayout(new GridLayout(1,5));
        for(int i = 0; i < 5; i++)
        { 
            imagens[i] = new ImageIcon(INICIO_IMAGENS+nomeDosNavios[i]+".jpg");
           // System.out.printf("Fonte imagem %d: %s\n",i,INICIO_IMAGENS+nomeDosNavios[i]+".jpeg");
        }
        
        for(int i = 0; i < 5; i++){
            
            containerDosNavios[i] = new JButton/*JPanel*/();
            containerDosNavios[i].addActionListener(actionHandler);
            containerDosNavios[i].setIcon(imagens[i]);
            this.add(containerDosNavios[i]);
        }
        
        containerDosNavios[0].setText("Patrulha");
        containerDosNavios[0].setBounds(30,30,160,40);
        containerDosNavios[1].setText("Submarino");
        containerDosNavios[1].setBounds(200,30,185,40);
        containerDosNavios[2].setText("Encouraçado");
        containerDosNavios[2].setBounds(395,30,225,40);
        containerDosNavios[3].setText("Seawolf");
        containerDosNavios[3].setBounds(90,80,210,40);
        containerDosNavios[4].setText("Porta aviões");
        containerDosNavios[4].setBounds(310,80,250,40);
        
    }
   
    protected void disableUltimoNavioSelecionado(){
    
        containerDosNavios[posicaoUltimoNavio].setEnabled(false);
        repaint();
        
    }

    /*protected void paintComponent(Graphics g){
        
            //imagens[0].paintIcon(this,g, 30, 40);
            //imagens[1].paintIcon(this,g, 115, 40);
    }*/
    
    private class ActionHandler implements ActionListener{
        
        public void actionPerformed(ActionEvent ae){
            
            /*for(int i = 0; i < 5; i++){
                                
                if(me.getSource() == containerDosNavios[i] && containerDosNavios[i].isEnabled()){
                    
                    containerDosNavios[i].requestFocusInWindow();
                    nomeUltimoNavio = nomeDosNavios[i];
                    imagemUltimoNavio = (imagens[i]).getImage();
                    
                    if(nomeUltimoNavio.equalsIgnoreCase("patrulha")) larguraUltimoNavio = 50;
                    else if(nomeUltimoNavio.equalsIgnoreCase("submarino")) larguraUltimoNavio = 75;
                    else if(nomeUltimoNavio.equalsIgnoreCase("encouracado")) larguraUltimoNavio = 100;
                    else if(nomeUltimoNavio.equalsIgnoreCase("seawolf")) larguraUltimoNavio = 100;
                    else larguraUltimoNavio = 125;
                    posicaoUltimoNavio = i;
                    System.out.printf("Nome do navio: %s Posição:%d",nomeUltimoNavio,posicaoUltimoNavio);    
                    break;
                }    
            }*/
            
            if(ae.getSource() == containerDosNavios[0]){
                 nomeUltimoNavio = nomeDosNavios[0];
                 imagemUltimoNavio = (imagens[0]).getImage();
                 larguraUltimoNavio = 50;
                 posicaoUltimoNavio = 0;
            }
            else             if(ae.getSource() == containerDosNavios[1]){
                 nomeUltimoNavio = nomeDosNavios[1];
                 imagemUltimoNavio = (imagens[1]).getImage();
                 larguraUltimoNavio = 75;
                 posicaoUltimoNavio = 1;
            }
            
            else             if(ae.getSource() == containerDosNavios[2]){
                 nomeUltimoNavio = nomeDosNavios[2];
                 imagemUltimoNavio = (imagens[2]).getImage();
                 larguraUltimoNavio = 100;
                 posicaoUltimoNavio = 2;
            }
            
            else             if(ae.getSource() == containerDosNavios[3]){
                 nomeUltimoNavio = nomeDosNavios[3];
                 imagemUltimoNavio = (imagens[3]).getImage();
                 larguraUltimoNavio = 100;
                 posicaoUltimoNavio = 3;
            }
            
            else{                 
                 nomeUltimoNavio = nomeDosNavios[4];
                 imagemUltimoNavio = (imagens[4]).getImage();
                 larguraUltimoNavio = 125;
                 posicaoUltimoNavio = 4;
                }
            
                System.out.println("Nome do navio: "+nomeUltimoNavio);
                System.out.println("Posição do navio: "+ posicaoUltimoNavio);
            }
        }
}
