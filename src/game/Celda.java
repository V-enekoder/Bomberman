package game;

/**
 *  This enum represents the different types of components the the tablero can have.
 */
public enum Celda 	// Inspector complains on enum not being used, which is not the case.
{
    /**
     * This enum-type represents the PISO on which the AbstractCharacters can Movimiento and drop bombs.
     */
    PISO, 
    /**
     * This enum-type represents the UNBREABLEBLOCKs which serves as a frame and blocks that cannot
     * be destroyed or walked over.
     */
    PARED,
    /**
     * This enum-type represents the BREABLEBLOCKs can be destroyed by bombs and potentially drop powerups.
     */
    BLOQUE
}
