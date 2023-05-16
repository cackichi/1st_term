package com.example.footbalteam;

import java.io.IOException;
import java.time.LocalDate;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.LocalDateStringConverter;


public class TableController {

    //******************** СОЗДАНИЕ И НАИМЕНОВАНИЕ СТОЛБЦОВ ********************
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
    private TableColumn<FootballPlayers, Integer> yellowCards;
    @FXML
    private TableColumn<FootballPlayers, Integer> redCards;

    //******************** СОЗДАНИЕ И НАИМЕНОВАНИЕ ТАБЛИЦЫ ********************
    @FXML
    private TableView<FootballPlayers> table_football_players;

    //******************** СОЗДАНИЕ И НАИМЕНОВАНИЕ КНОПОК ********************
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

    //******************** СОХДАНИЕ И НАИМЕНОВАНИЕ ВСПЛЫВАЮЩИХ СПИСКОВ ********************
    @FXML
    private ComboBox<String> comboSort;
    @FXML
    private ComboBox<String> comboFilter;

    //******************** СОЗДАНИЕ И НАИМЕНОВАНИЕ ТЕКСТОВЫХ ПОЛЕЙ ********************
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

    //******************** СОЗДАНИЕ СПИСКОВ ИГРОКОВ ********************
    private ObservableList<FootballPlayers> footballPlayers = FXCollections.observableArrayList();
    private final ObservableList<FootballPlayers> example = FXCollections.observableArrayList();

    //******************** МЕТОД, КОТОРЫЙ ВЫЗЫВАЕТСЯ ПРИ ЗАПУСКЕ ОКНА ********************
    @FXML
    void initialize() {
        stopButton.setVisible(false);
        saveButton.setDisable(true);

        // Устанавливаем конвертор даты по формату дд.ММ.гггг на столбец
        dateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

        // Указание столбцам параметров, которые он будет хранить
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        matches.setCellValueFactory(new PropertyValueFactory<>("matches"));
        goals.setCellValueFactory(new PropertyValueFactory<>("goals"));
        assists.setCellValueFactory(new PropertyValueFactory<>("assists"));
        yellowCards.setCellValueFactory(new PropertyValueFactory<>("yellowCards"));
        redCards.setCellValueFactory(new PropertyValueFactory<>("redCards"));

        // Заполняем таблицу данными из файла
        footballPlayers = FileFootballPlayers.getInfoPlayers();
        table_football_players.setItems(footballPlayers);
        example.setAll(footballPlayers);

        // Заполняем всплывающие списоки и устанавливаем их начальное значение
        comboSort.setItems(FXCollections.observableArrayList("Номер в списке", "ФИО", "Дата рождения", "Кол-во матчей",
                "Голы", "Передачи", "Желтые карточки", "Красные карточки", "Сортировка"));
        comboSort.setValue("Сортировка");

        comboFilter.setItems(FXCollections.observableArrayList("Номер в списке", "ФИО", "Дата рождения", "Кол-во матчей",
                "Голы", "Передачи", "Желтые карточки", "Красные карточки", "Поиск"));
        comboFilter.setValue("Поиск");
    }

    //******************** МЕТОД ВСПЛЫВАЮЩЕГО СПИСКА "Сортировка" ДЛЯ СОРТИРОВКИ ТАБЛИЦЫ ********************
    @FXML
    public void sortPlayers() {
        // Получаем номер выбранного элемента в comboSort
        int call = comboSort.getSelectionModel().getSelectedIndex();
        int length = footballPlayers.size();

        // Исходя из номера элемента, проводится сортировка
        if (call == 0) sortNumber(length);
        if (call == 1) sortFullName(length);
        if (call == 2) sortDateOfBirth(length);
        if (call == 3) sortMatches(length);
        if (call == 4) sortGoals(length);
        if (call == 5) sortAssists(length);
        if (call == 6) sortYellowCards(length);
        if (call == 7) sortRedCards(length);
        if (call == 8) {
            sortNumber(length);
            comboSort.setValue("Сортировка");
        }
    }

