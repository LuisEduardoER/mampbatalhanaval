/*
 * AreaCentral.java
 *
 * Criado em 12 de Agosto de 2007, 22:42
 *
 * Esta classe implementa a �rea onde o usu�rio dever� clicar escolher os navios e posicion�-los no seu tabuleiro.
 */

package batalha.interfacegrafica.jogo;

import batalha.interfacegrafica.*;
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
 *
 * @date 24/08/2007
 * @version 0.3
 */
public class AreaCentral extends JPanel{
    
    /**
     * Segue uma explica��o do uso desta classe:
     * 
     * A classe AreaCentral compartilha certos campos com a classe TabuleiroJogador (@see TabuleiroJogador), de forma
     * que estes campos representam informa��es sobre o �ltimo navio selecionado (i.e, qual foi o �ltimo bot�o clicado pelo usu�rio,
     * que por sua vez indica o navio selecionado). Para que este compartilhamento seja poss�vel, deve-se utilizar o modificador
     * "protected", de forma que apenas classes dentro do mesmo pacote tenham visibilidade sobre estes campos. Ap�s a devida configura��o
     * esta classe passa a armazenar dados sobre a rede (@see DadosRede.java)
     * 
     * 
     * @author: Renato
     */
    
    //Bot�es que o usu�rio deve escolher para escolher seu navio
    private JButton[] containerDosNavios = null;
    private JButton botaoValidaPosicao = null;
    //Array com as imagens de cada navio (s�o �cones para os bot�es)
    private ImageIcon[] imagens = null;
    //Array que armazena o nome dos navios
    protected String[] nomeDosNavios = null;
    /**
     * @TODO: O pacote que est� sendo utilizado para carregar as imagens � o pr�prio projeto. Precisamos descobrir como fazer o carre-
     * gamento correto.
     */
    protected static final String INICIO_IMAGENS = "";
    //Armazena a imagem do �ltimo navio selecionado
    protected Image imagemUltimoNavio = null;
    //Armazena o nome do �ltimo navio selecionado
    protected String nomeUltimoNavio = null;
    //Armazena a largura do �ltimo navio, e sua posi��o dentro do array de bot�es
    protected int larguraUltimoNavio = 0, 
                posicaoUltimoNavio = -1;
    //Adicionado depois que os navios estiverem configurados
    private DadosRede dadosRede = null;
    
    /**
     * Indica se o navio est� configurado na vertical ou horizontal. Uma forma mais intuitiva, para o usu�rio, � adicionar um cursor que
     * possa indicar se o navio est� na horizontal ou vertical. Este desenvolvimento est� sendo feito por mim.
     * 
     * FEITO.
     * @author: Renato
     */
    protected boolean verticalShip = false;
    //Handler para os bot�es, configurando os campos compartilhados
    private ActionHandler actionHandler = null;
    //private PainelDoJogo painelDoJogo = null;
    private TabuleiroInimigo tabuleiroInimigo = null;
    
    /**
     * Construtor da classe AreaCentral
     */
    public AreaCentral(TabuleiroInimigo tabuleiroInimigo) {
        
        containerDosNavios = new JButton[5];
        imagens = new ImageIcon[5];
        nomeDosNavios = new String[5];
        actionHandler = new ActionHandler();
        botaoValidaPosicao = new JButton();
        this.tabuleiroInimigo = tabuleiroInimigo;
        //Cria uma borda com t�tulo sobre o painel
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
        
        //Apenas inicializa os bot�es. A configura��o de nomes e tamanho de cada bot�o foi configurada fora do la�o, logo abaixo.
        for(int i = 0; i < 5; i++){
            
            containerDosNavios[i] = new JButton();
            containerDosNavios[i].addActionListener(actionHandler);
            containerDosNavios[i].setIcon(imagens[i]);
            this.add(containerDosNavios[i]);
        }
        
        this.add(botaoValidaPosicao);
        
        containerDosNavios[0].setText("Patrulha");
        containerDosNavios[0].setBounds(30,30,160,40);
        containerDosNavios[1].setText("Submarino");
        containerDosNavios[1].setBounds(200,30,185,40);
        containerDosNavios[2].setText("Encoura�ado");
        containerDosNavios[2].setBounds(395,30,225,40);
        containerDosNavios[3].setText("Seawolf");
        containerDosNavios[3].setBounds(30,80,210,40);
        containerDosNavios[4].setText("Porta avi�es");
        containerDosNavios[4].setBounds(250,80,250,40);
        botaoValidaPosicao.setText("Ok!");
        botaoValidaPosicao.setBounds(510,80,80,40);
        botaoValidaPosicao.setEnabled(false);
    }
   
    /**
     * Desabilita o �ltimo navio selecionado, caso sua configura��o ocorreu com sucesso.
     * Servi�o oferecido para a classe TabuleiroJogo (@see TabuleiroJogo.java).
     */
    protected void disableUltimoNavioSelecionado(){
    
        containerDosNavios[posicaoUltimoNavio].setEnabled(false);
        repaint();
        
    }
    
    /**
     * Habilita o bot�o que confirma que tudo foi corretamente configurado.
     * A a��o ligada a este bot�o � liberar o tabuleiro do inimigo, para que o jogo inicie
     *
     * @TODO: AQUI ENTRA A L�GICA DE "LOCK" DA QUAL EU FALAVA. DEVEMOS SINCRONIZAR O ENVIO DA MATRIZ L�GICA E �NICIO DO JOGO.
     */
    protected void habilitaBotaoOk(){
        
        botaoValidaPosicao.setEnabled(true);
        botaoValidaPosicao.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       
                       //Remove os elementos do painel pelo objeto DadosRede 
                       for(int i = 0; i < containerDosNavios.length; i++)
                           remove(containerDosNavios[i]);
                       remove(botaoValidaPosicao);
                       
                       dadosRede = new DadosRede();
                       AreaCentral.this.add(dadosRede);
                       AreaCentral.this.validate();
                       AreaCentral.this.setBorder(BorderFactory.createTitledBorder(
                       BorderFactory.createLineBorder(Color.BLACK),
                       "Dados da Rede"));
                       
                       //Atualiza a interface
                       SwingUtilities.updateComponentTreeUI(AreaCentral.this);
                       
                       /**
                        *"AQUI" ENTRA A L�GICA DE SINCRONIZA��O DE ENVIO DA MATRIZ L�GICA. 
                        */
                       tabuleiroInimigo.ligar();
                    }
                }
        );

        repaint();
    }
    
    
    /**
     * Handler de evento de clique sobre um bot�o.
     */
    private class ActionHandler implements ActionListener{
        
        /**
         * O m�todo actionPerformed verifica uma a��o sobre cada bot�o. Dependendo do bot�o selecionado, configura-se o nome, largura
         * e posi��o do navio que este bot�o representa.
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
            else if(ae.getSource() == containerDosNavios[2]){ //Navio Encoura�ado
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
            else{                                             //Navio Porta-Avi�es
                nomeUltimoNavio = nomeDosNavios[4];
                imagemUltimoNavio = (imagens[4]).getImage(); 
                larguraUltimoNavio = 125;
                posicaoUltimoNavio = 4;
            }
            
        }
   }
}
