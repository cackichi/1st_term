package com.example.footbalteam;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;

public class SignInController {

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signInButton;

    private ObservableList<Accounts> list;

    @FXML
    void initialize() {
        list = FileFootballPlayers.getInfoAccounts();
    }
    @FXML
    public void signIn() {
        int length = list.size();
        int call = 0;
        for (Accounts accounts : list) {
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
            } else call++;
        }
        if(call == length){
            Alert authAlert = new Alert(Alert.AlertType.ERROR);
            authAlert.setTitle("Ошибка авторизации");
            authAlert.setContentText("Такие логин и пароль не найдены");
            authAlert.showAndWait();
        }
    }
    @FXML
    public void signUp(){
        signInButton.getScene().getWindow().hide();
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
    }
}