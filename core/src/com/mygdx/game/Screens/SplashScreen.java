package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.BaseGame;
import com.mygdx.game.JaxGame;
import com.mygdx.game.Screens.BaseScreen;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.mygdx.game.BaseGame.setActiveScreen;


public class SplashScreen extends BaseScreen implements Screen {

    private final JaxGame jg;
    private Stage stageSplash;

    private Image splashImage;
    private Image splashBack;
    private Image flow;

    public SplashScreen (final JaxGame jg){
        super(0,0);
        this.jg = jg;
        this.stageSplash = new Stage(new FillViewport(TilemapActor.windowWidth,TilemapActor.windowHeight,jg.cameraSplash));
    }


    @Override
    public void show() {

        Runnable transition = new Runnable() {
            @Override
            public void run() {
                setActiveScreen(new MenuScreen());
            }
        };

        Texture splashBackTexture = jg.assetManager.get("Splash/back.png",Texture.class);
        splashBack = new Image(splashBackTexture);

        Texture splashTexture = jg.assetManager.get("Splash/logo.png",Texture.class);
        splashImage = new Image(splashTexture);
        splashImage.setOrigin(splashImage.getWidth()/2,splashImage.getHeight()/2);

        Texture splashTextureFlow = jg.assetManager.get("Splash/flow.png");

        splashBack.setPosition(0,0);
        splashBack.addAction(sequence(sizeTo(stageSplash.getWidth(),stageSplash.getHeight()),alpha(0f),fadeIn(3f),
                delay(4f),fadeOut(1f),run(transition)));


        splashImage.setPosition(stageSplash.getWidth()/2 - 400,-225);
        splashImage.addAction(sequence((alpha(0f)),scaleTo(1f,1f),
                parallel(fadeIn(5f, Interpolation.pow3),
                        moveTo(stageSplash.getWidth()/2 - 400,stageSplash.getHeight()/2 - 225,3f,Interpolation.swing)),
                delay(1f),fadeOut(1f)));

        stageSplash.addActor(splashBack);
        stageSplash.addActor(splashImage);
}

    @Override
    public void render(float delta) {
        super.render(delta);

        stageSplash.draw();
    }

    @Override
    public void resize(int width, int height) {
        stageSplash.getViewport().update(width,height,false);
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
        stageSplash.dispose();
    }


    @Override
    public void initialize() {


    }

    @Override
    public void update(float dt) {
        stageSplash.act(dt);
    }
}
