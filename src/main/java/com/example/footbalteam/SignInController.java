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

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signInButton;

    private ObservableList<Accounts> accounts;

    @FXML
    void initialize() {
        accounts = FileFootballPlayers.getInfoAccounts();
    }
    @FXML
    public void signIn() {
        int length = accounts.size();
        int call = 0;
        for (Accounts accounts : accounts) {
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

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        Alert closeAlert = new Alert(Alert.AlertType.WARNING, "Внимание", ButtonType.OK, ButtonType.CANCEL);
                        closeAlert.setTitle("Внимание!");
                        closeAlert.setHeaderText("Закрытие приложения");
                        closeAlert.setContentText("Вы действительно хотите закрыть приложение?");
                        Optional<ButtonType> result = closeAlert.showAndWait();
                        if(result.get() == ButtonType.OK){
                            stage.close();
                        } else windowEvent.consume();
                    }
                } );
            } else call++;
        }
        if(call == length){
            Alert authAlert = new Alert(Alert.AlertType.ERROR);
            authAlert.setTitle("Ошибка авторизации");
            authAlert.setContentText("Такие логин и пароль не найдены\nПовторите попытку");
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

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Alert closeAlert = new Alert(Alert.AlertType.WARNING, "Внимание", ButtonType.OK, ButtonType.CANCEL);
                closeAlert.setTitle("Внимание!");
                closeAlert.setHeaderText("Закрытие приложения");
                closeAlert.setContentText("Вы действительно хотите закрыть приложение?");
                Optional<ButtonType> result = closeAlert.showAndWait();
                if(result.get() == ButtonType.OK){
                    stage.close();
                } else windowEvent.consume();
            }
        } );
    }
}