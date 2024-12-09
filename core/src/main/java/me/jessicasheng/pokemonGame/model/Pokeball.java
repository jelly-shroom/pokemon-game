package me.jessicasheng.pokemonGame.model;

import java.io.Serializable;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public enum Pokeball implements Serializable {
    POKEBALL(20),
    GREATBALL(50),
    ULTRABALL(100);

    private double catchRate;

    private Pokeball(double catchRate) {
        this.catchRate = catchRate;
    }

    public double getCatchRate() {
        return catchRate;
    }
}
