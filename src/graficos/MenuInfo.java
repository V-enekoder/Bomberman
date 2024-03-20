package graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Esta clase representa el menú de información en el juego Bomberman.
 * Proporciona información sobre el juego y permite navegar entre diferentes pantallas de información.
 */
public class MenuInfo extends Menu {

    JLabel background2, background3;
    JButton irAdelante, irAtras, botonRegresar;
    private int cambios;

    /**
     * Constructor de la clase MenuInfo.
     * Configura y muestra la ventana del menú de información.
     */
    public MenuInfo() {
        setSize(616, 639);
        setTitle("Bomberman: Menu de Información (Info I)");
        setLocationRelativeTo(null);
        setResizable(false);
        setMinimumSize(new Dimension(200, 200));
        getContentPane().setBackground(Color.white);
        iniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        respuesta = 0;
        respuestaEstablecida = false;
    }

    @Override
    protected void iniciarComponentes() {
        colocarPanel();
        colocarEtiquetas();
        colocarBotones();
        cambios = 1;
    }

    private void colocarPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }

    private void colocarEtiquetas() {
        background = new JLabel();
        background.setIcon(new ImageIcon("menuPrincipalInfo1.png"));
        background.setBounds(0, 0, 600, 600);
        panel.add(background);

        background2 = new JLabel();
        background2.setIcon(new ImageIcon("menuPrincipalInfo2.png"));
        background2.setBounds(0, 0, 600, 600);
        panel.add(background2);
        background2.setVisible(false);

        background3 = new JLabel();
        background3.setIcon(new ImageIcon("menuAcercaDe.png"));
        background3.setBounds(0, 0, 600, 600);
        panel.add(background3);
        background3.setVisible(false);
    }

    private void colocarBotones() {
        irAdelante = new JButton();
        irAdelante.setBounds(500, 462, 42, 42);
        irAdelante.setEnabled(true);
        irAdelante.setIcon(new ImageIcon("irAdelante.png"));
        panel.add(irAdelante);
        irAdelante.setVisible(true);

        irAtras = new JButton();
        irAtras.setBounds(445, 462, 42, 42);
        irAtras.setEnabled(true);
        irAtras.setIcon(new ImageIcon("irAtras.png"));
        panel.add(irAtras);
        irAtras.setVisible(true);

        botonRegresar = new JButton();
        botonRegresar.setBounds(228, 528, 145, 26);
        botonRegresar.setEnabled(true);
        botonRegresar.setIcon(new ImageIcon("botonRegresar.png"));
        panel.add(botonRegresar);
        botonRegresar.setVisible(true);

        irAdelante.addActionListener(pasar);
        irAtras.addActionListener(devolver);
        botonRegresar.addActionListener(volverMenuPrincipal);
    }

    /**
     * Reproduce un sonido desde un archivo de audio.
     * @param ruta La ruta del archivo de audio.
     */
    private void reproducirSonido(String ruta) {
        try {
            File archivoSonido = new File(ruta);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    ActionListener pasar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cambios == 1) {
                background.setVisible(false);
                background2.setVisible(true);
                background3.setVisible(false);
                cambios = 2;
            } else if (cambios == 2) {
                background.setVisible(false);
                background2.setVisible(false);
                background3.setVisible(true);
                cambios = 3;
            } else if (cambios == 3) {
                background.setVisible(true);
                background2.setVisible(false);
                background3.setVisible(false);
                cambios = 1;
            } else {
                System.out.println("No hay mas cambios");
            }

            reproducirSonido("audio2.wav");
        }
    };

    ActionListener devolver = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cambios == 1) {
                background.setVisible(false);
                background2.setVisible(false);
                background3.setVisible(true);
                cambios = 3;
            } else if (cambios == 3) {
                background.setVisible(false);
                background2.setVisible(true);
                background3.setVisible(false);
                cambios = 2;
            } else if (cambios == 2) {
                background.setVisible(true);
                background2.setVisible(false);
                background3.setVisible(false);
                cambios = 1;
            } else {
                System.out.println("No hay mas cambios");
            }

            reproducirSonido("audio2.wav");
        }
    };

    ActionListener volverMenuPrincipal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            reproducirSonido("audio2.wav");
            background.setVisible(false);
            retornar();
        }
    };

    /**
     * Retorna al menú principal del juego.
     */
    void retornar() {
        new MenuPrincipal();
        this.dispose();
    }
}
