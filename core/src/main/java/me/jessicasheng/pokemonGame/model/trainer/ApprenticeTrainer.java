package me.jessicasheng.pokemonGame.model.trainer;
import me.jessicasheng.pokemonGame.controller.QuestDataManager;
import me.jessicasheng.pokemonGame.model.quests.*;
import java.util.*;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class ApprenticeTrainer extends Trainer {
    private Map<QuestType, Quest> activeQuests;

    public ApprenticeTrainer(String username, String password, String name) {
        super(username, password, name, "Apprentice");
        activeQuests = new HashMap<>();
    }

    public void acceptQuest(Quest quest) {
        activeQuests.put(quest.getQuestType(), quest);
        QuestDataManager.addTaker(quest.getQuestID(), getUsername());
    }

    //getters and setters
    public Map<QuestType, Quest> getActiveQuests() {
        return activeQuests;
    }

    public void setActiveQuests(Map<QuestType, Quest> activeQuests) {
        this.activeQuests = activeQuests;
    }
}