    //******************** МЕТОД КНОПКИ "Поиск" ДЛЯ ПОИСКА ПО ЛЮБОМУ ПАРАМЕТРУ ********************
    @FXML
    public void filterPlayers() {
        // Получаем номер выбранного элемента в comboFilter
        int call = comboFilter.getSelectionModel().getSelectedIndex();
        // Создаем буферный список для заполнения его элементами которые содержат строку из поиска
        ObservableList<FootballPlayers> prompt = FXCollections.observableArrayList();
        String str2;
        // Если мы не ввели пустую строку без пробелов,то выполняем поиск по выбранному элементу в comboFilter
        // Иначе всплывает диалоговое окно "Пустой поиск"
        if (!findFilter.getText().trim().isEmpty()) {
            footballPlayers.setAll(example);
            str2 = findFilter.getText();

            if (call == 0) findNumber(str2, prompt);
            if (call == 1) findFullName(str2, prompt);
            if (call == 2) findDateOfBirth(str2, prompt);
            if (call == 3) findMatches(str2, prompt);
            if (call == 4) findGoals(str2, prompt);
            if (call == 5) findAssists(str2, prompt);
            if (call == 6) findYellowCards(str2, prompt);
            if (call == 7) findRedCards(str2, prompt);
            if (call != 8) stopButton.setVisible(true);

        } else {
            if(call != 8) {
                footballPlayers.setAll(example);
                Alert filterAl = new Alert(Alert.AlertType.INFORMATION);
                filterAl.setTitle("Поиск");
                filterAl.setContentText("По результату поиска ничего не найдено");
                filterAl.setHeaderText("Информация");
                filterAl.showAndWait();
            }
        }

    }

    //******************** МЕТОД КНОПКИ "Остановить поиск" ДЛЯ ПРЕКРАЩЕНИЯ ПОИСКА ********************
    // Метод прекращает поиск и устанавливает начальное значение comboFilter
    @FXML
    public void stopFilter() {
        stopButton.setVisible(false);
        findFilter.clear();
        comboFilter.setValue("Поиск");
        footballPlayers.setAll(example);
    }

    //******************** МЕТОД КНОПКИ "Добавить" ДЛЯ ДОБАВЛЕНИЯ НОВОГО ИГРОКА В ТАБЛИЦУ ********************
    @FXML
    public void addPlayers() {
        int x = example.size() + 1;
        // Если ввод корректен, то мы устанавливаем значения для игрока из текстовых полей
        if (isRightInput()) {
            FootballPlayers fp = new FootballPlayers();
            fp.setNumber(x);
            fp.setFullName(fullName_field.getText());
            fp.setDateOfBirth(Date.analise(dateOfBirth_field.getText()));
            fp.setMatches(Integer.parseInt(matches_field.getText()));
            fp.setGoals(Integer.parseInt(goals_field.getText()));
            fp.setAssists(Integer.parseInt(assists_field.getText()));
            fp.setYellowCards(Integer.parseInt(yellowCards_field.getText()));
            fp.setRedCards(Integer.parseInt(redCards_field.getText()));
            // Очищаем поля ввода
            clearAll();
            // Добавляем
            example.add(fp);
            footballPlayers.add(fp);
            saveButton.setDisable(false);
            Alert addAlert = new Alert(Alert.AlertType.INFORMATION);
            addAlert.setTitle("Добавление игрока");
            addAlert.setHeaderText("Информация");
            addAlert.setContentText("Игрок добавлен в таблицу");
            addAlert.showAndWait();
        }
    }

