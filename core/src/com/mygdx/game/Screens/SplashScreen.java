package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.mygdx.game.BaseGame.setActiveScreen;


public class SplashScreen extends BaseScreen implements Screen {

    private final JaxGame jg;
    private Stage stageSplash;

    private Image splashImage;
    private Image splashBack;
    private Image splashFlow;
    private Image splashLight;
    private Music intro;

    public SplashScreen (final JaxGame jg){
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

        Texture splashTextureFlow = jg.assetManager.get("Splash/flow.png");
        splashFlow = new Image(splashTextureFlow);

        Texture splashTexture = jg.assetManager.get("Splash/logo.png",Texture.class);
        splashImage = new Image(splashTexture);
        splashImage.setOrigin(splashImage.getWidth()/2,splashImage.getHeight()/2);

        Texture splashLightTexture = jg.assetManager.get("Splash/Light.png",Texture.class);
        splashLight = new Image(splashLightTexture);

        splashBack.setPosition(0,0);
        splashBack.addAction(sequence(sizeTo(stageSplash.getWidth(),stageSplash.getHeight()),alpha(0f),fadeIn(3f),
                delay(4f),fadeOut(3f),run(transition)));


        Runnable transFlow = new Runnable() {
            @Override
            public void run() {
                splashFlow.setPosition(stageSplash.getWidth()/2-191*7,stageSplash.getHeight());
                splashFlow.addAction(sequence(moveTo(stageSplash.getWidth()/2-191*7,-410*7,.2f)));
            }
        };

        Runnable transFlow2 = new Runnable() {
            @Override
            public void run() {
                splashFlow.setPosition(stageSplash.getWidth()/2-191*7,stageSplash.getHeight());
                splashFlow.addAction(sequence(moveTo(stageSplash.getWidth()/2-191*7,-410*7,.2f)));
            }
        };

        splashFlow.setPosition(stageSplash.getWidth()/2-191*7,stageSplash.getHeight());
        splashFlow.setSize(382*7,410*7);
        splashFlow.addAction(sequence(delay(2f),moveTo(stageSplash.getWidth()/2-191*7,-410*7,.2f),run(transFlow),delay(.2f),run(transFlow2)));

        splashImage.setPosition(stageSplash.getWidth()/2 - 400,-225);
        splashImage.addAction(sequence((alpha(0f)),scaleTo(1f,1f),
                parallel(fadeIn(5f, Interpolation.pow3),
                        moveTo(stageSplash.getWidth()/2 - 400,stageSplash.getHeight()/2 - 225,3f,Interpolation.swing)),
                delay(1f),fadeOut(1f)));


        final Runnable intro = new Runnable() {
            @Override
            public void run() {
                intro();
            }
        };

        splashLight.setPosition(0,0);
        splashLight.setSize(stageSplash.getWidth(),stageSplash.getHeight());
        splashLight.addAction(sequence(alpha(0f),delay(1f),run(intro),fadeIn(1f,Interpolation.pow5),fadeOut(3f)));

        stageSplash.addActor(splashBack);
        stageSplash.addActor(splashImage);
        stageSplash.addActor(splashFlow);
        stageSplash.addActor(splashLight);
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

    public void intro(){
        intro = Gdx.audio.newMusic(Gdx.files.internal("Splash/splashINTRO.mp3"));
        intro.setLooping(false);
        intro.play();
    }

}
