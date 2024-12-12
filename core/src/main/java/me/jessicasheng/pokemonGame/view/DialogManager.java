package me.jessicasheng.pokemonGame.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class DialogManager {
    private final Skin skin;
    private final Stage stage;

    public DialogManager(Skin skin, Stage stage) {
        this.skin = skin;
        this.stage = stage;
    }

    public void showDialog(String title, String message, String button1Text, Runnable button1Action,
                           String button2Text, Runnable button2Action) {
        Dialog dialog = new Dialog(title, skin);

        // Set background
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        bgPixmap.setColor(new Color(0.2f, 0.2f, 0.2f, 1));
        bgPixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new Texture(bgPixmap));
        dialog.setBackground(drawable);
        bgPixmap.dispose();

        // dialog layout
        Table contentTable = dialog.getContentTable();
        Label messageLabel = new Label(message, skin);
        messageLabel.setWrap(true);
        contentTable.add(messageLabel).width(200).pad(20).row();

        // Button layout
        checkIfNull(button1Text, button1Action, dialog);
        checkIfNull(button2Text, button2Action, dialog);

        dialog.show(stage);
    }

    private void checkIfNull(String buttonText, Runnable buttonAction, Dialog dialog) {
        if (buttonText != null) {
            TextButton button = new TextButton(buttonText, skin);
            button.pad(8);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (buttonAction != null) {
                        buttonAction.run();
                        System.out.println("Button clicked. Running: " + buttonAction);
                    }
                    dialog.hide();
                }
            });
            dialog.button(button);
        }
    }

    public void showError(String message) {
        showDialog("Error", message, "OK", null, null, null);
    }

    public void showSuccess(String message, Runnable onClose) {
        showDialog("Success", message, "OK", onClose, null, null);
    }

}
