/*
 * DadosRede.java
 *
 * Criado em 22 de Agosto de 2007, 20:18
 *
 * O prop�sito desta classe � receber dados relativos � rede, e inform�-los em duas JTextAreas.
 */

package batalha.interfacegrafica;

import java.awt.Dimension;
import javax.swing.*;

/**
 *
 * @author Usuario
 */
public class DadosRede extends JPanel{
    
    //TextAreas de exibi��o dos textos
    private JTextArea areaEntrada = null, areaSaida = null;
    //Labels apenas para mostrar o que cada textArea informa
    private JLabel lbDadosEnviados = null, lbDadosRecebidos = null;
            
    public DadosRede() {
        
        //Inst�ncia um gerenciador de layout do tipo SpringLayout, e configura o tamanho do painel
        //Para quem quiser ver como funciona o SpringLayout, http://java.sun.com/docs/books/tutorial/uiswing/layout/spring.html
        //�lias, d�em todos uma boa lida nos tutoriais da Sun, resolvem muitas dores de cabe�a hehe ;-)
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(620,119));
        
        this.areaEntrada = new JTextArea();
        this.areaSaida = new JTextArea();
        this.lbDadosEnviados = new JLabel("Dados Enviados");
        this.lbDadosRecebidos = new JLabel("Dados Recebidos");
        
        JScrollPane paneEntrada = new JScrollPane();
        JScrollPane paneSaida = new JScrollPane();
        
        //Instancia dois visualizadores para as textAreas
        paneEntrada.setViewportView(areaEntrada);
        paneEntrada.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        paneEntrada.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        paneSaida.setViewportView(areaSaida);
        paneSaida.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        paneSaida.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.add(lbDadosEnviados);
        this.add(lbDadosRecebidos);
        this.add(paneEntrada);
        this.add(paneSaida);
        
        //Configura as dimens�es corretas
        lbDadosEnviados.setPreferredSize(new Dimension(100,20));
        lbDadosRecebidos.setPreferredSize(new Dimension(100,20));
        areaEntrada.setPreferredSize(new Dimension(150,60));
        areaSaida.setPreferredSize(new Dimension(150,60));
        
        //Configura o layout, adicionando espa�amento entre os componentes
        layout.putConstraint(SpringLayout.WEST, lbDadosEnviados, 130, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, paneEntrada, 130, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, paneEntrada, 5, SpringLayout.SOUTH, lbDadosEnviados);
        layout.putConstraint(SpringLayout.WEST, lbDadosRecebidos, 390, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, paneSaida, 390, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, paneSaida, 5, SpringLayout.SOUTH, lbDadosRecebidos);

    }
    
    /**
     * Adiciona uma informa��o � �rea de entrada. Todo pacote que for recebido deve ser inserido nesta TextArea.
     */
    public void addDadoEntrada(String dado){
        
        areaEntrada.append(dado);
    }

    /**
     * Adiciona uma informa��o � �rea de sa�da. Todo pacote que for enviado deve ser inserido nesta TextArea.
     */    
    public void addDadoSaida(String dado){

        areaSaida.append(dado);
    }
    
}
