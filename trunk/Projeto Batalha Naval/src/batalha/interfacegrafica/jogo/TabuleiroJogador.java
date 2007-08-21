/*
 * TabuleiroJogador.java
 *
 * Criado em 12 de Agosto de 2007, 22:08
 *  
 * O prop�sito desta classe � implementar o tabuleiro do jogo. Na verdade,
 * o tabuleiro do jogador n�o ter� nenhum listener de evento associado.
 * Apenas o tabuleiro "do outro" jogador conter� este listener, mas � in�til
 * criar duas classes distintas; basta remover o listener do tabuleiro do jogador.
 */

package batalha.interfacegrafica.jogo;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Renato
 * 
 * @date 12/08/2007
 * @version 0.1
 */
public class TabuleiroJogador extends JPanel{
    
    //Novo cursor para usar sobre o tabuleiro inimigo
    private Cursor novoCursor = null;
    //Refer�ncia � �rea de configura��o dos navios
    private AreaDeConfiguracaoDeNavio areaDeConfiguracaoDeNavio = null;
    //Inst�ncia de um objeto manipulador de eventos do mouse
    private MouseHandler mouseHandler = null;
    //Armazena as imagens do tabuleiro
    private ImagemDoTabuleiro[] imagensDoTabuleiro = null;
    //Matriz l�gica do tabuleiro, que armazena a posi��o e tipo do navio
    private String[][] matrizLogicaDoTabuleiro = null;
    private String nomeDoNavio = null;
    private int larguraNavio = -1;
    private int alturaNavio = -1;
    /**
     * 
     * Construtor da classe TabuleiroJogador
     * 
     * Recebe uma refer�ncia � �rea de configura��o de navio (no caso do tabuleiro do inimigo, esta �rea � nula, afinal s� 
     * pode ser configurado o seu tabuleiro, n�o o do oponente)
     * 
     * 
     * @param areaDeConfiguracaoDeNavio refer�ncia ao objeto da classe AreaDeConfiguracaoDeNavio
     */ 
    public TabuleiroJogador(AreaDeConfiguracaoDeNavio areaDeConfiguracaoDeNavio){
       
        this.setSize(251,251);
        this.areaDeConfiguracaoDeNavio = areaDeConfiguracaoDeNavio;
        this.novoCursor = Toolkit.getDefaultToolkit()
             .createCustomCursor( (new ImageIcon("cursor.gif")).getImage(),
                new Point(12,12), null);
        this.imagensDoTabuleiro = new ImagemDoTabuleiro[5];
        this.matrizLogicaDoTabuleiro = new String[10][10];
        for(int i = 0; i < 100; i++)
             this.matrizLogicaDoTabuleiro[i/10][i%10] = new String("agua");
        
        this.mouseHandler = new MouseHandler();
        addMouseListener(this.mouseHandler);
        
    }

    /**
     * Remove o listener de evento do mouse, depois que o tabuleiro j� estiver configurado
     */
    public void setListenerOff(){
            
        removeMouseListener(mouseHandler);
    }
    
    
    /**
     * M�todo que desenha o tabuleiro
     */
    protected void paintComponent(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g;
        
        GradientPaint gp = new GradientPaint(0.0f, 0.0f, Color.CYAN,
				   250.0f, 250.0f, Color.WHITE);
	g2.setPaint(gp);
	g2.fillRect(0, 0, 250, 250);

        g2.setColor(new Color(0,100,90));
        
        for (int i=1; i<10; i++)
        {
            g2.drawLine(i*25,0,i*25,250);
	    g2.drawLine(0,i*25,250,i*25);
        }

       
       for(int i = 0; i < imagensDoTabuleiro.length; i++){
            
           if(imagensDoTabuleiro[i] != null){
               
              g.drawImage(imagensDoTabuleiro[i].imagem, imagensDoTabuleiro[i].pontoInicial.x,  imagensDoTabuleiro[i].pontoInicial.y,this);  
           }
       }
    }
    
    /**
     * Configura a imagem no tabuleiro do jogador
     * 
     * @param x coordenada x do evento do mouse
     *        y coordenada y do evento do mouse
     */
    private void configuraImagem(int x, int y) {
         
        Image imagem = null;
        
        boolean checkPosicao = validaPosicaoInsercao(nomeDoNavio, larguraNavio, 
                               alturaNavio, x, y);

        if(checkPosicao){

                 imagensDoTabuleiro[areaDeConfiguracaoDeNavio.posicaoUltimoNavio] = 
                            new ImagemDoTabuleiro(areaDeConfiguracaoDeNavio.imagemUltimoNavio, normalizaPonto(x,y));
                 repaint();
                 areaDeConfiguracaoDeNavio.disableUltimoNavioSelecionado();
                
        } else{

            JOptionPane.showMessageDialog(null,"SEU NAVIO N�O PODE SER COLOCADO NESTA POSI��O. TENTE NOVAMENTE",
                    "ERRO DE POSICIONAMENTO DO NAVIO", JOptionPane.WARNING_MESSAGE /*Icone vem aqui.*/);

        }
    }

