package game;

public class Explosion{

    private int fila;
    private int columna;
    private int duracion = 5;
    private boolean[] jugadorFuera;

    public Explosion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        jugadorFuera = new boolean[2];
        for(int i = 0; i < 2; i++)
            this.jugadorFuera[i] = false;
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

    public boolean estaJugadorFuera(int id){
        return jugadorFuera[id];
    }

    public void setJugadorFuera(boolean valor, int id) {
        jugadorFuera[id] = valor;
    }

}
