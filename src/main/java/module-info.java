module org.lossuperconocidos.sistemagestionconstancias {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens org.lossuperconocidos.sistemagestionconstancias to javafx.fxml;
    exports org.lossuperconocidos.sistemagestionconstancias;
}