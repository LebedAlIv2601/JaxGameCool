package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mygdx.game.Actors.FlyEnemy;
import com.mygdx.game.Actors.Grass;
import com.mygdx.game.Actors.Jax;
import com.mygdx.game.Actors.Sign;
import com.mygdx.game.Actors.Solid;
import com.mygdx.game.Actors.Stairs;
import com.mygdx.game.Actors.StairsRectangle;
import com.mygdx.game.Actors.Teleport;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.BaseGame;
import com.mygdx.game.JaxGame;
import com.mygdx.game.MainGameValues;
import com.mygdx.game.Actors.StickEnemy;

public class BaseLevelScreen extends BaseScreen {
    private Jax jax;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton attackButton;
    private TextButton climbButton;
    private TextButton menuButton;
    private ProgressBar healthBar;
    private ProgressBar staminaBar;
    private Label loseLabel;
    private ProgressBar.ProgressBarStyle pbs;
    private Pixmap pixmap;
    private TextureRegionDrawable drawable;
    private Sound bruh;
    private Sound crystalCollectSound;
    private Sound firstAidKitSound;
    private Sound jumpSound;
    private Sound whatSound;
    public static Music ost;
    private boolean flag;
    private float audioVolume;
    private TilemapActor tma;
    private boolean goal;
    private boolean allCollected;
    private boolean checkCrystalsFlag;
    private boolean zeroClimbFlag;
    private DialogBox dialogBox;
    private TextButton jumpButton;
    private boolean sayWhat;
    public boolean zeroStamina;
    private Label healthLabel;
    private boolean[] loadList;
    public static boolean isEndLoad;

    public BaseLevelScreen(int n, int g, JaxGame jg) {
        super(n,g,jg);
    }

