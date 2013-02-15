/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.passwordgen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * FXML Controller class
 *
 * @author sk
 */
public class MainWindowController implements Initializable {

	@FXML
	private ComboBox<Integer> generateLengthComboBox;
	@FXML
	private ComboBox<String> generateCharacterTypeComboBox;
	@FXML
	private TextField generatedTextField;

	@FXML
	protected void handleButtonAction(ActionEvent event) {

		// TODO vaidation...

		Integer length = generateLengthComboBox.getValue();
		if (length == null) {
			// TODO
			return;
		}
		String characterTypeText = generateCharacterTypeComboBox.getValue();
		if (characterTypeText == null) {
			return;
		}
		String generatedText = CharacterType.characterTypeOfText(characterTypeText).generate(length.intValue());
		generatedTextField.setText(generatedText);
	}

	@FXML
	protected void handleCopyAction(ActionEvent event) {

		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(generatedTextField.getText());
		clipboard.setContent(content);
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		Config config = new ConfigLoader().load();
		generateLengthComboBox.getItems().clear();
		for (int i = 1; i < config.getMaxCharacterLength() + 1; i++) {
			generateLengthComboBox.getItems().add(i);
		}
		generateLengthComboBox.getSelectionModel().select(Integer.valueOf(config.getDefaultCharacterLength()));

		generateCharacterTypeComboBox.getItems().clear();
		generateCharacterTypeComboBox.getItems().addAll(CharacterType.allTextValues());
		generateCharacterTypeComboBox.getSelectionModel().select(CharacterType.ALPHA_NUMERIC.text);

	}	
}
