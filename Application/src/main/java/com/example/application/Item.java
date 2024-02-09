package com.example.application;

public class Item {
    private String nazev;
    private double goal;
    private double saved;

    public Item(String nazev, double gaol, double saved) {
        this.nazev = nazev;
        this.goal = goal;
        this.saved = saved;
    }

    public String getNazev() {
        return nazev;
    }

    public double getGoal() {
        return goal;
    }

    public double getSaved() {
        return saved;
    }
}
