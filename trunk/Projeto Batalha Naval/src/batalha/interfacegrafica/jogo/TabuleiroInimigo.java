/*
 * TabuleiroInimigo.java
 *
 * Criado em 22 de Agosto de 2007, 22:08
 *  
 * O propósito desta classe é implementar o tabuleiro do jogo propriamente dito. Sua implementação é realmente bem simples.
 * Após configurar seu tabuleiro, o jogador poderá atacar seu inimigo através daqui. Como receberemos a matriz lógica do jogador oponente,
 * basta analizarmos localmente esta matriz e configurar a imagem e contador de acertos.
 */

package batalha.interfacegrafica.jogo;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * @author Renato, Paulo, Alexandre, Moisés e Marcelo
 *
 * @date 22/08/2007
 * @version 0.1
 */
public class TabuleiroInimigo extends JPanel {
    
    //ArrayList que armazena todas as imagens geradas. Todo clique neste tabuleiro irá gerar uma imagem que será adicionado
    //neste ArrayList
    private ArrayList<ImagemDoTabuleiro> imagens = null;
    //Matriz lógica do tabuleiro, que armazena a posição e tipo do navio
    private String[][] matrizLogicaDoTabuleiro = null;
    //Armazena a posição atual do cursor
    private Point posicaoCursor = null;
    //Instância de um objeto manipulador de eventos do mouse
    private MouseHandler mouseHandler = null;
    //Manipulador de eventos de movimento do mouse
    private MouseMotionHandler mouseMotionHandler = null;
    //Configura constantes para dizer se o jogador acertou água, navio ou se ele tentou clicar em uma posição já clicada.
    public static final int  HIT_AGUA = 0,
                             HIT_NAVIO = 1,
                             HIT_PREV_HIT = 2;
    
    //Imagem do cursor
    private Image imagemCursor = null;
    //Imagem do cursor da patada
    private Image imagemCursorPatada = null;
    
    //Boolean que indica de quem é a vez: true = jogador e false = adversário
    protected boolean vez;
    
    /**
     * Construtor da classe TabuleiroInimigo
     */
    public TabuleiroInimigo(){
         
        //Configura dimensão preferida do painel
         this.setPreferredSize(new Dimension(249,249));
         //Cria uma borda
         this.setBorder( BorderFactory.createLineBorder( new Color(0,100,90) ) );
         
         //Inicializa o arrayList para armazenar as imagens
         this.imagens = new ArrayList<ImagemDoTabuleiro>();
         
         imagemCursor = new ImageIcon("src/imagens/cursor_pata.gif").getImage();
         //Configura listeners
         this.mouseHandler = new MouseHandler();
         this.mouseMotionHandler = new MouseMotionHandler();
         this.setEnabled(false); //Inicialmente está desabilitado
         setMatrizLogica(null); //teste apenas
         this.vez = false;
    }
    
    /**
     * Habilita os handlers de eventos e o próprio painel
     */
    public void ligar(){
        
        setEnabled(true);
        addMouseListener(this.mouseHandler);
        addMouseMotionListener(this.mouseMotionHandler);
        this.posicaoCursor = new Point(-1,-1);
        repaint();
    }
    
    /**
     * Desliga os handlers de eventos
     */
    public void desligar(){
        
        setEnabled(false);
        removeMouseListener(this.mouseHandler);
        removeMouseMotionListener(this.mouseMotionHandler);
        this.posicaoCursor = new Point(-1,-1);
        repaint();
    }

    /*
     * Seta a vez
     */
    public void setVez(boolean v) {
    
        this.vez = v;
    }
    
    /**
     * Configura a matriz lógica utilizada. Esta matriz virá pela rede.
     */
    public void setMatrizLogica(String[][] matrizLogicaDoTabuleiro){
        
        this.matrizLogicaDoTabuleiro = matrizLogicaDoTabuleiro;
        
    }
    
    public void setJogadaPatada(){
        
        imagemCursorPatada = new ImageIcon("src/imagens/cursor_patada.gif").getImage();
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
        
        if(imagemCursorPatada != null)
            g.drawImage(imagemCursorPatada,p.x,p.y,this);
        else
            g.drawImage(imagemCursor,p.x,p.y,this);
    }
    
