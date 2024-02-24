package graficos;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEstadisticas extends Menu{

    public MenuEstadisticas(){
        panel = new JPanel();
        panel.removeAll();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        // Configura la ventana del juego
        setSize(616, 639);
        setTitle("Bomberman: Menu de Logueo (Registro - Login)");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        iniciarComponentes();
        setVisible(true);
    }
    @Override
    protected void iniciarComponentes(){
        colocarEtiquetas();
        colocarBotones();
    }

    void colocarEtiquetas(){
        background=new JLabel();
        background.setIcon(new ImageIcon("menuStats.png"));
        background.setBounds(0, 0, 600, 600);
        background.setOpaque(false);
        panel.add(background);
    }

    void colocarBotones(){

    }
}
