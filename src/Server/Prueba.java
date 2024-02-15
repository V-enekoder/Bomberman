package Server;
import java.util.*;

public class Prueba {
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        Cliente cliente = new Cliente();

        Thread servidorThread = new Thread(servidor);
        Thread clienteThread = new Thread(cliente);

        servidorThread.start();
        clienteThread.start();
    }
}