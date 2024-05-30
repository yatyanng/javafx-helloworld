package com.example.javafx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@SpringBootApplication
public class Boot extends javafx.application.Application {

	private static final Logger logger = LoggerFactory.getLogger(Boot.class);

	private static ApplicationContext applicationContext;

	@Bean("searchText")
	public StringBuffer searchText() {
		return new StringBuffer();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(Boot.class.getResource("/SearchController.fxml"));
		loader.setControllerFactory(clazz -> applicationContext.getBean(clazz));
        AnchorPane page = (AnchorPane) loader.load();
        Scene scene = new Scene(page);

        scene.getStylesheets().add("/search.css");

        primaryStage.setTitle(applicationContext.getEnvironment().getProperty("app.title"));
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	public static void main(String[] args) {
		String configDirectory = "conf";
		if (args.length > 0) {
			configDirectory = args[0];
		}
		logger.info("config directory: {}", configDirectory);

		if (new java.io.File(configDirectory).exists() && new java.io.File(configDirectory).isDirectory()) {
			System.setProperty("spring.config.location", configDirectory + "/springboot.yml");
			System.setProperty("logging.config", configDirectory + "/logback.xml");
		}
		applicationContext = SpringApplication.run(Boot.class, args);
		launch(args);
	}
}
