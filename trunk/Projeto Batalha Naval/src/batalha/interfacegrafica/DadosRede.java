/*
 * DadosRede.java
 *
 * Criado em 22 de Agosto de 2007, 20:18
 *
 */

package batalha.interfacegrafica;

//import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author Usuario
 */
public class DadosRede extends JPanel{
    
    private JTextArea areaEntrada = null, areaSaida = null;
    private JLabel lbDadosEnviados = null, lbDadosRecebidos = null;
            
    public DadosRede() {
     
        Box layoutBox1 = Box.createVerticalBox();
        Box layoutBox2 = Box.createVerticalBox();
        this.setBounds(16,13,610,90);
        //this.setSize(600,70);
        //this.setLayout(new GridLayout(2,2));
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
    
    public void addDadoEntrada(String dado){
        
        areaEntrada.append(dado);
    }
    
    public void addDadoSaida(String dado){

        areaSaida.append(dado);
    }
    
    /*public void setLabelConexao(String protocolo){
        
        statusConexao.setText(String.format("Velocidade de conexão: %dKbps\nProtocolo utilizado: %s.",0,protocolo));
    }*/
    
    /*public void atualizaLabel(String velocidade){
        
        statusConexao.setText(String.format("Velocidade de conexão: %sKbps\nProtocolo utilizado: %s.",velocidade,"-"));
    }*/
}
