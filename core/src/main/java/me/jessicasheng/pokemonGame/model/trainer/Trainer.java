package me.jessicasheng.pokemonGame.model.trainer;
import java.io.Serializable;
import java.util.*;

import me.jessicasheng.pokemonGame.model.BattleCapable;
import me.jessicasheng.pokemonGame.model.pokemon.*;
import me.jessicasheng.pokemonGame.model.Pokeball;
import me.jessicasheng.pokemonGame.model.quests.BattleQuest;
import me.jessicasheng.pokemonGame.model.quests.Quest;
import me.jessicasheng.pokemonGame.model.quests.QuestType;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public abstract class Trainer implements Serializable, BattleCapable {
    private String username;
    private String password;

    private String name;
    private int level;
    private int experience;

    private String trainerType;

    private ArrayList<Pokemon> ownedPokemon;
    private Map<Integer, Pokeball> pokeballInventory;

    public Trainer(String username, String password, String name, String trainerType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.level = 1;
        this.ownedPokemon = new ArrayList<>();
        this.pokeballInventory = new HashMap<>();
        this.pokeballInventory.put(5, Pokeball.POKEBALL);
        this.trainerType = trainerType;
    }

    //perplexity code to create a listener interface
    public interface TrainerListener {
        void onBattleInitiated(Object thisEntity, Object opponent);
        void onCaptureAttempt(WildPokemon wildPokemon, boolean success);
        void onFlee(Object entity);
        void onQuestProgressUpdated(String questName, int progress, int goal);
    }
    private TrainerListener listener;
    public void setTrainerListener(TrainerListener listener) {
        this.listener = listener;
    }
    public TrainerListener getListener() { return listener; }

    /**
     * Adds a pokemon to the Trainer's ownedPokemon list if Trainer successfully
     * catches the Pokemon.
     * @return
     */
    public boolean attemptCapture(WildPokemon pokemon, Pokeball pokeball) {
        // get the capture rate of the pokemon and ball
        double pokemonCatchRate = pokemon.getCatchRate();
        double pokeballCatchRate = pokeball.getCatchRate();

        //  total catch rate
        double totalCatchRate = pokemonCatchRate + pokeballCatchRate;
        double randomValue = Math.random() * 100;

        boolean captured = randomValue <= totalCatchRate;

        if (captured) {
            // Add Pokémon to owned list
            ownedPokemon.add(pokemon);
        }
        usePokeball(pokeball);


        if (listener != null) listener.onCaptureAttempt(pokemon, captured);

        return captured;
    }

    /**
     * Tries to initiate battle against a WildPokemon.
     * @param opponent
     */
    @Override
    public void initiateBattle(Object opponent) {
        if (opponent instanceof WildPokemon) {
            WildPokemon wildPokemon = (WildPokemon) opponent;
            System.out.println("Initiating battle with: " + wildPokemon.getName());

            Random random = new Random();

            // Check if pokemon flees
            boolean pokemonFlees = random.nextBoolean(); // 50% chance for fleeing
            if (pokemonFlees) {
                System.out.println(wildPokemon.getName() + " fled from the battle!");

                // Listens to the event of fleeing and notifies the view layer
                if (listener != null) listener.onFlee(wildPokemon);
                return;
            }

            progressBattleQuest(this);
            System.out.println("You defeated " + wildPokemon.getName() + "!");

            // Listens to the initiating battle and notifies the view layer
            if (listener != null) listener.onBattleInitiated(this, wildPokemon);
        }
    }

    /**
     * Progresses the battle quest of the trainer
     * @param trainer
     */
    public void progressBattleQuest(Trainer trainer){
        // Count towards active battle quest if applicable
        if (trainer instanceof ApprenticeTrainer) {
            ApprenticeTrainer apprenticeTrainer = (ApprenticeTrainer) trainer;

            Map<Integer, Quest> activeQuests = apprenticeTrainer.getActiveQuests();
            for (Quest quest : activeQuests.values()) {
                if (quest instanceof BattleQuest) {
                    quest.incrementProgress(() -> {
                        // Notify view layer
                        System.out.println("Quest progress updated for: " + quest.getQuestName());
                        if (listener != null)
                            listener.onQuestProgressUpdated(quest.getQuestName(),
                                quest.getProgress(), quest.getCompletionGoal());
                    });

                    if (quest.getProgress() >= quest.getCompletionGoal()) {
                        System.out.println("You completed the battle quest: " + quest.getQuestName());
                        apprenticeTrainer.completeQuest(quest);
                    }
                    break; // Increment only one battle quest at a time
                }
            }
        }
    }

    /**
     * Allows the trainer to flee from a battle with  a 50% chance
     */
    @Override
    public boolean flee() {
        boolean fleeSuccess = new Random().nextBoolean();
        return fleeSuccess;
    }


    /**
     * Uses up a pokeball from the Trainer's inventory.
     * @param pokeball
     * @return
     */
    private boolean usePokeball(Pokeball pokeball) {
        for (Map.Entry<Integer, Pokeball> entry : pokeballInventory.entrySet()) {
            if (entry.getValue() == pokeball && entry.getKey() > 0) {
                int quantity = entry.getKey();
                pokeballInventory.remove(quantity);
                if (quantity > 1) {
                    pokeballInventory.put(quantity - 1, pokeball);
                }
                return true;
            }
        }
        return false;
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

            // check if the active Pokemon has more than 0 HP (is in battle)
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
            ", experience=" + experience +
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
            && Objects.equals(pokeballInventory, trainer.pokeballInventory) && trainer.experience
            == experience;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, level, ownedPokemon,
            pokeballInventory, experience);
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

    //experience bar goes back to 0 on level up
    public void setLevel(int level) {
        this.level = level;
        this.experience = 0;
    }

    public int getExperience() {
        return experience;
    }

    //increases experience by a certain amount
    public void increaseExperience(int experience) {
        this.experience += experience;
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

    public String getTrainerType() {
        return trainerType;
    }

    public void setTrainerType(String trainerType) {
        this.trainerType = trainerType;
    }
}
