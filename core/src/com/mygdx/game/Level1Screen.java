package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class Level1Screen extends BaseScreen {
    private Jax jax;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton attackButton;
    private ProgressBar healthBar;
    private Label loseLabel;
    private ProgressBar.ProgressBarStyle pbs;
    private Pixmap pixmap;
    private TextureRegionDrawable drawable;
    private Sound bruh;
    private Music ost;
    private boolean flag;
    private float audioVolume;
    @Override
    public void initialize() {
        TilemapActor tma = new TilemapActor("Level1Map/Level1Map.tmx", mainStage);

        for(MapObject obj : tma.getRectangleList("Solid")){
            MapProperties props = obj.getProperties();
            new Solid((float)props.get("x"), (float)props.get("y"), (float)props.get("width"), (float)props.get("height"), mainStage);
        }



        for(MapObject obj : tma.getTileList("Fire")){
            MapProperties props = obj.getProperties();
            new Fire((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        for(MapObject obj : tma.getRectangleList("StickEnemy")){
            MapProperties props = obj.getProperties();
            new StickEnemy((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        jax = new Jax((float)startProps.get("x"), (float)startProps.get("y"), mainStage);


        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle pbs =  new ProgressBar.ProgressBarStyle();
        pbs.background= drawable;

        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        pbs.knob = drawable;

        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        pbs.knobBefore = drawable;

        healthBar = new ProgressBar(0, 1, 0.01f, false, pbs);
        healthBar.setValue(1);
        healthBar.setAnimateDuration(0.25f);
//        healthBar.setBounds(10,10,500,20);
        Button.ButtonStyle buttonStyleRestart = new Button.ButtonStyle();
        Texture buttonRestart = new Texture(Gdx.files.internal("restart.png"));
        TextureRegion buttonRegionRestart = new TextureRegion(buttonRestart);
        buttonStyleRestart.up = new TextureRegionDrawable(buttonRegionRestart);

        Button restartButton = new Button(buttonStyleRestart);

        restartButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                BaseGame.setActiveScreen(new Level1Screen());
                ost.dispose();
                return false;
            }
        });

        TextButton jumpButton = new TextButton("jumpYEP", BaseGame.textButtonStyle);

        jumpButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                if(jax.isOnSolid()){
                    jax.jump();
                }
                return false;
            }
        });

        leftButton = new TextButton("<=", BaseGame.textButtonStyle);
        rightButton = new TextButton("=>", BaseGame.textButtonStyle);
        attackButton = new TextButton("=[==>", BaseGame.textButtonStyle);




        uiTable.pad(10);
        uiTable.add(healthBar).top().colspan(2);
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top().right().colspan(2);
        uiTable.row();
        uiTable.add(leftButton).bottom();
        uiTable.add(rightButton).bottom();
        uiTable.add().expandX().expandY();
        uiTable.add(attackButton).bottom();
        uiTable.add(jumpButton).bottom();

        audioVolume = 1;

        bruh = Gdx.audio.newSound(Gdx.files.internal("bruh1.mp3"));
        ost = Gdx.audio.newMusic(Gdx.files.internal("ost.mp3"));
        ost.setLooping(true);
        ost.setVolume(audioVolume/3);
        ost.play();
        flag = true;

    }

    @Override
    public void update(float dt){

        if(leftButton.isPressed()){
            jax.addVelocityVec(-1);
        } else if(rightButton.isPressed()){
            jax.addVelocityVec(1);
        }else {
            jax.decelerateActor();
        }
        if(attackButton.isPressed()){
            jax.setHitting(true);
        } else {
            jax.setHitting(false);
        }

        for(BaseActor b : BaseActor.getList(mainStage, "StickEnemy")) {
            StickEnemy st =(StickEnemy) b;
            allLogic(st);
        }
        allLogic(jax);
        jaxHit();
        findJax();
        healthBar.setValue(jax.getHealth()/MainGameValues.jaxHealth);

    }

    public void onGround(BaseActor a){
        for (BaseActor actor : BaseActor.getList(mainStage, "Solid")){
            Solid solid = (Solid)actor;
            if(a.overlaps(solid) && solid.isEnabled()){
                Vector2 offset = a.preventOverlap(solid);
                if(offset !=null){
                    if(Math.abs(offset.x)>Math.abs(offset.y)){
                        a.velocityVec.x = 0;
                    } else{
                        a.velocityVec.y = 0;
                    }
                }
            }
        }
    }

    public void contactWithFire(BaseActor a){
        for(BaseActor fireActor : BaseActor.getList(mainStage, "Fire")){
            if(a.overlaps(fireActor)){
                Fire fire = (Fire)fireActor;
                a.setHealth(fire.getDamage());

            }
        }
    }

    public void jaxHit(){
        for(BaseActor b : BaseActor.getList(mainStage, "StickEnemy")){
            if(jax.overlaps(b) && jax.isHitting()){
                StickEnemy st = (StickEnemy) b;
                st.setHealth(jax.getDamage());

            }
        }
    }

    public void findJax(){
        for(BaseActor b : BaseActor.getList(mainStage, "StickEnemy")){
            if(jax.getY()==b.getY() && Math.abs(jax.getX()-b.getX())<=25){
                StickEnemy st = (StickEnemy) b;
                st.setSpeed(0);
                st.setHitting(true);
                jax.setHealth(st.getDamage());
            } else if(jax.getY()<=b.getY() && Math.abs(jax.getX()-b.getX())<=500 && Math.abs(jax.getX()-b.getX())>=25){
                StickEnemy st = (StickEnemy) b;
                st.setHitting(false);
                st.addVelocityVec((jax.getX()-b.getX())/(Math.abs(jax.getX()-b.getX())));
            }
        }
    }

    public void ifDead(BaseActor a){
        if(a.getHealth()<=0 || a.isOut()){
            if(a == jax){
                BaseActor loseMessage = new BaseActor(0, 0, uiStage);
                loseMessage.loadTexture("loseMessage.png");
                loseMessage.centerAtPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
                loseMessage.setOpacity(0);
                loseMessage.addAction(Actions.delay(1));
                loseMessage.addAction(Actions.after(Actions.fadeIn(1)));
            }
            a.death();
        }
    }
    public void allLogic(BaseActor a){
        a.setOut();
        onGround(a);
        contactWithFire(a);
        ifDead(a);
    }
}
