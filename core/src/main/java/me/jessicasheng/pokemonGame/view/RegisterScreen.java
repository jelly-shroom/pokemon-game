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
public class RegisterScreen implements Screen {
    private final Game app;
    private Stage stage;
    private Skin skin;
    UserDataManager userManager;
    DialogManager dialogManager;


    public RegisterScreen(Game app) {
        this.app = app;
        this.userManager = new UserDataManager();

    }

    /**
     * if username is unique, new trainer is created and added to the game
     * @param username
     * @param password
     * @param displayName
     */
    public void handleRegistration(String username, String password, String displayName) {
        //fields cant be empty
        if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
            dialogManager.showError("Please fill in all fields");
            return;
        }

        //if regusterUser returns true (unique username)
        if (userManager.registerUser(username, password, displayName)) {
            Trainer trainer = new ApprenticeTrainer(username, password, displayName);
            dialogManager.showSuccess("Registration successful! Welcome.", () -> {
                ((Main) app).setLoggedInTrainer(trainer);
                ((Main)app).toGame();
            });
        } else {
            dialogManager.showError("Username already exists");
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


        //table styling
        Table table = new Table();
        table.setFillParent(true);
        Label titleLabel = new Label("Register", skin);

        //text fields
        TextField usernameField = new TextField("", skin);
        usernameField.setWidth(200);
        TextField displayNameField = new TextField("", skin);
        displayNameField.setWidth(200);
        TextField passwordField = new TextField("", skin);
        passwordField.setWidth(200);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        //group into horizontal groups
        HorizontalGroup buttonGroup = new HorizontalGroup().space(8f);
        HorizontalGroup usernameGroup = new HorizontalGroup().space(8f);
        HorizontalGroup displayNameGroup = new HorizontalGroup().space(8f);
        HorizontalGroup passwordGroup = new HorizontalGroup().space(8f);

        //buttons
        TextButton registerButton = (TextButton) new TextButton("Register", skin).pad(8f);
        TextButton backButton = (TextButton) new TextButton("Back", skin).pad(8f);

        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();
                String displayName = displayNameField.getText().trim();

                handleRegistration(username, password, displayName);
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Main)app).toMainMenu();
            }
        });

        //add buttons to group
        buttonGroup.addActor(registerButton);
        buttonGroup.addActor(backButton);

        usernameGroup.addActor(new Label("Username:", skin));
        usernameGroup.addActor(usernameField);

        passwordGroup.addActor(new Label("Password:", skin));
        passwordGroup.addActor(passwordField);

        displayNameGroup.addActor(new Label("Display Name:", skin));
        displayNameGroup.addActor(displayNameField);

        table.add(titleLabel).colspan(1).padBottom(20).row();
        table.add(usernameGroup.padBottom(10)).row();
        table.add(displayNameGroup.padBottom(10)).row();
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