    //******************** МЕТОД ПРОВЕРКИ ВВОДИМЫХ ДАННЫХ ********************
    private boolean isRightInput() {
        // Создаем строку, которая будет содержать текст ошибки ввода
        String sms = "";
        // Проверяем поля на заполненость
        if (fullName_field == null || fullName_field.getText().trim().isEmpty()) sms += "Пустое поле ФИО\n";
        if (dateOfBirth_field == null || dateOfBirth_field.getText().trim().isEmpty())
            sms += "Пустое поле даты рождения\n";
        else {
            if (!Date.isDate(dateOfBirth_field.getText())) {
                sms += "Используйте формат дд.мм.гггг\n";
            }
        }
        if (matches_field.getText().trim().isEmpty()) sms += "Пустое поле матчей\n";
        if (goals_field.getText().trim().isEmpty()) sms += "Пустое поле голов\n";
        if (assists_field.getText().trim().isEmpty()) sms += "Пустое поле голевых передач\n";
        if (yellowCards_field.getText().trim().isEmpty()) sms += "Пустое поле желтых карточек\n";
        if (redCards_field.getText().trim().isEmpty()) sms += "Пустое поле красных карточек\n";
        // Проверяем поля на числовой положительный ввод
        try {
            int a = Integer.parseInt(matches_field.getText());
            int b = Integer.parseInt(goals_field.getText());
            int c = Integer.parseInt(assists_field.getText());
            int d = Integer.parseInt(yellowCards_field.getText());
            int e = Integer.parseInt(redCards_field.getText());
            if (a < 0 || b < 0 || c < 0 || d < 0 || e < 0) throw new NegativeException();
        } catch (NumberFormatException e) {
            sms += "Неверный ввод числа\n";
        } catch (NegativeException e) {
            sms += "Отрицательный ввод\n";
        }
        // Если после всей проверки строка пуста, значит ни одной ошибки нет и мутод возвращает значение true
        // Иначе всплывает диалоговое окно со всеми нашими ошибками и метод возвращает значение false
        if (sms.length() == 0) {
            return true;
        } else {
            Alert emptyField = new Alert(Alert.AlertType.ERROR);
            emptyField.setTitle("Ошибка заполнения");
            emptyField.setHeaderText("Пожалуйста, заполните поля корректно");
            emptyField.setContentText(sms);
            emptyField.showAndWait();
            return false;
        }
    }

    //******************** МЕТОД КНОПКИ "Изменить" ДЛЯ РЕДАКТИРОВАНИЯ ДАННЫХ ********************
    @FXML
    public void changePlayers() {
        // Если не выбрана ни одна строка в таблице, то всплывает диалоговое окно "Пустой выбор"
        // Если же какая-то строка выбрана то заполняем текстовые поля значениями выбранной строки
        if (!table_football_players.getSelectionModel().isEmpty()) {
            // Запрещаем добавление, удаление и сохранение во время редактирования
            addButton.setDisable(true);
            deleteButton.setDisable(true);
            saveButton.setDisable(true);
            // Заполняем поля
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
            // Диалоговое окно "Пустой выбор"
            Alert noSelect = new Alert(Alert.AlertType.WARNING);
            noSelect.setHeaderText("Пустой выбор");
            noSelect.setTitle("Предупреждение");
            noSelect.setContentText("Вы ничего не выбрали для редактирования");
            noSelect.showAndWait();
        }
    }

    //******************** МЕТОД КНОПКИ "Сохранение изменений" ДЛЯ СОХРАНЕНИЯ ОТРЕДАКТИРОВАННЫХ ДАННЫХ ********************
    @FXML
    public void saveChanges() {
        // Если ввод корректен, то мы устанавливаем значения для игрока из текстовых полей
        if (isRightInput()) {
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
            // Очищаем поля для ввода
            clearAll();
            // Заменяем выбранную строку на отредактированную
            footballPlayers.set(num, fp);
            // Сортируем таблицу по номеру игрока в таблице
            sortNumber(footballPlayers.size());
            example.set(call - 1, fp);

            Alert saveAl = new Alert(Alert.AlertType.INFORMATION);
            saveAl.setTitle("Изменение данных");
            saveAl.setHeaderText("Информация");
            saveAl.setContentText("Данные изменены");
            saveAl.showAndWait();

            saveChangesButton.setVisible(false);
            addButton.setDisable(false);
            deleteButton.setDisable(false);
            saveButton.setDisable(false);
        }
    }

