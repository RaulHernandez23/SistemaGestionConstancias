package org.lossuperconocidos.sistemagestionconstancias.utilidades;
import javafx.stage.Stage;
import java.io.File;
import javafx.stage.DirectoryChooser;


public class VentanasEmergentes {
    /**
     * Abre un explorador de directorios y permite seleccionar un directorio.
     *
     * @param stage El Stage (ventana) principal en la que se abre el explorador.
     * @param title El título del explorador de directorios.
     * @return La ruta del directorio seleccionado, o null si no se selecciona ninguno.
     */
    public static String openDirectoryChooser(Stage stage, String title) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        File selectedDirectory = directoryChooser.showDialog(stage);
        return (selectedDirectory != null) ? selectedDirectory.getAbsolutePath() : null;
    }
}
