package Server.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Juego.Packet.Packet;
import Juego.Packet.Packet00Ingreso;
import Juego.Packet.Packet01Desconexion;
import Juego.Packet.Packet02Derrota;
import Juego.Packet.Packet03Informacion;
import graficos.MenuBatalla;
import graficos.MenuLogueo;
import graficos.MenuPrincipal;

/**
 * La clase Cliente representa un cliente UDP que puede enviar y recibir paquetes desde un servidor.
 * Implementa la interfaz Runnable para ejecutar el cliente en un hilo separado.
 */
public class Cliente implements Runnable {

    private final int PUERTO_SERVIDOR;
    private byte[] datos;
    private DatagramSocket socket;
    private InetAddress direccionServidor;
    private volatile boolean running = true;
    private ArrayList<Integer> coloresDisponibles = new ArrayList<>();
    private String nombre;
    private int color;

    /**
     * Constructor de la clase Cliente sin parámetros.
     * Configura el puerto del servidor y el tamaño del buffer de datos.
     * Crea un nuevo socket DatagramSocket y obtiene la dirección del servidor local.
     */
    public Cliente(){
        this.PUERTO_SERVIDOR = 5000;
        this.datos = new byte[1024];
        try {
            this.socket = new DatagramSocket();
            this.direccionServidor = InetAddress.getByName("localhost");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor de la clase Cliente con nombre y color seleccionado.
     * Configura el puerto del servidor y el tamaño del buffer de datos.
     * Crea un nuevo socket DatagramSocket y obtiene la dirección del servidor local.
     * @param nombre El nombre del cliente.
     * @param colorSeleccionado El color seleccionado por el cliente.
     */
    public Cliente(String nombre, int colorSeleccionado){
        this.PUERTO_SERVIDOR = 5000;
        this.datos = new byte[1024];
        this.nombre = nombre;
        color = colorSeleccionado;
        try {
            this.socket = new DatagramSocket();
            this.direccionServidor = InetAddress.getByName("localhost");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implementación del método run de la interfaz Runnable.
     * Inicia el cliente y realiza el proceso de registro y comunicación con el servidor.
     */
    @Override
    public void run() {
        // Código para la interfaz gráfica de usuario
        MenuPrincipal v1 = new MenuPrincipal();
        
        // Espera hasta que la selección haya ocurrido
        while (!MenuBatalla.isSeleccion()) {
            try {
                Thread.sleep(1000); // Espera 1 segundo antes de verificar nuevamente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Packet00Ingreso ingreso = new Packet00Ingreso(MenuLogueo.getTextoIngresado(), MenuBatalla.getColorSeleccionado());
        ingreso.enviar(this);
        
        while(running){
            DatagramPacket recibido = recibir();
            analizarPacket(recibido.getData(), recibido.getAddress(), recibido.getPort());
        }
        socket.close();
    }

    /**
     * Analiza el paquete recibido y toma acciones según su tipo.
     * @param data Los datos contenidos en el paquete.
     * @param address La dirección IP del remitente del paquete.
     * @param port El puerto del remitente del paquete.
     */
    public void analizarPacket(byte[] data, InetAddress address, int port) {
        String mensaje = new String(data).trim();
        int id = Integer.parseInt(mensaje.substring(0, 2));
        String nombre;
        switch(Packet.identificarTipo(id)){
            default:
            case INVALIDO:
                break;
            case INGRESO:
                Packet00Ingreso ingreso = new Packet00Ingreso(data);
                break;
            case DESCONEXION:
                Packet01Desconexion desconexion = new Packet01Desconexion(data);
                break;
            case DERROTA:
                Packet02Derrota derrota = new Packet02Derrota(data);
                nombre = derrota.getNombre();
                break;
            case INFORMACION:
                Packet03Informacion info = new Packet03Informacion(data);
                for (char caracter : info.getColores().toCharArray())
                    this.coloresDisponibles.add(Character.getNumericValue(caracter));
                break;
        }
    }

    /**
     * Devuelve la lista de colores disponibles.
     * @return La lista de colores disponibles.
     */
    public ArrayList<Integer> getColoresDisponibles() {
        return this.coloresDisponibles;
    }

    /**
     * Establece la lista de colores disponibles.
     * @param coloresDisponibles La lista de colores disponibles a establecer.
     */
    public void setColoresDisponibles(ArrayList<Integer> coloresDisponibles) {
        this.coloresDisponibles = coloresDisponibles;
    }

    /**
     * Recibe un paquete del servidor.
     * @return El paquete recibido.
     */
    public DatagramPacket recibir(){
        DatagramPacket paquete = new DatagramPacket(datos,datos.length);
        try {
            socket.receive(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paquete;
    }

    /**
     * Envía datos al servidor.
     * @param datos Los datos a enviar.
     */
    public void enviarServidor(byte[] datos){
        DatagramPacket respuesta = new DatagramPacket(datos, datos.length, direccionServidor, PUERTO_SERVIDOR);
        try {
            socket.send(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Detiene la ejecución del cliente.
     */
    public void stop() {
        running = false;
    }
}

