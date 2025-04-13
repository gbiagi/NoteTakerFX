package cat.gbiagi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static Stage stageFX;

    @Override
    public void start(Stage stage) throws Exception {

        stageFX = stage;
        // Carrega la vista inicial des del fitxer FXML
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/assets/main_layout.fxml")));
        Scene scene = new Scene(root);

        stageFX.setScene(scene);
        stageFX.setResizable(true);
        stageFX.setTitle("NoteTaker");
        //stageFX.getIcons().add(new Image("/images/UndirLaFlotaLogo.png"));
        stageFX.show();

        // Afegeix una icona només si no és un Mac
        if (!System.getProperty("os.name").contains("Mac")) {
            stageFX.getIcons().add(new Image("/icons/icon.png"));
        }
    }

    @Override
    public void stop() {
        System.exit(1); // Kill all executor services
    }

    public static void main(String[] args) {
        launch(args);
    }

}