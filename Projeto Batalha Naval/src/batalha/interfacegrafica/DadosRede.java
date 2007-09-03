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
 * @author Renato, Paulo, Alexandre, Mois�s e Marcelo
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
        
        JScrollPane paneEntrada = new JScrollPane(areaEntrada);
        JScrollPane paneSaida = new JScrollPane(areaSaida);
        
        areaEntrada.setColumns(20);
        areaEntrada.setLineWrap(true);
        areaSaida.setColumns(20);
        areaSaida.setLineWrap(true);
        //Instancia dois visualizadores para as textAreas
        paneEntrada.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        paneEntrada.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        areaEntrada.setEditable(true);
        
        paneSaida.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        paneSaida.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        areaSaida.setEditable(true);
        
        paneEntrada.setPreferredSize(new Dimension(250,70));
        paneSaida.setPreferredSize(new Dimension(250,70));
        this.add(lbDadosEnviados);
        this.add(lbDadosRecebidos);
        this.add(paneEntrada);
        this.add(paneSaida);
        
        //Configura as dimens�es corretas
        lbDadosEnviados.setPreferredSize(new Dimension(100,20));
        lbDadosRecebidos.setPreferredSize(new Dimension(100,20));
        
        //Configura o layout, adicionando espa�amento entre os componentes
        layout.putConstraint(SpringLayout.WEST, lbDadosEnviados, 100, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, paneEntrada, 50, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, paneEntrada, 5, SpringLayout.SOUTH, lbDadosEnviados);
        layout.putConstraint(SpringLayout.WEST, lbDadosRecebidos, 370, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, paneSaida, 320, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, paneSaida, 5, SpringLayout.SOUTH, lbDadosRecebidos);

    }
    
    /**
     * Adiciona uma informa��o � �rea de entrada. Todo pacote que for recebido deve ser inserido nesta TextArea.
     */
    public void addDadoEnviado(final String dado){
        
        SwingUtilities.invokeLater(
            new Runnable(){
                
                public void run(){        
                    System.out.println("Dados rede" + dado);
                    areaEntrada.append(dado); //coloca os dados de entrada na JtextArea
                    areaEntrada.setCaretPosition(areaEntrada.getText().length());
                }
            }
        );
    }

    /**
     * Adiciona uma informa��o � �rea de sa�da. Todo pacote que for enviado deve ser inserido nesta TextArea.
     */    
    public void addDadoRecebido(final String dado){

        SwingUtilities.invokeLater(
            new Runnable(){
                
                public void run(){
                    
                    areaSaida.append(dado);
                    areaSaida.setCaretPosition(areaSaida.getText().length());
                }
            }
        );
    }
    
}
