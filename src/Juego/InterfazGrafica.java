package Juego;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * La clase InterfazGrafica representa la interfaz gráfica principal del juego.
 * Extiende la clase JFrame y contiene un ComponenteGrafico para mostrar el tablero del juego.
 */
public class InterfazGrafica extends JFrame{
    private ComponenteGrafico bombermanComponent; // Componente gráfico para mostrar el tablero del juego

    /**
     * Constructor de la clase InterfazGrafica.
     * @param title El título de la ventana de la interfaz gráfica.
     * @param tablero El tablero del juego que se mostrará en la interfaz gráfica.
     */
    public InterfazGrafica(final String title, Tablero tablero) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Cierra la ventana al presionar el botón de cerrar
        bombermanComponent = new ComponenteGrafico(tablero); // Crea un nuevo componente gráfico con el tablero proporcionado
        setKeyStrokes(); // Configura los atajos de teclado
        this.setLayout(new BorderLayout()); // Establece un diseño de borde
        this.add(bombermanComponent, BorderLayout.CENTER); // Agrega el componente gráfico al centro de la ventana
        this.pack(); // Ajusta el tamaño de la ventana según el contenido
    }

    /**
     * Obtiene el ComponenteGrafico asociado a esta interfaz gráfica.
     * @return El ComponenteGrafico asociado a esta interfaz gráfica.
     */
    public ComponenteGrafico getBombermanComponent() {
        return bombermanComponent;
    }

    /**
     * Configura los atajos de teclado para la interfaz gráfica.
     * En este caso, agrega un atajo para cerrar la ventana al presionar CTRL + W.
     */
    private void setKeyStrokes() {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, 
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()); // Define la combinación de teclas CTRL + W
        bombermanComponent.getInputMap().put(stroke, "q"); // Asigna la combinación de teclas a la acción "q"
        bombermanComponent.getActionMap().put("q", quit); // Asigna la acción "q" al método quit
    }

    /**
     * Acción para cerrar la ventana.
     * Esta acción se activa cuando se presiona el atajo de teclado CTRL + W.
     */
    private final Action quit = new AbstractAction(){
        public void actionPerformed(ActionEvent e) {
            dispose(); // Cierra la ventana    
        }
    };
}