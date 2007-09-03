/*
 * BatalhaNavalWindow.java
 *
 * Criado em 25 de Agosto de 2007, 15:17
 *
 */

package batalha.interfacegrafica;

import batalha.interfacegrafica.jogo.*;
import batalha.rede.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;

/**
 * @author Renato, Paulo, Alexandre, Moisés e Marcelo
 */

public class BatalhaNavalWindow extends JFrame{
    
    //Label para o apelido do jogador
    private JLabel lbApelido = null,
            //Label para a decisão entre ser servidor ou cliente
            lbOpcao = null,
            //Label para IPs
            lbIPs = null;
    //TextField para o apelido do jogador
    private JTextField txfApelido = null,
                       txfNovoIP = null;
    //ComboBox com os IPs mais utilizados, formados através de um arquivo
    private JComboBox cbIPs = null;
    //Grupo de RadioButtons, funciona como um gerenciador
    private ButtonGroup bgOpcao = null;
    //Botões de opção para servidor ou cliente
    private JRadioButton jrbServidor = null,
                         jrbCliente = null;
    //Checkbox que deve ser habilitada caso o jogador queira utilizar um novo IP
    private JCheckBox chbNovoIP = null;
    //Arquivo com os endereços IPs já adicionados
    private BufferedReader streamEntrada = null;
    //Stream para escrever o nome IP no arquivo
    private PrintWriter streamSaida = null;
    //Nome do arquivo com os IPs
    private static final String ARQUIVO_IPs = "src/batalha/arquivo_ips.txt";
    //Layout do frame
    private CardLayout layoutFrame = null;
    //Layout do container padrão
    private SpringLayout layoutContainer = null;
    //Indica se o jogador é servidor ou cliente
    private boolean isServidor = false;
    //Handler de eventos da seleção de opção
    private ButtonGroupHandler bgHandler = null;
    //IP selecionado, caso haja
    private String ultimoIP = null;
    //Container Padrão do Frame
    private Container cInicial = null;
    //Botão que inicia tudo hehe ;-)
    private JButton botaoOk = null;
    //Armazena o apelido do jogador
    private String apelidoJogador = null;
    //Painel do Jogo
    private PainelDoJogo painel = null;
    //Conexao e Logica do Jogo 
    
    private Thread jogo;

    
    public BatalhaNavalWindow() {
        
        configuraFrame();
//        configuraBarraDeMenu();
    }
    
    private void configuraFrame() {
        
        //Configura o layout do frame
       // layoutFrame = new CardLayout();
        //this.setLayout(layoutFrame);
        
        //Container inicial
        cInicial = new Container();
        painel = new PainelDoJogo();
        
        //Configura layout do container
        layoutContainer = new SpringLayout();
        cInicial.setLayout(layoutContainer);
        cInicial.setPreferredSize(new Dimension(620,400));
        
        //Inicializa os labels e configura suas dimensões e texto
        lbApelido = new JLabel("Meu apelido: ");
        lbOpcao = new JLabel("Quero ser: ");
        lbIPs = new JLabel("Quero me conectar no IP: ");
        lbApelido.setPreferredSize(new Dimension(80,20)); 
        lbOpcao.setPreferredSize(new Dimension(80,20));
        lbIPs.setPreferredSize(new Dimension(160,20));
        
        //Inicializa o campo de texto e configura sua dimensão
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
                
        //Inicializa os botões, configura suas dimensões e adiciona no grupo de botões
        jrbServidor = new JRadioButton("Servidor");
        jrbCliente = new JRadioButton("Cliente");
        //Inicia o checkBox que indica se o usuário deseja colocar um novo ip
        chbNovoIP = new JCheckBox("Quero colocar um novo ip");
        chbNovoIP.addItemListener(new CheckBoxHandler());
        chbNovoIP.setEnabled(false);
        //Inicializa o handler de eventos
        bgHandler = new ButtonGroupHandler();
        jrbServidor.addItemListener(bgHandler);
        jrbCliente.addItemListener(bgHandler);
        jrbServidor.setSelected(true);
        //Grupo de botão para organizar logicamente os radio buttons
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
     * Inicializa a combo box com os IPs. Este método pode lançar uma excessão se o arquivo não for encontrado.
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
        
        /**
        * @TODO: não consegui dar append nesta string com o restante do arquivo. Vou dar uma pesquisada aqui. Se alguém conseguir,
        * coloque como FEITO.
        *
        * @author Renato
        */
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
     * O propósito desta classe é implementar um listener para os eventos do gerenciador de botões de rádio
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
     * O propósito desta classe é lidar com eventos sobre a combo box
     */    
    private class ComboBoxHandler implements ItemListener{
        
        public void itemStateChanged(ItemEvent e) {
            
            if(e.getStateChange() == ItemEvent.SELECTED){
                    
                    ultimoIP = (String) ((JComboBox)e.getSource()).getSelectedItem();
                    System.out.printf("Ultimo Ip Selecionado: %s",(ultimoIP == null)?"nenhum":ultimoIP);
            }
        }
    }
    
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
    
    private class ButtonHandler implements ActionListener{
        
        public void actionPerformed(ActionEvent ae){
        
            BatalhaNavalWindow.this.remove(BatalhaNavalWindow.this.getContentPane());
            //copia o apelido do jogador para a variável       
            
            //Verifica se o campo de texto para adicionar novo IP está habilitado (se estiver é porque um novo IP foi adicionado)
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
            painel.setServidor(isServidor);
            painel.setVisible(true);

            jogo = new RedeManager(painel);
            BatalhaNavalWindow.this.setContentPane(painel);
            BatalhaNavalWindow.this.validate();
            BatalhaNavalWindow.this.pack();
            SwingUtilities.updateComponentTreeUI(BatalhaNavalWindow.this);
            Som.playAudio(Som.SOM_CONFIG);
            jogo.start(); //inicia a thread do jogo...
        }
    }
        
}
