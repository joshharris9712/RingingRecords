package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import sample.Scraper;

import java.io.File;
import java.io.IOException;

public class Controller {

	@FXML
	private TextField urlTextField;

	@FXML
	private TextField folderTextField;

	@FXML
	private Button fileChooserButton;

	@FXML
	private AnchorPane loadingPane;

	@FXML
	private AnchorPane defaultPane;


	@FXML
	void go(ActionEvent event) {
		defaultPane.setDisable(true);
		loadingPane.setVisible(true);
		new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Scraper s = new Scraper(urlTextField.getText(), folderTextField.getText());

				try {
					s.scrape();
				} catch (IOException e){
					Platform.runLater(() -> {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("A problem occurred while saving records");
						alert.setContentText(" - Make sure the search URL and download folder are correct\n - Make sure you are connected to the internet");

						alert.showAndWait();
					});
				} finally {
					Platform.runLater(() -> {
						defaultPane.setDisable(false);
						loadingPane.setVisible(false);
					});
				}


				return null;
			}
		}).start();
	}

	@FXML
	void openFileChooser(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		File f = dc.showDialog(null);
		folderTextField.setText(f.getAbsolutePath());
	}



}
