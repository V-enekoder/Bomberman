package graficos;

<<<<<<< HEAD
/**
 * Esta clase representa un botón de configuración.
 * Permite definir colores disponibles y no disponibles, así como su posición en la pantalla.
 */
public class BotonConfiguracion {
=======
public class BotonConfiguracion { //Evaluar si convertir en clase interna
>>>>>>> 52241674baf48fec8514baacb23d6aeae859df67
    private String colorDisponible;
    private String colorNoDisponible;
    private int x;

<<<<<<< HEAD
    /**
     * Constructor de la clase BotonConfiguracion.
     * @param colorDisponible El color disponible para el botón.
     * @param colorNoDisponible El color no disponible para el botón.
     * @param x La posición horizontal del botón en la pantalla.
     */
=======
>>>>>>> 52241674baf48fec8514baacb23d6aeae859df67
    public BotonConfiguracion(String colorDisponible, String colorNoDisponible, int x) {
        this.colorDisponible = colorDisponible;
        this.colorNoDisponible = colorNoDisponible;
        this.x = x;
    }

<<<<<<< HEAD
    /**
     * Devuelve el color disponible para el botón.
     * @return El color disponible para el botón.
     */
=======
>>>>>>> 52241674baf48fec8514baacb23d6aeae859df67
    public String getColorDisponible() {
        return colorDisponible;
    }

<<<<<<< HEAD
    /**
     * Devuelve el color no disponible para el botón.
     * @return El color no disponible para el botón.
     */
=======
>>>>>>> 52241674baf48fec8514baacb23d6aeae859df67
    public String getColorNoDisponible() {
        return colorNoDisponible;
    }
    
<<<<<<< HEAD
    /**
     * Devuelve la posición horizontal del botón en la pantalla.
     * @return La posición horizontal del botón.
     */
=======
>>>>>>> 52241674baf48fec8514baacb23d6aeae859df67
    public int getX() {
        return x;
    }
}
