/*
 * TabuleiroJogador.java
 *
 * Criado em 12 de Agosto de 2007, 22:08
 *  
 * O propósito desta classe é implementar o tabuleiro de configuração. Este tabuleiro será usado posteriormente apenas para refletir
 * o que acontece no jogo (se o inimigo acertou seu navio).
 */

package batalha.interfacegrafica.jogo;

import batalha.interfacegrafica.PainelDoJogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Renato
 * 
 * @date 12/08/2007
 * @version 0.1
 *
 * @date 21/08/2007
 * @version 0.1
 *
 * @date 22/08/2007
 * @version 0.2
 */

public class TabuleiroJogador extends JPanel{
    
    //Referência à àrea de configuração dos navios
    private AreaCentral areaCentral = null;
    //Instância de um objeto manipulador de eventos do mouse
    private MouseHandler mouseHandler = null;
    //Manipulador de eventos de movimento do mouse
    private MouseMotionHandler mouseMotionHandler = null;
    //Armazena as imagens do tabuleiro
    private ImagemDoTabuleiro[] imagensDoTabuleiro = null;
    //Matriz lógica do tabuleiro, que armazena a posição e tipo do navio
    private String[][] matrizLogicaDoTabuleiro = null;
    //Variável auxiliar que armazena o nome do último navio, necessária para o caso do navio se encontrar na vertical, onde deve-se
    //concatenar ao nome do navio a extensão "v". Veja o nome das imagens.
    private String nomeDoNavio = null;
    //Variável auxiliar que armazena a largura do navio, necessária para o caso do navio se encontrar na vertical, onde deve-se
    //reconfigurar a largura do navio para 25. Veja o tamanho das imagens.
    private int larguraNavio = -1;
    //Variável auxiliar que armazena a largura do navio, necessária para o caso do navio se encontrar na vertical, onde deve-se
    //reconfigurar a altura do navio. Veja o tamanho das imagens.    
    private int alturaNavio = -1;
    //Contador que verifica se todos os navios já foram posicionados. Esta variável ativa a política de desativar o
    //painel de configuração
    private int contadorPosicionamentoOk = 0;
    private Point posicaoCursor = null;

    
    /**
     * 
     * Construtor da classe TabuleiroJogador
     * 
     * Recebe uma referência à área de configuração de navio (no caso do tabuleiro do inimigo, esta área é nula, afinal só 
     * pode ser configurado o seu tabuleiro, não o do oponente)
     * 
     * 
     * 
     * 
     * @param areaCentral referência ao objeto da classe AreaCentral
     */ 
    public TabuleiroJogador(AreaCentral areaDeConfiguracaoDeNavio){
        
        //this.setSize(250,250);
        this.setPreferredSize(new java.awt.Dimension(250,250));
        
        this.areaCentral = areaDeConfiguracaoDeNavio;
        
        this.setBorder( BorderFactory.createLineBorder( new Color(0,100,90) ) );
        this.imagensDoTabuleiro = new ImagemDoTabuleiro[5];
        
        this.matrizLogicaDoTabuleiro = new String[10][10];
        for(int i = 0; i < 10; i++){
             for(int j = 0; j < 10; j++){
                    matrizLogicaDoTabuleiro[i][j] = "agua";
             }
        }
        
        this.mouseHandler = new MouseHandler();
        this.mouseMotionHandler = new MouseMotionHandler();
        addMouseListener(this.mouseHandler);
        addMouseMotionListener(this.mouseMotionHandler);
        this.posicaoCursor = new Point();
    }

    /**
     * Remove o listener de evento do mouse, depois que o tabuleiro já estiver configurado
     */
    public void desligarHandlers(){
            
        removeMouseListener(this.mouseHandler);
        removeMouseMotionListener(this.mouseMotionHandler);
        setEnabled(false);
    }
    
