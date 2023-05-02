package com.example.footbalteam;

import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;


public class TableController {
    @FXML
    private TableColumn<FootballPlayers, Integer> assists;
    @FXML
    private TableColumn<FootballPlayers, LocalDate> dateOfBirth;
    @FXML
    private TableColumn<FootballPlayers, String> fullName;
    @FXML
    private TableColumn<FootballPlayers, Integer> goals;
    @FXML
    private TableColumn<FootballPlayers, Integer> matches;
    @FXML
    private TableColumn<FootballPlayers, Integer> number;
    @FXML
    private TableColumn<FootballPlayers, Integer> redCards;
    @FXML
    private Button saveButton;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button individualButton;
    @FXML
    private TableView<FootballPlayers> table_football_players;
    @FXML
    private TableColumn<FootballPlayers, Integer> yellowCards;
    @FXML
    private ComboBox<String> comboSort;
    @FXML
    private ComboBox<String> comboFilter;
    @FXML
    private TextField fullName_field;
    @FXML
    private TextField dateOfBirth_field;
    @FXML
    private TextField matches_field;
    @FXML
    private TextField goals_field;
    @FXML
    private TextField assists_field;
    @FXML
    private TextField yellowCards_field;
    @FXML
    private TextField redCards_field;
    @FXML
    private TextField findFilter;

    //ObservableList - список на основе ArrayList.
    // Интерфейс ObservableList помимо интерфейса List наследует ещё интерфейс Observable,
    // в котором определены методы addListener() и removeListener() для того, чтобы можно было ловить события
    // (для ObservableList это события при изменении списка).
    private ObservableList<FootballPlayers> list = FXCollections.observableArrayList();
    private ObservableList<FootballPlayers> example = FXCollections.observableArrayList();

