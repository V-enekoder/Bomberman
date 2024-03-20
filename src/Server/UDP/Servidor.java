package Server.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import java.util.Map;


import Juego.Bomberman;
import Juego.InterfazGrafica;
import Juego.Tablero;
import Juego.Packet.*;
import Juego.Personaje.Jugador;
import graficos.MenuWin;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La clase Servidor representa un servidor UDP que gestiona la conexión y comunicación con los clientes.
 * Implementa la interfaz Runnable para ejecutar el servidor en un hilo separado.
 */

public class Servidor implements Runnable{
    private final int PUERTO;
    private byte[] datos;
    private DatagramSocket socket;
    private volatile boolean running = true;
    private List<Jugador> jugadoresConectados = new ArrayList<>();
    private List<String> nombresJugadores = new ArrayList<>();
    private Map<Jugador, Packet00Ingreso> ingresosPendientes = new HashMap<>();
    private ArrayList<Integer> coloresDisponibles = new ArrayList<>();
    private boolean juegoIniciado;
    private Tablero tablero;
	private ArrayList<int[]> inicio = new ArrayList<>();
    private ArrayList<Integer> id = new ArrayList<>();
    private Map<String, InterfazGrafica> interfaces = new HashMap<>();
    public int minimo = 1;
    private MenuWin w;

