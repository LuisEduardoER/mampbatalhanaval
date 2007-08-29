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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Administrador
 */
public class ServidorRede {
    
    private PainelDoJogo painel;
    
    /** Creates a new instance of ServidorRede */
    public ServidorRede(PainelDoJogo p) {

        this.painel = p;
    }
    
    public void Jogar(){
    
        painel.getTxfMensagem().addActionListener(
  
                new ActionListener(){
                    
                    public void actionPerformed(ActionEvent ae){
                        
                        painel.atualizaChat("\n" + painel.getNick()+ " diz: " + ae.getActionCommand());
                    }
                }
        );

        
    }
}
