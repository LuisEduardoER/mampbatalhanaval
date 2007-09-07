/*
 * GerenciadorJogo.java
 *
 * Criado em 27 de Agosto de 2007, 22:44
 *
 * Esta classe gerencia o controle do fluxo de rede, e controla a lógica de sincronização do jogo
 */

package batalha.gerencia;

import batalha.interfacegrafica.DadosRede; //para setar os dados de entrada e saida
import batalha.interfacegrafica.jogo.AreaCentral;
import batalha.interfacegrafica.PainelDoJogo;
import batalha.interfacegrafica.jogo.Som;
import static batalha.interfacegrafica.jogo.TabuleiroInimigo.*;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Renato, Paulo, Alexandre, Moisés e Marcelo
 */
public class GerenciadorJogo extends Thread {
    
    private PainelDoJogo painel;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private ServerSocket servidor;
    private Socket conexao;
    
    /** Creates a new instance of ServidorRede */
    public GerenciadorJogo(PainelDoJogo p) {

        this.painel = p;
    }
           
 public void run(){

    //escuta o enter no campo de texto do chat e envia a mensagem
     painel.getTxfMensagem().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    EnviaDado( ae.getActionCommand(), "Chat" );
                    painel.limpaTexto();
                }
            }
    );
    
       //chama o metodo Listener para fechar o programa
        painel.getSaida().addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
                try {
                    FechaConexao();  
                } catch (IOException ex) {
                    ex.printStackTrace();
                }  
                        System.exit( 0 );
         }
        });
        
        //chama o metodo para criar um novo jogo, ou pressionando a tecla f2
        //esse metodo esta relacionado com o JMenuBar da classe BatalhaNavalWindow
        painel.getNovoJogo().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                 EnviaDado("*", "Jogada");  
                 ReinciaJogo();                               
        }
        });
        
    //escuta o botão enviar do chat e envia a mensagem
    painel.getBtEnviarMensagem().addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    EnviaDado(painel.getTextoMensagem(), "Chat");
                    painel.limpaTexto();
                }
        }
    );

    //escuta o click no tabuleiro inimigo
    painel.getTabuleiroInimigo().addMouseListener(
            new MouseAdapter()  {
            public void mousePressed(MouseEvent me) {
            if(painel.getVez()) {
                
                if(painel.getPainelCentral().ehJogadaPatada()){
                    boolean acabou = false;
                
                    int resultado[][] = painel.getTabuleiroInimigo().getHitArea(me.getX(),me.getY(),100,100);                    
                    
                    for(int i = 0; i < resultado.length; i++){
                        for(int j = 0; j < resultado[0].length; j++){
                                                    
                            if(resultado[i][j] == -1) continue;
                            String msg = new String();
                            msg = msg + "!";
                            msg = msg + (i * 25);
                            msg = msg + ",";
                            msg = msg + (j * 25);
                            msg = msg + ",";
                            painel.getAreaCentral().getDadosDaRede().addDadoEnviado( msg );
                            EnviaDado(msg, "Jogada");

                            //acertou um navio
                            if(resultado[i][j] == HIT_NAVIO) {
                                painel.somaPontuacao();
                                //o jogo acaba quando o jogador faz 18 pontos
                                if(painel.getPontos() == 18) {
                                    //envia derrota para o adversário
                                    EnviaDado("Z", "Jogada");
                                    //exibe mensagem exaltando a vitória
                                    JOptionPane.showMessageDialog(null,"PARABÉNS, CONTINUE ASSIM", "VITÓRIA", JOptionPane.WARNING_MESSAGE);
                                    Som.playAudio(Som.VITORIA);
                                    //reinicia o jogo
                                    EnviaDado("*","Jogada");
                                    ReinciaJogo();
                                    acabou = true;
                                }
                            }
                            if(acabou) break;
                        }
                        if(acabou) break;
                    }
                    painel.getPainelCentral().desabilitaPatada();

                    //perde a vez de qualquer maneira, pois usou a patada
                    if(!acabou) {
                        EnviaDado("X", "Jogada");
                    }
                    painel.setVez(false);
                    
                } else{
                    String msg = new String();
                    msg = msg + "!";
                    msg = msg + me.getX();
                    msg = msg + ",";
                    msg = msg + me.getY();
                    msg = msg + ",";
                    EnviaDado(msg, "Jogada");
                    //invoca metodo para mostrar os dados das coordenadas clicadas que estara passando pela rede
                    painel.getAreaCentral().getDadosDaRede().addDadoEnviado( msg );
                    
                    int acerto = painel.getTabuleiroInimigo().getHit(me.getX(), me.getY());
                    //se acertou água, perde a vez e avisa ao adversário
                    if(acerto == HIT_AGUA) {
                        EnviaDado("X", "Jogada");
                        painel.setVez(false);
                    }
                    //se acertou navio deve somar 1 ponto ao placar do jogador
                    else if(acerto == HIT_NAVIO) {
                        painel.somaPontuacao();
                        //o jogo acaba quando o jogador faz 18 pontos
                        if(painel.getPontos() == 18) {
                            //envia derrota para o adversário
                            EnviaDado("Z", "Jogada");                         
                            //exibe mensagem exaltando a vitória
                            JOptionPane.showMessageDialog(null,"PARABÉNS, CONTINUE ASSIM", "VITÓRIA", JOptionPane.WARNING_MESSAGE);
                            Som.playAudio(Som.VITORIA);
                            //reinicia o jogo
                            EnviaDado("*","Jogada");
                            ReinciaJogo();
                        }
                    }
                }
            }
        }
    }
    );   
    
    painel.getBotaoPatada().addActionListener(
        new ActionListener(){
            
            public void actionPerformed(ActionEvent ae){
                    
                painel.getTabuleiroInimigo().setJogadaPatada();
                painel.getPainelCentral().setBotaoClickado();
            }
        }
    );
    
    //escuta o botão ok e envia a matriz do jogador para o adversário
    painel.getBotaoValidaPosicionamento().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    if(painel.getConectado()) {
                        painel.setJogadorPronto(true);
                        String matriz[][] = painel.getMatrizDoJogador();
                        String msg ="";
                        msg = msg + "#";
                       CardLayout c = (CardLayout) painel.getAreaCentral().getLayout();
                       c.show(painel.getAreaCentral(), AreaCentral.REDE_PANE);
                       //Habilita o tabuleiro inimigo
                       painel.getTabuleiroInimigo().ligar();
                       //Toca o som de configuração de navio
                       Som.stopAudio(Som.SOM_CONFIG);    
                        //transforma a matriz em um String
                        for(int i = 0;i<10;i++) {
                            for(int j=0;j<10;j++) {
                                msg = msg + matriz[i][j];
                                msg = msg + ",";
                            }
                        }
                        EnviaDado( msg, "Matriz" );
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"AGUARDE A CONEXÃO SER ESTABELECIDA E TENTE NOVAMENTE", "ERRO DE CONEXÃO", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
    );
    
    //se o jogador criou o servidor, chama a função que instancia o ServerSocket
     if(this.painel.getServidor()) {
            RodaServidor();
        }
    
    //senão, estabelece uma conexão como cliente
        else {
            RodaCliente();
        }
 }
 
    private void EnviaDado( String msg, String origem ){ // funcao que envia a msg...
     try{
         if(origem == null) return;
         
         if(origem.equals("Chat")) {
             String aux = new String();
             aux = painel.getNick()+ " diz: " + msg; 
             painel.atualizaChat(aux + "\n"); //escreve na propria tela
             painel.getAreaCentral().getDadosDaRede().addDadoEnviado("@" + aux + "\n");
             saida.writeObject("@" + aux );//escreve no buffer..
         }
         else if((origem.equals("Matriz")) || (origem.equals("Jogada") || origem.equals("Patada"))) {
             painel.getAreaCentral().getDadosDaRede().addDadoEnviado(  msg + "\n" );
             saida.writeObject(msg);
         }
         saida.flush(); //limpar o buffer
         
      }catch( IOException io){//caso de erro...
         painel.setConectado(false);
         painel.atualizaChat("\nConexao perdida.");
         painel.atualizaChat("\n\nDESDE JA OBRIGADO! EQUIPE MAMP&R ");
     }
 }
 
    public void RodaServidor() //configura e executa o servidor...
   {
       
    try{
        //cria um serverSocket na porta 5000 e o tamanho da fila 1, ou seja, maximo de clientes que podem se conectar no sevidor 
        servidor = new ServerSocket( 5000, 1); //
        
        while( true ){
            EsperaConexao();
            PegaStream();//obtem o fluxo de entrada e saida, ou seja os dados...msgs...
            ProcessaConexao();// processa a conexao
            FechaConexao(); //fecha a conexao...
        }
    }
    catch( EOFException eof){ //EXCEÇÕES...
        System.out.println( "Terminado a conexao do Cliente");
    }
   catch( IOException io){
       io.printStackTrace();
   }
}   

    public void RodaCliente(){//configura e executa o cliente
         try{
                ConectanoServidor();//para se conectar no servidor...
                PegaStream();//para o envio e recebimento do chat...
                ProcessaConexao(); //responsavel pela conexao entre ambas...
                FechaConexao(); //fecha a conexao...
            }
        catch( EOFException eof){
              System.out.println( "Servidor termina a conexão");
           }
        catch( IOException io){
            io.printStackTrace();
       }
   }

  private void EsperaConexao() throws IOException{ //espera por conexao...
             painel.atualizaChat( "Esperando por Conexao...\n");
             conexao = servidor.accept(); //quando chegado na porta aceita...
             //conexao estabelecida no servidor
             painel.setConectado(true);
             //mostra msg de recebimento...
             painel.atualizaChat( "Conexao recebida de: " +
             conexao.getInetAddress().getHostName() + "\n");
  }  

    //se conectando no servidor...
   private void ConectanoServidor() throws IOException{
           
      conexao = new Socket( InetAddress.getByName( painel.getIp()), 5000);//liga na porta 5000
      //conexao estabelecida no cliente
      painel.setConectado(true);

      //mostra que a conexao foi feita no nome de conexao tal...
      painel.atualizaChat("Conectado no: " + conexao.getInetAddress().getHostName() + "\n");
   }

    //obtem os fluxos para enviar e receber dados...
    private void PegaStream() throws IOException{
         saida = new ObjectOutputStream( conexao.getOutputStream() );

         saida.flush();//limpa o buffer de saida para enviar as informacoes 
         //configura o buffer de entrada para os dados
         entrada = new ObjectInputStream( conexao.getInputStream() );
   }

    //processa a conexao com o cliente...
    private void ProcessaConexao() throws IOException{
          
         String msg = "CONEXÃO COMPLETADA\n";
         painel.atualizaChat("___________Bem vindo ao MAMP Messenger___________\n" );
         String msg_fecha = "";
         saida.writeObject( msg ); //escreve no display do cliente quando feita a conexao..

         //invoca metodo para mostrar os dados que estara passando pela rede  
         painel.getAreaCentral().getDadosDaRede().addDadoEnviado( msg + "\n");

         saida.flush(); //limpa o buffer de saida...

         do{
             try{
                 msg = ( String ) entrada.readObject();

                 //invoca metodo para mostrar os dados que estara passando pela rede  
                 painel.getAreaCentral().getDadosDaRede().addDadoRecebido( msg + "\n")     ;

                 String pacote = msg.substring(0,1);
                 if(pacote.equals("@")) {
                    painel.atualizaChat(msg.substring(1) + "\n");
                    
                    Som.playAudio(Som.CHAT);
                 } 
                 else if(pacote.equals("*")) {
                    ReinciaJogo();
                 } 

                 else if(pacote.equals("#")){
                     //entrar nesse if significa que o adversário configurou seu tabuleiro
                     String[][] matriz = new String[10][10];
                     String aux = "";
                     char caracter;
                     int posicao = 1;
                     for(int i =0; i<10;i++) {
                         for(int j =0; j<10;j++) {
                             do{
                                 caracter = msg.charAt(posicao++);
                                 aux = aux += caracter;
                             }while(caracter != ',');
                             matriz[i][j] = aux.substring(0,aux.length()-1);
                             aux = "";
                         }
                     }
                     painel.setMatrizInimigo(matriz);                 

                     if(painel.getJogadorPronto()){
                        JOptionPane.showMessageDialog(null,"VOCÊ COMEÇA JOGANDO", "AVISO", JOptionPane.WARNING_MESSAGE);
                        painel.setVez(true);
                        painel.getTabuleiroInimigo().setVez(true);
                     }

                 }
                 else if(pacote.equals("!")){
                     //desenhar a imagem jogada no tabuleiro do jogador
                     String aux = "";
                     char caracter;
                     int posicao = 1;
                     //recebe a coordenada x
                     do {
                         caracter = msg.charAt(posicao++);
                         aux += caracter;
                     } while(caracter != ',');
                     int x = Integer.parseInt(aux.substring(0,aux.length()-1));
                     //recebe a coordenada y
                     aux = "";
                     do {
                         caracter = msg.charAt(posicao++);
                         aux += caracter;
                     } while(caracter != ',');
                     int y = Integer.parseInt(aux.substring(0,aux.length()-1));
                     painel.configuraHit(x,y);                     
                     if(painel.getMeuTabuleiro().getNaviosDestruidos() == 15) painel.getPainelCentral().habilitaPatada();
                 }
                 //o adversário errou e o jogador recebe a vez
                 else if(pacote.equals("X")){
                    painel.setVez(true);
                    painel.getTabuleiroInimigo().setVez(true);
                 }
                 //o jogo acabou e o jogador perdeu
                 else if(pacote.equals("Z")) {
                    JOptionPane.showMessageDialog(null,"VOCÊ PERDEU, TENTE MELHORAR NA PRÓXIMA", "DERROTA", JOptionPane.WARNING_MESSAGE);
                    Som.playAudio(Som.DERROTA);
                 }
             }catch( ClassNotFoundException ce){
                painel.atualizaChat( "Tipo de objeto desconhecido \n" );
         }
     }while (!msg_fecha.trim().equals(painel.getNick() + " diz: SAIR")) ;//se digitar SAIR termina a conexao...
   
}

 private void FechaConexao() throws IOException{
     
        SwingUtilities.invokeLater(
         new Runnable()
         {        
            public void run()
            {
              
               painel.atualizaChat("Usuario terminou a conexao \n" );
            } 
         } 
      );
     
      if( painel.getConectado() ){
            saida.close(); //fecha a saida...
            entrada.close(); //entrada..
            conexao.close(); //e a conexao...
     }
     
  }
 
 private void ReinciaJogo(){
     
     painel.limpaPainel();
     painel.zeraPontos();
     painel.somaPontuacao();
     painel.setVez(false);
     painel.getTabuleiroInimigo().setVez(false);
     painel.getTabuleiroInimigo().limpaCursorPatada();
     painel.setJogadorPronto(false);
     painel.getPainelCentral().desabilitaPatada();
 }
}
