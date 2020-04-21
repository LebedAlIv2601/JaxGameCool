package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.BaseGame;
import com.mygdx.game.JaxGame;

import java.awt.Font;

public class LoadingScreen extends BaseScreen implements Screen {

    private final JaxGame jg;

    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(final JaxGame jg){
        this.jg = jg;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

        this.progress = 0f;
        queueAssets();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(498,jg.cameraSplash.viewportHeight/2-10,jg.cameraSplash.viewportWidth-996,20);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(500,jg.cameraSplash.viewportHeight/2-8,(jg.cameraSplash.viewportWidth-1000)*progress,16);

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update(float dt) {
        progress = MathUtils.lerp(progress,jg.assetManager.getProgress(),.1f);
        if(jg.assetManager.update() && progress >= jg.assetManager.getProgress() - .001f){
            jg.setScreen(jg.splashScreen);
        }
    }

    private void queueAssets(){
        jg.assetManager.load("Splash/back.png", Texture.class);
        jg.assetManager.load("Splash/logo.png", Texture.class);
        jg.assetManager.load("Splash/flow.png", Texture.class);
        jg.assetManager.load("Level1Map/fire.png", Texture.class);
        jg.assetManager.load("Level1Map/Level1TileSet.png", Texture.class);
        jg.assetManager.load("1.png", Texture.class);
        jg.assetManager.load("2.png", Texture.class);
        jg.assetManager.load("3.png", Texture.class);
        jg.assetManager.load("4.png", Texture.class);
        jg.assetManager.load("5.png", Texture.class);
        jg.assetManager.load("1going.png", Texture.class);
        jg.assetManager.load("2going.png", Texture.class);
        jg.assetManager.load("3going.png", Texture.class);
        jg.assetManager.load("4going.png", Texture.class);
        jg.assetManager.load("5going.png", Texture.class);
        jg.assetManager.load("bruh1.mp3", Sound.class);
        jg.assetManager.load("button.png", Texture.class);
        jg.assetManager.load("cooper.fnt", BitmapFont.class);
        jg.assetManager.load("cooper.png", Texture.class);
        jg.assetManager.load("emenyHit.png", Texture.class);
        jg.assetManager.load("emenySt1.png", Texture.class);
        jg.assetManager.load("enemySt2.png", Texture.class);
        jg.assetManager.load("flag1.png", Texture.class);
        jg.assetManager.load("flag2.png", Texture.class);
        jg.assetManager.load("jump1.png", Texture.class);
        jg.assetManager.load("jump2.png", Texture.class);
        jg.assetManager.load("jump3.png", Texture.class);
        jg.assetManager.load("loseMessage.png", Texture.class);
        jg.assetManager.load("restart.png", Texture.class);
        jg.assetManager.load("Staying.png", Texture.class);
        jg.assetManager.load("trava1.png", Texture.class);
        jg.assetManager.load("trava2.png", Texture.class);
    }
}
