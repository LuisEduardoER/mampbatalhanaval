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
 * @author  Renato, Paulo, Alexandre, Mois�s e Marcelo
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
     * Segue uma explica��o do uso desta classe:
     * 
     * Esta � a classe de interface mais importante do jogo, que abriga todos os elementos gr�ficos que o comp�em. � tamb�m a classe que
     * mais modifiquei. Ao come�ar a ler o c�digo gerado pelo construtor de interface do NetBeans, simplesmente me perdi. Ent�o acabei
     * fazendo direto na "m�o", utilizando pelo menos 2 importantes gerenciadores: BoxLayout e SpringLayout. Como j� indiquei link do
     * tutorial do SpringLayout na classe DadosRede, deixo aqui o link para o tutorial do BoxLayout da Sun: 
     * http://java.sun.com/docs/books/tutorial/uiswing/layout/box.html
     */
   
    //Label para mostrar de quem � a vez
    private JLabel lbVez, 
                   lbPontuacao;
    //TextField para envio de mensagens no txaChat
    private JTextField txfMensagem;
    //Area Central, que primeiro exibe os navios depois os dados da rede
    //@see batalha#interfacegrafica#jogo.AreaCentral
    private AreaCentral areaCentral;
    //Bot�o para enviar mensagem
    private JButton btEnviarMensagem;
    //�rea de texto para o chat
    private JTextArea txaChat;
    //Exibe a �rea de texto em um campo
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
    //boolean que indica que existe uma conex�o estabelecida (true) ou n�o (false)
    private boolean conectado = false;
    //boolean que indica que o jogador montou seu tabuleiro e serve para definir quem
    //come�a o jogo, pois aquele que montar o tabuleiro primeiro tem o direito de come�ar
    private boolean jogadorPronto;
    //boolean que indica a vez: true = vez do jogador e false = vez do advers�rio
    private boolean vez;
    //inteiro que armazena a pontua��o do jogador
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
    //seta o apelido que o jogador entrar, este metodo � chamado na classe BatalhaNavalWindow
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
    
    //retorna o bot�o enviar mensagem
    public JButton getBtEnviarMensagem() {
    
        return this.btEnviarMensagem;
    }
    
    //retorna o texto de txfMensagem na forma de String
    public String getTextoMensagem() {
    
        return this.txfMensagem.getText();
    }
    
    //retorna a �rea central
    public AreaCentral getAreaCentral(){

       return this.areaCentral;
    }

    //retorna o boolean que indica se existe conex�o estabelecida
    public boolean getConectado() {
        
        return this.conectado;
    }
    
    //seta o boolean que indica se existe conexao estabelecida
    public void setConectado(boolean con) {
        
        this.conectado = con;
        this.areaCentral.setConectado(con);
    }
    
    //seta o boolean respons�vel por indicar que o jogador preparou seu tabuleiro
    public void setJogadorPronto(boolean jog) {
        
        this.jogadorPronto = jog;
    }
    
    //retorna o boolean que indica que o tabuleiro est� pronto para o jogo
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
    
  
    //retorna o boolean que indica de quem � a vez
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
    
    //acrescenta 1 ao n�mero de pontos do jogador
    public void somaPontuacao() {
        
        this.pontos++;
        
        final Font font = new Font("Arial Bold", Font.BOLD, 15); 
        
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    lbPontuacao.setFont( font );
                    lbPontuacao.setForeground( Color.BLUE );
                    lbPontuacao.setText("Sua pontua��o atual � " + pontos );
                }
            }
        );
    }
    
    //retorna a pontua��o do jogador
    public int getPontos() {
        
        return this.pontos;
    }
    
    //zera a pontua��o
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
     * Inicializa os componentes que ser�o adicionados a este painel
     */
    private void initComponents() {
        
        //Primeiro configuramos o gerenciador de layout para BorderLayout. Este gerenciador � muito simples, e permite que os containeres
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
        this.lbVez = new JLabel("Este label indica de quem � a vez");
        lbVez.setFont( font );
        lbVez.setForeground( Color.GREEN);
        this.lbVez.setPreferredSize(new Dimension(0,0));
        
        //inicia o label da pontua��o
        this.lbPontuacao = new JLabel("          Este label indica sua pontua��o");
        lbPontuacao.setFont( font );
        lbPontuacao.setForeground( Color.GREEN);
        this.lbPontuacao.setPreferredSize(new Dimension(0,0));
        //inicialmente n�o existe conex�o estabelecida
        this.conectado = false;

        //inicialmente o advers�rio est� montando seu tabuleiro
        this.jogadorPronto = false;
        
        //a vez deve ser false at� o jogo come�ar
        this.vez = false;
        
        //o jogador come�a com 0 ponto e, se tiver sorte, esse n�mero muda
        this.pontos = 0;
        
        //Cria uma box, que � um gerenciador de containeres. Neste caso criamos uma box horizontal, e todo container adicionado � box
        //o ser� da esquerda para a direita. Para quem quiser saber mais sobre Box (BoxLayout), indiquei o link do tutorial da Sun
        //na descri��o da classe.
        Box boxHorizontalCima = Box.createHorizontalBox();
        
        //Aqui criamos 4 box auxiliares. A box1CimaX � uma box horizontal que adiciona o container com as letras e o tabuleiro do
        //jogador. Depois "empacotamos" esta box na box1CimaY, junto com o container com os n�meros. O mesmo acontece com box2CimaX
        //e box2CimaY. Explicarei melhor no decorrer do c�digo ;-)
        Box box1CimaX = Box.createHorizontalBox(); Box box2CimaX = Box.createHorizontalBox();
        Box box1CimaY = Box.createVerticalBox(); Box box2CimaY = Box.createVerticalBox();
        
        //Criamos uma box para adicionar o painel do jogo. Est� apenas em amarelo agora
        Box boxLogo = Box.createVerticalBox();        
        //Dimens�es para adicionar o painel com o logo
        Dimension distLogoCima = new Dimension(80,20);
        Dimension distLogoBaixo = new Dimension(80,10);
        //Colocamos uma dist�ncia, empacotamos o logo, e depois colocamos outra dist�ncia
        boxLogo.add(new Box.Filler(distLogoCima,distLogoCima,distLogoCima));
        boxLogo.add(painelCentral);
        boxLogo.add(new Box.Filler(distLogoBaixo,distLogoBaixo,distLogoBaixo));
        
        //Dimens�es padr�o para distanciamento, necess�rio para Box.Filler
        Dimension distPadraoX = new Dimension(10, 249); 
        Dimension distPadraoY  = new Dimension(250,10);
        
        //Criamos os containeres com as letras e n�meros. bottom1 � um container que serve apenas para dar um espa�o de preenchimento.
        Container letras = criarVetorLetras();
        Container numeros = criarVetorNumeros();
        Container bottom1 = new Container();
        
        bottom1.setPreferredSize(new Dimension(251,15));
        
        //Primeiro adicionamos um espa�o de preenchimento na box horizontal, com dimens�o 10,252. Isto cria o espa�o entre o lado 
        //esquerdo do frame e as letras
        box1CimaX.add(new Box.Filler(distPadraoX,distPadraoX,distPadraoX));
        //Adicionamos as letras  e o tabuleiro do jogador
        box1CimaX.add(letras);
        box1CimaX.add(meuTabuleiro);
        
        //Depois adicionamos um espa�o de preenchimento similar � box horizontal, mas agora na vertical. Isto cria o espa�o entre o
        //a parte de cima do frame e os n�meros
        box1CimaY.add(new Box.Filler(distPadraoY,distPadraoY,distPadraoY));
        //Adicionamos os n�meros e a box horizontal, para empacotar tudo numa grande box
        box1CimaY.add(numeros);
        box1CimaY.add(box1CimaX);
        //Adicionamos um espa�o de preenchimento tamb�m na vertical. Isto cria o espa�o entre o tabuleiro e o painel com os bot�es 
        //(ou dados da rede).
        box1CimaY.add(new Box.Filler(distPadraoY,distPadraoY,distPadraoY));
        //Por fim, adicionamos a box que cont�m o tabuleiro, letras e n�meros na box horizontal de cima. Entenda que isto funciona 
        //basicamente como uma s�rie de empacotamentos de containeres. At� aqui, adicionamos apenas o tabuleiro do jogador
        boxHorizontalCima.add(box1CimaY);
        boxHorizontalCima.add(new Box.Filler(distPadraoX,distPadraoX,distPadraoX));
        //Adicionamos agora o painel do jogo. Perceba que o espa�amento d� a ilus�o de que este painel possui o mesmo tamanho dos ta
        //buleiros (a id�ia � essa... )
        boxHorizontalCima.add(boxLogo);
        
        //A partir daqui o princ�pio � o mesmo, s� que agora iremos configurar o tabuleiro do inimigo. Criamos os containeres com
        //as letras e n�meros (n�o podemos utilizar os containeres j� criados pois o Java n�o suporta que dois containeres sejam usa
        //dos no mesmo container-pai. Ou seja, se tentarmos usar os containeres letra e numero aqui novamente, eles n�o apareceram
        //onde foram previamente colocados
        Container letras2 = criarVetorLetras();
        Container numeros2 = criarVetorNumeros();
        
        //Criamos um espa�amento entre o painel com o logo e o tabuleiro do inimigo
        box2CimaX.add(new Box.Filler(distPadraoX,distPadraoX,distPadraoX));
        //Empacotamos as letras, depois o tabuleiro
        box2CimaX.add(letras2);
        box2CimaX.add(tabuleiroInimigo);

        Container bottom2 = new Container();
        
        bottom2.setPreferredSize(new Dimension(251,15));
        
        //Criamos um espa�amento entre a parte de cima do frame e o tabuleiro
        box2CimaY.add(new Box.Filler(distPadraoY,distPadraoY,distPadraoY));
        //Empacotamos os n�meros e a box horizontal
        box2CimaY.add(numeros2);
        box2CimaY.add(box2CimaX);
        //Damos um espa�amento entre este painel e lado esquerdo do frame
        box2CimaY.add(new Box.Filler(distPadraoY,distPadraoY,distPadraoY));
        //Empacotamos a box com o tabuleiro do inimigo na box principal de cima. 
        boxHorizontalCima.add(box2CimaY);
        //Damos um espa�o entre o painel do tabuleiro e o painel com os navios. At� aqui configuramos toda a �rea dos tabuleiros.
        boxHorizontalCima.add(new Box.Filler(distPadraoX,distPadraoX,distPadraoX));
        
        //Esta box empacota os labels de pontua��o e vez
        Box boxHorizontalMeio1 = Box.createHorizontalBox();
        
        boxHorizontalMeio1.add(lbVez);
        Dimension distLabel = new Dimension(100,50);
        boxHorizontalMeio1.add(new Box.Filler(distLabel,distLabel,distLabel));
        boxHorizontalMeio1.add(lbPontuacao);
        
        //Esta box simplesmente empacota a �rea central, e ser� adicionada no meio deste objeto
        Box boxHorizontalMeio2 = Box.createHorizontalBox();
        boxHorizontalMeio2.add(areaCentral);
        
        Box boxVerticalMeio = Box.createVerticalBox();
        
        boxVerticalMeio.add(boxHorizontalMeio1);
        boxVerticalMeio.add(boxHorizontalMeio2);
        
        //Por fim, configuramos a parte do chat. Esta parte tamb�m n�o � muito dif�cil. Primeiro criamos uma box horizontal
        Box boxHorizontalBaixo = Box.createHorizontalBox();
        
        //Depois criamos 3 box auxiliares, duas horizontais e uma vertical. A primeira horizontal empacota a �rea de texto,
        //enquanto a segunda empacota o campo de texto e o bot�o para enviar mensagem. Por fim, empacotamos tudo na box 
        //vertical
        Box box3BaixoX1 = Box.createHorizontalBox(); Box box3BaixoY = Box.createVerticalBox();
        Box box3BaixoX2 = Box.createHorizontalBox();

        //Configuramos a �rea de vis�o, dimens�o e tamanho (em termos de coluna de texto) da �rea de texto.
       
        jspChat = new JScrollPane(txaChat);                                 
        txaChat.setColumns(30);
        txaChat.setLineWrap(true);
        jspChat.setPreferredSize(new Dimension(350,100));
        //Configuramos a pol�tica da �rea de texto, caso seja necess�rio habilitar scroll vertical e/ou horizontal
        jspChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        txaChat.setEditable(true);
        
        //Configuramos o bot�o para enviar mensagem 
        //@TODO: adicionar listener
        btEnviarMensagem.setPreferredSize(new Dimension(80,30));
        btEnviarMensagem.setText("Enviar");
        
        //Inst�ncia do gerenciador de layout SpringLayout. Este gerenciador � muito pr�tico e poderoso, e funciona de maneira bem simples
        //Tudo que devemos fazer � especificar o posicionamento de um container em rela��o a outro, de acordo com uma coordenada. Explica
        //rei no decorrer do c�digo
        SpringLayout layout = new SpringLayout();
        
        //Criamos um container auxiliar para gerenciar atrav�s do SpringLayout
        Container c = new Container();
        c.setLayout(layout);
        //Configuramos a dimens�o do container auxiliar, e adicionamos os componentes
        c.setPreferredSize(new Dimension(620,200));
        c.add(jspChat);
        c.add(txfMensagem);
        c.add(btEnviarMensagem);
        
        //Aqui come�amos a especificar a posi��o relativa de cada componete
        //A primeira linha indica que a �rea de texto deve ter sua posi��o oeste a 120 pixels de dist�ncia da posi��o oeste do container
        //Como todo container possui seu "oeste" na posi��o 0, isso significa que a �rea de texto aparecer� a 120 pixels � oeste do container
        //Isso � o mesmo que dizer a �rea de texto deve estar 120 pixels � direita do container
        layout.putConstraint(SpringLayout.WEST, jspChat, 120, SpringLayout.WEST, c);
        //Aqui configuramos o posicionamento norte da �rea de texto. Sua posi��o norte deve estar a 15 pixels de dist�ncia do norte
        //do container. Como todo container possui seu "norte" na posi��o 0, isso significa que a �rea de texto aparecer� a 
        //15 pixels abaixo do in�cio desta parte de baixo (execute o jogo para perceber)
        //Isso significa que a �rea de texto deve estar 15 pixels abaixo do norte do container
        layout.putConstraint(SpringLayout.NORTH, jspChat, 15, SpringLayout.NORTH, c);
        //Aqui j� existe um posicionamento diferente
        //O campo de texto deve ter sua posi��o norte a 120 pixels de dist�ncia da �rea de texto
        //Isso significa que o campo de texto deve aparecer 120 pixels abaixo da �rea de texto
        layout.putConstraint(SpringLayout.NORTH, txfMensagem, 120, SpringLayout.NORTH, jspChat);
        //O campo de texto deve aparecer a 120 pixels � direita do container
        layout.putConstraint(SpringLayout.WEST, txfMensagem, 120, SpringLayout.WEST, c);
        //Outro posicionamento diferente
        //O bot�o deve aparecer a 15 pixels � direita do FIM do campo de texto (perceba que WEST sempre indica a posi��o de in�cio
        //e EAST a de fim de um componente.
        layout.putConstraint(SpringLayout.WEST, btEnviarMensagem, 15, SpringLayout.EAST, txfMensagem);
        //Por fim, ajustamos o bot�o para aparecer 110 pixels abaixo do norte da �rea de texto
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
     * Retorna um container com os n�meros de cada linha do tabuleiro
     * @return container - container com os labels que indicam os n�meros
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
    
   //limpa o conte�do do campo texto do chat
    public void limpaTexto() {
    
        this.txfMensagem.setText("");
    }
    
    //retorna o tabuleiro inimigo
    public TabuleiroInimigo getTabuleiroInimigo() {
    
        return this.tabuleiroInimigo.getTabuleiroInimigo();
    }
    
    //envia as coordenadas da jogada do advers�rio para serem pintadas no tabuleiro do jogador
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
