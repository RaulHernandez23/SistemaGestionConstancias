package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.lossuperconocidos.sistemagestionconstancias.Inicio;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Utilidades;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FXMLMenuDocente implements Initializable {
    public BorderPane bpSolicitarConstancia;
    public BorderPane bpHistorialConstancia;;
    @FXML
    private BorderPane bpRegistrarDocente;

    @FXML
    private BorderPane bpRegistrarParticipacion;

    @FXML
    private ImageView ivSalir;

    @FXML
    private Label lblNombreUsuario;

    Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void clicSolicitarConstancia(ActionEvent event) {
        System.out.println("botón solicitar constancia");
    }

    @FXML
    void clicConsultarHistorial(ActionEvent event) {
        Stage escenario = (Stage) lblNombreUsuario.getScene().getWindow();
        try {
            escenario.hide();
            FXMLLoader loader = new FXMLLoader(Inicio.class.getResource("FXMLConstancias.fxml"));
            Parent root = loader.load();
            FXMLConstancias controlador = loader.getController();
            controlador.inicializar(usuario);
            Stage nuevoEscenario = new Stage();
            nuevoEscenario.setScene(new Scene(root));
            nuevoEscenario.setTitle("Colaboraciones activas profesor");
            nuevoEscenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEscenario.showAndWait();
            escenario.show();
        } catch (IOException e) {
            escenario.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void clicRegistrarDocente(ActionEvent event) {
        Stage escenario = (Stage) ivSalir.getScene().getWindow();

        Utilidades.inicializarVentana(
                "/org/lossuperconocidos/sistemagestionconstancias/FXMLRegistrarDocente.fxml", false);
    }

    @FXML
    void clicRegistrarParticipacion(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lossuperconocidos/sistemagestionconstancias/FXMLRegistrarParticipacion.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage escenario = new Stage();
            escenario.setScene(scene);
            escenario.setTitle("Registrar participación");
            escenario.show();

            Stage ventanaActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @FXML
    void btnSalir(MouseEvent event) {
        Stage escenario = (Stage) ivSalir.getScene().getWindow();
        escenario.close();
        Utilidades.inicializarVentana(
                "/org/lossuperconocidos/sistemagestionconstancias/FXMLLogIn.fxml", false);

    }

    public void inicializarVentana(Usuario usuario) {
        this.usuario = usuario;
        String mensaje = "Bienvenido "
                + usuario.getNombre() + " "
                + usuario.getApellidoPaterno()
                + (usuario.getApellidoMaterno() != null ? " " + usuario.getApellidoMaterno() : "");

        lblNombreUsuario.setText(mensaje);
        mostrarMenuCorrespondinete();
    }

    private void mostrarMenuCorrespondinete() {
        ArrayList<String> privilegios = usuario.separaTiposUsuarios();
        bpHistorialConstancia.visibleProperty().set(false);
        bpRegistrarDocente.visibleProperty().set(false);
        bpSolicitarConstancia.visibleProperty().set(false);
        bpRegistrarParticipacion.visibleProperty().set(false);
        for (String privilegio : privilegios) {
            if (privilegio.contains(usuario.FILTRO_DOCENTE)) {
                bpHistorialConstancia.visibleProperty().set(true);
                bpSolicitarConstancia.visibleProperty().set(true);
            }
            if (privilegio.contains(usuario.FILTRO_PERSONAL_ADMINISTRATIVO)){
                bpSolicitarConstancia.visibleProperty().set(true);
                bpHistorialConstancia.visibleProperty().set(true);
                bpRegistrarDocente.visibleProperty().set(true);
                bpRegistrarParticipacion.visibleProperty().set(true);
            }
            if (privilegio.contains(usuario.FILTRO_ADMINISTRADOR)){
                bpHistorialConstancia.visibleProperty().set(true);
                bpRegistrarDocente.visibleProperty().set(true);
                bpSolicitarConstancia.visibleProperty().set(true);
                bpRegistrarParticipacion.visibleProperty().set(true);
            }
        }
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
