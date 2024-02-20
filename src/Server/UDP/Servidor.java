package Server.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import Juego.Bomberman;
import Juego.InterfazGrafica;
import Juego.Tablero;
import Juego.Packet.Packet;
import Juego.Packet.Packet00Ingreso;
import Juego.Packet.Packet01Desconexion;
import Juego.Packet.Packet.packet;
import Juego.Personaje.JugadorMJ;

public class Servidor implements Runnable{
    private final int PUERTO;
    private byte[] datos;
    private DatagramSocket socket;
    private volatile boolean running = true;
    private List<JugadorMJ> jugadoresConectados = new ArrayList<JugadorMJ>();
    private Bomberman juego;
    private Tablero tablero;
    private InterfazGrafica GUI;
	private static ArrayList<int[]> inicio = new ArrayList<>();
    private static ArrayList<Integer> id = new ArrayList<>();

    static {
        inicio.add(new int[]{60, 60});
        inicio.add(new int[]{60, 540});
        inicio.add(new int[]{540, 60});
        inicio.add(new int[]{540, 540});
        for(int i = 0; i < 4; i++)
            id.add(i);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public InterfazGrafica getGUI() {
        return GUI;
    }

    public void setGUI(InterfazGrafica gUI) {
        GUI = gUI;
    }

    public Servidor(Bomberman juego, int puerto){
        this.PUERTO = puerto;
        this.datos = new byte[1024];
        this.juego = juego;

        this.tablero = new Tablero(15,15,15);
        this.GUI = new InterfazGrafica("Bomberman Principal", tablero);
        GUI.setLocationRelativeTo(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.agregarSensor(GUI.getBombermanComponent());
        
        try {
            this.socket = new DatagramSocket(PUERTO);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Servidor iniciado");
        while(running){
            
            DatagramPacket recibido = recibir(datos);

            analizarPacket(recibido.getData(),recibido.getAddress(),recibido.getPort());
        }
        socket.close();
    }

    private void analizarPacket(byte[] data, InetAddress address, int port) {
        String mensaje = new String(data).trim();
        String nombre;
        int id = Integer.parseInt(mensaje.substring(0, 2));
        packet tipo = Packet.identificarTipo(id);
        Packet packet = null;
        switch(tipo){
            default:
            case INVALIDO:
                break;
            case INGRESO:
                packet = new Packet00Ingreso(data);
                nombre =  ((Packet00Ingreso)packet).getNombre();
                System.out.println("Se ha conectado ["+address.getHostAddress()+" ; "+port+"] "
                    + nombre +" exitosamente");
                JugadorMJ j = crearJugador(nombre, address, port);
                j.getGUI().setVisible(true);
                tablero.agregarJugador(j);

                //Hay que mandar el objeto JugadorMJ en un pakcet;
                conectarJugador(j,(Packet00Ingreso)packet);
                break;
            case DESCONEXION:
                packet = new Packet01Desconexion(data);
                nombre = ((Packet00Ingreso)packet).getNombre();
                System.out.println("Se ha desconectado ["+address.getHostAddress()+" ; "+port+" ] "
                    + nombre);
                tablero.eliminarJugador(nombre);
                desconectarJugador((Packet01Desconexion)packet);
                break;
        }
    }

    private JugadorMJ crearJugador(String nombre, InetAddress direccionIP, int puerto){
        Random random = new Random();
        int numeroAleatorio = random.nextInt(inicio.size());
        int[] casillas = inicio.get(numeroAleatorio);

        InterfazGrafica jugadorGUI = new InterfazGrafica(nombre, tablero);
        jugadorGUI.setLocationRelativeTo(null);
        jugadorGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jugadorGUI.setVisible(true);
        tablero.agregarSensor(jugadorGUI.getBombermanComponent());
        JugadorMJ jugador = new JugadorMJ(jugadorGUI /*GUI*/, tablero, casillas,
            id.get(0),nombre,direccionIP,puerto);

        inicio.remove(numeroAleatorio);
        id.remove(0);
        return jugador;
    }


    public void conectarJugador(JugadorMJ jugador, Packet00Ingreso ingreso){
        boolean yaConectado = false;
        for(JugadorMJ j: jugadoresConectados){
            if(j.getNombre().equalsIgnoreCase(jugador.getNombre())){
                /*if(j.direccionIP == null)
                    j.direccionIP = address;
                if(j.puerto == -1)
                    j.puerto = port;*/
                yaConectado = true;
           }
           else
                enviar(ingreso.getDatos(),j.getDireccionIP(),j.getPuerto());
        }
        if(!yaConectado)
            this.jugadoresConectados.add(jugador);
    }

    public void desconectarJugador(Packet01Desconexion packet) {
        int posicion = getPosicion(packet.getNombre());
        jugadoresConectados.remove(posicion);
        packet.escribirInformacion(this);

    }

    public int getPosicion(String nombre){
        int posicion = 0;
        for(JugadorMJ j: jugadoresConectados){
            if(j.getNombre().equals(nombre))
                break;
            posicion++;
        }
        return posicion;
    }


    public DatagramPacket recibir(byte[] datos){
        DatagramPacket paquete = new DatagramPacket(datos,datos.length);
        try {
            socket.receive(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paquete;
    }

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
    public void enviarTodosLosClientes(byte[] datos) {
        for(JugadorMJ jugador: jugadoresConectados){
            enviar(datos,jugador.getDireccionIP(), jugador.getPuerto());
        }
    }
}