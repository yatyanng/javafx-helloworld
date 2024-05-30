package com.example.javafx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.javafx.model.Person;

@Component
public class SearchController {

	public static final int PAGE_ITEMS_COUNT = 10;

	@FXML
	private TextField searchField;
	@FXML
	private Button searchButton;

	@Autowired
	@Qualifier("searchText")
	private StringBuffer searchText;

	@FXML
	private TableView<Person> tableView;
	@FXML
	private VBox dataContainer;

	private ObservableList<Person> masterData = FXCollections.observableArrayList();
	private ObservableList<Person> results = FXCollections.observableList(masterData);

	public SearchController() {
		masterData.add(new Person(5, "John", true));
		masterData.add(new Person(7, "Albert", true));
		masterData.add(new Person(11, "Monica", false));
	}

	@FXML
	private void initialize() {

		// search panel
		searchButton.setText("Search");
		searchButton.setOnAction(event -> loadData());
		searchButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");

		searchField.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				loadData();
			}
		});

		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			searchText.setLength(0);
			searchText.append(newValue);
		});

		initTable();

	}

	@SuppressWarnings("unchecked")
	private void initTable() {
		tableView = new TableView<>(FXCollections.observableList(masterData));
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<Person, ?> id = new TableColumn<>("ID");
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Person, ?> name = new TableColumn<>("NAME");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Person, ?> employed = new TableColumn<>("EMPLOYED");
		employed.setCellValueFactory(new PropertyValueFactory<>("isEmployed"));
		tableView.getColumns().addAll(id, name, employed);

		dataContainer.getChildren().add(tableView);
	}

	private void loadData() {

		String searchText = searchField.getText();

		Task<ObservableList<Person>> task = new Task<ObservableList<Person>>() {
			@Override
			protected ObservableList<Person> call() throws Exception {
				updateMessage("Loading data");
				return FXCollections.observableArrayList(
						masterData.stream().filter(value -> value.getName().toLowerCase().contains(searchText))
								.collect(Collectors.toList()));
			}
		};

		task.setOnSucceeded(event -> {
			results = task.getValue();
			tableView.setItems(FXCollections.observableList(results));
		});

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

}