    @Override
    public void initialize() {
//      Прогрузка и размещение ОБЪЕКТОВ на карте
        isEndLoad = false;

        tma = new TilemapActor(MainGameValues.maps[levelNumber], mainStage);

        loadList = new boolean[tma.getRectangleList("Solid").size()];
        int i = 0;
        for(MapObject obj : tma.getRectangleList("Solid")){
                MapProperties props = obj.getProperties();
                new Solid((float)props.get("x"), (float)props.get("y"), (float)props.get("width"), (float)props.get("height"), mainStage);
                loadList[i] = true;
                i++;
        }

        for(MapObject obj : tma.getRectangleList("StairsRectangle")){
            MapProperties props = obj.getProperties();
            new StairsRectangle((float)props.get("x"), (float)props.get("y"), (float)props.get("width"), (float)props.get("height"), mainStage);
        }

        for(MapObject obj : tma.getTileList("Stairs")){
            MapProperties props = obj.getProperties();
            new Stairs((float)props.get("x"), (float)props.get("y"), mainStage);
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

        for(MapObject obj : tma.getTileList("FlyEnemy")){
            MapProperties props = obj.getProperties();
            new FlyEnemy((float)props.get("x"), (float)props.get("y"), mainStage);
        }

//      СОздание всего остального

        healthBar = createProgressBar(Color.RED, Color.GREEN);
        staminaBar = createProgressBar(Color.GRAY, Color.BLUE);


// healthBar.setBounds(10,10,500,20); slap
        Button.ButtonStyle buttonStyleRestart = new Button.ButtonStyle();
        Texture buttonRestart = LoadingLevelsScreen.assetManagerLvl.get("restart.png",Texture.class);
        TextureRegion buttonRegionRestart = new TextureRegion(buttonRestart);
        buttonStyleRestart.up = new TextureRegionDrawable(buttonRegionRestart);

        crystalCollectSound = LoadingLevelsScreen.assetManagerLvl.get("crystalCollectSound.mp3",Sound.class);
        firstAidKitSound = LoadingLevelsScreen.assetManagerLvl.get("firstAidKitSound.mp3",Sound.class);
        jumpSound = LoadingLevelsScreen.assetManagerLvl.get("jumpSound.mp3",Sound.class);
        whatSound = LoadingLevelsScreen.assetManagerLvl.get("whatSound.mp3",Sound.class);

        Button restartButton = new Button(buttonStyleRestart);

        restartButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                BaseGame.setActiveScreen(new LoadingLevelsScreen(levelNumber, goalNumber,jg));
                ost.dispose();
                return false;
            }
        });

        jumpButton = new TextButton("jump", BaseGame.textButtonStyle);
        jumpButton.addAction(Actions.alpha(0.4f));

        jumpButton.addListener(new EventListener() {
            @Override

            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)) {
                    return false;
                }
                if (jax.isOnSolid()) {
                    jumpSound.play(BaseGame.prefs.getFloat("SoundVolume"));
                    jax.jump();
                }
                return false;
            }
        });

        climbButton = new TextButton("climb", BaseGame.textButtonStyle);
        climbButton.addAction(Actions.alpha(0.4f));



        menuButton = new TextButton("Menu", BaseGame.textButtonStyle);

        menuButton.addListener(new EventListener() {
            @Override

            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                BaseGame.setActiveScreen(new MenuScreen(jg));
                ost.dispose();
                return false;
            }
        });

        leftButton = new TextButton("<=", BaseGame.textButtonStyle);
        leftButton.addAction(Actions.alpha(0.4f));
        leftButton.getLabel().setFontScale(6);
        rightButton = new TextButton("=>", BaseGame.textButtonStyle);
        rightButton.addAction(Actions.alpha(0.4f));
        attackButton = new TextButton("=[==>", BaseGame.textButtonStyle);
        attackButton.addAction(Actions.alpha(0.4f));

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


        dialogBox = new DialogBox(Gdx.graphics.getWidth()/2 - 225, Gdx.graphics.getHeight() - 210,uiStage);
        dialogBox.setDialogSize(450, 200);
        dialogBox.setFontScale(0.8f);
        dialogBox.setFontColor(Color.GOLD);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);


        uiTable.pad(10);
        uiTable.add(healthBar).minWidth(200).top().colspan(1);
        uiTable.add(staminaBar).minWidth(200).padLeft(10).top().colspan(1);
        uiTable.add().minWidth(200).padLeft(10).top().colspan(1);
        uiTable.add().expandX().expandY();
        uiTable.add(menuButton).minWidth(200).minHeight(200).top().right();
        uiTable.add(restartButton).minWidth(200).minHeight(200).top().padLeft(20).right();
        uiTable.row();
        uiTable.add().expandY();
        uiTable.row();
        uiTable.add().colspan(4);
        uiTable.add().expandX().expandY();
        uiTable.add(climbButton).minWidth(200).minHeight(200).bottom().right().colspan(1);
        uiTable.row();
        uiTable.add(leftButton).minWidth(200).minHeight(200).maxWidth(200).maxHeight(200).bottom().colspan(1);
        uiTable.add(rightButton).minWidth(200).minHeight(200).bottom().colspan(1);
        uiTable.add().colspan(1);
        uiTable.add().expandX().expandY();
        uiTable.add(attackButton).minWidth(200).minHeight(200).bottom().right();
        uiTable.add(jumpButton).minWidth(200).minHeight(200).bottom().right();

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

        bruh = LoadingLevelsScreen.assetManagerLvl.get("bruh1.mp3",Sound.class);
        ost = LoadingLevelsScreen.assetManagerLvl.get("ost.mp3",Music.class);
        ost.setLooping(true);
        ost.setVolume(BaseGame.prefs.getFloat("MusicVolume")/3);
        ost.play();
        flag = true;
        allCollected = false;
        zeroClimbFlag = false;
        sayWhat = false;
        zeroStamina = false;
    }

    @Override
    public void update(float dt){
        if(checkLoad()) {

            isEndLoad = true;

            ost.setVolume(BaseGame.prefs.getFloat("MusicVolume") / 3);

            if (leftButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.A)) {
                jax.addVelocityVec(-1);
            } else if (rightButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.D)) {
                jax.addVelocityVec(1);
            } else {
                jax.decelerateActor();
            }

            if (!zeroStamina && (attackButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
                jax.setHitting(true);
                jax.setStamina(-1.2f);
            } else {
                jax.setHitting(false);
                if (!attackButton.isPressed()) {
                    jax.setStamina(0.2f);
                }
            }

            if (climbButton.isPressed() && jax.getStairsOverlap()) {
                jax.setGravity(0);
                jax.climb();
            } else if (jax.getStairsOverlap()) {
                if (!zeroClimbFlag) {
                    jax.velocityVec.y = 0;
                    zeroClimbFlag = true;
                }
                jax.climbDown();
                jax.setGravity(0);
            } else {
                jax.setGravity(700);
                zeroClimbFlag = false;
            }


            for (BaseActor b : BaseActor.getList(mainStage, "StickEnemy")) {
                StickEnemy st = (StickEnemy) b;
                allLogic(st);
            }
            for (BaseActor b : BaseActor.getList(mainStage, "FlyEnemy")) {
                FlyEnemy fl = (FlyEnemy) b;
                allLogic(fl);
            }
            allLogic(jax);
            jaxHit();
            findJaxByStick();
            findJaxByFly();
            isOverlapTeleport();
            checkCrystalsCollected();
            crystalCollect();
            collectFirstAidKit();
            overlapSign();
            overlapStairs();
            controlZeroStamina();
            healthBar.setValue(jax.getHealth() / BaseGame.prefs.getFloat("Health"));
            staminaBar.setValue(jax.getStamina() / BaseGame.prefs.getFloat("Stamina"));

        }

    }

    public boolean checkLoad(){
        int n = 0;
        for(int i = 0; i<tma.getRectangleList("Solid").size(); i++){
            if(loadList[i] == true){
                n+=1;
            }
        }
        if(n == tma.getRectangleList("Solid").size()){
            return true;
        } else{
            return false;
        }
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
                st.setHealth(-BaseGame.prefs.getFloat("Damage"));
            }
        }
        for(BaseActor b : BaseActor.getList(mainStage, "FlyEnemy")){
            if(jax.overlaps(b) && jax.isHitting()){
                FlyEnemy fl = (FlyEnemy) b;
                fl.setHealth(-BaseGame.prefs.getFloat("Damage"));
            }
        }
    }

    public void findJaxByStick(){
        for(BaseActor b : BaseActor.getList(mainStage, "StickEnemy")){
            if(Math.abs(jax.getY()-b.getY())<50 && Math.abs(jax.getX()-b.getX())<=25){
                StickEnemy st = (StickEnemy) b;
                st.velocityVec.x = 0;
                st.setHitting(true);
                jax.setHealth(st.getDamage());
            } else if(Math.abs(jax.getY()-b.getY())<=150 && Math.abs(jax.getX()-b.getX())<=500 && Math.abs(jax.getX()-b.getX())>=25){
                StickEnemy st = (StickEnemy) b;
                st.setHitting(false);
                st.addVelocityVec((jax.getX()-b.getX())/(Math.abs(jax.getX()-b.getX())));
            } else{
                StickEnemy st = (StickEnemy) b;
                st.setHitting(false);
                st.velocityVec.x = 0;
            }
        }
    }

    public void findJaxByFly(){
        for(BaseActor b : BaseActor.getList(mainStage, "FlyEnemy")){
            if(Math.abs(jax.getY()-b.getY())<50 && Math.abs(jax.getX()-b.getX())<=25){
                FlyEnemy fl = (FlyEnemy) b;
                fl.velocityVec.x = 0;
                fl.velocityVec.y = 0;
                fl.setHitting(true);
                jax.setHealth(fl.getDamage());
            } else if((Math.abs(jax.getX()-b.getX())<=500 && Math.abs(jax.getX()-b.getX())>=25 && Math.abs(jax.getY()-b.getY())<=350) || (Math.abs(jax.getY()-b.getY())<=350 && Math.abs(jax.getY()-b.getY())>=50 && Math.abs(jax.getX()-b.getX())<=500)){
                FlyEnemy fl = (FlyEnemy) b;
                fl.setHitting(false);
                if(Math.abs(jax.getX()-b.getX())<=500 && Math.abs(jax.getX()-b.getX())>=25) {
                    fl.addVelocityVec((jax.getX() - b.getX()) / (Math.abs(jax.getX() - b.getX())));
//                    if(fl.getFlyX()!=fl.getX()){
//                        fl.setFlyX(fl.getX());
//                    } else {
//                        if(fl.getX())
//                        fl.addVelocityVecY((jax.getY() - b.getY()) / (Math.abs(jax.getY() - b.getY())));
//                    }
                } else{
                    fl.velocityVec.x = 0;
                }
                if(Math.abs(jax.getY()-b.getY())<=350  &&  Math.abs(jax.getY()-b.getY())>=50) {
                    fl.addVelocityVecY((jax.getY() - b.getY()) / (Math.abs(jax.getY() - b.getY())));
                } else {
                    fl.velocityVec.y = 0;
                }

            } else{
                FlyEnemy fl = (FlyEnemy) b;
                fl.setHitting(false);
                fl.velocityVec.x = 0;
                fl.velocityVec.y = 0;
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
                crystalCollectSound.play(BaseGame.prefs.getFloat("SoundVolume"));
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
            goal = true;
        }
    }

    public void collectFirstAidKit(){
        for(BaseActor b : BaseActor.getList(mainStage, "FirstAidKit")){
            if(jax.overlaps(b)){
                FirstAidKit fak = (FirstAidKit) b;
                fak.clearActions();
                fak.addAction(Actions.removeActor());
                firstAidKitSound.play(BaseGame.prefs.getFloat("SoundVolume"));
                jax.setHealth(50);
            }
        }
    }

    public void isOverlapTeleport(){
        for(BaseActor b : BaseActor.getList(mainStage, "Teleport")){
            if(jax.overlaps(b) && goal){
                if(levelNumber+1 == MainGameValues.maps.length){
                    BaseGame.setActiveScreen(new MenuScreen(jg));
                } else {
                    BaseGame.setActiveScreen(new LoadingLevelsScreen(levelNumber + 1, MainGameValues.getGoal(levelNumber+1),jg));
                }
                ost.dispose();
            } else if(jax.overlaps(b) && !goal){
                if(!sayWhat){
                    whatSound.play(BaseGame.prefs.getFloat("SoundVolume"));
                    sayWhat = true;
                }
            } else {
                sayWhat = false;
            }
        }

    }

    public void overlapSign(){
        for(BaseActor signActor : BaseActor.getList(mainStage, "Sign")){
            Sign sign = (Sign)signActor;

            boolean nearby = jax.isWithinDistance(4, sign);
            if(nearby && !sign.isViewing()){
                dialogBox.setText(sign.getText());
                if(!sayWhat){
                    whatSound.play(BaseGame.prefs.getFloat("SoundVolume"));
                    sayWhat = true;
                }
                dialogBox.setVisible(true);
                sign.setViewing(true);
            }

            if(sign.isViewing() && !nearby){
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
                sayWhat = false;
            }
        }
    }

    public void overlapStairs(){
        for(BaseActor b : BaseActor.getList(mainStage, "StairsRectangle")){
            StairsRectangle stairs = (StairsRectangle) b;
            if(jax.overlaps(stairs)){
                jax.setStairsOverlap(true);
            } else{
                jax.setStairsOverlap(false);
            }
        }
    }

    public ProgressBar createProgressBar(Color c1, Color c2){
        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(c1);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        pbs = new ProgressBar.ProgressBarStyle();
        pbs.background= drawable;

        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(c2);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        pbs.knob = drawable;

        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(c2);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        pbs.knobBefore = drawable;

        ProgressBar pb;

        pb = new ProgressBar(0, 1, 0.01f, false, pbs);
        pb.setValue(1);
        pb.setAnimateDuration(0.25f);
        return pb;
    }

    public void controlZeroStamina(){
        if(jax.getStamina()<=0){
            zeroStamina = true;
        } else if(jax.getStamina() >= 50){
            zeroStamina = false;
        }
    }
}