    public Servidor(int puerto){

        this.PUERTO = puerto;
        this.datos = new byte[1024];

        this.tablero = new Tablero(15,15,1);

        for(int i = 0; i < 5; i++)
            coloresDisponibles.add(i);
    
        inicio.add(new int[]{60, 60});
        inicio.add(new int[]{60, 540});
        inicio.add(new int[]{540, 60});
        inicio.add(new int[]{540, 540});
        
        for(int i = 0; i < 4; i++)
            id.add(i);
        
        try {
            this.socket = new DatagramSocket(PUERTO);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
    /**
     * Implementación del método run de la interfaz Runnable.
     * Inicia el servidor y gestiona la conexión y comunicación con los clientes.
     */
    @Override
    public void run() {
        Bomberman juego = new Bomberman();
        juegoIniciado = false;
        while(running){
            DatagramPacket recibido = recibir(datos);
            analizarPacket(recibido.getData(),recibido.getAddress(),recibido.getPort());
            if(ingresosPendientes.size() == minimo && !juegoIniciado){
                juego.startGame(tablero);
                ingresosPendientes.clear();
                juegoIniciado = true;
                for (Map.Entry<String, InterfazGrafica> entry : interfaces.entrySet()) {
                    InterfazGrafica valor = entry.getValue();
                   valor.setVisible(true);
                }
            }
        }
        socket.close();
    }
    /**
     * Analiza el paquete recibido y toma acciones según su tipo.
     * @param data Los datos contenidos en el paquete.
     * @param address La dirección IP del remitente del paquete.
     * @param port El puerto del remitente del paquete.
     */
    private void analizarPacket(byte[] data, InetAddress address, int port) { //Yo tengo el tablero, solo debo mirar ahí
        String mensaje = new String(data).trim();
        int id = Integer.parseInt(mensaje.substring(0, 2));
        String nombre;
        String [] puestos = {"",""};
        MenuWin menu;
        Jugador j;
        switch (Packet.identificarTipo(id)) {
            case INVALIDO:
                break;
            case INGRESO:
                Packet00Ingreso ingreso = new Packet00Ingreso(data);
                nombre = ingreso.getNombre();
                nombresJugadores.add(nombre);
    
                int color = Integer.parseInt(mensaje.substring(2, 3));
    
                j = crearJugador(nombre, address, port, color);
    
                ingresosPendientes.put(j, ingreso);
    
                if (ingresosPendientes.size() == minimo && !juegoIniciado) {
                    for (Map.Entry<Jugador, Packet00Ingreso> entrada : ingresosPendientes.entrySet()) {
                        tablero.agregarJugador(entrada.getKey());
                        conectarJugador(entrada.getKey(), entrada.getValue());
                    }
                } else if (juegoIniciado) {
                    for (Map.Entry<Jugador, Packet00Ingreso> entrada : ingresosPendientes.entrySet()) {
                        tablero.agregarJugador(entrada.getKey());
                        conectarJugador(entrada.getKey(), entrada.getValue());
                    }
                    ingresosPendientes.clear();
                    for (Map.Entry<String, InterfazGrafica> entry : interfaces.entrySet()) {
                        InterfazGrafica valor = entry.getValue();
                        valor.setVisible(true);
                    }
                }
                break;
    
            case DESCONEXION:
                Packet01Desconexion desconexion = new Packet01Desconexion(data);
                nombre = tablero.getJugadores().get(desconexion.getIdJugador()).getNombre();
                //nombre = nombresJugadores.get(desconexion.getIdJugador());
                if (!getJugador(desconexion.getIdJugador()).isFantasma()) {
                    getJugador(desconexion.getIdJugador()).getDatos().aumentarPartidasAbandonadas();
                    getJugador(desconexion.getIdJugador()).getDatos().guardar();
                }
                tablero.eliminarJugador(getJugador(desconexion.getIdJugador()));
                desconectarJugador(desconexion);
                break;
    
            case DERROTA:
                Packet02Derrota derrota = new Packet02Derrota(data);
                nombre = tablero.getJugadores().get(derrota.getIdJugador()).getNombre();
                System.out.println("Ha perdido "+ nombre);
                break;
    
            case INFORMACION:
                String colores = "";
                for (Integer c : coloresDisponibles)
                    colores += String.valueOf(c);
                Packet03Informacion info = new Packet03Informacion(colores);
                enviar(info.getInformacion(), address, port);
                break;
    
            case ACTUALIZACION:
                Packet04Actualizacion actualizacion = new Packet04Actualizacion(data);
                this.coloresDisponibles.clear();
                for (char caracter : actualizacion.getColores().toCharArray())
                    this.coloresDisponibles.add(Character.getNumericValue(caracter));
                break;
    
            case GAMEOVER:

            for (Jugador jugador : tablero.getJugadores()) {
                if (jugador.getVidas() != 0) {
                    jugador.getDatos().aumentarPartidasGanadas();
                    jugador.getDatos().guardar();
                }
                interfaces.get(jugador.getNombre()).dispose();
            }

            if(tablero.getJugadores().get(1).isFantasma()){
                puestos[0] = tablero.getJugadores().get(0).getNombre();
                puestos[1] = tablero.getJugadores().get(1).getNombre();
                menu = new MenuWin(puestos[0],puestos[1]);
            }

            else{
                puestos[1] = tablero.getJugadores().get(0).getNombre();
                puestos[0] = tablero.getJugadores().get(1).getNombre();
                menu = new MenuWin(puestos[1],puestos[0]);
            }
                break;    
               
        }
    }
    /**
     * Desconecta a un jugador del servidor.
     * @param packet El paquete de desconexión del jugador.
     */
    private void desconectarJugador(Packet packet) { 
        if (packet!=null) {
            jugadoresConectados.remove(getJugador(packet.getNombre()));
            packet.enviar(this);
        }
    }
    /**
     * Crea un nuevo jugador y su interfaz gráfica asociada.
     * @param nombre El nombre del jugador.
     * @param direccionIP La dirección IP del jugador.
     * @param puerto El puerto del jugador.
     * @param color El color asignado al jugador.
     * @return El jugador creado.
     */
    private Jugador crearJugador(String nombre, InetAddress direccionIP, int puerto, int color){
        Random random = new Random();
        int numeroAleatorio = random.nextInt(inicio.size());
        int[] casillas = inicio.get(numeroAleatorio);

        InterfazGrafica jugadorGUI = new InterfazGrafica(nombre, tablero);
        int idJugador = id.get(0);
        tablero.agregarSensor(jugadorGUI.getBombermanComponent());
        Jugador jugador = new Jugador(jugadorGUI, tablero, casillas,
            idJugador,nombre,direccionIP,puerto,color);

        jugadorGUI.setLocationRelativeTo(null);

        // Agregar un WindowListener para capturar el evento de cierre de la ventana
        jugadorGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                enviarSalida(nombre,idJugador);
            }
        });
        jugadorGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //jugadorGUI.setVisible(true);