    /**
     * Normaliza o ponto da imagem. Resolvi sobrescrever a MESMA FUNÇÃO da classe TabuleiroJogador apenas por claridade.
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
        
        if(imagemCursorPatada != null){
            
           Point p = normalizaPonto(x,y);
           
           int pxFinal = (p.x + 100);
           int pyFinal = (p.y + 100);
           
           if(pxFinal > 250) pxFinal = 250; 
           if(pyFinal > 250) pyFinal = 250;
           
           
           for(int i = p.x/25; i < pxFinal/25; i++){
              for(int j = p.y/25; j < pyFinal/25; j++){
                
                  switch(getHit(i*25,j*25)){
                    
                      case HIT_AGUA:  matrizLogicaDoTabuleiro[i/25][j/25] = "Y"; 
                                      imagens.add(new ImagemDoTabuleiro(new ImageIcon("src/imagens/splash.gif").getImage(), new Point(i*25,j*25)));
                                      break;
                      case HIT_NAVIO: matrizLogicaDoTabuleiro[i/25][j/25] = "X"; 
                                      imagens.add(new ImagemDoTabuleiro(new ImageIcon("src/imagens/explodido.gif").getImage(), new Point(i*25,j*25)));
                                      break;
                  }
              }  
           }
           
           Som.playAudio(Som.PATADA);
           imagemCursorPatada = null; //gastou a jogada
        }
        else{
                int checkPosicao = getHit(x,y);
                Point p = normalizaPonto(x,y);

                //Errou, prayboy
                if(checkPosicao == HIT_AGUA){

                    matrizLogicaDoTabuleiro[x/25][y/25] = "Y";
                    imagens.add(new ImagemDoTabuleiro(new ImageIcon("src/imagens/splash.gif").getImage(), p));
                    Som.playAudio(Som.ERRO);
                    repaint();
                    this.vez = false;
                    return;

                //Acertou navio! Viva!    
                } else if(checkPosicao == HIT_NAVIO){

                    matrizLogicaDoTabuleiro[x/25][y/25] = "X";
                    imagens.add(new ImagemDoTabuleiro(new ImageIcon("src/imagens/explodido.gif").getImage(), p));
                    Som.playAudio(Som.ACERTO);
                    repaint();
                    return;
                }
                //Se toca, cabeção! T_T    
                else if (checkPosicao == HIT_PREV_HIT) {
                    JOptionPane.showMessageDialog(null,"Esta_ posição já foi clicada!", "Posição já escolhida",JOptionPane.INFORMATION_MESSAGE);
                }
        }
    }
    
    //Verifica o que o jogador acertou
    public int getHit(int x, int y){
        
        int xHit = x/25;
        int yHit = y/25;
        
        if(matrizLogicaDoTabuleiro[xHit][yHit].equalsIgnoreCase("agua")) return HIT_AGUA;
        //Acertou alguma coisa que não era navio (por exemplo, alguma outra parte já acertada)
        else if(matrizLogicaDoTabuleiro[xHit][yHit].equalsIgnoreCase("X") || matrizLogicaDoTabuleiro[xHit][yHit].equalsIgnoreCase("Y"))
                return HIT_PREV_HIT;
        return HIT_NAVIO;
    }
    
   public int[][] getHitArea(int x, int y, int w, int h){
    
           Point p = normalizaPonto(x,y);
           
           int pxFinal = (p.x + w);
           int pyFinal = (p.y + h);
           
           if(pxFinal > 250) pxFinal = 250; 
           if(pyFinal > 250) pyFinal = 250;
           
           int[][] resultado = new int[10][10];
           
           for(int i = 0; i < 100; i++) resultado[i/10][i%10] = -1;
           
           for(int i = p.x/25; i < pxFinal/25; i++){
              for(int j = p.y/25; j < pyFinal/25; j++){
                
                  switch(getHit(i*25,j*25)){
                    
                      case HIT_AGUA:  resultado[i][j] = HIT_AGUA;
                                      break;
                      case HIT_NAVIO: resultado[i][j] = HIT_NAVIO;
                                      break;
                  }
              }  
           }
           
           return resultado;
   } 
    /**
     * MouseHandler.java
     *
     * Criado em 22 de Agosto de 2007, 22:08
     *
     * O propósito desta classe é lidar com eventos de mouse sobre o tabuleiro, de forma a saber em qual posição 
     * será inserida a imagem
     */
    public class MouseHandler extends MouseAdapter{
        
        public void mousePressed(MouseEvent me){
            
            if(vez) {
                configuraHit(me.getX(),me.getY());
            }
        }
        
    }
    
    /**
     * MouseMotionHandler.java
     *
     * Criado em 22 de Agosto de 2007, 22:08
     *
     * O propósito desta classe é lidar com eventos de mouse sobre o tabuleiro, de forma a ajustar o ponto atual do cursor
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
    
    //limpa a matriz logica do tabuleiro
    public void limpaMatriz() {
        
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++) {
                this.matrizLogicaDoTabuleiro[i][j] = "agua";
            }
        }
    }
    
    //limpa as imagens do tabuleiro
    public void limpaImagens() {
    
        this.imagens.clear();
        desligar();
        repaint();
    }
}
