package graficos;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Menu extends JFrame{

    protected JPanel panel;
    protected JLabel background;
    protected int respuesta;
    protected boolean respuestaEstablecida;
    
    protected abstract void iniciarComponentes();

    public int getRespuesta() {
        return respuesta;
    }
    public boolean isRespuestaEstablecida() {
        return respuestaEstablecida;
    }
    
}
