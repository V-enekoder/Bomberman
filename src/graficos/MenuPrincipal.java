package graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends Menu{

    private JLabel boton_estadisticas, boton_info;
    private JButton botonbatalla, botoninfo, botonestadisticas;

    public MenuPrincipal(){
        setSize(616, 639);
        setTitle("Bomberman");
        setLocationRelativeTo(null); // La locacion en el centro
        setResizable(false); // Se puede o no se puede cambiar el tamaño o no de la ventana
        setMinimumSize(new Dimension(200,200)); // Establezco tamaño minimo
        getContentPane().setBackground(Color.white);// Establecemos el color de la ventana.
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
    
    private void colocarEtiquetas(){

        background = new JLabel();
        background.setIcon(new ImageIcon("background.png"));
        background.setBounds(0, 0, 600, 600);
        panel.add(background);
        
        boton_info=new JLabel();
        boton_info.setIcon(new ImageIcon("boton_info.png"));
        boton_info.setBounds(528, 19, 52, 52);
        panel.add(boton_info);
        
        boton_estadisticas=new JLabel();
        boton_estadisticas.setIcon(new ImageIcon("boton_info.png"));
        boton_estadisticas.setBounds(528, 80, 52, 52);
        panel.add(boton_estadisticas);
        
    }
    
    private void colocarBotones(){
        
        botonbatalla = new JButton();
        botonbatalla.setBounds(38, 255, 178, 161);
        botonbatalla.setEnabled(true); // Me deja o no pinchar el boton
        botonbatalla.setIcon(new ImageIcon("boton_batalla.png"));
        panel.add(botonbatalla);
        botonbatalla.setVisible(true);
        
        botonestadisticas = new JButton();
        botonestadisticas.setBounds(528, 80, 52, 52);
        botonestadisticas.setEnabled(true); // Me deja o no pinchar el boton
        botonestadisticas.setIcon(new ImageIcon("boton_estadisticas.png"));
        panel.add(botonestadisticas);
        botonestadisticas.setVisible(true);
        
        botoninfo = new JButton();
        botoninfo.setBounds(528, 19, 52, 52);
        botoninfo.setEnabled(true); // Me deja o no pinchar el boton
        botoninfo.setIcon(new ImageIcon("boton_info.png"));
        panel.add(botoninfo);
        botoninfo.setVisible(true);
                
        // Iniciar menu batalla (llamo al servidor)
        botonbatalla.addActionListener(iniciarBatalla);
        botonestadisticas.addActionListener(verEstadisticas);
        botoninfo.addActionListener(verInfo);

    }

    ActionListener iniciarBatalla = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            botonbatalla.setVisible(false);
            botonestadisticas.setVisible(false);
            botoninfo.setVisible(false);
            background.setVisible(false);
            System.out.println("game.log_startLogueo");
            respuesta = 1;
            respuestaEstablecida = true;
            MenuLogueo();
        }
    };
    void MenuLogueo(){
        new MenuLogueo();
        this.dispose();
    }

    ActionListener verEstadisticas = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            botonbatalla.setVisible(false);
            botonestadisticas.setVisible(false);
            botoninfo.setVisible(false);
            background.setVisible(false);
            System.out.println("game.log_viewStats");
            MenuEstadisticas();
        }
    };
    
    void MenuEstadisticas(){
        new MenuEstadisticas();
        this.dispose();
    }

    ActionListener verInfo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            botonbatalla.setVisible(false);
            botonestadisticas.setVisible(false);
            botoninfo.setVisible(false);
            background.setVisible(false);
            System.out.println("game.log_viewInfo");
            menuInfo();
        }
    };

    void menuInfo(){
        new MenuInfo();
        this.dispose();
    }
}