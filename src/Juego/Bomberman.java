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
public class Bomberman{ //Menu con modo ataque, defensa y equilibrado

    private final int TIME_STEP = 30;
    private Timer clockTimer;
    private Servidor socketServidor;

    public Bomberman() {
    }

    public static void main(String[] args) {
        Bomberman bomberman = new Bomberman();
        if(true){
            bomberman.startServer();
            for(int i = 0; i < 1; i++)     
                bomberman.startClient();
            //bomberman.startGame(bomberman.socketServidor.getTablero(),bomberman.socketServidor.getGUI());
            //Se puede hacer aquí o en el server. preguntar a la profe a ver is hay diferencia
        }
        else 
            bomberman.startClient();
    }

    public void startServer(){
        this.socketServidor = new Servidor(this,5000);
        Thread servidorThread = new Thread(socketServidor);
        servidorThread.start();
    }

    public void startClient() {
        Cliente cliente = new Cliente(this);
        Thread clienteThread = new Thread(cliente);
        clienteThread.start();
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
            SwingUtilities.invokeLater(() -> gameOver(GUI));    
        //gameOver(GUI);

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