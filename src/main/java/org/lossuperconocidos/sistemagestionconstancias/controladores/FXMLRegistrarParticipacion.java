package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;

import java.io.IOException;

public class FXMLRegistrarParticipacion
{
    public Usuario usuario;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void actionCancelar(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lossuperconocidos/sistemagestionconstancias/FXMLMenuDocente.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage escenario = new Stage();
            escenario.setScene(scene);
            escenario.setTitle("Menú del docente");
            escenario.initStyle(StageStyle.UNDECORATED);
            escenario.setResizable(false);
            FXMLMenuDocente controlador = loader.getController();
            controlador.inicializarVentana(usuario);

            escenario.show();

            Stage ventanaActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void actionProyecto(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lossuperconocidos/sistemagestionconstancias/FXMLParticipacionProyecto.fxml"));
            Scene scene = new Scene(loader.load());

            Stage escenario = new Stage();
            escenario.setScene(scene);
            escenario.setTitle("Registrar participacion:  Proyecto de campo");
            escenario.initStyle(StageStyle.UNDECORATED);
            escenario.setResizable(false);
            FXMLParticipacionProyecto controlador = loader.getController();
            controlador.usuario = usuario;

            escenario.show();

            Stage ventanaActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException ioEx){
            ioEx.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void actionPladea(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lossuperconocidos/sistemagestionconstancias/FXMLParticipacionPladea.fxml"));
            Scene scene = new Scene(loader.load());

            Stage escenario = new Stage();
            escenario.setScene(scene);
            escenario.setTitle("Registrar participacion: Jurado");
            escenario.initStyle(StageStyle.UNDECORATED);
            escenario.setResizable(false);
            FXMLParticipacionPladea controlador = loader.getController();
            controlador.usuario = usuario;

            escenario.show();

            Stage ventanaActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void actionImparticion(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lossuperconocidos/sistemagestionconstancias/FXMLParticipacionImparticion.fxml"));
            Scene scene = new Scene(loader.load());

            Stage escenario = new Stage();
            escenario.setScene(scene);
            escenario.setTitle("Registrar participacion: Imparticion de experiencia educativa");
            escenario.initStyle(StageStyle.UNDECORATED);
            escenario.setResizable(false);
            FXMLParticipacionImparticion controlador = loader.getController();
            controlador.usuario = usuario;

            escenario.show();

            Stage ventanaActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void actionJurado(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lossuperconocidos/sistemagestionconstancias/FXMLParticipacionJurado.fxml"));
            Scene scene = new Scene(loader.load());

            Stage escenario = new Stage();
            escenario.setScene(scene);
            escenario.setTitle("Registrar participacion: Jurado");
            escenario.initStyle(StageStyle.UNDECORATED);
            escenario.setResizable(false);
            FXMLParticipacionJurado controlador = loader.getController();
            controlador.usuario = usuario;

            escenario.show();

            Stage ventanaActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}