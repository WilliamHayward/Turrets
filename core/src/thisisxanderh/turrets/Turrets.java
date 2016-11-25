package thisisxanderh.turrets;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import thisisxanderh.turrets.actors.Player;
import thisisxanderh.turrets.core.GameStage;
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
		stage.setMap(map);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for(int x = 0; x < layer.getWidth(); x++){
            for(int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if (cell == null) {
                	continue;
                }
                Object property = cell.getTile().getProperties().get("spawn");
                if(property != null){
                	stage.addSpawn(x, y);
                    cell.setTile(null);
                }
            }
        }
        
		Player player = new Player();
		stage.addActor(player);
		player.spawn();
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.zoom = 2f;
		
		Viewport viewport = new ScreenViewport(camera);
		
		stage.setViewport(viewport);
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
}
