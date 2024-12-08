package me.jessicasheng.pokemonGame.model.trainer;

import me.jessicasheng.pokemonGame.model.quests.*;
import me.jessicasheng.pokemonGame.view.UI;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class MasterTrainer extends Trainer {
    private UI ui;
    public MasterTrainer(String username, String password, String name) {
        super(username, password, name);
    }

    /**
     * Creates a Quest object and adds it to the master file of 
     * all quests.
     * @return
     */
    public Quest createQuest() {
        Quest newQuest = null;
        String questChoice = ui.readln("Enter the type of quest: ");
        QuestType questType = QuestType.valueOf(questChoice.toUpperCase());
        
        String questName = ui.readln("Enter the name of the quest: ");
        String questDescription = ui.readln("Enter the description of the quest: ");
        int questReward = Integer.parseInt(ui.readln("Enter the reward for the quest: "));
        
        if (questType == QuestType.BATTLE) {
            newQuest = new BattleQuest(questType, questName, questDescription, questReward);
        } else if (questType == QuestType.BONDING) {
            newQuest = new BondQuest(questType, questName, questDescription, questReward);
        } else if (questType == QuestType.TRAINER_GROWTH){
            newQuest = new TrainerGrowthQuest(questType, questName, questDescription, questReward);
        } else if (questType == QuestType.POKEMON_GROWTH){
            newQuest = new PokemonGrowthQuest(questType, questName, questDescription, questReward);
        }
        
        return newQuest;
    }
}
