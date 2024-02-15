package Juego;

public class Explosion{

    private int fila;
    private int columna;
    private int duracion = 5;

    public Explosion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return this.fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return this.columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getDuracion() {
        return this.duracion;
    }
    
    public void setDuration(final int duracion) {
        this.duracion = duracion;
    }

    public void reducirDuracion(){
        duracion--;
    }
}
