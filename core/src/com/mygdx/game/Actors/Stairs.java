package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Stairs extends BaseActor {
    public Stairs(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("Stairs.png");
        setBoundaryRectangle();
    }
}
