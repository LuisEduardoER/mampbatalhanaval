/*
 * Splash.java
 *
 * Created on 3 de Setembro de 2007, 20:44
 *
 * Esta classe criara o Splash de inicializacao do Jogo
 * 
 */

package batalha.interfacegrafica;

/**
 * @author Renato, Paulo, Alexandre, Moisés e Marcelo
 */

 import java.awt.event.*;
 import java.awt.*;
 import javax.swing.*;

 //Classe que incializa o sistema com um Splash...
 public class Splash
 {

    JProgressBar progresso;
    Thread thread;

    public static JFrame frame;
    private static Splash splash;
 
   //Singleton pra garantir apenas uma unica instancia na memoria
   static {
		splash = new Splash();
   }
    
    public Splash()
    {
    }
    
    public void criaSplash(String imgCaminho, String message){
        frame = new JFrame("MAMP&R Batalha Naval");
          
        JPanel panel = new JPanel();
        panel.setBackground( new Color(255,255,255) );//colore o fundo do painel...
        panel.setSize(450,250); //tamano do painel..
        panel.setBounds(0,0,450,250); //posicao...
 
        JLabel texto = new JLabel( message );//Messagem q aparecera na inicializacao...
        texto.setForeground(Color.GRAY); //tera a cor do Texto de Laranja...                
 
        JLabel img = new JLabel();
        img.setIcon( new ImageIcon( imgCaminho ) );
 
        progresso = new JProgressBar(0, 100); //O ProgressBar comecar com 0 ate 100

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        progresso.setStringPainted( true ); //visivel a porcentagem que esta a barra...
        progresso.setBackground(Color.CYAN); //a cor do fundo da barra cinza
        progresso.setForeground(Color.ORANGE); //a cor que ira sobrepondo laranja

        //ajunta os elementos no painel...
        panel.add("North",img);
        panel.add("East",texto);
        panel.add("East",progresso);
      
        
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(450,250);//tamanho do frame..
 
        //ponhe o Splash no centro da tela...
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screen.width - frame.getSize().width)/2,(screen.height - frame.getSize().height)/2);
        
        frame.setUndecorated( true );
        frame.setVisible( true ); //visivel sim...
        frame.setResizable( false );//naum pode altera o tamanho...
 
        //a barrinha tera a seguintes medidas...
        //10px da lateral esquerda, 220 px do topo do frame, 430 px o tamanho da barra,
        //e 13 px de largura...
        progresso.setBounds(new Rectangle(10,220,430,13));
        
        // Criacao da thread para a barra "andar"...
        thread = new Thread( new Progresso() );
        thread.start(); //inicia a thread..               
    
    }
    
    
    //esta é classe responsavel por fazer o JProgressBar funcionar...    
    public class Progresso implements Runnable
        {
           public void run()
           {
                for (int i = 1; i < 100; i++)
                {
                    progresso.setValue( i );
                    progresso.setString( i + " %" );
                    try
                    {
                        thread.sleep( 80 );//determina a velocidade que andara a barra...
                    }
                    catch(Exception e)
                    {
                       e.printStackTrace();
                        frame.dispose();//se der erro destroe o frame...
                    }
                }

              frame.dispose(); //terminado o tempo especificado na Thread...destroe o frame...

            }
        }
     
   } 
