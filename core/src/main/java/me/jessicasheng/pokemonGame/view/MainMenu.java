package me.jessicasheng.pokemonGame.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jessicasheng.pokemonGame.controller.Main;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/8/24
*/
public class MainMenu implements Screen{
    private final Game app;
    private Stage stage;
    private Skin skin;

    public MainMenu(Game app) {
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(640, 480));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);

        HorizontalGroup buttonGroup = new HorizontalGroup().space(8f);

        Label welcomeLabel = new Label("Welcome to the Pokemon game!", skin);
        TextButton loginButton = (TextButton) new TextButton("Login", skin).pad(8f);
        TextButton registerButton = (TextButton) new TextButton("Register", skin).pad(8f);

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Main)app).toLogin();
            }
        });
        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Main)app).toRegister();
            }
        });


        buttonGroup.addActor(loginButton);
        buttonGroup.addActor(registerButton);

        table.add(welcomeLabel).padBottom(20).row();
        table.add(buttonGroup).row();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
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
