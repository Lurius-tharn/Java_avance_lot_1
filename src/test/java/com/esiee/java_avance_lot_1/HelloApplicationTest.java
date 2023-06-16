package com.esiee.java_avance_lot_1;

import com.esiee.java_avance_lot_1.vue.HomeApplication;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.NodeQueryUtils;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
public class HelloApplicationTest {

    @Mock
    FileChooser fileChooser = Mockito.mock(FileChooser.class);
    Stage stage;

    @Start
    private void start(Stage stage) throws IOException {
        this.stage = stage;
        HomeApplication application = new HomeApplication();
        application.start(stage);

    }

    @Test
    void should_contain_button_with_text(FxRobot robot) {
        FxAssert.verifyThat("#validerButton", LabeledMatchers.hasText("Valider"));
    }

    @Test
    void should_open_a_file_xml(FxRobot robot) throws TimeoutException {

        robot.clickOn("#menuFile");
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> robot.lookup("#menuOpen").match(NodeQueryUtils.isVisible()).tryQuery().isPresent());

        robot.clickOn("#menuOpen");
        when(fileChooser.showOpenDialog(stage)).thenReturn(null);

        // Vérifiez que le FileChooser est ouvert
        FxAssert.verifyThat("#validerButton", LabeledMatchers.hasText("Valider"));
    }
}
