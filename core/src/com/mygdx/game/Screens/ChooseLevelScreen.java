package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseGame;
import com.mygdx.game.JaxGame;
import com.mygdx.game.MainGameValues;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class ChooseLevelScreen extends BaseScreen {
    private ArrayList<TextButton> lvlButtons;
    private Image menuTree;
    private Image menuBack;
    private TextButton menuButton;
    private Label chooseLabel;

    public ChooseLevelScreen(JaxGame jg) {
        super(0, 0,jg);
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

        menuButton = new TextButton("Menu", BaseGame.textButtonStyle);

        menuButton.addListener(new EventListener() {
            @Override

            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                BaseGame.setActiveScreen(new MenuScreen());
                MenuScreen.disposeOst();
                return false;
            }
        });

        chooseLabel = new Label("Choose Level", BaseGame.labelStyle);
        chooseLabel.setFontScale(2);

        lvlButtons = new ArrayList<TextButton>();
        uiTable.pad(20);
        uiTable.add(menuButton).top().left().colspan(2).padBottom(30);
        uiTable.add(chooseLabel).top().center().colspan(5).padBottom(30);
        uiTable.add().expandX();
        uiTable.row();
        for(int i = 0; i< MainGameValues.maps.length; i++){
            createNextButton(i);
        }
        uiTable.add().expandX().expandY();


    }

    @Override
    public void update(float dt) {

    }
    public void createNextButton(int i){
        final int num = i;
        lvlButtons.add(new TextButton(Integer.toString(i+1), BaseGame.textButtonStyle));
        lvlButtons.get(i).addListener(new EventListener() {
            @Override

            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                int gn;
                if(num == 0){
                    gn = 0;
                } else {
                    gn = MainGameValues.getGoal(num - 1);
                }
                JaxGame.setActiveScreen(new LoadingLevelsScreen(num,gn,jg));
                //slap
                MenuScreen.disposeOst();
                return false;
            }
        });

        uiTable.add(lvlButtons.get(i)).top().padLeft(30);
        if(i%8==0 && i!=0){
            uiTable.row();
        }
    }

}
