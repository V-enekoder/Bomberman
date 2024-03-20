package Juego;

/**
 * Representa una bomba en el juego Bomberman.
 */
public class Bomba{
    private static final int SIZE = 30; // Tamaño de la bomba
    private static final int TIEMPO = 100; // Tiempo antes de que explote la bomba
    private int tiempoRestante = TIEMPO; // Tiempo restante para que explote la bomba
    private int fila; // Fila en la que se encuentra la bomba
    private int columna; // Columna en la que se encuentra la bomba
    private int radioExplosion; // Radio de explosión de la bomba
    private boolean[] jugadorFuera; // Array que indica si cada jugador ha sido expulsado por la explosión de la bomba
    private int idJugador; // Identificador del jugador que colocó la bomba

    /**
     * Constructor de la clase Bomba.
     * 
     * @param fila            La fila en la que se coloca la bomba.
     * @param columna         La columna en la que se coloca la bomba.
     * @param radioExplosion  El radio de explosión de la bomba.
     * @param idJugador       El identificador del jugador que colocó la bomba.
     */
    public Bomba(final int fila, final int columna, int radioExplosion, int idJugador){
        this.fila = fila;
        this.columna = columna;
        this.radioExplosion = radioExplosion;
        this.idJugador = idJugador;
        jugadorFuera = new boolean[4];
        for(int i = 0; i < 4; i++)
            this.jugadorFuera[i] = false;
    }

    /**
     * Obtiene la fila en la que se encuentra la bomba.
     * 
     * @return La fila de la bomba.
     */
    public int getFila(){
        return fila;
    }

    /**
     * Obtiene la columna en la que se encuentra la bomba.
     * 
     * @return La columna de la bomba.
     */
    public int getColumna(){
        return columna;
    }

    /**
     * Obtiene el tamaño de la bomba.
     * 
     * @return El tamaño de la bomba.
     */
    public static int getSIZE(){
        return SIZE;
    }

    /**
     * Obtiene el tiempo restante para que explote la bomba.
     * 
     * @return El tiempo restante de la bomba.
     */
    public int getTiempoRestante(){
        return tiempoRestante;
    }

    /**
     * Establece el tiempo restante para que explote la bomba.
     * 
     * @param tiempoRestante El tiempo restante de la bomba.
     */
    public void setTiempoRestante(int tiempoRestante){
        this.tiempoRestante = tiempoRestante;
    }

    /**
     * Obtiene el radio de explosión de la bomba.
     * 
     * @return El radio de explosión de la bomba.
     */
    public int getRadioExplosion(){
        return radioExplosion;
    }

    /**
     * Verifica si un jugador ha sido expulsado por la explosión de la bomba.
     * 
     * @param id El identificador del jugador.
     * @return true si el jugador ha sido expulsado, false de lo contrario.
     */
    public boolean estaJugadorFuera(int id){
        return jugadorFuera[id];
    }

    /**
     * Establece si un jugador ha sido expulsado por la explosión de la bomba.
     * 
     * @param valor true si el jugador ha sido expulsado, false de lo contrario.
     * @param id    El identificador del jugador.
     */
    public void setJugadorFuera(boolean valor, int id) {
        jugadorFuera[id] = valor;
    }

    /**
     * Reduce el tiempo restante para que explote la bomba.
     */
    public void reducirTiempoExplosion(){
        tiempoRestante--;
    }

    /**
     * Obtiene el identificador del jugador que colocó la bomba.
     * 
     * @return El identificador del jugador.
     */
    public int getIdJugador() {
        return idJugador;
    }

    /**
     * Establece el identificador del jugador que colocó la bomba.
     * 
     * @param idJugador El identificador del jugador.
     */
    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }
}