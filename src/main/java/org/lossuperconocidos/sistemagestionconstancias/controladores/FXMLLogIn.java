package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.lossuperconocidos.sistemagestionconstancias.Inicio;

import java.io.IOException;

public class FXMLLogIn
{

    @javafx.fxml.FXML
    private PasswordField pfPassword;
    @javafx.fxml.FXML
    private TextField tfUsuario;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void btnSalir(ActionEvent actionEvent) {
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @javafx.fxml.FXML
    public void btnEntrar(ActionEvent actionEvent) {
        //TODO
        Stage escenario = (Stage) tfUsuario.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(Inicio.class.getResource("FXMLConstancias.fxml"));
        try {
            Parent root = loader.load();

            FXMLConstancias controlador = loader.getController();
            controlador.inicializar();
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Colaboraciones activas profesor");
            escenario.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}