package graficos;

import javax.swing.*;

import Juego.Packet.Packet02Derrota;
import Server.UDP.Servidor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import java.util.LinkedList;

public class MenuWin extends Menu{
    
    private JLabel nameP1, nameP2;
    private JButton retornar;
    private String ganador,perdedor;

    public MenuWin(String ganador, String perdedor){
        setSize(616, 639);
        setTitle("Bomberman: Menu de Ganadores y Perdedores");
        setLocationRelativeTo(null); // La locacion en el centro
        setResizable(false); // Se puede o no se puede cambiar el tamaÃ±o o no de la ventana
        setMinimumSize(new Dimension(200,200)); // Establezco tamaÃ±o minimo
        getContentPane().setBackground(Color.white);// Establecemos el color de la ventana.
        this.ganador = ganador;
        this.perdedor = perdedor;
        iniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        respuesta = 0;
        respuestaEstablecida = false;
    }

    @Override
    protected void iniciarComponentes(){
        colocarPanel();
        colocarEtiquetas();
        colocarBotones();
    }

    private void colocarPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }

    void colocarEtiquetas(){

        background = new JLabel();
        background.setIcon(new ImageIcon("menuServidorWin.png"));
        background.setBounds(0, 0, 600, 600);
        panel.add(background);

        nameP1 = new JLabel(ganador);
        nameP1.setBounds(90,140,500,18);
        nameP1.setFont(new Font("Arial",1,18));
        nameP1.setOpaque(false);
        nameP1.setForeground(Color.WHITE);
        panel.add(nameP1);
        panel.setComponentZOrder(nameP1, 0);

        nameP2 = new JLabel(perdedor);
        nameP2.setBounds(90,245,500,18);
        nameP2.setFont(new Font("Arial",1,18));
        nameP2.setOpaque(false);
        nameP2.setForeground(Color.WHITE);
        panel.add(nameP2);
        panel.setComponentZOrder(nameP2, 0);

    }

    void colocarBotones(){

        retornar = new JButton();
        retornar.setBounds(195, 558, 209, 34);
        retornar.setEnabled(true); // Me deja o no pinchar el boton
        retornar.setIcon(new ImageIcon("botonMenuBatalla.png"));
        panel.add(retornar);
        retornar.setVisible(true);

        retornar.addActionListener(backToBattle);
    }

    ActionListener backToBattle = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            background.setVisible(false);
            MenuPrincipal();
        }
    };

    void MenuPrincipal(){
        new MenuPrincipal();
        this.dispose();
    }
}
