/*
 * ImagemDoTabuleiro.java
 *
 * Criado em 22 de Agosto de 2007, 18:25
 *
 */

package batalha.interfacegrafica.jogo;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author Usuario
 */
public class ImagemDoTabuleiro{

    private Image imagem = null;
    private Point pontoInicial = null;


    public ImagemDoTabuleiro(Image imagem, Point pontoInicial){

        this.imagem = imagem; 
        this.pontoInicial = pontoInicial; 

    }
    
    public Image getImagem(){
        
        return this.imagem;
    }
    
    public Point getPontoInicial(){
        
        return this.pontoInicial;
    }

}