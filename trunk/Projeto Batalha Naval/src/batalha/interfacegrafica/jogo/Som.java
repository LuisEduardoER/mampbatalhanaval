/*
 * Som.java
 *
 * Criado em 31 de Agosto de 2007, 18:51
 *
 * Esta classe gerencia os sons do jogo
 */

package batalha.interfacegrafica.jogo;

import java.io.*;
import java.applet.*;
import java.net.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Renato
 */
public class Som {
    
    
    //Constantes para verificar de qual som se trata
    public static final int ACERTO = 0,
            ERRO = 1,
            VITORIA = 2,
            DERROTA = 3,
            WELCOME = 4,
            PATADA = 5;
    
    //Clipes de Áudio para tocar cada som
    private static AudioClip acerto, erro, vitoria, derrota, welcome, patada;
    
    //Trecho estático de inicialização dos audio clipes
    static{
        
        try{
            
            acerto = Applet.newAudioClip(new File("acerto.wav").toURI().toURL());
            erro = Applet.newAudioClip(new File("erro.wav").toURI().toURL());
            vitoria = Applet.newAudioClip(new File("vitoria.wav").toURI().toURL());
            derrota = Applet.newAudioClip(new File("derrota.wav").toURI().toURL());
            welcome = Applet.newAudioClip(new File("welcome.wav").toURI().toURL());
            patada = Applet.newAudioClip(new File("patada.wav").toURI().toURL());
            
        }catch(MalformedURLException excp){
            
            excp.printStackTrace();
        }
    }
    
    //Método estático para tocar um som
    public static void play(int som){
        
        switch(som){
            
            case ACERTO: acerto.play();  break;
            case ERRO: erro.play();  break;
            case VITORIA: vitoria.play();  break;
            case DERROTA: derrota.play();  break;
            case WELCOME: welcome.play();  break;
            case PATADA: patada.play();  break;
            default: JOptionPane.showMessageDialog(null,"Som não encontrado");
        }
    }
    
}

