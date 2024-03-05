package Juego;

import javax.swing.*;

import Juego.Packet.Packet00Ingreso;
import Juego.Packet.Packet03Informacion;
import Juego.Packet.Packet.packet;
import Juego.Personaje.Jugador;
import Juego.Personaje.JugadorMJ;
import Server.*;
import Server.UDP.Cliente;
import Server.UDP.Servidor;
import graficos.MenuLogueo;
import graficos.MenuPrincipal;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;
/*
 * 
El problema que estás experimentando se debe a que la comunicación entre el cliente y el servidor se realiza de manera asíncrona. Cuando intentas obtener coloresDisponibles en el método startClient justo después de iniciar el hilo del cliente, es muy probable que el cliente aún no haya recibido los datos del servidor y, por lo tanto, la lista coloresDisponibles todavía esté vacía.

Para solucionar este problema y asegurarte de que puedes acceder a coloresDisponibles una vez que se hayan recibido los datos del servidor, puedes utilizar un mecanismo de espera, como un semáforo o una barrera de sincronización.

Aquí te muestro cómo puedes hacerlo utilizando un semáforo:

En la clase Bomberman, crea un semáforo y úsalo para esperar a que el cliente haya recibido los datos del servidor antes de intentar acceder a coloresDisponibles:

java
Copy code
import java.util.concurrent.Semaphore;

public class Bomberman {
    private final Semaphore semaforo = new Semaphore(0);

    // Resto del código

    public void startClient(String nombre, int colorSeleccionado){
        Cliente cliente = new Cliente(nombre, colorSeleccionado);
        Thread clienteThread = new Thread(cliente);
        clienteThread.start();

        // Esperar a que el cliente haya recibido los datos del servidor
        try {
            semaforo.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ahora puedes acceder a coloresDisponibles
        ArrayList<Integer> coloresDisponibles = cliente.getColoresDisponibles();
        System.out.println("Tamaño = " + coloresDisponibles.size());
    }

    // Resto del código

    public void setColoresDisponibles(ArrayList<Integer> coloresDisponibles) {
        this.coloresDisponibles = coloresDisponibles;
        // Liberar el semáforo para indicar que los datos han sido recibidos
        semaforo.release();
    }
}
Y en la clase Cliente, llama al método setColoresDisponibles una vez que hayas recibido los datos del servidor:

java
Copy code
public class Cliente implements Runnable {
    private ArrayList<Integer> coloresDisponibles = new ArrayList<>();

    // Resto del código

    private void analizarPacket(byte[] data, InetAddress address, int port) {
        // Resto del código

        case INFORMACION:
            Packet03Informacion info = new Packet03Informacion(data);
            for (char caracter : info.getColores().toCharArray()) {
                this.coloresDisponibles.add(Character.getNumericValue(caracter));
            }
            // Una vez que se han recibido los datos, llamar al método setColoresDisponibles en Bomberman
            bomberman.setColoresDisponibles(this.coloresDisponibles);
            break;

        // Resto del código
    }

    // Resto del código
}
Con este enfoque, el semáforo se utilizará para esperar a que el 
cliente haya recibido los datos del servidor antes de intentar acceder a coloresDisponibles. 
Una vez que los datos se hayan recibido y se haya llamado al método setColoresDisponibles en la clase Cliente, 
el semáforo se liberará, permitiendo que el flujo de ejecución continúe en startClient.
 * 
 * 
 */
@SuppressWarnings("unused")
public class Bomberman{

    private final int TIME_STEP = 30;
    private Timer clockTimer;
    private Servidor socketServidor;
    private int contador_mejoras = 1000*60;
    private static boolean inicio=false;

    public static boolean getInicio() {
        return inicio;
    }

    public static void setInicio(boolean inicio) {
        Bomberman.inicio = inicio;
    }

    public Bomberman() {
    }

    public static void main(String[] args) {
        Bomberman juego = new Bomberman();
        juego.startServer();
        juego.startClient();
        
    }

    public void startServer(){
        this.socketServidor = new Servidor(5000);
        Thread servidorThread = new Thread(socketServidor);
        servidorThread.start();
    }

    public void startClient(){
        Cliente cliente = new Cliente();
        Thread clienteThread = new Thread(cliente);
        clienteThread.start();
    }
 
    public void startGame(Tablero tablero/*, InterfazGrafica GUI*/) {

        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tick(tablero/*,GUI*/);
            }
        };

        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    private void tick(Tablero tablero/*, InterfazGrafica GUI*/) {
        //if (tablero.isGameOver())
        //    SwingUtilities.invokeLater(() -> gameOver(GUI));    
        //gameOver(GUI);
        contador_mejoras -= 30;
        if(contador_mejoras <= 0){
            tablero.generarMejoraAleatoria();
            contador_mejoras = 1000*60;
        }

        tablero.moverEnemigos();
        tablero.avanzarCuentaRegresiva();
        tablero.generarExplosion();
        tablero.aplicarExplosion();
        tablero.informarSensores();
        tablero.choqueconEnemigos();
        for(JugadorMJ jugador: tablero.getJugadores())
            if(jugador.isInmune())
                tablero.reducirTiempoInmunidad();
    }

    private void gameOver(InterfazGrafica GUI){
        clockTimer.stop();
        GUI.dispose();
    }

    public int getTIME_STEP() {
        return this.TIME_STEP;
    }

    public Timer getClockTimer() {
        return this.clockTimer;
    }

    public void setClockTimer(Timer clockTimer) {
        this.clockTimer = clockTimer;
    }
}