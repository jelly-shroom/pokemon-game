package me.jessicasheng.pokemonGame.model.trainer;
import java.util.*;
import me.jessicasheng.pokemonGame.model.pokemon.*;
import me.jessicasheng.pokemonGame.model.Pokeball;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public abstract class Trainer {
    private String username;
    private String password;

    private String name;
    private int level;

    private ArrayList<Pokemon> ownedPokemon;
    private Map<Integer, Pokeball> pokeballInventory;

    public Trainer(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.level = 1;
        this.ownedPokemon = new ArrayList<>();
        this.pokeballInventory = new HashMap<>();
        this.pokeballInventory.put(5, Pokeball.POKEBALL);
    }

    //TODO: addPokemon, usePokeball, choosePokemon, switchPokemon, releasePokemon,
    //TODO: levelUp, becomeMaster

    //toString
    @Override
    public String toString() {
        return "Trainer{" +
            "name='" + name + '\'' +
            ", level=" + level +
            ", ownedPokemon=" + ownedPokemon +
            ", pokeballInventory=" + pokeballInventory +
            '}';
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return level == trainer.level && Objects.equals(username, trainer.username)
            && Objects.equals(password, trainer.password) && Objects.equals(name, trainer.name)
            && Objects.equals(pokeballInventory, trainer.pokeballInventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, level, ownedPokemon,
            pokeballInventory);
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public ArrayList<Pokemon> getOwnedPokemon() {
        return ownedPokemon;
    }

    public void setOwnedPokemon(ArrayList<Pokemon> ownedPokemon) {
        this.ownedPokemon = ownedPokemon;
    }

    public Map<Integer, Pokeball> getPokeballInventory() {
        return pokeballInventory;
    }

    public void setPokeballInventory(Map<Integer, Pokeball> pokeballInventory) {
        this.pokeballInventory = pokeballInventory;
    }
}
