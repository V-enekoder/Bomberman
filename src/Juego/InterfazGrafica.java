package Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

public class InterfazGrafica extends JFrame{
	private ComponenteGrafico bombermanComponent;

	public InterfazGrafica(final String title, Tablero tablero) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		bombermanComponent = new ComponenteGrafico(tablero);
		setKeyStrokes();
		this.setLayout(new BorderLayout());
		this.add(bombermanComponent, BorderLayout.CENTER);
		this.pack();
		//this.setVisible(true);
    }

    public ComponenteGrafico getBombermanComponent() {
		return bombermanComponent;
    }

    private void setKeyStrokes() {

		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, 
			Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
		bombermanComponent.getInputMap().put(stroke, "q");
		bombermanComponent.getActionMap().put("q", quit);
    }
    private final Action quit = new AbstractAction(){
		public void actionPerformed(ActionEvent e) {
			dispose();    
		}
    };
}

