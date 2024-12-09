package me.jessicasheng.pokemonGame.model.quests;

import java.io.Serializable;
import java.util.Objects;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public abstract class Quest implements Serializable {
    private QuestType questType;
    private String questName;
    private String questDescription;
    private int questReward;

    public Quest(QuestType quest, String questName,
                 String questDescription, int questReward) {
        this.questName = questName;
        this.questDescription = questDescription;
        this.questReward = questReward;
    }

    //TODO: startQuest, completeQuest, etc

    //toString
    @Override
    public String toString() {
        return "Quest{" +
            "questType=" + questType +
            ", questName='" + questName + '\'' +
            ", questDescription='" + questDescription + '\'' +
            ", questReward=" + questReward +
            '}';
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;
        return questReward == quest.questReward && questType == quest.questType && Objects.equals(questName, quest.questName) && Objects.equals(questDescription, quest.questDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questType, questName, questDescription, questReward);
    }

    // Getters and setters
    public QuestType getQuestType() {
        return questType;
    }

    public void setQuestType(QuestType questType) {
        this.questType = questType;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public void setQuestDescription(String questDescription) {
        this.questDescription = questDescription;
    }

    public int getQuestReward() {
        return questReward;
    }

    public void setQuestReward(int questReward) {
        this.questReward = questReward;
    }
}
