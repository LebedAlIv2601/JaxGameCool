package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class StairsRectangle extends BaseActor {
    public StairsRectangle(float x, float y,float width, float height, Stage s) {
        super(x, y, s);
        setSize(width, height);
        setBoundaryRectangle();
    }
}
