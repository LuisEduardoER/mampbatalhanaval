     /*
 * BatalhaNavalWindow.java
 *
 * Criado em 25 de Agosto de 2007, 15:17
 *
 */

package batalha.interfacegrafica;

import batalha.gerencia.GerenciadorJogo;
import batalha.interfacegrafica.jogo.*;
import java.util.ArrayList;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;

/**
 * @author Renato, Paulo, Alexandre, Mois�s e Marcelo
 */

public class BatalhaNavalWindow extends JFrame{
    
    //Label para o apelido do jogador
    private JLabel lbApelido = null,
            //Label para a decis�o entre ser servidor ou cliente
            lbOpcao = null,
            //Label para IPs
            lbIPs = null;
    //TextField para o apelido do jogador
    private JTextField txfApelido = null,
                       txfNovoIP = null;
    //ComboBox com os IPs mais utilizados, formados atrav�s de um arquivo
    private JComboBox cbIPs = null;
    //Grupo de RadioButtons, funciona como um gerenciador
    private ButtonGroup bgOpcao = null;
    //Bot�es de op��o para servidor ou cliente
    private JRadioButton jrbServidor = null,
                         jrbCliente = null;
    //Checkbox que deve ser habilitada caso o jogador queira utilizar um novo IP
    private JCheckBox chbNovoIP = null;
    //Arquivo com os endere�os IPs j� adicionados
    private BufferedReader streamEntrada = null;
    //Stream para escrever o nome IP no arquivo
    private PrintWriter streamSaida = null;
    //Nome do arquivo com os IPs
    private static final String ARQUIVO_IPs = "src/batalha/arquivo_ips.txt";
    //Layout do frame
    private CardLayout layoutFrame = null;
    //Layout do container padr�o
    private SpringLayout layoutContainer = null;
    //Indica se o jogador � servidor ou cliente
    private boolean isServidor = false;
    //Handler de eventos da sele��o de op��o
    private ButtonGroupHandler bgHandler = null;
    //IP selecionado, caso haja
    private String ultimoIP = null;
    //Container Padr�o do Frame
    private Container cInicial = null;
    //Bot�o que inicia tudo hehe ;-)
    private JButton botaoOk = null;
    //Armazena o apelido do jogador
    private String apelidoJogador = null;
    //Painel do Jogo
    private PainelDoJogo painel = null;
    //Conexao e Logica do Jogo 
    private Thread jogo;
    //para verificar se o jogo ja foi criado
    private boolean jogoCriado = false;
    //para o Menu    
    private JMenuBar barramenu;
    private JMenu mnuJogo;
    private JMenuItem mnuNovoJogo;
    private JMenuItem mnuSair;
    private JMenu mnuAjuda;
    private JMenuItem mnuComoUsar;
    private JMenu mnuCredito;
    private JMenuItem mnuSobre;
    
    /**
     * Construtor da classe BatalhaNavalWindow
     */
    public BatalhaNavalWindow() {
        
        configuraFrame();
       
    }
    
