package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FXMLRegistrarDocente implements Initializable {

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
    private Label lbErrorDocenteExistente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegistrar.setDisable(true);
        configurarListenerACampos();
    }

    @FXML
    void clicRegistrar(ActionEvent event) {
        if(validarCampos()){
            System.out.println("Paso la prueba");
        }
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

    private boolean validarCampos() {
        boolean esValido = true;

        // Validación del campo de correo electrónico
        String correoElectronico = tfCorreoElectronico.getText().trim();
        if (correoElectronico.isEmpty() || !correoElectronico.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            tfCorreoElectronico.setStyle("-fx-border-color: red;");
            lbErrorCorreo.setText("Correo electrónico no válido.");
            esValido = false;
        } else {
            tfCorreoElectronico.setStyle(null);
            lbErrorCorreo.setText("");
        }

        // Validación del campo de contraseña
        String password = tfPassword.getText().trim();
        if (password.isEmpty() || password.length() < 8) {
            tfPassword.setStyle("-fx-border-color: red;");
            lbErrorPassword.setText("La contraseña debe tener al menos 8 caracteres.");
            esValido = false;
        } else {
            tfPassword.setStyle(null);
            lbErrorPassword.setText("");
        }

        // Validación del campo de número personal
        String numeroPersonal = tfNumeroPersonal.getText().trim();
        if (numeroPersonal.isEmpty() || !numeroPersonal.matches("^\\d{5,10}$")) {
            tfNumeroPersonal.setStyle("-fx-border-color: red;");
            lbErrorNumero.setText("Número personal no válido.");
            esValido = false;
        } else {
            tfNumeroPersonal.setStyle(null);
            lbErrorNumero.setText("");
        }

        // Validación del campo de nombre
        String nombre = tfNombre.getText().trim();
        if (nombre.isEmpty() || !nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜ\\s]{3,40}$")) {
            tfNombre.setStyle("-fx-border-color: red;");
            lbErrorNombre.setText("Nombre no válido.");
            esValido = false;
        } else {
            tfNombre.setStyle(null);
            lbErrorNombre.setText("");
        }

        return esValido;
    }

    private void configurarListenerACampos() {

        ChangeListener<String> cambiosEnCampos = (observable,
                                                  oldValue,
                                                  newValue) -> verificarCamposLlenos();

        tfNombre.textProperty().addListener(cambiosEnCampos);
        tfNumeroPersonal.textProperty().addListener(cambiosEnCampos);
        tfCorreoElectronico.textProperty().addListener(cambiosEnCampos);
        tfPassword.textProperty().addListener(cambiosEnCampos);

    }

    private void verificarCamposLlenos() {

        btnRegistrar.setDisable(
                tfNombre.getText().isEmpty()
                        || tfNombre.getText().isEmpty()
                        || tfNumeroPersonal.getText().isEmpty()
                        || tfCorreoElectronico.getText().isEmpty()
                        || tfPassword.getText().isEmpty());

    }

}
