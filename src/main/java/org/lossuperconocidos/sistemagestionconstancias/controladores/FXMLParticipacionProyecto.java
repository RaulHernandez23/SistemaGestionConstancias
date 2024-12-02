package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.lossuperconocidos.sistemagestionconstancias.daos.DocenteDAO;
import org.lossuperconocidos.sistemagestionconstancias.daos.ParticipacionDAO;
import org.lossuperconocidos.sistemagestionconstancias.daos.PeriodoEscolarDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.PeriodoEscolar;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Proyecto;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Alertas;

import java.io.IOException;
import java.util.*;

import static org.lossuperconocidos.sistemagestionconstancias.utilidades.Alertas.mostrarAlertaConfirmacion;
import static org.lossuperconocidos.sistemagestionconstancias.utilidades.Constantes.MENSAJE_CANCELAR_PARTICIPACION;

public class FXMLParticipacionProyecto {
    @javafx.fxml.FXML
    private TextField txtAlumno;
    @javafx.fxml.FXML
    private TextField txtProyecto;
    @javafx.fxml.FXML
    private Label lblErrorProyecto;
    @javafx.fxml.FXML
    private Label lblErrorImpacto;
    @javafx.fxml.FXML
    private Label lblErrorLugar;
    @javafx.fxml.FXML
    private ComboBox cbDocentes;
    @javafx.fxml.FXML
    private Label lblErrorAlumno;
    @javafx.fxml.FXML
    private ComboBox cbPeriodos;
    @javafx.fxml.FXML
    private TextField txtLugar;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private TextField txtImpacto;
    @javafx.fxml.FXML
    private TableColumn<String, String> tcAlumnos;
    @javafx.fxml.FXML
    private TableView<String> tvAlumnos;
    @javafx.fxml.FXML
    private Button btnRegistrar;
    @javafx.fxml.FXML
    private Button btnAnadir;
    @javafx.fxml.FXML
    private Label lblErrorDocente;
    @javafx.fxml.FXML
    private Label lblErrorPeriodo;

    public Usuario usuario;

