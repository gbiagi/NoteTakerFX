package cat.gbiagi;


import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LandingController implements Initializable {
    @FXML
    public ListView<NoteController> sidePanel;
    @FXML
    public VBox notesPanel;
    @FXML
    public Button addNoteButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar acción del botón para añadir notas
        addNoteButton.setOnAction(e -> {
            showNewNoteDialog();
        });
    }
    public void addNoteToList(String text) {
        try {
            // Cargar el diseño de la nota desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/note.fxml"));

            // Crear un controlador para la nota y asignar el texto
            NoteController noteController = new NoteController();
            loader.setController(noteController);

            // Cargar el nodo AnchorPane desde el archivo FXML
            AnchorPane noteNode = loader.load();

            // Agregar el nodo al VBox
            notesPanel.getChildren().add(noteNode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNewNoteDialog() {
        try {
            // Cargar el diseño del diálogo desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/noteDialog.fxml"));
            Parent root = loader.load();

            CustomDialogController controller = loader.getController();
            Stage dialogStage = new Stage();
            controller.setDialogStage(dialogStage);

            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            String noteText, tag1, tag2;

            // Get the note text and tags if it has
            if (!controller.getNoteText().isEmpty()) {
                noteText = controller.getNoteText();
            }
            if (!controller.getTag1().isEmpty()) {
                tag1 = controller.getTag1();
            }
            if (!controller.getTag2().isEmpty()) {
                tag2 = controller.getTag2();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}