    //Этот метод вызывается автоматически при запуске
    @FXML
    void initialize() {
        stopButton.setVisible(false);
        saveButton.setDisable(true);

        dateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        matches.setCellValueFactory(new PropertyValueFactory<>("matches"));
        goals.setCellValueFactory(new PropertyValueFactory<>("goals"));
        assists.setCellValueFactory(new PropertyValueFactory<>("assists"));
        yellowCards.setCellValueFactory(new PropertyValueFactory<>("yellowCards"));
        redCards.setCellValueFactory(new PropertyValueFactory<>("redCards"));

        //Заполняем таблицу данными из файла
        list = FileFootballPlayers.getInfoPlayers();
        table_football_players.setItems(list);
        example.setAll(list);

        comboSort.setItems(FXCollections.observableArrayList("Номер в списке","ФИО","Дата рождения","Кол-во матчей",
                "Голы","Передачи","Желтые карточки","Красные карточки","Сортировка"));
        comboSort.setValue("Сортировка");

        comboFilter.setItems(FXCollections.observableArrayList("Номер в списке","ФИО","Дата рождения","Кол-во матчей",
                "Голы","Передачи","Желтые карточки","Красные карточки","Поиск"));
        comboFilter.setValue("Поиск");
    }
    @FXML
    public void sortPlayers(){
        int call = comboSort.getSelectionModel().getSelectedIndex();
        int length = list.size();

        if(call == 0) sortNumber(length);
        if(call == 1) sortFullName(length);
        if(call == 2) sortDateOfBirth(length);
        if(call == 3) sortMatches(length);
        if(call == 4) sortGoals(length);
        if(call == 5) sortAssists(length);
        if(call == 6) sortYellowCards(length);
        if(call == 7) sortRedCards(length);
        if(call == 8) {
            sortNumber(length);
            comboSort.setValue("Сортировка");
        }
    }
    @FXML
    public void filterPlayers(){
        int call = comboFilter.getSelectionModel().getSelectedIndex();
        ObservableList<FootballPlayers> prompt = FXCollections.observableArrayList();
        String str2;

        if(!findFilter.getText().trim().isEmpty()) {
            list.setAll(example);
            str2 = findFilter.getText();

            if (call == 0) findNumber(str2, prompt);
            if (call == 1) findFullName(str2, prompt);
            if (call == 2) findDateOfBirth(str2,prompt);
            if (call == 3) findMatches(str2, prompt);
            if (call == 4) findGoals(str2, prompt);
            if (call == 5) findAssists(str2, prompt);
            if (call == 6) findYellowCards(str2, prompt);
            if (call == 7) findRedCards(str2, prompt);
            if (call != 8) stopButton.setVisible(true);
        } else {
            list.setAll(example);
        }

    }
    @FXML
    public void stopFilter(){
        stopButton.setVisible(false);
        findFilter.clear();
        comboFilter.setValue("Поиск");
        list.setAll(example);
    }
    //Метод добавления новых игроков в таблицу
    @FXML
    public void addPlayers() {
        int x = example.size()+1;
        if(isRightInput()){
            FootballPlayers fp = new FootballPlayers();
            fp.setNumber(x);
            fp.setFullName(fullName_field.getText());
            fp.setDateOfBirth(Date.analise(dateOfBirth_field.getText()));
            fp.setMatches(Integer.parseInt(matches_field.getText()));
            fp.setGoals(Integer.parseInt(goals_field.getText()));
            fp.setAssists(Integer.parseInt(assists_field.getText()));
            fp.setYellowCards(Integer.parseInt(yellowCards_field.getText()));
            fp.setRedCards(Integer.parseInt(redCards_field.getText()));
            clearAll();
            example.add(fp);
            saveButton.setDisable(false);
        }
    }
    //Метод проверки введеных данных в текстовые поля
    private boolean isRightInput(){
        String sms = "";
        if(fullName_field == null || fullName_field.getText().trim().isEmpty()) sms += "Пустое поле ФИО\n";
        if(dateOfBirth_field == null || dateOfBirth_field.getText().trim().isEmpty()) sms += "Пустое поле даты рождения\n";
        else{
            if (!Date.isDate(dateOfBirth_field.getText())){
                sms += "Используйте формат дд.мм.гггг\n";
            }
        }
        if(matches_field.getText().trim().isEmpty()) sms += "Пустое поле матчей\n";
        if(goals_field.getText().trim().isEmpty()) sms += "Пустое поле голов\n";
        if(assists_field.getText().trim().isEmpty()) sms += "Пустое поле голевых передач\n";
        if(yellowCards_field.getText().trim().isEmpty()) sms += "Пустое поле желтых карточек\n";
        if(redCards_field.getText().trim().isEmpty()) sms += "Пустое поле красных карточек\n";
        try {
            int a = Integer.parseInt(matches_field.getText());
            int b = Integer.parseInt(goals_field.getText());
            int c = Integer.parseInt(assists_field.getText());
            int d = Integer.parseInt(yellowCards_field.getText());
            int e = Integer.parseInt(redCards_field.getText());
            if(a < 0 || b < 0 || c < 0 || d < 0 || e < 0) throw new NegativeException();
        } catch (NumberFormatException e) {
            sms += "Неверный ввод числа\n";
        } catch (NegativeException e){
            sms += "Отрицательный ввод\n";
        }
        if(sms.length() == 0){
            return true;
        }else{
            Alert emptyField = new Alert(Alert.AlertType.ERROR);
            emptyField.setTitle("Ошибка заполнения");
            emptyField.setHeaderText("Пожалуйста, заполните поля корректно");
            emptyField.setContentText(sms);
            emptyField.showAndWait();
            return false;
        }
    }
    @FXML
    public void changePlayers(){
        if(!table_football_players.getSelectionModel().isEmpty()) {
            addButton.setDisable(true);
            deleteButton.setDisable(true);
            saveButton.setDisable(true);
            FootballPlayers fp = table_football_players.getSelectionModel().getSelectedItem();
            fullName_field.setText(fp.getFullName());
            dateOfBirth_field.setText(Date.form(fp.getDateOfBirth()));
            matches_field.setText(String.valueOf(fp.getMatches()));
            goals_field.setText(String.valueOf(fp.getGoals()));
            assists_field.setText(String.valueOf(fp.getAssists()));
            yellowCards_field.setText(String.valueOf(fp.getYellowCards()));
            redCards_field.setText(String.valueOf(fp.getRedCards()));
            saveChangesButton.setVisible(true);
        } else {
            Alert noSelect = new Alert(Alert.AlertType.WARNING);
            noSelect.setHeaderText("Пустой выбор");
            noSelect.setTitle("Предупреждение");
            noSelect.setContentText("Вы ничего не выбрали для редактирования");
            noSelect.showAndWait();
        }
    }
    @FXML
    public void saveChanges(){
        if(isRightInput()){
            int call = table_football_players.getSelectionModel().getSelectedItem().getNumber();
            int num = table_football_players.getSelectionModel().getSelectedIndex();
            FootballPlayers fp = new FootballPlayers();
            fp.setNumber(call);
            fp.setFullName(fullName_field.getText());
            fp.setDateOfBirth(Date.analise(dateOfBirth_field.getText()));
            fp.setMatches(Integer.parseInt(matches_field.getText()));
            fp.setGoals(Integer.parseInt(goals_field.getText()));
            fp.setAssists(Integer.parseInt(assists_field.getText()));
            fp.setYellowCards(Integer.parseInt(yellowCards_field.getText()));
            fp.setRedCards(Integer.parseInt(redCards_field.getText()));
            clearAll();
            list.set(num, fp);
            sortNumber(list.size());
            example.set(call-1,fp);
            saveChangesButton.setVisible(false);
            addButton.setDisable(false);
            deleteButton.setDisable(false);
            saveButton.setDisable(false);
        }
    }
    //Метод очистки полей
    @FXML
    public void clearAll(){
        fullName_field.clear();
        dateOfBirth_field.clear();
        matches_field.clear();
        goals_field.clear();
        assists_field.clear();
        yellowCards_field.clear();
        redCards_field.clear();
    }
    //Метод сохранения таблицы в файл
    @FXML
    public void saveAll(){
        FileFootballPlayers.setInfoPlayers(example);
        saveButton.setDisable(true);
    }
    //Метод удаления игрока из таблицы
    @FXML
    public void delPlayers() {
        if (table_football_players.getSelectionModel().isEmpty()) {
            Alert noSelect = new Alert(Alert.AlertType.WARNING);
            noSelect.setHeaderText("Пустой выбор");
            noSelect.setTitle("Предупреждение");
            noSelect.setContentText("Вы ничего не выбрали для удаления");
            noSelect.showAndWait();
        } else {
            Alert delAlert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK, ButtonType.CANCEL);
            delAlert.setTitle("Предупреждение");
            delAlert.setHeaderText("Удаление данных");
            delAlert.setContentText("Вы уверены что хотите удалить эти данные?\nДанные исчезнут");
            delAlert.showAndWait();

            if (delAlert.getResult() == ButtonType.OK) {
                FootballPlayers fp = table_football_players.getSelectionModel().getSelectedItem();
                int num = table_football_players.getSelectionModel().getSelectedItem().getNumber();
                int length_exm = example.size();
                if(length_exm != num){
                    for(int i = num; i < length_exm; i++) {
                        example.get(i).setNumber(i);
                    }
                }
                findFilter.clear();
                stopButton.setVisible(false);
                comboFilter.setValue("Поиск");
                comboSort.setValue("Сортировка");
                list.removeAll(fp);
                example.removeAll(fp);
                list.setAll(example);
                saveButton.setDisable(false);
            } else delAlert.close();
        }
    }

    @FXML
    public void individualTask(){
        if(saveButton.isDisable()) {
            individualButton.getScene().getWindow().hide();
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("result.fxml"));
            try {
                load.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent pr = load.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Таблица результативности");
            stage.setScene(new Scene(pr));
            stage.show();
        } else {
            Alert saveTrouble = new Alert(Alert.AlertType.WARNING,"Warning", ButtonType.OK, ButtonType.CANCEL);
            saveTrouble.setTitle("Потеря данных");
            saveTrouble.setHeaderText("Внимание!");
            saveTrouble.setContentText("Вы не сохранили данные, это приведет к потере." +
                    "\nХотите продолжить с данными последнего сохранения?");
            saveTrouble.showAndWait();
            if(saveTrouble.getResult() == ButtonType.OK){
                individualButton.getScene().getWindow().hide();
                saveButton.setDisable(true);
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("result.fxml"));
                try {
                    load.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent pr = load.getRoot();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("Таблица результативности");
                stage.setScene(new Scene(pr));
                stage.show();
            } else saveTrouble.close();
        }
    }

    public void sortNumber(int length){
        FootballPlayers fp1, fp2;
        for(int i = 0; i + 1 < length ; i++){
            for(int j = 0; j + 1 < length - i; j++){
                fp1 = list.get(j + 1);
                fp2 = list.get(j);
                if(fp1.getNumber() < fp2.getNumber()){
                    list.set(j + 1, fp2);
                    list.set(j, fp1);
                }
            }
        }
    }
