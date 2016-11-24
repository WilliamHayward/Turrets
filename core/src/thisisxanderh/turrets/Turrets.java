package thisisxanderh.turrets;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import thisisxanderh.turrets.actors.Player;
import thisisxanderh.turrets.core.GameStage;

public class Turrets extends ApplicationAdapter {
	GameStage stage;
	
	private OrthographicCamera camera;
	
	@Override
	public void create () {
		TiledMap map = new TmxMapLoader().load("tiles/test.tmx");
        
		stage = new GameStage();
		stage.setMap(map);

		float xSpawn = 0;
		float ySpawn = 0;
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for(int x = 0; x < layer.getWidth(); x++){
            for(int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if (cell == null) {
                	continue;
                }
                Object property = cell.getTile().getProperties().get("spawn");
                if(property != null){
                    xSpawn = x;
                    ySpawn = y;
                }
            }
        }
        
		Player player = new Player();
		player.setPosition(xSpawn * player.getWidth(), ySpawn * player.getHeight());
		stage.addActor(player);
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		
		Viewport viewport = new ScreenViewport(camera);
		
		stage.setViewport(viewport);
		camera.position.x = player.getX();
		camera.position.y = player.getY();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
	}
}
