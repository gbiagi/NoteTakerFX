package cat.gbiagi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

// aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

public class NoteController  {
    @FXML
    public AnchorPane notePane;
    @FXML
    public Text textNote;
    @FXML
    public Button deleteBt;

    public String getNoteText() {
        return textNote.getText();
    }

    public void setNoteText(String noteText) {
        this.textNote.setText(noteText);
    }

}
