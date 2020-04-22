package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainGameValues;

public class Grass extends BaseActor{
    public Grass(float x, float y, Stage s) {
        super(x, y, s);

        loadAnimationFromFiles(MainGameValues.grassAnimation, 0.1f, true);
    }
}
