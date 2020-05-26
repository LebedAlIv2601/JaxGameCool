package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.JaxGame;


public class LoadingScreen extends BaseScreen implements Screen {

    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen( JaxGame jg){
        super(0,0, jg);
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
        jg.assetManager.load("Splash/Light.png",Texture.class);
        jg.assetManager.load("Splash/splashINTRO.mp3",Music.class);
        jg.assetManager.load("Menu/menuBack.png",Texture.class);
        jg.assetManager.load("Menu/levelsButton.png",Texture.class);
        jg.assetManager.load("Menu/menuLogo.png",Texture.class);
        jg.assetManager.load("Menu/menuTree.png",Texture.class);
        jg.assetManager.load("Menu/quitButton.png",Texture.class);
        jg.assetManager.load("Menu/settingsButton.png",Texture.class);
        jg.assetManager.load("Menu/startButton.png",Texture.class);
        jg.assetManager.load("menuOst.mp3",Music.class);
        jg.assetManager.load("buttonClickSound.mp3",Sound.class);
        jg.assetManager.finishLoading();
    }
}
