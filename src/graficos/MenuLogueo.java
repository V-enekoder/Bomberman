package graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuLogueo extends Menu{

    private JLabel verificacion;
    private JButton botonCancelar, botonLoguearse;
    private JTextField ingresarUsuario;
    private String verificacionNombre = "Francisco";

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

        verificacion = new JLabel();
        verificacion.setBounds(75,460,500,16);
        verificacion.setFont(new Font("Arial",1,16));
        verificacion.setOpaque(true);
        verificacion.setForeground(Color.BLACK);
        panel.add(verificacion);

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

    void verificarDatos(){

        //Logica de archivos para verificar datos, hare un ejemplo con variables equis...

    }

    void retornar(){
        MenuPrincipal mainToBack=new MenuPrincipal();
        this.dispose();
    }
    ActionListener verificacionUsuario = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            String textoIngresado = ingresarUsuario.getText();
    
            if (verificacionNombre.equals(textoIngresado)) {
                System.out.println("Nombre verificado");
                verificacion.setText("El usuario: "+ingresarUsuario.getText()+" ha sido verificado/creado con exito.");
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
    void menuBatalla(){
        MenuBatalla batallaMenu=new MenuBatalla();
        this.dispose();
    }
}