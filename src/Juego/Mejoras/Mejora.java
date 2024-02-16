package Juego.Mejoras;

import Juego.Personaje.Jugador;

public abstract class Mejora{  //Vida extra, mas velocidad y destruir pared

    protected final int SIZE = 30;
    protected int fila;
    protected int columna;
    protected String nombre;

    public Mejora(int fila, int columna) {
	    this.fila = fila;
    	this.columna = columna;
    }

    public abstract void agregarMejora(Jugador jugador);
    public abstract String getNombre();
    
    public int getPowerupSize() {
	    return SIZE;
    }

    public int getFila() {
	    return fila;
    }
    public int getColumna() {
	    return columna;
    }
}
