package me.jessicasheng.pokemonGame.controller;

import me.jessicasheng.pokemonGame.model.quests.Quest;
import me.jessicasheng.pokemonGame.model.trainer.*;

import java.io.*;
import java.io.ObjectInputStream;
import java.util.*;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class UserDataManager {
    private static final String FILE_PATH = "userData.ser";
    private Map<String, Trainer> userDatabase;


    public UserDataManager() {
        loadUserData();
    }

    /**
     * loads use rdata from file
     */
    private void loadUserData() {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object data = ois.readObject();
            if (data instanceof Map) {
                Map<String, Trainer> rawDatabase = (Map<String, Trainer>) data;
                userDatabase = new HashMap<>();

                for (Map.Entry<String, Trainer> entry : rawDatabase.entrySet()) {
                    Trainer rawTrainer = entry.getValue();
                    Trainer trainer;

                    // check the trainer type and recreate the correct subclass
                    if ("Apprentice".equalsIgnoreCase(rawTrainer.getTrainerType())) {
                        trainer = new ApprenticeTrainer(rawTrainer.getUsername(), rawTrainer.getPassword(), rawTrainer.getName());
                        ((ApprenticeTrainer) trainer).setActiveQuests(loadActiveQuests((ApprenticeTrainer) rawTrainer));
                    } else if ("Master".equalsIgnoreCase(rawTrainer.getTrainerType())) {
                        trainer = new MasterTrainer(rawTrainer.getUsername(), rawTrainer.getPassword(), rawTrainer.getName());
                        ((MasterTrainer) trainer).setCreatedQuests(loadCreatedQuests((MasterTrainer) rawTrainer));
                    } else {
                        throw new IllegalStateException("Unknown trainer type: " + rawTrainer.getTrainerType());
                    }

                    // Copy other fields from raw data
                    trainer.setLevel(rawTrainer.getLevel());
                    trainer.setOwnedPokemon(rawTrainer.getOwnedPokemon());
                    trainer.setPokeballInventory(rawTrainer.getPokeballInventory());

                    userDatabase.put(entry.getKey(), trainer);
                }
            } else {
                userDatabase = new HashMap<>();
            }

        } catch (FileNotFoundException e) {
            userDatabase = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            userDatabase = new HashMap<>();
        }
    }

    /**
     * loads active quests
     * only for paprentice trainers
     * @param apprentice
     * @return
     */
    private Map<Integer, Quest> loadActiveQuests(ApprenticeTrainer apprentice) {
        Map<Integer, Quest> activeQuests = apprentice.getActiveQuests();
        List<Quest> allQuests = QuestDataManager.loadQuests();

        // Ensure questTakers map is loaded
        QuestDataManager.loadQuestTakers();

        // Filter quests specific to this apprentice
        for (Quest quest : allQuests) {
            List<String> takers = QuestDataManager.getQuestTakers(quest.getQuestID());
            if (takers != null && takers.contains(apprentice.getUsername())) {
                activeQuests.put(quest.getQuestID(), quest);
            }
        }

        return activeQuests;
    }


    /**
     * loads created quests
     * only for master trainers
     * @param master
     * @return
     */
    private Map<Integer, Quest> loadCreatedQuests(MasterTrainer master) {
        Map<Integer, Quest> createdQuests = new HashMap<>();
        List<Quest> allQuests = QuestDataManager.loadQuests();

        // Load quest creators map
        QuestDataManager.loadQuestCreators();

        // Filter quests specific to this master
        for (Quest quest : allQuests) {
            String creator = QuestDataManager.getQuestCreator(quest.getQuestID());
            if (creator != null && creator.equals(master.getUsername())) {
                createdQuests.put(quest.getQuestID(), quest);
            }
        }

        return createdQuests;
    }

    /**
     * saves user data to file
     */
    public void saveUserData() {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(userDatabase);
            } catch (IOException e) {
        }
    }

    /**
     * validates user when logging in
     * @param username
     * @param password
     * @return
     */
    public boolean validateUser(String username, String password) {
        System.out.println("Validating user: " + username);
        Trainer trainer = userDatabase.get(username);

        if (trainer == null) {
            System.out.println("No trainer found with username: " + username);
            return false;
        }

        System.out.println("Trainer found: " + trainer.getUsername());
        System.out.println("Expected password: " + trainer.getPassword());
        System.out.println("Provided password: " + password);

        return trainer.getPassword().equals(password);
    }

    /**
     * registers a new user to system
     * @param username
     * @param password
     * @param displayName
     * @return
     */
    public boolean registerUser(String username, String password, String displayName) {
        boolean validUsername = true;
        //  if username already exists
        if (userDatabase.containsKey(username)) {
            validUsername = false;
        } else {
            //defaults to apprentice
            Trainer newTrainer = new ApprenticeTrainer(username, password, displayName);
            userDatabase.put(username, newTrainer);
            saveUserData();
        }
        return validUsername;
    }

    public Trainer getTrainer(String username) {
        return userDatabase.get(username);
    }
}
