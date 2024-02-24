package graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBatalla extends Menu{

    public MenuBatalla(){
        panel = new JPanel();
        panel.removeAll();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        // Configura la ventana del juego
        setSize(616, 639);
        setTitle("Bomberman: Menu de Juego (Modo Batalla)");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        iniciarComponentes();
        setVisible(true);
    }
    @Override
    protected void iniciarComponentes(){
        iniciarEtiquetas();
        iniciarBotones();
    }
    
    void iniciarEtiquetas(){

        background = new JLabel();
        background.setIcon(new ImageIcon("menuServidor.png"));
        background.setBounds(0, 0, 600, 600);
        panel.add(background);

    }
    
    void iniciarBotones(){
        
    }
    
}