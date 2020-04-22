package com.mygdx.game.Screens;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.Actors.Crystal;
import com.mygdx.game.Actors.Fire;
import com.mygdx.game.Actors.FirstAidKit;
import com.mygdx.game.Actors.Grass;
import com.mygdx.game.Actors.Jax;
import com.mygdx.game.Actors.Sign;
import com.mygdx.game.Actors.Solid;
import com.mygdx.game.Actors.StickEnemy;
import com.mygdx.game.Actors.Teleport;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.BaseGame;

public class Level2Screen extends BaseLevelScreen{
    public void initialize() {
        super.initialize();
        TilemapActor tma = new TilemapActor("Level1Map/Level2Map.tmx", mainStage);

        for(MapObject obj : tma.getRectangleList("Solid")){
            MapProperties props = obj.getProperties();
            new Solid((float)props.get("x"), (float)props.get("y"), (float)props.get("width"), (float)props.get("height"), mainStage);
        }
        for(MapObject obj : tma.getTileList("Grass")){
            MapProperties props = obj.getProperties();
            new Grass((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        for(MapObject obj : tma.getRectangleList("Teleport")){
            MapProperties props = obj.getProperties();
            new Teleport((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        for(MapObject obj : tma.getTileList("Fire")){
            MapProperties props = obj.getProperties();
            new Fire((float)props.get("x"), (float)props.get("y"), mainStage);
        }
        for(MapObject obj : tma.getTileList("Crystal")){
            MapProperties props = obj.getProperties();
            new Crystal((float)props.get("x"), (float)props.get("y"), mainStage);
        }
        for(MapObject obj : tma.getTileList("FirstAidKit")){
            MapProperties props = obj.getProperties();
            new FirstAidKit((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        for(MapObject obj : tma.getTileList("Sign")){
            MapProperties props = obj.getProperties();
            Sign s = new Sign((float)props.get("x"), (float)props.get("y"), mainStage);
            s.setText((String)props.get("message"));
        }


        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        jax = new Jax((float)startProps.get("x"), (float)startProps.get("y"), mainStage);

        for(MapObject obj : tma.getTileList("StickEnemy")){
            MapProperties props = obj.getProperties();
            new StickEnemy((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        restartButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                BaseGame.setActiveScreen(new Level2Screen());
                ost.dispose();
                return false;
            }
        });
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        for(BaseActor b : BaseActor.getList(mainStage, "Teleport")){
            if(jax.overlaps(b) && isCrystalsCollected()){
                BaseGame.setActiveScreen(new Level1Screen());
                ost.dispose();
            }
        }
        checkCrystalsCollected();
    }
}
