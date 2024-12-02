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
    public Text txtNombre;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void inicializarContanciaItem(ContanciaItem constanciaItem, boolean esProfesor)
    {
        this.lbPeriodo.setText("Perido" +constanciaItem.periodo);
        this.lbTipo.setText("Tipo" + constanciaItem.tipo);
        try {
            if (constanciaItem.getNombreCompleto() != null && !constanciaItem.getNombreCompleto().isEmpty()) {
                if (esProfesor){
                    this.lbNombre.setText("");
                    this.txtNombre.visibleProperty().set(false);
                    return;
                }else {
                    lbNombre.setText(constanciaItem.getNombreCompleto());
                }
            }else {
                lbNombre.setText("");
                this.txtNombre.visibleProperty().set(false);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
