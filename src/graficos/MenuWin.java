package graficos;

import javax.swing.*;
import java.awt.*;

/**
 * La clase MenuWin representa la ventana que muestra los ganadores y perdedores del juego Bomberman.
 * Extiende la clase Menu para heredar sus funcionalidades de configuración de la ventana.
 */
public class MenuWin extends Menu {
    
    private JLabel nameP1, nameP2;
    private String ganador, perdedor;

    /**
     * Crea una nueva instancia de la ventana MenuWin.
     * 
     * @param ganador El nombre del jugador ganador.
     * @param perdedor El nombre del jugador perdedor.
     */
    public MenuWin(String ganador, String perdedor) {
        // Configura la ventana del juego
        setSize(616, 639);
        setTitle("Bomberman: Menu de Ganadores y Perdedores");
        setLocationRelativeTo(null); // La ubicación en el centro
        setResizable(false); // Se puede o no se puede cambiar el tamaño de la ventana
        setMinimumSize(new Dimension(200, 200)); // Establece el tamaño mínimo
        getContentPane().setBackground(Color.white); // Establece el color de la ventana
        this.ganador = ganador;
        this.perdedor = perdedor;
        iniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        respuesta = 0;
        respuestaEstablecida = false;
    }

    /**
     * Inicializa los componentes de la ventana, como el panel y las etiquetas.
     */
    @Override
    protected void iniciarComponentes() {
        colocarPanel();
        colocarEtiquetas();
    }

    /**
     * Coloca el panel en la ventana.
     */
    private void colocarPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.getContentPane().add(panel);
    }

    /**
     * Coloca las etiquetas de los ganadores y perdedores en la ventana.
     */
    void colocarEtiquetas() {
        background = new JLabel();
        background.setIcon(new ImageIcon("menuServidorWin.png"));
        background.setBounds(0, 0, 600, 600);
        panel.add(background);

        nameP1 = new JLabel(ganador);
        nameP1.setBounds(90, 140, 500, 18);
        nameP1.setFont(new Font("Arial", 1, 18));
        nameP1.setOpaque(false);
        nameP1.setForeground(Color.WHITE);
        panel.add(nameP1);
        panel.setComponentZOrder(nameP1, 0);

        nameP2 = new JLabel(perdedor);
        nameP2.setBounds(90, 245, 500, 18);
        nameP2.setFont(new Font("Arial", 1, 18));
        nameP2.setOpaque(false);
        nameP2.setForeground(Color.WHITE);
        panel.add(nameP2);
        panel.setComponentZOrder(nameP2, 0);
    }
}
