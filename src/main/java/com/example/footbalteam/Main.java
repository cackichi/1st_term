package com.example.footbalteam;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.Optional;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("auth.fxml"));

        File file = new File("E:\\FootballTeam\\FootbalTeam\\src\\main\\java\\com\\example\\footbalteam\\accounts.csv");
        if(file.createNewFile()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("admin;admin");
            bw.close();
        }

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setResizable(false);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
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

    public static void main(String[] args) {
        launch();
    }
}