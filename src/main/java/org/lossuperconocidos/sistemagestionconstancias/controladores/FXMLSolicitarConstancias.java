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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.util.JRLoader;
import org.lossuperconocidos.sistemagestionconstancias.daos.ParticipacionDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.*;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Alertas;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.design.JRDesignStyle;

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

    private ImparticionEE imparticionEESeleccionada;

    private Jurado juradoSeleccionado;

    private Proyecto proyectoSeleccionado;

    private Pladea pladeaSeleccionado;


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

        if (cbParticipaciones.getSelectionModel().getSelectedItem().equals("Impartición EE")) {

            try {

                Map<String, Object> parametros = new HashMap<>();

                parametros.put("NOMBRE_DOCENTE", imparticionEESeleccionada.getNombreDocente());
                parametros.put("PERIODO_ESCOLAR", imparticionEESeleccionada.getPeriodoEscolar());
                parametros.put("PROGRAMA_EDUCATIVO", imparticionEESeleccionada.getProgramaEducativo());
                parametros.put("EXPERIENCIA_EDUCATIVA", imparticionEESeleccionada.getExperienciaEducativa());
                parametros.put("BLOQUE", imparticionEESeleccionada.getBloque());
                parametros.put("SECCION", imparticionEESeleccionada.getSeccion() + "");
                parametros.put("CREDITOS", imparticionEESeleccionada.getCreditos() + "");
                parametros.put("HORAS", imparticionEESeleccionada.getHoras() + "");
                parametros.put("SEMANAS", imparticionEESeleccionada.getSemanas() + "");
                parametros.put("MESES", imparticionEESeleccionada.getMeses() + "");
                parametros.put("NOMBRE_DIRECTOR", "");

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar Constancia");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

                File file = fileChooser.showSaveDialog(btnSolicitar.getScene().getWindow());

                if (file != null) {

                    String rutaSalida = file.getAbsolutePath();

                    generarReporte(
                            "src/main/resources/plantillas/constancia_imparticion_ee.jasper",
                            rutaSalida,
                            parametros
                    );

                    Alertas.mostrarAlertaInformacion("Éxito", "Constancia generada correctamente");

                    tvImparticiones.getSelectionModel().clearSelection();
                    btnSolicitar.setDisable(true);

                }

            } catch (JRException e) {

                Alertas.mostrarAlertaError("Error", e.getMessage());

            }

        }
        else if (cbParticipaciones.getSelectionModel().getSelectedItem().equals("Jurado")) {

                try {

                    Map<String, Object> parametros = new HashMap<>();

                    parametros.put("NOMBRE_DOCENTE", juradoSeleccionado.getNombreDocente());
                    parametros.put("FECHA_PRESENTACION", juradoSeleccionado.getFechaPresentacion() + "");
                    parametros.put("TITULO_TRABAJO", juradoSeleccionado.getTituloTrabajo());
                    parametros.put("MODALIDAD", juradoSeleccionado.getModalidad());
                    parametros.put("NOMBRE_ALUMNOS", juradoSeleccionado.getNombreAlumnos());
                    parametros.put("RESULTADO_OBTENIDO", juradoSeleccionado.getResultadoObtenido());
                    parametros.put("PROGRAMA_EDUCATIVO", "Ingeniería de Software");

                    FileChooser fileChooser = new FileChooser();

                    fileChooser.setTitle("Guardar Constancia");

                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

                    File file = fileChooser.showSaveDialog(btnSolicitar.getScene().getWindow());

                    if (file != null) {

                        String rutaSalida = file.getAbsolutePath();

                        generarReporte(
                                "src/main/resources/plantillas/constancia_jurado.jasper",
                                rutaSalida,
                                parametros
                        );

                        Alertas.mostrarAlertaInformacion("Éxito", "Constancia generada correctamente");

                        tvJurados.getSelectionModel().clearSelection();
                        btnSolicitar.setDisable(true);

                    }

                } catch (JRException e) {
                    Alertas.mostrarAlertaError("Error", e.getMessage());
                }

        }
        else if (cbParticipaciones.getSelectionModel().getSelectedItem().equals("Proyecto de Campo")) {

            try {

                Map<String, Object> parametros = new HashMap<>();

                parametros.put("NOMBRE_DOCENTE", proyectoSeleccionado.getNombreDocente());
                parametros.put("PROYECTO_REALIZADO", proyectoSeleccionado.getProyectoRealizado());
                parametros.put("DURACION", "6 meses");
                parametros.put("IMPACTO_OBTENIDO", proyectoSeleccionado.getImpactoObtenido());
                parametros.put("LUGAR", proyectoSeleccionado.getLugar());
                parametros.put("NOMBRE_ALUMNOS", proyectoSeleccionado.getNombreAlumnos());

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar Constancia");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

                File file = fileChooser.showSaveDialog(btnSolicitar.getScene().getWindow());

                if (file != null) {

                    String rutaSalida = file.getAbsolutePath();

                    generarReporte(
                            "src/main/resources/plantillas/constancia_proyecto_campo.jasper",
                            rutaSalida,
                            parametros
                    );

                    Alertas.mostrarAlertaInformacion("Éxito", "Constancia generada correctamente");

                    tvProyectos.getSelectionModel().clearSelection();
                    btnSolicitar.setDisable(true);

                }

            } catch (JRException e) {

                Alertas.mostrarAlertaError("Error", e.getMessage());

            }

        }
        else if (cbParticipaciones.getSelectionModel().getSelectedItem().equals("Pladea")) {

            try {

                Map<String, Object> parametros = new HashMap<>();

                parametros.put("NOMBRE_DOCENTE", pladeaSeleccionado.getNombreDocente());
                parametros.put("ACCIONES", pladeaSeleccionado.getAcciones());
                parametros.put("EJE_ESTRATEGICO", pladeaSeleccionado.getEjeEstrategico());
                parametros.put("METAS", pladeaSeleccionado.getMetas());
                parametros.put("OBJETIVOS_GENERALES", pladeaSeleccionado.getObjetivosGenerales());
                parametros.put("PROGRAMA_ESTRATEGICO", pladeaSeleccionado.getProgramaEstrategico());
                parametros.put("RESULTADO_OBTENIDO", "Satisfactorio");

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar Constancia");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

                File file = fileChooser.showSaveDialog(btnSolicitar.getScene().getWindow());

                if (file != null) {

                    String rutaSalida = file.getAbsolutePath();

                    generarReporte(
                            "src/main/resources/plantillas/constancia_pladea.jasper",
                            rutaSalida,
                            parametros
                    );

                    Alertas.mostrarAlertaInformacion("Éxito", "Constancia generada correctamente");

                    tvPladeas.getSelectionModel().clearSelection();
                    btnSolicitar.setDisable(true);

                }

            } catch (JRException e) {
                Alertas.mostrarAlertaError("Error", e.getMessage());
            }

        }

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

            tvImparticiones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImparticionEE>() {
                @Override
                public void changed(ObservableValue<? extends ImparticionEE> observable, ImparticionEE oldValue, ImparticionEE newValue) {
                    imparticionEESeleccionada = newValue;
                    btnSolicitar.setDisable(false);
                }
            });

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

            tvJurados.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Jurado>() {
                @Override
                public void changed(ObservableValue<? extends Jurado> observable, Jurado oldValue, Jurado newValue) {
                    juradoSeleccionado = newValue;
                    btnSolicitar.setDisable(false);
                }
            });

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

            tvProyectos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Proyecto>() {
                @Override
                public void changed(ObservableValue<? extends Proyecto> observable, Proyecto oldValue, Proyecto newValue) {
                    proyectoSeleccionado = newValue;
                    btnSolicitar.setDisable(false);
                }
            });

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

            tvPladeas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pladea>() {
                @Override
                public void changed(ObservableValue<? extends Pladea> observable, Pladea oldValue, Pladea newValue) {
                    pladeaSeleccionado = newValue;
                    btnSolicitar.setDisable(false);
                }
            });

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

                    btnSolicitar.setDisable(true);

                }

            }
        };

        cbParticipaciones.getSelectionModel().selectedItemProperty().addListener(listener);

    }

    public void generarReporte(String rutaPlantilla, String rutaSalida, Map<String, Object> parametros) throws JRException {

        JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(rutaPlantilla);

        JRDataSource dataSource = new JREmptyDataSource();

        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalida);

        System.out.println("Reporte guardado en: " + rutaSalida);

    }

}
