package me.jessicasheng.pokemonGame.view;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/8/24
*/

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jessicasheng.pokemonGame.controller.Main;
import me.jessicasheng.pokemonGame.model.Pokeball;
import me.jessicasheng.pokemonGame.model.pokemon.PokemonStages;
import me.jessicasheng.pokemonGame.model.pokemon.PokemonType;
import me.jessicasheng.pokemonGame.model.pokemon.WildPokemon;
import me.jessicasheng.pokemonGame.model.trainer.ApprenticeTrainer;
import me.jessicasheng.pokemonGame.model.trainer.Trainer;

import java.util.Random;

public class MainGame implements Screen {
    private final Main app;
    private final Trainer trainer;
    private Stage stage;
    private Skin skin;

    public MainGame(Main app, Trainer trainer) {
        this.app = app;
        this.trainer = trainer;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(640, 480));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Trainer Info
        Label displayNameLabel = new Label("Trainer: " + trainer.getName(), skin);
        Label levelLabel = new Label("Level: " + trainer.getLevel(), skin);
        Label typeLabel = new Label("Type: " + (trainer instanceof ApprenticeTrainer ? "Apprentice Trainer" : "Master Trainer"), skin);
        Label experienceLabel = new Label("Experience: " + trainer.getExperience(), skin); // Example experience calculation
        Label ownedPokemonLabel = new Label("Owned Pokemon: " + trainer.getOwnedPokemon().size(), skin);
//        Label activeQuestLabel = new Label("Active Quest: " + (trainer.getActiveQuest() != null ? trainer.getActiveQuest().getDescription() : "None"), skin);

        // Buttons
        TextButton randomEventButton = new TextButton("Encounter Random Event", skin);
        TextButton takeQuestButton = new TextButton("Take on a Quest", skin);

        // Add Listeners for Buttons
        randomEventButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                handleRandomEvent();
            }
        });

        takeQuestButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Main) app).toMainMenu();
            }
        });

        // Layout
        table.add(displayNameLabel).align(Align.left).padBottom(10).row();
        table.add(levelLabel).align(Align.left).padBottom(10).row();
        table.add(typeLabel).align(Align.left).padBottom(10).row();
        table.add(experienceLabel).align(Align.left).padBottom(10).row();
        table.add(ownedPokemonLabel).align(Align.left).padBottom(10).row();
//        table.add(activeQuestLabel).align(Align.left).padBottom(20).row();

        table.add(randomEventButton).width(200).height(50).padBottom(20).row();
        table.add(takeQuestButton).width(200).height(50);
    }

    /**
     * Handles random events when the user clicks the "Encounter Random Event" button.
     */
    private void handleRandomEvent() {
        Random random = new Random();
        int eventType = random.nextInt(3);

        String message;
        switch (eventType) {
            case 0:
                // Give Pokeballs
                trainer.getPokeballInventory().putIfAbsent(Pokeball.POKEBALL.ordinal(), Pokeball.POKEBALL);
                message = "You found some Pokeballs!";
                break;
            case 1:
                // Encounter Wild Pokemon
                WildPokemon wildPokemon = generateRandomWildPokemon();
                message = "You encountered a wild " + wildPokemon.getName() + "! Try to capture it!";
                break;
            case 2:
                // Gain Experience
                trainer.levelUp();
                message = "You gained experience and leveled up!";
                break;
            default:
                message = "Nothing happened...";
                break;
        }

        showDialog("Random Event", message);
    }

    /**
     * Generates a random Wild Pokemon for the player to encounter.
     */
    private WildPokemon generateRandomWildPokemon() {
        String[] pokemonNames = {"Pikachu", "Charmander", "Bulbasaur", "Squirtle"};
        int level = new Random().nextInt(10) + 1; // Random level between 1 and 10
        return new WildPokemon(pokemonNames[new Random().nextInt(pokemonNames.length)], level, 50, 10, PokemonType.ELECTRIC, PokemonStages.BASIC, 30.0);
    }

    /**
     * Displays a dialog with a given title and message.
     */
    private void showDialog(String title, String message) {
        Dialog dialog = new Dialog(title, skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
