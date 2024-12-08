package me.jessicasheng.pokemonGame.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jessicasheng.pokemonGame.view.LoginScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import me.jessicasheng.pokemonGame.view.MainMenu;


public class Main extends Game {


    private final Game app = this;

    private Screen mainMenuScreen;
    private Screen loginScreen;

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
        setScreen(mainMenuScreen);
//        window.pack();
//        // We round the window position to avoid awkward half-pixel artifacts.
//        // Casting using (int) would also work.
//        window.setPosition(MathUtils.roundPositive(stage.getWidth() / 2f - window.getWidth() / 2f),
//            MathUtils.roundPositive(stage.getHeight() / 2f - window.getHeight() / 2f));
//        window.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(1f)));
//        stage.addActor(window);


    }

    public void toLogin() {
        setScreen(loginScreen);
    }

    public void toMainMenu() {
        setScreen(mainMenuScreen);
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

}
