package com.mygdx.game.Actors;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainGameValues;

public class FlyEnemy extends BaseActor {
    private Animation stand;
    private Animation walk;
    private Animation hit;
    private float flyX;
    private float flyY;

    public FlyEnemy(float x, float y, Stage s) {
        super(x, y, s);
        maxHorizontalSpeed = 400;
        walkAcceleration = 500;
        walkDeceleration = 2000;
        gravity = 0;
        maxVerticalSpeed = 400;
        setHealth(75);
        setDamage(0.8f);
        stand = loadAnimationFromFiles(MainGameValues.flyStand, 0.1f, true);
        hit = loadAnimationFromFiles(MainGameValues.flyHit, 0.1f, true);
        walk = loadAnimationFromFiles(MainGameValues.flyWalk, 0.1f, true);
        setBoundaryPolygon(8);
        setFlyX(-1);
        setFlyY(-1);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        physicsApply(dt);

        if(isHitting()){
            setAnimation(hit, flip);
        } else if(velocityVec.x == 0){
            setAnimation(stand, flip);
        } else{
            setAnimation(walk,flip);
        }
    }

    public void setFlyX(float t){
        flyX = t;
    }
    public void setFlyY(float t){
        flyY = t;
    }
    public float getFlyX(){
        return flyX;
    }
    public float getFlyY(){
        return flyY;
    }
}
