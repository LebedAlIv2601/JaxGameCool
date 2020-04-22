package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Teleport extends BaseActor{
    public Teleport(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("teleport.png", 4, 4, 0.1f, true);

    }
}
