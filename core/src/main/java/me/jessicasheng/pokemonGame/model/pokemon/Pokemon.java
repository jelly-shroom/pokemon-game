package me.jessicasheng.pokemonGame.model.pokemon;

import java.io.Serializable;
import java.util.Objects;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class Pokemon implements Serializable {
    private String name;
    private int level;
    private int hp;
    private int baseAttack;

    private PokemonType type;
    private PokemonStages stage;

    public Pokemon(String name, int level, int hp, int baseAttack, PokemonType type, PokemonStages stage) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.baseAttack = baseAttack;
        this.type = type;
        this.stage = stage;
    }

    //TODO: attack, isFainted, receiveDamage

    //toString
    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", hp=" + hp +
                ", baseAttack=" + baseAttack +
                ", type=" + type +
                ", stage=" + stage +
                '}';
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return level == pokemon.level && hp == pokemon.hp && baseAttack == pokemon.baseAttack && Objects.equals(name, pokemon.name) && type == pokemon.type && stage == pokemon.stage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, hp, baseAttack, type, stage);
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public PokemonStages getStage() {
        return stage;
    }

    public void setStage(PokemonStages stage) {
        this.stage = stage;
    }
}
