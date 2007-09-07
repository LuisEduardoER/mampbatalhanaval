/*
 * PainelJogo.java
 *
 * Criado em 19 de Agosto de 2007, 21:09
 *
 * Implementa a classe principal de interface, que armazena todos os outros componentes.
 */

package batalha.interfacegrafica;

import batalha.interfacegrafica.jogo.*;
import batalha.gerencia.GerenciadorJogo;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * @author  Renato, Paulo, Alexandre, Moisés e Marcelo
 *
 * @date 19/08/2007
 * @version 0.1
 *
 * @date 20/08/2007
 * @version 0.2
 *
 * @date 20/08/2007
 * @version 0.3
 *
 * @date 22/08/2007
 * @version 0.4
 *
 * @date 25/08/2007
 * @version 0.5
 *
 * @date 01/09/2007
 * @version 0.6
 *
 * @date 03/09/2007
 * @version 0.7
 *
 * @date 04/09/2007
 * @version 0.8
 */
public class PainelDoJogo extends JPanel{
    
    /**
     * Segue uma explicação do uso desta classe:
     * 
     * Esta é a classe de interface mais importante do jogo, que abriga todos os elementos gráficos que o compõem. É também a classe que
     * mais modifiquei. Ao começar a ler o código gerado pelo construtor de interface do NetBeans, simplesmente me perdi. Então acabei
     * fazendo direto na "mão", utilizando pelo menos 2 importantes gerenciadores: BoxLayout e SpringLayout. Como já indiquei link do
     * tutorial do SpringLayout na classe DadosRede, deixo aqui o link para o tutorial do BoxLayout da Sun: 
     * http://java.sun.com/docs/books/tutorial/uiswing/layout/box.html
     */
   
    //Label para mostrar de quem é a vez
    private JLabel lbVez, 
                   lbPontuacao;
    //TextField para envio de mensagens no txaChat
    private JTextField txfMensagem;
    //Area Central, que primeiro exibe os navios depois os dados da rede
    //@see batalha#interfacegrafica#jogo.AreaCentral
    private AreaCentral areaCentral;
    //Botão para enviar mensagem
    private JButton btEnviarMensagem;
    //Área de texto para o chat
    private JTextArea txaChat;
    //Exibe a área de texto em um campo
    private JScrollPane jspChat;
    //Tabuleiro do jogador
    private TabuleiroJogador meuTabuleiro;
    //Tabuleiro do inimigo
    private TabuleiroInimigo tabuleiroInimigo;
    //Painel com o logo
    private PainelCentral painelCentral;
    //Apelido 
    private String apelidoJogador;
    //IP
    private String Ip;
    //boolean que indica se o objeto eh o servidor (true) ou cliente (false)
    private boolean Servidor;
    //boolean que indica que existe uma conexão estabelecida (true) ou não (false)
    private boolean conectado = false;
    //boolean que indica que o jogador montou seu tabuleiro e serve para definir quem
    //começa o jogo, pois aquele que montar o tabuleiro primeiro tem o direito de começar
    private boolean jogadorPronto;
    //boolean que indica a vez: true = vez do jogador e false = vez do adversário
    private boolean vez;
    //inteiro que armazena a pontuação do jogador
    private int pontos;
    //Itens de menu para sair do jogo e criar novo jogo
    private JMenuItem menuSair;
    private JMenuItem novoJogo;
    
      
    /** 
     * Construtor da classe PainelDoJogo
     */
    public PainelDoJogo( JMenuItem sair, JMenuItem novo ) {
        menuSair = sair;   
        novoJogo = novo;
        initComponents();
    }
                               
    /****************************************************************************************/
    //seta o apelido que o jogador entrar, este metodo é chamado na classe BatalhaNavalWindow
    void setNick(String apelido){
        apelidoJogador = apelido;        
    }
    
    //retorna o apelido 
    public String  getNick(){
      return  apelidoJogador;
    }
    
