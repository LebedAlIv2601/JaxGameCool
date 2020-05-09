package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BaseGame;
import com.mygdx.game.Screens.BaseLevelScreen;
import com.mygdx.game.Screens.BaseLevelScreen;
import com.mygdx.game.Screens.LoadingLevelsScreen;

import java.util.ArrayList;

public class BaseActor extends Group {
    protected Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;
    public Vector2 velocityVec;
    protected Vector2 accelerationVec;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;
    private Polygon boundaryPolygon;
    private static Rectangle worldBounds;
    protected boolean flip;
    protected Texture texture;
    protected float walkAcceleration;
    protected float walkDeceleration;
    protected float maxHorizontalSpeed;
    protected float maxVerticalSpeed;
    protected float gravity;
    protected float deltaTime;
    protected float jumpSpeed;
    protected float health;
    protected float damage;
    protected float climbSpeed;
    protected boolean soundPlayedFlag;
    protected Sound bruh;
    private boolean out;
    private boolean at;
    protected boolean dead;

    public BaseActor(float x, float y, Stage s){
        super();

        setPosition(x, y);
        s.addActor(this);

        animation = null;
        elapsedTime = 0;
        animationPaused = false;
        velocityVec = new Vector2(0,0);
        accelerationVec = new Vector2(0, 0);
        acceleration= 0;
        maxSpeed = 1000;
        deceleration = 0;
        out = false;
        soundPlayedFlag = false;
        dead = false;
        bruh = Gdx.audio.newSound(Gdx.files.internal("bruh1.mp3"));
    }
    public void setAnimationPaused(boolean pause){
        animationPaused = pause;
    }

    public void act(float dt){
        super.act(dt);
        deltaTime+=dt;
        if(!animationPaused){
            elapsedTime += dt;
        }
    }

