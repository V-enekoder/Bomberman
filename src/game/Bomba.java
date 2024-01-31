package game;

public class Bomba{
    private static final int SIZE = 30;
    private static final int TIEMPO = 100;
    private int tiempoRestante = TIEMPO;
    private int fila;
    private int columna;
    private int radioExplosion;
    private boolean jugadorFuera;

    public Bomba(final int fila, final int columna, int radioExplosion){
        this.fila = fila;
        this.columna = columna;
        this.radioExplosion = radioExplosion;
        jugadorFuera = false;
    }

    public int getFila(){
        return fila;
    }

    public int getColumna(){
        return columna;
    }

    public static int getSIZE(){
        return SIZE;
    }

    public int getTiempoRestante(){
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante){
        this.tiempoRestante = tiempoRestante;
    }

    public int getRadioExplosion(){
        return radioExplosion;
    }

    public boolean estaJugadorFuera(){
        return jugadorFuera;
    }

    public void setJugadorFuera(final boolean jugadorFuera) {
        this.jugadorFuera = jugadorFuera;
    }

    public void reducirTiempoExplosion(){
        tiempoRestante--;
    }
}