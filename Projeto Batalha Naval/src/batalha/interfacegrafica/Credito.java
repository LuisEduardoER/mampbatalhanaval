/*
 * Credito.java
 *
 * Created on 5 de Setembro de 2007, 14:08
 *
 * Esta classe tera informacoes sobre os criadores do jogo 
 * 
 */

package batalha.interfacegrafica;

/**
 *
 * @author Renato, Paulo, Alexandre, Moisés e Marcelo
 *
 */


import javax.swing.*;
import java.awt.*;


public class Credito extends JFrame
{
     private JTabbedPane abas;

    public Credito ()
    {
           super("Créditos");
           
           final ImageIcon image = new ImageIcon("src/imagens/gato.gif"); //poe um novo simbolo no lugar do simbolo do java
           setIconImage(image.getImage()); //no frame
       
           abas = new JTabbedPane(); //instancia um objeto do tipo JTabbedPane
 
           JPanel jp = new JPanel(new GridLayout()); // seguinte aqui é a panel que vai ficar o conteudo                                           
           ImageIcon img = new ImageIcon("src/imagens/batalhafundo.jpg");
           JLabel label = new JLabel( img );
           jp.add(label, BorderLayout.CENTER); //coloca imagem no centro...
        
           //aqui eu adiciono a panel a JTabbedPane() a primeira aba (About)
           abas.setBackground(Color.RED); //o fundo da aba nao selecionada VERMELHA
           abas.addTab("About", jp ); //o nome da aba sera (About)

           //adicionando a abas no frame principal
           getContentPane().add(abas);
     
     /******************************************************************************************/      
           //instanciando novamente para fazer referencia apenas ao conteudo da nova aba
           jp = new JPanel(new GridLayout());
            //aqui eu adiciono a panel a JTabbedPane() a primeira aba (About)
           abas.setBackground(Color.RED); //o fundo da aba nao selecionada VERMELHA
           
            JOptionPane novo = new JOptionPane();
                    novo.showMessageDialog(null,"MAMP&R - Batalha Naval Versão 1.0\n" 
                    +"Edição de 32 bits\n"  
                    +"Copyright 2005-2007 MAMP&R\n"  
                    +"Todos os direitos reservados\n\n"                                                              
                    +"     © 2007 MAMP&R ","Versão",
                      JOptionPane.WARNING_MESSAGE,image);  
                    
           
           jp.add( novo );                     

    /************************************************************************************************/           
           //instanciando novamente para fazer referencia apenas ao conteudo da nova aba
           jp = new JPanel(new GridLayout());                          
        
            JTextArea textArea;    //cria uma variavel do tipo JTextArea           
        
            textArea = new JTextArea(15, 20){
            ImageIcon imagem = new ImageIcon("src/imagens/credito.jpg");    
            Image im = imagem.getImage(); //invoca uma figura para ficar como marca da agua
        
       
                    public void paintComponent ( Graphics g ) 
                    { 
                        super.paintComponent ( g ); 
                        int x = ( this.getWidth ( ) - im.getWidth ( null ) ) / 2; 
                        int y = ( this.getHeight ( ) - im.getHeight ( null ) ) / 2; 
                        g.drawImage ( im , x , y , this ); 
                    } 
            
            };
            
            textArea.setLineWrap( true ); // define a quebra de linha automática
            textArea.setWrapStyleWord( true );
            textArea.setEditable( false ); //somente leitura       
            textArea.setForeground( Color.RED ); //cor do texto VERMELHA...
            JScrollPane scrollPane = new JScrollPane( textArea );
        
            // Define a fonte a ser usada
            Font fonte = new Font("Verdana", Font.BOLD, 13);
            textArea.setFont(fonte);
        
            jp.add(textArea); //add no Painel

             //cria uma aba chamada (CREDITO)                 
             abas.addTab("Credito",jp);
            
             this.getContentPane().add(abas);  //add a ABA                                  
             this.setVisible( true ); //VISIVEL sim..
             this.setSize(410,400); //janela 400x 400  
              //ponhe no centro da tela...
             Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
             this.setLocation((screen.width - this.getSize().width)/2,(screen.height - this.getSize().height)/2);
             
             this.setResizable( false ); //nao pode alterar o tamanho pre definido

   }
}