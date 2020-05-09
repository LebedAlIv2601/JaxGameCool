package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.Screens.BaseLevelScreen;

import java.util.ArrayList;

public class MainGameValues {

    public static String[] texturesForHit = {"udar1.png", "udar2.png"};

    public static String[] texturesForWalk = {"1going.png","2going.png", "3going.png"};
    public static String[] texturesForJump = {"jump3.png"};
    public static String[] staying = {"Staying.png", "Staying2.png"};
    public static String[] climbing = {"climb1.png", "climb2.png"};
    public static float jaxHealth = 100;
    public static float jaxStamina = 100;
    public static int jaxExp = 100;



    public static String[] enemyStand = {"EnemySt1.png", "EnemySt2.png"};
    public static String[] enemyWalk = {"runEnemy1.png", "runEnemy2.png", "runEnemy3.png", "runEnemy4.png"};
    public static String[] enemyHit = {"enemyHit.png", "EnemySt1.png"};

    public static String[] flyStand = {"fly1.png", "fly2.png", "fly3.png"};
    public static String[] flyWalk = {"flyRun1.png", "flyRun2.png", "flyRun3.png"};
    public static String[] flyHit = {"flyHit1.png", "flyHit2.png", "flyHit3.png", "flyHit4.png"};

    public static String[] grassAnimation = {"trava1.png", "trava2.png"};

    public static String[] signAnimation = {"flag1.png", "flag2.png"};

    public static String[] maps = {"Level1Map1.tmx", "Level1Map.tmx", "Level2Map.tmx", "Level2Map2.tmx"};

    public static String[] fire = {"Level1Map/fire1.png","Level1Map/fire2.png"};

    public static ArrayList<BaseLevelScreen> lvlArray;
    public static int getGoal(int n){
        int g;
        switch (n) {
            case 0:
                g = 0;
                break;
            case 1:
                g = 0;
                break;
            case 2:
                g = 1;
                break;
            case 3:
                g = 1;
                break;
            default:
                g = 0;
                break;
        }
        return g;
    }

}
