package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.BaseGame;
import com.mygdx.game.JaxGame;
import com.mygdx.game.Screens.BaseScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class SplashScreen extends BaseScreen implements Screen {

    private final JaxGame jg;
    private Stage stageSplash;

    private Image splashImage;

    public SplashScreen (final JaxGame jg){
        this.jg = jg;
        this.stageSplash = new Stage(new FitViewport(TilemapActor.windowWidth,TilemapActor.windowHeight,jg.cameraSplash));
        Gdx.input.setInputProcessor(stageSplash);

        Texture splashTexture = new Texture(Gdx.files.internal("Splash/logo.png"));
        splashImage = new Image(splashTexture);


        stageSplash.addActor(splashImage);
    }


    @Override
    public void show() {
        splashImage.setPosition(stageSplash.getWidth()/2 - 400,stageSplash.getHeight()/2 - 225);
        splashImage.addAction(alpha(.5f));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

//        jg.batch.begin();
//        jg.batch.draw(jg.back,stageSplash.getWidth()/2 - 400,stageSplash.getHeight()/2 - 225);
//        jg.batch.end();

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
