package me.jessicasheng.pokemonGame.model.pokemon;

import java.util.Objects;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class OwnedPokemon extends Pokemon {
    private int experience;

    public OwnedPokemon(String name, PokemonType type1, PokemonType type2, int hp,
                        int baseAttack, int level, PokemonStages stage, int exp) {
        super(name, type1, type2, hp, baseAttack, level, stage);
        this.experience = exp;
    }

    //TODO: gainExperience, levelUp, evolve, rest


    //toString
    @Override
    public String toString() {
        return "OwnedPokemon{" +
                "experience=" + experience +
                "} " + super.toString();
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OwnedPokemon that = (OwnedPokemon) o;
        return experience == that.experience;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), experience);
    }

    //getters and setters
    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
