package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.lossuperconocidos.sistemagestionconstancias.daos.DocenteDAO;
import org.lossuperconocidos.sistemagestionconstancias.daos.UsuarioDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Categoria;
import org.lossuperconocidos.sistemagestionconstancias.modelos.TipoContratacion;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Alertas;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Constantes;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FXMLRegistrarDocente implements Initializable {

    @FXML
    private Label lbErrorCorreo;
    @FXML
    private ComboBox<Categoria> cbCategoria;
    @FXML
    private TextField tfCorreoElectronico;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private ComboBox<TipoContratacion> cbTipoContratacion;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorAPaterno;
    @FXML
    private Label lbErrorAMaterno;
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
        cargarCategorias();
        cargarTiposContratacion();
    }

    @FXML
    void clicRegistrar(ActionEvent event) {
        if(validarCampos()){
            registrarDocente();
        }
    }

    @FXML
    void clicCancelar(ActionEvent event) {
        boolean confirmacion = Alertas.mostrarAlertaConfirmacion("Advertencia",
                        "¿Estas seguro de cancelar el registro? ");
        if (confirmacion) {
            cerrarVentana();
        }
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

        // Validación del campo de apellido paterno
        String apellidoPaterno = tfApellidoPaterno.getText().trim();
        if (apellidoPaterno.isEmpty() || !apellidoPaterno.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜ\\s]{3,40}$")) {
            tfApellidoPaterno.setStyle("-fx-border-color: red;");
            lbErrorAPaterno.setText("Apellido paterno no válido.");
            esValido = false;
        } else {
            tfApellidoPaterno.setStyle(null);
            lbErrorAPaterno.setText("");
        }

        // Validación del campo de apellido materno (opcional)
        String apellidoMaterno = tfApellidoMaterno.getText().trim();
        if (!apellidoMaterno.isEmpty() && !apellidoMaterno.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜ\\s]{0,40}$")) {
            tfApellidoMaterno.setStyle("-fx-border-color: red;");
            lbErrorAMaterno.setText("Apellido materno no válido.");
            esValido = false;
        } else {
            tfApellidoMaterno.setStyle(null);
            lbErrorAMaterno.setText("");
        }

        return esValido;
    }

    private void configurarListenerACampos() {

        ChangeListener<String> cambiosEnCampos = (observable,
                                                  oldValue,
                                                  newValue) -> verificarCamposLlenos();

        tfNombre.textProperty().addListener(cambiosEnCampos);
        tfApellidoPaterno.textProperty().addListener(cambiosEnCampos);
        tfNumeroPersonal.textProperty().addListener(cambiosEnCampos);
        tfCorreoElectronico.textProperty().addListener(cambiosEnCampos);
        tfPassword.textProperty().addListener(cambiosEnCampos);

        // Listener para el ComboBox de categoría
        cbCategoria.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            verificarCamposLlenos();
        });

        // Listener para el ComboBox de tipo de contratación
        cbTipoContratacion.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            verificarCamposLlenos();
        });
    }

    private void verificarCamposLlenos() {
        boolean camposVacios = tfNombre.getText().isEmpty()
                || tfApellidoPaterno.getText().isEmpty()
                || tfNumeroPersonal.getText().isEmpty()
                || tfCorreoElectronico.getText().isEmpty()
                || tfPassword.getText().isEmpty();

        boolean categoriaNoSeleccionada = cbCategoria.getSelectionModel().getSelectedIndex() == 0;
        boolean tipoContratacionNoSeleccionado = cbTipoContratacion.getSelectionModel().getSelectedIndex() == 0;

        btnRegistrar.setDisable(camposVacios || categoriaNoSeleccionada || tipoContratacionNoSeleccionado);
    }
    //Metodo Incompleto
    private void registrarDocente(){
        Usuario docente = new Usuario();
        Categoria categoriaSeleccionada = cbCategoria.getSelectionModel().getSelectedItem();
        TipoContratacion tipoContratacionSeleccionada = cbTipoContratacion.getSelectionModel().getSelectedItem();

        docente.setNombre(tfNombre.getText());
        docente.setApellidoPaterno(tfApellidoPaterno.getText());
        docente.setApellidoMaterno(tfApellidoMaterno.getText());
        docente.setCorreoElectronico(tfCorreoElectronico.getText());
        docente.setNo_personal(tfNumeroPersonal.getText());
        docente.setContrasena(tfPassword.getText());
        docente.setIdCategoria(categoriaSeleccionada.getIdCategoria());
        docente.setIdTipoContratacion(tipoContratacionSeleccionada.getIdTipoContratacion());

        try{
            HashMap<String, Object> respuesta = DocenteDAO.registrarDocente(docente);
            if (!(Boolean) respuesta.get("error")) {

                Alertas.mostrarAlertaInformacion("Registro exitoso",
                        (String) respuesta.get("mensaje"));
            } else {

                Alertas.mostrarAlertaError("Error en el registro",
                        (String) respuesta.get("mensaje"));

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void cargarCategorias() {
        try {
            HashMap<String, Object> respuestaCategorias = UsuarioDAO.consultarCategorias();

            if (!(Boolean) respuestaCategorias.get("error")) {
                ArrayList<Categoria> listaCategorias = (ArrayList<Categoria>) respuestaCategorias.get("categorias");
                ObservableList<Categoria> categorias = FXCollections.observableArrayList();
                
                Categoria ningunaCategoriaSeleccionada = new Categoria();
                ningunaCategoriaSeleccionada.setNombreCategoria("Seleccione una categoría");
                ningunaCategoriaSeleccionada.setIdCategoria(null);
                categorias.add(ningunaCategoriaSeleccionada);
                categorias.addAll(listaCategorias);

                cbCategoria.setItems(categorias);
                cbCategoria.getSelectionModel().select(ningunaCategoriaSeleccionada);
            } else {
                Alertas.mostrarAlertaError("Error de conexión", Constantes.MENSAJE_ERROR_DE_CONEXION);
            }
        } catch (Exception e) {
            Alertas.mostrarAlertaError("Error de conexión", Constantes.MENSAJE_ERROR_DE_CONEXION);
        }
    }

    private void cargarTiposContratacion() {
        try {
            HashMap<String, Object> respuestaTiposContratacion = UsuarioDAO.consultarTiposContratacion();

            if (!(Boolean) respuestaTiposContratacion.get("error")) {
                ArrayList<TipoContratacion> listaTiposContratacion =
                        (ArrayList<TipoContratacion>) respuestaTiposContratacion.get("tiposContratacion");

                ObservableList<TipoContratacion> tiposContratacion = FXCollections.observableArrayList();

                TipoContratacion ningunTipoSeleccionado = new TipoContratacion();
                ningunTipoSeleccionado.setNombreTipoContratacion("Seleccione un tipo de contratación");
                ningunTipoSeleccionado.setIdTipoContratacion(null);
                tiposContratacion.add(ningunTipoSeleccionado);

                tiposContratacion.addAll(listaTiposContratacion);

                cbTipoContratacion.setItems(tiposContratacion);
                cbTipoContratacion.getSelectionModel().select(ningunTipoSeleccionado);
            } else {
                Alertas.mostrarAlertaError("Error de conexión", Constantes.MENSAJE_ERROR_DE_CONEXION);
            }
        } catch (Exception e) {
            Alertas.mostrarAlertaError("Error de conexión", Constantes.MENSAJE_ERROR_DE_CONEXION);
        }
    }
}
