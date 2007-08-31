/*
 * TabuleiroInimigo.java
 *
 * Criado em 22 de Agosto de 2007, 22:08
 *  
 * O prop�sito desta classe � implementar o tabuleiro do jogo propriamente dito. Sua implementa��o � realmente bem simples.
 * Ap�s configurar seu tabuleiro, o jogador poder� atacar seu inimigo atrav�s daqui. Como receberemos a matriz l�gica do jogador oponente,
 * basta analizarmos localmente esta matriz e configurar a imagem e contador de acertos.
 */

package batalha.interfacegrafica.jogo;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * @author Renato
 *
 * @date 22/08/2007
 * @version 0.1
 */
public class TabuleiroInimigo extends JPanel {
    
    //ArrayList que armazena todas as imagens geradas. Todo clique neste tabuleiro ir� gerar uma imagem que ser� adicionado
    //neste ArrayList
    private ArrayList<ImagemDoTabuleiro> imagens = null;
    //Matriz l�gica do tabuleiro, que armazena a posi��o e tipo do navio
    private String[][] matrizLogicaDoTabuleiro = null;
    //Armazena a posi��o atual do cursor
    private Point posicaoCursor = null;
    //Inst�ncia de um objeto manipulador de eventos do mouse
    private MouseHandler mouseHandler = null;
    //Manipulador de eventos de movimento do mouse
    private MouseMotionHandler mouseMotionHandler = null;
    //Configura constantes para dizer se o jogador acertou �gua, navio ou se ele tentou clicar em uma posi��o j� clicada.
    private static final int HIT_AGUA = 0,
                             HIT_NAVIO = 1,
                             HIT_PREV_HIT = 2;
    
    //Imagem do cursor
    private Image imagemCursor = null;

    /**
     * Construtor da classe TabuleiroInimigo
     */
    public TabuleiroInimigo(){
         
        //Configura dimens�o preferida do painel
         this.setPreferredSize(new Dimension(249,249));
         //Cria uma borda
         this.setBorder( BorderFactory.createLineBorder( new Color(0,100,90) ) );
         
         //Inicializa o arrayList para armazenar as imagens
         this.imagens = new ArrayList<ImagemDoTabuleiro>();
         
         imagemCursor = new ImageIcon("cursor_pata.gif").getImage();
         //Configura listeners
         this.mouseHandler = new MouseHandler();
         this.mouseMotionHandler = new MouseMotionHandler();
         this.setEnabled(false); //Inicialmente est� desabilitado
         setMatrizLogica(null); //teste apenas
    }
    
    /**
     * Habilita os handlers de eventos e o pr�prio painel
     */
    public void ligar(){
        
        setEnabled(true);
        addMouseListener(this.mouseHandler);
        addMouseMotionListener(this.mouseMotionHandler);
        this.posicaoCursor = new Point(-1,-1);
        repaint();
    }
    
    /**
     * Configura a matriz l�gica utilizada. Esta matriz vir� pela rede.
     */
    public void setMatrizLogica(String[][] matrizLogicaDoTabuleiro){
        
        this.matrizLogicaDoTabuleiro = matrizLogicaDoTabuleiro;
        
    }
    
    /**
     * Pinta o painel
     */
    protected void paintComponent(Graphics g){
    
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        GradientPaint gp = new GradientPaint(0.0f, 0.0f, Color.CYAN,
				   250.0f, 250.0f, Color.WHITE);
	g2.setPaint(gp);
	
        //Desenha o tabuleiro, de acordo com o gradiente
        g2.fillRect(0,0,250,251);

        g2.setColor(new Color(0,100,90));
        
        //Desenha as linhas do tabuleiro
        for (int i=1; i<10; i++)
        {
            g2.drawLine(i*25,0,i*25,250);
	    g2.drawLine(0,i*25,250,i*25);
        }
        
        if(posicaoCursor == null || posicaoCursor.x == -1) return;
        
        //Percorre o array desenhando as imagens armazenadas no ArrayList
        for(ImagemDoTabuleiro i: imagens)
            g.drawImage(i.getImagem(),i.getPontoInicial().x,i.getPontoInicial().y,this);
        
        Point p = normalizaPonto(posicaoCursor.x, posicaoCursor.y);
        //g2.fill3DRect(p.x,p.y,25,25,false);
        g.drawImage(imagemCursor,p.x,p.y,this);
    }
    
