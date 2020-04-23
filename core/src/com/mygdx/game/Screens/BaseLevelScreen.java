package com.mygdx.game.Screens;

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
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.Actors.Crystal;
import com.mygdx.game.Actors.DialogBox;
import com.mygdx.game.Actors.Fire;
import com.mygdx.game.Actors.FirstAidKit;
import com.mygdx.game.Actors.Grass;
import com.mygdx.game.Actors.Jax;
import com.mygdx.game.Actors.Sign;
import com.mygdx.game.Actors.Solid;
import com.mygdx.game.Actors.Teleport;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.BaseGame;
import com.mygdx.game.MainGameValues;
import com.mygdx.game.Actors.StickEnemy;


public class BaseLevelScreen extends BaseScreen {
    private Jax jax;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton attackButton;
    private TextButton menuButton;
    private ProgressBar healthBar;
    private Label loseLabel;
    private ProgressBar.ProgressBarStyle pbs;
    private Pixmap pixmap;
    private TextureRegionDrawable drawable;
    private Sound bruh;
    private Music ost;
    private boolean flag;
    private float audioVolume;
    private TilemapActor tma;
    private boolean goal;
    private boolean allCollected;
    private boolean checkCrystalsFlag;
    private DialogBox dialogBox;

    public BaseLevelScreen(int n, int g) {
        super(n, g);
    }

