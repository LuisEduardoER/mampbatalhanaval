/*
 * DadosRede.java
 *
 * Criado em 22 de Agosto de 2007, 20:18
 *
 * O propósito desta classe é receber dados relativos à rede, e informá-los em duas JTextAreas.
 */

package batalha.interfacegrafica;

import javax.swing.*;

/**
 *
 * @author Usuario
 */
public class DadosRede extends JPanel{
    
    //TextAreas de exibição dos textos
    private JTextArea areaEntrada = null, areaSaida = null;
    //Labels apenas para mostrar o que cada textArea informa
    private JLabel lbDadosEnviados = null, lbDadosRecebidos = null;
            
    public DadosRede() {
     
        //Configuração de layout
        Box layoutBox1 = Box.createVerticalBox();
        Box layoutBox2 = Box.createVerticalBox();
        this.setBounds(16,13,610,90);
        this.areaEntrada = new JTextArea();
        this.areaSaida = new JTextArea();
        this.lbDadosEnviados = new JLabel("Dados Enviados");
        this.lbDadosRecebidos = new JLabel("Dados Recebidos");
        
        lbDadosRecebidos.setBounds(65,15,30,15);
        areaEntrada.setBounds(100,55,100,60);
        areaEntrada.setRows(5);
        areaEntrada.setColumns(15);
        areaEntrada.setEditable(false);
        
        lbDadosEnviados.setBounds(205,15,30,15);
        areaSaida.setBounds(240,55,100,60);
        areaSaida.setRows(5);
        areaSaida.setColumns(15);
        areaSaida.setEditable(false);
        
        layoutBox1.add(lbDadosRecebidos);
        layoutBox2.add(lbDadosEnviados);
        layoutBox1.add(areaEntrada);
        layoutBox2.add(areaSaida);
        layoutBox1.setBounds(40,13,300,90);
        layoutBox1.setBounds(340,13,300,90);
        this.add(layoutBox1);
        this.add(layoutBox2);
    }
    
    /**
     * Adiciona uma informação à área de entrada. Todo pacote que for recebido deve ser inserido nesta TextArea.
     */
    public void addDadoEntrada(String dado){
        
        areaEntrada.append(dado);
    }

    /**
     * Adiciona uma informação à área de saída. Todo pacote que for enviado deve ser inserido nesta TextArea.
     */    
    public void addDadoSaida(String dado){

        areaSaida.append(dado);
    }
    
}
