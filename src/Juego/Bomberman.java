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


    public Bomberman() {
    }

//Al intentar instanciar 2 veces, no se crea en el 2do servidor, sino que ingresa al primero.
//Ni idea si esto es bueno o mallo

    public static void main(String[] args) {
        Bomberman bomberman = new Bomberman();
        if(!true){
            bomberman.startServer();
            for(int i = 0; i < 1; i++)     
                bomberman.startClient();
        }
        else 
            bomberman.startClient();
        
        bomberman.startGame(bomberman.socketServidor.getTablero(),bomberman.socketServidor.getGUI());
    }

    public void startServer(){
        Tablero tablero = new Tablero(15,15,15);
        InterfazGrafica GUI = new InterfazGrafica("Bomberman", tablero);
        GUI.setLocationRelativeTo(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.agregarSensor(GUI.getBombermanComponent());
        this.socketServidor = new Servidor(this,5000);
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
}