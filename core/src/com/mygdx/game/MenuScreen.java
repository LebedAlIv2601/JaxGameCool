package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends BaseScreen{

    @Override
    public void initialize() {

//        BaseActor title = new BaseActor(0,0, mainStage);
//        title.setAnim();

        TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);

        startButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if ((!(e instanceof InputEvent) || !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))){
                    return false;
                }
                JaxGame.setActiveScreen(new Level1Screen());
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


    }

    @Override
    public void update(float dt) {

    }
}
