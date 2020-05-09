package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.JaxGame;
import com.mygdx.game.MainGameValues;

public class LoadingLevelsScreen extends BaseScreen implements Screen {

    private ShapeRenderer shapeRendererLvlLoad;
    private float progressLvlLoad;
    public static AssetManager assetManagerLvl;
    public OrthographicCamera cameraSplashLvlLoad;

    private int num,goal;

    public LoadingLevelsScreen(int n, int g, JaxGame jg){
        super(0,0, jg);
        num = n;
        goal = g;
        this.shapeRendererLvlLoad = new ShapeRenderer();
    }

    @Override
    public void show() {

        this.progressLvlLoad = 0f;
        queueAssetsLvlLoad();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);

        shapeRendererLvlLoad.begin(ShapeRenderer.ShapeType.Filled);

        shapeRendererLvlLoad.setColor(Color.GRAY);
        shapeRendererLvlLoad.rect(498,cameraSplashLvlLoad.viewportHeight/2-10,cameraSplashLvlLoad.viewportWidth-996,20);

        shapeRendererLvlLoad.setColor(Color.WHITE);
        shapeRendererLvlLoad.rect(500,cameraSplashLvlLoad.viewportHeight/2-8,(cameraSplashLvlLoad.viewportWidth-1000)*progressLvlLoad,16);

        shapeRendererLvlLoad.end();
    }

    @Override
    public void dispose() {
        shapeRendererLvlLoad.dispose();
    }

    @Override
    public void initialize() {

        assetManagerLvl = new AssetManager();
        cameraSplashLvlLoad = new OrthographicCamera();
        cameraSplashLvlLoad.setToOrtho(false, TilemapActor.windowWidth,TilemapActor.windowHeight);

    }

    @Override
    public void update(float dt) {
        progressLvlLoad = MathUtils.lerp(progressLvlLoad,assetManagerLvl.getProgress(),.1f);
        if(assetManagerLvl.update() && progressLvlLoad >= assetManagerLvl.getProgress() - .001f){
            JaxGame.setActiveScreen(new BaseLevelScreen(num, goal,jg));
        }
    }

    private void queueAssetsLvlLoad(){
        assetManagerLvl.load("Level1Map/fire1.png", Texture.class);
        assetManagerLvl.load("Level1Map/fire2.png", Texture.class);
        assetManagerLvl.load("Level1Map/Level1TileSet.png", Texture.class);
        assetManagerLvl.load("1.png", Texture.class);
        assetManagerLvl.load("2.png", Texture.class);
        assetManagerLvl.load("3.png", Texture.class);
        assetManagerLvl.load("4.png", Texture.class);
        assetManagerLvl.load("5.png", Texture.class);
        assetManagerLvl.load("1going.png", Texture.class);
        assetManagerLvl.load("2going.png", Texture.class);
        assetManagerLvl.load("3going.png", Texture.class);
        assetManagerLvl.load("4going.png", Texture.class);
        assetManagerLvl.load("5going.png", Texture.class);
        assetManagerLvl.load("bruh1.mp3", Sound.class);
        assetManagerLvl.load("button.png", Texture.class);
        assetManagerLvl.load("cooper.fnt", BitmapFont.class);
        assetManagerLvl.load("cooper.png", Texture.class);
        assetManagerLvl.load("enemyHit.png", Texture.class);
        assetManagerLvl.load("EnemySt1.png", Texture.class);
        assetManagerLvl.load("EnemySt2.png", Texture.class);
        assetManagerLvl.load("flag1.png", Texture.class);
        assetManagerLvl.load("flag2.png", Texture.class);
        assetManagerLvl.load("climb1.png", Texture.class);
        assetManagerLvl.load("climb2.png", Texture.class);
        assetManagerLvl.load("Staying2.png", Texture.class);
        assetManagerLvl.load("jump1.png", Texture.class);
        assetManagerLvl.load("jump2.png", Texture.class);
        assetManagerLvl.load("jump3.png", Texture.class);
        assetManagerLvl.load("loseMessage.png", Texture.class);
        assetManagerLvl.load("restart.png", Texture.class);
        assetManagerLvl.load("Staying.png", Texture.class);
        assetManagerLvl.load("Staying2.png", Texture.class);
        assetManagerLvl.load("trava1.png", Texture.class);
        assetManagerLvl.load("trava2.png", Texture.class);
        assetManagerLvl.load("ost.mp3", Music.class);
        assetManagerLvl.load("whatSound.mp3",Sound.class);
        assetManagerLvl.load("jumpSound.mp3",Sound.class);
        assetManagerLvl.load("firstAidKitSound.mp3",Sound.class);
        assetManagerLvl.load("crystalCollectSound.mp3",Sound.class);
        assetManagerLvl.load("udar1.png",Texture.class);
        assetManagerLvl.load("udar2.png",Texture.class);
        assetManagerLvl.load("runEnemy1.png",Texture.class);
        assetManagerLvl.load("runEnemy2.png",Texture.class);
        assetManagerLvl.load("runEnemy3.png",Texture.class);
        assetManagerLvl.load("runEnemy4.png",Texture.class);

        assetManagerLvl.load("fly1.png",Texture.class);
        assetManagerLvl.load("fly2.png",Texture.class);
        assetManagerLvl.load("fly3.png",Texture.class);
        assetManagerLvl.load("flyRun1.png",Texture.class);
        assetManagerLvl.load("flyRun2.png",Texture.class);
        assetManagerLvl.load("flyRun3.png",Texture.class);
        assetManagerLvl.load("flyHit1.png",Texture.class);
        assetManagerLvl.load("flyHit2.png",Texture.class);
        assetManagerLvl.load("flyHit3.png",Texture.class);
        assetManagerLvl.load("flyHit4.png",Texture.class);

        assetManagerLvl.load("goalGetToTheEnd.png",Texture.class);
        assetManagerLvl.load("dialogBox.png",Texture.class);
        assetManagerLvl.load("Stairs.png",Texture.class);
        assetManagerLvl.load("crystal.png",Texture.class);
        assetManagerLvl.load("firstAidKit.png",Texture.class);
        assetManagerLvl.load("goalCrystals.png",Texture.class);
        assetManagerLvl.load("collectCrystals.png",Texture.class);

        assetManagerLvl.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        assetManagerLvl.load( "Level1Map/" + MainGameValues.maps[num], TiledMap.class);

        assetManagerLvl.finishLoading();
    }
}
