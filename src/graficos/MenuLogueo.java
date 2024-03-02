package graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuLogueo extends Menu{

    private JLabel verificacion;
    private JButton botonCancelar, botonLoguearse;
    JTextField ingresarUsuario;
    private static String textoIngresado;
    private static String verificacionNombre = "Francisco";

    public static String getTextoIngresado() {
        return textoIngresado;
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

        // Cancelar (vuelvo al menu principal)
        botonCancelar.addActionListener(backToMain);
        botonLoguearse.addActionListener(verificacionUsuario);

    }
    ActionListener backToMain=(ActionEvent e)->{
        botonCancelar.setVisible(false);
        botonLoguearse.setVisible(false);
        background.setVisible(false);
        System.out.println("game.log_cancelLogueo");
        retornar();
    };

    ActionListener verificacionUsuario = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            textoIngresado = ingresarUsuario.getText();
    
            if (verificacionNombre.equals(textoIngresado)) {
                System.out.println("Nombre verificado");
                verificacion.setText("El usuario: "+ingresarUsuario.getText()+" ha sido verificado con exito.");
                panel.remove(botonLoguearse);
                panel.remove(botonCancelar);
                // esperar 5 segundos antes de llamar a menuBatalla para mostrar mensaje de verificacion
                Timer timer = new Timer(1000*1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuBatalla();
                    }
                }); timer.setRepeats(false); // esto asegura que el temporizador solo se ejecute una vez
                timer.start();
            }
            else{
                //Registrar usuario.
                panel.remove(botonLoguearse);
                panel.remove(botonCancelar);
                verificacion.setText("El usuario: "+ingresarUsuario.getText()+" ha sido creado con exito.");
                Timer timer = new Timer(1000*1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuBatalla();
                    }
                }); timer.setRepeats(false); // esto asegura que el temporizador solo se ejecute una vez
                timer.start();
            }
        }
    };


    void retornar(){
        new MenuPrincipal();
        this.dispose();
    }
    
    void verificarDatos(){

        //Logica de archivos para verificar datos, hare un ejemplo con variables equis...

    }

    void menuBatalla(){
        new MenuBatalla();
        this.dispose();
    }
}