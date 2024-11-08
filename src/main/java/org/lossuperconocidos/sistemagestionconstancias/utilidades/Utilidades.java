package org.lossuperconocidos.sistemagestionconstancias.utilidades;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class Utilidades {
    public static URL getURL(String url) {
        return Utilidades.class.getResource(url);
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(Utilidades.getURL(fxml));
    }

    public static String getURLString(String url) {
        return Utilidades.getURL(url).toExternalForm();
    }

    public static void inicializarVentana(String fxml, boolean esperar) {
        try {
            FXMLLoader fxmlLoader = Utilidades.getFXMLLoader(fxml);
            Parent vista = fxmlLoader.load();
            Scene escena = new Scene(vista);

            // Crear un nuevo Stage en lugar de usar el existente
            Stage nuevoEscenario = new Stage();
            nuevoEscenario.setScene(escena);
            nuevoEscenario.setResizable(false);
            nuevoEscenario.initStyle(StageStyle.UNDECORATED);

            if (esperar) {
                nuevoEscenario.showAndWait();
            } else {
                nuevoEscenario.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
