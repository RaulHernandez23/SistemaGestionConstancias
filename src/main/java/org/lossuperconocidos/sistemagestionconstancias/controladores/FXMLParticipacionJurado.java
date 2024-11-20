package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.lossuperconocidos.sistemagestionconstancias.daos.DocenteDAO;
import org.lossuperconocidos.sistemagestionconstancias.daos.PeriodoEscolarDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.PeriodoEscolar;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FXMLParticipacionJurado {
    @javafx.fxml.FXML
    private TextField txtAlumno;
    @javafx.fxml.FXML
    private Label lblErrorModalidad;
    @javafx.fxml.FXML
    private DatePicker dpFecha;
    @javafx.fxml.FXML
    private TextField txtResultado;
    @javafx.fxml.FXML
    private ComboBox cbDocentes;
    @javafx.fxml.FXML
    private Label lblErrorAlumno;
    @javafx.fxml.FXML
    private Label lblErrorTitulo;
    @javafx.fxml.FXML
    private TextField txtModalidad;
    @javafx.fxml.FXML
    private Label lblErrorDocente;
    @javafx.fxml.FXML
    private ComboBox cbPeriodos;
    @javafx.fxml.FXML
    private Label lblErrorResultado;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private TableColumn<String, String> tcAlumnos;
    @javafx.fxml.FXML
    private Button btnAnadir;
    @javafx.fxml.FXML
    private TableView<String> tvAlumnos;
    @javafx.fxml.FXML
    private Button btnRegistrar;
    @javafx.fxml.FXML
    private TextField txtTitulo;
    @javafx.fxml.FXML
    private Label lblErrorPeriodo;
    @javafx.fxml.FXML
    private Label lblErrorFecha;

    private ObservableList<String> listaAlumnos = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        cargarPeriodos();
        cargarDocentes();
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lossuperconocidos/sistemagestionconstancias/FXMLMenuDocente.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage escenario = new Stage();
            escenario.setScene(scene);
            escenario.setTitle("Menú del docente");
            escenario.show();

            Stage ventanaActual = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void actionEliminar(ActionEvent actionEvent) {
        String alumno = tvAlumnos.getSelectionModel().getSelectedItem();
        listaAlumnos.remove(alumno);
        evaluarCampos();
    }

    @javafx.fxml.FXML
    public void actionRegistrar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void actionAnadir(ActionEvent actionEvent) {
        String alumno = txtAlumno.getText().trim();

        listaAlumnos.add(alumno);
        txtAlumno.clear();
    }

    private void cargarDocentes() {
        HashMap<String, Object> resultadoConsulta = DocenteDAO.recuperarDocentes();
        if (!(boolean) resultadoConsulta.get("error")) {
            List<Usuario> docentes = (List<Usuario>) resultadoConsulta.get("docentes");
            cbDocentes.setItems(FXCollections.observableArrayList(docentes));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los docentes");
            alert.setContentText(resultadoConsulta.get("mensaje").toString());
            alert.showAndWait();
        }
    }

    private void cargarPeriodos() {
        HashMap<String, Object> resultadoConsulta = PeriodoEscolarDAO.recuperarPeriodosEscolares();
        if (!(boolean) resultadoConsulta.get("error")) {
            List<PeriodoEscolar> periodos = (List<PeriodoEscolar>) resultadoConsulta.get("periodos");
            cbPeriodos.setItems(FXCollections.observableArrayList(periodos));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar los periodos escolares");
            alert.setContentText(resultadoConsulta.get("mensaje").toString());
            alert.showAndWait();
        }
    }

    private void crearListeners() {
        txtModalidad.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtModalidad.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorModalidad.setText(mensajeError);
                lblErrorModalidad.setVisible(true);
            } else {
                lblErrorModalidad.setVisible(false);
            }
            evaluarCampos();
        });

        txtTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtTitulo.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorTitulo.setText(mensajeError);
                lblErrorTitulo.setVisible(true);
            } else {
                lblErrorTitulo.setVisible(false);
            }
            evaluarCampos();
        });

        txtResultado.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtResultado.getText());
            if(!mensajeError.isEmpty()) {
                lblErrorResultado.setText(mensajeError);
                lblErrorResultado.setVisible(true);
            } else {
                lblErrorResultado.setVisible(false);
            }
            evaluarCampos();
        });

        txtAlumno.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtAlumno.getText());
            if(listaAlumnos.isEmpty()) {
                if(!mensajeError.isEmpty()) {
                    lblErrorAlumno.setText(mensajeError);
                    lblErrorAlumno.setVisible(true);
                } else {
                    lblErrorAlumno.setVisible(false);
                }
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
        boolean tituloValido = obtenerMensajeError(txtTitulo.getText()).isEmpty();
        boolean modalidadValida = obtenerMensajeError(txtModalidad.getText()).isEmpty();
        boolean resultadoValido = obtenerMensajeError(txtResultado.getText()).isEmpty();
        boolean tablaLlena = !listaAlumnos.isEmpty();

        btnRegistrar.setDisable(!docenteValido
                || !periodoValido
                || !tituloValido
                || !modalidadValida
                || !resultadoValido
                || !tablaLlena);
    }
}
