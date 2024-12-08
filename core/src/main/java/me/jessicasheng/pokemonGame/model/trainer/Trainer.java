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

    /**
     * Adds a Pokemon to the Trainer's ownedPokemon list if Trainer successfully
     * catches the Pokemon.
     * @return
     */
    public boolean attemptCapture(WildPokemon pokemon, Pokeball pokeball) {
        //gets the capture rate of the pokemon and the type of pokeball
        double pokemonCatchRate = pokemon.getCatchRate();
        double pokeballCatchRate = pokeball.getCatchRate();

        //calculates the total catch rate
        double totalCatchRate = pokemonCatchRate + pokeballCatchRate;
        double random = Math.random() * 100;

        boolean captured = random <= totalCatchRate;
        if (captured) {
            //adds the pokemon to the trainer's ownedPokemon list
            //and removes the pokeball from the inventory
            ownedPokemon.add(pokemon);
            usePokeball(pokeball);
        }

        return captured;
    }

    /**
     * Uses up a pokeball from the Trainer's inventory.
     * @param pokeball
     * @return
     */
    public String usePokeball(Pokeball pokeball) {
        for (Map.Entry<Integer, Pokeball> entry : pokeballInventory.entrySet()) {
            if (entry.getValue() == pokeball && entry.getKey() > 0) {
                pokeballInventory.put(entry.getKey() - 1, entry.getValue());
                return "Used a " + pokeball + "!";
            }
        }
        return "You don't have any more " + pokeball + "s!";
    }

    /**
     * chooses a pokemon from owned list for battle
     * @param index
     * @return
     */
    public Pokemon choosePokemon(int index) {
        if (index >= 0 && index < ownedPokemon.size()) {
            return ownedPokemon.get(index);
        }
        return null;
    }

    /**
     * swaps out a pokemon currently in battle to another in
     * the owned list
     * @param activeIndex
     * @param newPokemonIndex
     */
    public boolean switchPokemon(int activeIndex, int newPokemonIndex) {
        if (activeIndex >= 0 && activeIndex < ownedPokemon.size() &&
            newPokemonIndex >= 0 && newPokemonIndex < ownedPokemon.size()) {

            Pokemon activePokemon = ownedPokemon.get(activeIndex);
            Pokemon newPokemon = ownedPokemon.get(newPokemonIndex);

            // Check if the active Pokemon has more than 0 HP (is in battle)
            if (activePokemon.getHp() > 0) {
                Collections.swap(ownedPokemon, activeIndex, newPokemonIndex);
                return true;
            }
        }
        return false;
    }

    /**
     * releases a pokemon from the owned list
     * @param index
     * @return
     */
    public Pokemon releasePokemon(int index) {
        if (index >= 0 && index < ownedPokemon.size()) {
            return ownedPokemon.remove(index);
        }
        return null;
    }

    /**
     * levels up the trainer
     */
    public void levelUp() {
        this.level++;
    }


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
