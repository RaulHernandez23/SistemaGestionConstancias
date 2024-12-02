module org.lossuperconocidos.sistemagestionconstancias {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires java.sql;
    requires atlantafx.base;
    requires org.apache.poi.ooxml;
    requires com.aspose.words;
    requires net.sf.jasperreports.core;

    opens org.lossuperconocidos.sistemagestionconstancias to javafx.fxml;
    opens org.lossuperconocidos.sistemagestionconstancias.modelos to javafx.base;
    exports org.lossuperconocidos.sistemagestionconstancias;
    exports org.lossuperconocidos.sistemagestionconstancias.controladores;
    opens org.lossuperconocidos.sistemagestionconstancias.controladores to javafx.fxml;
}