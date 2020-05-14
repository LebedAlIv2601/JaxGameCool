package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Actors.BaseActor;
import com.mygdx.game.Screens.LoadingLevelsScreen;

import java.util.ArrayList;
import java.util.Iterator;

public class TilemapActor extends Actor {
//    Закоменнтированные строки - попытка сделать нормальную отрисовку с масштабом
//    public static int windowHeight = 100;
//    public static int windowWidth = Gdx.graphics.getWidth()/(Gdx.graphics.getHeight()/windowHeight);

    public static int windowWidth = Gdx.graphics.getWidth();
    public static int windowHeight = Gdx.graphics.getHeight();



    private TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;
    private AssetManager assetManager;
//    private float unitScale;

    public TilemapActor(String filename, Stage theStage){
//      Прогрузка непосредственно карты с помощью ассет менеджера

        tiledMap = LoadingLevelsScreen.assetManagerLvl.get("Level1Map/" + filename,TiledMap.class);

        int tileWidth  = (int)tiledMap.getProperties().get("tilewidth");
        int tileHeight = (int)tiledMap.getProperties().get("tileheight");
        int numTilesHorizontal = (int)tiledMap.getProperties().get("width");
        int numTilesVertical = (int)tiledMap.getProperties().get("height");
        int mapWidth = tileWidth * numTilesHorizontal;
        int mapHeight = tileHeight*numTilesVertical;

        BaseActor.setWorldBounds(mapWidth,mapHeight);

//        unitScale = 1f / 100f;

        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, windowWidth, windowHeight);

        theStage.addActor(this);
    }

    public void act(float dt){
        super.act(dt);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//      Привязка камеры на карте к камере сцены, которая устанавливается в BaseActor
        Camera mainCamera = getStage().getCamera();
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
//        tiledCamera.zoom = 2;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);
        batch.end();
        tiledMapRenderer.render();
        batch.begin();
    }

    public ArrayList<MapObject> getRectangleList(String propertyName){
        ArrayList<MapObject> list = new ArrayList<MapObject>();

        for (MapLayer layer : tiledMap.getLayers()){
            for(MapObject obj : layer.getObjects()){
                if(!(obj instanceof RectangleMapObject)){
                    continue;
                }

                MapProperties props = obj.getProperties();
                if(props.containsKey("name") && props.get("name").equals(propertyName)){
                    list.add(obj);
                }
            }
        }
        return list;
    }


    public ArrayList<MapObject> getTileList(String propertyName){
        ArrayList<MapObject> list = new ArrayList<MapObject>();

        for(MapLayer layer : tiledMap.getLayers()){
            for(MapObject obj : layer.getObjects()){
                if(!(obj instanceof TiledMapTileMapObject)){
                    continue;
                }

                MapProperties props = obj.getProperties();

                TiledMapTileMapObject tmtmo = (TiledMapTileMapObject)obj;
                TiledMapTile t = tmtmo.getTile();
                MapProperties defaultProps = t.getProperties();

                if(defaultProps.containsKey("name") && defaultProps.get("name").equals(propertyName)){
                    list.add(obj);
                }

                Iterator<String> propertyKeys = defaultProps.getKeys();
                while(propertyKeys.hasNext()){
                    String key = propertyKeys.next();

                    if(props.containsKey(key)){
                        continue;
                    }
                    else{
                        Object value = defaultProps.get(key);
                        props.put(key, value);
                    }
                }
            }
        }
        return list;
    }
}
