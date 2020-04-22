package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Crystal extends BaseActor{
    private boolean collected;
    public Crystal(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("crystal.png");

        Action spin = Actions.sequence(Actions.moveBy(0, 10, 1), Actions.moveBy(0, -10, 1));
        this.addAction(Actions.forever(spin));
        setBoundaryPolygon(8);
        collected = false;

    }
    public boolean isCollected(){
        return collected;
    }

    public void collect(){
        collected = true;
        clearActions();
        addAction(Actions.removeActor());
    }
}
