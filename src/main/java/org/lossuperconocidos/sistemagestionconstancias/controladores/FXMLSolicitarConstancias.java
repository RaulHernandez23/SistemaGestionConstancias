package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import org.lossuperconocidos.sistemagestionconstancias.daos.ParticipacionDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.*;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Alertas;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FXMLSolicitarConstancias implements Initializable {

    private Usuario usuario;

    private String no_personal;

    private ObservableList<ImparticionEE> listaImparticiones;

    private ObservableList<Jurado> listaJurados;

    private ObservableList<Proyecto> listaProyectos;

    private ObservableList<Pladea> listaPladeas;


    private TableView<ImparticionEE> tvImparticiones;

    private TableColumn colPeriodoImparticion;

    private TableColumn colEEImparticion;

    private TableColumn colProgramaEducativoImparticion;


    private TableView<Jurado> tvJurados;

    private TableColumn colPeriodoJurado;

    private TableColumn colTituloTrabajoJurado;

    private TableColumn colFechaPresentacionJurado;

    private TableColumn colModalidadJurado;


    private TableView<Proyecto> tvProyectos;

    private TableColumn colPeriodoCampo;

    private TableColumn colProyectoRealizadoCampo;

    private TableColumn colLugarCampo;


    private TableView<Pladea> tvPladeas;

    private TableColumn colPeriodoPladea;

    private TableColumn colEjeEstrategicoPladea;


    @FXML
    private Button btnVolver;

    @FXML
    private Button btnSolicitar;

    @FXML
    private ComboBox<String> cbParticipaciones;

    @FXML
    private BorderPane bpVentana;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colPeriodoImparticion = new TableColumn();
        colEEImparticion = new TableColumn();
        colProgramaEducativoImparticion = new TableColumn();

        colPeriodoJurado = new TableColumn();
        colTituloTrabajoJurado = new TableColumn();
        colFechaPresentacionJurado = new TableColumn();
        colModalidadJurado = new TableColumn();

        colPeriodoCampo = new TableColumn();
        colProyectoRealizadoCampo = new TableColumn();
        colLugarCampo = new TableColumn();

        colPeriodoPladea = new TableColumn();
        colEjeEstrategicoPladea = new TableColumn();

        configurarTablas();

        cargarTipoParticipaciones();

    }

    public void inicializar(Usuario usuario) {

        this.usuario = usuario;
        this.no_personal = usuario.getNo_personal();

        cbParticipaciones.getSelectionModel().selectFirst();

    }

    @FXML
    private void onBtnSolicitarClick() {
        // TODO: implementar
    }

    @FXML
    private void onBtnVolverClick() {
        cerrarVentana();
    }

    private void cerrarVentana() {

        Stage stage = (Stage) this.btnVolver.getScene().getWindow();
        stage.close();

    }

    public void configurarTablas() {

            configurarTablaImparticion();
            configurarTablaJurado();
            configurarTablaProyecto();
            configurarTablaPladea();

    }

    public void configurarTablaImparticion() {

        this.colPeriodoImparticion.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        this.colEEImparticion.setCellValueFactory(new PropertyValueFactory<>("experienciaEducativa"));
        this.colProgramaEducativoImparticion.setCellValueFactory(new PropertyValueFactory<>("programaEducativo"));

    }

    public void configurarTablaJurado() {

        this.colPeriodoJurado.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        this.colTituloTrabajoJurado.setCellValueFactory(new PropertyValueFactory<>("tituloTrabajo"));
        this.colFechaPresentacionJurado.setCellValueFactory(new PropertyValueFactory<>("fechaPresentacion"));
        this.colModalidadJurado.setCellValueFactory(new PropertyValueFactory<>("modalidad"));

    }

    public void configurarTablaProyecto() {

        this.colPeriodoCampo.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        this.colProyectoRealizadoCampo.setCellValueFactory(new PropertyValueFactory<>("proyectoRealizado"));
        this.colLugarCampo.setCellValueFactory(new PropertyValueFactory<>("lugar"));

    }

    public void configurarTablaPladea() {

        this.colPeriodoPladea.setCellValueFactory(new PropertyValueFactory<>("periodoEscolar"));
        this.colEjeEstrategicoPladea.setCellValueFactory(new PropertyValueFactory<>("ejeEstrategico"));

    }

    public void ajustarColumnasImparticion() {

        tvImparticiones = new TableView<ImparticionEE>();

        int numeroColumnas = 3;

        tvImparticiones.getColumns().clear();

        colPeriodoImparticion.prefWidthProperty().bind(
                tvImparticiones.widthProperty().divide(numeroColumnas));

        colEEImparticion.prefWidthProperty().bind(
                tvImparticiones.widthProperty().divide(numeroColumnas));

        colProgramaEducativoImparticion.prefWidthProperty().bind(
                tvImparticiones.widthProperty().divide(numeroColumnas));

        tvImparticiones.getColumns().addAll(
                colPeriodoImparticion,
                colEEImparticion,
                colProgramaEducativoImparticion);

    }

    public void ajustarColumnasJurado() {

        tvJurados = new TableView<Jurado>();

        int numeroColumnas = 4;

        tvJurados.getColumns().clear();

        colPeriodoJurado.prefWidthProperty().bind(
                tvJurados.widthProperty().divide(numeroColumnas));

        colTituloTrabajoJurado.prefWidthProperty().bind(
                tvJurados.widthProperty().divide(numeroColumnas));

        colFechaPresentacionJurado.prefWidthProperty().bind(
                tvJurados.widthProperty().divide(numeroColumnas));

        colModalidadJurado.prefWidthProperty().bind(
                tvJurados.widthProperty().divide(numeroColumnas));

        tvJurados.getColumns().addAll(
                colPeriodoJurado,
                colTituloTrabajoJurado,
                colFechaPresentacionJurado,
                colModalidadJurado);

    }

    public void ajustarColumnasProyecto() {

        tvProyectos = new TableView<Proyecto>();

        int numeroColumnas = 3;

        tvProyectos.getColumns().clear();

        colPeriodoCampo.prefWidthProperty().bind(
                tvProyectos.widthProperty().divide(numeroColumnas));

        colProyectoRealizadoCampo.prefWidthProperty().bind(
                tvProyectos.widthProperty().divide(numeroColumnas));

        colLugarCampo.prefWidthProperty().bind(
                tvProyectos.widthProperty().divide(numeroColumnas));

        tvProyectos.getColumns().addAll(
                colPeriodoCampo,
                colProyectoRealizadoCampo,
                colLugarCampo);

    }

    public void ajustarColumnasPladea() {

        tvPladeas = new TableView<Pladea>();

        int numeroColumnas = 2;

        tvPladeas.getColumns().clear();

        colPeriodoPladea.prefWidthProperty().bind(
                tvPladeas.widthProperty().divide(numeroColumnas));

        colEjeEstrategicoPladea.prefWidthProperty().bind(
                tvPladeas.widthProperty().divide(numeroColumnas));

        tvPladeas.getColumns().addAll(
                colPeriodoPladea,
                colEjeEstrategicoPladea);

    }

    public void cambiarTablaImparticion() {

        ajustarColumnasImparticion();

        HashMap<String, Object> respuesta = ParticipacionDAO.obtenerImparticionesEE(no_personal);

        Boolean error = (Boolean) respuesta.get("error");

        if (!error) {

            listaImparticiones = FXCollections.observableArrayList();

            ArrayList<ImparticionEE> imparticiones = (ArrayList<ImparticionEE>) respuesta.get("imparticiones");

            listaImparticiones.addAll(
                    imparticiones
            );

            tvImparticiones.setItems(listaImparticiones);

            bpVentana.setCenter(tvImparticiones);

            BorderPane.setMargin(
                    tvImparticiones,
                    new Insets(0, 50, 0, 50));


            colPeriodoImparticion.setText("Periodo Escolar");

            colEEImparticion.setText("Experiencia Educativa");

            colProgramaEducativoImparticion.setText("Programa Educativo");

            colPeriodoImparticion.setResizable(false);
            colEEImparticion.setResizable(false);
            colProgramaEducativoImparticion.setResizable(false);

        }
        else {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar las imparticiones");
            alerta.setContentText(respuesta.get("mensaje").toString());
            alerta.showAndWait();

        }

    }

    public void cambiarTablaJurado() {

        ajustarColumnasJurado();

        HashMap<String, Object> respuesta = ParticipacionDAO.obtenerJurados(no_personal);

        Boolean error = (Boolean) respuesta.get("error");

        if (!error) {

            listaJurados = FXCollections.observableArrayList();

            ArrayList<Jurado> jurados = (ArrayList<Jurado>) respuesta.get("jurados");

            listaJurados.addAll(
                    jurados
            );

            tvJurados.setItems(listaJurados);

            bpVentana.setCenter(tvJurados);

            BorderPane.setMargin(
                    tvJurados,
                    new Insets(0, 50, 0, 50));

            colPeriodoJurado.setText("Periodo Escolar");

            colTituloTrabajoJurado.setText("Título del Trabajo");

            colFechaPresentacionJurado.setText("Fecha de Presentación");

            colModalidadJurado.setText("Modalidad");

            colPeriodoImparticion.setResizable(false);

            colEEImparticion.setResizable(false);

            colProgramaEducativoImparticion.setResizable(false);

        }
        else {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar los jurados");
            alerta.setContentText(respuesta.get("mensaje").toString());
            alerta.showAndWait();

        }

    }

    public void cambiarTablaProyecto() {

        ajustarColumnasProyecto();

        HashMap<String, Object> respuesta = ParticipacionDAO.obtenerProyectosDeCampo(no_personal);

        Boolean error = (Boolean) respuesta.get("error");

        if (!error) {

            listaProyectos = FXCollections.observableArrayList();

            ArrayList<Proyecto> proyectos = (ArrayList<Proyecto>) respuesta.get("proyectos");

            listaProyectos.addAll(
                    proyectos
            );

            tvProyectos.setItems(listaProyectos);

            bpVentana.setCenter(tvProyectos);

            BorderPane.setMargin(
                    tvProyectos,
                    new Insets(0, 50, 0, 50));

            colPeriodoCampo.setText("Periodo Escolar");

            colProyectoRealizadoCampo.setText("Proyecto Realizado");

            colLugarCampo.setText("Lugar");

            colPeriodoImparticion.setResizable(false);

            colEEImparticion.setResizable(false);

            colProgramaEducativoImparticion.setResizable(false);

        }
        else {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar los proyectos de campo");
            alerta.setContentText(respuesta.get("mensaje").toString());
            alerta.showAndWait();

        }

    }

    public void cambiarTablaPladea() {

        ajustarColumnasPladea();

        HashMap<String, Object> respuesta = ParticipacionDAO.obtenerPladeas(no_personal);

        Boolean error = (Boolean) respuesta.get("error");

        if (!error) {

            listaPladeas = FXCollections.observableArrayList();

            ArrayList<Pladea> pladeas = (ArrayList<Pladea>) respuesta.get("pladeas");

            listaPladeas.addAll(
                    pladeas
            );

            tvPladeas.setItems(listaPladeas);

            bpVentana.setCenter(tvPladeas);

            BorderPane.setMargin(
                    tvPladeas,
                    new Insets(0, 50, 0, 50));

            colPeriodoPladea.setText("Periodo Escolar");

            colEjeEstrategicoPladea.setText("Eje Estratégico");

            colPeriodoImparticion.setResizable(false);

            colEEImparticion.setResizable(false);

        }
        else {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar los pladeas");
            alerta.setContentText(respuesta.get("mensaje").toString());
            alerta.showAndWait();

        }

    }

    public void cargarTipoParticipaciones() {

        cbParticipaciones.getItems().clear();

        cbParticipaciones.getItems().addAll(
                "Impartición EE",
                "Jurado",
                "Proyecto de Campo",
                "Pladea"
        );

        ChangeListener<String> listener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue != null) {

                    switch (newValue) {

                        case "Impartición EE":
                            cambiarTablaImparticion();
                            break;

                        case "Jurado":
                            cambiarTablaJurado();
                            break;

                        case "Proyecto de Campo":
                            cambiarTablaProyecto();
                            break;

                        case "Pladea":
                            cambiarTablaPladea();
                            break;

                    }

                }

            }
        };

        cbParticipaciones.getSelectionModel().selectedItemProperty().addListener(listener);

    }

}
