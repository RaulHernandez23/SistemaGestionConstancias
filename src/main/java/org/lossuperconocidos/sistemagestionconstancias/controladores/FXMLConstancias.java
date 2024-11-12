package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.lossuperconocidos.sistemagestionconstancias.Inicio;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ContanciaItem;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLConstancias implements Initializable {

    private static final int  ALTO_PARA_ITEM = 80;
    private static final int  PADDING_ARRIBA_PARA_ITEM = 5;
    private static final int  PADDING_ABAJO_PARA_ITEM = 5;
    public ToggleGroup criteriosBusqueda;
    public ListView<VBox> listaContancias;
    public RadioButton rdImparticion;
    public RadioButton rdPladea;
    public RadioButton rbJurado;
    public ComboBox cbPeriodo;
    public RadioButton rbProyecto;
    private RadioButton lastSelectedRadioButton = null;
    private Usuario usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    public void inicializar(Usuario usuario)
    {
        this.usuario = usuario;
        //TODO: falta el metodo de recuperar participaciones
        recuperarParticipaciones();
        //TODO:
        // Lista de items que quieres cargar
        List<ContanciaItem> items = Arrays.asList(
                new ContanciaItem("FEB", "jurado"),
                new ContanciaItem("JUN", "Pladea")
        );
        cargarParticipaciones(items);
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(Inicio.class.getResource("FXMLConstanciasItem.fxml"));
//            VBox anchorPane = fxmlLoader.load();
//            FXMLConstanciaItem controller = fxmlLoader.getController();
//
//            controller.inicializarContanciaItem(new ContanciaItem("FEB", "jurado"));
//
//
//            FXMLLoader fxmlLoader2 = new FXMLLoader(Inicio.class.getResource("FXMLConstanciasItem.fxml"));
//            VBox anchorPane2 = fxmlLoader2.load() ;
//            FXMLConstanciaItem controller2 = fxmlLoader.getController();
//            controller2.inicializarContanciaItem(new ContanciaItem("Jun", "Pladea"));
//
//            ObservableList<VBox> dataList = FXCollections.observableArrayList(
//                    anchorPane,
//                    anchorPane2
//            );
//
//            iniciarListView(dataList);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        inicializarGrupoRadioBoton();
        inicializarComboBox();
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
    }

    private void recuperarParticipaciones() {
        //TODO: Recuperar participaciones de BD
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
        cbPeriodo.getItems().addAll(
                "FEB-JUN 2024", "AGOS-DIC 2024",
                "FEB-JUN 2025", "AGOS-DIC 2025"
                //TODO: Agregar más períodos según sea necesario en base a la BD
        );
    }

    private void inicializarGrupoRadioBoton() {
        agregarDeselecionAccion(rbJurado);
        agregarDeselecionAccion(rdImparticion);
        agregarDeselecionAccion(rdPladea);
        agregarDeselecionAccion(rbProyecto);
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
