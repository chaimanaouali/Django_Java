package Services;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private TableView<Service> tableView;
    private ObservableList<Service> allServices;

    @Override
    public void start(Stage primaryStage) {
        // Create TableView
        tableView = new TableView<>();
        TableColumn<Service, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());

        TableColumn<Service, String> responsibleColumn = new TableColumn<>("Responsible");
        responsibleColumn.setCellValueFactory(data -> data.getValue().responsibleProperty());

        TableColumn<Service, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        tableView.getColumns().addAll(idColumn, responsibleColumn, statusColumn);

        // Create TextField for search
        TextField searchField = new TextField();
        searchField.setPromptText("Search");

        // Layout
        VBox root = new VBox(searchField, tableView);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Service Search");
        primaryStage.show();

        // Sample data
        allServices = FXCollections.observableArrayList(
                new Service("1", "John Doe", "Pending"),
                new Service("2", "Alice Smith", "Completed"),
                new Service("3", "Bob Johnson", "In Progress"),
                new Service("4", "Emily Brown", "Pending")
        );
        tableView.setItems(allServices);

        // Add listener to the search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    // Method to filter TableView based on search text
    private void filterTable(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            tableView.setItems(allServices);
            return;
        }

        ObservableList<Service> filteredList = FXCollections.observableArrayList();

        for (Service service : allServices) {
            if (service.getId().toLowerCase().contains(searchText.toLowerCase()) ||
                    service.getResponsible().toLowerCase().contains(searchText.toLowerCase()) ||
                    service.getStatus().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(service);
            }
        }

        tableView.setItems(filteredList);
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Service class representing your service object
    public static class Service {
        private final String id;
        private final String responsible;
        private final String status;

        public Service(String id, String responsible, String status) {
            this.id = id;
            this.responsible = responsible;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public String getResponsible() {
            return responsible;
        }

        public String getStatus() {
            return status;
        }

        public StringProperty idProperty() {
            return new SimpleStringProperty(id);
        }

        public StringProperty responsibleProperty() {
            return new SimpleStringProperty(responsible);
        }

        public StringProperty statusProperty() {
            return new SimpleStringProperty(status);
        }
    }
}