        inicio.remove(numeroAleatorio);
        id.remove(0);
        interfaces.put(nombre, jugadorGUI);
        return jugador;
    }
    /**
     * Envia un paquete de desconexión a todos los clientes.
     * @param nombre El nombre del jugador que se está desconectando.
     * @param id El ID del jugador que se está desconectando.
     */
    private void enviarSalida(String nombre, int id){
        Cliente c = new Cliente();
        Packet01Desconexion desconexion = new Packet01Desconexion(nombre,id);
        desconexion.enviar(c);
    }
    /**
     * Conecta un jugador al servidor.
     * @param jugador El jugador que se está conectando.
     * @param ingreso El paquete de ingreso del jugador.
     */
    public void conectarJugador(Jugador jugador, Packet00Ingreso ingreso){
        boolean yaConectado = false;
        for(Jugador j: jugadoresConectados){
            if(j.getNombre().equalsIgnoreCase(jugador.getNombre()))
                yaConectado = true;
           else
                enviar(ingreso.getDatos(),j.getDireccionIP(),j.getPuerto());
        }
        if(!yaConectado){
            this.jugadoresConectados.add(jugador);
        }
    }
    /**
     * Obtiene la posición de un jugador en la lista de jugadores conectados.
     * @param nombre El nombre del jugador.
     * @return La posición del jugador en la lista.
     */
    public int getPosicion(String nombre){
        int posicion = 0;
        for(Jugador j: jugadoresConectados){
            if(j.getNombre().equals(nombre))
                break;
            posicion++;
        }
        return posicion;
    }
    /**
     * Obtiene un jugador por su nombre.
     * @param nombre El nombre del jugador.
     * @return El jugador correspondiente al nombre especificado.
     */
    public Jugador getJugador(String nombre){
        for(Jugador jugador: jugadoresConectados){
            if(jugador.getNombre().equals(nombre))
                return jugador;
        }
        return null; //Manejar excepcion
    }

    public Jugador getJugador(int id){
        for(Jugador jugador: jugadoresConectados){
            if(jugador.getId() == id)
                return jugador;
        }
        return null;
    }
        /**
     * Recibe un paquete enviado por un cliente.
     * @param datos El arreglo de bytes donde se almacenarán los datos del paquete.
     * @return El paquete recibido.
     */
    public DatagramPacket recibir(byte[] datos){
        DatagramPacket paquete = new DatagramPacket(datos,datos.length);
        try {
            socket.receive(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paquete;
    }
 /**
     * Envía un paquete a un cliente.
     * @param datos Los datos a enviar.
     * @param direccionCliente La dirección IP del cliente.
     * @param puertoCliente El puerto del cliente.
     */
    public void enviar(byte[] datos, InetAddress direccionCliente, int puertoCliente){
        DatagramPacket respuesta = new DatagramPacket(datos, datos.length,direccionCliente,puertoCliente);
        try {
            socket.send(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        running = false;
    }
        /**
     * Envía datos a todos los clientes conectados.
     * @param datos Los datos a enviar.
     */
    public void enviarTodosLosClientes(byte[] datos) {
        for(Jugador jugador: jugadoresConectados){
            enviar(datos,jugador.getDireccionIP(), jugador.getPuerto());
        }
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
    
    public boolean isRunning() {
        return running;
    }
}