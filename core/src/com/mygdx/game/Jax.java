package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Jax extends BaseActor{
    public Jax(float x, float y, Stage s){
        super(x,y,s);

        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
        setBoundaryPolygon(8);
    }
}
