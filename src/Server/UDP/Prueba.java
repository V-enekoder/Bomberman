package Server.UDP;

import java.util.ArrayList;

public class Prueba { 
    public static void main(String[] args) {
         ArrayList<Integer> coloresDisponibles = new ArrayList<>();
         for(int i = 0; i < 5; i++)
            coloresDisponibles.add(i);
        String mensaje = "03";

        for(Integer color: coloresDisponibles)
            mensaje += String.valueOf(color);
        
        System.out.println(mensaje);
    }
}
