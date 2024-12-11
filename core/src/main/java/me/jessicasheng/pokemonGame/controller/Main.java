package me.jessicasheng.pokemonGame.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import me.jessicasheng.pokemonGame.model.trainer.Trainer;
import me.jessicasheng.pokemonGame.view.LoginScreen;

import me.jessicasheng.pokemonGame.view.MainGameScreen;
import me.jessicasheng.pokemonGame.view.MainMenu;
import me.jessicasheng.pokemonGame.view.RegisterScreen;


public class Main extends Game {
    private final Game app = this;

    private Trainer loggedInTrainer = null;

    private Screen mainMenuScreen;
    private Screen loginScreen;
    private Screen registerScreen;
    private Screen mainGameScreen;

    @Override
    public void setScreen(Screen screen){
        Screen old = getScreen();
        super.setScreen(screen);
        if(old != null){
            old.dispose();
        }
    }


    @Override
    public void create() {
        GdxNativesLoader.load();

        mainMenuScreen = new MainMenu(this);
        loginScreen = new LoginScreen(this);
        registerScreen = new RegisterScreen(this);
        setScreen(mainMenuScreen);
    }

    public void toLogin() {
        setScreen(loginScreen);
    }

    public void toMainMenu() {
        setScreen(mainMenuScreen);
    }

    public void toRegister() {
        setScreen(registerScreen);
    }

    public void toGame() {
        mainGameScreen = new MainGameScreen(this, loggedInTrainer);

        setScreen(mainGameScreen);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        super.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        super.dispose();
        mainMenuScreen.dispose();
        loginScreen.dispose();
    }

    public Trainer getLoggedInTrainer() {
        return loggedInTrainer;
    }

    public void setLoggedInTrainer(Trainer loggedInTrainer) {
        this.loggedInTrainer = loggedInTrainer;
    }
}
