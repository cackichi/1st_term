package com.example.footbalteam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Этот класс нужен для преобразования даты в строку
public class Date {
    //Шаблон даты
    private static final String DATE_FORMAT ="dd.MM.yyyy";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
//Метод возвращает дату в виде отформатированной строки
    public static String form(LocalDate ld){
        if(ld == null){
            return null;
        }
        return DATE_TIME_FORMATTER.format(ld);
    }
//Метод преобразует строку по правилам шаблона
    public static LocalDate analise(String date) {
        try {
            return DATE_TIME_FORMATTER.parse(date, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;//Если строка не может быть преобразована
        }
    }
    //Проверка на корректность
    public static boolean isDate(String dateString) {
        // Разбираем строку
        return Date.analise(dateString) != null;
    }

}
