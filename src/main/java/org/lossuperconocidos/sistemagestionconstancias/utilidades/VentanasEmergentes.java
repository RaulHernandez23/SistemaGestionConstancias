package org.lossuperconocidos.sistemagestionconstancias.utilidades;
import javafx.stage.FileChooser;
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
    public static String openFileChooser(Stage stage, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        // Opcional: Define extensiones de archivo permitidas
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Documentos Word", "*.docx"),
                new FileChooser.ExtensionFilter("Todos los Archivos", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        return (selectedFile != null) ? selectedFile.getAbsolutePath() : null;
    }
}
