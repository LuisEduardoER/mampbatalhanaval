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
    
        painel.atualizaChat("\nPaulo diz: oi");
        painel.atualizaChat("\nPaulo diz: td bem?");
        painel.atualizaChat("\nPaulo diz: gostaria de te conhecer melhor...");
        painel.atualizaChat("\nPaulo diz: oi");
        painel.atualizaChat("\nPaulo diz: td bem?");
        painel.atualizaChat("\nPaulo diz: gostaria de te conhecer melhor...");
        
    }
}
