package Server.UDP;

import java.awt.Frame;

public class Prueba {
    
    public static void main(String[] args) {
        Frame frame = new Frame("Ejemplo de dispose()");
        frame.setSize(400, 300);
        frame.setVisible(true);

        // Realizar alguna operación...

        // Llamar a dispose() para cerrar la ventana
        frame.dispose();
    }
}
