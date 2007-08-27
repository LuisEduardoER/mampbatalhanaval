/*
 * ObjetosRede.java
 *
 * Criado em 25 de Agosto de 2007, 15:59
 *
 * Especifica os tipos de pacotes que irão transitar na rede
 */

package batalha.rede;

import java.io.*;

/**
 * @author Renato
 */
public class ObjetosRede implements Serializable{
    
    //Constante que indica um objeto de mensagem
    private static final int CHAT_PACK = 1,
    //Constante que indica um objeto do tipo matriz
                             MATRIZ_LOGICA_PACK = 2,
    //Constante que indica um objeto do tipo imagem e coordenada
                             HIT_PACK = 3;
    
    //Indica o tipo do pacote
    private int tipo = 0;
    //Conteúdo do pacote
    private Object[] pacote = null;
    
    /**
     * Construtor sobrecarregado para pacotes do tipo de chat
     */
    public ObjetosRede(String mensagem) {
        
        tipo = CHAT_PACK;
        pacote = new Object[1];
        pacote[0] = mensagem;
    }
    
    /**
     * Construtor sobrecarregado para a matriz lógica
     */
    public ObjetosRede(String[][] matrizLogica){
        
        tipo = MATRIZ_LOGICA_PACK;
        pacote = new Object[matrizLogica.length * matrizLogica.length];
        for(int i = 0; i < matrizLogica.length * matrizLogica.length; i++)
            pacote[i] = matrizLogica[i/matrizLogica.length][i%matrizLogica.length];
    }
    
    /**
     * Construtor sobrecarregado para a imagem e coordenada
     */
    public ObjetosRede(String nomeImagem, int x, int y){
        
        tipo = HIT_PACK;
        pacote = new Object[3];
        pacote[0] = nomeImagem; pacote[1] = new Integer(x); pacote[2] = new Integer(y);
    }
    
    /**
     * Retorna o tipo do pacote
     */
    public int getTipo(){
        
        return this.tipo;
    }
   
    /**
     * Retorna o conteúdo do pacote
     */
    public Object[] getPacote(){
        
        return this.pacote;
    }
}
