package graficos;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * Esta clase representa el menú de logueo en el juego Bomberman.
 * Permite a los usuarios ingresar y verificar sus credenciales.
 */

 public class MenuLogueo extends Menu {

    private JLabel verificacion, menuAlerta;
    private JButton botonCancelar, botonLoguearse, botonAceptar;
    JTextField ingresarUsuario;
    private static String textoIngresado;
    private static File archivoUsuarios;
    private static File archivo;

    /**
     * Obtiene el texto ingresado en el campo de texto de usuario.
     * @return El texto ingresado.
     */
    public static String getTextoIngresado() {
        return textoIngresado;
    }

    /**
     * Obtiene el campo de texto de ingreso de usuario.
     * @return El campo de texto de ingreso de usuario.
     */
    public JTextField getIngresarUsuario() {
        return ingresarUsuario;
    }

    /**
     * Constructor de la clase MenuLogueo.
     * Configura y muestra la ventana del menú de logueo.
     */
    public MenuLogueo(){
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
        colocarCajaTexto();
    }

    void colocarEtiquetas(){

        background=new JLabel();
        background.setIcon(new ImageIcon("menuLogin.png"));
        background.setBounds(0, 0, 600, 600);
        background.setOpaque(false);
        panel.add(background);

        menuAlerta=new JLabel();
        menuAlerta.setIcon(new ImageIcon("menuAlerta.png"));
        menuAlerta.setBounds(0, 0, 600, 600);
        menuAlerta.setOpaque(false);
        panel.add(menuAlerta);
        menuAlerta.setVisible(false);
        panel.setComponentZOrder(menuAlerta, 0);

        verificacion = new JLabel();
        verificacion.setBounds(90,380,500,14);
        verificacion.setFont(new Font("Arial",1,14));
        verificacion.setOpaque(false);
        verificacion.setForeground(Color.WHITE);
        //verificacion.setHorizontalAlignment(SwingConstants.CENTER); // centrar texto
        panel.add(verificacion);
        panel.setComponentZOrder(verificacion, 0);

    }

    void colocarCajaTexto(){

        ingresarUsuario = new JTextField();
        ingresarUsuario.setBounds(97, 306, 234, 24);
        panel.add(ingresarUsuario);

    }

    void colocarBotones(){
        botonCancelar=new JButton();
        botonCancelar.setBounds(228, 404, 147, 28);
        botonCancelar.setEnabled(true); // Me deja o no pinchar el boton
        botonCancelar.setIcon(new ImageIcon("botonCancelar.png"));
        panel.add(botonCancelar);
        botonCancelar.setVisible(true);

        botonLoguearse=new JButton();
        botonLoguearse.setBounds(341, 303, 147, 28);
        botonLoguearse.setEnabled(true); // Me deja o no pinchar el boton
        botonLoguearse.setIcon(new ImageIcon("botonLoguearse.png"));
        panel.add(botonLoguearse);
        botonLoguearse.setVisible(true);

        botonAceptar=new JButton();
        botonAceptar.setBounds(255, 306, 87, 30);
        botonAceptar.setEnabled(true); // Me deja o no pinchar el boton
        botonAceptar.setIcon(new ImageIcon("botonAceptar.png"));
        panel.add(botonAceptar);
        botonAceptar.setVisible(false);

        // Cancelar (vuelvo al menu principal)
        botonCancelar.addActionListener(volverMenuPrincipal);
        botonLoguearse.addActionListener(verificarUsuario);
        botonAceptar.addActionListener(aceptar);

    }

    /**
     * ActionListener para volver al menú principal.
     */
    ActionListener volverMenuPrincipal = (ActionEvent e)->{
        reproducirSonido("audio2.wav");
        botonCancelar.setVisible(false);
        botonLoguearse.setVisible(false);
        background.setVisible(false);
        retornar();
    };

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ActionListener para verificar el usuario ingresado.
     */
    ActionListener verificarUsuario = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            textoIngresado = ingresarUsuario.getText();
    
            if (textoIngresado.isEmpty()) {
                sinTexto();
                return;
            }
    
            if (verificarUsuario(textoIngresado)) {
                verificacion.setText("El usuario: " + ingresarUsuario.getText() + " ha sido verificado con éxito.");
                System.out.println("Usuario verificado");
                reproducirSonido("audio1.wav");
                panel.remove(botonLoguearse);
                panel.remove(botonCancelar);
            } else {
                verificacion.setText("El usuario: " + ingresarUsuario.getText() + " ha sido creado con éxito.");
                reproducirSonido("audio1.wav");
                System.out.println("Usuario registrado");
                guardarUsuario(textoIngresado);
    
                panel.remove(botonLoguearse);
                panel.remove(botonCancelar);
            }
            crearArchivoEstadisticas(textoIngresado);
            Timer timer = new Timer(1000 * 0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menuBatalla(textoIngresado);
                }
            });
            timer.setRepeats(false); // esto asegura que el temporizador solo se ejecute una vez
            timer.start();
        }
    };

    /**
     * Verifica si un usuario existe en el archivo de usuarios.
     * @param usuario El nombre de usuario a verificar.
     * @return true si el usuario existe, false de lo contrario.
     */
    boolean verificarUsuario(String usuario) {
        archivoUsuarios = new File("src/DatosJugadores/listaJugadores.txt");
    
        try {
            if (!archivoUsuarios.exists()) {
                archivoUsuarios.createNewFile();
                return false;
            }

            java.util.Scanner scanner = new java.util.Scanner(archivoUsuarios);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.equals(usuario)) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Guarda un nuevo usuario en el archivo de usuarios.
     * @param usuario El nombre de usuario a guardar.
     */
    void guardarUsuario(String usuario) {
        archivoUsuarios = new File("src/DatosJugadores/" + "listaJugadores.txt");
      
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios, true))) {
            writer.write(usuario + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Crea un archivo de estadísticas para un nuevo usuario.
     * @param usuario El nombre de usuario para el archivo de estadísticas.
     */
    void crearArchivoEstadisticas(String usuario) {
        archivo = new File("src/DatosJugadores/"+usuario +".txt");

        if (!archivo.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                String datosIniciales = "Partidas jugadas: 1"+ 
                "\nPartidas ganadas: 0" +
                "\nPartidas perdidas: 0" +
                "\nPartidas abandonadas: 0";
                writer.write(datosIniciales);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Muestra un mensaje de alerta cuando no se ingresa texto en el campo de usuario.
     */
    void sinTexto(){
        botonCancelar.setVisible(false);
        botonLoguearse.setVisible(false);
        ingresarUsuario.setVisible(false);

        menuAlerta.setVisible(true);
        botonAceptar.setVisible(true);
    }

    /**
     * ActionListener para aceptar el mensaje de alerta de falta de texto.
     */
    ActionListener aceptar=(ActionEvent e)->{
        menuAlerta.setVisible(false);
        botonAceptar.setVisible(false);
        iniciarComponentes();
    };

    /**
     * Retorna al menú principal del juego.
     */
    void retornar(){
        new MenuPrincipal();
        this.dispose();
    }

    /**
     * Abre el menú de batalla del juego.
     * @param usuario El nombre de usuario para el juego.
     */
    void menuBatalla(String usuario){
        new MenuBatalla(usuario);
        this.dispose();
    }
}