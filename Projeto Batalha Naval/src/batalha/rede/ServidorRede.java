/*
 * ServidorRede.java
 *
 * Created on 27 de Agosto de 2007, 22:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package batalha.rede;
import batalha.interfacegrafica.PainelDoJogo;


import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Administrador
 */
public class ServidorRede extends Thread {
    
    private PainelDoJogo painel;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private ServerSocket servidor;
    private Socket conexao;
    
    /** Creates a new instance of ServidorRede */
    public ServidorRede(PainelDoJogo p) {

        this.painel = p;

    }
           
 public void run(){

         painel.getTxfMensagem().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    EnviaDado( ae.getActionCommand() );
                }
            }
    );

     if(this.painel.getServidor()) {
            RodaServidor();
        }
        else {
            RodaCliente();
        }
 }
 
 private void EnviaDado( String msg ){ // funcao que envia a msg...
     try{
                 
         painel.atualizaChat(painel.getNick()+ " diz: " + msg + "\n" ); //escreve na propria tela
         saida.writeObject( painel.getNick() + " diz: " + msg);//escreve no buffer..
         saida.flush(); //limpar o buffer
         
      }catch( IOException io){//caso de erro...
         painel.atualizaChat("\nErro na escrita do objeto.");
         painel.atualizaChat("\nConexao de rede perdida.");
         painel.atualizaChat("\n\nDESDE JA OBRIGADO! EQUIPE MAMP&R ");
     }
 }
 
    public void RodaServidor() //configura e executa o servidor...
   {
       
    try{
        //cria um serverSocket na porta 5000 e o tamanho da fila 100, ou seja, maximo de clientes que podem se conectar no sevidor 
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
     //mostra msg de recebimento...
     painel.atualizaChat( "Conexao recebida de: " +
             conexao.getInetAddress().getHostName() + "\n");
  }  

    //se conectando no servidor...
   private void ConectanoServidor() throws IOException{
     // displayArea.setText("\nEsperando por conexão\n" );//quando inciado o cliente mostra...essa msg
      
      conexao = new Socket( InetAddress.getByName( painel.getIp()), 5000);//liga na porta 5000
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
     
    // displayArea.setEditable( false );//para naum apagar o que foi escrito
     
     String msg = "CONEXÃO COMPLETADA\n";
     
     painel.atualizaChat("__________Bem vindo ao MAMP Messenger__________\n" );
     
     String msg_fecha = "";
     
     saida.writeObject( msg ); //escreve no display do cliente quando feita a conexao..
     
     saida.flush(); //limpa o buffer de saida...
     
     //enterField.setEnabled( true );//e habilita pra digitar e enviar msgs...
     
     do{
         try{
             msg = ( String ) entrada.readObject();
             
             painel.atualizaChat(msg + "\n");
             
             //displayArea.setCaretPosition( displayArea.getText().length() );
             
            // displayArea.setEditable( false );//para naum apagar o que foi escrito
             
         }catch( ClassNotFoundException ce){
            painel.atualizaChat( "Tipo de objeto desconhecido \n" );
     }
 }while (!msg_fecha.trim().equals(painel.getNick() + " diz: SAIR")) ;//se digitar SAIR termina a conexao...
   
}

 private void FechaConexao() throws IOException{
     painel.atualizaChat("Usuario terminou a conexao \n" );
 //    enterField.setEnabled( false ); //desabilita o envio de msgs
     saida.close(); //fecha a saida...
     entrada.close(); //entrada..
     conexao.close(); //e a conexao...
  }
 
}
