package graficos;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import Juego.Packet.Packet03Informacion;
import Juego.Packet.Packet04Actualizacion;
import Server.UDP.Cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuBatalla extends Menu {

    private JButton confirmar, cancelar;
    private JButton[] botones = new JButton[5];
    private JLabel jugador, eleccion;
    private String nombre;
    private static int colorSeleccionado;
    private static boolean seleccion = false;
    private ArrayList<Integer> colores = new ArrayList<>();
    //private Estadisticas estadisticas = new Estadisticas();

    public MenuBatalla(String nombre) {
        this.nombre=nombre;
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

        colorSeleccionado = 0;
        recibirColores();
    }

    private void recibirColores(){
        this.colores.clear();
        Cliente c = new Cliente();
        Packet03Informacion informacion = new Packet03Informacion();
        informacion.enviar(c);
        DatagramPacket recibido = c.recibir();
        c.analizarPacket(recibido.getData(),recibido.getAddress(),recibido.getPort());
        this.colores = c.getColoresDisponibles();
    }
        private void reproducirSonido(String ruta) {
        try {
            File archivoSonido = new File(ruta);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void iniciarComponentes() {
        iniciarEtiquetas();
        iniciarBotones();
    }

    void iniciarEtiquetas(){
        String color = "";

        switch (colorSeleccionado) {
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
        String imagen;
        if(!colores.contains(i))
            imagen = config.getColorDisponible();
        else
            imagen = colorSeleccionado == i ? config.getColorDisponible(): 
                config.getColorNoDisponible();
        boton.setIcon(new ImageIcon(imagen));
        boton.addActionListener(cambiarColor);
        return boton;
    }

    /**
     * Cambia el color seleccionado por el jugador al hacer clic en un botón de color.
     * @param e El evento de acción que desencadena la función.
     */
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

            if(colores.contains(nuevoColor)){
                colorSeleccionado = nuevoColor;
                for (int i = 0; i < botones.length; i++) {
                    if (i == nuevoColor)
                        panel.remove(botones[i]);
                    else
                        panel.add(botones[i]);
                }
            }
            reproducirSonido("audio3.wav");
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
        boton.addActionListener(e -> {
            confirmar();
            reproducirSonido("audio1.wav");
        });
        return boton;
    }

    private JButton crearBotonCancelar() {
        JButton boton = new JButton();
        boton.setBounds(227, 560, 147, 28);
        boton.setEnabled(true);
        boton.setIcon(new ImageIcon("botonCancelar.png"));
        panel.add(boton);
        boton.addActionListener(e -> {
            cancelar();
            reproducirSonido("audio4.wav");
        });
        return boton;
    }

    void cancelar(){
        new MenuPrincipal();
        this.dispose();
    }

    void confirmar(){
        enviarColores();
        seleccion = true;
        this.dispose();
    }

    /**
     * Envia los colores seleccionados por los jugadores al servidor para su actualización.
     */
    private void enviarColores(){
        for(Integer i: colores){
            if(i == colorSeleccionado){
                colores.remove(i);  
                break;
            } 
        }
        
        String datos = "";
        for (Integer c : colores)
            datos += String.valueOf(c);
        
        Cliente c = new Cliente();
        Packet04Actualizacion actualizacion = new Packet04Actualizacion(datos);
        actualizacion.enviar(c);
        try {
            Thread.sleep(1000); // Espera 1 segundo antes de verificar nuevamente
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isSeleccion() {
        return seleccion;
    }

    public static void setSeleccion(boolean seleccion) {
        MenuBatalla.seleccion = seleccion;
    }

    public static int getColorSeleccionado() {
        return colorSeleccionado;
    }

    public static void setColorSeleccionado(int colorSeleccionado) {
        MenuBatalla.colorSeleccionado = colorSeleccionado;
    }
}