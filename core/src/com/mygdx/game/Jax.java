package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class Jax extends BaseActor{

    private boolean at;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> hit;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> stand;



    public Jax(float x, float y, Stage s){
        super(x,y,s);
        walk = loadAnimationFromFiles(MainGameValues.texturesForWalk, 0.1f,true);
        hit = loadAnimationFromFiles(MainGameValues.texturesForHit, 0.1f,true);
//        jump = loadAnimationFromFiles(MainGameValues.texturesForWalk, 0.1f,true);
        stand = loadAnimationFromFiles(MainGameValues.staying, 0.1f,true);

        at = false;
        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
        setBoundaryPolygon(8);
    }

    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);

        if(isHitting()){
            setAnimation(hit);
        } else if(isMoving()){
            setAnimation(walk);
        } else{
            setAnimation(stand);
        }

    }

    public void setHitting(boolean t){
        at = t;
    }
    public boolean isHitting(){
        return at;
    }

}
