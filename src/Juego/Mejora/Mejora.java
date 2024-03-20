package Juego.Mejora;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Juego.Personaje.Jugador;

/**
 * La clase abstracta Mejora representa una mejora en el juego Bomberman que puede ser recogida por un jugador.
 * Cada mejora tiene una posición en el tablero y un nombre asociado.
 */
public abstract class Mejora {  

    protected final int SIZE = 30;
    protected int fila;
    protected int columna;
    protected String nombre;

    /**
     * Crea una nueva instancia de una mejora en la posición especificada en el tablero.
     * 
     * @param fila La fila en la que se encuentra la mejora.
     * @param columna La columna en la que se encuentra la mejora.
     */
    public Mejora(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Método abstracto para agregar la mejora a un jugador cuando este la recoge.
     * 
     * @param jugador El jugador que recoge la mejora.
     */
    public abstract void agregarMejora(Jugador jugador);

    /**
     * Obtiene el nombre de la mejora.
     * 
     * @return El nombre de la mejora.
     */
    public abstract String getNombre();
    
    /**
     * Obtiene el tamaño de la mejora (tamaño del icono en píxeles).
     * 
     * @return El tamaño de la mejora.
     */
    public int getPowerupSize() {
        return SIZE;
    }

    /**
     * Obtiene la fila en la que se encuentra la mejora en el tablero.
     * 
     * @return La fila de la mejora.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la columna en la que se encuentra la mejora en el tablero.
     * 
     * @return La columna de la mejora.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Reproduce un sonido asociado a la mejora.
     * 
     * @param ruta La ruta del archivo de audio a reproducir.
     */
    protected void reproducirSonido(String ruta) {
        try {
            File archivoSonido = new File(ruta);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
