package me.jessicasheng.pokemonGame.model.pokemon;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class WildPokemon extends Pokemon {
    private double catchRate;


    public WildPokemon(String name, int level, int hp, int baseAttack,
                       PokemonType type, PokemonStages stage, double catchRate) {
        super(name, level, hp, baseAttack, type, stage);
        this.catchRate = catchRate;
    }

    public double getCatchRate() {
        return catchRate;
    }
}
