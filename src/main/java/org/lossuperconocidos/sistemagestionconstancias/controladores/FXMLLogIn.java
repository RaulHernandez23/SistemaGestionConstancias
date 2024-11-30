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
import java.util.ArrayList;
import java.util.Arrays;
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
    private static final String SEPARADOR_TIPO_USUARIO = ",";
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

    //FIXME: Tipo usuario ahora es un string con los distintos tipos separador por ','
    private void cargarMenuPrincipal(Usuario usuario) {
        ArrayList<String> privilegios = separaTiposUsuarios(usuario.getTipoUsuario());
        if (privilegios.isEmpty()) throw new RuntimeException("No tiene tipo de usuario");
        inicializarmenuDocente(usuario);

    }
    private ArrayList<String> separaTiposUsuarios(String tipoUsuario){
        ArrayList<String> tipoUsuarioList = new ArrayList<>();
        if (tipoUsuario.contains(SEPARADOR_TIPO_USUARIO)) {
            tipoUsuarioList = new ArrayList<>(Arrays.asList(tipoUsuario.split(SEPARADOR_TIPO_USUARIO)));
        } else {
            tipoUsuarioList = new ArrayList<>();
            tipoUsuarioList.add(tipoUsuario);
        }
        return tipoUsuarioList;
    }

    private void inicializarmenuDocente(Usuario usuario ) {
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