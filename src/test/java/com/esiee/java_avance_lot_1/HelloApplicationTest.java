package com.esiee.java_avance_lot_1;

import com.esiee.java_avance_lot_1.vue.HomeApplication;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.IOException;

public class HelloApplicationTest {

    @Start
    private void start(Stage stage) throws IOException {
        HomeApplication application = new HomeApplication();
        application.start(stage);

    }

    @Test
    public void should_click(FxRobot fxRobot) {
        FxAssert.verifyThat("#validerButton", LabeledMatchers.hasText("Valider"));
    }
}
