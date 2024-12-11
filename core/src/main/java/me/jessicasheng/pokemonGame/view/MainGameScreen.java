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
import me.jessicasheng.pokemonGame.controller.QuestDataManager;
import me.jessicasheng.pokemonGame.controller.PokemonDataReader;
import me.jessicasheng.pokemonGame.model.Pokeball;
import me.jessicasheng.pokemonGame.model.pokemon.PokemonType;
import me.jessicasheng.pokemonGame.model.pokemon.WildPokemon;
import me.jessicasheng.pokemonGame.model.quests.Quest;
import me.jessicasheng.pokemonGame.model.quests.QuestType;
import me.jessicasheng.pokemonGame.model.trainer.ApprenticeTrainer;
import me.jessicasheng.pokemonGame.model.trainer.MasterTrainer;
import me.jessicasheng.pokemonGame.model.trainer.Trainer;

import java.util.*;
import java.util.List;

public class MainGameScreen implements Screen {
    private final Main app;
    private final Trainer trainer;
    private Stage stage;
    private Skin skin;
    private DialogManager dialogManager;

    public MainGameScreen(Main app, Trainer trainer) {
        this.app = app;
        this.trainer = trainer;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(640, 480));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        dialogManager = new DialogManager(skin, stage);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // trainer Info
        Label displayNameLabel = new Label("Trainer: " + trainer.getName(), skin);
        Label levelLabel = new Label("Level: " + trainer.getLevel(), skin);
        Label typeLabel = new Label("Type: " + (trainer instanceof ApprenticeTrainer ?
            "Apprentice Trainer" : "Master Trainer"), skin);
        Label experienceLabel = new Label("Experience: " + trainer.getExperience(), skin); // Example experience calculation
        Label ownedPokemonLabel = new Label("Owned Pokemon: " + trainer.getOwnedPokemon().size(), skin);

        //TODO: implement this properly
//        Label pokeballListLable = new Label("Pokeballs: " + trainer.getPokeballInventory().size(), skin);
//        Label activeQuestLabel = new Label("Active Quest: " + (((ApprenticeTrainer) trainer).getActiveQuest() != null ? trainer.getActiveQuest().getDescription() : "None"), skin);
        // Pokeball Inventory Table
        Table pokeballTable = createPokeballTable();
        ScrollPane questScrollPane = createQuestScrollPane();


        // random event button
        TextButton randomEventButton = new TextButton("Encounter Random Event", skin);

        randomEventButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                handleRandomEvent();
            }
        });

        // talbe layout
        table.add(displayNameLabel).align(Align.left).padBottom(10).row();
        table.add(levelLabel).align(Align.left).padBottom(10).row();
        table.add(typeLabel).align(Align.left).padBottom(10).row();
        table.add(experienceLabel).align(Align.left).padBottom(10).row();
        table.add(ownedPokemonLabel).align(Align.left).padBottom(10).row();

        table.add(new Label("Pokeballs:", skin)).align(Align.left).padBottom(10).row();
        table.add(pokeballTable).align(Align.left).padBottom(20).row();

        table.add(new Label(trainer instanceof ApprenticeTrainer ?
            "Active Quests:" : "Created Quests:", skin)).align(Align.left).padBottom(10).row();
        table.add(questScrollPane).width(400).height(150).padBottom(20).row();


        table.add(randomEventButton).width(200).height(50).padBottom(20).row();

        // Only show takeQuestButton for ApprenticeTrainer
        if (trainer instanceof ApprenticeTrainer) {
            TextButton takeQuestButton = new TextButton("Take on a Quest", skin);

            takeQuestButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    showAvailableQuestsDialog();
                }
            });

            table.add(takeQuestButton).width(200).height(50).padBottom(20).row();

        } else if (trainer instanceof MasterTrainer) {
            TextButton createQuestButton = new TextButton("Create Quest", skin);
            createQuestButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    showCreateQuestDialog();
                }
            });

            table.add(createQuestButton).width(200).height(50).padBottom(20).row();
        }
    }

    /**
     * creates table to display pokeball inventory
     * @return
     */
    private Table createPokeballTable() {
        Table pokeballTable = new Table(skin);

        for (Map.Entry<Integer, Pokeball> entry : trainer.getPokeballInventory().entrySet()) {
            String pokeballType = entry.getValue().name();
            int quantity = entry.getKey(); // Assuming key is the quantity
            pokeballTable.add(new Label(pokeballType + ": " + quantity, skin)).padRight(10);
            pokeballTable.row();
        }

        return pokeballTable;
    }

    /**
     * creates a scroll pane to display quests
     * @return
     */
    private ScrollPane createQuestScrollPane() {
        Table questTable = new Table(skin);

        if (trainer instanceof ApprenticeTrainer) {
            // Display active quests for ApprenticeTrainer
            Map<Integer, Quest> activeQuests = ((ApprenticeTrainer) trainer).getActiveQuests();
            if (activeQuests.isEmpty()) {
                questTable.add(new Label("No active quests.", skin)).padBottom(10).row();
            } else {
                for (Map.Entry<Integer, Quest> entry : activeQuests.entrySet()) {
                    Quest quest = entry.getValue();
                    questTable.add(new Label(quest.getQuestName() + ": " + quest.getQuestDescription(), skin)).padBottom(10).row();
                }
            }
        } else if (trainer instanceof MasterTrainer) {
            // Display created quests for MasterTrainer
            Map<Integer, Quest> createdQuests = ((MasterTrainer) trainer).getCreatedQuests();
            if (createdQuests.isEmpty()) {
                questTable.add(new Label("No created quests.", skin)).padBottom(10).row();
            } else {
                for (Map.Entry<Integer, Quest> entry : createdQuests.entrySet()) {
                    Quest quest = entry.getValue();
                    questTable.add(new Label(quest.getQuestName() + ": " + quest.getQuestDescription(), skin)).padBottom(10).row();
                }
            }
        }

        return new ScrollPane(questTable, skin);
    }


    /**
     * refreshes UI
     */
    //needed to make sure ui updates when quests are added or taken on
    private void refreshQuests() {
        ScrollPane updatedScrollPane = createQuestScrollPane();

        // Clear stage and re-add all UI elements
        stage.clear();
        show(); // Rebuilds all UI elements using updated data
    }

    /**
     * shows available quests to take on
     * only for apprentice trainers
     */
    private void showAvailableQuestsDialog() {
        if (!(trainer instanceof ApprenticeTrainer)) return;

        ApprenticeTrainer apprentice = (ApprenticeTrainer) trainer;
        List<Quest> availableQuests = QuestDataManager.loadQuests();

        // Create the dialog
        Dialog dialog = new Dialog("Available Quests", skin);
        Table contentTable = new Table(skin);

        if (availableQuests.isEmpty()) {
            contentTable.add(new Label("No available quests.", skin)).padBottom(10).row();
        } else {
            for (Quest quest : availableQuests) {
                Table questRow = new Table(skin);

                // Quest details
                Label questDetails = new Label(
                    "Name: " + quest.getQuestName() + "\n" +
                        "Description: " + quest.getQuestDescription() + "\n" +
                        "Reward: " + quest.getQuestReward(),
                    skin
                );
                questDetails.setWrap(true);

                // Take quest button
                TextButton takeQuestButton = new TextButton("Take Quest", skin);
                takeQuestButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        // Add the quest to the apprentice's active quests
                        apprentice.acceptQuest(quest);
                        QuestDataManager.addTaker(quest.getQuestID(), apprentice.getUsername());

                        // Refresh UI
                        refreshQuests();

                        // Close the dialog
                        dialog.hide();
                    }
                });

                // Add details and button to the row
                questRow.add(questDetails).width(300).padRight(10);
                questRow.add(takeQuestButton).width(100);
                contentTable.add(questRow).padBottom(10).row();
            }
        }

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        dialog.getContentTable().add(scrollPane).width(450).height(300);
        dialog.button("Close").padTop(10);
        dialog.show(stage);
    }


    /**
     * shows dialog to create a new quest
     * only for master trainers
     */
    private void showCreateQuestDialog() {
        if (!(trainer instanceof MasterTrainer)) return;

        SelectBox<QuestType> typeSelectBox = new SelectBox<>(skin);
        TextField nameField = new TextField("", skin);
        TextField goalField = new TextField("", skin);
        TextField rewardField = new TextField("", skin);
        typeSelectBox.setItems(QuestType.values());

        // Initialize the dialog
        final Dialog dialog = new Dialog("Create New Quest", skin) {
            @Override
            protected void result(Object object) {
                if (Boolean.TRUE.equals(object)) {
                    try {
                        QuestType type = typeSelectBox.getSelected();
                        String name = nameField.getText().trim();
                        int goal = Integer.parseInt(goalField.getText().trim());
                        // Generate quest description based on type and goal
                        String description = generateQuestDescription(type, goal);
                        int reward = Integer.parseInt(rewardField.getText().trim());

                        // Create and add the quest
                        ((MasterTrainer) trainer).createQuest(name, description, reward, type, goal);

                        refreshQuests();


                        this.hide(); // Close the dialog
                    } catch (NumberFormatException e) {
                        dialogManager.showDialog("Error", "Invalid reward amount.", "OK", null, null, null);
                    }
                }
            }
        };

        // Create table layout
        Table contentTable = new Table();
        contentTable.setFillParent(true);

        // Title label
        Label titleLabel = new Label("Create New Quest", skin);

        // Horizontal groups for each row
        HorizontalGroup nameGroup = new HorizontalGroup().space(10);
        nameGroup.addActor(new Label("Name:", skin));
        nameGroup.addActor(nameField);

        HorizontalGroup descriptionGroup = new HorizontalGroup().space(10);
        descriptionGroup.addActor(new Label("Completion Goal (number):", skin));
        descriptionGroup.addActor(goalField);

        HorizontalGroup rewardGroup = new HorizontalGroup().space(10);
        rewardGroup.addActor(new Label("Reward:", skin));
        rewardGroup.addActor(rewardField);

        HorizontalGroup typeGroup = new HorizontalGroup().space(10);
        typeGroup.addActor(new Label("Type:", skin));
        typeGroup.addActor(typeSelectBox);

        // Add rows to the table
        contentTable.add(titleLabel).colspan(2).padBottom(20).row();
        contentTable.add(nameGroup).padBottom(10).row();
        contentTable.add(descriptionGroup).padBottom(10).row();
        contentTable.add(rewardGroup).padBottom(10).row();
        contentTable.add(typeGroup).padBottom(20).row();

        dialog.getContentTable().add(contentTable);

        // Add buttons with associated results
        dialog.button("Create", true);  // Passes true as the result
        dialog.button("Cancel", false); // Passes false as the result

        // Show the dialog
        dialog.show(stage);
    }

    /**
     * Generates a quest description based on its type and completion goal.
     */
    private String generateQuestDescription(QuestType type, int goal) {
        switch (type) {
            case BATTLE:
                return "Battle a wild Pokémon " + goal + " times.";
            case BONDING:
                return "Feed your Pokémon " + goal + " times.";
            case TRAINER_GROWTH:
                return "Gain " + goal + " trainer levels.";
            case POKEMON_GROWTH:
                return "Level up your Pokémon " + goal + " times.";
            default:
                return "Complete this quest.";
        }
    }

    /**
     * handles random events when the user clicks the "random event" button.
     */
    private void handleRandomEvent() {
        Random random = new Random();
        int eventType = random.nextInt(3);

        String message;
        switch (eventType) {
            case 0:
                // give Pokeballs
                trainer.getPokeballInventory().putIfAbsent(Pokeball.POKEBALL.ordinal(), Pokeball.POKEBALL);
                message = "You found some Pokeballs!";
                break;
            case 1:
                // wild pokemon
                WildPokemon wildPokemon = generateRandomWildPokemon();
                handleWildPokemonEncounter(wildPokemon);
                message = "You encountered a wild Pokémon!";
                break;
            case 2:
                // gain experience
                trainer.levelUp();
                message = "You gained experience and leveled up!";
                break;
            default:
                message = "Nothing happened...";
                break;
        }

        dialogManager.showDialog("Random Event", message, "OK", null, null, null);
    }

    /**
     * generates a random Wild Pokemon for the player to encounter.
     */
    private WildPokemon generateRandomWildPokemon() {
        List<WildPokemon> pokemonList = PokemonDataReader.loadPokemonData();
        if (pokemonList.isEmpty()) {
            throw new IllegalStateException("No Pokémon data available.");
        }

        Random random = new Random();
        return pokemonList.get(random.nextInt(pokemonList.size()));
    }

    private void handleWildPokemonEncounter(WildPokemon wildPokemon) {
        Random random = new Random();
        boolean pokemonWantsToFight = random.nextBoolean(); // 50% chance to initiate a battle

        if (pokemonWantsToFight) {
            // Wild Pokémon initiates battle
            dialogManager.showDialog("Wild Pokémon Battle",
                wildPokemon.getName() + " wants to fight!",
                "Flee", () -> trainer.flee(),
                "Fight", () -> trainer.initiateBattle(wildPokemon));
        } else {
            // Wild Pokémon is passive; give options to fight or capture
            dialogManager.showDialog("Wild Pokémon Encounter",
                wildPokemon.getName() + " is passive. What do you want to do?",
                "Capture", () -> showPokeballSelectionDialog(wildPokemon),
                "Fight", () -> trainer.initiateBattle(wildPokemon));
        }
    }

    private void showPokeballSelectionDialog(WildPokemon wildPokemon) {
        Map<Integer, Pokeball> pokeballInventory = trainer.getPokeballInventory();
        if (pokeballInventory.isEmpty()) {
            dialogManager.showDialog("No Pokéballs",
                "You don't have any Pokéballs!",
                "OK", null, null, null);
            return;
        }

        Dialog pokeballDialog = new Dialog("Select a Pokéball", skin);
        Table pokeballTable = new Table(skin);

        // Dynamically create buttons for each Pokéball in inventory
        for (Map.Entry<Integer, Pokeball> entry : pokeballInventory.entrySet()) {
            int quantity = entry.getKey();
            Pokeball pokeball = entry.getValue();

            if (quantity > 0) { // Only show Pokéballs with quantities > 0
                TextButton pokeballButton = new TextButton(pokeball.name() + " (" + quantity + ")", skin);
                pokeballButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        boolean success = trainer.attemptCapture(wildPokemon, pokeball); // Attempt capture
                        String resultMessage = success ?
                            "You successfully captured " + wildPokemon.getName() + "!" :
                            wildPokemon.getName() + " escaped!";
                        dialogManager.showDialog("Capture Result", resultMessage, "OK", null
                            , null, null);

                        // Close the selection dialog
                        pokeballDialog.hide();
                    }
                });
                pokeballTable.add(pokeballButton).pad(10).row();
            }
        }

        ScrollPane scrollPane = new ScrollPane(pokeballTable, skin);
        pokeballDialog.getContentTable().add(scrollPane).width(300).height(200);
        pokeballDialog.button("Cancel").padTop(10); // Add a cancel button
        pokeballDialog.show(stage);
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