//        В Java compareTo() получает значение 0, если аргумент
//        является строкой лексически равной данной строке; значение меньше 0,
//        если аргумент является строкой лексически большей, чем сравниваемая строка;
//        и значение больше 0, если аргумент является строкой лексически меньшей этой строки.
    public void sortFullName(int length){
        FootballPlayers fp1, fp2;
        for(int i = 0; i < length - 1 ; i++){
            for(int j = i + 1; j < length; j++) {
                fp1 = list.get(i);
                fp2 = list.get(j);
                if(fp1.getFullName().compareTo(fp2.getFullName()) > 0){
                    list.set(i,fp2);
                    list.set(j,fp1);
                }
            }
        }
    }
    public void sortDateOfBirth(int length){
        FootballPlayers fp1, fp2;
        for(int i = 0; i + 1 < length ; i++){
            for(int j = 0; j + 1 < length - i; j++){
                fp1 = list.get(j + 1);
                fp2 = list.get(j);
                if(fp1.getDateOfBirth().isBefore(fp2.getDateOfBirth())){
                    list.set(j + 1, fp2);
                    list.set(j, fp1);
                }
            }
        }
    }
    public void sortMatches(int length){
        FootballPlayers fp1, fp2;
        for(int i = 0; i + 1 < length ; i++){
            for(int j = 0; j + 1 < length - i; j++){
                fp1 = list.get(j + 1);
                fp2 = list.get(j);
                if(fp1.getMatches() < fp2.getMatches()){
                    list.set(j + 1, fp2);
                    list.set(j, fp1);
                }
            }
        }
    }
    public void sortGoals(int length){
        FootballPlayers fp1, fp2;
        for(int i = 0; i + 1 < length ; i++){
            for(int j = 0; j + 1 < length - i; j++){
                fp1 = list.get(j + 1);
                fp2 = list.get(j);
                if(fp1.getGoals() < fp2.getGoals()){
                    list.set(j + 1, fp2);
                    list.set(j, fp1);
                }
            }
        }
    }
    public void sortAssists(int length){
        FootballPlayers fp1, fp2;
        for(int i = 0; i + 1 < length ; i++){
            for(int j = 0; j + 1 < length - i; j++){
                fp1 = list.get(j + 1);
                fp2 = list.get(j);
                if(fp1.getAssists() < fp2.getAssists()){
                    list.set(j + 1, fp2);
                    list.set(j, fp1);
                }
            }
        }
    }
    public void sortYellowCards(int length){
        FootballPlayers fp1, fp2;
        for(int i = 0; i + 1 < length ; i++){
            for(int j = 0; j + 1 < length - i; j++){
                fp1 = list.get(j + 1);
                fp2 = list.get(j);
                if(fp1.getYellowCards() < fp2.getYellowCards()){
                    list.set(j + 1, fp2);
                    list.set(j, fp1);
                }
            }
        }
    }
    public void sortRedCards(int length){
        FootballPlayers fp1, fp2;
        for(int i = 0; i + 1 < length ; i++){
            for(int j = 0; j + 1 < length - i; j++){
                fp1 = list.get(j + 1);
                fp2 = list.get(j);
                if(fp1.getRedCards() < fp2.getRedCards()){
                    list.set(j + 1, fp2);
                    list.set(j, fp1);
                }
            }
        }
    }
    public void findNumber(String str2, ObservableList<FootballPlayers> prompt){
        String str1;
        for (FootballPlayers fp : list) {
            str1 = String.valueOf(fp.getNumber());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        list.setAll(prompt);
    }
    public void findFullName(String str2, ObservableList<FootballPlayers> prompt){
        String str1;
        for (FootballPlayers fp : list) {
            str1 = fp.getFullName().toUpperCase();
            if (str1.contains(str2.toUpperCase())) {
                prompt.add(fp);
            }
        }
        list.setAll(prompt);
    }
    public void findDateOfBirth(String str2, ObservableList<FootballPlayers> prompt){
        String str1;
        for (FootballPlayers fp : list) {
            str1 = Date.form(fp.getDateOfBirth());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        list.setAll(prompt);
    }
    public void findMatches(String str2, ObservableList<FootballPlayers> prompt){
        String str1;
        for (FootballPlayers fp : list) {
            str1 = String.valueOf(fp.getMatches());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        list.setAll(prompt);
    }
    public void findGoals(String str2, ObservableList<FootballPlayers> prompt){
        String str1;
        for (FootballPlayers fp : list) {
            str1 = String.valueOf(fp.getGoals());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        list.setAll(prompt);
    }
    public void findAssists(String str2, ObservableList<FootballPlayers> prompt){
        String str1;
        for (FootballPlayers fp : list) {
            str1 = String.valueOf(fp.getAssists());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        list.setAll(prompt);
    }
    public void findYellowCards(String str2, ObservableList<FootballPlayers> prompt){
        String str1;
        for (FootballPlayers fp : list) {
            str1 = String.valueOf(fp.getYellowCards());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        list.setAll(prompt);
    }
    public void findRedCards(String str2, ObservableList<FootballPlayers> prompt){
        String str1;
        for (FootballPlayers fp : list) {
            str1 = String.valueOf(fp.getRedCards());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        list.setAll(prompt);
    }
}