    /**
     * Método que desenha o tabuleiro
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
       
        //Desenha as imagens
       for(int i = 0; i < imagensDoTabuleiro.length; i++){
            
           if(imagensDoTabuleiro[i] != null){
               
              g2.drawImage(imagensDoTabuleiro[i].getImagem(), imagensDoTabuleiro[i].getPontoInicial().x, imagensDoTabuleiro[i].getPontoInicial().y,this);  
           }
       }
       
       if(areaCentral.imagemUltimoNavio == null ) return; 
       Point p = normalizaPonto(posicaoCursor.x, posicaoCursor.y);
       //System.out.println("Ponto p: "+p.getX()+","+p.getY());
       //System.out.println("Ponto p: "+p.x+","+p.y);
       if (areaCentral.verticalShip) g2.fill3DRect(p.x,
                 p.y, 25, areaCentral.larguraUltimoNavio, false);
       else g2.fill3DRect(p.x,
                 p.y, areaCentral.larguraUltimoNavio, 25, false);
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
                 
                 //Atualiza o nome da imagem para "X.v", onde X é o nome do navio solicitado.
                 if(areaCentral.verticalShip)
                        imagensDoTabuleiro[areaCentral.posicaoUltimoNavio] = 
                            new ImagemDoTabuleiro((new ImageIcon(nomeDoNavio+".gif").getImage()), normalizaPonto(x,y));
                 else imagensDoTabuleiro[areaCentral.posicaoUltimoNavio] = 
                            new ImagemDoTabuleiro(areaCentral.imagemUltimoNavio, normalizaPonto(x,y));
                 
                 //Reconfigura a área de navio
                 areaCentral.disableUltimoNavioSelecionado();
                 areaCentral.nomeUltimoNavio = null;
                 areaCentral.larguraUltimoNavio = -1; 
                 areaCentral.posicaoUltimoNavio = -1;
                 areaCentral.imagemUltimoNavio = null;
                 
                 //Se todos os navios estão configurados, retira-se o listener de evento do tabuleiro.
                 if(++contadorPosicionamentoOk == 5){
                     
                     desligarHandlers();
                     //painelDoJogo.trocarPaineis();
                     areaCentral.habilitaBotaoOk();
                 }
                 repaint();
        } 
        else{
            JOptionPane.showMessageDialog(null,"SEU NAVIO NÃO PODE SER COLOCADO NESTA POSIÇÃO. TENTE NOVAMENTE",
                    "ERRO DE POSICIONAMENTO DO NAVIO", JOptionPane.WARNING_MESSAGE /*Icone vem aqui.*/);
        }
    }

   /**
    * Retorna um ponto normalizado onde deve-se desenhar cada navio
    */ 
    private Point normalizaPonto(int x, int y){
        
        int xNormalizado = (int)(x/25);
        int yNormalizado = (int)(y/25);
        
        return new Point(xNormalizado*25,yNormalizado*25);
    }
    
    /**
     * Verifica se a posição de clique do mouse pode conter o navio selecionado
     * 
     * @param nomeDoNavio nome do navio a ser posicionado
     *        larguraNavio largura do navio a ser posicionado
     *        alturaNavio altura do navio a ser posicionado
     *        x posição x a ser colocado o navio
     *        y posição y a ser colocado o navio
     */
    private boolean validaPosicaoInsercao(String nomeDoNavio, int larguraNavio, int alturaNavio, int x, int y){
       
        boolean check = true;
        
       // imprimeTabuleiro();
        
        if(areaCentral.verticalShip){
          //  System.out.println("VERTICAL\n");    
            int xInicialMatriz = (int)(x/25); 
            int yInicialMatriz = (int)(y/25); 
            int yFinalMatriz = (int)((y+alturaNavio)/25);
            
           // System.out.println("X inicial: "+xInicialMatriz+", Y Inicial: "+yInicialMatriz+", Y Final: "+yFinalMatriz);
            
            if(yInicialMatriz + (yFinalMatriz - yInicialMatriz) -1 > 9) return false;
            
            for(int i = yInicialMatriz; i < yFinalMatriz; i++){
                    if(!matrizLogicaDoTabuleiro[xInicialMatriz][i].equalsIgnoreCase("agua")) return false;
            }
            
            for(int i = yInicialMatriz; i < yFinalMatriz; i++){
                   matrizLogicaDoTabuleiro[xInicialMatriz][i] = nomeDoNavio; 
            }
            
            imprimeTabuleiro();
            return true;
        }
        
       // System.out.println("HORIZONTAL\n");
        int xInicialMatriz = (int)(x/25);
        int yInicialMatriz = (int)(y/25);
        int xFinalMatriz = (int)((x+larguraNavio)/25);
        //System.out.println("X inicial: "+xInicialMatriz+", Y Inicial: "+yInicialMatriz+", X Final: "+xFinalMatriz);
        
        if(xInicialMatriz + (xFinalMatriz - xInicialMatriz) -1 > 9) return false;
        
        for(int i = xInicialMatriz; i < xFinalMatriz; i++){
            if(!matrizLogicaDoTabuleiro[i][yInicialMatriz].equalsIgnoreCase("agua")) return false;
        }
        
        for(int i = xInicialMatriz; i < xFinalMatriz; i++){
            matrizLogicaDoTabuleiro[i][yInicialMatriz] = nomeDoNavio;
        }
       // System.out.println("\n\n");
        imprimeTabuleiro();
        return true;
    }

    private void imprimeTabuleiro(){
        
        for(int i = 0; i < 10; i++){
           for(int j = 0; j < 10; j++){
                System.out.print(i+","+j+"= "+matrizLogicaDoTabuleiro[i][j]);
                System.out.print("\t");
           }
           System.out.println("");
        }
        
        System.out.println("\n\n");
    }
    

    
    /**
     * MouseHandler.java
     *
     * Criado em 12 de Agosto de 2007, 22:08
     *
     * O propósito desta classe é lidar com eventos de mouse sobre o tabuleiro, de forma a saber em qual posição 
     * será inserida a imagem
     */
    
    private class MouseHandler extends MouseAdapter{
        
        public void mousePressed(MouseEvent me){
            
           // if(eventsAreOff) return; //Solução gambiarra -.-
            //Posiciona uma imagem no tabuleiro
            if(me.getButton() == me.BUTTON1){
                
                if(areaCentral.nomeUltimoNavio == null && areaCentral.posicaoUltimoNavio == -1){
                    JOptionPane.showMessageDialog(null,"SELECIONE UM NAVIO PARA POSICIONAR NO TABULEIRO.",
                    "SELECIONE UM NAVIO", JOptionPane.INFORMATION_MESSAGE /*Icone vem aqui.*/);
                    return;
                }
                
                if(areaCentral != null){
                    
                    if(areaCentral.verticalShip){
                        
                        nomeDoNavio = areaCentral.nomeUltimoNavio+"v";
                        //System.out.println("Nome do navio, dentro do tabuleiro: "+nomeDoNavio);
                        larguraNavio = 25;
                        alturaNavio = areaCentral.larguraUltimoNavio;
                        //System.out.println("Altura do navio, dentro do tabuleiro: "+alturaNavio);
                    }else{
                        
                        nomeDoNavio = areaCentral.nomeUltimoNavio;
                        alturaNavio = 25;
                        //System.out.println("Nome do navio, dentro do tabuleiro,sem ser vertical: "+nomeDoNavio);
                        larguraNavio = areaCentral.larguraUltimoNavio;
                    }   //System.out.println("Largura do navio, dentro do tabuleiro,sem ser vertical: "+larguraNavio);
                    
                    //System.out.println("Ponto x: "+me.getX()+", ponto y: "+me.getY());
                    configuraImagem(me.getX(),me.getY());
                    areaCentral.verticalShip = false;
                }
                return;
            }
            
            //Altera o modo em que a imagem será colocada
            if(me.getButton() == me.BUTTON3){
                
                areaCentral.verticalShip = !areaCentral.verticalShip;
                repaint();
            }
            
        }
   }
    
    /**
     * MouseMotionHandler.java
     *
     * Criado em 12 de Agosto de 2007, 22:08
     *
     * O propósito desta classe é lidar com eventos de mouse sobre o tabuleiro, de forma a ajustar o ponto atual do cursor
     * e orientar o usuário a saber se o navio está na vertical ou horizontal.
     */
    private class MouseMotionHandler extends MouseMotionAdapter{

        public void mouseMoved(MouseEvent me) {

            posicaoCursor.setLocation(me.getPoint());
            repaint();
        }
    }
    
}//fim da classe TabuleiroJogador
