package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Level1Screen extends BaseScreen {
    private Jax jax;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton attackButton;
    @Override
    public void initialize() {
        jax = new Jax(20,20,mainStage);


        leftButton = new TextButton("<=", BaseGame.textButtonStyle);
        rightButton = new TextButton("=>", BaseGame.textButtonStyle);
        attackButton = new TextButton("=[==>", BaseGame.textButtonStyle);

//        attackButton.addListener(
//                new EventListener() {
//                    @Override
//                    public boolean handle(Event e) {
//                        if (!Level1Screen.this.isTouchDownEvent(e)) {
//                            return false;
//                        }
//                        jax.setHitting(true);
//                        return true;
//                    }
//                }
//        );


        uiTable.pad(10);
        uiTable.add(leftButton).bottom();
        uiTable.add(rightButton).bottom();
        uiTable.add().expandX().expandY();
        uiTable.add(attackButton).bottom();

    }

    @Override
    public void update(float dt) {

        if(leftButton.isPressed()){
            jax.accelerateAtAngle(180);
        }
        if(rightButton.isPressed()){
            jax.accelerateAtAngle(0);
        }
        if(attackButton.isPressed()){
            jax.setHitting(true);
        } else {
            jax.setHitting(false);
        }
    }
}
