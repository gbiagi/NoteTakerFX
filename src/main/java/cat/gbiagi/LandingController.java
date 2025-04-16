package cat.gbiagi;


import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
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
import java.util.*;

public class LandingController implements Initializable {
    @FXML
    public ListView<Label> tagPanel;
    @FXML
    public VBox notesPanel;
    @FXML
    public Button addNoteButton;

    public String noteText, tag1, tag2;
    private final Map<String, List<String>> tagNoteMap = new HashMap<>(); // Relacion notas-tags
    private final List<String> allNotes = new ArrayList<>(); // Lista con todas las notas


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Añadir el tag "show all" al inicializar
        Label showAllTag = new Label("Show all");
        showAllTag.setOnMouseClicked(event -> showAllNotes());
        tagPanel.getItems().add(showAllTag);

        // Acción del botón para añadir notas
        addNoteButton.setOnAction(e -> showNewNoteDialog());

        // Clic en las etiquetas para cambiar la lista
        tagPanel.setOnMouseClicked(event -> {
            Label selectedTag = tagPanel.getSelectionModel().getSelectedItem();
            if (selectedTag != null && !selectedTag.getText().equals("show all")) {
                showNotesForTag(selectedTag.getText());
            } else {
                showAllNotes();
            }
        });
    }
    public void addNoteToList(String text, List<String> tags) {
        try {
            // Cargar el diseño de la nota desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/note.fxml"));
            AnchorPane noteNode = loader.load(); // Cargar el nodo AnchorPane desde el archivo FXML

            // Obtener el controlador para la nota y asignar el texto
            NoteController noteController = loader.getController();
            noteController.setNoteText(text);

            // Eliminar nota
            noteController.deleteBt.setOnAction(e -> {
                notesPanel.getChildren().remove(noteNode);
                allNotes.remove(text);
                tags.forEach(tag -> tagNoteMap.getOrDefault(tag, new ArrayList<>()).remove(text));
            });

            // Agregar nota al VBox
            notesPanel.getChildren().add(noteNode);
            allNotes.add(text);

            // Asociar la nota con las etiquetas
            for (String tag : tags) {
                tagNoteMap.computeIfAbsent(tag, k -> new ArrayList<>()).add(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showNewNoteDialog() {
        try {
            addNoteButton.setDisable(true);
            // Cargar el diseño del diálogo desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/noteDialog.fxml"));
            Parent root = loader.load();

            CustomDialogController controller = loader.getController();
            Stage dialogStage = new Stage();
            controller.setDialogStage(dialogStage);

            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // Get the note text and tags
            if (!controller.getNoteText().isEmpty()) {
                String noteText = controller.getNoteText();
                List<String> tags = new ArrayList<>();
                if (!controller.getTag1().isEmpty()) {
                    tags.add(controller.getTag1());
                    addNewTag(new Label(controller.getTag1()));
                }
                if (!controller.getTag2().isEmpty()) {
                    tags.add(controller.getTag2());
                    addNewTag(new Label(controller.getTag2()));
                }
                addNoteToList(noteText, tags);
            }
            addNoteButton.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addNewTag(Label tag) {
        // Evitar duplicados en la lista de tags
        if (tagPanel.getItems().stream().noneMatch(t -> t.getText().equals(tag.getText()))) {
            tagPanel.getItems().add(tag);
        }
    }
    public void showNotesForTag(String tag) {
        notesPanel.getChildren().clear(); // Limpiar el panel antes de mostrar las notas
        List<String> notes = new ArrayList<>(tagNoteMap.getOrDefault(tag, Collections.emptyList())); // Copiar la lista para evitar modificaciones concurrentes

        for (String note : notes) {
            // Crear y agregar las notas relacionadas al panel
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/note.fxml"));
                AnchorPane noteNode = loader.load();

                NoteController noteController = loader.getController();
                noteController.setNoteText(note);

                noteController.deleteBt.setOnAction(e -> {
                    notesPanel.getChildren().remove(noteNode);
                    tagNoteMap.getOrDefault(tag, new ArrayList<>()).remove(note);
                });

                notesPanel.getChildren().add(noteNode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void showAllNotes() {
        notesPanel.getChildren().clear();
        for (String note : allNotes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/note.fxml"));
                AnchorPane noteNode = loader.load();

                NoteController noteController = loader.getController();
                noteController.setNoteText(note);

                noteController.deleteBt.setOnAction(e -> {
                    notesPanel.getChildren().remove(noteNode);
                    allNotes.remove(note);
                    tagNoteMap.values().forEach(list -> list.remove(note));
                });

                notesPanel.getChildren().add(noteNode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}