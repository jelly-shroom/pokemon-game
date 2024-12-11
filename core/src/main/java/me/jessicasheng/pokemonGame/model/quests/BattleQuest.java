package me.jessicasheng.pokemonGame.model.quests;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class BattleQuest extends Quest{
    public BattleQuest(QuestType quest, String questName,
                    String questDescription, int questReward, int completionGoal) {
        super(quest, questName, questDescription, questReward, completionGoal);
    }

    //TODO: battleWildPokemon, winBattle, loseBattle
}