    public void draw(Batch batch, float parentAlpha){


        Color c = getColor();
        batch.setColor(c.r,c.g,c.b,c.a);
        if(animation != null && isVisible()){
            batch.draw(animation.getKeyFrame(elapsedTime), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), (flip ? -1 : 1)*getScaleX(), getScaleY(), getRotation());
        }
        super.draw(batch, parentAlpha);
    }

    public void setAnimation(Animation<TextureRegion> anim, boolean fl){
        animation = anim;
        flip = fl;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w/2, h/2);
        if(boundaryPolygon == null){
            setBoundaryRectangle();
        }
}

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop){
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(int i = 0; i< fileCount; i++){
            String fileName = fileNames[i];
            Texture texture = LoadingLevelsScreen.assetManagerLvl.get(fileName,Texture.class);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }
        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);
        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if(animation == null){
            setAnimation(anim, flip);
        }
        return anim;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop){
        Texture texture = new Texture (Gdx.files.internal(fileName), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth()/cols;
        int frameHeight = texture.getHeight()/rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(int r = 0; r<rows; r++){
            for(int c = 0; c<cols;c++){
                textureArray.add(temp[r][c]);
            }
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if(animation == null){
            setAnimation(anim, flip);
        }
        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName){
        String [] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public boolean isAnimationFinished(){
        return animation.isAnimationFinished(elapsedTime);
    }

    public void setSpeed(float speed){
        if (velocityVec.len() == 0){
            velocityVec.set(speed,0);
        } else {
            velocityVec.setLength(speed);
        }
    }

    public float getSpeed(){
        return velocityVec.len();
    }

    public void setMotionAngle(float angle){
        velocityVec.setAngle(angle);
    }

    public float getMotionAngle(){
        return velocityVec.angle();
    }

    public boolean isMoving(){
        return(getSpeed()>0);
    }

    public void setAcceleration(float acc){
        acceleration = acc;
    }

    public void accelerateAtAngle(float angle){
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }

    public void accelerateForward(){
        accelerateAtAngle(getRotation());
    }

    public void setMaxSpeed(float ms){
        maxSpeed = ms;
    }

    public void setDeceleration(float dec){
        deceleration = dec;
    }

    public void applyPhysics(float dt){
        velocityVec.add(accelerationVec.x*dt, accelerationVec.y*dt);
        float speed = getSpeed();
        if(accelerationVec.len() == 0){
            speed-=deceleration*dt;
        }
        speed = MathUtils.clamp(speed, 0, maxSpeed);

        setSpeed(speed);

        moveBy(velocityVec.x * dt, velocityVec.y*dt);

        accelerationVec.set(0,0);
    }

    public void setBoundaryRectangle(){
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0, w,0, w,h, 0,h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides){
        float w = getWidth();
        float h = getHeight();

        float[] vertices = new float[2*numSides];
        for (int i = 0; i<numSides;i++){
            float angle = i*6.28f/numSides;
            vertices[2*i] = w/2*MathUtils.cos(angle) + w/2;
            vertices[2*i+1] = h/2*MathUtils.sin(angle) + h/2;
        }
        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon(){
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    public boolean overlaps(BaseActor other){
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();
        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return false;
        }
        return Intersector.overlapConvexPolygons(poly1, poly2);
    }
    public void centerAtPosition(float x, float y){
        setPosition(x-getWidth()/2, y - getHeight()/2);
    }
    public void centerAtActor(BaseActor other){
        centerAtPosition(other.getX() + other.getWidth()/2, other.getY() + other.getHeight()/2);
    }
    public void setOpacity(float opacity){
        this.getColor().a = opacity;
    }

    public Vector2 preventOverlap(BaseActor other){
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return null;
        }

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);
        if(!polygonOverlap){
            return null;
        }

        this.moveBy(mtv.normal.x*mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
    }

    public static ArrayList<BaseActor> getList(Stage stage, String className){
        ArrayList<BaseActor> list = new ArrayList<BaseActor>();

        Class theClass = null;
        try{
            theClass = Class.forName("com.mygdx.game.Actors."+className);
        }
        catch(Exception error){
            error.printStackTrace();
        }
        for(Actor a : stage.getActors()){
            if(theClass.isInstance(a)){
                list.add((BaseActor)a);
            }
        }
        return list;
    }

    public static int count(Stage stage, String className){
        return getList(stage, className).size();
    }

    public static void setWorldBounds(float width, float height){
        worldBounds = new Rectangle(0,0,width,height);
    }
    public static void setWorldBounds(BaseActor ba){
        setWorldBounds(ba.getWidth(), ba.getHeight());
    }

    public void boundToWorld(){
        if(getX()<0){
            setX(0);
        }
        if(getX() + getWidth()>worldBounds.width){
            setX(worldBounds.width-getWidth());
        }
        if(getY()<0){
            setY(0);
        }
        if(getY() + getHeight()>worldBounds.height){
            setY(worldBounds.height-getHeight());
        }
    }

//  Создание и привязка камеры к вьюпорту

    public void alignCamera(){
        Camera cam =  this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();

        cam.position.set(this.getX() + this.getOriginX(), this.getY()+this.getOriginY(), 0);

        cam.position.x = MathUtils.clamp(cam.position.x, cam.viewportWidth/2, worldBounds.width-cam.viewportWidth/2);
        cam.position.y = MathUtils.clamp(cam.position.y, cam.viewportHeight/2, worldBounds.height-cam.viewportHeight/2);
        cam.update();
    }

    public void wrapAroundWorld(){
        if(getX() + getWidth()<0){
            setX(worldBounds.width);
        }
        if(getX() >worldBounds.width){
            setX(-getWidth());
        }
        if(getY()+getHeight()<0){
            setY(worldBounds.height);
        }
        if(getY()>worldBounds.height){
            setY(-getHeight());
        }
    }

    public boolean isWithinDistance(float distance, BaseActor other){
        Polygon poly1 = this.getBoundaryPolygon();
        float scaleX = (this.getWidth() + 2* distance)/this.getWidth();
        float scaleY = (this.getHeight() + 2 * distance) / this.getHeight();
        poly1. setScale(scaleX, scaleY);

        Polygon poly2 = other.getBoundaryPolygon();

        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return false;
        }

        return Intersector.overlapConvexPolygons(poly1, poly2);
    }

//  реализцаия физики для актеров
    public void physicsApply(float dt){
        accelerationVec.add(0, -gravity);
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        velocityVec.x = MathUtils.clamp(velocityVec.x, -maxHorizontalSpeed, maxHorizontalSpeed);
        velocityVec.y = MathUtils.clamp(velocityVec.y, -maxVerticalSpeed, maxVerticalSpeed);
        moveBy(velocityVec.x*dt, velocityVec.y*dt);
        accelerationVec.set(0,0);
        if(velocityVec.x>0){
            flip = false;
        } else if (velocityVec.x<0){
            flip = true;
        }
    }

    public float getHealth(){
        return health;
    }
    public void setHealth(float d){
        health += d;
    }

    public void addVelocityVec(float det){
        accelerationVec.add(det*walkAcceleration, 0);
    }
    public void addVelocityVecY(float det){
        accelerationVec.add(0, det*walkAcceleration);
    }
    public void decelerateActor(){
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

    public float getDamage(){
        return damage;
    }
    public void setDamage(float damag){
        damage = -damag;
    }

    public void death(){
        if(!soundPlayedFlag) {
            bruh.play(BaseGame.prefs.getFloat("SoundVolume"));
            soundPlayedFlag = true;
        }
        dead = true;
        remove();
    }

    public boolean isDead(){
        return dead;
    }

    public boolean isOut(){
        return out;
    }

    public void setOut(){
        if(getY() + getHeight()<0){
            out = true;
        }
    }

    public void setHitting(boolean t){
        at = t;
    }
    public boolean isHitting(){
        return at;
    }

    public void setVelocityVecX(float v){
        velocityVec.x = v;
    }
    public void setVelocityVecY(float v){
        velocityVec.y = v;
    }


}
