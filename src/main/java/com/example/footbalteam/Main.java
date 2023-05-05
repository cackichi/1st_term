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

        File file1 = new File("E:\\FootballTeam\\FootbalTeam\\src\\main\\java\\com\\example\\footbalteam\\accounts.csv");
        File file2 = new File("E:\\FootballTeam\\FootbalTeam\\src\\main\\java\\com\\example\\footbalteam\\table.csv");
        if(file1.createNewFile()) {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(file1));
            bw1.write("admin;admin");
            bw1.close();
        }
        file2.createNewFile();

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setResizable(false);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
        stop(stage);

    }

    public static void main(String[] args) {
        launch();
    }

    public static void stop(Stage stage){
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