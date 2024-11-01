package org.lossuperconocidos.sistemagestionconstancias.controladores;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.lossuperconocidos.sistemagestionconstancias.daos.UsuarioDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;

import java.util.HashMap;

public class FXMLLogIn
{

    @javafx.fxml.FXML
    private PasswordField pfPassword;
    @javafx.fxml.FXML
    private TextField tfUsuario;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void btnSalir(ActionEvent actionEvent) {
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @javafx.fxml.FXML
    public void btnEntrar(ActionEvent actionEvent) {

        String no_personal = tfUsuario.getText();
        String password = pfPassword.getText();

        HashMap<String, Object> respuesta = UsuarioDAO.iniciarSesion(no_personal, password);

        if(respuesta.containsKey("error")) {
            System.out.println(respuesta.get("mensaje"));
        } else {

            Usuario usuario = (Usuario) respuesta.get("usuario");
            System.out.println("Bienvenido " + usuario.getNombre());

        }

    }
}