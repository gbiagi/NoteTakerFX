package cat.gbiagi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomDialogController implements Initializable {
    @FXML
    public Label tag1;
    @FXML
    public Label tag2;
    @FXML
    public TextArea noteText;
    @FXML
    public TextField tagName;
    @FXML
    public Button addTagButton, saveButton;

    private Stage dialogStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tag1.setVisible(false);
        tag2.setVisible(false);
        tagName.setVisible(false);

        addTagButton.setOnAction(event -> {createTag();});
        saveButton.setOnAction(event ->{closeDialog();});
    }

    // Dialog control
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void closeDialog() {
        // Cierra el diálogo
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    // Tags control on note creation
    public void createTag(){
        if (tag1.getText().isEmpty()) {
            // Set the textfield visible and when it presses enter save the text on the tag1
            tagNameControl(tag1);
        } else if (tag2.getText().isEmpty()) {
            // Set the textfield visible and when it presses enter save the text on the tag2
            tagNameControl(tag2);
        }
    }
    private void tagNameControl(Label tag) {
        tagName.setVisible(true);
        tagName.setOnKeyPressed(event -> {
            // Wait for enter
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                if (tagName.getText().length() <= 6) { // Not allowed if longer than 6 chars
                    tag.setText(tagName.getText());
                    tag.setVisible(true);
                    tagName.clear();
                    tagName.setVisible(false);
                    // If 2 tags are created, disable the button
                    if (!tag1.getText().isEmpty() && !tag2.getText().isEmpty()) {
                        addTagButton.setDisable(true);
                    }
                }
            }
        });
    }

    // Get & Set
    public String getNoteText() {
        return noteText.getText();
    }
    public String getTag1(){
        return tag1.getText();
    }
    public String getTag2(){
        return tag2.getText();
    }

}