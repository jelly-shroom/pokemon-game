package me.jessicasheng.pokemonGame.model.trainer;

import me.jessicasheng.pokemonGame.controller.QuestDataManager;
import me.jessicasheng.pokemonGame.model.BattleCapable;
import me.jessicasheng.pokemonGame.model.pokemon.Pokemon;
import me.jessicasheng.pokemonGame.model.quests.*;
import me.jessicasheng.pokemonGame.view.UI;

import java.util.*;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class MasterTrainer extends Trainer implements BattleCapable {
    private Map<Integer, Quest> createdQuests;
    private UI ui;

    public MasterTrainer(String username, String password, String name) {
        super(username, password, name, "Master");
        createdQuests = new HashMap<>();
    }

    /**
     * Creates a Quest object and adds it to the master file of
     * all quests.
     * @return
     */
    public Quest createQuest(String questName, String questDescription, int questReward,
                             QuestType questType, int completionGoal) {
        Quest newQuest = null;
        if (questType == QuestType.BATTLE) {
            newQuest = new BattleQuest(questType, questName, questDescription, questReward, completionGoal, 0);
        } else if (questType == QuestType.BONDING) {
            newQuest = new BondQuest(questType, questName, questDescription, questReward, completionGoal, 0);
        } else if (questType == QuestType.TRAINER_GROWTH){
            newQuest = new TrainerGrowthQuest(questType, questName, questDescription, questReward, completionGoal, 0);
        } else if (questType == QuestType.POKEMON_GROWTH){
            newQuest = new PokemonGrowthQuest(questType, questName, questDescription, questReward, completionGoal, 0);
        }

        //numerically increment the key for the quest
        int key = newQuest.getQuestID();
        createdQuests.put(key, newQuest);

        // Write the new quest to the CSV file
        QuestDataManager.addQuest(newQuest, super.getUsername());

        return newQuest;
    }

    public void removeQuest(int questID) {
        if (createdQuests.containsKey(questID)) {
            createdQuests.remove(questID);
            System.out.println("Quest with ID " + questID + " removed.");
        } else {
            System.out.println("No quest found with ID " + questID);
        }
    }

    public Map<Integer, Quest> getCreatedQuests() {
        return createdQuests;
    }

    public void setCreatedQuests(Map<Integer, Quest> createdQuests) {
        this.createdQuests = createdQuests;
    }

    /**
     * @param pokemon
     */
    @Override
    public void initiateBattle(Object pokemon) {
        super.initiateBattle(pokemon);
    }

    /**
     *
     */
    @Override
    public boolean flee() {
        return super.flee();
    }
}
