package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.BaseGame;
import com.mygdx.game.JaxGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SettingsScreen extends BaseScreen{
    private Image menuTree;
    private Image menuBack;
    private TextButton menuButton;
    private Label settingsLabel;
    private Label musicLabel;
    private Label soundLabel;
    private Slider musicSlider;
    private Slider soundSlider;
    private SliderStyle  sliderStyle;
    private Pixmap pixmap;
    private TextureRegionDrawable drawable;

    public SettingsScreen(JaxGame jg) {
        super(0, 0, jg);
    }

    @Override
    public void initialize() {
        Texture menuBackTexture = new Texture("Menu/menuBack.png");
        menuBack = new Image(menuBackTexture);
        Texture menuTreeTexture = new Texture("Menu/menuTree.png");
        menuTree = new Image(menuTreeTexture);

        menuBack.setPosition(0f,0f);
        menuBack.setSize(mainStage.getWidth(),mainStage.getHeight());
        menuBack.addAction(sequence(alpha(0f),fadeIn(1f)));

        menuTree.setPosition(50,0);
        menuTree.setSize(mainStage.getWidth(),mainStage.getHeight());
        menuTree.addAction(forever(sequence(moveTo(-70,0,5f, Interpolation.pow2),
                delay(1f),
                moveTo(50,0,5f,Interpolation.pow2))));
        mainStage.addActor(menuBack);
        mainStage.addActor(menuTree);

        settingsLabel = new Label("Settings", BaseGame.labelStyle);
        settingsLabel.setFontScale(2);

        musicLabel = new Label("Music volume", BaseGame.labelStyle);
        musicLabel.setFontScale(2);

        soundLabel = new Label("Sound volume", BaseGame.labelStyle);
        soundLabel.setFontScale(2);

        menuButton = new TextButton("Menu", BaseGame.textButtonStyle);

        menuButton.addListener(new EventListener() {
            @Override

            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                BaseGame.setActiveScreen(new MenuScreen(jg));
//                MenuScreen.disposeOst();
                return false;
            }
        });

        sliderStyle = new SliderStyle();

        pixmap = new Pixmap(500, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        sliderStyle.background = drawable;

        pixmap = new Pixmap(500, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.YELLOW);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        sliderStyle.knobBefore = drawable;

        drawable = new TextureRegionDrawable(new TextureRegion(new Texture("knob.png")));
        sliderStyle.knob = drawable;

        musicSlider = new Slider(0, 1, 0.1f, false, sliderStyle);
        soundSlider = new Slider(0, 1, 0.1f, false, sliderStyle);

        musicSlider.setValue(BaseGame.prefs.getFloat("MusicVolume"));
        soundSlider.setValue(BaseGame.prefs.getFloat("SoundVolume"));

        uiTable.pad(20);
        uiTable.add(menuButton).top().left().colspan(2).padBottom(30);
        uiTable.add(settingsLabel).top().center().colspan(5).padBottom(30);
        uiTable.add().colspan(2);

//        uiTable.add().expandX();
        uiTable.row();
        uiTable.add().colspan(2);
        uiTable.add(musicLabel).top().center().colspan(5).padTop(50).padBottom(30);
        uiTable.add().colspan(2);
        uiTable.row();
        uiTable.add().colspan(2);
        uiTable.add(musicSlider).top().center().colspan(5).padBottom(30);
        uiTable.add().colspan(2);
        uiTable.row();
        uiTable.add().colspan(2);
        uiTable.add(soundLabel).top().center().colspan(5).padBottom(30);
        uiTable.add().colspan(2);
        uiTable.row();
        uiTable.add().colspan(2);
        uiTable.add(soundSlider).top().center().colspan(5).padBottom(30);
        uiTable.add().colspan(2);
        uiTable.row();
        uiTable.add().expandY();
    }

    @Override
    public void update(float dt) {
//      Изменение уровня громкости
        BaseGame.prefs.putFloat("MusicVolume", musicSlider.getValue());
        BaseGame.prefs.putFloat("SoundVolume", soundSlider.getValue());
        MenuScreen.changeSoundOst();
    }
}
