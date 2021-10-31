package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelloFX extends Application {

  @Override
  public void start(Stage primaryStage) {
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label l =
        new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    Scene scene = new Scene(new StackPane(l), 640, 480);
    primaryStage.setScene(scene);
    primaryStage.setTitle("My First JavaFX App");
    primaryStage.show();

    Label l2 = new Label("Close me");
    Scene scene2 = new Scene(new StackPane(l2), 320, 240);
    Stage secondaryStage = new Stage();
    secondaryStage.setScene(scene2);
    secondaryStage.initOwner(primaryStage);
    secondaryStage.initModality(Modality.WINDOW_MODAL);
    secondaryStage.setTitle("Close me");
    secondaryStage.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }

}
