package org.lossuperconocidos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

//TODO: LOS METODOS QUE HAYA EN DAO Y MODELS
public class tests {
    @Test
    public void getFechaInicio_retornaFecha() {
        //Arrage
        Participacion_test participacion = new Participacion_test();
        //Act

        //assert
        //NullPointerException exception = assertThrows(NullPointerException.class, participacion::getFechaInicio);
        assertThrowsExactly(NullPointerException.class, participacion::getFechaInicio);


    }
}
