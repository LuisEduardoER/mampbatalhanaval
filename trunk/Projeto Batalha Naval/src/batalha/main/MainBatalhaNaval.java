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
             
            Som.playAudio(Som.BEM_VINDO);
            BatalhaNavalWindow jogo = new BatalhaNavalWindow();
        } 
}
