package com.mygdx.game.Screens;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.BaseGame;
import com.mygdx.game.JaxGame;
import com.mygdx.game.MainGameValues;

import java.util.ArrayList;


public class ChooseLevelScreen extends BaseScreen {
    private ArrayList<TextButton> lvlButtons;
    private ArrayList<BaseLevelScreen> lvlArray;
    private int n;

    public ChooseLevelScreen() {
        super(0, 0);
    }

    @Override
    public void initialize() {
        lvlButtons = new ArrayList<TextButton>();
        uiTable.pad(20);
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
                JaxGame.setActiveScreen(new BaseLevelScreen(num, gn));
                return false;
            }
        });

        uiTable.add(lvlButtons.get(i)).top().padLeft(30);
        if(i%8==0 && i!=0){
            uiTable.row();
        }
    }
}
