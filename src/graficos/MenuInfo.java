package graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInfo extends Menu{

        JLabel background2, background3;
        JButton irAdelante, irAtras, botonRegresar;
        private int cambios;

        public MenuInfo(){
            setSize(616, 639);
            setTitle("Bomberman: Menu de Información (Info I)");
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
        cambios=1;
    }

    private void colocarPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }

    private void colocarEtiquetas(){
        
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

    private void colocarBotones(){

        irAdelante = new JButton();
        irAdelante.setBounds(500, 462, 42, 42);
        irAdelante.setEnabled(true); // Me deja o no pinchar el boton
        irAdelante.setIcon(new ImageIcon("irAdelante.png"));
        panel.add(irAdelante);
        irAdelante.setVisible(true);

        irAtras = new JButton();
        irAtras.setBounds(445, 462, 42, 42);
        irAtras.setEnabled(true); // Me deja o no pinchar el boton
        irAtras.setIcon(new ImageIcon("irAtras.png"));
        panel.add(irAtras);
        irAtras.setVisible(true);

        botonRegresar = new JButton();
        botonRegresar.setBounds(228, 528, 145, 26);
        botonRegresar.setEnabled(true); // Me deja o no pinchar el boton
        botonRegresar.setIcon(new ImageIcon("botonRegresar.png"));
        panel.add(botonRegresar);
        botonRegresar.setVisible(true);

        irAdelante.addActionListener(pasar);
        irAtras.addActionListener(devolver);
        botonRegresar.addActionListener(volverMenuPrincipal);
    }

    ActionListener pasar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(cambios==1){
                background.setVisible(false);
                background2.setVisible(true);
                background3.setVisible(false);
                cambios=2;
            } else if(cambios==2){
                background.setVisible(false);
                background2.setVisible(false);
                background3.setVisible(true);
                cambios=3;
            } else if(cambios==3){
                background.setVisible(true);
                background2.setVisible(false);
                background3.setVisible(false);
                cambios=1;
            } else{
                System.out.println("No hay mas cambios");
            }
        }
    };

    ActionListener devolver = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(cambios==1){
                background.setVisible(false);
                background2.setVisible(false);
                background3.setVisible(true);
                cambios=3;
            } else if(cambios==3){
                background.setVisible(false);
                background2.setVisible(true);
                background3.setVisible(false);
                cambios=2;
            } else if(cambios==2){
                background.setVisible(true);
                background2.setVisible(false);
                background3.setVisible(false);
                cambios=1;
            } else{
                System.out.println("No hay mas cambios");
            }
        }
    };

    ActionListener volverMenuPrincipal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            background.setVisible(false);
            retornar();
        }
    };

    void retornar(){
        new MenuPrincipal();
        this.dispose();
    } 

}
