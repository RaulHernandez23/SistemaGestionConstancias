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

public class FXMLParticipacionPladea {
    @javafx.fxml.FXML
    private Label lblErrorEje;
    @javafx.fxml.FXML
    private Label lblErrorAcciones;
    @javafx.fxml.FXML
    private TextArea txtObjetivos;
    @javafx.fxml.FXML
    private Label lblErrorMetas;
    @javafx.fxml.FXML
    private TextArea txtMetas;
    @javafx.fxml.FXML
    private ComboBox cbDocentes;
    @javafx.fxml.FXML
    private Label lblErrorDocente;
    @javafx.fxml.FXML
    private TextField txtPrograma;
    @javafx.fxml.FXML
    private Label lblErrorPrograma;
    @javafx.fxml.FXML
    private TextField txtEje;
    @javafx.fxml.FXML
    private ComboBox cbPeriodos;
    @javafx.fxml.FXML
    private TextArea txtAcciones;
    @javafx.fxml.FXML
    private Label lblErrorObjetivos;
    @javafx.fxml.FXML
    private Button btnRegistrar;
    @javafx.fxml.FXML
    private Label lblErrorPeriodo;

    @javafx.fxml.FXML
    public void initialize() {
        cargarDocentes();
        cargarPeriodos();
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
    public void actionRegistrar(ActionEvent actionEvent) {
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
        txtAcciones.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtAcciones.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorAcciones.setText(mensajeError);
                lblErrorAcciones.setVisible(true);
            } else {
                lblErrorAcciones.setVisible(false);
            }
            evaluarCampos();
        });

        txtEje.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtEje.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorEje.setText(mensajeError);
                lblErrorEje.setVisible(true);
            } else {
                lblErrorEje.setVisible(false);
            }
            evaluarCampos();
        });

        txtMetas.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtMetas.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorMetas.setText(mensajeError);
                lblErrorMetas.setVisible(true);
            } else {
                lblErrorMetas.setVisible(false);
            }
            evaluarCampos();
        });

        txtObjetivos.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtObjetivos.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorObjetivos.setText(mensajeError);
                lblErrorObjetivos.setVisible(true);
            } else {
                lblErrorObjetivos.setVisible(false);
            }
            evaluarCampos();
        });

        txtPrograma.textProperty().addListener((observable, oldValue, newValue) -> {
            String mensajeError = obtenerMensajeError(txtPrograma.getText());
            if (!mensajeError.isEmpty()) {
                lblErrorPrograma.setText(mensajeError);
                lblErrorPrograma.setVisible(true);
            } else {
                lblErrorPrograma.setVisible(false);
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
        boolean programaValido = obtenerMensajeError(txtPrograma.getText()).isEmpty();
        boolean ejeValido = obtenerMensajeError(txtEje.getText()).isEmpty();
        boolean accionesValidas = obtenerMensajeError(txtAcciones.getText()).isEmpty();
        boolean metasValidas = obtenerMensajeError(txtMetas.getText()).isEmpty();
        boolean objetivosValidos = obtenerMensajeError(txtObjetivos.getText()).isEmpty();

        btnRegistrar.setDisable(!docenteValido
                || !periodoValido
                || !programaValido
                || !ejeValido
                || !accionesValidas
                || !metasValidas
                || !objetivosValidos);
    }
}
