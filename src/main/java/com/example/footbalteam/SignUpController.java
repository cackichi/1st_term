package com.example.footbalteam;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;

public class SignUpController {

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField passwordConfirm_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button backButton;
    private ObservableList<Accounts> list;

    @FXML
    void initialize() {
        list = FileFootballPlayers.getInfoAccounts();
    }

    @FXML
    void signUp() {
        if(password_field.getText().equals(passwordConfirm_field.getText()) && !password_field.getText().trim().isEmpty() &&
                !login_field.getText().trim().isEmpty()){
            int call = 0;
            String file = "E:\\FootballTeam\\FootbalTeam\\src\\main\\java\\com\\example\\footbalteam\\accounts.csv";
            BufferedWriter bw;
            Accounts account1 = new Accounts(login_field.getText(), password_field.getText());
            for(Accounts account2: list){
                if(account1.getLogin().equals(account2.getLogin()) || account1.getPassword().equals(account2.getPassword()))
                    call++;
            }
            if(call == 0) {
                try {
                    bw = new BufferedWriter(new FileWriter(file, true));
                    bw.newLine();
                    bw.write(account1.toString());
                    bw.close();
                    password_field.clear();
                    passwordConfirm_field.clear();
                    login_field.clear();
                    Alert saveWindow = new Alert(Alert.AlertType.INFORMATION);
                    saveWindow.setTitle("Сохранение");
                    saveWindow.setContentText("Аккаунт создан");
                    saveWindow.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert exist = new Alert(Alert.AlertType.ERROR);
                exist.setTitle("Ошибка регистрации");
                exist.setContentText("Такие пароль или логин уже существуют");
                exist.showAndWait();
                password_field.clear();
                passwordConfirm_field.clear();
                login_field.clear();
            }
        }else{
            Alert saveWindow = new Alert(Alert.AlertType.ERROR);
            saveWindow.setTitle("Ошибка регистрации");
            saveWindow.setContentText("Пароли не совпадают или пустые логин и пароль");
            saveWindow.showAndWait();
        }
    }
    @FXML
    public void backUp(){
        backButton.getScene().getWindow().hide();
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("auth.fxml"));
        try {
            load.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent pr = load.getRoot();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Авторизация");
        stage.setScene(new Scene(pr));
        stage.show();
    }
}