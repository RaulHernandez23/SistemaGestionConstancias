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
    public Label lbNombre;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void inicializarContanciaItem(ContanciaItem constanciaItem)
    {
        this.lbPeriodo.setText("Perido" +constanciaItem.periodo);
        this.lbTipo.setText("Tipo" + constanciaItem.tipo);
        try {
            if (constanciaItem.getNombreCompleto() != null && !constanciaItem.getNombreCompleto().isEmpty()) {
                lbNombre.setText(constanciaItem.getNombreCompleto());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
