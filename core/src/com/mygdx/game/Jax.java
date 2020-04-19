package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class Jax extends BaseActor{

    private boolean at;
    private boolean flip;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> hit;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> stand;
    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float maxVerticalSpeed;
    private float gravity;
    private float deltaTime;
    private float jumpSpeed;
    private float health;
    private BaseActor belowSensor;
    private Sound bruh;
    private boolean out;



    public Jax(float x, float y, Stage s){
        super(x,y,s);
        walk = loadAnimationFromFiles(MainGameValues.texturesForWalk, 0.05f,true);
        hit = loadAnimationFromFiles(MainGameValues.texturesForHit, 0.1f,true);
        jump = loadAnimationFromFiles(MainGameValues.texturesForJump, 0.1f,true);
        stand = loadAnimationFromFiles(MainGameValues.staying, 0.1f,true);

        at = false;
        flip = false;
        out = false;
        maxHorizontalSpeed = 250;
        walkAcceleration = 400;
        walkDeceleration = 400;
        gravity = 700;
        maxVerticalSpeed = 1000;
        jumpSpeed = 450;
        health = MainGameValues.jaxHealth;

        setBoundaryPolygon(8);
        belowSensor = new BaseActor(0,0,s);
        belowSensor.loadTexture("button.png");
        belowSensor.setSize(this.getWidth()-8,8);
        belowSensor.setBoundaryRectangle();
        belowSensor.setVisible(false);

        bruh = Gdx.audio.newSound(Gdx.files.internal("bruh1.mp3"));
    }

    public void act(float dt) {
        super.act(dt);
        deltaTime+=dt;

        accelerationVec.add(0,-gravity);
        velocityVec.add(accelerationVec.x*dt, accelerationVec.y*dt);

        velocityVec.x = MathUtils.clamp(velocityVec.x, -maxHorizontalSpeed, maxHorizontalSpeed);
        velocityVec.y = MathUtils.clamp(velocityVec.y, -maxVerticalSpeed, maxVerticalSpeed);
        health = MathUtils.clamp(health, 0, MainGameValues.jaxHealth);
        moveBy(velocityVec.x*dt, velocityVec.y*dt);
        accelerationVec.set(0,0);

        belowSensor.setPosition(getX()+4,getY()-8);
        if(velocityVec.x>0){
            flip = false;
        } else if (velocityVec.x<0){
            flip = true;
        }
        if(isHitting()){
            setAnimation(hit, flip);
        } else if(this.isOnSolid()){
            belowSensor.setColor(Color.GREEN);
            if(velocityVec.x == 0){
                setAnimation(stand, flip);
            } else{
                setAnimation(walk,flip);
            }
        } else{
            belowSensor.setColor(Color.RED);
            setAnimation(jump, flip);
        }
        alignCamera();

        if(getY() + getHealth()<0){
            out = true;
        }

//        boundToWorld();
//        if(health<=0){
//            death();
//            bruh.play();
//        }
    }

    public void setHitting(boolean t){
        at = t;
    }
    public boolean isHitting(){
        return at;
    }
    public void addVelocityVec(float det){
        accelerationVec.add(det*walkAcceleration, 0);
    }
    public void decelerateJax(){
        float decelerationAmount = walkDeceleration*deltaTime;

        float walkDirection;
        if(velocityVec.x>0){
            walkDirection = 1;
        } else {
            walkDirection = -1;
        }

        float walkSpeed = Math.abs(velocityVec.x);

        walkSpeed-=decelerationAmount;

        if (walkSpeed < 0){
            walkSpeed=0;
        }
        velocityVec.x = walkSpeed *walkDirection;
    }

    public boolean belowOverlaps(BaseActor actor){
        return belowSensor.overlaps(actor);
    }

    public boolean isOnSolid(){
        for (BaseActor actor : BaseActor.getList(getStage(), "Solid")) {
            Solid solid = (Solid) actor;
            if (belowOverlaps(solid) && solid.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    public void jump(){
        velocityVec.y = jumpSpeed;
    }

    public float getHealth(){
        return health;
    }
    public void setHealth(float d){
        health -= d;
    }
    public boolean isOut(){
        return out;
    }
}
