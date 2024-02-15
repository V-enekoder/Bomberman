package Server;

import java.io.*;

public class ContadorDeIngresos {
    public static void main(String[] args) {
        String nombreUsuario = "Eldesbaratamala";
        File archivo = new File(nombreUsuario + ".txt");

        int contador = 1;
        if (archivo.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(archivo));
                String linea = reader.readLine();
                if (linea != null) {
                    contador = Integer.parseInt(linea.trim()) + 1;
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Ocurrió un error al leer el archivo.");
                e.printStackTrace();
            }
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(archivo));
            writer.println(contador);
            writer.close();
            System.out.println("El usuario " + nombreUsuario + " ha ingresado " + contador + " veces.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo.");
            e.printStackTrace();
        }
    }
}
