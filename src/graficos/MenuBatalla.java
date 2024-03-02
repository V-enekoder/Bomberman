package graficos;

import javax.swing.*;

import Juego.Bomberman;
import Juego.Personaje.Jugador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MenuBatalla extends Menu {

    private JButton confirmar, cancelar;
    private JButton[] botones = new JButton[5];
    private JLabel jugador, eleccion;

    public MenuBatalla() {
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
    protected void iniciarComponentes() {
        iniciarEtiquetas();
        iniciarBotones();
    }

    void iniciarEtiquetas(){
        String color = "";

        switch (Jugador.getColor()) {
            case 0:
                color = "Blanco";
                break;
            case 1:
                color = "Negro";
                break;
            case 2:
                color = "Rojo";
                break;
            case 3:
                color = "Azul";
                break;
            case 4:
                color = "Verde";
                break;
        }

        background = new JLabel();
        background.setIcon(new ImageIcon("menuServidor.png"));
        background.setBounds(0, 0, 600, 600);
        panel.add(background);

        
        jugador = new JLabel(MenuLogueo.getTextoIngresado());
        jugador.setBounds(90, 125, 200, 16);
        jugador.setOpaque(false);
        jugador.setForeground(Color.WHITE);
        jugador.setFont(new Font("Arial",Font.BOLD,16));
        panel.add(jugador);
        panel.setComponentZOrder(jugador, 0);

        eleccion = new JLabel("Color: " + color);
        eleccion.setBounds(195, 275, 200, 20);
        eleccion.setOpaque(true);
        eleccion.setBackground(Color.BLACK);
        eleccion.setForeground(Color.WHITE);
        eleccion.setFont(new Font("Arial",Font.PLAIN,16));
        eleccion.setHorizontalAlignment(SwingConstants.CENTER); // centrar texto
        panel.add(eleccion);
        panel.setComponentZOrder(eleccion, 0);

    }

    void iniciarBotones(){
        Map<Integer, BotonConfiguracion> botonesConfiguracion = obtenerConfiguracionBotones();
        
        for(int i = 0; i < 5; i++){
            BotonConfiguracion config = botonesConfiguracion.get(i);
            botones[i] = crearBoton(config,i);
            panel.add(botones[i]);
            panel.setComponentZOrder(botones[i], 0);
        }

        confirmar = crearBotonConfirmar();
        cancelar = crearBotonCancelar();
    }
    
    private Map<Integer, BotonConfiguracion> obtenerConfiguracionBotones() {
        Map<Integer, BotonConfiguracion> botonesConfiguracion = new HashMap<>();
        botonesConfiguracion.put(0, new BotonConfiguracion("whiteND.png", 
            "whiteD.png", 16));
        botonesConfiguracion.put(1, new BotonConfiguracion("blackND.png", 
            "blackD.png", 131));
        botonesConfiguracion.put(2, new BotonConfiguracion("redND.png", 
            "redD.png", 245));
        botonesConfiguracion.put(3, new BotonConfiguracion("blueND.png", 
            "blueD.png", 360));
        botonesConfiguracion.put(4, new BotonConfiguracion("greenND.png", 
            "greenD.png", 473));
        return botonesConfiguracion;
    }

    private JButton crearBoton(BotonConfiguracion config, int i){
        JButton boton = new JButton();
        boton.setBounds(config.getX(), 314, 110, 101);
        boton.setEnabled(true);
        String imagen = Jugador.getColor() == i ? config.getColorDisponible() : config.getColorNoDisponible();
        boton.setIcon(new ImageIcon(imagen));
        boton.addActionListener(cambiarColor);
        return boton;
    }

    ActionListener cambiarColor = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton boton = (JButton) e.getSource();
            int nuevoColor = -1;
            for (int i = 0; i < botones.length; i++) {
                if (boton == botones[i]) {
                    nuevoColor = i;
                    break;
                }
            }
            Jugador.setColor(nuevoColor);
            for (int i = 0; i < botones.length; i++) {
                if (i == nuevoColor)
                    panel.remove(botones[i]);
                else
                    panel.add(botones[i]);
            }
            repintar();
        }
    };

    void repintar(){
        iniciarComponentes();
        panel.repaint();
    }   

    private JButton crearBotonConfirmar() {
        JButton boton = new JButton();
        boton.setBounds(190, 450, 219, 77);
        boton.setEnabled(true);
        boton.setIcon(new ImageIcon("confirmar.png"));
        panel.add(boton);
        boton.addActionListener(e -> confirmar());
        return boton;
    }

    private JButton crearBotonCancelar() {
        JButton boton = new JButton();
        boton.setBounds(227, 560, 147, 28);
        boton.setEnabled(true);
        boton.setIcon(new ImageIcon("botonCancelar.png"));
        panel.add(boton);
        boton.addActionListener(e -> cancelar());
        return boton;
    }

    void cancelar(){
        new MenuPrincipal();
        this.dispose();
    }

    void confirmar(){
        Bomberman bomberman = new Bomberman();
        bomberman.startClient();
        this.dispose();
    }
}