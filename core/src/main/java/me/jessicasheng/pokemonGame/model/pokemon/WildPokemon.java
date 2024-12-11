package me.jessicasheng.pokemonGame.model.pokemon;

import me.jessicasheng.pokemonGame.model.BattleCapable;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class WildPokemon extends Pokemon implements BattleCapable {
    private double catchRate;


    public WildPokemon(String name, PokemonType type1, PokemonType type2, int hp, int baseAttack,
                        int level, PokemonStages stage, double catchRate) {
        super(name, type1, type2, hp, baseAttack, level, stage);
        this.catchRate = catchRate;
    }

    public double getCatchRate() {
        return catchRate;
    }

    /**
     * @param pokemon
     */
    @Override
    public void initiateBattle(Object trainer) {

    }

    /**
     *
     */
    @Override
    public void flee() {

    }
}
