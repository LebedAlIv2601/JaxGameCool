package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.Actors.TilemapActor;
import com.mygdx.game.BaseGame;
import com.mygdx.game.JaxGame;
import com.mygdx.game.MainGameValues;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class MenuScreen extends BaseScreen implements Screen {
    private Image startButton;
    private Image menuBack;
    private Image levelsButton;
    private Image settingsButton;
    private Image quitButton;
    private Image menuTree;
    private Image menuLogo;
    private OrthographicCamera camMenu;
    private static Music menuOst;
    private float audioVolume;
    private Sound buttonClickSound;

    public MenuScreen() {
        super(0, 0);
    }



    @Override
    public void show() {
        super.show();

        Texture menuLogoTexture = new Texture("Menu/menuLogo.png");
        menuLogo = new Image(menuLogoTexture);

        Texture startButtonTexture = new Texture("Menu/startButton.png");
        startButton = new Image(startButtonTexture);

        Texture levelsButtonTexture = new Texture("Menu/levelsButton.png");
        levelsButton = new Image(levelsButtonTexture);

        Texture menuBackTexture = new Texture("Menu/menuBack.png");
        menuBack = new Image(menuBackTexture);

        Texture settingsButtonTexture = new Texture("Menu/settingsButton.png");
        settingsButton = new Image(settingsButtonTexture);

        Texture quitButtonTexture = new Texture("Menu/quitButton.png");
        quitButton = new Image(quitButtonTexture);

        Texture menuTreeTexture = new Texture("Menu/menuTree.png");
        menuTree = new Image(menuTreeTexture);


        menuLogo.setPosition(mainStage.getWidth(),mainStage.getHeight()/2 + 100);
        menuLogo.addAction(forever(sequence((alpha(0f)),scaleTo(1f,1f),
                parallel(fadeIn(5f, Interpolation.pow3),
                        moveTo(mainStage.getWidth()/2 - 279,mainStage.getHeight()/2 + 100,5f,Interpolation.swing)),
                        delay(5f),
                        parallel(fadeOut(1f),
                        moveTo(-560,mainStage.getHeight()/2 + 100,2f,Interpolation.swing)),
                        moveTo(mainStage.getWidth(),mainStage.getHeight()/2 + 100))));

        startButton.setPosition(mainStage.getWidth()/2-150,mainStage.getHeight()/2);
        startButton.setSize(300,100);
        startButton.addAction(forever(sequence(moveTo(mainStage.getWidth()/2-145,mainStage.getHeight()/2-5,1f,Interpolation.swing),
                moveTo(mainStage.getWidth()/2-155,mainStage.getHeight()/2,1f,Interpolation.swing))));

        levelsButton.setPosition(mainStage.getWidth()/2 - 130,mainStage.getHeight()/2 - 100);
        levelsButton.setSize(210,70);
        levelsButton.addAction(forever(sequence(moveTo(mainStage.getWidth()/2-135,mainStage.getHeight()/2 - 103,3f,Interpolation.swing),
                moveTo(mainStage.getWidth()/2-125,mainStage.getHeight()/2 - 97,3f,Interpolation.swing))));

        settingsButton.setPosition(mainStage.getWidth()/2 - 70,mainStage.getHeight()/2 - 190);
        settingsButton.setSize(210,70);
        settingsButton.addAction(forever(sequence(moveTo(mainStage.getWidth()/2-75,mainStage.getHeight()/2 - 180,2f,Interpolation.swing),
                moveTo(mainStage.getWidth()/2-65,mainStage.getHeight()/2 - 194,2f,Interpolation.swing))));

        quitButton.setPosition(mainStage.getWidth()/2-90,mainStage.getHeight()/2 - 280);
        quitButton.setSize(210,70);
        quitButton.addAction(forever(sequence(moveTo(mainStage.getWidth()/2-85,mainStage.getHeight()/2 - 285,3f,Interpolation.swing),
                moveTo(mainStage.getWidth()/2-95,mainStage.getHeight()/2 - 273,3f,Interpolation.swing))));


        menuBack.setPosition(0f,0f);
        menuBack.setSize(mainStage.getWidth(),mainStage.getHeight());
        menuBack.addAction(sequence(alpha(0f),fadeIn(1f)));

        menuTree.setPosition(50,0);
        menuTree.setSize(mainStage.getWidth(),mainStage.getHeight());
        menuTree.addAction(forever(sequence(moveTo(-70,0,5f,Interpolation.pow2),
                delay(1f),
                moveTo(50,0,5f,Interpolation.pow2))));

        mainStage.addActor(menuBack);
        mainStage.addActor(menuTree);
        mainStage.addActor(startButton);
        mainStage.addActor(levelsButton);
        mainStage.addActor(settingsButton);
        mainStage.addActor(quitButton);
        mainStage.addActor(menuLogo);
    }


    @Override
    public void initialize() {
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("buttonClickSound.mp3"));
        audioVolume = 1;

        menuOst = Gdx.audio.newMusic(Gdx.files.internal("menuOst.mp3"));
        menuOst.setLooping(true);
        menuOst.setVolume(audioVolume/3);
        menuOst.play();
        camMenu = new OrthographicCamera();
        camMenu.setToOrtho(false,mainStage.getWidth(),mainStage.getHeight());
    }


    public void handleInput(){

        if(Gdx.input.isTouched()){
            if(isButtonTouch(startButton)){
                JaxGame.setActiveScreen(new BaseLevelScreen(0,0));
                menuOst.dispose();
            }
            if(isButtonTouch(levelsButton)){
                JaxGame.setActiveScreen(new ChooseLevelScreen());
            }
            if(isButtonTouch(settingsButton)){
                Gdx.app.exit();
            }
            if(isButtonTouch(quitButton)){
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void update(float dt) {
        camMenu.update();



        handleInput();
    }

    public boolean isButtonTouch(Image b){
        Vector3 menuTouch = new Vector3();
        menuTouch.set(Gdx.input.getX(),Gdx.input.getY(),0);
        camMenu.unproject(menuTouch);
        if(menuTouch.x > b.getX() &&
                menuTouch.x < b.getX()+b.getWidth() &&
                menuTouch.y < b.getY()+b.getHeight() &&
                menuTouch.y > b.getY() ){
            buttonClickSound.play();
            return true;
        } else{
            return false;
        }
    }
    public static void disposeOst(){
        menuOst.dispose();
    }

}
