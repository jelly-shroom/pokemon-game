package me.jessicasheng.pokemonGame.model;

import me.jessicasheng.pokemonGame.model.pokemon.Pokemon;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/8/24
*/
public interface BattleCapable {
    void initiateBattle(Object opponent);
    void flee();
}
