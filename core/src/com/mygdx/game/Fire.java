package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.PageAttributes;

public class Fire extends BaseActor {
    private float damage;
    public Fire(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("Level1Map/fire.png");
        damage = 0.1f;
    }
    public float getDamage(){
        return damage;
    }
}
