package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.lossuperconocidos.sistemagestionconstancias.Inicio;
import org.lossuperconocidos.sistemagestionconstancias.daos.ParticipacionDAO;
import org.lossuperconocidos.sistemagestionconstancias.daos.PeriodoEscolarDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.ParticipacionUsuario;
import org.lossuperconocidos.sistemagestionconstancias.modelos.PeriodoEscolar;
import org.lossuperconocidos.sistemagestionconstancias.modelos.PlantillaProyecto;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

public class FXMLConstancias implements Initializable {

    private static final int  ALTO_PARA_ITEM = 80;
    private static final int  PADDING_ARRIBA_PARA_ITEM = 5;
    private static final int  PADDING_ABAJO_PARA_ITEM = 5;
    private String nombreBusquedardImparticion ="mpartici";
    private String nombreBusquedardPladea = "ladea";
    private String nombreBusquedarbJurado = "urado";
    private String nombreBusquedarbProyecto = "royect";
    public ToggleGroup criteriosBusqueda;
    public ListView<VBox> listaContancias;
    public RadioButton rdImparticion;
    public RadioButton rdPladea;
    public RadioButton rbJurado;
    public ComboBox<PeriodoEscolar> cbPeriodo;
    public RadioButton rbProyecto;
    private RadioButton lastSelectedRadioButton = null;
    private Usuario usuario;
    private List<ContanciaItem> participacionesOriginales;
    private PeriodoEscolar periodoSeleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    public void inicializar(Usuario usuario)
    {
        this.usuario = usuario;
        ArrayList<String> privilegios = usuario.separaTiposUsuarios();
        boolean contieneFiltroDocente = false;
        for (String privilegio : privilegios) {
            if (privilegio.contains(usuario.FILTRO_DOCENTE)) {
                contieneFiltroDocente = true;
                break;
            }
        }
        if (contieneFiltroDocente){
            participacionesOriginales = recuperarParticipacionesDocente();
        }else {
            participacionesOriginales = recuperarTodasParticipaciones();
        }
        cargarParticipaciones(participacionesOriginales);
        inicializarGrupoRadioBoton();
        inicializarComboBox();
    }

    private List<ContanciaItem> recuperarTodasParticipaciones() {
        List<ContanciaItem> resultado;
        try {
            HashMap<String, Object> resultadoConsulta = ParticipacionDAO.recuperarTodaParticipacion();
            if (resultadoConsulta == null || (boolean)resultadoConsulta.get(ParticipacionDAO.ERROR_KEY)) {
                throw new Exception("No se puede recuperar la participacion");
            }
            ArrayList<ParticipacionUsuario> participaciones= (ArrayList<ParticipacionUsuario>) resultadoConsulta.get(ParticipacionDAO.PARTICIPACIONES_KEY);


            if (participaciones != null && !participaciones.isEmpty()) {
                resultado = new ArrayList<>();
                participaciones.forEach(participacion -> {
                    ContanciaItem itemDeLista = new ContanciaItem(
                            participacion.getPeriodoEscolarNombre(),
                            participacion.getTipoParticipacion(),
                            participacion.getNombreCompleto()
                    );
                    resultado.add(itemDeLista);
                });
                return  resultado;
            }
        } catch (Exception e) {
            System.err.println("Error al recuperar las participaciones: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    private void cargarParticipaciones(List<ContanciaItem> participaciones) {
        ObservableList<VBox> dataList = FXCollections.observableArrayList();

        for (ContanciaItem item : participaciones) {
            VBox anchorPane = cargarConstanciaItem(item);
            if (anchorPane != null) {
                dataList.add(anchorPane);
            }
        }
        iniciarListView(dataList);
        if (participaciones == null || participaciones.isEmpty()){
            Alertas.mostrarAlertaInformacion("No se encontraron participaciones", "Por el momento tu usuario no tiene participaciones");
        }
    }

    private List<ContanciaItem> recuperarParticipacionesDocente() {
        List<ContanciaItem> resultado;
        try {
            HashMap<String, Object> resultadoConsulta = ParticipacionDAO.recuperarParticipacionPorNoPerosnal(usuario.getNo_personal());
            if (resultadoConsulta == null || (boolean)resultadoConsulta.get(ParticipacionDAO.ERROR_KEY)) {
                throw new Exception("No se puede recuperar la participacion");
            }
            ArrayList<ParticipacionUsuario> participaciones= (ArrayList<ParticipacionUsuario>) resultadoConsulta.get(ParticipacionDAO.PARTICIPACIONES_KEY);

            if (participaciones != null && !participaciones.isEmpty()) {
                resultado = new ArrayList<>();
                participaciones.forEach(participacion -> {
                    ContanciaItem itemDeLista = new ContanciaItem(
                            participacion.getPeriodoEscolarNombre(),
                            participacion.getTipoParticipacion()
                    );
                    resultado.add(itemDeLista);
                });
                return  resultado;
            }
        } catch (Exception e) {
            System.err.println("Error al recuperar las participaciones: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private VBox cargarConstanciaItem(ContanciaItem item) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Inicio.class.getResource("FXMLConstanciasItem.fxml"));
            VBox anchorPane = fxmlLoader.load();
            FXMLConstanciaItem controller = fxmlLoader.getController();
            controller.inicializarContanciaItem(item);
            return anchorPane;
        } catch (IOException e) {
            System.err.println("Error al cargar el item: " + item);
            e.printStackTrace();
            return null;
        }
    }

    private void inicializarComboBox() {
        try {
            HashMap<String, Object> resultadoConsulta = PeriodoEscolarDAO.recuperarPeriodosEscolares();
            if (resultadoConsulta == null || (boolean)resultadoConsulta.get(ParticipacionDAO.ERROR_KEY)) {
                throw new Exception("No se puede recuperar la participacion");
            }
            List<PeriodoEscolar> periodos = (List<PeriodoEscolar>) resultadoConsulta.get(PeriodoEscolarDAO.PERIODOS_KEY);
            if (periodos != null && !periodos.isEmpty()) {

                cbPeriodo.getItems().addAll(periodos);
                // Listener para cambios en el ComboBox
                cbPeriodo.valueProperty().addListener((observable, oldValue, newValue) -> {
                    periodoSeleccionado = newValue;
                    aplicarFiltros();
                });
            } else {
                throw new Exception("La lista de periodos escolares está vacía");
            }
        }catch (Exception e){
            System.err.println("Error al inicializar el ComboBox de periodos escolares: " + e.getMessage());
            e.printStackTrace();
            Alertas.mostrarAlertaError("Error al cargar periodos", "No se pudo cargar la lista de periodos escolares.");

        }
    }

    private void inicializarGrupoRadioBoton() {
        agregarDeselecionAccion(rbJurado);
        agregarDeselecionAccion(rdImparticion);
        agregarDeselecionAccion(rdPladea);
        agregarDeselecionAccion(rbProyecto);
        rdImparticion.setOnAction(event -> aplicarFiltros());
        rdPladea.setOnAction(event -> aplicarFiltros());
        rbJurado.setOnAction(event -> aplicarFiltros());
        rbProyecto.setOnAction(event -> aplicarFiltros());

        criteriosBusqueda.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                cargarParticipaciones(participacionesOriginales);
            }
        });
    }

