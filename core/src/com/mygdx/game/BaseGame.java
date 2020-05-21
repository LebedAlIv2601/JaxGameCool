package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.game.Screens.BaseScreen;


public abstract class BaseGame extends Game {
    private static BaseGame game;
    public static LabelStyle labelStyle;
    public static TextButtonStyle textButtonStyle;

    public static Preferences prefs;


    public BaseGame(){
        game = this;
    }

    public void create(){
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
        labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("jokerman.fnt"));

        textButtonStyle = new TextButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("BaseButton.png"));
        NinePatch buttonPath = new NinePatch(buttonTex, 24,24,24,24);
        textButtonStyle.up = new NinePatchDrawable(buttonPath);
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("jokerman.fnt"));
        textButtonStyle.fontColor = Color.GOLD;

//      Создание preferences

        prefs = Gdx.app.getPreferences("prefs");
        if(!prefs.contains("Exp")){
            prefs.putInteger("Exp", 0);
        }
        if(!prefs.contains("Health")){
            prefs.putFloat("Health", 100);
        }
        if(!prefs.contains("Stamina")){
            prefs.putFloat("Stamina", 100);
        }
        if(!prefs.contains("Damage")){
            prefs.putFloat("Damage", 2f);
        }
        if(!prefs.contains("MusicVolume")){
            prefs.putFloat("MusicVolume", 1);
        }
        if(!prefs.contains("SoundVolume")){
            prefs.putFloat("SoundVolume", 1);
        }
        prefs.flush();

    }

    public static void setActiveScreen(BaseScreen s){
        game.setScreen(s);
    }
}
