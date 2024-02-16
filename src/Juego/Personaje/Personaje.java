package Juego.Personaje;

import Juego.Tablero;

public abstract class Personaje{
    
  private int SIZE = 30;
  private int x;
  private int y;
  protected int velocidad;

  protected Personaje(int x, int y) {
    this.x = x;
    this.y = y;
    this.velocidad = this instanceof Jugador ? 4 : 1;
  }

  public enum Movimiento{
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

  public void Movimiento(Movimiento Movimiento) {
    y += Movimiento.deltaY * velocidad;
    x += Movimiento.deltaX * velocidad;
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