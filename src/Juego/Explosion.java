package Juego;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * La clase Explosion representa una explosión en el juego.
 * Tiene métodos para reproducir un sonido de explosión y reducir la duración de la explosión.
 */
public class Explosion{

    private int fila;       // Fila en la que ocurrió la explosión
    private int columna;    // Columna en la que ocurrió la explosión
    private int duracion;   // Duración restante de la explosión

    /**
     * Constructor de la clase Explosion.
     * @param fila La fila en la que ocurrió la explosión.
     * @param columna La columna en la que ocurrió la explosión.
     */
    public Explosion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.duracion = 5;  // Duración predeterminada de la explosión
    }

    /**
     * Obtiene la fila en la que ocurrió la explosión.
     * @return La fila de la explosión.
     */
    public int getFila() {
        return this.fila;
    }

    /**
     * Establece la fila en la que ocurrió la explosión.
     * @param fila La fila de la explosión.
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * Obtiene la columna en la que ocurrió la explosión.
     * @return La columna de la explosión.
     */
    public int getColumna() {
        return this.columna;
    }

    /**
     * Establece la columna en la que ocurrió la explosión.
     * @param columna La columna de la explosión.
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * Obtiene la duración restante de la explosión.
     * @return La duración restante de la explosión.
     */
    public int getDuracion() {
        return this.duracion;
    }
    
    /**
     * Establece la duración de la explosión.
     * @param duracion La duración de la explosión.
     */
    public void setDuration(final int duracion) {
        this.duracion = duracion;
    }

    /**
     * Reduce la duración de la explosión en 1 unidad.
     * Si la duración alcanza 0, reproduce un sonido de explosión.
     */
    public void reducirDuracion(){
        duracion--;
        if (duracion <= 0) {
            reproducirSonido("audio6.wav");  // Reproduce el sonido de explosión cuando la duración es cero
        }
    }

    /**
     * Reproduce un sonido de explosión utilizando un archivo de audio especificado por la ruta.
     * @param ruta La ruta del archivo de sonido de explosión.
     */
    
    private void reproducirSonido(String ruta) {
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
     /*private void reproducirSonido(String ruta) {
        try {
            File archivoSonido = new File(ruta);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(archivoSonido));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
