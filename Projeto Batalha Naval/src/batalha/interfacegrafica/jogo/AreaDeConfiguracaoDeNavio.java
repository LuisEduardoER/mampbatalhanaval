/*
 * AreaDeConfiguracaoDeNavio.java
 *
 * Criado em 12 de Agosto de 2007, 22:42
 *
 * Esta classe implementa a área onde o usuário deverá clicar escolher os navios e posicioná-los no seu tabuleiro.
 */

package batalha.interfacegrafica.jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Renato E. Silva
 * 
 * @date 21/08/2007
 * @version 0.1
 *
 * @date 22/08/2007
 * @version 0.2
 */
public class AreaDeConfiguracaoDeNavio extends JPanel{
    
    /**
     * Segue uma explicação do uso desta classe:
     * 
     * A classe AreaDeConfiguracaoDeNavio compartilha certos campos com a classe TabuleiroJogador (@see TabuleiroJogador), de forma
     * que estes campos representam informações sobre o último navio selecionado (i.e, qual foi o último botão clicado pelo usuário,
     * que por sua vez indica o navio selecionado). Para que este compartilhamento seja possível, deve-se utilizar o modificador
     * "protected", de forma que apenas classes dentro do mesmo pacote tenham visibilidade sobre estes campos.
     * 
     * @author: Renato
     */
    
    //Botões que o usuário deve escolher para escolher seu navio
    private JButton[] containerDosNavios = null;
    //Array com as imagens de cada navio (são ícones para os botões)
    private ImageIcon[] imagens = null;
    //Array que armazena o nome dos navios
    protected String[] nomeDosNavios = null;
    /**
     * @TODO: O pacote que está sendo utilizado para carregar as imagens é o próprio projeto. Precisamos descobrir como fazer o carre-
     * gamento correto.
     */
    protected static final String INICIO_IMAGENS = "";
    //Armazena a imagem do último navio selecionado
    protected Image imagemUltimoNavio = null;
    //Armazena o nome do último navio selecionado
    protected String nomeUltimoNavio = null;
    //Armazena a largura do último navio, e sua posição dentro do array de botões
    protected int larguraUltimoNavio = 0, 
                posicaoUltimoNavio = -1;
    /**
     * Indica se o navio está configurado na vertical ou horizontal. Uma forma mais intuitiva, para o usuário, é adicionar um cursor que
     * possa indicar se o navio está na horizontal ou vertical. Este desenvolvimento está sendo feito por mim.
     *
     * @author: Renato
     */
    protected boolean verticalShip = false;
    //Handler para os botões, configurando os campos compartilhados
    private ActionHandler actionHandler = null;
    
    /**
     * Construtor da classe AreaDeConfiguracaoDeNavio
     */
    public AreaDeConfiguracaoDeNavio() {
        
        containerDosNavios = new JButton[5];
        imagens = new ImageIcon[5];
        nomeDosNavios = new String[5];
        actionHandler = new ActionHandler();
        
        //Cria uma borda com título sobre o painel
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Posicione seus navios"));
        
        this.setLayout(new GridLayout(1,5));
        
        nomeDosNavios[0] = "patrulha";
        nomeDosNavios[1] = "submarino";
        nomeDosNavios[2] = "encouracado";
        nomeDosNavios[3] = "seawolf";
        nomeDosNavios[4] = "portaavioes";
       
        
        for(int i = 0; i < 5; i++)
        { 
            imagens[i] = new ImageIcon(INICIO_IMAGENS+nomeDosNavios[i]+".jpg");
        }
        
        //Apenas inicializa os botões. A configuração de nomes e tamanho de cada botão foi configurada fora do laço, logo abaixo.
        for(int i = 0; i < 5; i++){
            
            containerDosNavios[i] = new JButton();
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
   
    /**
     * Desabilita o último navio selecionado, caso sua configuração ocorreu com sucesso.
     * Serviço oferecido para a classe TabuleiroJogo (@see TabuleiroJogo.java).
     */
    protected void disableUltimoNavioSelecionado(){
    
        containerDosNavios[posicaoUltimoNavio].setEnabled(false);
        repaint();
        
    }
    
    /**
     * Handler de evento de clique sobre um botão.
     */
    private class ActionHandler implements ActionListener{
        
        /**
         * O método actionPerformed verifica uma ação sobre cada botão. Dependendo do botão selecionado, configura-se o nome, largura
         * e posição do navio que este botão representa.
         */
        public void actionPerformed(ActionEvent ae){
                        
            if(ae.getSource() == containerDosNavios[0]){   //Navio Patrulha
                nomeUltimoNavio = nomeDosNavios[0];
                imagemUltimoNavio = (imagens[0]).getImage();
                larguraUltimoNavio = 50;
                posicaoUltimoNavio = 0;
            }
            else if(ae.getSource() == containerDosNavios[1]){ //Navio Submarino
                nomeUltimoNavio = nomeDosNavios[1];
                imagemUltimoNavio = (imagens[1]).getImage();
                larguraUltimoNavio = 75;
                posicaoUltimoNavio = 1;
            }
            else if(ae.getSource() == containerDosNavios[2]){ //Navio Encouraçado
                nomeUltimoNavio = nomeDosNavios[2];
                imagemUltimoNavio = (imagens[2]).getImage();
                larguraUltimoNavio = 100;
                posicaoUltimoNavio = 2;
            }
            else if(ae.getSource() == containerDosNavios[3]){ //Navio Seawolf
                nomeUltimoNavio = nomeDosNavios[3];
                imagemUltimoNavio = (imagens[3]).getImage();
                larguraUltimoNavio = 100;
                posicaoUltimoNavio = 3;
            }
            else{                                             //Navio Porta-Aviões
                nomeUltimoNavio = nomeDosNavios[4];
                imagemUltimoNavio = (imagens[4]).getImage(); 
                larguraUltimoNavio = 125;
                posicaoUltimoNavio = 4;
            }
            
            }
        }
}
