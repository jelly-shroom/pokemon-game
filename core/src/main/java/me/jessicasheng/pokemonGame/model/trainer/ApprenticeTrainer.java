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

    /**
     * Accepts a quest and adds to activeQuests list
     * @param quest
     */
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
     * Initiates a battle with a wild pokemon
     * @param pokemon
     */
    @Override
    public void initiateBattle(Object pokemon) {
        super.initiateBattle(pokemon);
    }

    /**
     *Fles from a battle
     */
    @Override
    public boolean flee() {
        return super.flee();
    }

    /**
     * Completes a quest and removes it from activeQuests list
     * @param quest
     */
    public void completeQuest(Quest quest) {
        this.getActiveQuests().remove(quest.getQuestID());
    }
}
