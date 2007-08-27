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

    //Imagem armazenada
    private Image imagem = null;
    //Ponto onde se inicia a imagem
    private Point pontoInicial = null;

    /**
     * Construtor da classe ImagemDoTabuleiro
     *
     * @param imagem - imagem a ser armazenada
     *        pontoInicial - ponto onde se inicia a imagem
     */
    public ImagemDoTabuleiro(Image imagem, Point pontoInicial){

        this.imagem = imagem; 
        this.pontoInicial = pontoInicial; 

    }
    
    /**
     * Retorna a imagem contida neste objeto
     * @return imagem - imagem armazenada no objeto
     */
    public Image getImagem(){
        
        return this.imagem;
    }
    
    /**
     * Retorna o ponto onde se inicia a imagem
     * @return pontoInicial - ponto onde se inicia a imagem
     */
    public Point getPontoInicial(){
        
        return this.pontoInicial;
    }

}