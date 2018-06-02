package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import sample.Scraper;

import java.io.File;

public class Controller {

	@FXML
	private TextField urlTextField;

	@FXML
	private TextField folderTextField;

	@FXML
	private Button fileChooserButton;

	@FXML
	void go(ActionEvent event) {
		Scraper s = new Scraper(urlTextField.getText(), folderTextField.getText());
		s.scrape();
	}

	@FXML
	void openFileChooser(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		File f = dc.showDialog(null);
		folderTextField.setText(f.getAbsolutePath());
	}

}
