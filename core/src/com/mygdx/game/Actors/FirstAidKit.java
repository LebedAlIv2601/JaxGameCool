package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class FirstAidKit extends BaseActor{
    public FirstAidKit(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("firstAidKit.png");
        Action spin = Actions.sequence(Actions.moveBy(0, 10, 1), Actions.moveBy(0, -10, 1));
        this.addAction(Actions.forever(spin));
        setBoundaryPolygon(8);
    }
}
