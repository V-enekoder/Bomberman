package Juego;

public class Bomba{
    private static final int SIZE = 30;
    private static final int TIEMPO = 100;
    private int tiempoRestante = TIEMPO;
    private int fila;
    private int columna;
    private int radioExplosion;
    private boolean[] jugadorFuera;
    private int idJugador;

    public Bomba(final int fila, final int columna, int radioExplosion,int idJugador){
        this.fila = fila;
        this.columna = columna;
        this.radioExplosion = radioExplosion;
        this.idJugador = idJugador;
        jugadorFuera = new boolean[4];
        for(int i = 0; i < 4; i++)
            this.jugadorFuera[i] = false;
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

    public boolean estaJugadorFuera(int id){
        return jugadorFuera[id];
    }

    public void setJugadorFuera(boolean valor, int id) {
        jugadorFuera[id] = valor;
    }

    public void reducirTiempoExplosion(){
        tiempoRestante--;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

}