     //seta o boolean que indica se o objeto eh servidor ou cliente
    public void setServidor(boolean ser) {
        
        this.Servidor = ser;
    }

    //retorna o boolean que indica se o objeto eh servidor ou nao
    public boolean getServidor() {
    
        return this.Servidor;
    }
    
    
    //seta o Ip que o jogador entrar sendo este metodo chamado na classe BatalhaNavalWindow
    void setIp(String ip ){
        Ip = ip;
    }     
    
    //retorna o Ip
    public String getIp(){
       return Ip;
    }
    
    //retorna o campo de texto da mensagem do chat
    public JTextField getTxfMensagem() {
    
        return this.txfMensagem;
    }
    
    //retorna o botão enviar mensagem
    public JButton getBtEnviarMensagem() {
    
        return this.btEnviarMensagem;
    }
    
    //retorna o texto de txfMensagem na forma de String
    public String getTextoMensagem() {
    
        return this.txfMensagem.getText();
    }
    
    //retorna a área central
    public AreaCentral getAreaCentral(){

       return this.areaCentral;
    }

    //retorna o boolean que indica se existe conexão estabelecida
    public boolean getConectado() {
        
        return this.conectado;
    }
    
    //seta o boolean que indica se existe conexao estabelecida
    public void setConectado(boolean con) {
        
        this.conectado = con;
        this.areaCentral.setConectado(con);
    }
    
    //seta o boolean responsável por indicar que o jogador preparou seu tabuleiro
    public void setJogadorPronto(boolean jog) {
        
        this.jogadorPronto = jog;
    }
    
    //retorna o boolean que indica que o tabuleiro está pronto para o jogo
    public boolean getJogadorPronto() {
        
        return this.jogadorPronto;
    }
    
