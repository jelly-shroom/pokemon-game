package me.jessicasheng.pokemonGame.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jessicasheng.pokemonGame.controller.Main;
import me.jessicasheng.pokemonGame.controller.UserDataManager;
import me.jessicasheng.pokemonGame.model.trainer.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class LoginScreen implements Screen {
    private final Game app;
    private Stage stage;
    private Skin skin;
    UserDataManager userManager;
    private DialogManager dialogManager;

    public LoginScreen(Game app) {
        this.app = app;
        this.userManager = new UserDataManager();

    }

    /**
     * Handles the login process. If the username and password are valid, the user is transitioned to the game screen.
     * @param username
     * @param password
     */
    private void handleLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            dialogManager.showError("Please fill in all fields");
            return;
        }

        if (userManager.validateUser(username, password)) {
            Trainer trainer = userManager.getTrainer(username);

            if (trainer != null) {
                // Transition to the game screen with the trainer object
                ((Main) app).setLoggedInTrainer(trainer);
                ((Main) app).toGame();
            } else {
                dialogManager.showError("Error loading trainer data. Please try again.");
            }
        } else {
            dialogManager.showError("Invalid credentials. Please check your username and password.");
        }
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        stage = new Stage(new FitViewport(640, 480));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        dialogManager = new DialogManager(skin, stage);


        Table table = new Table();

        table.setFillParent(true);

        Label titleLabel = new Label("Login", skin);
        TextField usernameField = new TextField("", skin);
        usernameField.setWidth(200);
        TextField passwordField = new TextField("", skin);
        passwordField.setWidth(200);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        HorizontalGroup buttonGroup = new HorizontalGroup().space(8f);
        HorizontalGroup usernameGroup = new HorizontalGroup().space(8f);
        HorizontalGroup passwordGroup = new HorizontalGroup().space(8f);

        TextButton loginButton = (TextButton) new TextButton("Login", skin).pad(8f);
        TextButton backButton = (TextButton) new TextButton("Back", skin).pad(8f);

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();
                handleLogin(username, password);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Main)app).toMainMenu();
            }
        });

        buttonGroup.addActor(loginButton);
        buttonGroup.addActor(backButton);

        usernameGroup.addActor(new Label("Username:", skin));
        usernameGroup.addActor(usernameField);

        passwordGroup.addActor(new Label("Password:", skin));
        passwordGroup.addActor(passwordField);

        table.add(titleLabel).colspan(1).padBottom(20).row();
        table.add(usernameGroup.padBottom(10)).row();
        table.add(passwordGroup.padBottom(10)).row();

        table.add(buttonGroup).colspan(2).row();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        stage.act(delta);
        stage.draw();
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
