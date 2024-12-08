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

public class Main extends Game {
    private Stage stage;
    private Skin skin;

    private TextButton loginButton, registerButton;
    private final Game app = this;


    @Override
    public void setScreen(Screen screen){
        Screen old = getScreen();
        super.setScreen(screen);
        if(old != null){
            old.dispose();
        }
    }
    public void toLogin() {
        app.setScreen(new LoginScreen(this));
    }

    @Override
    public void create() {
        GdxNativesLoader.load();

        stage = new Stage(new FitViewport(640, 480));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Window window = new Window("Log in or register", skin, "border");
        window.defaults().pad(8f);
        window.add("Welcome to the Pokemon game!").row();

        loginButton = new TextButton("Login", skin);
        registerButton = new TextButton("Register", skin);
        loginButton.pad(8f);
        registerButton.pad(8f);

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                toLogin();
            }
        });

        HorizontalGroup buttons = new HorizontalGroup().space(8f);
        window.add(buttons).row();

        buttons.addActor(loginButton);
        buttons.addActor(registerButton);

        window.pack();
        // We round the window position to avoid awkward half-pixel artifacts.
        // Casting using (int) would also work.
        window.setPosition(MathUtils.roundPositive(stage.getWidth() / 2f - window.getWidth() / 2f),
            MathUtils.roundPositive(stage.getHeight() / 2f - window.getHeight() / 2f));
        window.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(1f)));
        stage.addActor(window);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        super.render();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}
