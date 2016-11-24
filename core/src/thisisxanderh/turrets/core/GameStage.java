package thisisxanderh.turrets.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import thisisxanderh.turrets.terrain.Terrain;

public class GameStage extends Stage {
	
	TiledMap map;
	TiledMapRenderer mapRenderer;
	Terrain terrain;
	public GameStage() {
		
	}

	public GameStage(Viewport viewport) {
		super(viewport);
	}

	public GameStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
	}
	
	public void setMap(TiledMap map) {
		this.map = map;
		terrain = new Terrain(map);
		mapRenderer = new OrthogonalTiledMapRenderer(map);
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	@Override
	public void draw() {
		OrthographicCamera camera = (OrthographicCamera) this.getViewport().getCamera();
		if (camera != null) {
	        camera.update();
	        mapRenderer.setView(camera);
	        mapRenderer.render();
		}
		super.draw();
	}
}
