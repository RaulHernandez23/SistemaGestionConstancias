package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FXMLMenuDocente implements Initializable {

    @FXML
    private ImageView ivSalir;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void hoverInSalir(javafx.scene.input.MouseEvent mouseEvent) {
        ivSalir.setImage(new Image(
                getClass().getResource("/imagenes/logoSalir2.png").toExternalForm()
        ));
    }

    public void hoverOutSalir(javafx.scene.input.MouseEvent mouseEvent) {
        ivSalir.setImage(new Image(
                getClass().getResource("/imagenes/logoSalir.png").toExternalForm()
        ));
    }
}