    private ObservableList<String> listaAlumnos = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        cargarDocentes();
        cargarPeriodos();
        tcAlumnos.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        tvAlumnos.setItems(listaAlumnos);
        tvAlumnos.setPlaceholder(new Label("No hay alumnos registrados"));
        btnAnadir.setDisable(true);
        btnEliminar.setDisable(true);
        btnRegistrar.setDisable(true);
        crearListeners();
    }

    @javafx.fxml.FXML
    public void actionCancelar(ActionEvent actionEvent) {
        if (mostrarAlertaConfirmacion("Cancelar registro", MENSAJE_CANCELAR_PARTICIPACION)) {
            cargarMenu();
            cerrarVentana();
        }
    }

    @javafx.fxml.FXML
    public void actionAnadir(ActionEvent actionEvent) {
        String alumno = txtAlumno.getText().trim();

        listaAlumnos.add(alumno);
        txtAlumno.clear();
    }

    @javafx.fxml.FXML
    public void actionEliminar(ActionEvent actionEvent) {
        String alumno = tvAlumnos.getSelectionModel().getSelectedItem();
        listaAlumnos.remove(alumno);
    }

    @javafx.fxml.FXML
    public void actionRegistrar(ActionEvent actionEvent) {
        Proyecto proyecto = leerDatos();

        HashMap<String, Object> resultadoRegistro = ParticipacionDAO.registrarProyecto(proyecto);
        if (!(boolean) resultadoRegistro.get("error")) {
            Alertas.mostrarAlertaInformacion("Registro exitoso", resultadoRegistro.get("mensaje").toString());
            cargarMenu();
            cerrarVentana();
        } else {
            Alertas.mostrarAlertaError("Error al registrar", resultadoRegistro.get("mensaje").toString());
        }
    }

    private void cargarDocentes() {
        HashMap<String, Object> resultadoConsulta = DocenteDAO.recuperarDocentes();
        if (!(boolean) resultadoConsulta.get("error")) {
            List<Usuario> docentes = (List<Usuario>) resultadoConsulta.get("docentes");
            cbDocentes.setItems(FXCollections.observableArrayList(docentes));
        } else {
            Alertas.mostrarAlertaError("Error al cargar docentes", resultadoConsulta.get("mensaje").toString());
        }
    }

    private void cargarPeriodos() {
        HashMap<String, Object> resultadoConsulta = PeriodoEscolarDAO.recuperarPeriodosEscolares();
        if (!(boolean) resultadoConsulta.get("error")) {
            List<PeriodoEscolar> periodos = (List<PeriodoEscolar>) resultadoConsulta.get("periodos");
            cbPeriodos.setItems(FXCollections.observableArrayList(periodos));
        } else {
            Alertas.mostrarAlertaError("Error al cargar periodos", resultadoConsulta.get("mensaje").toString());
        }
    }

    private void crearListeners() {

        cbDocentes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                lblErrorDocente.setText("Seleccione un docente");
                lblErrorDocente.setVisible(true);
            } else {
                lblErrorDocente.setVisible(false);
            }
            evaluarCampos();
        });

        cbPeriodos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                lblErrorPeriodo.setText("Seleccione un periodo escolar");
                lblErrorPeriodo.setVisible(true);
            } else {
                lblErrorPeriodo.setVisible(false);
            }
            evaluarCampos();
        });

        txtAlumno.textProperty().addListener((observable, oldValue, newValue) -> {
            // Activar o desactivar el botón según el texto ingresado
            btnAnadir.setDisable(newValue.trim().isEmpty());
        });

        tvAlumnos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Activar el botón si hay un elemento seleccionado
            btnEliminar.setDisable(newValue == null);
        });

        txtAlumno.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Cuando el campo pierde el enfoque
                String mensajeError = obtenerMensajeError(txtAlumno.getText());
                if (listaAlumnos.isEmpty()) {
                    if (!mensajeError.isEmpty()) { // Si está vacío
                        lblErrorAlumno.setText(mensajeError);
                        lblErrorAlumno.setVisible(true); // Mostrar el Label de error
                    } else {
                        lblErrorAlumno.setVisible(false); // Ocultar el Label si ya no está vacío
                    }
                }
            }
            evaluarCampos();
        });
        txtAlumno.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtAlumno.getText());
            if(listaAlumnos.isEmpty()) {
                if (!mensajeError.isEmpty()) {
                    lblErrorAlumno.setText(mensajeError);
                    lblErrorAlumno.setVisible(true);
                } else {
                    lblErrorAlumno.setVisible(false);
                }
            }
            evaluarCampos();
        });

        txtProyecto.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Cuando el campo pierde el enfoque
                String mensajeError = obtenerMensajeError(txtProyecto.getText());
                if (!mensajeError.isEmpty()) { // Si está vacío
                    lblErrorProyecto.setText(mensajeError);
                    lblErrorProyecto.setVisible(true); // Mostrar el Label de error
                } else {
                    lblErrorProyecto.setVisible(false); // Ocultar el Label si ya no está vacío
                }
            }
            evaluarCampos();
        });
        txtProyecto.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtProyecto.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorProyecto.setText(mensajeError);
                lblErrorProyecto.setVisible(true);
            } else {
                lblErrorProyecto.setVisible(false);
            }
            evaluarCampos();
        });

        txtImpacto.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Cuando el campo pierde el enfoque
                String mensajeError = obtenerMensajeError(txtImpacto.getText());
                if (!mensajeError.isEmpty()) { // Si está vacío
                    lblErrorImpacto.setText(mensajeError);
                    lblErrorImpacto.setVisible(true); // Mostrar el Label de error
                } else {
                    lblErrorImpacto.setVisible(false); // Ocultar el Label si ya no está vacío
                }
            }
            evaluarCampos();
        });
        txtImpacto.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtImpacto.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorImpacto.setText(mensajeError);
                lblErrorImpacto.setVisible(true);
            } else {
                lblErrorImpacto.setVisible(false);
            }
            evaluarCampos();
        });

        txtLugar.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Cuando el campo pierde el enfoque
                String mensajeError = obtenerMensajeError(txtLugar.getText());
                if (!mensajeError.isEmpty()) { // Si está vacío
                    lblErrorLugar.setText(mensajeError);
                    lblErrorLugar.setVisible(true); // Mostrar el Label de error
                } else {
                    lblErrorLugar.setVisible(false); // Ocultar el Label si ya no está vacío
                }
            }
            evaluarCampos();
        });
        txtLugar.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtLugar.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorLugar.setText(mensajeError);
                lblErrorLugar.setVisible(true);
            } else {
                lblErrorLugar.setVisible(false);
            }
            evaluarCampos();
        });
    }

    private String obtenerMensajeError(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "El campo no puede estar vacío.";
        }
        if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            return "El campo no debe contener caracteres especiales.";
        }
        return "";
    }

    private void evaluarCampos() {
        boolean docenteValido = cbDocentes.getSelectionModel().getSelectedItem() != null;
        boolean periodoValido = cbPeriodos.getSelectionModel().getSelectedItem() != null;
        boolean proyectoValido = obtenerMensajeError(txtProyecto.getText()).isEmpty();
        boolean impactoValido = obtenerMensajeError(txtImpacto.getText()).isEmpty();
        boolean lugarValido = obtenerMensajeError(txtLugar.getText()).isEmpty();
        boolean tablaLlena = !listaAlumnos.isEmpty();

        btnRegistrar.setDisable(!proyectoValido
                || !impactoValido
                || !lugarValido
                || !tablaLlena
                || !docenteValido
                || !periodoValido);
    }

    private void cargarMenu() {
        try{
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
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private void cerrarVentana() {
        Stage escenario = (Stage) cbDocentes.getScene().getWindow();
        escenario.close();
    }

    private Proyecto leerDatos() {
        Usuario docente = (Usuario) cbDocentes.getSelectionModel().getSelectedItem();
        String periodo = cbPeriodos.getSelectionModel().getSelectedItem().toString();
        String proyecto = txtProyecto.getText();
        String impacto = txtImpacto.getText();
        String lugar = txtLugar.getText();
        String alumnos = String.join(", ", listaAlumnos);

        return new Proyecto(docente.getNo_personal(), periodo, proyecto, impacto, lugar, alumnos);
    }
}
