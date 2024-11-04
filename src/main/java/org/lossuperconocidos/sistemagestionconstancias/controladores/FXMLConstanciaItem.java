package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class FXMLConstanciaItem implements Initializable {
    public Text lbFecha;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void inicializarContanciaItem(String fecha){
        this.lbFecha.setText("Fecha" + fecha);
    }
}
