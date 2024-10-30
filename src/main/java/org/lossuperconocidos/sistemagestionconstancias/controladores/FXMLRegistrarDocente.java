package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.HashMap;

import java.awt.*;

public class FXMLRegistrarDocente {

    @FXML
    private Label lbErrorCorreo;

    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    private TextField tfCorreoElectronico;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfNumeroPersonal;

    @FXML
    private TextField tfNombre;

    @FXML
    private ComboBox<String> cbTipoContratación;

    @FXML
    private Label lbErrorNombre;

    @FXML
    private Button btnCancelar;

    @FXML
    private Label lbErrorPassword;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Label lbErrorNumero;

    @FXML
    void clicRegistrar(ActionEvent event) {

    }

    @FXML
    void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @javafx.fxml.FXML
    public void btnSalir(ActionEvent actionEvent) {
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void cerrarVentana() {
        Stage escenario = (Stage) tfNombre.getScene().getWindow();
        escenario.close();
    }

}
