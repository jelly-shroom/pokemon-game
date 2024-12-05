package me.jessicasheng.pokemonGame.model.pokemon;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public enum PokemonType {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY;

    /**
     * determines whether a Pokemon is strong or weak against another Pokemon
     */
    boolean isAdvantageous (PokemonType other) {
        switch (this) {
            case NORMAL:
                return false;
            case FIRE:
                return other == PokemonType.GRASS || other == PokemonType.ICE || other == PokemonType.BUG || other == PokemonType.STEEL;
            case WATER:
                return other == PokemonType.FIRE || other == PokemonType.GROUND || other == PokemonType.ROCK;
            case ELECTRIC:
                return other == PokemonType.WATER || other == PokemonType.FLYING;
            case GRASS:
                return other == PokemonType.WATER || other == PokemonType.GROUND || other == PokemonType.ROCK;
            case ICE:
                return other == PokemonType.GRASS || other == PokemonType.GROUND || other == PokemonType.FLYING || other == PokemonType.DRAGON;
            case FIGHTING:
                return other == PokemonType.NORMAL || other == PokemonType.ICE || other == PokemonType.ROCK || other == PokemonType.DARK || other == PokemonType.STEEL;
            case POISON:
                return other == PokemonType.GRASS || other == PokemonType.FAIRY;
            case GROUND:
                return other == PokemonType.FIRE || other == PokemonType.ELECTRIC || other == PokemonType.POISON || other == PokemonType.ROCK || other == PokemonType.STEEL;
            case FLYING:
                return other == PokemonType.GRASS || other == PokemonType.FIGHTING || other == PokemonType.BUG;
            case PSYCHIC:
                return other == PokemonType.FIGHTING || other == PokemonType.POISON;
            case BUG:
                return other == PokemonType.GRASS || other == PokemonType.PSYCHIC || other == PokemonType.DARK;
            case ROCK:
                return other == PokemonType.FIRE || other == PokemonType.ICE || other == PokemonType.FLYING || other == PokemonType.BUG;
            case GHOST:
                return other == PokemonType.PSYCHIC || other == PokemonType.GHOST;
            case DRAGON:
                return other == PokemonType.DRAGON;
            case DARK:
                return other == PokemonType.PSYCHIC || other == PokemonType.GHOST;
            case STEEL:
                return other == PokemonType.ICE || other == PokemonType.ROCK || other == PokemonType.FAIRY;
            case FAIRY:
                return other == PokemonType.FIGHTING || other == PokemonType.DRAGON || other == PokemonType.DARK;
            default:
                return false;
        }
    }
}
