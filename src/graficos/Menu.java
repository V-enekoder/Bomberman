package graficos;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase abstracta que representa un menú visual en una aplicación gráfica.
 * Extiende la clase JFrame de Swing para proporcionar funcionalidad de ventana.
 */
public abstract class Menu extends JFrame{

    protected JPanel panel;
    protected JLabel background;
    protected int respuesta;
    protected boolean respuestaEstablecida;
    
    /**
     * Método abstracto que debe ser implementado por las subclases para inicializar los componentes del menú.
     */
    protected abstract void iniciarComponentes();

    /**
     * Obtiene la respuesta seleccionada en el menú.
     * @return La respuesta seleccionada.
     */
    public int getRespuesta() {
        return respuesta;
    }

    /**
     * Verifica si la respuesta en el menú ha sido establecida.
     * @return true si la respuesta ha sido establecida, false de lo contrario.
     */
    public boolean isRespuestaEstablecida() {
        return respuestaEstablecida;
    }
    
}

