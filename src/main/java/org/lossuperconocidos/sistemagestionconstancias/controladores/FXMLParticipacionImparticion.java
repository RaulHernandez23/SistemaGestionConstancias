package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.collections.FXCollections;
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

public class FXMLParticipacionImparticion {
    @javafx.fxml.FXML
    private Label lblErrorMeses;
    @javafx.fxml.FXML
    private Label lblErrorBloque;
    @javafx.fxml.FXML
    private Label lblErrorSemanas;
    @javafx.fxml.FXML
    private TextField txtBloque;
    @javafx.fxml.FXML
    private Spinner spinSeccion;
    @javafx.fxml.FXML
    private Spinner spinSemanas;
    @javafx.fxml.FXML
    private Label lblErrorExperiencia;
    @javafx.fxml.FXML
    private ComboBox cbDocentes;
    @javafx.fxml.FXML
    private TextField txtExperienciaEducativa;
    @javafx.fxml.FXML
    private Label lblErrorPrograma;
    @javafx.fxml.FXML
    private Label lblErrorDocente;
    @javafx.fxml.FXML
    private ComboBox cbPeriodos;
    @javafx.fxml.FXML
    private Spinner spinHoras;
    @javafx.fxml.FXML
    private ComboBox cbPrograma;
    @javafx.fxml.FXML
    private Spinner spinMeses;
    @javafx.fxml.FXML
    private Label lblErrorSeccion;
    @javafx.fxml.FXML
    private Spinner spinCreditos;
    @javafx.fxml.FXML
    private Button btnRegistrar;
    @javafx.fxml.FXML
    private Label lblErrorPeriodo;
    @javafx.fxml.FXML
    private Label lblErrorHoras;
    @javafx.fxml.FXML
    private Label lblErrorCreditos;


    @javafx.fxml.FXML
    public void initialize() {
        cargarDocentes();
        cargarPeriodos();
        crearListeners();
        limitarSpinners();
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
    public void actionRegistrar(ActionEvent actionEvent) {
        String docente = cbDocentes.getSelectionModel().getSelectedItem().toString();
        String periodoEscolar = cbPeriodos.getSelectionModel().getSelectedItem().toString();
        String experienciaEducativa = txtExperienciaEducativa.getText().trim();
        String bloque = txtBloque.getText().trim();
        int seccion = (int) spinSeccion.getValue();
        int semanas = (int) spinSemanas.getValue();
        int horas = (int) spinHoras.getValue();
        int creditos = (int) spinCreditos.getValue();
        int meses = (int) spinMeses.getValue();
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
        txtExperienciaEducativa.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtExperienciaEducativa.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorExperiencia.setText(mensajeError);
                lblErrorExperiencia.setVisible(true);
            } else {
                lblErrorExperiencia.setVisible(false);
            }
            evaluarCampos();
        });

        txtBloque.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtBloque.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorBloque.setText(mensajeError);
                lblErrorBloque.setVisible(true);
            } else {
                lblErrorBloque.setVisible(false);
            }
            evaluarCampos();
        });
    }

    private String obtenerMensajeError(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return "El campo no puede estar vacío.";
        }
        if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+")) {
            return "El campo no debe contener caracteres especiales.";
        }
        return "";
    }

    private void evaluarCampos() {
        boolean docenteValido = cbDocentes.getSelectionModel().getSelectedItem() != null;
        boolean periodoValido = cbPeriodos.getSelectionModel().getSelectedItem() != null;
        boolean experienciaValida = obtenerMensajeError(txtExperienciaEducativa.getText()).isEmpty();
        boolean bloqueValido = obtenerMensajeError(txtBloque.getText()).isEmpty();

        btnRegistrar.setDisable(!experienciaValida
                || !bloqueValido
                || !docenteValido
                || !periodoValido);
    }

    private void limitarSpinners() {
        SpinnerValueFactory<Integer> valueFactoryHoras = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        SpinnerValueFactory<Integer> valueFactoryCreditos = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 14);
        SpinnerValueFactory<Integer> valueFactoryMeses = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6);
        SpinnerValueFactory<Integer> valueFactorySemanas = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 36);
        SpinnerValueFactory<Integer> valueFactorySeccion = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);

        spinHoras.setValueFactory(valueFactoryHoras);
        spinCreditos.setValueFactory(valueFactoryCreditos);
        spinMeses.setValueFactory(valueFactoryMeses);
        spinSemanas.setValueFactory(valueFactorySemanas);
        spinSeccion.setValueFactory(valueFactorySeccion);
    }
}
