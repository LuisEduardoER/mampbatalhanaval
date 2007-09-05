/*
 * MainBatalhaNaval.java
 *
 * Criado em 19 de Agosto de 2007, 14:27
 *
 * 
 * Inicializa o jogo
 */

package batalha.main;

import batalha.interfacegrafica.*;
import batalha.interfacegrafica.jogo.Som;

/**
 *
 * @author Renato, Paulo, Alexandre, Moisés e Marcelo
 */
public class MainBatalhaNaval {


        public static void main(String args[]){
             
            // Cria um objeto do Tipo Splash
            Splash splash = new Splash();
                
            //cria Splash...e depois de 9 segundos inicia o simulador...
            splash.criaSplash("src/imagens/batalha.jpg","Inicializando MAMP&R Batalha Naval . . .");                                        
            
            //invoca a janela de definicoes de Nick, se sera cliente ou servidor juntamente
            //com o Ip para  conexao 
            BatalhaNavalWindow jogo = new BatalhaNavalWindow();
        } 
}
