package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.lossuperconocidos.sistemagestionconstancias.daos.DocenteDAO;
import org.lossuperconocidos.sistemagestionconstancias.daos.PeriodoEscolarDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.PeriodoEscolar;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    private ObservableList<String> listaNombres = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        cargarDocentes();
        cargarPeriodos();
        tcAlumnos.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        tvAlumnos.setItems(listaNombres);
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
    public void actionAnadir(ActionEvent actionEvent) {
        String alumno = txtAlumno.getText().trim();

        listaNombres.add(alumno);
        txtAlumno.clear();
    }

    @javafx.fxml.FXML
    public void actionEliminar(ActionEvent actionEvent) {
        String alumno = tvAlumnos.getSelectionModel().getSelectedItem();
        listaNombres.remove(alumno);
    }

    @javafx.fxml.FXML
    public void actionRegistrar(ActionEvent actionEvent) {
        String proyecto = txtProyecto.getText().trim();
        String impacto = txtImpacto.getText().trim();
        String lugar = txtLugar.getText().trim();
        String docente = cbDocentes.getSelectionModel().getSelectedItem().toString();
        String periodoEscolar = cbPeriodos.getSelectionModel().getSelectedItem().toString();
        String alumnos = String.join(", ", listaNombres);

        System.out.println("Proyecto: " + proyecto);
        System.out.println("Impacto: " + impacto);
        System.out.println("Lugar: " + lugar);
        System.out.println("Docente: " + docente);
        System.out.println("Periodo escolar: " + periodoEscolar);
        System.out.println("Alumnos: " + alumnos);

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
                if (listaNombres.isEmpty()) {
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
            if(listaNombres.isEmpty()) {
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
        boolean tablaLlena = !listaNombres.isEmpty();

        btnRegistrar.setDisable(!proyectoValido
                || !impactoValido
                || !lugarValido
                || !tablaLlena
                || !docenteValido
                || !periodoValido);
    }
}
