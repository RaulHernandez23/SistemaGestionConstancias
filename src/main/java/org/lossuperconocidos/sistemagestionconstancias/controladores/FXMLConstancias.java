package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.lossuperconocidos.sistemagestionconstancias.Inicio;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ListaConstancias;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLConstancias implements Initializable {

    public ToggleGroup criteriosBusqueda;
    public ListView<AnchorPane> listaContancias;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    public void inicializar()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Inicio.class.getResource("FXMLConstanciasItem.fxml"));
            AnchorPane anchorPane = fxmlLoader.load() ;
            FXMLConstanciaItem controller = fxmlLoader.getController();
            controller.inicializarContanciaItem("Hoy");


            FXMLLoader fxmlLoader2 = new FXMLLoader(Inicio.class.getResource("FXMLConstanciasItem.fxml"));
            AnchorPane anchorPane2 = fxmlLoader2.load() ;
            FXMLConstanciaItem controller2 = fxmlLoader.getController();
            controller2.inicializarContanciaItem("Hoy   2");

            ObservableList<AnchorPane> dataList = FXCollections.observableArrayList(
                    anchorPane,
                    anchorPane2
            );

            setListView(dataList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    // Método para cargar datos en el ListView
    private void setListView(ObservableList<AnchorPane> dataList) {
        listaContancias.setItems(dataList);
        inicializarLista();
    }

    private void inicializarLista() {
//        listaContancias.setCellFactory(new Callback<ListView<AnchorPane>, ListCell<AnchorPane>>() {
//            @Override
//            public ListCell<AnchorPane> call(ListView<AnchorPane> listView) {
//                return new ListaConstancias();
//            }
//        });
        listaContancias.setCellFactory(listView -> new ListCell<AnchorPane>() {
            @Override
            protected void updateItem(AnchorPane item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(item); // Muestra el AnchorPane directamente en la celda
                }
            }
        });
    }



}
