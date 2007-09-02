/*
 * MainBatalhaNaval.java
 *
 * Criado em 19 de Agosto de 2007, 14:27
 *
 */

package batalha.main;

import batalha.interfacegrafica.*;
import batalha.interfacegrafica.jogo.Som;
/*
 *
 * @author Usuario
 */
public class MainBatalhaNaval {


        public static void main(String args[]){
                     
            Som.play(Som.ACERTO);

            BatalhaNavalWindow jogo = new BatalhaNavalWindow();
        } 
}
