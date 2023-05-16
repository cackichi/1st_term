package com.example.footbalteam;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.Optional;

public class SignInController {

    // Поля для ввода логина и пароля
    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    // Кнопки "Войти" и "Регистрация"
    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    // Список учетных записей
    private ObservableList<Accounts> accounts;

    @FXML
    void initialize() {
        // Заполняем список учетных записей из файла
        accounts = FileFootballPlayers.getInfoAccounts();
    }

    // Метод кнопки "Войти"
    @FXML
    public void signIn() {
        int length = accounts.size();
        int call = 0;
        for (Accounts accounts : accounts) {
            // Проверяем совпадает ли введенные логин и пароля с существующими
            // Если да, то входим
            // Иначе ошибка
            if (login_field.getText().equals(accounts.getLogin()) && password_field.getText().equals(accounts.getPassword())) {
                signInButton.getScene().getWindow().hide();
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
            } else call++;
        }
        if (call == length) {
            Alert authAlert = new Alert(Alert.AlertType.ERROR);
            authAlert.setTitle("Ошибка авторизации");
            authAlert.setContentText("Такие логин и пароль не найдены\nПовторите попытку");
            authAlert.showAndWait();
        }
    }

    // Метод кнопки "Регистрация" для перехода на окно "Регистрация"
    @FXML
    public void signUp() {
        signUpButton.getScene().getWindow().hide();
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("sign_up.fxml"));
        try {
            load.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent pr = load.getRoot();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(pr));
        stage.show();

        Main.stop(stage);
    }
}