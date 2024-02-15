package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Servidor implements Runnable {
    private final int PUERTO;
    private byte[] datos;
    private DatagramSocket socket;
    private volatile boolean running = true;
    public Servidor(){
        this.PUERTO = 5000;
        this.datos = new byte[1024];
        try {
            this.socket = new DatagramSocket(PUERTO);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(running){

            DatagramPacket recibido = recibir(datos);

            String mensaje = new String(recibido.getData(), 0, recibido.getLength());
            System.out.println("Cliente > " + mensaje);

            datos = "Funcionando".getBytes();
    
            enviar(datos,recibido.getAddress(),recibido.getPort());
        }
        socket.close();
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
}