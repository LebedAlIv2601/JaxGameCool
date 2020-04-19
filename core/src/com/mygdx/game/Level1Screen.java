package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.nio.channels.Pipe;

public class Level1Screen extends BaseScreen {
    private Jax jax;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton attackButton;
    private Label healthNow;
    private ProgressBar healthBar;
    private ProgressBar.ProgressBarStyle pbs;
    private Pixmap pixmap;
    private TextureRegionDrawable drawable;
    @Override
    public void initialize() {
        TilemapActor tma = new TilemapActor("Level1Map/Level1Map.tmx", mainStage);

        for(MapObject obj : tma.getRectangleList("Solid")){
            MapProperties props = obj.getProperties();
            new Solid((float)props.get("x"), (float)props.get("y"), (float)props.get("width"), (float)props.get("height"), mainStage);
        }

        for(MapObject obj : tma.getTileList("Fire")){
            MapProperties props = obj.getProperties();
            new Fire((float)props.get("x"), (float)props.get("y"), mainStage);
        }

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        jax = new Jax((float)startProps.get("x"), (float)startProps.get("y"), mainStage);

//        healthNow = new Label("Health: ", BaseGame.labelStyle);

        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle pbs =  new ProgressBar.ProgressBarStyle();
        pbs.background= drawable;

        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        pbs.knob = drawable;

        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        pbs.knobBefore = drawable;

        healthBar = new ProgressBar(0, 1, 0.01f, false, pbs);
        healthBar.setValue(1);
        healthBar.setAnimateDuration(0.25f);
//        healthBar.setBounds(10,10,500,20);

        TextButton jumpButton = new TextButton("jumpYEP", BaseGame.textButtonStyle);

        jumpButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                if (!isTouchDownEvent(e)){
                    return false;
                }
                if(jax.isOnSolid()){
                    jax.jump();
                }
                return false;
            }
        });

        leftButton = new TextButton("<=", BaseGame.textButtonStyle);
        rightButton = new TextButton("=>", BaseGame.textButtonStyle);
        attackButton = new TextButton("=[==>", BaseGame.textButtonStyle);


        uiTable.pad(10);
//        uiTable.add(healthNow).top().colspan(2);
//        uiTable.row();
        uiTable.add(healthBar).top().colspan(2);
        uiTable.row();
        uiTable.add(leftButton).bottom();
        uiTable.add(rightButton).bottom();
        uiTable.add().expandX().expandY();
        uiTable.add(attackButton).bottom();
        uiTable.add(jumpButton).bottom();

    }

    @Override
    public void update(float dt) {

        if(leftButton.isPressed()){
            jax.addVelocityVec(-1);
        } else if(rightButton.isPressed()){
            jax.addVelocityVec(1);
        }else {
            jax.decelerateJax();
        }
        if(attackButton.isPressed()){
            jax.setHitting(true);
        } else {
            jax.setHitting(false);
        }

        for (BaseActor actor : BaseActor.getList(mainStage, "Solid")){
            Solid solid = (Solid)actor;
            if(jax.overlaps(solid) && solid.isEnabled()){
                Vector2 offset = jax.preventOverlap(solid);
                if(offset !=null){
                    if(Math.abs(offset.x)>Math.abs(offset.y)){
                        jax.velocityVec.x = 0;
                    } else{
                        jax.velocityVec.y = 0;
                    }
                }
            }
        }

        for(BaseActor fireActor : BaseActor.getList(mainStage, "Fire")){
            if(jax.overlaps(fireActor)){
                Fire fire = (Fire)fireActor;
                jax.setHealth(fire.getDamage());
                healthBar.setValue(jax.getHealth()/MainGameValues.jaxHealth);
            }
        }
//        healthNow.setText("Health: "+(int)jax.getHealth());
    }
}
