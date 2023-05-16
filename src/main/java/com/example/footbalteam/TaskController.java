package com.example.footbalteam;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TaskController {

    // Кнопка "Вернутсья назад"
    @FXML
    private Button backButton;

    // Колонки таблиц оштрафованных игроков и результативных игроков
    @FXML
    private TableColumn<FootballPlayers, Integer> assists_1;

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
    private TableColumn<FootballPlayers, Integer> result_1;

    @FXML
    private TableColumn<FootballPlayers, Integer> redCards_2;

    // Таблицы оштрафованных игроков и результативных игроков
    @FXML
    private TableView<FootballPlayers> table_red_cards;

    @FXML
    private TableView<FootballPlayers> table_result;

    // Списки результативных и оштрафованных игроков
    private final ObservableList<FootballPlayers> list_result = FXCollections.observableArrayList();
    private final ObservableList<FootballPlayers> list_red_cards = FXCollections.observableArrayList();

    // Метод инициализирует окно "Индивидуальное задание"
    @FXML
    void initialize() {
        // Указание столбцам параметров, которые он будет хранить для таблицы результавных игроков
        number_1.setCellValueFactory(new PropertyValueFactory<>("number"));
        fullName_1.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        goals_1.setCellValueFactory(new PropertyValueFactory<>("goals"));
        assists_1.setCellValueFactory(new PropertyValueFactory<>("assists"));
        result_1.setCellValueFactory(new PropertyValueFactory<>("result"));
        // Указание столбцам параметров, которые он будет хранить для таблицы оштрафованных игроков
        number_2.setCellValueFactory(new PropertyValueFactory<>("number"));
        fullName_2.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        redCards_2.setCellValueFactory(new PropertyValueFactory<>("redCards"));
        // Заполняем списки результативных игроков и оштрафованных игроков
        FileFootballPlayers.getPlayersIndividual(list_result, list_red_cards);
        // Заполняем таблицы списками
        table_result.setItems(list_result);
        table_red_cards.setItems(list_red_cards);
    }

    // Метод возвращение на окно "Таблица игроков"
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

        Main.stop(stage);
    }
}
