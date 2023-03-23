module com.esiee.java_avance_lot_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.xml.bind;


    opens com.esiee.java_avance_lot_1.vue to javafx.fxml;
    opens com.esiee.java_avance_lot_1.jaxbe to jakarta.xml.bind, javafx.base;

    exports com.esiee.java_avance_lot_1.vue;
    exports com.esiee.java_avance_lot_1.controller;
    opens com.esiee.java_avance_lot_1.controller to javafx.fxml;
}