    //************************** МЕТОД КНОПКИ "Очистить" ДЛЯ ОЧИСТКИ ВСЕХ ПОЛЕЙ ВВОДА **************************
    // Метод очищает все поля для ввода
    @FXML
    public void clearAll() {
        fullName_field.clear();
        dateOfBirth_field.clear();
        matches_field.clear();
        goals_field.clear();
        assists_field.clear();
        yellowCards_field.clear();
        redCards_field.clear();
    }

    //************************** МЕТОД КНОПКИ "Сохранить" ДЛЯ СОХРАНЕНИЯ ТАБЛИЦЫ В ФАЙЛ **************************
    @FXML
    public void saveAll() {
        FileFootballPlayers.setInfoPlayers(example);
        saveButton.setDisable(true);
    }

    //************************** МЕТОД КНОПКИ "Удалить" УДАЛЕНИЯ ИГРОКОВ ИЗ ТАБЛИЦЫ **************************
    @FXML
    public void delPlayers() {
        // Проверяем выбрана ли строка. Если нет, то всплывает диалоговое окно "Пустой выбор"
        if (table_football_players.getSelectionModel().isEmpty()) {
            Alert noSelect = new Alert(Alert.AlertType.WARNING);
            noSelect.setHeaderText("Пустой выбор");
            noSelect.setTitle("Предупреждение");
            noSelect.setContentText("Вы ничего не выбрали для удаления");
            noSelect.showAndWait();
        } else {
            // Иначе диалоговое окно "Подтверждение удаления"
            Alert delAlert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK, ButtonType.CANCEL);
            delAlert.setTitle("Предупреждение");
            delAlert.setHeaderText("Удаление данных");
            delAlert.setContentText("Вы уверены что хотите удалить эти данные?\nДанные исчезнут");
            delAlert.showAndWait();

            // Если подтверждаем, то игрок удаляется из таблицы и номера игроков которые больше удаленного уменьшаются на один
            if (delAlert.getResult() == ButtonType.OK) {
                FootballPlayers fp = table_football_players.getSelectionModel().getSelectedItem();
                int num = table_football_players.getSelectionModel().getSelectedItem().getNumber();
                int length_exm = example.size();
                if (length_exm != num) {
                    for (int i = num; i < length_exm; i++) {
                        example.get(i).setNumber(i);
                    }
                }
                findFilter.clear();
                stopButton.setVisible(false);
                comboFilter.setValue("Поиск");
                comboSort.setValue("Сортировка");
                footballPlayers.removeAll(fp);
                example.removeAll(fp);
                footballPlayers.setAll(example);
                saveButton.setDisable(false);

                Alert delAl = new Alert(Alert.AlertType.INFORMATION);
                delAl.setTitle("Удаление игрока");
                delAl.setHeaderText("Информация");
                delAl.setContentText("Игрок удален");
                delAl.showAndWait();

            } else delAlert.close();
            // Иначе закрывается диалоговое окно "Подтверждение удаления" и ничего не происходит
        }
    }

    //****************** МЕТОД КНОПКИ "Индивидуальное задание" ДЛЯ ЗАПУСКА ОКНА ИНДИВИДУАЛЬНОГО ЗАДАНИЯ ******************
    @FXML
    public void individualTask() {
        // Проверяем сохранена ли таблица, если да, то открываем окно индивидуального задания
        // Иначе всплывает диалоговое окно "Потеря данных"
        if (saveButton.isDisable()) {
            // Запуск окна индивидуального задания
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

            Main.stop(stage);
        } else {
            // Диалоговое окно "Потеря данных"
            Alert saveTrouble = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK, ButtonType.CANCEL);
            saveTrouble.setTitle("Потеря данных");
            saveTrouble.setHeaderText("Внимание!");
            saveTrouble.setContentText("Вы не сохранили данные, это приведет к потере." +
                    "\nХотите продолжить с данными последнего сохранения?");
            saveTrouble.showAndWait();
            if (saveTrouble.getResult() == ButtonType.OK) {
                // Если нажать на кнопку "OK", то индивидуальное задание выполниться для последнего сохранения
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

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        Alert closeAlert = new Alert(Alert.AlertType.WARNING, "Внимание", ButtonType.OK, ButtonType.CANCEL);
                        closeAlert.setTitle("Внимание!");
                        closeAlert.setHeaderText("Закрытие приложения");
                        closeAlert.setContentText("Вы действительно хотите закрыть приложение?");
                        Optional<ButtonType> result = closeAlert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            stage.close();
                        } else windowEvent.consume();
                    }
                });
            } else saveTrouble.close();
            // Иначе, диалоговое окно закрывается
        }
    }

    //************************** МЕТОДЫ ВСЕХ СОРТИРОВОК **************************
    // Все сортировки представлены сортирвокой "пузырьком"
    public void sortNumber(int length) {
        FootballPlayers fp1, fp2;
        for (int i = 0; i + 1 < length; i++) {
            for (int j = 0; j + 1 < length - i; j++) {
                fp1 = footballPlayers.get(j + 1);
                fp2 = footballPlayers.get(j);
                if (fp1.getNumber() < fp2.getNumber()) {
                    footballPlayers.set(j + 1, fp2);
                    footballPlayers.set(j, fp1);
                }
            }
        }
    }

    //        В Java compareTo() получает значение 0, если аргумент
    //        является строкой лексически равной данной строке; значение меньше 0,
    //        если аргумент является строкой лексически большей, чем сравниваемая строка;
    //        и значение больше 0, если аргумент является строкой лексически меньшей этой строки.
    public void sortFullName(int length) {
        FootballPlayers fp1, fp2;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                fp1 = footballPlayers.get(i);
                fp2 = footballPlayers.get(j);
                if (fp1.getFullName().compareTo(fp2.getFullName()) > 0) {
                    footballPlayers.set(i, fp2);
                    footballPlayers.set(j, fp1);
                }
            }
        }
    }

    public void sortDateOfBirth(int length) {
        FootballPlayers fp1, fp2;
        for (int i = 0; i + 1 < length; i++) {
            for (int j = 0; j + 1 < length - i; j++) {
                fp1 = footballPlayers.get(j + 1);
                fp2 = footballPlayers.get(j);
                if (fp1.getDateOfBirth().isBefore(fp2.getDateOfBirth())) {
                    footballPlayers.set(j + 1, fp2);
                    footballPlayers.set(j, fp1);
                }
            }
        }
    }

    public void sortMatches(int length) {
        FootballPlayers fp1, fp2;
        for (int i = 0; i + 1 < length; i++) {
            for (int j = 0; j + 1 < length - i; j++) {
                fp1 = footballPlayers.get(j + 1);
                fp2 = footballPlayers.get(j);
                if (fp1.getMatches() < fp2.getMatches()) {
                    footballPlayers.set(j + 1, fp2);
                    footballPlayers.set(j, fp1);
                }
            }
        }
    }

    public void sortGoals(int length) {
        FootballPlayers fp1, fp2;
        for (int i = 0; i + 1 < length; i++) {
            for (int j = 0; j + 1 < length - i; j++) {
                fp1 = footballPlayers.get(j + 1);
                fp2 = footballPlayers.get(j);
                if (fp1.getGoals() < fp2.getGoals()) {
                    footballPlayers.set(j + 1, fp2);
                    footballPlayers.set(j, fp1);
                }
            }
        }
    }

    public void sortAssists(int length) {
        FootballPlayers fp1, fp2;
        for (int i = 0; i + 1 < length; i++) {
            for (int j = 0; j + 1 < length - i; j++) {
                fp1 = footballPlayers.get(j + 1);
                fp2 = footballPlayers.get(j);
                if (fp1.getAssists() < fp2.getAssists()) {
                    footballPlayers.set(j + 1, fp2);
                    footballPlayers.set(j, fp1);
                }
            }
        }
    }

    public void sortYellowCards(int length) {
        FootballPlayers fp1, fp2;
        for (int i = 0; i + 1 < length; i++) {
            for (int j = 0; j + 1 < length - i; j++) {
                fp1 = footballPlayers.get(j + 1);
                fp2 = footballPlayers.get(j);
                if (fp1.getYellowCards() < fp2.getYellowCards()) {
                    footballPlayers.set(j + 1, fp2);
                    footballPlayers.set(j, fp1);
                }
            }
        }
    }

    public void sortRedCards(int length) {
        FootballPlayers fp1, fp2;
        for (int i = 0; i + 1 < length; i++) {
            for (int j = 0; j + 1 < length - i; j++) {
                fp1 = footballPlayers.get(j + 1);
                fp2 = footballPlayers.get(j);
                if (fp1.getRedCards() < fp2.getRedCards()) {
                    footballPlayers.set(j + 1, fp2);
                    footballPlayers.set(j, fp1);
                }
            }
        }
    }

    //************************** МЕТОДЫ ВСЕХ ПОИСКОВ **************************
    // Проверяем содержание введенной строки
    // Если строка поиска содержится в строке игрока, то добавляем этого игрока в буферный список
    // После цикла заполняем список footballPlayers значениями буферного списка prompt
    public void findNumber(String str2, ObservableList<FootballPlayers> prompt) {
        String str1;
        for (FootballPlayers fp : footballPlayers) {
            str1 = String.valueOf(fp.getNumber());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        footballPlayers.setAll(prompt);
    }

    public void findFullName(String str2, ObservableList<FootballPlayers> prompt) {
        String str1;
        for (FootballPlayers fp : footballPlayers) {
            str1 = fp.getFullName().toUpperCase();
            if (str1.contains(str2.toUpperCase())) {
                prompt.add(fp);
            }
        }
        footballPlayers.setAll(prompt);
    }

    public void findDateOfBirth(String str2, ObservableList<FootballPlayers> prompt) {
        String str1;
        for (FootballPlayers fp : footballPlayers) {
            str1 = Date.form(fp.getDateOfBirth());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        footballPlayers.setAll(prompt);
    }

    public void findMatches(String str2, ObservableList<FootballPlayers> prompt) {
        String str1;
        for (FootballPlayers fp : footballPlayers) {
            str1 = String.valueOf(fp.getMatches());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        footballPlayers.setAll(prompt);
    }

    public void findGoals(String str2, ObservableList<FootballPlayers> prompt) {
        String str1;
        for (FootballPlayers fp : footballPlayers) {
            str1 = String.valueOf(fp.getGoals());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        footballPlayers.setAll(prompt);
    }

    public void findAssists(String str2, ObservableList<FootballPlayers> prompt) {
        String str1;
        for (FootballPlayers fp : footballPlayers) {
            str1 = String.valueOf(fp.getAssists());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        footballPlayers.setAll(prompt);
    }

    public void findYellowCards(String str2, ObservableList<FootballPlayers> prompt) {
        String str1;
        for (FootballPlayers fp : footballPlayers) {
            str1 = String.valueOf(fp.getYellowCards());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        footballPlayers.setAll(prompt);
    }

    public void findRedCards(String str2, ObservableList<FootballPlayers> prompt) {
        String str1;
        for (FootballPlayers fp : footballPlayers) {
            str1 = String.valueOf(fp.getRedCards());
            if (str1.contains(str2)) {
                prompt.add(fp);
            }
        }
        footballPlayers.setAll(prompt);
    }
}