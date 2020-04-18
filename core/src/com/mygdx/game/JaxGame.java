package com.mygdx.game;

public class JaxGame extends BaseGame {
    @Override
    public void create() {
        super.create();
        setActiveScreen(new MenuScreen());
    }
}
