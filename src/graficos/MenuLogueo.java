package graficos;

import javax.swing.*;

//import Juego.Estadisticas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MenuLogueo extends Menu{

    private JLabel verificacion, menuAlerta;
    private JButton botonCancelar, botonLoguearse, botonAceptar;
    JTextField ingresarUsuario;
    private static String textoIngresado;
    private static File archivoUsuarios;
    private static File archivo;

    public static String getTextoIngresado() {
        return textoIngresado;
    }

    public JTextField getIngresarUsuario() {
        return ingresarUsuario;
    }

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
    ActionListener volverMenuPrincipal = (ActionEvent e)->{
        botonCancelar.setVisible(false);
        botonLoguearse.setVisible(false);
        background.setVisible(false);
        retornar();
    };

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
    
                panel.remove(botonLoguearse);
                panel.remove(botonCancelar);
            } else {
                verificacion.setText("El usuario: " + ingresarUsuario.getText() + " ha sido creado con éxito.");
                
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


    boolean verificarUsuario(String usuario) {
        
// Si no se encontro el usuario en el archivo, retorna false
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
    
    void guardarUsuario(String usuario) {
        archivoUsuarios = new File("src/DatosJugadores/" + "listaJugadores.txt");
      
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios, true))) {
            writer.write(usuario + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void crearArchivoEstadisticas(String usuario) {
        archivo = new File("src/DatosJugadores/"+usuario +".txt");

        if (!archivo.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                String datosIniciales = "Partidas jugadas: 1"+ 
                "\nPartidas ganadas: 0" +
                "\nPartidas perdidas: 0" +
                "\nPartidas abandonadas: 0";
                // Convertimos las estadisticas a formato de texto y las escribimos en el archivo
                //
                writer.write(datosIniciales);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    void sinTexto(){

        botonCancelar.setVisible(false);
        botonLoguearse.setVisible(false);
        ingresarUsuario.setVisible(false);

        menuAlerta.setVisible(true);
        botonAceptar.setVisible(true);

    }

    ActionListener aceptar=(ActionEvent e)->{

        menuAlerta.setVisible(false);
        botonAceptar.setVisible(false);
        iniciarComponentes();

    };


    void retornar(){
        new MenuPrincipal();
        this.dispose();
    }

    void menuBatalla(String usuario){
        new MenuBatalla(usuario);
        this.dispose();
    }
}