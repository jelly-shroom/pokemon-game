package me.jessicasheng.pokemonGame.model.trainer;
import me.jessicasheng.pokemonGame.controller.QuestDataManager;
import me.jessicasheng.pokemonGame.model.BattleCapable;
import me.jessicasheng.pokemonGame.model.pokemon.Pokemon;
import me.jessicasheng.pokemonGame.model.quests.*;
import java.util.*;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class ApprenticeTrainer extends Trainer implements BattleCapable {
    private Map<Integer, Quest> activeQuests;

    public ApprenticeTrainer(String username, String password, String name) {
        super(username, password, name, "Apprentice");
        activeQuests = new HashMap<>();
    }

    public void acceptQuest(Quest quest) {
        activeQuests.put(quest.getQuestID(), quest);
        QuestDataManager.addTaker(quest.getQuestID(), getUsername());
    }

    //getters and setters
    public Map<Integer, Quest> getActiveQuests() {
        return activeQuests;
    }

    public void setActiveQuests(Map<Integer, Quest> activeQuests) {
        this.activeQuests = activeQuests;
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
    public void flee() {
        super.flee();
    }

    public void completeQuest(Quest quest) {
        this.getActiveQuests().remove(quest.getQuestID());
    }
}
