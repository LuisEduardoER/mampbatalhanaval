/*
 * TabuleiroJogador.java
 *
 * Criado em 12 de Agosto de 2007, 22:08
 *  
 * O propósito desta classe é implementar o tabuleiro do jogo. Na verdade,
 * o tabuleiro do jogador não terá nenhum listener de evento associado.
 * Apenas o tabuleiro "do outro" jogador conterá este listener, mas é inútil
 * criar duas classes distintas; basta remover o listener do tabuleiro do jogador.
 */

package batalha.interfacegrafica.jogo;


import java.awt.image.BufferedImage;
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
    
    //Verifica se a imagem a ser adicionada no tabuleiro é vertical ou horizontal
    private boolean verticalShip = false;
    //Ponto onde se encontra o cursor atualmente
    private Point pontoCursor = null;
    //Novo cursor para usar sobre o tabuleiro inimigo
    private Cursor novoCursor = null;
    //Referência à àrea de configuração dos navios
    private AreaDeConfiguracaoDeNavio areaDeConfiguracaoDeNavio = null;
    //
    private MouseHandler mouseHandler = null;
    private ImagemDoTabuleiro[] imagensDoTabuleiro = null;
    private String[][] matrizLogicaDoTabuleiro = null;
    
    /**
     * 
     * Construtor da classe TabuleiroJogador
     * 
     * Recebe uma referência à área de configuração de navio (no caso do tabuleiro do inimigo, esta área é nula, afinal só 
     * pode ser configurado o seu tabuleiro, não o do oponente), e uma string que indica onde se encontra a imagem do cursor
     * (apenas para o tabuleiro do inimigo o cursor deve ser modificado).
     * 
     * 
     * @param areaDeConfiguracaoDeNavio referência ao objeto da classe AreaDeConfiguracaoDeNavio
     */ 
    public TabuleiroJogador(AreaDeConfiguracaoDeNavio areaDeConfiguracaoDeNavio){
       
        this.setSize(250,250);
        this.areaDeConfiguracaoDeNavio = areaDeConfiguracaoDeNavio;
        this.imagensDoTabuleiro = new ImagemDoTabuleiro[5];
        this.matrizLogicaDoTabuleiro = new String[10][10];
        for(int i = 0; i < 100; i++)
             this.matrizLogicaDoTabuleiro[i/10][i%10] = new String("agua");
    }
    
    /**
     * Configura o listeners de evento de mouse
     */
    public void setListenersOn(){
        
        this.mouseHandler = new MouseHandler();
        addMouseListener(this.mouseHandler);
    }

    
    public void setListenerOff(){
            
        removeMouseListener(mouseHandler);
    }
    
    protected void paintComponent(Graphics g){
        
        Graphics2D g2d = (Graphics2D) g;
        
        BufferedImage agua = new BufferedImage(25,25,BufferedImage.TYPE_INT_RGB);
      
        
    }
    
    private void configuraImagem(int x, int y) {
        
        String nomeDoNavio = areaDeConfiguracaoDeNavio.getNomeUltimoNavio();
        int larguraNavio = areaDeConfiguracaoDeNavio.getLarguraUltimoNavio();
        int alturaNavio = areaDeConfiguracaoDeNavio.getAlturaUltimoNavio();
        
        boolean checkPosicao = validaPosicaoInsercao(nomeDoNavio, larguraNavio, alturaNavio, x, y);

        if(checkPosicao){

            int posicaoImagem = areaDeConfiguracaoDeNavio.getPosicaoUltimoNavio();
            Image imagem = areaDeConfiguracaoDeNavio.getImagemUltimoNavio();
            imagensDoTabuleiro[posicaoImagem] = new ImagemDoTabuleiro(imagem, new Point(x,y));
            areaDeConfiguracaoDeNavio.setConfiguracaoOk();
        } else{

            JOptionPane.showMessageDialog(null,"SEU NAVIO NÃO PODE SER COLOCADO NESTA POSIÇÃO. TENTE NOVAMENTE",
                    "ERRO DE POSICIONAMENTO DO NAVIO", JOptionPane.WARNING_MESSAGE /*Icone vem aqui.*/);

        }
    }

    private boolean validaPosicaoInsercao(String nomeDoNavio, int larguraNavio, int alturaNavio, int x, int y){
       
        boolean check = true;
        
        if(verticalShip){
                
            int xInicialMatriz = (int)(x/25);
            int yInicialMatriz = (int)(y/25);
            int yFinalMatriz = (int)((y+alturaNavio)/25);
             
            for(int i = yInicialMatriz; i <= yFinalMatriz; i++){
                    if(matrizLogicaDoTabuleiro[xInicialMatriz][i].compareTo("agua") != 0) return false;
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
            if(matrizLogicaDoTabuleiro[xInicialMatriz][i].compareTo("agua") != 0) return false;
        }
        
        for(int i = xInicialMatriz; i <= xFinalMatriz; i++){
            matrizLogicaDoTabuleiro[xInicialMatriz][i] = nomeDoNavio;
        }
        
        return true;
    }

    private class ImagemDoTabuleiro{
        
        private Image imagem = null;
        private Point pontoInicial = null;
        
        public ImagemDoTabuleiro(Image imagem, Point pontoInicial){
            
            this.imagem = imagem; this.pontoInicial = pontoInicial;
        }
        
        protected void setImagem(Image imagem){ this.imagem = imagem; }
        
        protected void setPontoInicial(Point pontoInicial){ this.pontoInicial = pontoInicial; }
        
        protected Image getImagem(){ return imagem; }
        
        protected Point getPontoInicial(){ return pontoInicial; }

    }
    
    /**
     * MouseMotionHandler.java
     *
     * Criado em 12 de Agosto de 2007, 22:08
     *
     * O propósito desta classe é lidar com eventos de mouse sobre o tabuleiro, de forma a:
     * - ajustar o ponto atual do cursor
     * - saber em qual posição será inserida a imagem
     */
    
    private class MouseHandler extends MouseAdapter{
        
        public void mousePressed(MouseEvent me){
            
            //Posiciona uma imagem no tabuleiro
            if(me.getModifiers() == me.BUTTON1){
                
                if(areaDeConfiguracaoDeNavio != null) configuraImagem(me.getX(),me.getY());
                return;
            }
            
            //Altera o modo em que a imagem será colocada
            if(me.getModifiers() == me.BUTTON3){
                
                verticalShip = !verticalShip;
            }
        }

   }
    
}//fim da classe TabuleiroJogador
