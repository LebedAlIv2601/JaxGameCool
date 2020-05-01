package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.Actors.Solid;
import com.mygdx.game.MainGameValues;

public class Jax extends BaseActor{

    private boolean at;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> hit;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> stand;
    private float jumpSpeed;
    private BaseActor belowSensor;
    private boolean stairsOverlap;
    private Sound deathSound;
    private float stamina;




    public Jax(float x, float y, Stage s){
        super(x,y,s);
        walk = loadAnimationFromFiles(MainGameValues.texturesForWalk, 0.05f,true);
        hit = loadAnimationFromFiles(MainGameValues.texturesForHit, 0.1f,true);
        jump = loadAnimationFromFiles(MainGameValues.texturesForJump, 0.1f,true);
        stand = loadAnimationFromFiles(MainGameValues.staying, 0.1f,true);

        at = false;
        flip = false;
        maxHorizontalSpeed = 250;
        walkAcceleration = 400;
        walkDeceleration = 400;
        gravity = 700;
        maxVerticalSpeed = 1000;
        jumpSpeed = 450;
        climbSpeed = 200;
        stairsOverlap = false;
        setHealth(MainGameValues.jaxHealth);
        setStamina(MainGameValues.jaxStamina);
        setDamage(5f);

        setBoundaryPolygon(8);
        belowSensor = new BaseActor(0,0,s);
        belowSensor.loadTexture("button.png");
        belowSensor.setSize(this.getWidth()-10,8);
        belowSensor.setBoundaryRectangle();
        belowSensor.setVisible(false);
        deathSound = Gdx.audio.newSound(Gdx.files.internal("deathSound.mp3"));


    }

    public void act(float dt) {

        super.act(dt);

        physicsApply(dt);
        health = MathUtils.clamp(health, 0, MainGameValues.jaxHealth);
        stamina = MathUtils.clamp(stamina, 0, MainGameValues.jaxStamina);


        belowSensor.setPosition(getX()+5,getY()-8);
        if(isHitting()){
            setAnimation(hit, flip);
        } else if(this.isOnSolid()){
            belowSensor.setColor(Color.GREEN);
            if(velocityVec.x == 0){
                setAnimation(stand, flip);
            } else{
                setAnimation(walk,flip);
            }
        } else if(!stairsOverlap){
            belowSensor.setColor(Color.RED);
            setAnimation(jump, flip);
        } else{
            setAnimation(stand, flip);
        }
        if(!isDead()) {
            alignCamera();
        }

    }

    public void death() {
        if(!soundPlayedFlag) {
            deathSound.play();
            soundPlayedFlag = true;
        }
        dead = true;
        setPosition(-1000,-1000);
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

    public void setGravity(float g){
        gravity = g;
    }

    public void setStairsOverlap(boolean r){
        stairsOverlap = r;
    }
    public boolean getStairsOverlap(){
        return stairsOverlap;
    }
    public void climb(){
        velocityVec.y = climbSpeed;
    }
    public void setStamina(float s){
        stamina += s;
    }
    public float getStamina(){
        return stamina;
    }
}
