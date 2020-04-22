package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.MainGameValues;

public class StickEnemy extends BaseActor {
    private Animation stand;
    private Animation walk;
    private Animation hit;
    public StickEnemy(float x, float y, Stage s) {
        super(x, y, s);
        maxHorizontalSpeed = 200;
        walkAcceleration = 300;
        walkDeceleration = 2000;
        gravity = 700;
        maxVerticalSpeed = 1000;
        setHealth(100);
        setDamage(0.6f);
        stand = loadAnimationFromFiles(MainGameValues.enemyStand, 0.1f, true);
        hit = loadAnimationFromFiles(MainGameValues.enemyHit, 0.1f, true);
        walk = loadAnimationFromFiles(MainGameValues.enemyWalk, 0.1f, true);
        setBoundaryPolygon(8);
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
}
