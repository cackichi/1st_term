package com.example.footbalteam;

import java.time.LocalDate;

public class FootballPlayers {
    //Это JavaFX-свойства
    //Изменение значения любого поля автоматически приводит к изменению значения в ячейке таблицы
    private int number ,matches, goals, assists, redCards, yellowCards, result;
    private String fullName;
    private LocalDate dateOfBirth;

    public FootballPlayers(int number, int matches, int goals, int assist, int yellowCards, int redCards, String fullName, LocalDate dateOfBirth) {
        this.number = number;
        this.matches = matches;
        this.goals = goals;
        this.assists = assist;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public FootballPlayers(int number, String fullName, int goals, int assists){
        this.number = number;
        this.fullName = fullName;
        this.goals = goals;
        this.assists = assists;
        this.result = this.goals + this.assists;
    }

    public FootballPlayers(int number, String fullName, int redCards){
        this.number = number;
        this.fullName = fullName;
        this.redCards = redCards;
    }

    public FootballPlayers(){

    }

    @Override
    public String toString(){
        return getNumber() + ";" + getFullName() + ";" + Date.form(getDateOfBirth()) + ";" + getMatches() + ";" +
                getGoals() + ";" + getAssists() + ";" + getYellowCards() + ";" + getRedCards();
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getResult() {
        return result;
    }
}
