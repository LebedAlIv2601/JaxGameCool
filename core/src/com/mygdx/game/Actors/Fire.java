package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.MainGameValues;


public class Fire extends BaseActor {
    public Fire(float x, float y, Stage s) {
        super(x, y, s);

        loadAnimationFromFiles(MainGameValues.fire,.1f,true);
        setDamage(1.1f);
    }

}
