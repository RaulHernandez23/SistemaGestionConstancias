package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLRegistrarParticipacion
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void actionCancelar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void actionProyecto(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lossuperconocidos/sistemagestionconstancias/FXMLParticipacionProyecto.fxml"));
            Scene scene = new Scene(loader.load());

            Stage escenario = new Stage();
            escenario.setScene(scene);
            escenario.setTitle("Registrar participacion:  Proyecto de campo");
            escenario.show();
        } catch (IOException ioEx){
            ioEx.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void actionPladea(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void actionImparticion(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void actionJurado(ActionEvent actionEvent) {
    }
}