    /**
     * Normaliza o ponto da imagem. Resolvi sobrescrever a MESMA FUN��O da classe TabuleiroJogador apenas por claridade.
     */
    private Point normalizaPonto(int x, int y){
        
        int xNormalizado = x/25;
        int yNormalizado = y/25;
        
        return new Point(xNormalizado*25,yNormalizado*25);
        
    }
    
    /**
     * Configura a imagem no tabuleiro de jogo, e envia esta mesma imagem para que seu inimigo configure a imagem no seu tabuleiro.
     */
    private void configuraHit(int x, int y) {
        
        int checkPosicao = getHit(x,y);
        Point p = normalizaPonto(x,y);
        
        //Errou, prayboy
        if(checkPosicao == HIT_AGUA){
            
            matrizLogicaDoTabuleiro[x/25][y/25] = "Y";
            imagens.add(new ImagemDoTabuleiro(new ImageIcon("splash.gif").getImage(), p));
            repaint();
            System.out.println("Acertou agua!");
            
            /**
             * AQUI VEM A L�GICA DE ENVIAR A IMAGEM E PONTO DE ACERTO PARA O JOGADOR ADVERS�RIO, PELA REDE.
             * 
             */
            
        //Acertou navio! Viva!    
        } else if(checkPosicao == HIT_NAVIO){
            
            matrizLogicaDoTabuleiro[x/25][y/25] = "X";
            imagens.add(new ImagemDoTabuleiro(new ImageIcon("explodido.gif").getImage(), p));
            repaint();
            System.out.println("Acertou navio!");
            
            /**
             * AQUI VEM A L�GICA DE ENVIAR A IMAGEM E PONTO DE ACERTO PARA O JOGADOR ADVERS�RIO, PELA REDE.
             * 
             */
            
        } else{
            
            JOptionPane.showMessageDialog(null,"Esta posi��o j� foi clicada!", "Posi��o j� escolhida",JOptionPane.INFORMATION_MESSAGE);
            
            /**
             * PROCESSAMENTO LOCAL. APENAS DIZ AO JOGADOR QUE ELE CLICOU EM UMA POSI��O QUE J� FOI CLICADA.
             * ISSO SIGNIFICA QUE ELE AINDA N�O PERDEU A VEZ.
             */
        }
    }
    
    //Verifica o que o jogador acertou
    private int getHit(int x, int y){
        
        int xHit = x/25;
        int yHit = y/25;
        
        if(matrizLogicaDoTabuleiro[xHit][yHit].equalsIgnoreCase("agua")) return HIT_AGUA;
        //Acertou alguma coisa que n�o era navio (por exemplo, alguma outra parte j� acertada)
        else if(matrizLogicaDoTabuleiro[xHit][yHit].equalsIgnoreCase("X") || matrizLogicaDoTabuleiro[xHit][yHit].equalsIgnoreCase("Y"))
                return HIT_PREV_HIT;
        
        return HIT_NAVIO;
    }
    
    /**
     * MouseHandler.java
     *
     * Criado em 22 de Agosto de 2007, 22:08
     *
     * O prop�sito desta classe � lidar com eventos de mouse sobre o tabuleiro, de forma a saber em qual posi��o 
     * ser� inserida a imagem
     */
    public class MouseHandler extends MouseAdapter{
        
        public void mousePressed(MouseEvent me){
            
            configuraHit(me.getX(),me.getY());
        }
        
    }
    
    /**
     * MouseMotionHandler.java
     *
     * Criado em 22 de Agosto de 2007, 22:08
     *
     * O prop�sito desta classe � lidar com eventos de mouse sobre o tabuleiro, de forma a ajustar o ponto atual do cursor
     */
    private class MouseMotionHandler extends MouseMotionAdapter{
        
        public void mouseMoved(MouseEvent me){
                
                if(!TabuleiroInimigo.this.isEnabled()) return;
                posicaoCursor.setLocation(me.getPoint());
                repaint();
        }
    }
    
    public TabuleiroInimigo getTabuleiroInimigo() {
        
        return this;
    }

}
