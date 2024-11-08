package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Utilidades;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMenuDocente implements Initializable {
    @FXML
    private BorderPane bpRegistrarDocente;

    @FXML
    private BorderPane bpRegistrarParticipacion;

    @FXML
    private ImageView ivSalir;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void clicSolicitarConstancia(ActionEvent event) {
        System.out.println("botón solicitar constancia");
    }

    @FXML
    void clicConsultarHistorial(ActionEvent event) {
        System.out.println("botón consultar historial");
    }

    @FXML
    void clicRegistrarDocente(ActionEvent event) {
        Stage escenario = (Stage) ivSalir.getScene().getWindow();

        Utilidades.inicializarVentana(
                "/org/lossuperconocidos/sistemagestionconstancias/FXMLRegistrarDocente.fxml", false);
    }

    @FXML
    void clicRegistrarParticipacion(ActionEvent event) {
        System.out.println("botón registrar participación");
    }

    @FXML
    void btnSalir(MouseEvent event) {
        Stage escenario = (Stage) ivSalir.getScene().getWindow();
        escenario.close();
        Utilidades.inicializarVentana(
                "/org/lossuperconocidos/sistemagestionconstancias/FXMLLogIn.fxml", false);

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
