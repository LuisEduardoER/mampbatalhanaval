/*
 * TabuleiroInimigo.java
 *
 * Created on 22 de Agosto de 2007, 17:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package batalha.interfacegrafica.jogo;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Usuario
 */
public class TabuleiroInimigo extends JPanel {
    
    private ArrayList<ImagemDoTabuleiro> imagens = null;
    //Matriz lógica do tabuleiro, que armazena a posição e tipo do navio
    private String[][] matrizLogicaDoTabuleiro = null;
    private Point posicaoCursor = null;
    //Instância de um objeto manipulador de eventos do mouse
    private MouseHandler mouseHandler = null;
    //Manipulador de eventos de movimento do mouse
    private MouseMotionHandler mouseMotionHandler = null;
    
    private static final int HIT_AGUA = 0,
                             HIT_NAVIO = 1,
                             HIT_PREV_HIT = 2;
    
    public TabuleiroInimigo(){
    
         this.imagens = new ArrayList<ImagemDoTabuleiro>();
         this.setBorder( BorderFactory.createLineBorder( new Color(0,100,90) ) );
         this.mouseHandler = new MouseHandler();
         this.mouseMotionHandler = new MouseMotionHandler();
         //addMouseListener(this.mouseHandler);
         //addMouseMotionListener(this.mouseMotionHandler);
        //this.posicaoCursor = new Point();
         this.setEnabled(false);
         
    }
    
    public void ligar(){
        
        setEnabled(true);
        addMouseListener(this.mouseHandler);
        addMouseMotionListener(this.mouseMotionHandler);
        this.posicaoCursor = new Point(-1,-1);
        repaint();
    }
    
    public void setMatrizLogica(String[][] matrizLogicaDoTabuleiro){
        
        this.matrizLogicaDoTabuleiro = matrizLogicaDoTabuleiro;
    }
    
    protected void paintComponent(Graphics g){
    
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        GradientPaint gp = new GradientPaint(0.0f, 0.0f, Color.CYAN,
				   250.0f, 250.0f, Color.WHITE);
	g2.setPaint(gp);
	
        //Desenha o tabuleiro, de acordo com o gradiente
        g2.fillRect(0,0,250,250);

        g2.setColor(new Color(0,100,90));
        
        //Desenha as linhas do tabuleiro
        for (int i=1; i<10; i++)
        {
            g2.drawLine(i*25,0,i*25,250);
	    g2.drawLine(0,i*25,250,i*25);
        }
        
        //if(imagens.isEmpty()) return;
        if(posicaoCursor == null || posicaoCursor.x == -1) return;
        
        for(ImagemDoTabuleiro i: imagens)
            g.drawImage(i.getImagem(),i.getPontoInicial().x,i.getPontoInicial().y,this);
        
        Point p = normalizaPonto(posicaoCursor.x, posicaoCursor.y);
        g2.fill3DRect(p.x,p.y,25,25,false);
    }
    
    private Point normalizaPonto(int x, int y){
        
        int xNormalizado = x/25;
        int yNormalizado = y/25;
        
        return new Point(xNormalizado*25,yNormalizado*25);
        
    }
    
    private void configuraHit(int x, int y) {
        
        
        int checkPosicao = getHit(x,y);
        Point p = normalizaPonto(x,y);
        
        //Errou, prayboy
        if(checkPosicao == HIT_AGUA){
            
            matrizLogicaDoTabuleiro[x/25][y/25] = "Y";
            imagens.add(new ImagemDoTabuleiro(new ImageIcon("aguaHit.jpg").getImage(), p));
            repaint();
            System.out.println("Acertou agua!");
        } else if(checkPosicao == HIT_NAVIO){
            
            matrizLogicaDoTabuleiro[x/25][y/25] = "X";
            imagens.add(new ImagemDoTabuleiro(new ImageIcon("navioHit.jpg").getImage(), p));
            repaint();
            System.out.println("Acertou navio!");
        } else{
            
            JOptionPane.showMessageDialog(null,"Esta posição já foi clicada!", "Posição já escolhida",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
   /* public void setMatrizLogica(String matrizLogica[][]){
        
        this.matrizLogicaDoTabuleiro = matrizLogica;
    }*/
    private int getHit(int x, int y){
        
        int xHit = x/25;
        int yHit = y/25;
        
        if(matrizLogicaDoTabuleiro[xHit][yHit].equalsIgnoreCase("agua")) return HIT_AGUA;
        else if(matrizLogicaDoTabuleiro[xHit][yHit].equalsIgnoreCase("X") || matrizLogicaDoTabuleiro[xHit][yHit].equalsIgnoreCase("Y"))
                return HIT_PREV_HIT;
        
        return HIT_NAVIO;
    }
    
    private class MouseHandler extends MouseAdapter{
        
        public void mousePressed(MouseEvent me){
            
            configuraHit(me.getX(),me.getY());
        }
        
        
    }
    
    private class MouseMotionHandler extends MouseMotionAdapter{
        
        public void mouseMoved(MouseEvent me){
                
                if(!TabuleiroInimigo.this.isEnabled()) return;
                posicaoCursor.setLocation(me.getPoint());
                repaint();
        }
    }
}
