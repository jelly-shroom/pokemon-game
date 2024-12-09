package me.jessicasheng.pokemonGame.controller;

import me.jessicasheng.pokemonGame.model.trainer.ApprenticeTrainer;
import me.jessicasheng.pokemonGame.model.trainer.Trainer;

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

    private void loadUserData() {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object data = ois.readObject();
            if (data instanceof Map) {
                userDatabase = (Map<String, Trainer>) data;
            } else {
                userDatabase = new HashMap<>();
            }

        } catch (FileNotFoundException e) {
            userDatabase = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            userDatabase = new HashMap<>();
        }
    }

    public void saveUserData() {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(userDatabase);

        } catch (IOException e) {
        }
    }

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

    public boolean registerUser(String username, String password, String displayName) {
        boolean validUsername = true;
        if (userDatabase.containsKey(username)) {
            validUsername = false;
        } else {
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
