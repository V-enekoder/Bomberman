package graficos;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Menu extends JFrame{

    protected JPanel panel;
    protected JLabel background;

    protected abstract void iniciarComponentes();
}
