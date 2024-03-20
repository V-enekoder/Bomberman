package Juego.Personaje;

import Juego.Tablero;

public class Personaje{
    
  protected int SIZE = 30 - 5;
  protected int x;
  protected int y;
  protected int velocidad;
  private static String jugador="ESTATICO";

  public static String getJugador() {
    return jugador;
  }

  protected Personaje(int x, int y){ //Preguntar a la profe por este constructor y su privacidad
    this.x = x;
    this.y = y;
    this.velocidad = this instanceof Jugador ? 4 : 1;
  }

  public enum Movimiento {
    ABAJO(0, 1),
    ARRIBA(0, -1),
    DERECHA(1, 0),
    IZQUIERDA(-1, 0);

    private final int deltaX;
    private final int deltaY;

    Movimiento(final int deltaX, final int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

} 
  public void Movimiento(Movimiento movimiento) {
    y += movimiento.deltaY * velocidad;
    x += movimiento.deltaX * velocidad;
  }

  public void controles(Movimiento control){
    switch (control){
      case ARRIBA:
      jugador="ARRIBA";
      break;
    case ABAJO:
      jugador="ABAJO";
      break;
    case DERECHA:
      jugador="DERECHA";
      break;
    case IZQUIERDA:
      jugador="IZQUIERDA";
      break;   
    }
  }

  public void regresar(Movimiento direccion) {
    switch (direccion) {
      case ABAJO:
        Movimiento(Movimiento.ARRIBA);
        break;
      case ARRIBA:
        Movimiento(Movimiento.ABAJO);
        break;
      case IZQUIERDA:
        Movimiento(Movimiento.DERECHA);
        break;
      case DERECHA:
        Movimiento(Movimiento.IZQUIERDA);
        break;        
    }
  }

  public int getSize() {
    return SIZE;
  }

  public int getX()  {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getUbicacionColumna() {
    return Tablero.transfromarACelda(x);
  }

  public int getUbicacionFila() {
    return Tablero.transfromarACelda(y);
  }
}