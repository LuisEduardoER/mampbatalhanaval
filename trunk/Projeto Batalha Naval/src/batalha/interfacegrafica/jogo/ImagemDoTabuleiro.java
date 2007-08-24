/**
* ImagemDoTabuleiro.java
*
* Criado em 12 de Agosto de 2007, 22:18
* 
* O propósito desta classe é armazenar cada imagem que representa um navio e sua posição no tabuleiro
*/

package batalha.interfacegrafica.jogo;

import java.awt.Image;
import java.awt.Point;

/**
 * @author Renato
 *
 * @date 24/08/2007
 * @version 0.1
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