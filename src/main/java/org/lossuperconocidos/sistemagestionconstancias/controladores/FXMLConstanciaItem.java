package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ContanciaItem;

import java.net.URL;
import java.util.ResourceBundle;


public class FXMLConstanciaItem implements Initializable {

    public Label lbTipo;
    public Label lbPeriodo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void inicializarContanciaItem(ContanciaItem contanciaItem)
    {
        this.lbPeriodo.setText("Perido" +contanciaItem.periodo);
        this.lbTipo.setText("Tipo" + contanciaItem.tipo);
    }
}
