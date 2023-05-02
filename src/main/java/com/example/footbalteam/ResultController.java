package com.example.footbalteam;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ResultController {

    @FXML
    private TableColumn<FootballPlayers, Integer> assists_1;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<FootballPlayers, String> fullName_1;

    @FXML
    private TableColumn<FootballPlayers, String> fullName_2;

    @FXML
    private TableColumn<FootballPlayers, Integer> goals_1;

    @FXML
    private TableColumn<FootballPlayers, Integer> number_1;

    @FXML
    private TableColumn<FootballPlayers, Integer> number_2;

    @FXML
    private TableColumn<FootballPlayers, Integer> redCards_2;

    @FXML
    private TableColumn<FootballPlayers, Integer> result_1;

    @FXML
    private Button saveChangesButton;

    @FXML
    private TableView<FootballPlayers> table_red_cards;

    @FXML
    private TableView<FootballPlayers> table_result;

    private final ObservableList<FootballPlayers> list_result = FXCollections.observableArrayList();
    private final ObservableList<FootballPlayers> list_red_cards = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        number_1.setCellValueFactory(new PropertyValueFactory<>("number"));
        fullName_1.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        goals_1.setCellValueFactory(new PropertyValueFactory<>("goals"));
        assists_1.setCellValueFactory(new PropertyValueFactory<>("assists"));
        result_1.setCellValueFactory(new PropertyValueFactory<>("result"));

        number_2.setCellValueFactory(new PropertyValueFactory<>("number"));
        fullName_2.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        redCards_2.setCellValueFactory(new PropertyValueFactory<>("redCards"));

        FileFootballPlayers.getPlayersIndividual(list_result,list_red_cards);

        table_result.setItems(list_result);
        table_red_cards.setItems(list_red_cards);
    }

    @FXML
    public void backUp() {
        backButton.getScene().getWindow().hide();
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("table.fxml"));
        try {
            load.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent pr = load.getRoot();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Таблица игроков");
        stage.setScene(new Scene(pr));
        stage.show();
    }
}
