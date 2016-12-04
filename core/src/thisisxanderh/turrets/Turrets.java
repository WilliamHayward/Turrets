package thisisxanderh.turrets;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import thisisxanderh.turrets.actors.enemies.Spawner;
import thisisxanderh.turrets.actors.players.*;
import thisisxanderh.turrets.actors.players.Player;
import thisisxanderh.turrets.actors.players.PlayerTypes;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.core.commands.InvalidCommandException;
import thisisxanderh.turrets.graphics.SpriteCache;

public class Turrets extends ApplicationAdapter {
	GameStage stage;
	
	Texture background;
	
	private OrthographicCamera camera;
	
	
	@Override
	public void create () {
		SpriteCache.loadAllSprites();
		TiledMap map = new TmxMapLoader().load("tiles/test.tmx");
        
		stage = new GameStage();
		

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.zoom = 2f;
		
		Viewport viewport = new ScreenViewport(camera);
		
		stage.setViewport(viewport);
		
		
		stage.setMap(map);
		
		Spawner spawn = null;
		try {
			spawn = new Spawner("tiles/spawn.txt");
		} catch (InvalidCommandException e) {
			e.printStackTrace();
			System.exit(1);
		}

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if (cell == null) {
                	continue;
                }
                MapProperties properties = cell.getTile().getProperties();
                Object property = properties.get("spawn");
                if(property != null){
                	String type = (String) property;
                	stage.addSpawn(x, y, PlayerTypes.valueOf(type));
                    cell.setTile(null);
                }
                property = properties.get("enemy_path");
                if (property != null) {
                	int pathPosition  = (int) property;
                	spawn.addPosition(pathPosition, x, y);
                    cell.setTile(null);
                }
            }
        }
        stage.addActor(spawn);
		Player player = new Hero(camera);
		stage.addActor(player);
		player.spawn();
		
		camera.position.x = player.getX() + player.getWidth() / 4f;
		camera.position.y = player.getY() + player.getHeight() / 4f;
		
		background = new Texture(Gdx.files.internal("sprites/background.png"));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.getBatch().begin();
		Camera cam = stage.getCamera();
		float x = cam.position.x - cam.viewportWidth;
        TiledMapTileLayer layer = (TiledMapTileLayer) stage.getMap().getLayers().get(0);
		x -= (cam.position.x / layer.getWidth()) * 5;
		float y = cam.position.y - cam.viewportHeight;
		while (x < cam.position.x + cam.viewportWidth) {
			stage.getBatch().draw(background, x, y);
			x += background.getWidth();
		}
		stage.getBatch().end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		
	}
}
