package Server.UDP;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import graficos.*;


public class Cliente implements Runnable {

    private final int PUERTO_SERVIDOR;
    private byte[] datos;
    private DatagramSocket socket;
    private InetAddress direccionServidor;
    private volatile boolean running = true;
    private ArrayList<Integer> coloresDisponibles = new ArrayList<>();
    private String nombre;
    private int color;

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
    @Override
    public void run() {
        MenuPrincipal v1 = new MenuPrincipal();
        
       // Espera hasta que la selección haya ocurrido
       while (!MenuBatalla.isSeleccion()) {
            try {
                Thread.sleep(1000); // Espera 1 segundo antes de verificar nuevamente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Packet00Ingreso ingreso = new Packet00Ingreso(MenuLogueo.getTextoIngresado(),MenuBatalla.getColorSeleccionado());
        ingreso.enviar(this);
        
        while(running){
            DatagramPacket recibido = recibir();
            analizarPacket(recibido.getData(),recibido.getAddress(),recibido.getPort());
        }
        socket.close();
    }

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
                System.out.println(ingreso.getNombre() +" ha ingresado");
                break;
            case DESCONEXION:
                Packet01Desconexion desconexion = new Packet01Desconexion(data);
                    
            System.out.println("["+address.getHostAddress()+" ; "+port+" ] "
                + desconexion.getNombre() + " se ha ido");
                break;
            case DERROTA:
                Packet02Derrota derrota = new Packet02Derrota(data);
                nombre = derrota.getNombre();
                System.out.println(nombre + " ha perdido. ["+address.getHostAddress()+" ; "+port+"]");
                break;
            case INFORMACION:
                Packet03Informacion info = new Packet03Informacion(data);
                for (char caracter : info.getColores().toCharArray())
                    this.coloresDisponibles.add(Character.getNumericValue(caracter));
                break;
        }
    }

    public ArrayList<Integer> getColoresDisponibles() {
        return this.coloresDisponibles;
    }

    public void setColoresDisponibles(ArrayList<Integer> coloresDisponibles) {
        this.coloresDisponibles = coloresDisponibles;
    }

    public DatagramPacket recibir(){
        DatagramPacket paquete = new DatagramPacket(datos,datos.length);
        try {
            socket.receive(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paquete;
    }

    public void enviarServidor(byte[] datos){
        DatagramPacket respuesta = new DatagramPacket(datos, datos.length,direccionServidor,PUERTO_SERVIDOR);
        try {
            socket.send(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        running = false;
    }
}

