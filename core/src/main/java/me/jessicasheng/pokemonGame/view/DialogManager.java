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
        contentTable.add(messageLabel).width(200).pad(20);

        // Button layout
        if (button1Text != null) {
            TextButton button1 = new TextButton(button1Text, skin);
            button1.pad(8);
            button1.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (button1Action != null) {
                        button1Action.run();

                    }
                    dialog.hide();
                }
            });
            dialog.button(button1);
        }

        if (button2Text != null) {
            TextButton button2 = new TextButton(button2Text, skin);
            button2.pad(8);
            button2.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    button2Action.run();
                    dialog.hide();
                }
            });
            dialog.button(button2);
        }

        dialog.show(stage);
    }

    public void showError(String message) {
        showDialog("Error", message, "OK", null, null, null);
    }

    public void showSuccess(String message, Runnable onClose) {
        showDialog("Success", message, "OK", onClose, null, null);
    }

}
