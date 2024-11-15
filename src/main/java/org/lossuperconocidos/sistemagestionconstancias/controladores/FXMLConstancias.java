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
import org.lossuperconocidos.sistemagestionconstancias.modelos.ParticipacionCorregido;
import org.lossuperconocidos.sistemagestionconstancias.modelos.PeriodoEscolar;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Alertas;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ContanciaItem;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.GeneradorConstancia;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.VentanasEmergentes;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

public class FXMLConstancias implements Initializable {

    private static final int  ALTO_PARA_ITEM = 80;
    private static final int  PADDING_ARRIBA_PARA_ITEM = 5;
    private static final int  PADDING_ABAJO_PARA_ITEM = 5;
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
        participacionesOriginales = recuperarParticipaciones();
        cargarParticipaciones(participacionesOriginales);
        inicializarGrupoRadioBoton();
        inicializarComboBox();
    }


    private void cargarParticipaciones(List<ContanciaItem> participaciones) {
        if (participaciones == null || participaciones.isEmpty()){
            Alertas.mostrarAlertaInformacion("No se encontraron participaciones", "Por el momento tu usuario no tiene participaciones");
            return;
        }
        ObservableList<VBox> dataList = FXCollections.observableArrayList();

        for (ContanciaItem item : participaciones) {
            VBox anchorPane = cargarConstanciaItem(item);
            if (anchorPane != null) {
                dataList.add(anchorPane);
            }
        }
        iniciarListView(dataList);
    }

    private List<ContanciaItem> recuperarParticipaciones() {
        List<ContanciaItem> resultado;
        try {
            HashMap<String, Object> resultadoConsulta = ParticipacionDAO.recuperarParticipacionPorNoPerosnal(usuario.getNo_personal());
            if (resultadoConsulta == null || (boolean)resultadoConsulta.get(ParticipacionDAO.ERROR_KEY)) {
                throw new Exception("No se puede recuperar la participacion");
            }
            ArrayList<ParticipacionCorregido> participaciones= (ArrayList<ParticipacionCorregido>) resultadoConsulta.get(ParticipacionDAO.PARTICIPACIONES_KEY);

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
            criterioTipo = "mpartici";
        } else if (rdPladea.isSelected()) {
            criterioTipo = "ladea";
        } else if (rbJurado.isSelected()) {
            criterioTipo = "urado";
        } else if (rbProyecto.isSelected()) {
            criterioTipo = "royect";
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
        String rutaDestino = VentanasEmergentes.openDirectoryChooser((Stage) listaContancias.getScene().getWindow(), "Ruta destino");
        String rutaDeLaPlantilla = VentanasEmergentes.openFileChooser((Stage) listaContancias.getScene().getWindow(), "Ruta de la plantilla");
        GeneradorConstancia generadorConstancia = new GeneradorConstancia();
        try {
            generadorConstancia.crearContancia(rutaDeLaPlantilla,rutaDestino);
            //generadorConstancia.crearContancia("E:\\JavaUV\\PDS\\SistemaGestionConstancias\\src\\main\\java\\org\\lossuperconocidos\\sistemagestionconstancias\\utilidades\\plantillas\\Proyecto.docx","C:\\Users\\USER\\Downloads\\ConstanciasPlantillas\\Ejemplo");
        } catch (FileAlreadyExistsException e) {
            Alertas.mostrarAlertaAdvertencia("Nombre duplicado", "Archivo con el mismo nombre");
        }
    }

    private void agregarDeselecionAccion(RadioButton radioButton) {
        radioButton.setOnMouseClicked(event -> {
            if (radioButton.equals(lastSelectedRadioButton)) {
                criteriosBusqueda.selectToggle(null); // Deselecciona el RadioButton en el grupo
                lastSelectedRadioButton = null;       // Limpia la última selección
            } else {
                lastSelectedRadioButton = radioButton; // Actualiza el último seleccionado
            }
        });
    }
}
