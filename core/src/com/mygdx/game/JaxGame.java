package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.Screens.LoadingScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.SplashScreen;



public class JaxGame extends BaseGame {

    public OrthographicCamera cameraSplash;
    public AssetManager assetManager;

    public LoadingScreen loadingScreen;
    public SplashScreen splashScreen;

    @Override
    public void create() {

        assetManager = new AssetManager();

        cameraSplash = new OrthographicCamera();
        cameraSplash.setToOrtho(false, TilemapActor.windowWidth,TilemapActor.windowHeight);

        loadingScreen = new LoadingScreen(this);
        splashScreen = new SplashScreen(this);

        super.create();
        this.setScreen(loadingScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        splashScreen.dispose();
        loadingScreen.dispose();
        this.getScreen().dispose();
    }
}
