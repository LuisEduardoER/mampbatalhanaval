/*
 * Som.java
 *
 * Criado em 3 de setembro de 2007, 10:08
 *
 * O propósito desta classe é manipular o som do jogo
 */

package batalha.interfacegrafica.jogo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.net.MalformedURLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Renato, Paulo, Alexandre, Moisés e Marcelo
 */

public class Som{
   
    public static final int ACERTO = 0, //Constante para o som de acerto,
                            ERRO = 1,   //erro,
                            VITORIA = 2, //vitória,
                            DERROTA = 3, //derrota e
                            BEM_VINDO = 4,//bem-vindo
                            PATADA = 5, //patada
                            SOM_CONFIG = 6;
    private static AudioClip acerto,   //Som de acerto do navio
                               erro,   //Som de erro do navio
                            vitoria,   //Som de vitoria para um jogador
                            derrota,   //Som de derrota para um jogador
                            bem_vindo, //Som de bem vindo ao jogo 
                            pre_game,  //Som de configuração do tabuleiro
                               patada; //Som da patada
                                
    //Trecho estático que inicializa os audioclipes
    static{
   
        try{
            acerto = Applet.newAudioClip(new URL("file:src/som/acerto.au"));
            erro = Applet.newAudioClip(new URL("file:src/som/erro.au"));
            vitoria = Applet.newAudioClip(new URL("file:src/som/vitoria.au"));
            derrota = Applet.newAudioClip(new URL("file:src/som/derrota.au"));
            bem_vindo = Applet.newAudioClip(new URL("file:src/som/bem_vindo.au"));
            patada = Applet.newAudioClip(new URL("file:src/som/patada.au"));
            pre_game = Applet.newAudioClip(new URL("file:src/som/pre_game.au"));
        }catch(MalformedURLException excp){
            
            excp.printStackTrace();
        }

    }
    
    /**
     * Método estático para tocar cada áudio clipe
     */
    public static void playAudio(int som){
    
        switch(som){
            
            case ACERTO: acerto.play(); break;
            case ERRO: erro.play(); break;
            case VITORIA: vitoria.play(); break;
            case DERROTA: derrota.play(); break;
            case BEM_VINDO: bem_vindo.play(); break;
            case PATADA: patada.play(); break;
            case SOM_CONFIG: pre_game.play(); break;
            //Nunca deve chegar aqui
            default: JOptionPane.showMessageDialog(null,"Som requisitado não existe");
        }
    }
    
    /**
     * Método estático para pausar um som
     */
    public static void stopAudio(int som){

        switch(som){
            
            case ACERTO: acerto.stop(); break;
            case ERRO: erro.stop(); break;
            case VITORIA: vitoria.stop(); break;
            case DERROTA: derrota.stop(); break;
            case BEM_VINDO: bem_vindo.stop(); break;
            case PATADA: patada.stop(); break;
            case SOM_CONFIG: pre_game.stop(); break;
            //Nunca deve chegar aqui
            default: JOptionPane.showMessageDialog(null,"Som requisitado não existe");
        }        
    }
}


