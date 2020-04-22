package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainGameValues;

public class Sign extends BaseActor{
    private String text;
    private boolean viewing;
    public Sign(float x, float y, Stage s){
        super(x,y,s);
        loadAnimationFromFiles(MainGameValues.signAnimation, 0.1f, true);
        text = " ";
        viewing = false;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public void setViewing(boolean v){
        viewing = v;
    }

    public boolean isViewing(){
        return viewing;
    }
}
