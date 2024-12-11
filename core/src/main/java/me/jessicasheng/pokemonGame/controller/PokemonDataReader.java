package me.jessicasheng.pokemonGame.controller;


import me.jessicasheng.pokemonGame.model.pokemon.PokemonStages;
import me.jessicasheng.pokemonGame.model.pokemon.PokemonType;
import me.jessicasheng.pokemonGame.model.pokemon.WildPokemon;

import java.io.*;
import java.util.*;

public class PokemonDataReader {
    private static final String POKEMON_FILE = "pokemon.csv";

    /**
     * Loads Pokemon data from a CSV file and returns a list of WildPokemon objects.
     *
     * @return List of WildPokemon objects.
     */
    public static List<WildPokemon> loadPokemonData() {
        List<WildPokemon> pokemonList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(POKEMON_FILE))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header line
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 7) { // Ensure required fields are present
                    String name = parts[1];
                    PokemonType type1 = PokemonType.valueOf(parts[2].toUpperCase());
                    PokemonType type2 = parts[3].isEmpty() ? null : PokemonType.valueOf(parts[3].toUpperCase());
                    int hp = Integer.parseInt(parts[5]);
                    int baseAttack = Integer.parseInt(parts[6]);

                    // Generate random level between 1 and 10
                    int level = new Random().nextInt(10) + 1;

                    WildPokemon wildPokemon = new WildPokemon(name, type1, type2, hp, baseAttack,
                        level, PokemonStages.BASIC, 30.0);
                    pokemonList.add(wildPokemon);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Pokemon file not found.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error reading Pokemon file.");
            e.printStackTrace();
        }
        return pokemonList;
    }
}