    private Point normalizaPonto(int x, int y){
        
        int xNormalizado = (int)(x/25);
        int yNormalizado = (int)(y/25);
        
        return new Point(x*25+1,y*25+1);
    }
    
    /**
     * Verifica se a posi��o de clique do mouse pode conter o navio selecionado
     * 
     * @param nomeDoNavio nome do navio a ser posicionado
     *        larguraNavio largura do navio a ser posicionado
     *        alturaNavio altura do navio a ser posicionado
     *        x posi��o x a ser colocado o navio
     *        y posi��o y a ser colocado o navio
     */
    private boolean validaPosicaoInsercao(String nomeDoNavio, int larguraNavio, int alturaNavio, int x, int y){
       
        boolean check = true;
        
        if(areaDeConfiguracaoDeNavio.verticalShip){
                
            int xInicialMatriz = (int)(x/25); 
            int yInicialMatriz = (int)(y/25); 
            int yFinalMatriz = (int)((y+alturaNavio)/25);
             
            for(int i = yInicialMatriz; i <= yFinalMatriz; i++){
                    if(matrizLogicaDoTabuleiro[xInicialMatriz][i].equalsIgnoreCase("agua")) return false;
            }
            
            for(int i = yInicialMatriz; i <= yFinalMatriz; i++){
                   matrizLogicaDoTabuleiro[xInicialMatriz][i] = nomeDoNavio; 
            }
            
            return true;
        }
        
        int xInicialMatriz = (int)(x/25);
        int yInicialMatriz = (int)(y/25);
        int xFinalMatriz = (int)((x+larguraNavio)/25);
        
        for(int i = xInicialMatriz; i <= xFinalMatriz; i++){
            if(matrizLogicaDoTabuleiro[xInicialMatriz][i].equalsIgnoreCase("agua")) return false;
        }
        
        for(int i = xInicialMatriz; i <= xFinalMatriz; i++){
            matrizLogicaDoTabuleiro[xInicialMatriz][i] = nomeDoNavio;
        }
        
        return true;
    }

   /**
    * MouseMotionHandler.java
    *
    * Criado em 12 de Agosto de 2007, 22:18
    * 
    * O prop�sito desta classe � armazenar cada imagem que representa um navio e sua posi��o no tabuleiro
    */
    private class ImagemDoTabuleiro{
        
        private Image imagem = null;
        private Point pontoInicial = null;
        
        
        public ImagemDoTabuleiro(Image imagem, Point pontoInicial){
            
            this.imagem = imagem; 
            this.pontoInicial = pontoInicial; 

        }
        
    }
    
    /**
     * MouseMotionHandler.java
     *
     * Criado em 12 de Agosto de 2007, 22:08
     *
     * O prop�sito desta classe � lidar com eventos de mouse sobre o tabuleiro, de forma a:
     * - ajustar o ponto atual do cursor
     * - saber em qual posi��o ser� inserida a imagem
     */
    
    private class MouseHandler extends MouseAdapter{
        
        public void mousePressed(MouseEvent me){
            
            //Posiciona uma imagem no tabuleiro
            if(me.getModifiers() == me.BUTTON1){
                
                if(areaDeConfiguracaoDeNavio != null){
                    
                    if(areaDeConfiguracaoDeNavio.verticalShip){
                        
                        nomeDoNavio = areaDeConfiguracaoDeNavio.nomeUltimoNavio+"v";
                        larguraNavio = 25;
                        alturaNavio = areaDeConfiguracaoDeNavio.larguraUltimoNavio;
                        
                    }else{
                        
                        nomeDoNavio = areaDeConfiguracaoDeNavio.nomeUltimoNavio;
                        alturaNavio = 25;
                        larguraNavio = areaDeConfiguracaoDeNavio.larguraUltimoNavio;
                    }
                    configuraImagem(me.getX(),me.getY());
                }
                return;
            }
            
            //Altera o modo em que a imagem ser� colocada
            if(me.getModifiers() == me.BUTTON3){
                
                areaDeConfiguracaoDeNavio.verticalShip = !areaDeConfiguracaoDeNavio.verticalShip;
                
            }
        }
        
        public void mouseEntered(MouseEvent me){
        
            if(getCursor() != novoCursor) setCursor(novoCursor);
        }
        
        public void mouseExited(MouseEvent me){
            
            setCursor(Cursor.getDefaultCursor());
        }
   }
    
}//fim da classe TabuleiroJogador
