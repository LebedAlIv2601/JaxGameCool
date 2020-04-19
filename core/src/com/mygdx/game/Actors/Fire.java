package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Actors.BaseActor;

public class Fire extends BaseActor {
    private float damage;
    public Fire(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("Level1Map/fire.png");
        damage = 1.1f;
    }
    public float getDamage(){
        return damage;
    }
}
