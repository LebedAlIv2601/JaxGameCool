package com.mygdx.game;

import com.mygdx.game.Screens.BaseLevelScreen;

import java.util.ArrayList;

public class MainGameValues {
    public static String[] texturesForHit = {"udar1.png", "udar2.png"};
    public static String[] texturesForWalk = {"1going.png","2going.png", "3going.png"};
    public static String[] texturesForJump = {"jump3.png"};
    public static String[] staying = {"Staying.png", "Staying.png"};
    public static float jaxHealth = 100;
    public static float jaxStamina = 100;

    public static String[] enemyStand = {"EnemySt1.png", "enemySt2.png"};
    public static String[] enemyWalk = {"runEnemy1.png", "runEnemy2.png", "runEnemy3.png", "runEnemy4.png"};
    public static String[] enemyHit = {"enemyHit.png", "EnemySt1.png"};

    public static String[] grassAnimation = {"trava1.png", "trava2.png"};

    public static String[] signAnimation = {"flag1.png", "flag2.png"};

    public static String[] maps = {"Level1Map.tmx", "Level2Map.tmx"};

    public static int getGoal(int n){
        int g;
        switch (n) {
            case 0:
                g = 1;
                break;
            case 1:
                g = 0;
                break;
            default:
                g = 0;
                break;
        }
        return g;
    }

}
