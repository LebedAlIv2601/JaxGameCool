package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseGame;
import com.mygdx.game.JaxGame;

public class MenuScreen extends BaseScreen implements Screen {

    @Override
    public void initialize() {

        TextButton startButton = new TextButton("Start", JaxGame.textButtonStyle);

        startButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if ((!(e instanceof InputEvent) || !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))){
                    return false;
                }
                JaxGame.setActiveScreen(new Level2Screen());
                return false;
            }
        });

        TextButton levelsButton = new TextButton("Levels", BaseGame.textButtonStyle);

        levelsButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if ((!(e instanceof InputEvent) || !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))){
                    return false;
                }
                Gdx.app.exit();
                return false;
            }
        });

        TextButton settingsButton = new TextButton("Settings", BaseGame.textButtonStyle);

        settingsButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if ((!(e instanceof InputEvent) || !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))){
                    return false;
                }
                Gdx.app.exit();
                return false;
            }
        });

        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);

        quitButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if ((!(e instanceof InputEvent) || !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))){
                    return false;
                }
                Gdx.app.exit();
                return false;
            }
        });

        uiTable.pad(10);
        uiTable.add(startButton).colspan(1);
        uiTable.row();
        uiTable.pad(10);
        uiTable.add(levelsButton);
        uiTable.row();
        uiTable.pad(10);
        uiTable.add(settingsButton);
        uiTable.row();
        uiTable.pad(10);
        uiTable.add(quitButton);

    }

    @Override
    public void update(float dt) {

    }
}