    private void aplicarFiltros() {
        if (participacionesOriginales == null || participacionesOriginales.isEmpty()) {
            return;
        }
        String criterioTipo = null;
        if (rdImparticion.isSelected()) {
            criterioTipo = nombreBusquedardImparticion;
        } else if (rdPladea.isSelected()) {
            criterioTipo = nombreBusquedardPladea;
        } else if (rbJurado.isSelected()) {
            criterioTipo = nombreBusquedarbJurado;
        } else if (rbProyecto.isSelected()) {
            criterioTipo = nombreBusquedarbProyecto;
        }

        List<ContanciaItem> filtradas = new ArrayList<>();
        for (ContanciaItem item : participacionesOriginales) {
            boolean coincideTipo = (criterioTipo == null) || item.getTipo().contains(criterioTipo);
            boolean coincidePeriodo = (periodoSeleccionado == null) || item.getPeriodo().equalsIgnoreCase(periodoSeleccionado.getNombre());

            if (coincideTipo && coincidePeriodo) {
                filtradas.add(item);
            }
        }

        cargarParticipaciones(filtradas);
    }


    private void iniciarListView(ObservableList<VBox> dataList) {
        listaContancias.setItems(dataList);
        inicializarLista();
    }

    private void inicializarLista() {
        listaContancias.setCellFactory(listView -> new ListCell<VBox>() {
            @Override
            protected void updateItem(VBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    item.prefWidthProperty().bind(listView.widthProperty().subtract(5)); // ajusta el margen si es necesario
                    setPrefHeight(ALTO_PARA_ITEM); // Define una altura fija para cada celda
                    setPadding(new Insets(PADDING_ARRIBA_PARA_ITEM , 0, PADDING_ABAJO_PARA_ITEM , 0));
                    setGraphic(item);
                }
            }
        });
    }


    public void clicBtnEjemplo(ActionEvent actionEvent) {
        String rutaActual = System.getProperty("user.dir");
        System.out.println(rutaActual);
        String rutaDestino = VentanasEmergentes.openDirectoryChooser((Stage) listaContancias.getScene().getWindow(), "Ruta destino");
        String rutaDeLaPlantilla = VentanasEmergentes.openFileChooser((Stage) listaContancias.getScene().getWindow(), "Ruta de la plantilla");
        GeneradorConstancia generadorConstancia = new GeneradorConstancia();
        try {
            PlantillaProyecto plantillaProyecto = new PlantillaProyecto.Builder()
                    .agregarValor("NombreDirector", "Juan Pérez")
                    .agregarValor("ProyectoRealizado", "Sistema Innovador")
                    .agregarValor("Lugar", "Ciudad de México")
                    .build();
            generadorConstancia.crearContancia(rutaDeLaPlantilla,rutaDestino, plantillaProyecto);
        } catch (FileAlreadyExistsException e) {
            Alertas.mostrarAlertaAdvertencia("Nombre duplicado", "Archivo con el mismo nombre");
        }
    }

    private void agregarDeselecionAccion(RadioButton radioButton) {
        radioButton.setOnMouseClicked(event -> {
            if (radioButton.equals(lastSelectedRadioButton)) {
                criteriosBusqueda.selectToggle(null);
                lastSelectedRadioButton = null;
                cbPeriodo.getSelectionModel().clearSelection();
            } else {
                lastSelectedRadioButton = radioButton;
            }
        });
    }
}
