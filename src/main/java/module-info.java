module com.esiee.java_avance_lot_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
//    requires java.xml.bind;
    requires jakarta.xml.bind;

//    requires com.sun.xml.ws;
//    requires java.xml.bind;
//    requires com.sun.xml.bind;
//    to jakarta.xml.bind
    opens com.esiee.java_avance_lot_1 to javafx.fxml;
    exports com.esiee.java_avance_lot_1;
    exports jaxb_sources.com.esiee.javaproj.jaxb;
    opens jaxb_sources.com.esiee.javaproj.jaxb to javafx.fxml, jakarta.xml.bind;
}