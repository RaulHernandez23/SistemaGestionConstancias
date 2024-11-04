package org.lossuperconocidos.sistemagestionconstancias.utilidades;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import org.lossuperconocidos.sistemagestionconstancias.controladores.FXMLConstanciaItem;


//TODO: Deberia recibir en el contructor el modelo de contancias
public class ListaConstancias extends ListCell<AnchorPane> {
    private AnchorPane root;

    public ListaConstancias() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLConstanciasItem.fxml"));
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(AnchorPane item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            setGraphic(root);
        }
    }
}