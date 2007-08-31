/*
 * SomManager.java
 *
 * Criado em 31 de Agosto de 2007, 17:50
 *
 * Esta classe implementa um gerenciador de som do jogo
 */

package batalha.interfacegrafica.jogo;

import java.io.*;
import java.applet.*;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class SomManager {
   
    public static final int ACERTO = 0, //Constante para o som de acerto,
                            ERRO = 1,   //erro,
                            VITORIA = 2, //vitória,
                            DERROTA = 3, //derrota e
                            BEM_VINDO = 4; //bem-vindo
    
    private static AudioClip acerto,   //Som de acerto do navio
                               erro,   //Som de erro do navio
                            vitoria,   //Som de vitoria para um jogador
                            derrota,   //Som de derrota para um jogador
                            bem_vindo; //Som de bem vindo ao jogo 
    
    public SomManager() {
   
        try{
            
            acerto = Applet.newAudioClip(new File("acerto.wav").toURI().toURL());
            erro = Applet.newAudioClip(new File("erro.wav").toURI().toURL());
            vitoria = Applet.newAudioClip(new File("vitoria.wav").toURI().toURL());
            derrota = Applet.newAudioClip(new File("derrota.wav").toURI().toURL());
            bem_vindo = Applet.newAudioClip(new File("welcome.wav").toURI().toURL());
            
        }catch(MalformedURLException excp){
            
            excp.printStackTrace();
        }
    }

    public void playAudio(int som){
    
        switch(som){
            
            case ACERTO: acerto.play(); break;
            case ERRO: erro.play(); break;
            case VITORIA: vitoria.play(); break;
            case DERROTA: derrota.play(); break;
            case BEM_VINDO: bem_vindo.play(); break;
            default: JOptionPane.showMessageDialog(null,"Som requisitado não existe");
        }
    }
}
