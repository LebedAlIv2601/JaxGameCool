package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
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
        TilemapActor tma = new TilemapActor("Level1Map/Level1Map.tmx", mainStage);

        for(MapObject obj : tma.getRectangleList("Solid")){
            MapProperties props = obj.getProperties();
            new Solid((float)props.get("x"), (float)props.get("y"), (float)props.get("width"), (float)props.get("height"), mainStage);
        }

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        jax = new Jax((float)startProps.get("x"), (float)startProps.get("y"), mainStage);

        TextButton jumpButton = new TextButton("jumpYEP", BaseGame.textButtonStyle);

        jumpButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                if(jax.isOnSolid()){
                    jax.jump();
                }
                return false;
            }
        });

        leftButton = new TextButton("<=", BaseGame.textButtonStyle);
        rightButton = new TextButton("=>", BaseGame.textButtonStyle);
        attackButton = new TextButton("=[==>", BaseGame.textButtonStyle);


        uiTable.pad(10);
        uiTable.add(leftButton).bottom();
        uiTable.add(rightButton).bottom();
        uiTable.add().expandX().expandY();
        uiTable.add(attackButton).bottom();
        uiTable.add(jumpButton).bottom();

    }

    @Override
    public void update(float dt) {

        if(leftButton.isPressed()){
            jax.addVelocityVec(-1);
        } else if(rightButton.isPressed()){
            jax.addVelocityVec(1);
        }else {
            jax.decelerateJax();
        }
        if(attackButton.isPressed()){
            jax.setHitting(true);
        } else {
            jax.setHitting(false);
        }

        for (BaseActor actor : BaseActor.getList(mainStage, "Solid")){
            Solid solid = (Solid)actor;
            if(jax.overlaps(solid) && solid.isEnabled()){
                Vector2 offset = jax.preventOverlap(solid);
                if(offset !=null){
                    if(Math.abs(offset.x)>Math.abs(offset.y)){
                        jax.velocityVec.x = 0;
                    } else{
                        jax.velocityVec.y = 0;
                    }
                }
            }
        }
    }
}
