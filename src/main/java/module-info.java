module com.esiee.java_avance_lot_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml.bind;

    opens com.esiee.java_avance_lot_1 to javafx.fxml;
    exports com.esiee.java_avance_lot_1;
}
