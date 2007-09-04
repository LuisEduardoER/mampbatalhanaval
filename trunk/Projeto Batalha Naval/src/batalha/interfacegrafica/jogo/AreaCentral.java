/*
 * AreaCentral.java
 *
 * Criado em 12 de Agosto de 2007, 22:42
 *
 * Esta classe implementa a �rea onde o usu�rio dever� clicar escolher os navios e posicion�-los no seu tabuleiro.
 */

package batalha.interfacegrafica.jogo;

import batalha.interfacegrafica.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Renato, Paulo, Alexandre, Mois�s e Marcelo
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
     *
     * Mudan�a: Deixei a antiga classe AreaDeConfiguracaoDeNavio como um gerenciador de Paineis. Fiz pois como existia  a necessidade
     * de se modificar seu conte�do ap�s a devida configura��o dos navios, seria mais f�cil apenas remover o conte�do deste painel e
     * adicionar o painel DadosRede aqui. Para isto, utilizei o gerenciador de layout CardLayout, que permite que dois paineis sejam
     * instanciados ao mesmo tempo, mas um fica "embaixo" do outro (funcionando tipo como um deck de cartas). Para quem quiser saber
     * mais sobre CardLayout: http://java.sun.com/docs/books/tutorial/uiswing/layout/card.html
     *
     * @author Renato
     */
    
    //Bot�es que o usu�rio deve escolher para escolher seu navio
    private JButton[] containerDosNavios = null;
    //Bot�o de valida��o da configura��o. S� deve ser validado depois que o jogador tenha configurado seus navios e a conex�o 
    //estabelecida
    private JButton botaoValidaPosicionamento = null;
    //Array com as imagens de cada navio (s�o �cones para os bot�es)
    private ImageIcon[] imagens = null;
    //Array que armazena o nome dos navios
    protected String[] nomeDosNavios = null;
    
    /**
     * @TODO: O pacote que est� sendo utilizado para carregar as imagens � o pr�prio projeto. Precisamos descobrir como fazer o carre-
     * gamento correto.
     */
    protected static final String INICIO_IMAGENS = "src/imagens/";
    //Armazena a imagem do �ltimo navio selecionado
    protected Image imagemUltimoNavio = null;
    //Armazena o nome do �ltimo navio selecionado
    protected String nomeUltimoNavio = null;
    //Armazena a largura do �ltimo navio, e sua posi��o dentro do array de bot�es
    protected int larguraUltimoNavio = 0, 
                posicaoUltimoNavio = -1;
    /**
     * Indica se o navio est� configurado na vertical ou horizontal.
     * @author: Renato
     */
    protected boolean verticalShip = false;
    
    //Adicionado depois que os navios estiverem configurados
    private DadosRede dadosRede = null;
    //Cria um painel que armazena os bot�es dos navios (funciona como a antiga classe AreaDeConfiguracaoDeNavio)
    private JPanel painelConteudoNavios = null;
    //Handler para os bot�es, configurando os campos compartilhados
    private ActionHandler actionHandler = null;
    //Refer�ncia ao tabuleiro inimigo, que ser� habilitado ap�s a devida configura��o dos navios
    private TabuleiroInimigo tabuleiroInimigo = null;    

    //Cria uma fonte para o t�tulo da borda
    private Font f = new Font("Arial Bold",Font.BOLD,10);
    //Constantes para o uso do CardLayout, que gerencia o layout deste objeto.
    public static final String NAVIOS_PANE = "Conteudo dos Navios",
                                REDE_PANE = "Conteudo da Rede";
    
    //boolean que informa se existe conex�o estabelecida
    protected boolean conectado;
    
    /**
     * Construtor da classe AreaCentral
     */
    public AreaCentral(TabuleiroInimigo tabuleiroInimigo) {
        
        //Instancia um gerenciador de layout do tipo CardLayout, e configura o tamanho do painel
        //Para quem quiser ver como funciona o SpringLayout, http://java.sun.com/docs/books/tutorial/uiswing/layout/card.html
        CardLayout layout = new CardLayout();
        this.setPreferredSize(new Dimension(621,120));        
        this.setLayout(layout);
        
        //Armazena as refer�ncias para o tabuleiro inimigo
        this.tabuleiroInimigo = tabuleiroInimigo;
        
        //Configura o painel que armazenar� os bot�es dos navios
        painelConteudoNavios = new JPanel();
        painelConteudoNavios.setPreferredSize(new Dimension(620,119));
        
        //Instancia um painel DadosRede
        dadosRede = new DadosRede();
        
        //Configura os bot�es, imagens e nomes referentes aos navios
        containerDosNavios = new JButton[5];
        imagens = new ImageIcon[5];
        nomeDosNavios = new String[5];
        actionHandler = new ActionHandler();
        
        //Cria o bot�o de valida��o de posicionamemto
        botaoValidaPosicionamento = new JButton();

        //Cria uma borda com t�tulo sobre os paineis
        painelConteudoNavios.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Posicione seus navios", TitledBorder.CENTER, TitledBorder.TOP, f, Color.blue));
                
        dadosRede.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Dados da rede", TitledBorder.CENTER, TitledBorder.TOP, f, Color.blue));
        
        //Configura os nomes de cada navio
        nomeDosNavios[0] = "patrulha";
        nomeDosNavios[1] = "submarino";
        nomeDosNavios[2] = "encouracado";
        nomeDosNavios[3] = "seawolf";
        nomeDosNavios[4] = "portaavioes";
       
        //Inicializa as imagens
        for(int i = 0; i < 5; i++)
        { 
                imagens[i] = new ImageIcon(INICIO_IMAGENS+nomeDosNavios[i]+".gif");
        }
        
        //Apenas inicializa os bot�es. A configura��o de nomes e tamanho de cada bot�o foi configurada fora do la�o, logo abaixo.
        for(int i = 0; i < 5; i++){
            
            containerDosNavios[i] = new JButton();
            containerDosNavios[i].addActionListener(actionHandler);
            containerDosNavios[i].setIcon(imagens[i]);
            painelConteudoNavios.add(containerDosNavios[i]);
        }
        
        //Adiciona o bot�o de valida��o no painel
        painelConteudoNavios.add(botaoValidaPosicionamento);
        
        //Configura dimensoes e texto para os bot�es
        containerDosNavios[0].setText("Patrulha");
        containerDosNavios[0].setBounds(0,0,90,40);
        containerDosNavios[1].setText("Submarino");
        containerDosNavios[1].setBounds(200,30,185,40);
        containerDosNavios[2].setText("Encoura�ado");
        containerDosNavios[2].setBounds(395,30,225,40);
        containerDosNavios[3].setText("Seawolf");
        containerDosNavios[3].setBounds(30,80,210,40);
        containerDosNavios[4].setText("Porta avi�es");
        containerDosNavios[4].setBounds(250,80,250,40);
        botaoValidaPosicionamento.setText("Ok!");
        botaoValidaPosicionamento.setBounds(510,80,80,60);
        botaoValidaPosicionamento.setEnabled(false);
        
        //Adiciona os dois paineis. Lembrando sempre que com o CardLayout apenas o primeiro adicionado ser� visto. Para trocar
        //pelo painel de baixo, devemos utilizar o m�todo show (� utilizado na implementa��o de listener do bot�o, no m�todo
        //habilitaBotaoOk
        this.add(painelConteudoNavios,NAVIOS_PANE);
        this.add(dadosRede,REDE_PANE);
        
        //inicialmente n�o existe conex�o
        this.conectado = false;
    }
    
    /*
     * Seta o valor do campo conectado
     */
    public void setConectado(Boolean con) {
        
        this.conectado = con;
    }
    
    /**
     * Retorna o painel dos dados da rede, para serem atualizado na classe de gerenciamento do jogo
     */
    public DadosRede getDadosDaRede(){
              return dadosRede;
    }
   
/* 
 * Retorna o bot�o que � clicado quando o jogador termina de montar o seu tabuleiro.
 * � utilizado para saber o momento de enviar a matriz do tabuleiro do jogador para o advers�rio.
 */
    public JButton getBotaoValidaPosicionamento() {
    
        return this.botaoValidaPosicionamento;
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
     */
    protected void habilitaBotaoOk(){
        
        botaoValidaPosicionamento.setEnabled(true);
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
    
    //exibe o painel que cont�m os navios
    public void mostraNavios() {

        for(int i = 0; i < 5; i++){
            containerDosNavios[i].setEnabled(true);
        }
        dadosRede.setVisible(false);
        painelConteudoNavios.setVisible(true);
    }
}
