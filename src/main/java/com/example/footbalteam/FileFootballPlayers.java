package com.example.footbalteam;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;

public class FileFootballPlayers {

    //Метод заполнения таблицы из файла
    public static ObservableList<FootballPlayers> getInfoPlayers() {
        ObservableList<FootballPlayers> table = FXCollections.observableArrayList();
        // Путь к файлу игроков
        String file = "E:\\FootballTeam\\FootbalTeam\\src\\main\\java\\com\\example\\footbalteam\\table.csv";
        BufferedReader br = null;
        String line;
        String splitSym = ";";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                // Считываем строку из файла и разделяем ее на подстроки по разделителю и заполянем ими массив строк
                String[] call = line.split(splitSym);
                // Заполняем список table
                table.add(new FootballPlayers(Integer.parseInt(call[0]), Integer.parseInt(call[3]), Integer.parseInt(call[4]),
                        Integer.parseInt(call[5]), Integer.parseInt(call[6]), Integer.parseInt(call[7]), call[1], Date.analise(call[2])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return table;
    }

    //Метод сохранения таблицы в файл
    public static void setInfoPlayers(ObservableList<FootballPlayers> table) {
        // Путь к файлу игроеов
        String file = "E:\\FootballTeam\\FootbalTeam\\src\\main\\java\\com\\example\\footbalteam\\table.csv";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            // Записываем игроков в файл
            for (FootballPlayers fp : table) {
                bw.write(fp.toString());
                bw.newLine();
            }
            bw.close();
            Alert saveWindow = new Alert(Alert.AlertType.INFORMATION, "Сохранено", ButtonType.OK);
            saveWindow.setContentText("Данные сохранены");
            saveWindow.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Этот метод считывает информацию из файла учетных записей
    public static ObservableList<Accounts> getInfoAccounts() {
        // Файл учетных записей
        String file = "E:\\FootballTeam\\FootbalTeam\\src\\main\\java\\com\\example\\footbalteam\\accounts.csv";
        String line, spliter = ";";
        ObservableList<Accounts> list = FXCollections.observableArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                // Считываем строку из файла и разделяем ее на подстроки по разделителю и заполянем ими массив строк
                String[] call = line.split(spliter);
                // Заполняем список list
                list.add(new Accounts(call[0], call[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    // Этот метод считывает информацию из файла игроков для заполнения таблиц результативности и оштрафованных игроков
    public static void getPlayersIndividual(ObservableList<FootballPlayers> list_result, ObservableList<FootballPlayers> list_red_cards) {
        // Файл игроков
        String file = "E:\\FootballTeam\\FootbalTeam\\src\\main\\java\\com\\example\\footbalteam\\table.csv";
        BufferedReader br = null;
        String line;
        String splitSym = ";";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                // Считываем строку из файла и разделяем ее на подстроки по разделителю и заполянем ими массив строк
                String[] call = line.split(splitSym);
                // Заполняем список list_result
                // Т.к. нам не нужно брать все данные мы берем из файла только нужные
                list_result.add(new FootballPlayers(Integer.parseInt(call[0]), call[1], Integer.parseInt(call[4]),
                        Integer.parseInt(call[5])));
                // Если количество красных карточек не равно нулю то загоняем этого игрока в список list_red_cards
                if (Integer.parseInt(call[7]) != 0) {
                    list_red_cards.add(new FootballPlayers(Integer.parseInt(call[0]), call[1], Integer.parseInt(call[7])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // Сортируем и удаляем ненужное
        sortAndDelete(list_result);
    }

    // Метод сортирует список по результативности и удаляет игроков, которые не входят в топ 6
    public static void sortAndDelete(ObservableList<FootballPlayers> list_result) {
        FootballPlayers fp1, fp2;
        //Сортируем список игроков по результативности
        int length = list_result.size();
        for (int i = 0; i + 1 < length; i++) {
            for (int j = 0; j + 1 < length - i; j++) {
                fp1 = list_result.get(j + 1);
                fp2 = list_result.get(j);
                if (fp1.getResult() > fp2.getResult()) {
                    list_result.set(j + 1, fp2);
                    list_result.set(j, fp1);
                }
            }
        }
        //Удаляем всех игроков кроме 6
        while (list_result.size() != 6) {
            list_result.remove(6);
        }
    }
}