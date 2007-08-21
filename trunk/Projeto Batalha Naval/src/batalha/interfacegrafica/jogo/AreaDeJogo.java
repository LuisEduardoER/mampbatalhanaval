/*
 * AreaDeJogo.java
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
public class AreaDeJogo extends JPanel{
    
    private TabuleiroJogador  tabuleiroJogador = null;

    
    public AreaDeJogo(AreaDeConfiguracaoDeNavio area) {
        
        this.setLayout(new BorderLayout());
        
        tabuleiroJogador = new TabuleiroJogador(area);
        
        this.add(tabuleiroJogador);
        
    }
    
    protected void paintComponent(Graphics g){
        
        tabuleiroJogador.repaint();
    }
}