    @Override
    public void initialize() {
        tma = new TilemapActor("Level1Map/"+MainGameValues.maps[levelNumber], mainStage);

        for(MapObject obj : tma.getRectangleList("Solid")){
            MapProperties props = obj.getProperties();
            new Solid((float)props.get("x"), (float)props.get("y"), (float)props.get("width"), (float)props.get("height"), mainStage);
        }
        for(MapObject obj : tma.getTileList("Grass")){
            MapProperties props = obj.getProperties();
            new Grass((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        for(MapObject obj : tma.getRectangleList("Teleport")){
            MapProperties props = obj.getProperties();
            new Teleport((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        for(MapObject obj : tma.getTileList("Fire")){
            MapProperties props = obj.getProperties();
            new Fire((float)props.get("x"), (float)props.get("y"), mainStage);
        }
        for(MapObject obj : tma.getTileList("Crystal")){
            MapProperties props = obj.getProperties();
            new Crystal((float)props.get("x"), (float)props.get("y"), mainStage);
        }
        for(MapObject obj : tma.getTileList("FirstAidKit")){
            MapProperties props = obj.getProperties();
            new FirstAidKit((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        for(MapObject obj : tma.getTileList("Sign")){
            MapProperties props = obj.getProperties();
            Sign s = new Sign((float)props.get("x"), (float)props.get("y"), mainStage);
            s.setText((String)props.get("message"));
        }


        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        jax = new Jax((float)startProps.get("x"), (float)startProps.get("y"), mainStage);

        for(MapObject obj : tma.getTileList("StickEnemy")){
            MapProperties props = obj.getProperties();
            new StickEnemy((float)props.get("x"), (float)props.get("y"), mainStage);
        }


        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle pbs = new ProgressBar.ProgressBarStyle();
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
// healthBar.setBounds(10,10,500,20);
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
                BaseGame.setActiveScreen(new BaseLevelScreen(levelNumber, goalNumber));
                ost.dispose();
                return false;
            }
        });

        TextButton jumpButton = new TextButton("jump", BaseGame.textButtonStyle);

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

        menuButton = new TextButton("Menu", BaseGame.textButtonStyle);

        menuButton.addListener(new EventListener() {
            @Override

            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                BaseGame.setActiveScreen(new MenuScreen());
                ost.dispose();
                return false;
            }
        });

        leftButton = new TextButton("<=", BaseGame.textButtonStyle);
        rightButton = new TextButton("=>", BaseGame.textButtonStyle);
        attackButton = new TextButton("=[==>", BaseGame.textButtonStyle);

        BaseActor goalMessage = new BaseActor(0, 0, uiStage);
        switch (goalNumber){
            case 0:
                goalMessage.loadTexture("goalGetToTheEnd.png");
                break;
            case 1:
                goalMessage.loadTexture("goalCrystals.png");
                break;
        }
        goalMessage.centerAtPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        goalMessage.setOpacity(0);
        goalMessage.addAction(Actions.sequence(Actions.fadeIn(2), Actions.fadeOut(1)));


        dialogBox = new DialogBox(Gdx.graphics.getWidth()/2 - 225, Gdx.graphics.getHeight() - 250,uiStage);
        dialogBox.setDialogSize(450, 200);
        dialogBox.setFontScale(0.8f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);


        uiTable.pad(10);
        uiTable.add(healthBar).top().colspan(2);
        uiTable.add().expandX().expandY();
        uiTable.add(menuButton).top().right();
        uiTable.add(restartButton).top().right();
        uiTable.row();
        uiTable.add(leftButton).bottom();
        uiTable.add(rightButton).bottom();
        uiTable.add().expandX().expandY();
        uiTable.add(attackButton).bottom();
        uiTable.add(jumpButton).bottom();

        switch(goalNumber){
            case 0:
                checkCrystalsFlag = true;
                goal = true;
                break;
            case 1:
                checkCrystalsFlag = false;
                goal = false;
                break;
        }

        audioVolume = 1;

        bruh = Gdx.audio.newSound(Gdx.files.internal("bruh1.mp3"));
        ost = Gdx.audio.newMusic(Gdx.files.internal("ost.mp3"));
        ost.setLooping(true);
        ost.setVolume(audioVolume/3);
        ost.play();
        flag = true;
        allCollected = false;
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
        isOverlapTeleport();
        checkCrystalsCollected();
        crystalCollect();
        collectFirstAidKit();
        overlapSign();
        healthBar.setValue(jax.getHealth()/ MainGameValues.jaxHealth);

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


    public void crystalCollect(){
        for(BaseActor b : BaseActor.getList(mainStage, "Crystal")){
            if(jax.overlaps(b)){
                Crystal cr = (Crystal)b;
                cr.clearActions();
                cr.addAction(Actions.removeActor());
            }
        }
    }

    public void checkCrystalsCollected(){
        if(BaseActor.count(mainStage, "Crystal")==0 && !checkCrystalsFlag){
            BaseActor collectMessage = new BaseActor(0, 0, uiStage);
            collectMessage.loadTexture("collectCrystals.png");
            collectMessage.centerAtPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
            collectMessage.setOpacity(0);
            collectMessage.addAction(Actions.sequence(Actions.fadeIn(1), Actions.fadeOut(1)));
            checkCrystalsFlag = true;
        }
    }

    public void collectFirstAidKit(){
        for(BaseActor b : BaseActor.getList(mainStage, "FirstAidKit")){
            if(jax.overlaps(b)){
                FirstAidKit fai = (FirstAidKit) b;
                fai.clearActions();
                fai.addAction(Actions.removeActor());
                jax.setHealth(50);
            }
        }
    }

    public void isOverlapTeleport(){
        for(BaseActor b : BaseActor.getList(mainStage, "Teleport")){
            if(jax.overlaps(b) && goal){
                if(levelNumber+1 == MainGameValues.maps.length){
                    BaseGame.setActiveScreen(new MenuScreen());
                } else {
                    int gn = MainGameValues.getGoal(levelNumber);
                    BaseGame.setActiveScreen(new BaseLevelScreen(levelNumber + 1, gn));
                }
                ost.dispose();
            }
        }

    }

    public void overlapSign(){
        for(BaseActor signActor : BaseActor.getList(mainStage, "Sign")){
            Sign sign = (Sign)signActor;

            boolean nearby = jax.isWithinDistance(4, sign);
            if(nearby && !sign.isViewing()){
                dialogBox.setText(sign.getText());
                dialogBox.setVisible(true);
                sign.setViewing(true);
            }

            if(sign.isViewing() && !nearby){
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
            }
        }
    }
}
