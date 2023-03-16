module com.esiee.java_avance_lot_1 {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires validatorfx;
                requires org.kordamp.bootstrapfx.core;
            
    opens com.esiee.java_avance_lot_1 to javafx.fxml;
    exports com.esiee.java_avance_lot_1;
}