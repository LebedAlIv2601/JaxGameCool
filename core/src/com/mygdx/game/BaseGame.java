package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;


public abstract class BaseGame extends Game {
    private static BaseGame game;
    public static LabelStyle labelStyle;
    public static TextButtonStyle textButtonStyle;

    public BaseGame(){
        game = this;
    }

    public void create(){
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
        labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("cooper.fnt"));

        textButtonStyle = new TextButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("button.png"));
        NinePatch buttonPath = new NinePatch(buttonTex, 24,24,24,24);
        textButtonStyle.up = new NinePatchDrawable(buttonPath);
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("cooper.fnt"));
        textButtonStyle.fontColor = Color.GOLD;
    }

    public static void setActiveScreen(BaseScreen s){
        game.setScreen(s);
    }
}
