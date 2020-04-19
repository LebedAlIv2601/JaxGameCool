package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.Screens.SplashScreen;


public class JaxGame extends BaseGame {

    public OrthographicCamera cameraSplash;

    public Texture back;
    public Texture logo;
    public Texture flow;
    public SpriteBatch batch;

    @Override
    public void create() {

        cameraSplash = new OrthographicCamera();
        cameraSplash.setToOrtho(false, TilemapActor.windowWidth,TilemapActor.windowHeight);

        back = new Texture("Splash/back.png");
        logo = new Texture("Splash/logo.png");
        flow = new Texture("Splash/flow.png");
        batch = new SpriteBatch();

        super.create();
       setActiveScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        this.getScreen().dispose();
    }
}
