/*
 * AreaDeConfiguracaoDeNavio.java
 *
 * Criado em 12 de Agosto de 2007, 22:42
 *
 */

package batalha.interfacegrafica.jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Usuario
 */
public class AreaDeConfiguracaoDeNavio extends JPanel{
    
    private JPanel[] imagensDosNavios = null;
    private String[] nomeDosNavios = null;
    private boolean configuracaoFoiOk = false;
    
    public AreaDeConfiguracaoDeNavio() {
    }
 
    protected Image getImagemUltimoNavio(){
        return null;
    }
    
    protected String getNomeUltimoNavio(){
        return null;
    }
    
    protected int getPosicaoUltimoNavio(){
        return -1;
    }
    
    protected int getLarguraUltimoNavio(){
        return -1;
    }
    
    protected int getAlturaUltimoNavio(){
        return -1;
    }
    
    protected void setConfiguracaoOk(){
        configuracaoFoiOk = true;
    }
}
