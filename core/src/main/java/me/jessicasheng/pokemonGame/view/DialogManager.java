package me.jessicasheng.pokemonGame.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.jessicasheng.pokemonGame.controller.UserDataManager;

public class DialogManager {
    private final Skin skin;
    private final Stage stage;

    public DialogManager(Skin skin, Stage stage) {
        this.skin = skin;
        this.stage = stage;
    }

    public void showDialog(String title, String message, String buttonText, Runnable onClose) {
        Dialog dialog = new Dialog(title, skin);

        // Set background
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        bgPixmap.setColor(new Color(0.2f, 0.2f, 0.2f, 1));
        bgPixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new Texture(bgPixmap));
        dialog.setBackground(drawable);
        bgPixmap.dispose();

        // Configure dialog layout
        Table contentTable = dialog.getContentTable();
        Label messageLabel = new Label(message, skin);
        messageLabel.setWrap(true);
        contentTable.add(messageLabel).width(200).pad(20);

        // Configure button layout
        Table buttonTable = dialog.getButtonTable();
        buttonTable.pad(10);

        TextButton okButton = new TextButton(buttonText, skin);
        okButton.pad(8);
        dialog.button(okButton);

        if (onClose != null) {
            okButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    onClose.run();
                }
            });
        }

        dialog.show(stage);
    }

    public void showError(String message) {
        showDialog("Error", message, "OK", null);
    }

    public void showSuccess(String message, Runnable onClose) {
        showDialog("Success", message, "OK", onClose);
    }

}