    private void configuraFrame() {
        
         try {                  
            //para o Splash...espera 9 segundos e pokinhu... para criar a janela principal...
            Thread.sleep( 9000 ); 
        } catch (InterruptedException ex) {
            ex.printStackTrace(); 
        }       
        
        Som.playAudio(Som.BEM_VINDO); //som de boas vindas...
         
        final ImageIcon image = new ImageIcon("src/imagens/gato.gif"); //poe um novo simbolo no lugar do simbolo do java
        setIconImage(image.getImage()); //no frame 
     
  /************************************* BARRA DE MENUS **************************************/  
  
        barramenu = new JMenuBar(); //para criar a barra onde ficara os menus
	mnuJogo = new JMenu("Jogo");//um menu sera CADASTAR
 	mnuJogo.setMnemonic('J');
                
       //os itens do menu jogo
        mnuNovoJogo = new JMenuItem("Novo Jogo");
        mnuNovoJogo.setMnemonic('N');        
        //cria a tecla de atalho F2 para novo jogo
        KeyStroke F2 = KeyStroke.getKeyStroke("F2");
        mnuNovoJogo.setAccelerator( F2 );                   
                
        mnuSair = new JMenuItem("Fechar");
        mnuSair.setMnemonic('S'); //quando pressionado Alt + F4 sai..
        //cria a tecla de atalho Alt + F4 para sair
        KeyStroke altF4 = KeyStroke.getKeyStroke("alt F4");
        mnuSair.setAccelerator(altF4);  
        
        //cria outro menu que � Ajuda
        mnuAjuda = new JMenu("Ajuda");
        mnuAjuda.setMnemonic('A');
        //os itens do menu Ajuda
        mnuComoUsar = new JMenuItem("como jogar...");
        mnuComoUsar.setMnemonic('C');
        //cria um atalho para ajuda quando pressionado a tecla F1
        KeyStroke ajudaF1 = KeyStroke.getKeyStroke("F1");
        mnuComoUsar.setAccelerator( ajudaF1 );
        
        //novo Menu, para exibir Cr�ditos
        mnuCredito = new JMenu("Creditos");//um menu sera CADASTAR 	
        mnuCredito.setMnemonic('C'); 
        
        //sera um item do Menu Creditos
        mnuSobre = new JMenuItem("Sobre");
        mnuSobre.setMnemonic('S');
        
        mnuCredito.add( mnuSobre );
        
        mnuJogo.add(mnuNovoJogo);              
        mnuJogo.addSeparator();
        mnuJogo.add( mnuSair );
        
        mnuAjuda.add( mnuComoUsar ); 
        
        mnuCredito.add( mnuCredito );
              
        barramenu.add( mnuJogo );      //add a barra o Menu Processo  
        barramenu.add( mnuAjuda );    //add a barra o Menu Ajuda
        barramenu.add( mnuCredito );
        
        this.setJMenuBar(barramenu);
         
       /********************************** LISTENER DOS MENUS *************************************/
         
        //QUANDO pressionado ALt + F4 fecha o joogo
        mnuSair.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
                    if(!jogoCriado){
                       System.exit( 0 );
                    }  
         }
        });
        
        mnuSobre.addActionListener( new ActionListener() {  
           public void actionPerformed(ActionEvent event)  
           {  
             
              new Credito(); //chama o construtor da classe Credito      
           }  
              
          }                              
         );  
            
          mnuComoUsar.addActionListener( new ActionListener() {  
             public void actionPerformed(ActionEvent event)  
                {  
                   
               
                 //  image = new ImageIcon( loader.getResource(APPLICATION_ICON_URL)); //poe um novo simbolo no lugar do simbolo do java
               
                   JOptionPane novo = new JOptionPane();
                    novo.showMessageDialog(null,"\n\t\t                                   MAMP&R - Batalha Naval 1.0\n\n" 
                    +"1 - Inicie o jogo colocando seu Nick, e escolhendo entre cliente e servidor\n"  
                    +"2 - Caso escolhido servidor, clique no bot�o  - Ok Estou Pronto!\n"  
                    +"3 - Caso escolhido cliente, selecione o Ip do servidor," +
                    "\n     se desejar adicionar novo Ip habilite o checkbox e insira o novo Ip e aperte o bot�o - Ok Estou Pronto!\n"  
                    +"4 - Posicione suas pe�as no tabuleiro da esquerda e aperte OK\n" 
                    +"5 - O jogador que pressionar primeiro o bot�o OK ser� o primeiro a jogar\n" 
                    +"6 - As vezes entre os jogadores v�o se alternando enquanto acertar as pe�as\n" +
                            " do advers�rio, se acertar �gua a vez � passado para outro jogador\n"
                    +"7 - O jogo termina quando as 18 pe�as de um jogador forem destruidas\n"
                    +"8 - Caso deseja jogar novamente aperte F2\n\n"         
                            +"\t\t\t\t                         BOA DIVERS�O\n\n" 
                    
                    +"\t\t\t                                        � 2007 MAMP&R\n\n ","Como Jogar",
                      JOptionPane.WARNING_MESSAGE,image);
                    
                  }  
               }  
            ); 
        
        /******************************************************************************************/
       
        //Container inicial
        cInicial = new Container();
        painel = new PainelDoJogo( this.mnuSair, this.mnuNovoJogo );
        
        //Configura layout do container
        layoutContainer = new SpringLayout();
        cInicial.setLayout(layoutContainer);
        cInicial.setPreferredSize(new Dimension(620,400));
        
        //Inicializa os labels e configura suas dimens�es e texto
        lbApelido = new JLabel("Meu apelido: ");
        lbOpcao = new JLabel("Quero ser: ");
        lbIPs = new JLabel("Quero me conectar no IP: ");
        lbApelido.setPreferredSize(new Dimension(80,20)); 
        lbOpcao.setPreferredSize(new Dimension(80,20));
        lbIPs.setPreferredSize(new Dimension(160,20));
        
        //Inicializa o campo de texto e configura sua dimens�o
        txfApelido = new JTextField(20);
        txfApelido.setPreferredSize(new Dimension(40,20));
        
        //Inicializa o campo de texto para o novo ip
        txfNovoIP = new JTextField(16);
        txfNovoIP.setPreferredSize(new Dimension(35,20));
        txfNovoIP.setEnabled(false);
        
        //Inicializa a comboBox
        try {
            inicializaComboBox();
        } catch (Exception e) {
            e.printStackTrace();
        }
                
        //Inicializa os bot�es, configura suas dimens�es e adiciona no grupo de bot�es
        jrbServidor = new JRadioButton("Servidor");
        jrbCliente = new JRadioButton("Cliente");
        //Inicia o checkBox que indica se o usu�rio deseja colocar um novo ip
        chbNovoIP = new JCheckBox("Quero colocar um novo ip");
        chbNovoIP.addItemListener(new CheckBoxHandler());
        chbNovoIP.setEnabled(false);
        //Inicializa o handler de eventos
        bgHandler = new ButtonGroupHandler();
        jrbServidor.addItemListener(bgHandler);
        jrbCliente.addItemListener(bgHandler);
        jrbServidor.setSelected(true);
       
        //Grupo de bot�o para organizar logicamente os radio buttons
        bgOpcao = new ButtonGroup();
        bgOpcao.add(jrbServidor);
        bgOpcao.add(jrbCliente);
        
        botaoOk = new JButton("Ok! Estou pronto!");
        botaoOk.setPreferredSize(new Dimension(140,30));
        botaoOk.setEnabled(true);
        botaoOk.addActionListener(new ButtonHandler());
        
        this.setContentPane(cInicial);
        cInicial.add(lbApelido);
        cInicial.add(lbOpcao);
        cInicial.add(lbIPs);
        cInicial.add(txfApelido);
        cInicial.add(cbIPs);
        cInicial.add(jrbServidor);
        cInicial.add(jrbCliente);
        cInicial.add(chbNovoIP);
        cInicial.add(txfNovoIP);
        cInicial.add(botaoOk);

        //Configura o layout do container
        configuraLayout();
        
        
        
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //ponhe o frame no centro da tela...
        Dimension posicao = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((posicao.width - this.getSize().width)/2,(posicao.height - this.getSize().height)/2);
        
        this.setVisible(true);
    }
    
    
    /**
     * Configura o layout do frame
     */
    private void configuraLayout() {
        
        layoutContainer.putConstraint(SpringLayout.NORTH, lbApelido, 50, SpringLayout.NORTH, cInicial);
        layoutContainer.putConstraint(SpringLayout.WEST, lbApelido, 100, SpringLayout.WEST, cInicial);
        layoutContainer.putConstraint(SpringLayout.NORTH, txfApelido, 50, SpringLayout.NORTH, cInicial);
        layoutContainer.putConstraint(SpringLayout.WEST, txfApelido, 10, SpringLayout.EAST, lbApelido);
        layoutContainer.putConstraint(SpringLayout.NORTH, lbOpcao, 25, SpringLayout.SOUTH, lbApelido);
        layoutContainer.putConstraint(SpringLayout.WEST, lbOpcao, 100, SpringLayout.WEST, cInicial);
        layoutContainer.putConstraint(SpringLayout.NORTH, jrbServidor, 25, SpringLayout.SOUTH, lbApelido);
        layoutContainer.putConstraint(SpringLayout.WEST, jrbServidor, 25, SpringLayout.EAST, lbOpcao);
        layoutContainer.putConstraint(SpringLayout.NORTH, jrbCliente, 25, SpringLayout.SOUTH, lbApelido);
        layoutContainer.putConstraint(SpringLayout.WEST, jrbCliente, 15, SpringLayout.EAST, jrbServidor);
        layoutContainer.putConstraint(SpringLayout.NORTH, lbIPs, 25, SpringLayout.SOUTH, lbOpcao);
        layoutContainer.putConstraint(SpringLayout.WEST, lbIPs, 100, SpringLayout.WEST, cInicial);
        layoutContainer.putConstraint(SpringLayout.NORTH, cbIPs, 25, SpringLayout.SOUTH, lbOpcao);
        layoutContainer.putConstraint(SpringLayout.WEST, cbIPs, 15, SpringLayout.EAST, lbIPs);
        layoutContainer.putConstraint(SpringLayout.NORTH, chbNovoIP, 25, SpringLayout.SOUTH, lbIPs);
        layoutContainer.putConstraint(SpringLayout.WEST, chbNovoIP, 10, SpringLayout.EAST, lbIPs);
        layoutContainer.putConstraint(SpringLayout.NORTH, txfNovoIP, 25, SpringLayout.SOUTH, chbNovoIP);
        layoutContainer.putConstraint(SpringLayout.WEST, txfNovoIP, 10, SpringLayout.EAST, lbIPs);
        layoutContainer.putConstraint(SpringLayout.NORTH, botaoOk, 55, SpringLayout.SOUTH, txfNovoIP);
        layoutContainer.putConstraint(SpringLayout.WEST, botaoOk, 5, SpringLayout.WEST, chbNovoIP);
    }
    
  
    
    /**
     * Inicializa a combo box com os IPs. Este m�todo pode lan�ar uma excess�o se o arquivo n�o for encontrado.
     */
    private void inicializaComboBox() throws IOException{
        
        ArrayList<String> ips = new ArrayList<String>();
        
        try {
            
            streamEntrada = new BufferedReader(new FileReader(ARQUIVO_IPs));
            String l = null;
           
            
            l = streamEntrada.readLine();
            
            if(l == null){
                
                ips.add(" ");
               
            }
            else{ 
                String ipsStr[] = l.split("/");
            
            
            for(String ip: ipsStr)
                ips.add(ip);
            }
            if(ips.size() > 0) ultimoIP = ips.get(0);
            cbIPs = new JComboBox(ips.toArray());
            cbIPs.setPreferredSize(new Dimension(200,25));
            cbIPs.setEditable(false);
            cbIPs.addItemListener(new ComboBoxHandler());       
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            
            if(streamEntrada != null)
                    streamEntrada.close();
         }        
    }
    
    /**
     * Adiciona um novo ip no arquivo
     */
    private void adicionarNovoIP(String novoIP) {
       
        try {
            
            streamSaida = new PrintWriter(new FileWriter(ARQUIVO_IPs,true));
            CharSequence cs = novoIP.subSequence(0, novoIP.length()-1) + "/";            
            streamSaida.append(cs);
            streamSaida.flush();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            
            if(streamSaida != null)
                    streamSaida.close();
         }           
    }
   
    
    /**
     * ButtonGroupHandler.java
     *
     * Criado em 25 de Agosto de 2007, 22:08
     *
     * O prop�sito desta classe � implementar um listener para os eventos do gerenciador de bot�es de r�dio
     */
    private class ButtonGroupHandler implements ItemListener{
        
        public void itemStateChanged(ItemEvent ae){
            
            if(ae.getStateChange() == ItemEvent.SELECTED){
                
                if(jrbServidor == (JRadioButton)ae.getSource()){
                        
                     isServidor = true;
                     cbIPs.setEnabled(false);
                     chbNovoIP.setEnabled(false);
                }
                else{
                    
                     isServidor = false;
                     cbIPs.setEnabled(true);
                     chbNovoIP.setEnabled(true);
                }
               
            }
        }
    }
    
    /**
     * ComboBoxHandler.java
     *
     * Criado em 25 de Agosto de 2007, 22:28
     *
     * O prop�sito desta classe � lidar com eventos sobre a combo box
     */    
    private class ComboBoxHandler implements ItemListener{
        
        public void itemStateChanged(ItemEvent e) {
            
            if(e.getStateChange() == ItemEvent.SELECTED){
                    
                    ultimoIP = (String) ((JComboBox)e.getSource()).getSelectedItem();
                    System.out.printf("Ultimo Ip Selecionado: %s",(ultimoIP == null)?"nenhum":ultimoIP);
            }
        }
    }
    
    /**
     * CheckBoxHandler.java
     *
     * Criado em 25 de Agosto de 2007, 22:28
     *
     * O prop�sito desta classe � lidar com eventos sobre o checkbox
     */ 
    private class CheckBoxHandler implements ItemListener{
        
        public void itemStateChanged(ItemEvent e){
            
            if(e.getStateChange() == ItemEvent.SELECTED){
                
                txfNovoIP.setEnabled(true);
                cbIPs.setEnabled(false);
            }
            if(e.getStateChange() == ItemEvent.DESELECTED){
                
                txfNovoIP.setEnabled(false);
                cbIPs.setEnabled(true);
            }
        }
    }
    
    /**
     * ButtonHandler.java
     *
     * Criado em 25 de Agosto de 2007, 22:28
     *
     * O prop�sito desta classe � lidar com eventos sobre o bot�o de jogar
     */
    private class ButtonHandler implements ActionListener{
        
        public void actionPerformed(ActionEvent ae){
        
            BatalhaNavalWindow.this.remove(BatalhaNavalWindow.this.getContentPane());
      
            
            //Verifica se o campo de texto para adicionar novo IP est� habilitado (se estiver � porque um novo IP foi adicionado)
            //e se algum IP foi realmente digitado ali
            if(txfNovoIP.isEnabled() && txfNovoIP.getText() != null){
                        
                    adicionarNovoIP(txfNovoIP.getText()+"\n");
                    ultimoIP = txfNovoIP.getText(); 
            }    
            
            //funcoes para passar o nick e o Ip para Painel Do Jogo
            painel.setNick( txfApelido.getText() ); 
            
            if(chbNovoIP.isSelected()) {
                painel.setIp(txfNovoIP.getText());
            }
            else {
                painel.setIp( (String) cbIPs.getSelectedItem() );
            }
            
            //funcao do Painel do jogo
            painel.setServidor(isServidor);
            painel.setVisible(true);
            jogoCriado = true;

            //Configura  a thread de jogo e inicializa
            jogo = new GerenciadorJogo(painel);
            BatalhaNavalWindow.this.setContentPane(painel);
            BatalhaNavalWindow.this.validate();
            BatalhaNavalWindow.this.pack();
            SwingUtilities.updateComponentTreeUI(BatalhaNavalWindow.this);
            Som.playAudio(Som.SOM_CONFIG);
            
            //verifica se o jogador � servidor ou cliente...e logo em seguida
            //seta o titulo, com cliente ou servidor mas o Nick 
            if(painel.getServidor())
                setTitle("Servidor - " + painel.getNick() );
            else setTitle("Cliente - " + painel.getNick() );
            
            jogo.start(); 
        }
    }
    
    public JMenuItem getNovoJogo(){
     return this.mnuNovoJogo;
    }
    
    public JMenuItem getSair(){
        return this.mnuSair;
    }
        
}
