package com.example.javafx.springboot.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SpringBootApplication
public class JavaFxApplicationSupport extends javafx.application.Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		String javaVersion = System.getProperty("java.version");
		String javafxVersion = System.getProperty("javafx.version");
		Label label = new Label(
				"Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ". Click to close me!");
		Scene scene = new Scene(new StackPane(label), 640, 480);
		primaryStage.setScene(scene);
		primaryStage.setTitle("My First JavaFX App");
		primaryStage.initStyle(StageStyle.DECORATED);
		label.setOnMouseClicked(evt -> {
			primaryStage.close();
		});
		primaryStage.show();

		Button button = new Button("Close me");
		Scene scene2 = new Scene(button, 320, 240);
		Stage secondaryStage = new Stage();
		secondaryStage.setScene(scene2);
		secondaryStage.initOwner(primaryStage);
		secondaryStage.initModality(Modality.WINDOW_MODAL);
		secondaryStage.setTitle("Close me");
		button.setOnAction(actionEvent -> {
			secondaryStage.close();
		});
		secondaryStage.show();
	}
	
	public static void boot(String[] args) {
		launch(args);
	}

}
