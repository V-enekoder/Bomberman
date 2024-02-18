package Juego.Personaje;

public class Enemigo extends Personaje{
    private Movimiento direccion;

    public Enemigo(int x, int y, boolean vertical) {
        super(x, y);
        direccion = asignarDireccion(vertical);
    }

    private Movimiento asignarDireccion(boolean vertical){
        int pick = (int) (Math.random() * (Movimiento.values().length-2));
        if(vertical) 
            return Movimiento.values()[pick];
        else
            return Movimiento.values()[pick+2];
    }
    
    public Movimiento getDireccion() {
        return direccion;
    }

    public void cambiarDireccion() {
        
        /*Random random = new Random();
        int numeroAleatorio = random.nextInt(4);
        switch (numeroAleatorio) {
            case 0:
                direccion = Movimiento.ARRIBA;
                break;
            case 1:
                direccion = Movimiento.ABAJO;
                break;
            case 2:
                direccion = Movimiento.DERECHA;
                break;
            case 3:
                direccion = Movimiento.IZQUIERDA;
                break;
        }*/
        switch (direccion) {
            case Movimiento.ABAJO:
                direccion = Movimiento.ARRIBA;
                break;
            case Movimiento.ARRIBA:
                direccion = Movimiento.ABAJO;
                break;
            case Movimiento.IZQUIERDA:
                direccion = Movimiento.DERECHA;
                break;
            case Movimiento.DERECHA:
                direccion = Movimiento.IZQUIERDA;
                break;        
        }
    }
}
