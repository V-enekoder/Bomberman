package Juego;

import javax.swing.*;

import Juego.Packet.Packet00Ingreso;
import Juego.Packet.Packet03Informacion;
import Juego.Packet.Packet05GameOver;
import Juego.Packet.Packet.packet;
import Juego.Personaje.Jugador;
import Juego.Personaje.Jugador;
import Server.*;
import Server.UDP.Cliente;
import Server.UDP.Servidor;
import graficos.MenuLogueo;
import graficos.MenuPrincipal;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unused")
public class Bomberman{

    private final int TIME_STEP = 30;
    private Timer clockTimer;
    private Servidor socketServidor;
    private int contador_mejoras = 1000*30;
    private boolean notificado;
    
    public Bomberman() {
        notificado = false;
    }

    public static void main(String[] args) {
        Bomberman juego = new Bomberman();
        if(true)
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
 
    public void startGame(Tablero tablero) {
        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tick(tablero);
            }
        };
        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    private void tick(Tablero tablero) {
        if (tablero.isGameOver() && !notificado){
            Cliente c = new Cliente();
            Packet05GameOver resultado = new Packet05GameOver();
            resultado.enviar(c);
            notificado = true;
        }


        contador_mejoras -= 30;
        if(contador_mejoras <= 0){
            tablero.generarMejoraAleatoria();
            contador_mejoras = 1000*30;
        }

        tablero.moverEnemigos();
        tablero.avanzarCuentaRegresiva();
        tablero.generarExplosion();
        tablero.aplicarExplosion();
        tablero.informarSensores();
        tablero.choqueconEnemigos();
        tablero.comprobarVictoria();
        for(Jugador jugador: tablero.getJugadores())
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