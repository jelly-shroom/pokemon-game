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

    public OwnedPokemon(String name, int level, int exp, int hp,
                        int baseAttack, PokemonType type, PokemonStages stage) {
        super(name, level, hp, baseAttack, type, stage);
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
