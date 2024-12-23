package me.jessicasheng.pokemonGame.controller;

import me.jessicasheng.pokemonGame.model.quests.*;

import java.io.*;
import java.util.*;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/10/24
*/
public class QuestDataManager {
    private static final String QUEST_FILE = "questData.csv";
    private static final String QUEST_CREATOR_FILE = "questCreators.csv";
    private static final String QUEST_TAKERS_FILE = "questTakers.csv";

    static Map<Integer, String> questCreators = new HashMap<>();
    static Map<Integer, List<String>> questTakers = new HashMap<>();

    /**
     * reads quests from the file and returns list
     *
     * @return
     */
    public static List<Quest> loadQuests() {
        List<Quest> quests = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(QUEST_FILE))) {
            //skip first line
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                //7 required fields
                if (parts.length == 7) {
                    String name = parts[0];
                    String description = parts[1];
                    int reward = Integer.parseInt(parts[2]);
                    QuestType type = QuestType.valueOf(parts[3].toUpperCase());
                    int goal = Integer.parseInt(parts[4]);
                    int progress = Integer.parseInt(parts[5]);
                    int questID = Integer.parseInt(parts[6]);

                    // new quest object based on type.
                    //used some ai to generate these repetitive statements
                    if (type == QuestType.BATTLE) {
                        quests.add(new BattleQuest(type, name, description, reward, goal, progress));
                    } else if (type == QuestType.BONDING) {
                        quests.add(new BondQuest(type, name, description, reward, goal, progress));
                    } else if (type == QuestType.TRAINER_GROWTH) {
                        quests.add(new TrainerGrowthQuest(type, name, description, reward, goal, progress));
                    } else if (type == QuestType.POKEMON_GROWTH) {
                        quests.add(new PokemonGrowthQuest(type, name, description, reward, goal, progress));
                    } else {
                        System.out.println("Unknown quest type: " + type);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Quest file not found. Returning an empty list.");
        }
        return quests;
    }

    /**
     * adds a new quest to the csv
     *
     * @param quest
     */
    public static void addQuest(Quest quest, String creatorUsername) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(QUEST_FILE, true))) {
            String formattedQuest = formatQuestForCsv(quest);
            writer.println(formatQuestForCsv(quest));
            System.out.println("Added quest to CSV: " + formattedQuest);
        } catch (IOException e) {
            System.out.println("Error writing to quest file.");
            e.printStackTrace();
        }

        // save the creator of the quest
        questCreators.put(quest.getQuestID(), creatorUsername);
        saveQuestCreators();
    }

    /**
     * associates an apprentice with a quest they have taken on.
     *
     * @param questId
     * @param apprenticeUsername
     */
    public static void addTaker(int questId, String apprenticeUsername) {
        questTakers.putIfAbsent(questId, new ArrayList<>());
        if (!questTakers.get(questId).contains(apprenticeUsername)) {
            questTakers.get(questId).add(apprenticeUsername);
            saveQuestTakers();
        }
    }

    /**
     * saves quest creators to a separate csv file
     */
    private static void saveQuestCreators() {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(QUEST_CREATOR_FILE))) {
            for (Map.Entry<Integer, String> entry : questCreators.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving quest creators.");
            e.printStackTrace();
        }
    }

    /**
     * saves quest takers to a csv file.
     */
    private static void saveQuestTakers() {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(QUEST_TAKERS_FILE))) {
            for (Map.Entry<Integer, List<String>> entry : questTakers.entrySet()) {
                writer.print(entry.getKey());
                for (String username : entry.getValue()) {
                    writer.print("," + username);
                }
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Error saving quest takers.");
            e.printStackTrace();
        }
    }

    /**
     * formats quest into string
     *
     * @param quest
     * @return formatted string
     */
    private static String formatQuestForCsv(Quest quest) {
        return String.join(",",
            quest.getQuestName(),
            quest.getQuestDescription(),
            String.valueOf(quest.getQuestReward()),
            quest.getQuestType().name(),
            String.valueOf(quest.getCompletionGoal()),
            String.valueOf(quest.getProgress()),
            String.valueOf(quest.getQuestID()));
    }

    /**
     * loads quest creators from file
     */
    public static void loadQuestCreators() {
        //clears first
        questCreators.clear();
        try (Scanner scanner = new Scanner(new File(QUEST_CREATOR_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                //2 required fields
                if (parts.length == 2) {
                    int questId = Integer.parseInt(parts[0]);
                    String creatorUsername = parts[1];
                    questCreators.put(questId, creatorUsername);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Quest creator file not found. Starting with an empty map.");
        }
    }

    /**
     * gets the creator of a quest
     * @param questId
     * @return
     */
    public static String getQuestCreator(int questId) {
        return questCreators.get(questId);
    }

    /**
     * loads quest takers
     */
    public static void loadQuestTakers() {
        questTakers.clear();
        try (Scanner scanner = new Scanner(new File(QUEST_TAKERS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                //should have 2
                if (parts.length == 2) {
                    int questId = Integer.parseInt(parts[0]);
                    List<String> takers = new ArrayList<>(Arrays.asList(parts).subList(1, parts.length));
                    questTakers.put(questId, takers);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Quest takers file not found. Starting with an empty map.");
        }
    }

    /**
     * tracks updates to quest progression (n/n completion)
     * @param updatedQuest
     */
    public static void updateQuest(Quest updatedQuest) {
        List<Quest> allQuests = loadQuests();
        // replace concerned quest
        for (int i = 0; i < allQuests.size(); i++) {
            if (allQuests.get(i).getQuestID() == updatedQuest.getQuestID()) {
                allQuests.set(i, updatedQuest);
                break;
            }
        }

        // rewrite quests
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(QUEST_FILE))) {
            writer.println("Name,Description,Reward,Type,Goal,Progress,ID"); // Write header
            for (Quest quest : allQuests) {
                writer.println(formatQuestForCsv(quest));
            }
        } catch (IOException e) {
            System.out.println("Error updating quests in CSV file.");
            e.printStackTrace();
        }
    }


    /**
     * gets list of quest takers
     * @param questId
     * @return
     */
    public static List<String> getQuestTakers(int questId) {
        return questTakers.getOrDefault(questId, new ArrayList<>());
    }

}

