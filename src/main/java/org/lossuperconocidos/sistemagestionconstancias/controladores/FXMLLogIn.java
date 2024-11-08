package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.lossuperconocidos.sistemagestionconstancias.daos.UsuarioDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Alertas;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Utilidades;

import java.io.IOException;
import java.util.HashMap;

public class FXMLLogIn
{

    @javafx.fxml.FXML
    private PasswordField pfPassword;
    @javafx.fxml.FXML
    private TextField tfNoPersonal;
    @javafx.fxml.FXML
    private Button btnEntrar;

    Alertas alerta = new Alertas();

    @javafx.fxml.FXML
    public void initialize() {

        tfNoPersonal.textProperty().addListener((observable, oldValue, newValue) -> validarCampos());
        pfPassword.textProperty().addListener((observable, oldValue, newValue) -> validarCampos());

    }

    @javafx.fxml.FXML
    public void btnSalir(ActionEvent actionEvent) {
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @javafx.fxml.FXML
    public void actionEntrar(ActionEvent actionEvent) {

        String no_personal = tfNoPersonal.getText();
        String password = pfPassword.getText();

        HashMap<String, Object> respuesta = UsuarioDAO.iniciarSesion(no_personal, password);

        if(!respuesta.containsKey("usuario")) {
            alerta.mostrarAlertaError("Error", respuesta.get("mensaje").toString());
        } else {

            Usuario usuario = (Usuario) respuesta.get("usuario");
            alerta.mostrarAlertaInformacion("Éxito", respuesta.get("mensaje").toString());
            cargarMenuPrincipal(usuario);

        }

    }

    private void validarCampos() {

        boolean camposLlenos = !tfNoPersonal.getText().trim().isEmpty() && !pfPassword.getText().trim().isEmpty();
        btnEntrar.setDisable(!camposLlenos);

    }

    private void configurarCambioFoco(TextField campoActual, TextField siguienteCampo) {
        campoActual.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                siguienteCampo.requestFocus();
            }
        });
    }

    private void cargarMenuPrincipal(Usuario usuario) {
        if (usuario.getTipoUsuario().equals("Administrador")) {
            // Cargar menú principal de administrador
            System.out.println("Menu principal de administrador");
        }
        if (usuario.getTipoUsuario().equals("Docente")) {
            inicializarmenuDocente(usuario);
        }
        if (usuario.getTipoUsuario().equals("Personal administrativo")) {
            // Cargar menú principal de personal administrativo
            System.out.println("Menu principal de personal administrativo");
        }
    }

    private void inicializarmenuDocente(Usuario usuario) {
        Stage escenario = (Stage) btnEntrar.getScene().getWindow();

        try {

            FXMLLoader fxmlLoader = Utilidades.getFXMLLoader(
                    "/org/lossuperconocidos/sistemagestionconstancias/FXMLMenuDocente.fxml");
            Pane vista = fxmlLoader.load();
            Scene escena = new Scene(vista);
            FXMLMenuDocente controlador = fxmlLoader
                    .getController();

            controlador.inicializarVentana(usuario);
            escenario.setScene(escena);
            escenario.setTitle("Menu Estudiante");
            escenario.setResizable(false);
            escenario.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}