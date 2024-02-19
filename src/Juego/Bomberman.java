package Juego;

import javax.swing.*;

import Juego.Packet.Packet00Ingreso;
import Juego.Packet.Packet.packet;
import Juego.Personaje.Jugador;
import Juego.Personaje.JugadorMJ;
import Server.*;
import Server.UDP.Cliente;
import Server.UDP.Servidor;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unused")
public class Bomberman implements Runnable { //Menu con modo ataque, defensa y equilibrado
//hay que extenderlo a multijugador local
// Agregar INPUT HANDLER
    private final int TIME_STEP = 30;
    private Timer clockTimer;
    private Tablero tablero;
    private InterfazGrafica GUI;
    //private boolean running;
    private Servidor socketServidor;
    private Thread gameThread;
    /*private boolean servidor;

	public boolean isServidor() {
        return servidor;
    }
    public void setServidor(boolean servidor) {
        this.servidor = servidor;
    }*/

    public Bomberman(/*int ancho, int alto, int enemigos*/) {
        /*this.tablero = new Tablero(ancho, alto, enemigos);
        this.GUI = new InterfazGrafica("Bomberman", tablero);
        GUI.setLocationRelativeTo(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.agregarSensor(GUI.getBombermanComponent());*/
        //this.servidor = true;
        //this.running = false;
    }
//Al intentar instanciar 2 veces, no se crea en el 2do servidor, sino que ingresa al primero.
//Ni idea si esto es bueno o mallo


    public static void main(String[] args) {
        Bomberman bomberman = new Bomberman();
        if(true)
            bomberman.startServer();

        bomberman.startGameThread(bomberman.socketServidor.getTablero(),bomberman.socketServidor.getGUI());
        bomberman.startClient();
        bomberman.startClient();
        
    }

    public void startServer(){
        Tablero tablero = new Tablero(15,15,15);
        InterfazGrafica GUI = new InterfazGrafica("Bomberman", tablero);
        GUI.setLocationRelativeTo(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.agregarSensor(GUI.getBombermanComponent());
        this.socketServidor = new Servidor(this,5000, tablero,GUI);
        Thread servidorThread = new Thread(socketServidor);
        servidorThread.start();
    }

    public void startClient() {
        Cliente cliente = new Cliente(this);
        Thread clienteThread = new Thread(cliente);
        clienteThread.start();
    }
    
    public synchronized void startGameThread(Tablero tablero, InterfazGrafica GUI) {
        this.gameThread = new Thread(() -> startGame(tablero, GUI));
        gameThread.start();
    }
    
    public void run() {
        startGame(tablero, GUI);
    }
 
    public void startGame(Tablero tablero, InterfazGrafica GUI) {

        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tick(tablero,GUI);
            }
        };

        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    private void tick(Tablero tablero, InterfazGrafica GUI) {
        if (tablero.isGameOver())
            gameOver(GUI);

        tablero.moverEnemigos();
        tablero.avanzarCuentaRegresiva();
        tablero.generarExplosion();
        tablero.aplicarExplosion();
        tablero.informarSensores();

        for(Jugador jugador: tablero.getJugadores())
            jugador.chocaConEnemigo(tablero);
        
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

    public Tablero getTablero() {
        return this.tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public InterfazGrafica getGUI() {
        return this.GUI;
    }

    public void setGUI(InterfazGrafica GUI) {
        this.GUI = GUI;
    }

    public Thread getGameThread() {
        return this.gameThread;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }
}