    //seta a vez
    public void setVez(final boolean v) {
        
        this.vez = v;             
       //cria um objeto fonte
      final Font font = new Font("Arial Bold", Font.BOLD, 15);                                    
           
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                        lbVez.setFont( font );//seta a fonte com a cor vermelha
                        lbVez.setForeground( Color.RED );
                        
                        if( v ){
                            lbVez.setText( "Tua vez         ");
                        }else{
                             lbVez.setForeground( Color.BLUE ); //seta a fonte com a cor azul   
                             lbVez.setText("Aguarde...         ");
                        }
                        
                }
            }
        );
    }
    
  
    //retorna o boolean que indica de quem é a vez
    public boolean getVez() {
    
        return this.vez;
    }
    
    //para pegar o Evento do BatalhaNavalWindows...
    public JMenuItem getSaida(){
        return this.menuSair;
    }
    
    public JMenuItem getNovoJogo(){
        return this.novoJogo;
    }
    
    //acrescenta 1 ao número de pontos do jogador
    public void somaPontuacao() {
        
        this.pontos++;
        
        final Font font = new Font("Arial Bold", Font.BOLD, 15); 
        
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    lbPontuacao.setFont( font );
                    lbPontuacao.setForeground( Color.BLUE );
                    lbPontuacao.setText("Sua pontuação atual é " + pontos );
                }
            }
        );
    }
    
    //retorna a pontuação do jogador
    public int getPontos() {
        
        return this.pontos;
    }
    
    //zera a pontuação
    public void zeraPontos() {
        
        this.pontos = -1;
    }
    
    public JButton getBotaoPatada(){
        
        return painelCentral.getBotaoPatada();
    }
    
    public PainelCentral getPainelCentral(){
        
        return painelCentral;
    }
    
    public TabuleiroJogador getMeuTabuleiro(){
        
        return meuTabuleiro;
    }
    /****************************************************************************************/
    
    /**
     * Inicializa os componentes que serão adicionados a este painel
     */
    private void initComponents() {
        
        //Primeiro configuramos o gerenciador de layout para BorderLayout. Este gerenciador é muito simples, e permite que os containeres
        //sejam adicionados em 5 partes: norte, sul, leste, oeste e centro. Para quem quiser saber mais sobre o BorderLayout,
        //http://java.sun.com/docs/books/tutorial/uiswing/layout/border.html
        this.setLayout(new BorderLayout());        
         
        Font font = new Font("Arial Bold", Font.BOLD, 15); 
        
        
        //Instancia os objetos
        tabuleiroInimigo = new TabuleiroInimigo();
        txaChat = new javax.swing.JTextArea();
        painelCentral = new PainelCentral();

        btEnviarMensagem = new javax.swing.JButton();
        
        areaCentral = new AreaCentral(tabuleiroInimigo);
        meuTabuleiro = new TabuleiroJogador(areaCentral);
        txfMensagem = new JTextField(30);
        
        //inicia o label da vez
        this.lbVez = new JLabel("Este label indica de quem é a vez");
        lbVez.setFont( font );
        lbVez.setForeground( Color.GREEN);
        this.lbVez.setPreferredSize(new Dimension(0,0));
        
        //inicia o label da pontuação
        this.lbPontuacao = new JLabel("          Este label indica sua pontuação");
        lbPontuacao.setFont( font );
        lbPontuacao.setForeground( Color.GREEN);
        this.lbPontuacao.setPreferredSize(new Dimension(0,0));
        //inicialmente não existe conexão estabelecida
        this.conectado = false;

        //inicialmente o adversário está montando seu tabuleiro
        this.jogadorPronto = false;
        
        //a vez deve ser false até o jogo começar
        this.vez = false;
        
        //o jogador começa com 0 ponto e, se tiver sorte, esse número muda
        this.pontos = 0;
        
        //Cria uma box, que é um gerenciador de containeres. Neste caso criamos uma box horizontal, e todo container adicionado à box
        //o será da esquerda para a direita. Para quem quiser saber mais sobre Box (BoxLayout), indiquei o link do tutorial da Sun
        //na descrição da classe.
        Box boxHorizontalCima = Box.createHorizontalBox();
        
        //Aqui criamos 4 box auxiliares. A box1CimaX é uma box horizontal que adiciona o container com as letras e o tabuleiro do
        //jogador. Depois "empacotamos" esta box na box1CimaY, junto com o container com os números. O mesmo acontece com box2CimaX
        //e box2CimaY. Explicarei melhor no decorrer do código ;-)
        Box box1CimaX = Box.createHorizontalBox(); Box box2CimaX = Box.createHorizontalBox();
        Box box1CimaY = Box.createVerticalBox(); Box box2CimaY = Box.createVerticalBox();
        
        //Criamos uma box para adicionar o painel do jogo. Está apenas em amarelo agora
        Box boxLogo = Box.createVerticalBox();        
        //Dimensões para adicionar o painel com o logo
        Dimension distLogoCima = new Dimension(80,20);
        Dimension distLogoBaixo = new Dimension(80,10);
        //Colocamos uma distância, empacotamos o logo, e depois colocamos outra distância
        boxLogo.add(new Box.Filler(distLogoCima,distLogoCima,distLogoCima));
        boxLogo.add(painelCentral);
        boxLogo.add(new Box.Filler(distLogoBaixo,distLogoBaixo,distLogoBaixo));
        
        //Dimensões padrão para distanciamento, necessário para Box.Filler
        Dimension distPadraoX = new Dimension(10, 249); 
        Dimension distPadraoY  = new Dimension(250,10);
        
        //Criamos os containeres com as letras e números. bottom1 é um container que serve apenas para dar um espaço de preenchimento.
        Container letras = criarVetorLetras();
        Container numeros = criarVetorNumeros();
        Container bottom1 = new Container();
        
        bottom1.setPreferredSize(new Dimension(251,15));
        
        //Primeiro adicionamos um espaço de preenchimento na box horizontal, com dimensão 10,252. Isto cria o espaço entre o lado 
        //esquerdo do frame e as letras
        box1CimaX.add(new Box.Filler(distPadraoX,distPadraoX,distPadraoX));
        //Adicionamos as letras  e o tabuleiro do jogador
        box1CimaX.add(letras);
        box1CimaX.add(meuTabuleiro);
        
        //Depois adicionamos um espaço de preenchimento similar à box horizontal, mas agora na vertical. Isto cria o espaço entre o
        //a parte de cima do frame e os números
        box1CimaY.add(new Box.Filler(distPadraoY,distPadraoY,distPadraoY));
        //Adicionamos os números e a box horizontal, para empacotar tudo numa grande box
        box1CimaY.add(numeros);
        box1CimaY.add(box1CimaX);
        //Adicionamos um espaço de preenchimento também na vertical. Isto cria o espaço entre o tabuleiro e o painel com os botões 
        //(ou dados da rede).
        box1CimaY.add(new Box.Filler(distPadraoY,distPadraoY,distPadraoY));
        //Por fim, adicionamos a box que contém o tabuleiro, letras e números na box horizontal de cima. Entenda que isto funciona 
        //basicamente como uma série de empacotamentos de containeres. Até aqui, adicionamos apenas o tabuleiro do jogador
        boxHorizontalCima.add(box1CimaY);
        boxHorizontalCima.add(new Box.Filler(distPadraoX,distPadraoX,distPadraoX));
        //Adicionamos agora o painel do jogo. Perceba que o espaçamento dá a ilusão de que este painel possui o mesmo tamanho dos ta
        //buleiros (a idéia é essa... )
        boxHorizontalCima.add(boxLogo);
        
        //A partir daqui o princípio é o mesmo, só que agora iremos configurar o tabuleiro do inimigo. Criamos os containeres com
        //as letras e números (não podemos utilizar os containeres já criados pois o Java não suporta que dois containeres sejam usa
        //dos no mesmo container-pai. Ou seja, se tentarmos usar os containeres letra e numero aqui novamente, eles não apareceram
        //onde foram previamente colocados
        Container letras2 = criarVetorLetras();
        Container numeros2 = criarVetorNumeros();
        
        //Criamos um espaçamento entre o painel com o logo e o tabuleiro do inimigo
        box2CimaX.add(new Box.Filler(distPadraoX,distPadraoX,distPadraoX));
        //Empacotamos as letras, depois o tabuleiro
        box2CimaX.add(letras2);
        box2CimaX.add(tabuleiroInimigo);

        Container bottom2 = new Container();
        
        bottom2.setPreferredSize(new Dimension(251,15));
        
        //Criamos um espaçamento entre a parte de cima do frame e o tabuleiro
        box2CimaY.add(new Box.Filler(distPadraoY,distPadraoY,distPadraoY));
        //Empacotamos os números e a box horizontal
        box2CimaY.add(numeros2);
        box2CimaY.add(box2CimaX);
        //Damos um espaçamento entre este painel e lado esquerdo do frame
        box2CimaY.add(new Box.Filler(distPadraoY,distPadraoY,distPadraoY));
        //Empacotamos a box com o tabuleiro do inimigo na box principal de cima. 
        boxHorizontalCima.add(box2CimaY);
        //Damos um espaço entre o painel do tabuleiro e o painel com os navios. Até aqui configuramos toda a área dos tabuleiros.
        boxHorizontalCima.add(new Box.Filler(distPadraoX,distPadraoX,distPadraoX));
        
        //Esta box empacota os labels de pontuação e vez
        Box boxHorizontalMeio1 = Box.createHorizontalBox();
        
        boxHorizontalMeio1.add(lbVez);
        Dimension distLabel = new Dimension(100,50);
        boxHorizontalMeio1.add(new Box.Filler(distLabel,distLabel,distLabel));
        boxHorizontalMeio1.add(lbPontuacao);
        
        //Esta box simplesmente empacota a área central, e será adicionada no meio deste objeto
        Box boxHorizontalMeio2 = Box.createHorizontalBox();
        boxHorizontalMeio2.add(areaCentral);
        
        Box boxVerticalMeio = Box.createVerticalBox();
        
        boxVerticalMeio.add(boxHorizontalMeio1);
        boxVerticalMeio.add(boxHorizontalMeio2);
        
        //Por fim, configuramos a parte do chat. Esta parte também não é muito difícil. Primeiro criamos uma box horizontal
        Box boxHorizontalBaixo = Box.createHorizontalBox();
        
        //Depois criamos 3 box auxiliares, duas horizontais e uma vertical. A primeira horizontal empacota a área de texto,
        //enquanto a segunda empacota o campo de texto e o botão para enviar mensagem. Por fim, empacotamos tudo na box 
        //vertical
        Box box3BaixoX1 = Box.createHorizontalBox(); Box box3BaixoY = Box.createVerticalBox();
        Box box3BaixoX2 = Box.createHorizontalBox();

        //Configuramos a área de visão, dimensão e tamanho (em termos de coluna de texto) da área de texto.
       
        jspChat = new JScrollPane(txaChat);                                 
        txaChat.setColumns(30);
        txaChat.setLineWrap(true);
        jspChat.setPreferredSize(new Dimension(350,100));
        //Configuramos a política da área de texto, caso seja necessário habilitar scroll vertical e/ou horizontal
        jspChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        txaChat.setEditable(true);
        
        //Configuramos o botão para enviar mensagem 
        //@TODO: adicionar listener
        btEnviarMensagem.setPreferredSize(new Dimension(80,30));
        btEnviarMensagem.setText("Enviar");
        
        //Instância do gerenciador de layout SpringLayout. Este gerenciador é muito prático e poderoso, e funciona de maneira bem simples
        //Tudo que devemos fazer é especificar o posicionamento de um container em relação a outro, de acordo com uma coordenada. Explica
        //rei no decorrer do código
        SpringLayout layout = new SpringLayout();
        
        //Criamos um container auxiliar para gerenciar através do SpringLayout
        Container c = new Container();
        c.setLayout(layout);
        //Configuramos a dimensão do container auxiliar, e adicionamos os componentes
        c.setPreferredSize(new Dimension(620,200));
        c.add(jspChat);
        c.add(txfMensagem);
        c.add(btEnviarMensagem);
        
        //Aqui começamos a especificar a posição relativa de cada componete
        //A primeira linha indica que a área de texto deve ter sua posição oeste a 120 pixels de distância da posição oeste do container
        //Como todo container possui seu "oeste" na posição 0, isso significa que a área de texto aparecerá a 120 pixels à oeste do container
        //Isso é o mesmo que dizer a área de texto deve estar 120 pixels à direita do container
        layout.putConstraint(SpringLayout.WEST, jspChat, 120, SpringLayout.WEST, c);
        //Aqui configuramos o posicionamento norte da área de texto. Sua posição norte deve estar a 15 pixels de distância do norte
        //do container. Como todo container possui seu "norte" na posição 0, isso significa que a área de texto aparecerá a 
        //15 pixels abaixo do início desta parte de baixo (execute o jogo para perceber)
        //Isso significa que a área de texto deve estar 15 pixels abaixo do norte do container
        layout.putConstraint(SpringLayout.NORTH, jspChat, 15, SpringLayout.NORTH, c);
        //Aqui já existe um posicionamento diferente
        //O campo de texto deve ter sua posição norte a 120 pixels de distância da área de texto
        //Isso significa que o campo de texto deve aparecer 120 pixels abaixo da área de texto
        layout.putConstraint(SpringLayout.NORTH, txfMensagem, 120, SpringLayout.NORTH, jspChat);
        //O campo de texto deve aparecer a 120 pixels à direita do container
        layout.putConstraint(SpringLayout.WEST, txfMensagem, 120, SpringLayout.WEST, c);
        //Outro posicionamento diferente
        //O botão deve aparecer a 15 pixels à direita do FIM do campo de texto (perceba que WEST sempre indica a posição de início
        //e EAST a de fim de um componente.
        layout.putConstraint(SpringLayout.WEST, btEnviarMensagem, 15, SpringLayout.EAST, txfMensagem);
        //Por fim, ajustamos o botão para aparecer 110 pixels abaixo do norte da área de texto
        layout.putConstraint(SpringLayout.NORTH, btEnviarMensagem, 110, SpringLayout.NORTH, jspChat);
        //Adicionamos tudo na box horizontal
        boxHorizontalBaixo.add(c);
        
        //Adicionamos as boxes no painel do jogo
        this.add(boxHorizontalCima, BorderLayout.NORTH);
        this.add(boxVerticalMeio, BorderLayout.CENTER);
        this.add(boxHorizontalBaixo, BorderLayout.SOUTH);
        
        //Revalida e repinta o painel
        revalidate(); repaint();
        
    }
    /**
     * Retorna um container com as letras de cada linha do tabuleiro
     * 
     * @return container - container com os labels
     */
    private Container criarVetorLetras() {
        
        Container container = new Container();
        container.setPreferredSize(new Dimension(10,250));     
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel label = null;
        
        for(char c = 'A'; c <= 'J'; c++) {
            label = new JLabel(c+"");
            container.add(label);
            container.add(Box.createRigidArea(new Dimension(0,9)));
        }
        return container;
    }

    /**
     * Retorna um container com os números de cada linha do tabuleiro
     * @return container - container com os labels que indicam os números
     */
    private Container criarVetorNumeros() {
        
        Container container = new Container();
        
        container.setPreferredSize(new Dimension(250,10));     
        container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));

        JLabel label = null;
        
        container.add(Box.createRigidArea(new Dimension(30,0)));
        
        for(int i = 0; i < 10; i++) {
            label = new JLabel(i+"");
            container.add(label);
            container.add(Box.createRigidArea(new Dimension(18,0)));
        }
        return container;
    }
    
    //coloca a menssagem do chat na tela do jogador 
    public void atualizaChat(final String texto) {
        
             txaChat.setEditable(true);
             txaChat.append(texto);
             txaChat.setCaretPosition(txaChat.getText().length());
             txaChat.setEditable(false);
    }
    
    public String[][] getMatrizDoJogador() {
    
        return this.meuTabuleiro.getMatrizJogador();
    }
    
    public void setMatrizInimigo(String[][] matrizLogicaDoTabuleiro) {
        
        this.tabuleiroInimigo.setMatrizLogica(matrizLogicaDoTabuleiro);
    }
    
    public JButton getBotaoValidaPosicionamento() {
    
        return this.areaCentral.getBotaoValidaPosicionamento();
    }
    
   //limpa o conteúdo do campo texto do chat
    public void limpaTexto() {
    
        this.txfMensagem.setText("");
    }
    
    //retorna o tabuleiro inimigo
    public TabuleiroInimigo getTabuleiroInimigo() {
    
        return this.tabuleiroInimigo.getTabuleiroInimigo();
    }
    
    //envia as coordenadas da jogada do adversário para serem pintadas no tabuleiro do jogador
    public void configuraHit(int x, int y) {
        this.meuTabuleiro.configuraHit(x,y);
    }
    
    //limpa painel para resetar o jogo
    public void limpaPainel() {
        
        this.areaCentral.mostraNavios();
        this.tabuleiroInimigo.limpaImagens();
        this.tabuleiroInimigo.limpaMatriz();
        this.meuTabuleiro.limpaMatriz();
        this.meuTabuleiro.limpaImagens();
        this.meuTabuleiro.ligarHandlers();
        this.meuTabuleiro.resetaNaviosDestruidos();
        this.painelCentral.resetaPatada();
    }  

    public void setForeground(Color fg) {
    }
}
