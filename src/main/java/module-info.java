module com.esiee.java_avance_lot_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.xml.bind;
    requires mysql.connector.java;
    requires java.sql;
    requires poi.ooxml;
    requires poi.ooxml.schemas;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires lombok;
    requires poi;
    requires spire.doc;
    opens com.esiee.java_avance_lot_1.vue to javafx.fxml;
    exports com.esiee.java_avance_lot_1.vue;
    exports com.esiee.java_avance_lot_1.controller;
    exports com.esiee.java_avance_lot_1.dao;
    exports com.esiee.java_avance_lot_1.model;

    opens com.esiee.java_avance_lot_1.controller to javafx.fxml;
    opens com.esiee.java_avance_lot_1.model to jakarta.xml.bind, javafx.base;
    opens com.esiee.java_avance_lot_1.dao to jakarta.xml.bind, javafx.base;
}
