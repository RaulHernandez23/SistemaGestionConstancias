module org.lossuperconocidos.sistemagestionconstancias {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;

    opens org.lossuperconocidos.sistemagestionconstancias to javafx.fxml;
    exports org.lossuperconocidos.sistemagestionconstancias;
    exports org.lossuperconocidos.sistemagestionconstancias.controladores;
    opens org.lossuperconocidos.sistemagestionconstancias.controladores to javafx.fxml;
}